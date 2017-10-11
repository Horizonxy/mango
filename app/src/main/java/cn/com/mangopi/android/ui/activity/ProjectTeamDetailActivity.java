package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.presenter.ProjectTeamPresenter;
import cn.com.mangopi.android.ui.adapter.ProjectTeamAdapter;
import cn.com.mangopi.android.ui.viewlistener.ProjectTeamListener;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.AppUtils;

public class ProjectTeamDetailActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener, ProjectTeamListener {

    long teamId;
    ProjectTeamPresenter teamPresenter;
    @Bind(R.id.tv_team_name)
    TextView tvTeamName;
    @Bind(R.id.tv_cipher)
    TextView tvCipher;
    @Bind(R.id.tv_bulletin)
    TextView tvBulletin;
    @Bind(R.id.tv_member_count)
    TextView tvMemberCount;
    @Bind(R.id.lv_member)
    ListView lvMember;
    ActorTeamBean actorTeamBean;
    ProjectTeamAdapter teamMemberAdapter;
    List<ActorTeamBean.TeamMember> memberData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_team_detail);

        teamId = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        teamPresenter = new ProjectTeamPresenter(this);
        teamPresenter.getActorTeam();
    }

    private void initView() {
        titleBar.setTitle(R.string.works_team);
//        titleBar.setRightText(R.string.save);

        lvMember.setAdapter(teamMemberAdapter = new ProjectTeamAdapter(this, R.layout.listview_item_project_team_member, memberData));
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
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
    public long getTeamId() {
        return teamId;
    }

    @Override
    public void onTeamSuccess(ActorTeamBean team) {
        actorTeamBean = team;
        bindData(actorTeamBean);
    }

    private void bindData(ActorTeamBean actorTeamBean) {
        tvTeamName.setText(actorTeamBean.getTeam_name());
        tvCipher.setText(actorTeamBean.getCipher());
        tvBulletin.setText(actorTeamBean.getBulletin());
        tvMemberCount.setText(String.format(getString(R.string.member_count), actorTeamBean.getMember_count()));

        memberData.clear();
        if(actorTeamBean.getMembers() != null) {
            memberData.addAll(actorTeamBean.getMembers());
        }
        teamMemberAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if(teamPresenter != null){
            teamPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
