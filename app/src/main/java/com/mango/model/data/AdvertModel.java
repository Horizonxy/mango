package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.RestResult;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class AdvertModel {

    public Subscription homeAdvert(String userIdentity, String position, Action0 onSubscribe, Subscriber<RestResult<List<AdvertBean>>> subscriber){
        return ApiManager.getAdvert(userIdentity, position)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
