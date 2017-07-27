package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.viewlistener.PlaceOrderListener;
import cn.com.mangopi.android.util.AppUtils;

public class PlaceOrderActivity extends BaseTitleBarActivity implements PlaceOrderListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        courseDetail = (CourseDetailBean) getIntent().getSerializableExtra(Constants.BUNDLE_COURSE_DETAIL);
        initView();
        presenter = new OrderPresenter(new OrderModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.place_order);
        if(courseDetail != null){
            tvCourseName.setText(courseDetail.getCourse_title());
            tvTutorName.setText(courseDetail.getMember_name());
            tvOrderCount.setText("x 1");
            tvSalePrice.setText(getString(R.string.rmb) + courseDetail.getSale_price().toString());
            tvTotalPrice.setText(getString(R.string.rmb) + courseDetail.getSale_price().toString());
        }
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
        return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
