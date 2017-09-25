package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.presenter.ProjectActorPresenter;
import cn.com.mangopi.android.ui.viewlistener.ProjectWorkListener;
import cn.com.mangopi.android.util.AppUtils;

public class ProjectWorkDetailActivity extends BaseTitleBarActivity implements ProjectWorkListener {

    ProjectActorPresenter projectActorPresenter;
    long projectId;
    ProjectActorBean projectActorBean;
    @Bind(R.id.tv_project_name)
    TextView tvProjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_work_detail);

        projectId = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        projectActorPresenter = new ProjectActorPresenter(this);
        projectActorPresenter.getProjectActor();
    }

    private void initView() {
        titleBar.setTitle(R.string.works);
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
    public long getProjectId() {
        return projectId;
    }

    @Override
    public void onProjectActorSuccess(ProjectActorBean projectActorBean) {
        this.projectActorBean = projectActorBean;
        bindData(projectActorBean);
    }

    private void bindData(ProjectActorBean projectActorBean) {
        tvProjectName.setText(projectActorBean.getProject_name());
    }
}
