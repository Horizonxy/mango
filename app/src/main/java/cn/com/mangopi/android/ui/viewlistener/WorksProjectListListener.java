package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.ProjectListBean;

public interface WorksProjectListListener extends BaseViewListener {
    int getPageNo();
    int getRelation();
    void onProjectListSuccess(List<ProjectListBean> projectList);
}
