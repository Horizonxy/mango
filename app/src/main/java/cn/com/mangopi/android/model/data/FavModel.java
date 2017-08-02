package cn.com.mangopi.android.model.data;

import java.util.List;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class FavModel {

    public Subscription addFav(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addFav(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }

    public Subscription delFav(long entityId, int entityTypeId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.delFav(entityId, entityTypeId), onSubscribe).subscribe(subscriber);
    }

    public Subscription getFavList(int pageNo, Action1 onError, Subscriber<RestResult<List<FavBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getFavList(pageNo), onError).subscribe(subscriber);
    }
}
