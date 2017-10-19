package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.PayResultBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.ui.viewlistener.OrderPayListener;
import rx.Subscription;

public class OrderPayPresenter extends BasePresenter {

    OrderModel orderModel;
    OrderPayListener payListener;

    public OrderPayPresenter(OrderModel orderModel, OrderPayListener payListener) {
        this.orderModel = orderModel;
        this.payListener = payListener;
    }

    public void orderPay(){
        Context context  = payListener.currentContext();
        Subscription subscription = orderModel.orderPay(payListener.getId(), payListener.getChannel(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<PayResultBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            payListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<PayResultBean> restResult) {
                        if(restResult != null){
                            if(restResult.isFailure()){
                                payListener.onFailure(restResult.getRet_msg());
                            } else {
                                payListener.onSuccess(restResult.getData());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
