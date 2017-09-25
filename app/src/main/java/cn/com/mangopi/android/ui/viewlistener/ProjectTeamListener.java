package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.ActorTeamBean;

public interface ProjectTeamListener extends BaseViewListener {
    long getTeamId();
    void onTeamSuccess(ActorTeamBean team);
}
