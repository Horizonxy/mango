package com.mango.presenter;

import com.mango.Application;
import com.mango.Constants;
import com.mango.model.bean.OrderBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.OrderModel;
import com.mango.ui.viewlistener.BaseViewListener;
import com.mango.ui.viewlistener.OrderListListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

public class OrderPresenter extends BasePresenter {

    OrderModel orderModel;
    BaseViewListener viewListener;

    public OrderPresenter(OrderModel orderModel, BaseViewListener viewListener) {
        this.orderModel = orderModel;
        this.viewListener = viewListener;
    }

    public void getOrderList(){
        OrderListListener listener = (OrderListListener) viewListener;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("page_no", listener.getPageNo());
        map.put("page_size", Constants.PAGE_SIZE);
        map.put("relation", listener.getRelation());
        Subscription subscription = orderModel.getOrderList(map, new Action1<Throwable>() {
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
}
