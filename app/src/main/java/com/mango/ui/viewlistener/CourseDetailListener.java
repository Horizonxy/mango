package com.mango.ui.viewlistener;

import com.mango.model.bean.CourseDetailBean;

public interface CourseDetailListener extends BaseViewListener {
    long getId();
    void onSuccess(CourseDetailBean courseDetail);
}
