package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WantCountModel;
import cn.com.mangopi.android.ui.viewlistener.WantCountListener;
import rx.Subscription;

public class WantCountPresenter extends BasePresenter {

    WantCountListener wantCountListener;
    WantCountModel wantCountModel;

    public WantCountPresenter(WantCountListener wantCountListener) {
        this.wantCountListener = wantCountListener;
        this.wantCountModel = new WantCountModel();
    }

    public void addWantCount(){
        Context context = wantCountListener.currentContext();
        Subscription subscription = wantCountModel.wantCount(wantCountListener.wantEntityId(), wantCountListener.wantEntityType(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            wantCountListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                wantCountListener.onWantCountSuccess();
                            } else {
                                wantCountListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
