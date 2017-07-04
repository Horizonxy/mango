package com.mango.model.data;


import com.mango.model.api.ApiManager;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MemberModel {

    public Subscription getRegistVerifyCode(String mobile, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.getRegistVerifyCode(mobile)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription login(String mobile, String smsCode, String sessId, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.login(mobile, smsCode, sessId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
