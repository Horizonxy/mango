package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.CourseBean;

public interface CourseListListener extends BaseViewListener {

    void onSuccess(List<CourseBean> courseList);
    Map<String, Object> getQueryMap();

}
