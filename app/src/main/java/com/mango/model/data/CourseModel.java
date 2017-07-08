package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class CourseModel {

    public Subscription getClassify(Map<String, Long> map, Action0 onSubscribe, Subscriber<RestResult<List<CourseClassifyBean>>> subscriber){
        return ApiManager.getClassifyList(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
