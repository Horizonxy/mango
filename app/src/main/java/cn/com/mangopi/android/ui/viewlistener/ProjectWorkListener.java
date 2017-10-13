package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.ProjectActorBean;

public interface ProjectWorkListener extends BaseViewListener {
    long getActorId();
    void onProjectActorSuccess(ProjectActorBean projectActorBean);
}
