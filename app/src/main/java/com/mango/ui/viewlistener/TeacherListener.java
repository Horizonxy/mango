package com.mango.ui.viewlistener;

import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;

import java.util.List;

public interface TeacherListener extends BaseViewListener {

    void onCourseListSuccess(int hotTypes, List<CourseBean> courseList);
    void onClassifySuccess(List<CourseClassifyBean> classifyList);
}
