package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.CourseDetailBean;

public interface CourseDetailListener extends BaseViewListener {
    long getId();
    void onSuccess(CourseDetailBean courseDetail);
}
