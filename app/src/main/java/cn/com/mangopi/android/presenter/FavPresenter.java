package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.ui.viewlistener.FavListener;

import rx.Subscription;

public class FavPresenter extends BasePresenter {

    FavModel favModel;
    FavListener listener;

    public FavPresenter(FavModel favModel, FavListener listener) {
        this.favModel = favModel;
        this.listener = listener;
    }

    public void addFav(){
        Context context = listener.currentContext();
        Subscription subscription = favModel.addFav(listener.getEntityId(), listener.getEntityTypeId(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onSuccess(true);
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void delFav(){
        Context context = listener.currentContext();
        Subscription subscription = favModel.delFav(listener.getEntityId(), listener.getEntityTypeId(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onSuccess(false);
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
