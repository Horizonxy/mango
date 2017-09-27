package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPayPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderPayListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

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
    public void onSuccess(String payData) {
        ActivityBuilder.startPayResultActivity(this);
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
        super.onDestroy();
    }
}
