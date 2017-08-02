package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.FavListListener;
import cn.com.mangopi.android.ui.viewlistener.FavListener;

import rx.Subscription;
import rx.functions.Action1;

public class FavPresenter extends BasePresenter {

    FavModel favModel;
    BaseViewListener listener;

    public FavPresenter(FavModel favModel, BaseViewListener listener) {
        this.favModel = favModel;
        this.listener = listener;
    }

    public void addFav(){
        FavListener listener = (FavListener) this.listener;
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
        FavListener listener = (FavListener) this.listener;
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

    public void getFavList(){
        FavListListener listListener = (FavListListener) this.listener;
        Subscription subscription = favModel.getFavList(listListener.getPageNo(),
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        listListener.onFailure(null);
                    }
                }, new BaseSubscriber<RestResult<List<FavBean>>>(){
                    @Override
                    public void onNext(RestResult<List<FavBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listListener.onSuccess(restResult.getData());
                            } else {
                                listListener.onFailure(restResult.getRet_msg());
                            }
                        } else {
                            listListener.onFailure(null);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
