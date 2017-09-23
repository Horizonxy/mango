package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.util.RxJavaUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class TrendModel {

    public Subscription getTrendList(Map<String, Object> map, Action1 onError, Subscriber<RestResult<List<TrendBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getTrendList(map), onError).subscribe(subscriber);
    }

    public Subscription addTrend(String content, List<String> pics, Action0 onSubscriber, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addTrend(content, pics), onSubscriber).subscribe(subscriber);
    }

    public Subscription getTrend(long id, Action0 onSubscriber, Subscriber<RestResult<TrendDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getTrend(id), onSubscriber).subscribe(subscriber);
    }
}
