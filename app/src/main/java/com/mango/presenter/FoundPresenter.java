package com.mango.presenter;

import android.content.Context;

import com.mango.Application;
import com.mango.Constants;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TrendBean;
import com.mango.model.data.TrendModel;
import com.mango.ui.viewlistener.FoundListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

public class FoundPresenter extends BasePresenter {

    TrendModel trendModel;
    FoundListener viewListener;

    public FoundPresenter(TrendModel trendModel, FoundListener viewListener) {
        this.trendModel = trendModel;
        this.viewListener = viewListener;
    }

    public void getTrendList(){
        Context context = viewListener.currentContext();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("page_no", viewListener.getPageNo());
        map.put("page_size", Constants.PAGE_SIZE);
        Subscription subscription = trendModel.getTrendList(map, new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                    viewListener.onFailure(e.getMessage());
                }
            }
        }, new BaseSubscriber<RestResult<List<TrendBean>>>() {

            @Override
            public void onNext(RestResult<List<TrendBean>> restResult) {
                if (restResult == null) {
                    viewListener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        viewListener.onSuccess(restResult.getData());
                    } else {
                        viewListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
