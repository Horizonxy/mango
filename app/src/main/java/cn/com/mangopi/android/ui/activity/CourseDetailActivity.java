package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.presenter.CourseDetailPresenter;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.presenter.WantCountPresenter;
import cn.com.mangopi.android.ui.popupwindow.SharePopupWindow;
import cn.com.mangopi.android.ui.viewlistener.CourseDetailListener;
import cn.com.mangopi.android.ui.viewlistener.FavListener;
import cn.com.mangopi.android.ui.viewlistener.WantCountListener;
import cn.com.mangopi.android.ui.widget.MangoUMShareListener;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class CourseDetailActivity extends BaseTitleBarActivity implements CourseDetailListener, FavListener, TitleBar.OnTitleBarClickListener, WantCountListener {

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
    @Bind(R.id.tv_type_explains)
    TextView tvTypeExplains;
    WantCountPresenter wantCountPresenter;
    boolean upgrade;

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

        titleBar.setOnTitleBarClickListener(this);
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
        if(courseDetail.getState() != null && courseDetail.getState().intValue() == 0 &&
                Application.application.getMember() != null && courseDetail.getMember_id() == Application.application.getMember().getId()){
            titleBar.setRightText("修改");
            upgrade = true;
        }else {
            titleBar.setRightBtnIcon(R.drawable.icon_share);
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
            upgrade = false;
        }
        fillCourseData(courseDetail);
    }

    private void fillCourseData(CourseDetailBean courseDetail) {
        if(courseDetail.getApprove_state() != null && courseDetail.getApprove_state().intValue() == 50 && courseDetail.getState() != null && courseDetail.getState().intValue() == 50){
            btnPlaceOrder.setEnabled(true);
        } else {
            btnPlaceOrder.setEnabled(false);
        }

        Application.application.getImageLoader().displayImage(MangoUtils.getCalculateScreenWidthSizeUrl(courseDetail.getAvatar_rsurl()), ivLogo, Application.application.getDefaultOptions());
        tvCourseTitle.setText(courseDetail.getCourse_title());
        if(courseDetail.getMember_name() != null) {
            tvToturName.setText("（" + courseDetail.getMember_name() + "）");
        }
        tvCity.setText(courseDetail.getCity());
        tvMethod.setText(courseDetail.getType_method());
        if(courseDetail.getSale_price() != null) {
            tvPrice.setText(getString(R.string.rmb) + courseDetail.getSale_price().toString());
        }
        if(courseDetail.is_favor()) {
            if (!upgrade){
                titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_pressed);
             }
            ivWant.setImageResource(R.drawable.faxian_xiangting_0);
        } else {
            if (!upgrade) {
                titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
            }
            ivWant.setImageResource(R.drawable.faxian_xiangting);
        }

        tvContent.setText(MangoUtils.delHTMLTag(courseDetail.getCourse_content()));
        tvTypeMethod.setText(courseDetail.getType_method()+"，"
                + courseDetail.getEach_time() +"/" + courseDetail.getService_time()+"  "
                +getString(R.string.rmb)+courseDetail.getSale_price().toString()+"元");
        tvTypeExplains.setText(MangoUtils.delHTMLTag(courseDetail.getType_explains()));
    }

    @OnClick(R.id.btn_place_order)
    void onPlaceOrder(View v){
        ActivityBuilder.startPlaceOrderActivity(this, courseDetail);
    }

    @Override
    protected void onDestroy() {
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(favPresenter != null){
            favPresenter.onDestroy();
        }
        if(wantCountPresenter != null){
            wantCountPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @OnClick(R.id.iv_want)
    void wantCountClick(View v){
        if(wantCountPresenter == null){
            wantCountPresenter = new WantCountPresenter(this);
        }
        wantCountPresenter.addWantCount();
    }

    @Override
    public long getEntityId() {
        return id;
    }

    @Override
    public int getEntityTypeId() {
        return Constants.EntityType.COURSE.getTypeId();
    }

    @Override
    public void onSuccess(boolean success) {
        if(success){
            courseDetail.setIs_favor(true);
            if (!upgrade) {
                titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_pressed);
            }
            ivWant.setImageResource(R.drawable.faxian_xiangting_0);
         } else {
            courseDetail.setIs_favor(false);
            if (!upgrade) {
                titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_nor);
            }
            ivWant.setImageResource(R.drawable.faxian_xiangting);
        }
    }

    @Override
    public void onTitleButtonClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ib_right:
                if(courseDetail == null){
                    return;
                }
                SharePopupWindow sharePopupWindow = new SharePopupWindow(this, String.format(Constants.COURSE_URL, courseDetail.getId()), courseDetail.getCourse_content(),
                        courseDetail.getCourse_title(), null, new MangoUMShareListener());
                sharePopupWindow.show();
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
            case R.id.tv_right:
                if(courseDetail != null) {
                    ActivityBuilder.startAddCourseActivity(this, courseDetail);
                }
                break;
        }
    }

    @Override
    public void onWantCountSuccess() {
        courseDetail.setIs_favor(true);
        if (!upgrade) {
            titleBar.setSecondRightBtnIcon(R.drawable.icon_shoucang_pressed);
        }
        ivWant.setImageResource(R.drawable.faxian_xiangting_0);
    }

    @Override
    public long wantEntityId() {
        return id;
    }

    @Override
    public int wantEntityType() {
        return Constants.EntityType.COURSE.getTypeId();
    }
}
