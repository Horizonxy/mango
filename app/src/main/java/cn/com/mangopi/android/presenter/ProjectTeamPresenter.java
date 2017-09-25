package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.ActorTeamModel;
import cn.com.mangopi.android.ui.viewlistener.ProjectTeamListener;
import rx.Subscription;

public class ProjectTeamPresenter extends BasePresenter {

    ActorTeamModel teamModel;
    ProjectTeamListener teamListener;

    public ProjectTeamPresenter(ProjectTeamListener teamListener) {
        this.teamListener = teamListener;
        this.teamModel = new ActorTeamModel();
    }

    public void getActorTeam(){
        Context context = teamListener.currentContext();
        Subscription subscription = teamModel.getActorTeam(teamListener.getTeamId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<ActorTeamBean>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    teamListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<ActorTeamBean> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        teamListener.onTeamSuccess(restResult.getData());
                    } else {
                        teamListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
