package cn.com.mangopi.android.presenter;

import java.util.Date;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.ui.viewlistener.TrendUpdateListener;
import cn.com.mangopi.android.util.DateUtils;
import rx.Subscription;

public class TrendUpdatePresenter extends BasePresenter {

    TrendModel trendModel;
    TrendUpdateListener trendUpdateListener;

    public TrendUpdatePresenter(TrendUpdateListener trendUpdateListener) {
        this.trendUpdateListener = trendUpdateListener;
        this.trendModel = new TrendModel();
    }

    public void trendUpdate() {
        Subscription subscription = trendModel.trendUpdate(DateUtils.sysDate(), new BaseSubscriber<RestResult<Object>>() {
            @Override
            public void onNext(RestResult<Object> restResult) {
                if (restResult != null && restResult.isSuccess()) {
                    trendUpdateListener.onHasTrend(true);
                }
            }
        });
        addSubscription(subscription);
    }
}
