package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.ProjectDetailBean;

public interface WorksProjectDetailListener extends BaseViewListener {

    void onSuccess(ProjectDetailBean projectDetail);
    long getId();
}
