package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;

public interface ProjectJoinListener extends BaseViewListener {

    Map<String, Object> getMap();
    void onJoinSuccess(ProjectJoinBean projectJoin);
    long getId();
    void onTeamList(List<ProjectTeamBean> projectTeamList);
}
