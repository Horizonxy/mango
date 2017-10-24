package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class RecommendCourseAdapter extends QuickAdapter<CourseBean> {

    private Context context;
    private int dp60;

    public RecommendCourseAdapter(Context context, int layoutResId, List<CourseBean> data) {
        super(context, layoutResId, data);
        this.context = context;
        this.dp60 = DisplayUtils.dip2px(context, 60);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CourseBean item) {
        helper.setImageUrl(R.id.iv_avatar, MangoUtils.getCalculateWidthSizeUrl(item.getAvatar_rsurl(), dp60))
                .setText(R.id.tv_course_title, item.getCourse_title())
                .setText(R.id.tv_member_name, item.getMember_name())
                .setText(R.id.tv_type_name, item.getType_name())
                .setText(R.id.tv_jobs, item.getTutor_jobs());
        if(item.getWant_count() > 0) {
            helper.setText(R.id.tv_want_count, item.getWant_count() + "人想听");
            helper.setVisible(R.id.tv_want_count, true);
        } else {
            helper.setVisible(R.id.tv_want_count, false);
        }
        helper.setOnClickListener(R.id.iv_avatar, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBuilder.startTutorDetailActivity((Activity) context, item.getMember_id());
            }
        });
        helper.setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));
    }
}
