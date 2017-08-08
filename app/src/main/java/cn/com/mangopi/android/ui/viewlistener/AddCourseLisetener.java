package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;

public interface AddCourseLisetener extends BaseViewListener {

    void onClassifySuccess(List<CourseClassifyBean> classifyList);
    void onTypeSuccess(List<CourseTypeBean> typeList);

}
