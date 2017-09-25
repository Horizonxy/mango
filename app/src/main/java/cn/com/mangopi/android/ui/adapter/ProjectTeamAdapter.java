package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class ProjectTeamAdapter extends QuickAdapter<ActorTeamBean.TeamMember> {

    public ProjectTeamAdapter(Context context, int layoutResId, List<ActorTeamBean.TeamMember> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ActorTeamBean.TeamMember item) {
        helper.setText(R.id.tv_member_info, String.format(context.getResources().getString(R.string.member_name_role), item.getNick_name(), item.getRole_name()))
                .setVisible(R.id.tv_self, item.getMember_id() == Application.application.getMember().getId())
                .setImageUrl(R.id.iv_avatar, item.getAvatar_rsurl());
    }
}
