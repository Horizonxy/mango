package com.mango.ui.adapter;

import android.content.Context;

import com.mango.R;
import com.mango.model.bean.CourseBean;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.List;

public class RecommendCourseAdapter extends QuickAdapter<CourseBean> {

    public RecommendCourseAdapter(Context context, int layoutResId, List<CourseBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CourseBean item) {
        helper.setImageUrl(R.id.iv_avatar, item.getAvatar_rsurl())
                .setText(R.id.tv_course_title, item.getCourse_title())
                .setText(R.id.tv_member_name, item.getMember_name())
                .setText(R.id.tv_type_name, item.getType_name())
                .setText(R.id.tv_jobs, item.getTutor_jobs())
                .setText(R.id.tv_want_count, item.getWant_count() + "人想听");
    }
}
