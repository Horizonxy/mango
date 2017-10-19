package cn.com.mangopi.android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mcxiaoke.bus.Bus;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.LoadingDialog;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.OkHttpUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String APP_SECRET = "98ad0ece5613c7edf35d56f53c39403b";
    private IWXAPI mWeixinAPI;
    public static final String WEIXIN_APP_ID = "wxea43a55c5ca9f34d";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeixinAPI = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
        mWeixinAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWeixinAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if(ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()){
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    AppUtils.showToast(this, "分享失败");
                    break;
                case BaseResp.ErrCode.ERR_OK:
//                    AppUtils.showToast(this, "微信分享成功");
                    break;
            }
            finish();
        } else if(ConstantsAPI.COMMAND_SENDAUTH == resp.getType()){
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //case BaseResp.ErrCode.ERR_USER_CANCEL:
                    AppUtils.showToast(this, "登录失败");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_OK:
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    if (sendResp != null) {
                        String code = sendResp.code;
                        getAccess_token(code);
                    }
                    break;
            }
        } else if(ConstantsAPI.COMMAND_PAY_BY_WX == resp.getType()){
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //case BaseResp.ErrCode.ERR_USER_CANCEL:
                    AppUtils.showToast(this, "支付失败");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_OK:
                    break;
            }
        }
    }

    LoadingDialog loadingDialog;
    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        loadingDialog = new LoadingDialog(this, getString(R.string.please_wait));
        loadingDialog.show();

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WEIXIN_APP_ID
                + "&secret="
                + APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        Logger.d("getAccess_token：" + path);

        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String openid = jsonObject.getString("openid").toString().trim();

                    String access_token = jsonObject.getString("access_token").toString().trim();
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {
                AppUtils.showToast(WXEntryActivity.this, "登录失败");

                loadingDialog.dismiss();
                finish();
            }
        };

        OkHttpUtils.get(path, resultCallback);
    }

    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        Logger.d("getUserMesg：" + path);


        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String openId = jsonObject.getString("openid");
                    String unoinId = jsonObject.getString("unionid");

                    BusEvent.WxOpenIdEvent event = new BusEvent.WxOpenIdEvent();
                    event.setOpenId(openId);
                    event.setUnoinId(unoinId);
                    Bus.getDefault().postSticky(event);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                AppUtils.showToast(WXEntryActivity.this, "登录失败");
                if(loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                finish();
            }
        };
        OkHttpUtils.get(path, resultCallback);
    }

    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("SLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
