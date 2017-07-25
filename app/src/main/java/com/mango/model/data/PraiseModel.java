package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.RestResult;
import com.mango.util.RxJavaUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class PraiseModel {

    public Subscription praise(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.praise(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }

    public Subscription wantCount(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.wantCount(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }
}
