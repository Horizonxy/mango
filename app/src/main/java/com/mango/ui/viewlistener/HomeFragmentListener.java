package com.mango.ui.viewlistener;


import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseClassifyBean;

import java.util.List;

public interface HomeFragmentListener extends BaseViewListener {

    void onSuccess(String position, List<AdvertBean> advertList);
    void onSuccess(List<BulletinBean> bulletinList);
    void onClassifySuccess(List<CourseClassifyBean> courseClassifyList);
    String getUserIdentity();
}
