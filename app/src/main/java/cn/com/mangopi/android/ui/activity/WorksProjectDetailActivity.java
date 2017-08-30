package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.presenter.WorksProjectPresenter;
import cn.com.mangopi.android.ui.adapter.ProjectDetailProgressAdapter;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectDetailListener;
import cn.com.mangopi.android.ui.widget.HorizontalListView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;

public class WorksProjectDetailActivity extends BaseTitleBarActivity implements WorksProjectDetailListener {

    long id;
    WorksProjectPresenter projectPresenter;
    @Bind(R.id.tv_project_name)
    TextView tvProjectName;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.lv_progress)
    HorizontalListView lvProgress;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_join_want)
    TextView tvJoinWant;
    @Bind(R.id.layout_intro)
    LinearLayout layoutIntro;
    @Bind(R.id.line_intro)
    View lineIntro;
    @Bind(R.id.tv_intro_content)
    TextView tvIntroContent;
    @Bind(R.id.tv_prizes_icon)
    TextView tvPrizesIcon;
    @Bind(R.id.tv_prizes_content)
    TextView tvPrizesContent;
    @Bind(R.id.layout_prizes_head)
    RelativeLayout layoutPrizesHead;
    @Bind(R.id.line_prizes)
    View linePrezes;
    @Bind(R.id.tv_instru_icon)
    TextView tvInstruIcon;
    @Bind(R.id.tv_instru_content)
    TextView tvInstruContent;
    @Bind(R.id.layout_instru_head)
    RelativeLayout layoutInstruHead;
    @Bind(R.id.line_instru)
    View lineInstru;

    ProjectDetailProgressAdapter progressAdapter;
    List<ProjectDetailProgressAdapter.ProgressBean> progressDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        if(id == 0){
            finish();
        }
        setContentView(R.layout.activity_works_project_detail);

        titleBar.setTitle(R.string.works_project_detail);
        projectPresenter = new WorksProjectPresenter(this);
        projectPresenter.getProject();
    }

    private void bindData(ProjectDetailBean projectDetail){
        Application.application.getImageLoader().displayImage(projectDetail.getLogo_rsurl(), ivLogo, Application.application.getDefaultOptions());
        tvProjectName.setText(projectDetail.getProject_name());
        tvProgress.setText("进度：" + String.valueOf(projectDetail.getProgress()) + "%");
        tvLocation.setText(String.format(getString(R.string.works_project_location), projectDetail.getLocation()));
        tvJoinWant.setText(String.format(getString(R.string.works_project_join_want), projectDetail.getWant_count(), projectDetail.getApplied_count()));
        if(TextUtils.isEmpty(projectDetail.getIntroduction())){
            layoutIntro.setVisibility(View.GONE);
            lineIntro.setVisibility(View.GONE);
        } else {
            layoutIntro.setVisibility(View.VISIBLE);
            lineIntro.setVisibility(View.VISIBLE);
            tvIntroContent.setText(projectDetail.getIntroduction());
        }

        if(TextUtils.isEmpty(projectDetail.getPrizes_content())){
            layoutPrizesHead.setVisibility(View.GONE);
            linePrezes.setVisibility(View.GONE);
        } else {
            layoutPrizesHead.setVisibility(View.VISIBLE);
            linePrezes.setVisibility(View.VISIBLE);
            tvPrizesContent.setText(projectDetail.getPrizes_content());
        }

        if(TextUtils.isEmpty(projectDetail.getEntry_instructions())){
            layoutInstruHead.setVisibility(View.GONE);
            lineInstru.setVisibility(View.GONE);
        } else {
            layoutInstruHead.setVisibility(View.VISIBLE);
            lineInstru.setVisibility(View.VISIBLE);
            tvInstruContent.setText(projectDetail.getEntry_instructions());
        }

        initProgressDatas(projectDetail);
        if(progressAdapter == null){
            lvProgress.setAdapter(progressAdapter = new ProjectDetailProgressAdapter(this, R.layout.listview_item_project_detail_progress, progressDatas));
        } else{
            progressAdapter.notifyDataSetChanged();
        }
    }

    private void initProgressDatas(ProjectDetailBean projectDetail){
        progressDatas.clear();
        Date now = new Date();

        ProjectDetailProgressAdapter.ProgressBean progressBean1 = new ProjectDetailProgressAdapter.ProgressBean();
        progressBean1.setTop(DateUtils.dateToString(projectDetail.getPublish_time(), DateUtils.DATE_PATTERN));
        progressBean1.setBottom("开始报名");
        progressBean1.setReach(now.after(projectDetail.getPublish_time()));
        progressDatas.add(progressBean1);

        ProjectDetailProgressAdapter.ProgressBean progressBean2 = new ProjectDetailProgressAdapter.ProgressBean();
        progressBean2.setBottom(DateUtils.dateToString(projectDetail.getApply_abort_time(), DateUtils.DATE_PATTERN));
        progressBean2.setTop("报名截止");
        progressBean2.setReach(now.after(projectDetail.getApply_abort_time()));
        progressDatas.add(progressBean2);

        ProjectDetailProgressAdapter.ProgressBean progressBean3 = new ProjectDetailProgressAdapter.ProgressBean();
        progressBean3.setTop(DateUtils.dateToString(projectDetail.getWorks_abort_time(), DateUtils.DATE_PATTERN));
        progressBean3.setBottom("作品截止");
        progressBean3.setReach(now.after(projectDetail.getWorks_abort_time()));
        progressDatas.add(progressBean3);

        ProjectDetailProgressAdapter.ProgressBean progressBean4 = new ProjectDetailProgressAdapter.ProgressBean();
        progressBean4.setBottom(DateUtils.dateToString(projectDetail.getAppraise_abort_time(), DateUtils.DATE_PATTERN));
        progressBean4.setTop("评审结束");
        progressBean4.setReach(now.after(projectDetail.getAppraise_abort_time()));
        progressDatas.add(progressBean4);
    }

    @OnClick(R.id.tv_prizes_icon)
    void prizesIconClick(View v){
        if(tvPrizesContent.getVisibility() == View.GONE){
            tvPrizesIcon.setText("收起");
            tvPrizesContent.setVisibility(View.VISIBLE);
        } else {
            tvPrizesIcon.setText("展开");
            tvPrizesContent.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_instru_icon)
    void instruIconClick(View v){
        if(tvInstruContent.getVisibility() == View.GONE){
            tvInstruIcon.setText("收起");
            tvInstruContent.setVisibility(View.VISIBLE);
        } else {
            tvInstruIcon.setText("展开");
            tvInstruContent.setVisibility(View.GONE);
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
    public void onSuccess(ProjectDetailBean projectDetail) {
        bindData(projectDetail);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    protected void onDestroy() {
        if(projectPresenter != null){
            projectPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
