package com.mango.model.data;


import com.mango.model.api.ApiManager;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MemberModel {

    public Subscription getLoginVerifyCode(String mobile, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.getLoginVerifyCode(mobile)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription quickLogin(String mobile, String smsCode, String sessId, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.quickLogin(mobile, smsCode, sessId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription updateMember(String nickName, int gender, String sessId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return ApiManager.updateMember(nickName, gender, sessId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getMember(long id, Action0 onSubscribe, Subscriber<RestResult<MemberBean>> subscriber){
       return ApiManager.getMember(id)
               .subscribeOn(Schedulers.io())
               .doOnSubscribe(onSubscribe)
               .subscribeOn(AndroidSchedulers.mainThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(subscriber);
    }
}
