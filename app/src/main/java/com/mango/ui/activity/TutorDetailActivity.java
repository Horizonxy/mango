package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.TutorBean;
import com.mango.model.data.TutorModel;
import com.mango.presenter.TutorDetailPresenter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.popupwindow.TutorCourseListPopupWindow;
import com.mango.ui.viewlistener.TutorDetailListener;
import com.mango.util.AppUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;

public class TutorDetailActivity extends BaseTitleBarActivity implements TutorDetailListener {

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
    }

    private void fillTutor(TutorBean tutor){
        Application.application.getImageLoader().displayImage(tutor.getAvatar_rsurl(), ivLogo, Application.application.getDefaultOptions());
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
        } else {
            ivWant.setImageResource(R.drawable.faxian_xiangting);
        }

        courseList.clear();
        courseList.addAll(tutor.getCourses());
        adapter.notifyDataSetChanged();
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
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
