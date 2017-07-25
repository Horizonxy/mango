package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.CourseDetailBean;
import com.mango.model.data.CourseModel;
import com.mango.model.data.FavModel;
import com.mango.presenter.CourseDetailPresenter;
import com.mango.presenter.FavPresenter;
import com.mango.ui.viewlistener.CourseDetailListener;
import com.mango.ui.viewlistener.FavListener;
import com.mango.ui.widget.TitleBar;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class CourseDetailActivity extends BaseTitleBarActivity implements CourseDetailListener, FavListener, TitleBar.OnTitleBarClickListener {

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
    @Bind(R.id.btn_place_order)
    Button btnPlaceOrder;
    CourseDetailPresenter presenter;
    CourseDetailBean courseDetail;
    FavPresenter favPresenter;

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
        titleBar.setOnTitleBarClickListener(this);
        btnPlaceOrder.setEnabled(false);
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
        this.courseDetail = courseDetail;
        fillCourseData(courseDetail);
        btnPlaceOrder.setEnabled(true);
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
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang);
        } else {
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
        }
        ivWant.setImageResource(R.drawable.faxian_xiangting);

        tvContent.setText(courseDetail.getCourse_content());
        tvTypeMethod.setText(courseDetail.getType_method()+"，"
                + courseDetail.getEach_time() +"/" + courseDetail.getService_time()+"  "
                +getString(R.string.rmb)+courseDetail.getSale_price().toString());
    }

    @OnClick(R.id.btn_place_order)
    void onPlaceOrder(View v){
        ActivityBuilder.startPlaceOrderActivity(this, courseDetail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(favPresenter != null){
            favPresenter.onDestroy();
        }
    }

    @Override
    public long getEntityId() {
        return courseDetail.getId();
    }

    @Override
    public int getEntityTypeId() {
        return Constants.EntityType.COURSE.getTypeId();
    }

    @Override
    public void onSuccess(boolean success) {
        if(success){
            courseDetail.setIs_favor(true);
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang);
         } else {
            courseDetail.setIs_favor(false);
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
        }
    }

    @Override
    public void onTitleButtonClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ib_right:
                break;
            case R.id.ib_second_right:
                if(favPresenter == null){
                    favPresenter = new FavPresenter(new FavModel(), this);
                }
                if(courseDetail.is_favor()){
                    favPresenter.delFav();
                } else {
                    favPresenter.addFav();
                }
                break;
        }
    }
}
