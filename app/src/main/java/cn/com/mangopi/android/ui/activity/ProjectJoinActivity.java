package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
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
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.util.AppUtils;

public class ProjectJoinActivity extends BaseTitleBarActivity implements RadioGroup.OnCheckedChangeListener, ProjectJoinListener {

    long projectId;
    String projectName;
    @Bind(R.id.tv_project_name)
    TextView tvProjectNamel;
    @Bind(R.id.rg_join_type)
    RadioGroup rgJoinType;
    @Bind(R.id.btn_join)
    Button btnJoin;
    @Bind(R.id.rb_member)
    RadioButton rbMember;
    @Bind(R.id.rb_new_team)
    RadioButton rbNewTeam;
    @Bind(R.id.rb_team)
    RadioButton rbTeam;
    @Bind(R.id.layout_new_team)
    LinearLayout layoutNewTeam;
    @Bind(R.id.lv_team)
    ListView lvTeam;
    int type = 1;
    @Bind(R.id.et_team_name)
    EditText etTeamName;
    @Bind(R.id.et_team_cipher)
    EditText etTeamCipher;
    @Bind(R.id.et_team_bulletin)
    EditText etTeamBulletin;
    @Bind(R.id.et_member_role)
    EditText etMemberRole;
    ProjectJoinPresenter joinPresenter;
    List<ProjectTeamBean> projectTeamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_join);

        projectId = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        projectName = getIntent().getStringExtra(Constants.BUNDLE_TITLE);
        initView();
        joinPresenter = new ProjectJoinPresenter(this);
    }

    private void initView() {
        titleBar.setTitle("报名");
        tvProjectNamel.setText(projectName);
        rgJoinType.setOnCheckedChangeListener(this);
        rbMember.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_member:
                layoutNewTeam.setVisibility(View.GONE);
                lvTeam.setVisibility(View.GONE);
                type = 1;
                break;
            case R.id.rb_new_team:
                layoutNewTeam.setVisibility(View.VISIBLE);
                lvTeam.setVisibility(View.GONE);
                type = 3;
                break;
            case R.id.rb_team:
                layoutNewTeam.setVisibility(View.GONE);
                lvTeam.setVisibility(View.VISIBLE);
                type = 2;
                break;
        }
    }

    @OnClick(R.id.btn_join)
    void joinClick(View v){
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
        map.put("type", type);
        if(type == 3){
            map.put("team_name", etTeamName.getText().toString());
            map.put("cipher", etTeamCipher.getText().toString());
            map.put("bulletin", etTeamBulletin.getText().toString());
            map.put("role", etMemberRole.getText().toString());
        } else if(type == 2){
            map.put("team_id", "");
        }
        return map;
    }

    @Override
    public void onJoinSuccess(ProjectJoinBean projectJoin) {

    }

    @Override
    public long getId() {
        return projectId;
    }

    @Override
    public void onTeamList(List<ProjectTeamBean> projectTeamList) {
        this.projectTeamList.clear();
        this.projectTeamList.addAll(projectTeamList);
    }

    @Override
    protected void onDestroy() {
        if(joinPresenter != null){
            joinPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
