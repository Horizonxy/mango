package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class ScheduleCalendarModel {

    public Subscription scheduleCalendar(Action0 onSubscribe, Subscriber<RestResult<String>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.scheduleCalendar(), onSubscribe).subscribe(subscriber);
    }
}
