package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.OrderBean;
import com.mango.model.bean.RestResult;
import com.mango.util.RxJavaUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class OrderModel {

    public Subscription getOrderList(Map<String, Object> map, Action1 onError, Subscriber<RestResult<List<OrderBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getOrderList(map), onError).subscribe(subscriber);
    }

    public Subscription addOrder(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<OrderBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addOrder(map), onSubscribe).subscribe(subscriber);
    }
}
