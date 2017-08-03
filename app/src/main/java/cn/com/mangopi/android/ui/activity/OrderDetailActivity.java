package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderDetailListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;

public class OrderDetailActivity extends BaseTitleBarActivity implements OrderDetailListener {

    long id;
    OrderPresenter presenter;
    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_pay_state_label)
    TextView tvStateLabel;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.tv_order_count)
    TextView tvOrderCount;
    @Bind(R.id.tv_order_name)
    TextView tvOrderName;
    @Bind(R.id.tv_sale_price)
    TextView tvSalePrice;
    @Bind(R.id.tv_member_name)
    TextView tvMemberName;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    OrderDetailBean orderDetail;
    @Bind(R.id.btn_cancle)
    Button btnCancel;
    @Bind(R.id.btn_pay)
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ORDER_ID, 0);
        initView();
        presenter = new OrderPresenter(new OrderModel(), this);
        presenter.getOrder();
    }

    private void initView() {
        titleBar.setTitle(R.string.order_detail);
    }

    private void fillData(OrderDetailBean orderDetail){
        this.orderDetail = orderDetail;
        tvOrderNo.setText("单号："+orderDetail.getFiveLenOrderNo());
        tvOrderTime.setText(DateUtils.dateToString(orderDetail.getOrder_time(), DateUtils.TIME_PATTERN_YMDHM));
        tvStateLabel.setText(orderDetail.getState_label());
        tvOrderName.setText(orderDetail.getOrder_name());
        tvOrderCount.setText("x "+orderDetail.getOrder_count());
        tvMemberName.setText(orderDetail.getTutor_name());
        if(orderDetail.getSale_price() != null) {
            tvSalePrice.setText(getString(R.string.rmb)+orderDetail.getSale_price().toString());
        }
        if(orderDetail.getSale_price() != null) {
            tvTotalPrice.setText(getString(R.string.rmb)+orderDetail.getTotal_price().toString());
        }
        if(orderDetail.getDiscount_price() != null) {
            tvDiscountPrice.setText(getString(R.string.rmb)+orderDetail.getDiscount_price().toString());
        }
        if(orderDetail.getPay_price() != null) {
            tvPayPrice.setText(getString(R.string.rmb)+orderDetail.getPay_price().toString());
        }
        if(orderDetail.getState() != null && orderDetail.getState().intValue() == 2) {
            btnCancel.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.GONE);
            btnPay.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_pay)
    void onPay(View v){
        ActivityBuilder.startSelectPayActivity(this, converOrderBean(orderDetail));
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
    public void onSuccess(OrderDetailBean orderDetail) {
        fillData(orderDetail);
    }

    @Override
    public long getId() {
        return id;
    }

    private OrderBean converOrderBean(OrderDetailBean orderDetail){
        OrderBean order = new OrderBean();
        order.setSale_price(orderDetail.getSale_price());
        order.setPay_price(orderDetail.getPay_price());
        order.setTotal_price(orderDetail.getTotal_price());
        order.setDiscount_price(orderDetail.getDiscount_price());
        order.setId(orderDetail.getId());
        order.setOrder_count(orderDetail.getOrder_count());
        order.setMember_id(orderDetail.getMember_id());
        order.setMember_name(orderDetail.getMember_name());
        order.setCourse_id(orderDetail.getCourse_id());
        order.setOrder_name(orderDetail.getOrder_name());
        order.setOrder_no(orderDetail.getOrder_no());
        order.setPromotion_code(orderDetail.getPromotion_code());
        return order;
    }
}
