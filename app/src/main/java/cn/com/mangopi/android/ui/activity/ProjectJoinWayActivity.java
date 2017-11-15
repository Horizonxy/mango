package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.presenter.ProjectJoinPresenter;
import cn.com.mangopi.android.ui.viewlistener.ProjectJoinListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;

public class ProjectJoinWayActivity extends BaseTitleBarActivity implements ProjectJoinListener {

    @Bind(R.id.tv_self_intro)
    TextView tvSelfIntro;
    @Bind(R.id.et_chiper)
    EditText etChiper;
    long projectId;
    ProjectTeamBean projectTeam;
    ProjectJoinPresenter joinPresenter;

    int type;//0 集结号    1 申请加入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_join_way);
        Bus.getDefault().register(this);

        projectId = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        projectTeam = (ProjectTeamBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);

        initView();
        joinPresenter = new ProjectJoinPresenter(this);
    }

    private void initView() {
        titleBar.setTitle(R.string.join_exit_team);

    }

    @Override
    protected void onDestroy() {
        if(joinPresenter != null){
            joinPresenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }

    @BusReceiver
    public void onJoinTeamIntroEvent(BusEvent.JoinTeamIntroEvent event){
        if(event != null){
            tvSelfIntro.setText(event.getIntro());
        }
    }

    @OnClick(R.id.tv_self_intro)
    void inputSelfIntroClicked(View v) {
        ActivityBuilder.startJoinTeamIntroActivity(this);
    }

    @OnClick(R.id.btn_join_with_chiper)
    void chiperJoinClicked(View v){
        if(TextUtils.isEmpty(etChiper.getText())){
            AppUtils.showToast(this, "请输入团队集结暗号");
            return;
        }
        type = 0;
        joinPresenter.projectJoin();
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
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 2);
        map.put("id", projectId);
        if(type == 0) {
            map.put("team_id", projectTeam.getId());
            map.put("cipher", etChiper.getText().toString());
        }
        return map;
    }

    @Override
    public void onJoinSuccess(ProjectJoinBean projectJoin) {
        ActivityBuilder.startMemberWorksActivity(this, Constants.UserIndentity.STUDENT);
    }

    @Override
    public long getId() {
        return projectId;
    }

    @Override
    public void onTeamList(List<ProjectTeamBean> projectTeamList) {
    }
}
