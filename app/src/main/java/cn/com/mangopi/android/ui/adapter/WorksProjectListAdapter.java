package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.List;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.popupwindow.SharePopupWindow;
import cn.com.mangopi.android.ui.widget.MangoUMShareListener;
import cn.com.mangopi.android.util.DateUtils;

public class WorksProjectListAdapter extends QuickAdapter<ProjectListBean> {

    int relation;

    public WorksProjectListAdapter(Context context, int layoutResId, List<ProjectListBean> data, int relation) {
        super(context, layoutResId, data);
        this.relation = relation;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProjectListBean item) {
        helper.setText(R.id.tv_project_name, item.getProject_name());
        StringBuffer startJoin = new StringBuffer();
        if(item.getPublish_time() != null){
            startJoin.append("起始  ").append(DateUtils.dateToString(item.getPublish_time(), DateUtils.DATE_PATTERN)).append("    ");
        }
        if(item.getMember_join_time() != null){
            startJoin.append("报名  ").append(DateUtils.dateToString(item.getMember_join_time(), DateUtils.DATE_PATTERN));
        }

        WorksProjectItemClickListener clickListener = new WorksProjectItemClickListener(item);

        helper.setText(R.id.tv_start_join, item.toString())
                .setText(R.id.tv_focus_count, "关注  "+String.valueOf(item.getFocus_count()))
                .setText(R.id.tv_applied_count, "报名  "+String.valueOf(item.getApplied_count()))
                .setText(R.id.tv_state_label, item.getState_label())
                .setOnClickListener(R.id.layout_share, clickListener);
    }

    class WorksProjectItemClickListener implements View.OnClickListener {

        ProjectListBean project;

        public WorksProjectItemClickListener(ProjectListBean project) {
            this.project = project;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_share:
                    SharePopupWindow sharePopupWindow = new SharePopupWindow((Activity) context, String.format(Constants.WORK_PROJECT_URL, project.getId()), project.getProject_name(),
                            project.getProject_name(), null, new MangoUMShareListener());
                    sharePopupWindow.show();
                    break;
            }
        }
    }
}