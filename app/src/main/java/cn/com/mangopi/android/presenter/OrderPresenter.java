package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.model.data.ScheduleCalendarModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.OrderDetailListener;
import cn.com.mangopi.android.ui.viewlistener.OrderListListener;
import cn.com.mangopi.android.ui.viewlistener.PlaceOrderListener;
import rx.Subscription;
import rx.functions.Action1;

public class OrderPresenter extends BasePresenter {

    OrderModel orderModel;
    BaseViewListener viewListener;
    ScheduleCalendarModel scheduleModel;

    public OrderPresenter(OrderModel orderModel, BaseViewListener viewListener) {
        this.orderModel = orderModel;
        this.viewListener = viewListener;
    }

    public void getOrderList(){
        OrderListListener listener = (OrderListListener) viewListener;
        Subscription subscription = orderModel.getOrderList(listener.getQueryMap(), new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                listener.onFailure(null);
            }
        }, new BaseSubscriber<RestResult<List<OrderBean>>>(){
            @Override
            public void onNext(RestResult<List<OrderBean>> restResult) {
                super.onNext(restResult);
                if (restResult == null) {
                    listener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        listener.onOrderListSuccess(restResult.getData());
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void addOrder(){
        Context context = viewListener.currentContext();
        PlaceOrderListener listener = (PlaceOrderListener)viewListener;
        Subscription subscription = orderModel.addOrder(listener.addOrderQuery(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<OrderBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<OrderBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onSuccess(restResult.getData());
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void cancelOrder(OrderBean order){
        OrderListListener listener = (OrderListListener) viewListener;
        Context context = listener.currentContext();
        Subscription subscription = orderModel.cancelOrder(order.getId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        listener.onCancelSuccess(order);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getOrder(){
        OrderDetailListener listener = (OrderDetailListener) viewListener;
        Context context = listener.currentContext();
        Subscription subscription = orderModel.getOrder(listener.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<OrderDetailBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<OrderDetailBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onSuccess(restResult.getData());
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void cancelSchedule(OrderBean order){
        OrderListListener listener = (OrderListListener) viewListener;
        Context context = listener.currentContext();
        if(scheduleModel == null){
            scheduleModel = new ScheduleCalendarModel();
        }
        Subscription subscription = scheduleModel.cancelOrderSchedule(order.getId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        listener.onCancelScheduleSuccess(order);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void addCourseComment(String content){
        OrderDetailListener listener = (OrderDetailListener) viewListener;
        Context context = listener.currentContext();
        Subscription subscription = orderModel.addCourseComment(listener.getId(), listener.getCourseId(), content,
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onCommentSuccess(content);
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void replyCourseComment(String reply){
        OrderDetailListener listener = (OrderDetailListener) viewListener;
        Context context = listener.currentContext();
        Subscription subscription = orderModel.replyCourseComment(listener.getCommentId(), reply,
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onReplySuccess(reply);
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
