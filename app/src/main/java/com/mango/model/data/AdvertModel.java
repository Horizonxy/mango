package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.RestResult;
import com.mango.util.RxJavaUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class AdvertModel {

    public Subscription homeAdvert(String userIdentity, String position, Action0 onSubscribe, Subscriber<RestResult<List<AdvertBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getAdvert(userIdentity, position), onSubscribe).subscribe(subscriber);
    }
}
