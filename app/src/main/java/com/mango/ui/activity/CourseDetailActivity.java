package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.data.CourseDetailBean;
import com.mango.model.data.CourseModel;
import com.mango.presenter.CourseDetailPresenter;
import com.mango.ui.viewlistener.CourseDetailListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class CourseDetailActivity extends BaseTitleBarActivity implements CourseDetailListener {

    long id;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_method)
    TextView tvMethod;
    @Bind(R.id.tv_course_name)
    TextView tvCourseTitle;
    @Bind(R.id.tv_tutor_name)
    TextView tvToturName;
    @Bind(R.id.iv_want)
    ImageView ivWant;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_type_method)
    TextView tvTypeMethod;
    CourseDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        presenter = new CourseDetailPresenter(new CourseModel(), this);
        presenter.getCourse();
    }

    private void initView() {
        titleBar.setTitle(R.string.course_detail);
        titleBar.setRightBtnIcon(R.drawable.icon_share);
        titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
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
        return id;
    }

    @Override
    public void onSuccess(CourseDetailBean courseDetail) {
        fillCourseData(courseDetail);
    }

    private void fillCourseData(CourseDetailBean courseDetail) {
        Application.application.getImageLoader().displayImage(courseDetail.getAvatar_rsurl(), ivLogo, Application.application.getDefaultOptions());
        tvCourseTitle.setText(courseDetail.getCourse_title());
        if(courseDetail.getMember_name() != null) {
            tvToturName.setText("（" + courseDetail.getMember_name() + "）");
        }
        tvCity.setText(courseDetail.getCity());
        tvMethod.setText(courseDetail.getType_method());
        if(courseDetail.getSale_price() != null) {
            tvPrice.setText(getString(R.string.rmb) + courseDetail.getSale_price().toString());
        }
        if(courseDetail.is_favor()){
            ivWant.setImageResource(R.drawable.icon_shoucang);
        } else {
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
        }
        tvContent.setText(courseDetail.getCourse_content());
        tvTypeMethod.setText(courseDetail.getType_method()+"，"
                + courseDetail.getEach_time() +"/" + courseDetail.getService_time()+"  "
                +getString(R.string.rmb)+courseDetail.getSale_price().toString());
    }

    @OnClick(R.id.btn_place_order)
    void onPlaceOrder(View v){
        ActivityBuilder.startPlaceOrderActivity(this, id);
    }
}
