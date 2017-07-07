package com.mango.presenter;


import android.content.Context;

import com.mango.R;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.AdvertModel;
import com.mango.ui.viewlistener.HomeFragmentListener;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class HomePresenter extends BasePresenter {

    AdvertModel advertModel;
    HomeFragmentListener homeListener;

    public HomePresenter(AdvertModel advertModel, HomeFragmentListener homeListener) {
        this.advertModel = advertModel;
        this.homeListener = homeListener;
    }

    public void getAdvert(String position){
        Context context = homeListener.currentContext();
        Subscription subscription = advertModel.homeAdvert(homeListener.getUserIdentity(), position, new Action0() {
            @Override
            public void call() {
                createLoading(context, context.getString(R.string.please_wait));
            }
        }, new Subscriber<RestResult<List<AdvertBean>>>() {
            @Override
            public void onCompleted() {
                dimissLoading(homeListener.currentContext());
            }

            @Override
            public void onError(Throwable e) {
                dimissLoading(homeListener.currentContext());
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(RestResult<List<AdvertBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onSuccess(position, restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
