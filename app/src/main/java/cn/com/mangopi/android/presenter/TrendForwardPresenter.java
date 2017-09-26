package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.ui.viewlistener.TrendForwardListener;
import rx.Subscription;

public class TrendForwardPresenter extends BasePresenter {

    TrendModel trendModel;
    TrendForwardListener trendForwardListener;

    public TrendForwardPresenter(TrendForwardListener trendForwardListener) {
        this.trendForwardListener = trendForwardListener;
        this.trendModel = new TrendModel();
    }

    public void forwardTrend(){
        Context context = trendForwardListener.currentContext();
        Subscription subscription = trendModel.forwardTrend(trendForwardListener.getContent(), trendForwardListener.getForwardId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            trendForwardListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                trendForwardListener.onForwardSuccess();
                            } else {
                                trendForwardListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
