package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WorksProjectModel;
import cn.com.mangopi.android.ui.viewlistener.WorkProjectCommentListener;
import rx.Subscription;

public class WorkProjectCommentPresenter extends BasePresenter {

    WorksProjectModel worksProjectModel;
    WorkProjectCommentListener commentListener;

    public WorkProjectCommentPresenter(WorkProjectCommentListener commentListener) {
        this.commentListener = commentListener;
        this.worksProjectModel = new WorksProjectModel();
    }

    public void actorComment(){
        Context context = commentListener.currentContext();
        Subscription subscription = worksProjectModel.actorComment(commentListener.getActorId(), commentListener.getScore(), commentListener.getComment(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            commentListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                commentListener.onCommentSuccess();
                            } else {
                                commentListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
