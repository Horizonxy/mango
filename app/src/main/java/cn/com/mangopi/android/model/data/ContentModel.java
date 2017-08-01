package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.ContentDetailBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class ContentModel {

    public Subscription getContent(long id, Action0 onSubscribe, Subscriber<RestResult<ContentDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getContent(id), onSubscribe).subscribe(subscriber);
    }
}
