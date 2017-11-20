package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.presenter.ProjectJoinPresenter;
import cn.com.mangopi.android.ui.adapter.ProjectJoinTeamAdapter;
import cn.com.mangopi.android.ui.viewlistener.ProjectJoinListener;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DialogUtil;

public class ProjectJoinActivity extends BaseTitleBarActivity implements RadioGroup.OnCheckedChangeListener, ProjectJoinListener, ProjectJoinTeamAdapter.ProjectJoinWithTeamListener {

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
    ProjectJoinTeamAdapter teamAdapter;
    long joinTeamId;
    String joinTeamCipher;

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
        lvTeam.setAdapter(teamAdapter = new ProjectJoinTeamAdapter(this, R.layout.listview_item_project_join_team, projectTeamList, projectId));
        teamAdapter.setJoinWithTeamListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_member:
                layoutNewTeam.setVisibility(View.GONE);
                lvTeam.setVisibility(View.GONE);
                type = 1;
                btnJoin.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_new_team:
                layoutNewTeam.setVisibility(View.VISIBLE);
                lvTeam.setVisibility(View.GONE);
                type = 3;
                btnJoin.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_team:
                layoutNewTeam.setVisibility(View.GONE);
                lvTeam.setVisibility(View.VISIBLE);
                type = 2;
                btnJoin.setVisibility(View.GONE);
                if(projectTeamList.size() == 0) {
                    joinPresenter.projectTeamList();
                }
                break;
        }
    }

    @OnClick(R.id.btn_join)
    void joinClick(View v){
        if(type == 3){
            if(TextUtils.isEmpty(etTeamName.getText())){
                AppUtils.showToast(this, "请输入团队名称");
                return;
            }
            if(TextUtils.isEmpty(etTeamCipher.getText())){
                AppUtils.showToast(this, "请输入团队集结暗号");
                return;
            }
            Pattern pattern = Pattern.compile("^[0-9]{4}$");
            Matcher isNum = pattern.matcher(etTeamCipher.getText());
            if (!isNum.matches()) {
                AppUtils.showToast(this, "集结暗号应为4位数字");
                return;
            }
        }
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
        map.put("id", projectId);
        if(type == 3){
            map.put("team_name", etTeamName.getText().toString());
            map.put("cipher", etTeamCipher.getText().toString());
            map.put("bulletin", etTeamBulletin.getText().toString());
            map.put("role", etMemberRole.getText().toString());
        } else if(type == 2){
            map.put("team_id", joinTeamId);
            map.put("cipher", joinTeamCipher);
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
        this.projectTeamList.clear();
        if(projectTeamList != null) {
            this.projectTeamList.addAll(projectTeamList);
        }
        teamAdapter.notifyDataSetChanged();
    }

    @Override
    public Map<String, Object> applyJoinMap() {
        return null;
    }

    @Override
    public void onApplyJoinSuccess() {
    }

    @Override
    protected void onDestroy() {
        if(joinPresenter != null){
            joinPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onJoinWithTeam(ProjectTeamBean team) {
        DialogUtil.createInputDialog(this, "集结号", "确定", "取消", "请输入团队集结号", new DialogUtil.OnInputDialogListener() {
            @Override
            public void onInput(String text) {
                joinTeamId = team.getId();
                joinTeamCipher = text;
                joinPresenter.projectJoin();
            }
        });
    }
}
