package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.presenter.WantCountPresenter;
import cn.com.mangopi.android.presenter.WorkProjectRankPresenter;
import cn.com.mangopi.android.presenter.WorksProjectPresenter;
import cn.com.mangopi.android.ui.adapter.ProjectDetailProgressAdapter;
import cn.com.mangopi.android.ui.adapter.RankActorAdapter;
import cn.com.mangopi.android.ui.adapter.RecommendCourseAdapter;
import cn.com.mangopi.android.ui.adapter.TeamInProjectDetailAdapter;
import cn.com.mangopi.android.ui.popupwindow.SharePopupWindow;
import cn.com.mangopi.android.ui.viewlistener.WantCountListener;
import cn.com.mangopi.android.ui.viewlistener.WorkProjectRankListener;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectDetailListener;
import cn.com.mangopi.android.ui.widget.HorizontalListView;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.ui.widget.MangoUMShareListener;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DialogUtil;
import cn.com.mangopi.android.util.MangoUtils;

public class WorksProjectDetailActivity extends BaseTitleBarActivity implements WorksProjectDetailListener, WantCountListener,
        AdapterView.OnItemClickListener, TitleBar.OnTitleBarClickListener,WorkProjectRankListener {

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
    @Bind(R.id.iv_want)
    ImageView ivWant;
    WantCountPresenter wantCountPresenter;
    @Bind(R.id.layout_courses)
    LinearLayout layoutCourses;
    @Bind(R.id.lv_courses)
    ListView lvCourses;
    @Bind(R.id.line_courses)
    View lineCourses;
    @Bind(R.id.line_courses_top)
    View lineCoursesTop;
    @Bind(R.id.line_courses_bottom)
    View lineCoursesBottom;
    @Bind(R.id.btn_project_join)
    Button btnProjectJoin;

    ProjectDetailProgressAdapter progressAdapter;
    List<ProjectDetailProgressAdapter.ProgressBean> progressDatas = new ArrayList<>();
    ProjectDetailBean projectDetail;
    List<Constants.UserIndentity> indentityList;

    @Bind(R.id.tv_project_team)
    TextView tvProjectTeam;
    @Bind(R.id.lv_project_team)
    ListView lvProjectTeam;
    @Bind(R.id.line_project_team)
    View lineProjectTeam;
    List<ProjectDetailBean.ProjectActorBean> projectActors = new ArrayList<>();
    TeamInProjectDetailAdapter teamAdatper;

    int whereFrom;
    @Bind(R.id.layout_empty)
    View emptyTeamView;
    @Bind(R.id.tv_content_empty_tip)
    TextView tvEmptyTeamTip;

    @Bind(R.id.line_rank)
    View lineRank;
    @Bind(R.id.layout_rank)
    RelativeLayout layoutRank;
    @Bind(R.id.lv_rank)
    ListView lvRank;
    @Bind(R.id.tv_rank_icon)
    TextView tvRankIcon;
    RankActorAdapter rankActorAdapter;
    List<ProjectActorBean> rankList = new ArrayList<>();

    WorkProjectRankPresenter rankPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        if(id == 0){
            finish();
        }
        whereFrom = getIntent().getIntExtra(Constants.BUNDLE_WHERE_FROM, 0);
        setContentView(R.layout.activity_works_project_detail);

        indentityList = MangoUtils.getIndentityList();
        titleBar.setTitle(R.string.works_project_detail);
        if(indentityList.contains(Constants.UserIndentity.STUDENT)) {
            titleBar.setRightText("我的工作包");
            titleBar.setOnTitleBarClickListener(this);
        }
        tvEmptyTeamTip.setText("暂无报名团队");
        projectPresenter = new WorksProjectPresenter(this);
        projectPresenter.getProject();

        rankPresenter = new WorkProjectRankPresenter(this);
        RichText.initCacheDir(this);
    }

    private void bindData(ProjectDetailBean projectDetail){
        this.projectDetail = projectDetail;
        Application.application.getImageLoader().displayImage(MangoUtils.getCalculateScreenWidthSizeUrl(projectDetail.getLogo_rsurl()), ivLogo, Application.application.getDefaultOptions());
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
            RichText.from(projectDetail.getIntroduction()).into(tvIntroContent);
//            tvIntroContent.setText(MangoUtils.delHTMLTag(projectDetail.getIntroduction()));
        }

        if(TextUtils.isEmpty(projectDetail.getPrizes_content())){
            layoutPrizesHead.setVisibility(View.GONE);
            linePrezes.setVisibility(View.GONE);
        } else {
            layoutPrizesHead.setVisibility(View.VISIBLE);
            linePrezes.setVisibility(View.VISIBLE);
            RichText.from(projectDetail.getPrizes_content()).into(tvPrizesContent);
//            tvPrizesContent.setText(MangoUtils.delHTMLTag(projectDetail.getPrizes_content()));
        }

        if(TextUtils.isEmpty(projectDetail.getEntry_instructions())){
            layoutInstruHead.setVisibility(View.GONE);
            lineInstru.setVisibility(View.GONE);
        } else {
            layoutInstruHead.setVisibility(View.VISIBLE);
            lineInstru.setVisibility(View.VISIBLE);
            RichText.from(projectDetail.getEntry_instructions()).into(tvInstruContent);
//            tvInstruContent.setText(MangoUtils.delHTMLTag(projectDetail.getEntry_instructions()));
        }

        initProgressDatas(projectDetail);
        if(progressAdapter == null){
            lvProgress.setAdapter(progressAdapter = new ProjectDetailProgressAdapter(this, R.layout.listview_item_project_detail_progress, progressDatas));
        } else{
            progressAdapter.notifyDataSetChanged();
        }
        if(projectDetail.is_favor()){
            ivWant.setImageResource(R.drawable.faxian_xiangting_0);
        } else {
            ivWant.setImageResource(R.drawable.faxian_xiangting);
        }

        if(whereFrom == Constants.FROM_MY_COMPANY_PROJECT){//我的企业工作包
            projectActors.clear();
            if(projectDetail.getActors() != null) {
                projectActors.addAll(projectDetail.getActors());
            }
            if(teamAdatper == null) {
                lvProjectTeam.setAdapter(teamAdatper = new TeamInProjectDetailAdapter(this, R.layout.listview_item_project_team_in_detail, projectActors));
            } else {
                teamAdatper.notifyDataSetChanged();
            }
            if(lvProjectTeam.getEmptyView() == null) {
                lvProjectTeam.setEmptyView(emptyTeamView);
            }
            tvProjectTeam.setVisibility(View.VISIBLE);
            lvProjectTeam.setVisibility(View.VISIBLE);
            lineProjectTeam.setVisibility(View.VISIBLE);
        } else {
            tvProjectTeam.setVisibility(View.GONE);
            lvProjectTeam.setVisibility(View.GONE);
            lineProjectTeam.setVisibility(View.GONE);
        }

        List<CourseBean> courses = projectDetail.getCourses();
        if(courses == null || courses.size() == 0){
            layoutCourses.setVisibility(View.GONE);
            lvCourses.setVisibility(View.GONE);
            lineCourses.setVisibility(View.GONE);
            lineCoursesBottom.setVisibility(View.GONE);
            lineCoursesTop.setVisibility(View.GONE);
        } else {
            layoutCourses.setVisibility(View.VISIBLE);
            lvCourses.setVisibility(View.VISIBLE);
            lineCourses.setVisibility(View.VISIBLE);
            lineCoursesBottom.setVisibility(View.VISIBLE);
            lineCoursesTop.setVisibility(View.VISIBLE);
            lvCourses.setAdapter(new RecommendCourseAdapter(this, R.layout.listview_item_recommend_teacher_class, courses));
            lvCourses.setOnItemClickListener(this);
        }

        Date applyAbort = projectDetail.getApply_abort_time();
        if(applyAbort != null && applyAbort.after(new Date()) /*&& !indentityList.contains(Constants.UserIndentity.COMPANY)*/){
            btnProjectJoin.setEnabled(true);
            btnProjectJoin.setText("立即报名");
        } else {
            btnProjectJoin.setEnabled(false);
            btnProjectJoin.setText("报名截止");
        }
        btnProjectJoin.setVisibility(View.VISIBLE);

        Date appraiseAbortTime = projectDetail.getAppraise_abort_time();
        if(appraiseAbortTime != null && appraiseAbortTime.before(new Date())){
            rankPresenter.getRankList(projectDetail.getActors());
        }
    }

    @OnClick(R.id.btn_project_join)
    void projectJoin(View v){
        if(projectDetail == null){
            return;
        }
        if(indentityList.contains(Constants.UserIndentity.STUDENT)) {
            ActivityBuilder.startProjectJoinActivity(this, projectDetail.getId(), projectDetail.getProject_name());
        } else {
            DialogUtil.createChoosseDialog(this, "提示", "只有升级成为学生才能参与该工作包", "去认证", "暂不认证", new DialogUtil.OnChooseDialogListener() {
                @Override
                public void onChoose() {
                    ActivityBuilder.startUpgradeRoleActivity(WorksProjectDetailActivity.this);
                }
            });
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

    @OnClick(R.id.tv_rank_icon)
    void rankIconClick(View v){
        if(lvRank.getVisibility() == View.GONE){
            tvRankIcon.setText("收起");
            lvRank.setVisibility(View.VISIBLE);
        } else {
            tvRankIcon.setText("展开");
            lvRank.setVisibility(View.GONE);
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
        if(teamAdatper != null){
            teamAdatper.onDestory();
        }
        if(projectPresenter != null){
            projectPresenter.onDestroy();
        }
        if(wantCountPresenter != null){
            wantCountPresenter.onDestroy();
        }
        if(rankPresenter != null){
            rankPresenter.onDestroy();
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

    @OnClick(R.id.iv_share)
    void shareClick(View v){
        if(projectDetail == null){
            return;
        }
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this, String.format(Constants.WORK_PROJECT_URL, projectDetail.getId()), projectDetail.getIntroduction(),
                projectDetail.getProject_name(), null, new MangoUMShareListener());
        sharePopupWindow.show();
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
        return Constants.EntityType.WORKS.getTypeId();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseBean item = (CourseBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startCourseDetailActivity(this, item.getId());
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                ActivityBuilder.startMemberWorksActivity(this, Constants.UserIndentity.STUDENT);
                break;
        }
    }

    @Override
    public void onRankList(List<ProjectActorBean> rankList) {
        bindRankData(rankList);
    }

    private void bindRankData(List<ProjectActorBean> rankList){
        if(rankList != null && rankList.size() > 0){
            lineRank.setVisibility(View.VISIBLE);
            layoutRank.setVisibility(View.VISIBLE);
            lvRank.setVisibility(View.VISIBLE);
            this.rankList.clear();
            this.rankList.addAll(rankList);
            if(rankActorAdapter == null){
                lvRank.setAdapter(rankActorAdapter = new RankActorAdapter(this, R.layout.listview_item_rank_actor_in_detail, rankList));
            } else {
                rankActorAdapter.notifyDataSetChanged();
            }
        } else {
            lineRank.setVisibility(View.GONE);
            layoutRank.setVisibility(View.GONE);
            lvRank.setVisibility(View.GONE);
        }
    }
}
