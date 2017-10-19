package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.PayResultBean;
import cn.com.mangopi.android.model.bean.ShowPayResultBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPayPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderPayListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.wxapi.WXEntryActivity;

public class SelectPayActivity extends BaseTitleBarActivity implements OrderPayListener {

    OrderBean order;
    @Bind(R.id.layout_unionpay)
    LinearLayout layoutUnionPay;
    @Bind(R.id.line_union)
    View lineUnion;
    @Bind(R.id.layout_alipay)
    LinearLayout layoutAlipay;
    @Bind(R.id.line_alipay)
    View lineAlipay;
    @Bind(R.id.layout_wechatpay)
    LinearLayout layoutWechatPay;
    String channel;
    OrderPayPresenter payPresenter;
    @Bind(R.id.iv_union_right)
    ImageView ivUnionRight;
    @Bind(R.id.iv_alipay_right)
    ImageView ivAlipayRight;
    @Bind(R.id.iv_wechat_right)
    ImageView ivWechatRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);
        Bus.getDefault().register(this);

        order = (OrderBean) getIntent().getSerializableExtra(Constants.BUNDLE_ORDER);
        if(order == null){
            finish();
        }
        initView();
        payPresenter = new OrderPayPresenter(new OrderModel(), this);
        layoutWechatPay.performClick();
    }

    @OnClick(R.id.layout_unionpay)
    void unionpayClick(View v){
        channel = Constants.UNION_PAY;
        ivUnionRight.setSelected(true);
        ivAlipayRight.setSelected(false);
        ivWechatRight.setSelected(false);
    }

    @OnClick(R.id.layout_alipay)
    void alipayClick(View v){
        channel = Constants.ALI_PAY;
        ivUnionRight.setSelected(false);
        ivAlipayRight.setSelected(true);
        ivWechatRight.setSelected(false);
    }

    @OnClick(R.id.layout_wechatpay)
    void wechatClick(View v){
        channel = Constants.WECHAT_PAY;
        ivUnionRight.setSelected(false);
        ivAlipayRight.setSelected(false);
        ivWechatRight.setSelected(true);
    }

    @OnClick(R.id.btn_pay)
    void payClick(View v){
        if(TextUtils.isEmpty(channel)){
            AppUtils.showToast(this, "请选择支付方式");
            return;
        }
        payPresenter.orderPay();
    }

    private void initView() {
        titleBar.setTitle(R.string.pay_for_order);

//        lineUnion.setVisibility(View.GONE);
//        layoutUnionPay.setVisibility(View.GONE);
//        lineAlipay.setVisibility(View.GONE);
//        layoutAlipay.setVisibility(View.GONE);
    }

    @Override
    public long getId() {
        return order.getId();
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public void onSuccess(PayResultBean data) {
        String payDataStr = data.getPayData();

        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, WXEntryActivity.WEIXIN_APP_ID, true);
        PayReq req = new PayReq();
        req.appId = WXEntryActivity.WEIXIN_APP_ID;// 微信开放平台审核通过的应用APPID
        try {
            JSONObject payData = new JSONObject(payDataStr);
            req.partnerId = payData.getString("partnerid");// 微信支付分配的商户号
            req.prepayId = payData.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
            req.nonceStr = payData.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
            req.timeStamp = payData.getString("timestamp");// 时间戳，app服务器小哥给出
            req.packageValue = payData.getString("package");// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
            req.sign = payData.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mWxApi.sendReq(req);
    }

    @BusReceiver
    public void onPayCodeEventEvent(BusEvent.PayCodeEvent event) {
        ShowPayResultBean result = new ShowPayResultBean();
        if(order.getPay_price() != null) {
            result.setPayPrice(order.getPay_price().toString());
        }
        result.setOrderName(order.getOrder_name());
        result.setOrderNo(order.getOrder_no());
        result.setPayDate(new Date());
        result.setPayChannel(getChannel());
        if(event.getErrorCode() == 0){//支付成功
            result.setSuccess(true);
            BusEvent.PayOrderSuccessEvent payOrderSuccessEvent = new BusEvent.PayOrderSuccessEvent();
            payOrderSuccessEvent.setId(getId());
            Bus.getDefault().postSticky(payOrderSuccessEvent);
        } else {
            result.setSuccess(false);
        }
        ActivityBuilder.startPayResultActivity(this, result);
        finish();
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if(payPresenter !=  null){
            payPresenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }
}
