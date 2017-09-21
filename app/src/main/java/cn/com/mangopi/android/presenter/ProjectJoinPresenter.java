package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WorksProjectModel;
import cn.com.mangopi.android.ui.viewlistener.ProjectJoinListener;
import rx.Subscription;

public class ProjectJoinPresenter extends BasePresenter {

    WorksProjectModel projectModel;
    ProjectJoinListener projectJoinListener;

    public ProjectJoinPresenter(ProjectJoinListener projectJoinListener) {
        this.projectJoinListener = projectJoinListener;
        this.projectModel = new WorksProjectModel();
    }

    public void projectJoin(){
        Context context = projectJoinListener.currentContext();
        Subscription subscription = projectModel.projectJoin(projectJoinListener.getMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ProjectJoinBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            projectJoinListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ProjectJoinBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                projectJoinListener.onJoinSuccess(restResult.getData());
                            } else {
                                projectJoinListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void projectTeamList(){
        Context context = projectJoinListener.currentContext();
        Subscription subscription = projectModel.projectTeamList(projectJoinListener.getId(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<List<ProjectTeamBean>>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            projectJoinListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<List<ProjectTeamBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                projectJoinListener.onTeamList(restResult.getData());
                            } else {
                                projectJoinListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
