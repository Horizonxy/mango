package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WorksProjectModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectDetailListener;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectListListener;
import rx.Subscription;
import rx.functions.Action1;

public class WorksProjectPresenter extends BasePresenter {

    private WorksProjectModel projectModel;
    private BaseViewListener projectListener;

    public WorksProjectPresenter(BaseViewListener projectListener) {
        this.projectListener = projectListener;
        this.projectModel = new WorksProjectModel();
    }

    public void getProjectList() {
        WorksProjectListListener projectListListener = (WorksProjectListListener) this.projectListener;
        Subscription subscription = projectModel.getProjectList(projectListListener.getPageNo(), projectListListener.getRelation(),
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        projectListListener.onFailure(null);
                    }
                }, new BaseSubscriber<RestResult<List<ProjectListBean>>>(){
                    @Override
                    public void onNext(RestResult<List<ProjectListBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                projectListListener.onProjectListSuccess(restResult.getData());
                            } else {
                                projectListListener.onFailure(restResult.getRet_msg());
                            }
                        } else {
                            projectListListener.onFailure(null);
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void getProject(){
        WorksProjectDetailListener projectDetailListener = (WorksProjectDetailListener) projectListener;
        Context context = projectDetailListener.currentContext();
        Subscription subscription = projectModel.getProject(projectDetailListener.getId(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ProjectDetailBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            projectDetailListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ProjectDetailBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                projectDetailListener.onSuccess(restResult.getData());
                            } else {
                                projectDetailListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
