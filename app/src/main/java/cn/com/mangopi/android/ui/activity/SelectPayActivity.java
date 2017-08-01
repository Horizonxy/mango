package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPayPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderPayListener;
import cn.com.mangopi.android.util.AppUtils;

public class SelectPayActivity extends BaseTitleBarActivity implements OrderPayListener {

    OrderBean order;
    @Bind(R.id.layout_unionpay)
    LinearLayout layoutUnionppay;
    @Bind(R.id.layout_alipay)
    LinearLayout layoutAlipay;
    @Bind(R.id.layout_wechatpay)
    LinearLayout layoutWechatpay;
    String channel;
    OrderPayPresenter payPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);

        order = (OrderBean) getIntent().getSerializableExtra(Constants.BUNDLE_ORDER);
        if(order == null){
            finish();
        }
        initView();
        payPresenter = new OrderPayPresenter(new OrderModel(), this);
    }

    @OnClick(R.id.layout_unionpay)
    void unionpayClick(View v){
        channel = "";
    }

    @OnClick(R.id.layout_alipay)
    void alipayClick(View v){
        channel = "";
    }

    @OnClick(R.id.layout_wechatpay)
    void wechatClick(View v){
        channel = Constants.WECHAT_PAY;
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
    public void onSuccess(String payData) {

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
        super.onDestroy();
    }
}
