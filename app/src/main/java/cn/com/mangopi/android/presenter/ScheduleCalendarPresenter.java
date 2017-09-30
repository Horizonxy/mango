package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;
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
        Subscription subscription = scheduleCalendarModel.scheduleCalendar(scheduleCalendarListener.getQueryMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<List<ScheduleCalendarBean>>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            scheduleCalendarListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<List<ScheduleCalendarBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                scheduleCalendarListener.onScheduleCanlendarSuccess(restResult.getData());
                            } else {
                                scheduleCalendarListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void addOrderSchedule(){
        Context context  = scheduleCalendarListener.currentContext();
        Subscription subscription = scheduleCalendarModel.addOrderSchedule(scheduleCalendarListener.getOrderId(),
                scheduleCalendarListener.getScheduleDate(), scheduleCalendarListener.getScheduleTime(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            scheduleCalendarListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                scheduleCalendarListener.onAddScheduleSuccess();
                            } else {
                                scheduleCalendarListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void cancelOrderBatchSchedule(){
        Context context  = scheduleCalendarListener.currentContext();
        Subscription subscription = scheduleCalendarModel.cancelOrderBatchSchedule(scheduleCalendarListener.getScheduleDate(), scheduleCalendarListener.getScheduleTime(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            scheduleCalendarListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                scheduleCalendarListener.onCancelScheduleSuccess();
                            } else {
                                scheduleCalendarListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
