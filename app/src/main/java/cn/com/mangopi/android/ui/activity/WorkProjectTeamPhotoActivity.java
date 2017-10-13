package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.presenter.ProjectActorPresenter;
import cn.com.mangopi.android.ui.adapter.MatchParentPhotoAdapter;
import cn.com.mangopi.android.ui.viewlistener.ProjectWorkListener;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class WorkProjectTeamPhotoActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener,
        ProjectWorkListener {

    long actorId;
    ProjectActorPresenter projectActorPresenter;
    ProjectActorBean projectActorBean;
    @Bind(R.id.tv_other_doc)
    TextView tvOtherDoc;
    @Bind(R.id.line_tutor_comment)
    View lineTutorComment;
    @Bind(R.id.tv_tutor_comment_tip)
    TextView tvTutorCommentTip;
    @Bind(R.id.tv_tutor_comment)
    TextView tvTutorComment;
    @Bind(R.id.line_company_comment)
    View lineCompanyComment;
    @Bind(R.id.tv_company_comment_tip)
    TextView  tvCompanyCommentTip;
    @Bind(R.id.tv_company_comment)
    TextView tvCompanyComment;
    @Bind(R.id.lv_photo)
    ListView lvPhotos;
    List<String> photoDatas = new ArrayList<String>();
    MatchParentPhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrok_project_team_photo);
        actorId = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        projectActorPresenter = new ProjectActorPresenter(this);
        projectActorPresenter.getProjectActor();
    }

    private void initView() {

        lvPhotos.setAdapter(photoAdapter = new MatchParentPhotoAdapter(this, R.layout.listview_item_match_photo, photoDatas));
    }

    @Override
    protected void onDestroy() {
        if(projectActorPresenter != null){
            projectActorPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_other_doc)
    void otherDocClicked(View v){

    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                if(projectActorBean != null){
                    ActivityBuilder.startWorkProjectCommentActivity(this, projectActorBean.getId());
                }
                break;
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
    public long getActorId() {
        return actorId;
    }

    @Override
    public void onProjectActorSuccess(ProjectActorBean projectActorBean) {
        this.projectActorBean = projectActorBean;
        bindData(projectActorBean);
    }

    private void bindData(ProjectActorBean projectActorBean){
        titleBar.setTitle(projectActorBean.getActor_name());
        tvOtherDoc.setVisibility(View.VISIBLE);

        photoDatas.clear();
        if(projectActorBean.getWorks_photo_rsurls() != null){
            photoDatas.addAll(projectActorBean.getWorks_photo_rsurls());
        }
        photoAdapter.notifyDataSetChanged();

        String tutorCommment = projectActorBean.getTutor_comments();
        if(TextUtils.isEmpty(tutorCommment)){
            MangoUtils.setViewsVisibility(View.GONE, lineTutorComment, tvTutorComment, tvTutorCommentTip);
        } else {
            MangoUtils.setViewsVisibility(View.VISIBLE, lineTutorComment, tvTutorComment, tvTutorCommentTip);
            tvCompanyComment.setText(tutorCommment);
        }

        String companyComment = projectActorBean.getCompany_comments();
        if(TextUtils.isEmpty(companyComment)){
            MangoUtils.setViewsVisibility(View.GONE, lineCompanyComment, tvCompanyCommentTip, tvCompanyComment);

            titleBar.setRightText(R.string.write_comment);
            titleBar.setOnTitleBarClickListener(this);
        } else {
            MangoUtils.setViewsVisibility(View.VISIBLE, lineCompanyComment, tvCompanyCommentTip, tvCompanyComment);
            tvCompanyComment.setText(companyComment);

            titleBar.setRightTextVisibilty(View.GONE);
        }
    }
}
