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
}
