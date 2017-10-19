package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.MangoUtils;

public class RankActorAdapter extends QuickAdapter<ProjectActorBean> {

    public RankActorAdapter(Context context, int layoutResId, List<ProjectActorBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProjectActorBean item) {
        helper.setText(R.id.tv_reward, item.getAwards())
                .setText(R.id.tv_actor_name, item.getActor_name());
        StringBuilder memberStr = new StringBuilder("成员：");
        if(item.getTeam() != null && item.getTeam().getMembers() != null) {
            List<ActorTeamBean.TeamMember> members = item.getTeam().getMembers();
            for (int i = 0; i < members.size(); i++) {
                memberStr.append(members.get(i).getNick_name());
                if(i < (members.size() - 1)){
                    memberStr.append("、");
                }
            }
        }
        helper.setText(R.id.tv_member_info, memberStr.toString())
                .setOnClickListener(R.id.tv_other_doc, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityBuilder.startWorkProjectTeamDocActivity((Activity) context, item.getActor_name(), item.getScheme_doc_rsurls());
                    }
                });

        List<String> photoDatas = new ArrayList<>();
        ListView lvPhotos = helper.getView(R.id.lv_photo);
        lvPhotos.setAdapter(new MatchParentPhotoAdapter(context, R.layout.listview_item_match_photo, photoDatas));
        if(item.getWorks_photo_rsurls() != null){
            photoDatas.addAll(item.getWorks_photo_rsurls());
        }
        View emptyPhotoView = helper.getView(R.id.layout_empty);
        if(lvPhotos.getEmptyView() == null) {
            lvPhotos.setEmptyView(emptyPhotoView);
        }
        ((MatchParentPhotoAdapter)lvPhotos.getAdapter()).notifyDataSetChanged();

        View lineTutorComment = helper.getView(R.id.line_tutor_comment);
        TextView tvTutorCommentTip = helper.getView(R.id.tv_tutor_comment_tip);
        TextView tvTutorComment = helper.getView(R.id.tv_tutor_comment);
        View lineCompanyComment = helper.getView(R.id.line_company_comment);
        TextView  tvCompanyCommentTip = helper.getView(R.id.tv_company_comment_tip);
        TextView tvCompanyComment = helper.getView(R.id.tv_company_comment);

        String tutorCommment = item.getTutor_comments();
        if(TextUtils.isEmpty(tutorCommment)){
            MangoUtils.setViewsVisibility(View.GONE, lineTutorComment, tvTutorComment, tvTutorCommentTip);
        } else {
            MangoUtils.setViewsVisibility(View.VISIBLE, lineTutorComment, tvTutorComment, tvTutorCommentTip);
            tvTutorComment.setText(tutorCommment);
        }

        String companyComment = item.getCompany_comments();
        if(TextUtils.isEmpty(companyComment)){
            MangoUtils.setViewsVisibility(View.GONE, lineCompanyComment, tvCompanyCommentTip, tvCompanyComment);
        } else {
            MangoUtils.setViewsVisibility(View.VISIBLE, lineCompanyComment, tvCompanyCommentTip, tvCompanyComment);
            tvCompanyComment.setText(companyComment);
        }
    }
}
