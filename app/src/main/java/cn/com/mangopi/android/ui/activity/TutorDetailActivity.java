package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.TutorBean;
import cn.com.mangopi.android.model.data.TutorModel;
import cn.com.mangopi.android.presenter.TutorDetailPresenter;
import cn.com.mangopi.android.presenter.WantCountPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.popupwindow.TutorCourseListPopupWindow;
import cn.com.mangopi.android.ui.viewlistener.TutorDetailListener;
import cn.com.mangopi.android.ui.viewlistener.WantCountListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class TutorDetailActivity extends BaseTitleBarActivity implements TutorDetailListener, AdapterView.OnItemClickListener, WantCountListener{

    ImageView ivLogo;
    TextView tvCity;
    TextView tvName;
    TextView tvJob;
    TextView tvWantCount;
    TextView tvWantedCount;
    @Bind(R.id.lv_courses)
    ListView lvCourses;
    @Bind(R.id.iv_want)
    ImageView ivWant;
    long id;
    TutorDetailPresenter presenter;
    List<CourseBean> courseList = new ArrayList<>();
    QuickAdapter<CourseBean> adapter;
    TextView tvIntro;
    WantCountPresenter wantCountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        presenter = new TutorDetailPresenter(new TutorModel(), this);
        presenter.getTutor();
    }

    private void initView() {
        titleBar.setTitle(R.string.teacher_detail);

        View header = getLayoutInflater().inflate(R.layout.layout_header_teacher_detail, null, false);
        ivLogo = (ImageView) header.findViewById(R.id.iv_logo);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvJob = (TextView) header.findViewById(R.id.tv_jobs);
        tvCity = (TextView) header.findViewById(R.id.tv_city);
        tvWantCount = (TextView) header.findViewById(R.id.tv_want_count);
        tvWantedCount = (TextView) header.findViewById(R.id.tv_wanted_count);
        tvIntro = (TextView) header.findViewById(R.id.tv_tutor_intro);

        lvCourses.addHeaderView(header);
        lvCourses.setDivider(null);
        lvCourses.setAdapter(adapter = new QuickAdapter<CourseBean>(this,R.layout.listview_item_course_in_tutor_detail, courseList) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseBean item) {
                helper.setText(R.id.tv_title, item.getCourse_title())
                        .setText(R.id.tv_type_method, item.getType_method())
                        .setText(R.id.tv_each_time, item.getEach_time() + "/" + item.getService_time());
                if(item.getSale_price() != null) {
                    helper.setText(R.id.tv_price, getString(R.string.rmb) + item.getSale_price().toString());
                }
            }
        });
        lvCourses.setOnItemClickListener(this);
    }

    private void fillTutor(TutorBean tutor){
        Application.application.getImageLoader().displayImage(MangoUtils.getCalculateScreenWidthSizeUrl(tutor.getAvatar_rsurl()), ivLogo, Application.application.getDefaultOptions());
        tvName.setText(tutor.getName());
        tvJob.setText(tutor.getTutor_jobs());
        tvCity.setText(tutor.getCity());
        if(tutor.getWant_count() > 0){
            tvWantCount.setVisibility(View.VISIBLE);
            tvWantCount.setText(String.format(getString(R.string.want_count), tutor.getWant_count()));
        } else {
            tvWantCount.setVisibility(View.GONE);
        }

        if(tutor.getWanted_count() > 0){
            tvWantedCount.setVisibility(View.VISIBLE);
            tvWantedCount.setText(String.format(getString(R.string.wanted_count), tutor.getWanted_count()));
        } else {
            tvWantedCount.setVisibility(View.GONE);
        }
        if(tutor.is_favor()){
            ivWant.setImageResource(R.drawable.faxian_xiangting_0);
            ivWant.setClickable(false);
        } else {
            ivWant.setImageResource(R.drawable.faxian_xiangting);
        }

        tvIntro.setText(MangoUtils.delHTMLTag(tutor.getIntro()));

        courseList.clear();
        if(tutor.getCourses() != null) {
            courseList.addAll(tutor.getCourses());
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_want)
    void wantCountClick(View v){
        if(wantCountPresenter == null){
            wantCountPresenter = new WantCountPresenter(this);
        }
        wantCountPresenter.addWantCount();
    }

    @OnClick(R.id.btn_listen)
    void listenClick(View v){
        TutorCourseListPopupWindow popupWindow = new TutorCourseListPopupWindow(this, courseList);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void onSuccess(TutorBean tutor) {
        if(tutor != null){
            fillTutor(tutor);
        }
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
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(wantCountPresenter != null){
            wantCountPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseBean courseBean = (CourseBean) parent.getAdapter().getItem(position);
        if(courseBean != null) {
            ActivityBuilder.startCourseDetailActivity(this, courseBean.getId());
        }
    }

    @Override
    public void onWantCountSuccess() {
        ivWant.setImageResource(R.drawable.faxian_xiangting_0);
    }

    @Override
    public long wantEntityId() {
        return id;
    }

    @Override
    public int wantEntityType() {
        return Constants.EntityType.MEMBER.getTypeId();
    }
}
