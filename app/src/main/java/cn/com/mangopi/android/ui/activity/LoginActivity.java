package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerLoginActivityComponent;
import cn.com.mangopi.android.di.module.LoginActivityModule;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.presenter.LoginPresenter;
import cn.com.mangopi.android.ui.viewlistener.LoginListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.wxapi.WXEntryActivity;
import rx.functions.Action1;

public class LoginActivity extends BaseTitleBarActivity implements LoginListener<RegistBean> {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verify_code)
    EditText etVerifyCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_protocol)
    TextView tvProtocal;
    @Bind(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @Inject
    LoginPresenter loginPresenter;
    @Bind(R.id.ib_wx_login)
    ImageButton ibWxLogin;
    CountDownTimer getCodeCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bus.getDefault().register(this);
        DaggerLoginActivityComponent.builder().loginActivityModule(new LoginActivityModule(this)).build().inject(this);

        initView();

        Application.application.loginOut();
        Application.application.finishBesides(this);
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.login_mango));
        titleBar.setLeftBtnIconVisibility(View.GONE);

        btnLogin.setEnabled(false);

        RxTextView.textChanges(etPhone).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnLogin.setEnabled(etVerifyCode.getText().length() > 0 && etPhone.getText().length() == 11);
            }
        });
        RxTextView.textChanges(etVerifyCode).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnLogin.setEnabled(etVerifyCode.getText().length() > 0 && etPhone.getText().length() == 11);
            }
        });

        RxView.clicks(ibWxLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                loginWx();
            }
        });

        RxView.clicks(btnLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                loginPresenter.quickLogin(etVerifyCode.getText().toString());
            }
        });

        initProtocol();
        setGetVerifyCodeTvWidth();
    }

    private void loginWx() {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(this, WXEntryActivity.WEIXIN_APP_ID, true);
        wxApi.registerApp(WXEntryActivity.WEIXIN_APP_ID);
        if (wxApi != null && wxApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_login";
            wxApi.sendReq(req);
        } else {
            AppUtils.showToast(this, R.string.uninstalled_wx);
        }
    }

    private void setGetVerifyCodeTvWidth() {
        tvGetVerifyCode.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = tvGetVerifyCode.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvGetVerifyCode.getLayoutParams();
                params.width = width;
                tvGetVerifyCode.setLayoutParams(params);
                return true;
            }
        });
    }

    private void initProtocol() {
        String tip = getResources().getString(R.string.login_protocal_tip);
        String userProtocol = getResources().getString(R.string.user_protocal);

        SpannableString span = new SpannableString(tip + userProtocol);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ActivityBuilder.startWebViewActivity(LoginActivity.this, Constants.USER_PROTOCOL);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.color_14b2ec));
            }
        }, tip.length(), tip.length() + userProtocol.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvProtocal.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvProtocal.setText(span);
        tvProtocal.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.tv_get_verify_code)
    void getVerifyCode(View v){
        loginPresenter.getVerifyCode(etPhone.getText().toString());
    }

    @BusReceiver
    public void onActivityFinishEvent(BusEvent.ActivityFinishEvent event){
        if(event != null && event.isFinish()){
            finish();
        }
    }

    @BusReceiver
    public void onWxOpenIdEvent(BusEvent.WxOpenIdEvent event){
        loginPresenter.wxLogin(event.getOpenId(), event.getUnoinId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getCodeCountDownTimer != null){
            getCodeCountDownTimer.cancel();
            getCodeCountDownTimer = null;
        }
        Bus.getDefault().unregister(this);

        if(loginPresenter != null) {
            loginPresenter.onDestroy();
        }
    }

    @Override
    public void onSuccess(RegistBean data) {
        if(data == null) {
            tvGetVerifyCode.setEnabled(false);
            getCodeCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvGetVerifyCode.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    tvGetVerifyCode.setEnabled(true);
                    tvGetVerifyCode.setText(getString(R.string.get_verify_code));
                }
            };
            getCodeCountDownTimer.start();
        }
    }

    @Override
    public void startSetNickName() {
        ActivityBuilder.startSetNickNameActivity(this);
        ActivityBuilder.defaultTransition(this);
    }

    @Override
    public void startMain() {
        ActivityBuilder.startMainActivity(this);
        ActivityBuilder.defaultTransition(this);
        finish();
    }

    @Override
    public void startRegist(String openId, String unionId) {
        Intent intent = new Intent(this, RegistActivity.class);
        intent.putExtra("open_id", openId);
        intent.putExtra("union_d", unionId);
        startActivity(intent);
    }

    @Override
    public Map<String, Object> getLoginParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", etPhone.getText().toString());
        map.put("sms_code", etVerifyCode.getText().toString());
        return map;
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
