package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.data.PraiseModel;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import rx.Subscription;
import rx.functions.Action1;

public class FoundPresenter extends BasePresenter {

    TrendModel trendModel;
    FoundListener viewListener;
    PraiseModel praiseModel;

    public FoundPresenter(PraiseModel praiseModel, TrendModel trendModel, FoundListener viewListener) {
        this.praiseModel = praiseModel;
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
                }
                viewListener.onFailure();
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

    public void praise(TrendBean  trend){
        Context context = viewListener.currentContext();
        Subscription subscription = praiseModel.praise(trend.getId(), Constants.EntityType.TREND.getTypeId(), new CreateLoading(context, context.getResources().getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    viewListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult != null){
                    if(restResult.isSuccess()){
                        trend.setPraise_count(trend.getPraise_count() + 1);
                        viewListener.notifyData();
                    } else {
                        viewListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
