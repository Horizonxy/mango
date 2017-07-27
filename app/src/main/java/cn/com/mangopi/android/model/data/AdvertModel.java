package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class AdvertModel {

    public Subscription homeAdvert(String userIdentity, String position, Action0 onSubscribe, Subscriber<RestResult<ArrayList<AdvertBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getAdvert(userIdentity, position), onSubscribe).subscribe(subscriber);
    }
}
