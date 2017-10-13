package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.presenter.ProjectActorPresenter;
import cn.com.mangopi.android.ui.adapter.ProjectActorPhotoAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.ProjectWorkListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DisplayUtils;

public class ProjectWorkDetailActivity extends BaseTitleBarActivity implements ProjectWorkListener {

    ProjectActorPresenter projectActorPresenter;
    long id;
    ProjectActorBean projectActorBean;
    @Bind(R.id.tv_project_name)
    TextView tvProjectName;
    @Bind(R.id.line_photo)
    View lineWorksPhoto;
    @Bind(R.id.layout_works_photo)
    LinearLayout layoutWorksPhoto;
    @Bind(R.id.gv_works_photo)
    GridView gvWorksPhoto;
    @Bind(R.id.line_doc)
    View lineDoc;
    @Bind(R.id.layout_doc)
    LinearLayout layoutDoc;
    @Bind(R.id.gv_works_doc)
    GridView gvDoc;
    @Bind(R.id.line_team)
    View lineTeam;
    @Bind(R.id.layout_team)
    LinearLayout layoutTeam;
    @Bind(R.id.gv_team_photo)
    GridView gvTeam;
    List<String> worksPhoto = new ArrayList<String>();
    List<String> docs = new ArrayList<String>();
    List<String> teamPhoto = new ArrayList<String>();
    QuickAdapter<String> worksPhotoAdapter;
    QuickAdapter<String> docsAdapter;
    QuickAdapter<String> teamsPhotoAdatper;

    @Bind(R.id.layout_photo_empty)
    View emptyPhotoView;
    @Bind(R.id.layout_doc_empty)
    View emptyDocView;
    @Bind(R.id.layout_team_empty)
    View emptyTeamView;
    TextView tvEmptyPhotoTip, tvEmptyDocTip, tvEmptyTeamTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_work_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        projectActorPresenter = new ProjectActorPresenter(this);
        projectActorPresenter.getProjectActor();
    }

    private void initView() {
        titleBar.setTitle(R.string.works);

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15 * 2 + 10 * 3)) / 4;
        gvWorksPhoto.setAdapter(worksPhotoAdapter = new ProjectActorPhotoAdapter(this, R.layout.gridview_item_image, worksPhoto, width));
        gvDoc.setAdapter(docsAdapter = new ProjectActorPhotoAdapter(this, R.layout.gridview_item_image, docs, width));
        gvTeam.setAdapter(teamsPhotoAdatper = new ProjectActorPhotoAdapter(this, R.layout.gridview_item_image, teamPhoto, width));

        tvEmptyPhotoTip = (TextView) emptyPhotoView.findViewById(R.id.tv_content_empty_tip);
        tvEmptyDocTip = (TextView) emptyDocView.findViewById(R.id.tv_content_empty_tip);
        tvEmptyTeamTip = (TextView) emptyTeamView.findViewById(R.id.tv_content_empty_tip);
        tvEmptyPhotoTip.setText("暂无作品照片");
        tvEmptyDocTip.setText("暂无方案文档");
        tvEmptyTeamTip.setText("暂无团队照片");
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
        return id;
    }

    @Override
    public void onProjectActorSuccess(ProjectActorBean projectActorBean) {
        this.projectActorBean = projectActorBean;
        bindData(projectActorBean);
    }

    private void bindData(ProjectActorBean projectActorBean) {
        tvProjectName.setText(projectActorBean.getProject_name());

        worksPhoto.clear();
        if(projectActorBean.getWorks_photo_rsurls() != null && projectActorBean.getWorks_photo_rsurls().size() > 0) {
            worksPhoto.addAll(projectActorBean.getWorks_photo_rsurls());
        }
        if(gvWorksPhoto.getEmptyView() == null){
            gvWorksPhoto.setEmptyView(emptyPhotoView);
        }
        worksPhotoAdapter.notifyDataSetChanged();

        docs.clear();
        if(projectActorBean.getScheme_doc_rsurls() != null && projectActorBean.getScheme_doc_rsurls().size() > 0) {
            docs.addAll(projectActorBean.getScheme_doc_rsurls());
        }
        if(gvDoc.getEmptyView() == null){
            gvDoc.setEmptyView(emptyDocView);
        }
        docsAdapter.notifyDataSetChanged();

        teamPhoto.clear();
        if(projectActorBean.getTeam_photo_rsurls() != null && projectActorBean.getTeam_photo_rsurls().size() > 0) {
            teamPhoto.addAll(projectActorBean.getTeam_photo_rsurls());
        }
        if(gvTeam.getEmptyView() == null){
            gvTeam.setEmptyView(emptyTeamView);
        }
        teamsPhotoAdatper.notifyDataSetChanged();

//        if(worksPhoto.size() == 0){
//            lineWorksPhoto.setVisibility(View.GONE);
//            layoutWorksPhoto.setVisibility(View.GONE);
//        } else {
//            lineWorksPhoto.setVisibility(View.VISIBLE);
//            layoutWorksPhoto.setVisibility(View.VISIBLE);
//        }
//        if(docs.size() == 0){
//            lineDoc.setVisibility(View.GONE);
//            layoutDoc.setVisibility(View.GONE);
//        } else {
//            lineDoc.setVisibility(View.VISIBLE);
//            layoutDoc.setVisibility(View.VISIBLE);
//        }
//        if(teamPhoto.size() == 0){
//            lineTeam.setVisibility(View.GONE);
//            layoutTeam.setVisibility(View.GONE);
//        } else {
//            lineTeam.setVisibility(View.VISIBLE);
//            layoutTeam.setVisibility(View.VISIBLE);
//        }
    }
}
