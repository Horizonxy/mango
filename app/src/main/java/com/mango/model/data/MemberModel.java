package com.mango.model.data;


import com.mango.model.api.ApiManager;
import com.mango.model.bean.RestResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MemberModel {

    public Subscription getRegistVerifyCode(String mobile, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return ApiManager.getRegistVerifyCode(mobile)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
