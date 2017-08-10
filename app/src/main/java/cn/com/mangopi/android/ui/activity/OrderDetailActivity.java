package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderDetailListener;
import cn.com.mangopi.android.ui.widget.UploadPictureView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;

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
    @Bind(R.id.layout_material)
    LinearLayout layoutMaterial;
    @Bind(R.id.grid_material)
    GridLayout gridMaterial;
    @Bind(R.id.line_material)
    View lineMaterial;
    int dp5;
    FrameLayout.LayoutParams rsItemLp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ORDER_ID, 0);
        initView();
        presenter = new OrderPresenter(new OrderModel(), this);
        presenter.getOrder();

        dp5 = (int) getResources().getDimension(R.dimen.dp_5);
        int width = (int) ((DisplayUtils.screenWidth(this) - getResources().getDimension(R.dimen.dp_15) * 2 - dp5 * 4) / 5);
        rsItemLp = new FrameLayout.LayoutParams(width, width);
    }

    private void initView() {
        titleBar.setTitle(R.string.order_detail);
    }

    private void fillData(OrderDetailBean orderDetail){
        this.orderDetail = orderDetail;
        if(orderDetail.getMaterial_rsurls() == null || orderDetail.getMaterial_rsurls().size() == 0){
            layoutMaterial.setVisibility(View.GONE);
            lineMaterial.setVisibility(View.GONE);
        } else {
            layoutMaterial.setVisibility(View.VISIBLE);
            lineMaterial.setVisibility(View.VISIBLE);
            for (int i = 0; i < orderDetail.getMaterial_rsurls().size(); i++){
                String rsurl = orderDetail.getMaterial_rsurls().get(i);
                ImageView item = new ImageView(this);
                item.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Application.application.getImageLoader().displayImage(rsurl, item, Application.application.getDefaultOptions());
                GridLayout.LayoutParams gl = new GridLayout.LayoutParams(rsItemLp);
                if(i >= 5){
                    gl.topMargin = dp5;
                }
                if (i % 5 == 1) {
                    gl.leftMargin = dp5;
                } else if(i % 5 == 2) {
                    gl.leftMargin = dp5;
                    gl.rightMargin = dp5;
                } else if(i % 5 == 3){
                    gl.rightMargin = dp5;
                }
                gridMaterial.addView(item, i, gl);
            }
        }
        tvOrderNo.setText("单号："+orderDetail.getFiveLenOrderNo());
        tvOrderTime.setText(DateUtils.dateToString(orderDetail.getOrder_time(), DateUtils.TIME_PATTERN_YMDHM));
        tvStateLabel.setText(orderDetail.getState_label());
        tvOrderName.setText(orderDetail.getOrder_name());
        tvOrderCount.setText("x "+orderDetail.getOrder_count());
        tvMemberName.setText(orderDetail.getTutor_name());
        if(orderDetail.getSale_price() != null) {
            tvSalePrice.setText(getString(R.string.rmb)+orderDetail.getSale_price().toString());
        }
        if(orderDetail.getTotal_price() != null) {
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
