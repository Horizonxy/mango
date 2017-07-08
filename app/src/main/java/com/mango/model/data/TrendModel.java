package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TrendBean;
import com.mango.util.RxJavaUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class TrendModel {

    public Subscription getTrendList(Map<String, Object> map, Action1 onError, Subscriber<RestResult<List<TrendBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getTrendList(map), onError).subscribe(subscriber);
    }
}
