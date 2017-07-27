package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.ui.viewlistener.AddTrendListener;

import rx.Subscription;

/**
 * @author 蒋先明
 * @date 2017/7/10
 */

public class AddTrendPresenter extends BasePresenter{

    TrendModel trendModel;
    AddTrendListener listener;

    public AddTrendPresenter(TrendModel trendModel, AddTrendListener listener) {
        this.trendModel = trendModel;
        this.listener = listener;
    }

    public void addTrend(){
        Context context = listener.currentContext();
        Subscription subscription = trendModel.addTrend(listener.getContent(), listener.getPics(), new CreateLoading(context, context.getResources().getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult == null){
                    listener.onFailure(context.getResources().getString(R.string.add_trend_failure));
                } else {
                    if(restResult.isSuccess()){
                        listener.onSuccess();
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
