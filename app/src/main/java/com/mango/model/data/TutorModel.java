package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TutorBean;
import com.mango.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class TutorModel {

    public Subscription getTutor(long id, Action0 onSubscribe, Subscriber<RestResult<TutorBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getTutor(id), onSubscribe).subscribe(subscriber);
    }
}
