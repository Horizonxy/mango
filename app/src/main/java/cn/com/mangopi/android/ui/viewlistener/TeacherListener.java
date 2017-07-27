package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;

import java.util.List;
import java.util.Map;

public interface TeacherListener extends BaseViewListener {

    void onCourseListSuccess(int hotTypes, List<CourseBean> courseList);
    void onClassifySuccess(List<CourseClassifyBean> classifyList);
    Map<String, Object> getQueryMap(int hotTypes);
}
