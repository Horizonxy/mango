package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.RestResult;
import com.mango.util.RxJavaUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class FavModel {

    public Subscription addFav(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addFav(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }

    public Subscription delFav(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.delFav(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }
}
