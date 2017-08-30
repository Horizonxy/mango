package cn.com.mangopi.android.presenter;

import java.util.List;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WorksProjectModel;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectListListener;
import rx.Subscription;
import rx.functions.Action1;

public class WorksProjectPresenter extends BasePresenter {

    private WorksProjectModel projectModel;
    private WorksProjectListListener projectListListener;

    public WorksProjectPresenter(WorksProjectListListener projectListListener) {
        this.projectListListener = projectListListener;
        this.projectModel = new WorksProjectModel();
    }

    public void getProjectList() {
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
}
