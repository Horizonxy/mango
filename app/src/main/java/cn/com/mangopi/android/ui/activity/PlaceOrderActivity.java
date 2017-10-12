package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CalcPriceBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.model.bean.CourseCouponBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.CalcPricePresenter;
import cn.com.mangopi.android.presenter.CouponListPresenter;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.dialog.ListViewDialog;
import cn.com.mangopi.android.ui.viewlistener.CalcPriceListener;
import cn.com.mangopi.android.ui.viewlistener.CouponListListener;
import cn.com.mangopi.android.ui.viewlistener.PlaceOrderListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

public class PlaceOrderActivity extends BaseTitleBarActivity implements PlaceOrderListener, CouponListListener, CalcPriceListener {

    CourseDetailBean courseDetail;
    OrderPresenter presenter;
    @Bind(R.id.tv_course_name)
    TextView tvCourseName;
    @Bind(R.id.tv_tutor_name)
    TextView tvTutorName;
    @Bind(R.id.tv_order_count)
    TextView tvOrderCount;
    @Bind(R.id.et_promotion_code)
    EditText etPromotionCode;
    @Bind(R.id.tv_sale_price)
    TextView tvSalePrice;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    String promotionCode;
    @Bind(R.id.tv_code_desc)
    TextView tvCodeDesc;
    @Bind(R.id.tv_coupon_name)
    TextView tvCouponName;
    List<CourseCouponBean>  couponList = new ArrayList<>();
    CouponListPresenter couponListPresenter;
    @Bind(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    CalcPricePresenter calcPricePresenter;
    @Bind(R.id.btn_add_order)
    Button btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        courseDetail = (CourseDetailBean) getIntent().getSerializableExtra(Constants.BUNDLE_COURSE_DETAIL);
        initView();
        presenter = new OrderPresenter(new OrderModel(), this);
        calcPricePresenter = new CalcPricePresenter(this);
        calcPricePresenter.calcPrice();
    }

    private void initView() {
        titleBar.setTitle(R.string.place_order);
        tvDiscountPrice.setText(String.format(getString(R.string.discount_price), "0"));
        if(courseDetail != null){
            tvCourseName.setText(courseDetail.getCourse_title());
            tvTutorName.setText(courseDetail.getMember_name());
            tvOrderCount.setText("x 1");
        }
    }

    @OnClick(R.id.layout_coupon)
    void onCouponClicked(View v){
        if(couponList.size() > 0){
            showCouponList(this.couponList);
        } else {
            if (couponListPresenter == null) {
                couponListPresenter = new CouponListPresenter(this);
            }
            couponListPresenter.courseCouponList(courseDetail.getId());
        }
    }

    @OnClick(R.id.btn_use)
    void onUseClick(View v){
        calcPricePresenter.calcPrice();
    }

    @OnClick(R.id.btn_add_order)
    void addOrder(View v){
        presenter.addOrder();
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
    public long getId() {
        return courseDetail.getId();
    }

    @Override
    public void onSuccess(OrderBean order) {
        ActivityBuilder.startSelectPayActivity(this, order);
        finish();
    }

    @Override
    public Map<String, Object> addOrderQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        if(courseDetail == null){
            return map;
        }
        map.put("lst_sessid", Application.application.getSessId());
        map.put("id", courseDetail.getId());
        map.put("order_count", 1);
        if(!TextUtils.isEmpty(promotionCode)) {
            map.put("promotion_code", promotionCode);
        }
        if(tvCouponName.getTag() != null) {
            CourseCouponBean couponBean = (CourseCouponBean) tvCouponName.getTag();
            map.put("member_coupon_id", couponBean.getId());
        }
        return map;
    }

    @Override
    protected void onDestroy() {
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(calcPricePresenter != null){
            calcPricePresenter.onDestroy();
        }
        if(couponListPresenter != null){
            couponListPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public int getPageNo() {
        return 1;
    }

    @Override
    public int getState() {
        return 50;
    }

    @Override
    public void onCouponListSuccess(List<CouponBean> couponList) {
    }

    @Override
    public void onCourseCouponListSuccess(List<CourseCouponBean> courseCouponList) {
        this.couponList.clear();
        if(courseCouponList != null) {
            this.couponList.addAll(courseCouponList);
        }
        showCouponList(this.couponList);
    }

    private void showCouponList(List<CourseCouponBean> couponList){
        if(couponList.size() > 0) {
            ListViewDialog<CourseCouponBean> couponListDialog = new ListViewDialog<CourseCouponBean>("选择优惠券", couponList) {
                @Override
                public void fiddData(BaseAdapterHelper helper, CourseCouponBean item) {
                    helper.setText(R.id.tv_text, item.getCouponName());
                }
            };
            couponListDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<CourseCouponBean>() {
                @Override
                public void onItemClicked(CourseCouponBean data) {
                    tvCouponName.setText(data.getCouponName());
                    tvCouponName.setTag(data);

                    calcPricePresenter.calcPrice();
                }
            });
            couponListDialog.show(this, (CourseCouponBean) tvCouponName.getTag());
        } else {
            onFailure("无可用优惠券");
        }
    }

    @Override
    public Map<String, Object> getCalcMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", courseDetail.getId());
        map.put("order_count", 1);
        if(!TextUtils.isEmpty(promotionCode)) {
            map.put("promotion_code", promotionCode);
        }
        if(tvCouponName.getTag() != null) {
            CourseCouponBean couponBean = (CourseCouponBean) tvCouponName.getTag();
            map.put("member_coupon_id", couponBean.getId());
        }
        return map;
    }

    @Override
    public void onCalcSuccess(CalcPriceBean calcPrice) {
        if(!TextUtils.isEmpty(calcPrice.getDiscount_price_label())) {
            tvCodeDesc.setText(calcPrice.getDiscount_price_label());
            tvCodeDesc.setVisibility(View.VISIBLE);
        } else {
            tvCodeDesc.setVisibility(View.GONE);
        }
        tvDiscountPrice.setText(String.format(getString(R.string.discount_price), calcPrice.getDiscount_price().toString()));
        tvSalePrice.setText(getString(R.string.rmb) + calcPrice.getTotal_price().toString());
        tvTotalPrice.setText(getString(R.string.rmb) + calcPrice.getPay_price().toString());
        btnAddOrder.setEnabled(true);
    }

    @Override
    public void onCalcFailure(String message) {
        tvCodeDesc.setText(message);
        tvCodeDesc.setVisibility(View.VISIBLE);
        btnAddOrder.setEnabled(false);
    }
}
