package cn.com.mangopi.android.presenter;

import android.content.Context;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CouponModel;
import cn.com.mangopi.android.ui.viewlistener.FetchCouponListener;
import rx.Subscription;

public class FetchCouponPresenter extends BasePresenter {

    CouponModel couponModel;
    FetchCouponListener fetchCouponListener;

    public FetchCouponPresenter(FetchCouponListener fetchCouponListener) {
        this.fetchCouponListener = fetchCouponListener;
        this.couponModel = new CouponModel();
    }

    public void fetchCoupon(long couponId){
        Context context = fetchCouponListener.currentContext();
        Subscription subscription = couponModel.fetchCoupon(couponId, new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            fetchCouponListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                fetchCouponListener.onFetchCouponSuccess(couponId);
                            } else {
                                fetchCouponListener.onFetchCouponFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
