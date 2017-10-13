package cn.com.mangopi.android.presenter;

import android.content.Context;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.ActorTeamModel;
import cn.com.mangopi.android.ui.viewlistener.ProjectWorkListener;
import rx.Subscription;

public class ProjectActorPresenter extends BasePresenter {

    ActorTeamModel actorModel;
    ProjectWorkListener projectWorkListener;

    public ProjectActorPresenter(ProjectWorkListener projectWorkListener) {
        this.projectWorkListener = projectWorkListener;
        this.actorModel = new ActorTeamModel();
    }

    public void getProjectActor(){
        Context context = projectWorkListener.currentContext();
        Subscription subscription = actorModel.getProjectActor(projectWorkListener.getActorId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<ProjectActorBean>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    projectWorkListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<ProjectActorBean> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        projectWorkListener.onProjectActorSuccess(restResult.getData());
                    } else {
                        projectWorkListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
