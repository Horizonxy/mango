package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TutorBean;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class TutorModel {

    public Subscription getTutor(long id, Action0 onSubscribe, Subscriber<RestResult<TutorBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getTutor(id), onSubscribe).subscribe(subscriber);
    }
}
