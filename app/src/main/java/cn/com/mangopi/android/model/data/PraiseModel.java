package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;

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
