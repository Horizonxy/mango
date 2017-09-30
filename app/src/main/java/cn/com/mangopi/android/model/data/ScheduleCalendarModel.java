package cn.com.mangopi.android.model.data;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;
import cn.com.mangopi.android.util.RxJavaUtils;
import retrofit2.http.QueryMap;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class ScheduleCalendarModel {

    public Subscription scheduleCalendar(@QueryMap Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<List<ScheduleCalendarBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.scheduleCalendar(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription addOrderSchedule(long orderId, String date, int time,  Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addOrderSchedule(orderId, date, time), onSubscribe).subscribe(subscriber);
    }

    public Subscription cancelOrderBatchSchedule(String date, int time,  Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.cancelOrderBatchSchedule(date, time), onSubscribe).subscribe(subscriber);
    }

    public Subscription cancelOrderSchedule(long orderId,  Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.cancelOrderSchedule(orderId), onSubscribe).subscribe(subscriber);
    }
}
