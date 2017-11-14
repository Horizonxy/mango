package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.List;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;

public class ProjectJoinTeamAdapter extends QuickAdapter<ProjectTeamBean> {

    ProjectJoinWithTeamListener joinWithTeamListener;
    long projectId;

    public void setJoinWithTeamListener(ProjectJoinWithTeamListener joinWithTeamListener) {
        this.joinWithTeamListener = joinWithTeamListener;
    }

    public ProjectJoinTeamAdapter(Context context, int layoutResId, List<ProjectTeamBean> data, long projectId) {
        super(context, layoutResId, data);
        this.projectId = projectId;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProjectTeamBean item) {
        helper.setText(R.id.tv_team_name, item.getTeam_name())
                .setText(R.id.tv_people_count, String.format(context.getResources().getString(R.string.have_member_count), item.getMember_count()))
                .setImageUrl(R.id.iv_avatar, item.getAvatar_rsurl())
                .setText(R.id.tv_team_role, String.format(context.getResources().getString(R.string.actor_info), item.getActor_name(), item.getRole_name()))
                .setText(R.id.tv_bulletin, item.getBulletin());
        if(joinWithTeamListener != null){
            helper.setOnClickListener(R.id.btn_join, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(joinWithTeamListener != null){
//                        joinWithTeamListener.onJoinWithTeam(item);
//                    }
                    ActivityBuilder.startProjectJoinWayActivity((Activity) context, item, projectId);
                }
            });
        }
    }

    public interface ProjectJoinWithTeamListener {
        void onJoinWithTeam(ProjectTeamBean team);
    }
}
