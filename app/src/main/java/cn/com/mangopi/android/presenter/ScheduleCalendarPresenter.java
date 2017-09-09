package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.ScheduleCalendarModel;
import cn.com.mangopi.android.ui.viewlistener.OrderScheduleCalendarListener;
import rx.Subscription;

public class ScheduleCalendarPresenter extends BasePresenter {

    ScheduleCalendarModel scheduleCalendarModel;
    OrderScheduleCalendarListener scheduleCalendarListener;

    public ScheduleCalendarPresenter(OrderScheduleCalendarListener scheduleCalendarListener) {
        this.scheduleCalendarListener = scheduleCalendarListener;
        this.scheduleCalendarModel = new ScheduleCalendarModel();
    }

    public void scheduleCalendar(){
        Context context  = scheduleCalendarListener.currentContext();
        Subscription subscription = scheduleCalendarModel.scheduleCalendar(new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<String>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            scheduleCalendarListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<String> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){

                            } else {
                                scheduleCalendarListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
