package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import java.util.List;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;

public class TeamInProjectDetailAdapter extends QuickAdapter<ProjectDetailBean.ProjectActorBean> {

    public TeamInProjectDetailAdapter(Context context, int layoutResId, List<ProjectDetailBean.ProjectActorBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProjectDetailBean.ProjectActorBean item) {
        helper.setText(R.id.tv_team_name, item.getActor_name());
        if(TextUtils.isEmpty(item.getCompany_comments())){
            helper.setText(R.id.tv_comment, context.getResources().getString(R.string.write_comment))
            .setTextColorRes(R.id.tv_comment, R.color.color_ffb900);
        } else {
            helper.setText(R.id.tv_comment, context.getResources().getString(R.string.has_write_comment))
                    .setTextColorRes(R.id.tv_comment, R.color.color_666666);
        }
        helper.setText(R.id.tv_vote_count, String.format(context.getResources().getString(R.string.vote_count), item.getVote_count()));
        TeamClickLstener clickLstener = new TeamClickLstener(item);
        helper.setOnClickListener(R.id.tv_comment, clickLstener)
                .setOnClickListener(R.id.tv_work, clickLstener);
    }

    class TeamClickLstener implements View.OnClickListener{

        ProjectDetailBean.ProjectActorBean actorBean;

        public TeamClickLstener(ProjectDetailBean.ProjectActorBean actorBean) {
            this.actorBean = actorBean;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_comment:
                    if(TextUtils.isEmpty(actorBean.getCompany_comments())){
                        ActivityBuilder.startWorkProjectCommentActivity((Activity) context, actorBean);
                    }
                    break;
                case R.id.tv_work:
                    break;
            }
        }
    }
}
