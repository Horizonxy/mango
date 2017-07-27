package cn.com.mangopi.android.ui.viewlistener;


import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;

import java.util.List;

public interface HomeFragmentListener extends BaseViewListener {

    void onSuccess(String position, List<AdvertBean> advertList);
    void onSuccess(List<BulletinBean> bulletinList);
    void onClassifySuccess(List<CourseClassifyBean> courseClassifyList);
    String getUserIdentity();
}
