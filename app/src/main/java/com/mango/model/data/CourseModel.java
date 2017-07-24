package com.mango.model.data;

import com.mango.model.api.ApiManager;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;
import com.mango.util.RxJavaUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class CourseModel {

    public Subscription getClassify(Map<String, Long> map, Action0 onSubscribe, Subscriber<RestResult<List<CourseClassifyBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getClassifyList(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription getClassify(Map<String, Long> map, Subscriber<RestResult<List<CourseClassifyBean>>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.getClassifyList(map)).subscribe(subscriber);
    }

    public Subscription getCourseList(Map<String, Object> map, Action1 onError, Subscriber<RestResult<List<CourseBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getCourseList(map), onError).subscribe(subscriber);
    }

    public Subscription getCourse(long id, Action0 onSubscribe, Subscriber<RestResult<CourseDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCourse(id), onSubscribe).subscribe(subscriber);
    }
}
