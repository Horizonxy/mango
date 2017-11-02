package cn.com.mangopi.android.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class CourseModel {

    public Subscription getClassify(Map<String, Long> map, Action0 onSubscribe, Subscriber<RestResult<ArrayList<CourseClassifyBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getClassifyList(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription getClassify(Map<String, Long> map, Subscriber<RestResult<List<CourseClassifyBean>>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.getClassifyList(map)).subscribe(subscriber);
    }

    public Subscription getTypeList(Action0 onSubscribe, Subscriber<RestResult<ArrayList<CourseTypeBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getTypeList(), onSubscribe).subscribe(subscriber);
    }

    public Subscription getCourseList(Map<String, Object> map, Action1 onError, Subscriber<RestResult<List<CourseBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getCourseList(map), onError).subscribe(subscriber);
    }

    public Subscription getCourse(long id, Action0 onSubscribe, Subscriber<RestResult<CourseDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCourse(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription addCourse(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addCourse(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription delCourse(long id, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.delCourse(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription offCourse(long id, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.offCourse(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription onCourse(long id, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.onCourse(id), onSubscribe).subscribe(subscriber);
    }
}
