package cn.com.mangopi.android.model.data;

import java.util.List;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CommunityClassifyBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class CommunityModel {

    public Subscription getCommunityTypeList(Action0 onSubscribe, Subscriber<RestResult<List<CommunityTypeBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCommunityTypeList(), onSubscribe).subscribe(subscriber);
    }

    public Subscription getCommunityClassifyList(Action0 onSubscribe, Subscriber<RestResult<List<CommunityClassifyBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCommunityClassifyList(), onSubscribe).subscribe(subscriber);
    }
}
