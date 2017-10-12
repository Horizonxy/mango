package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.CalcPriceBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CalcPriceModel;
import cn.com.mangopi.android.ui.viewlistener.CalcPriceListener;
import rx.Subscription;

public class CalcPricePresenter extends BasePresenter {

    CalcPriceModel calcPriceModel;
    CalcPriceListener calcPriceListener;

    public CalcPricePresenter(CalcPriceListener calcPriceListener) {
        this.calcPriceListener = calcPriceListener;
        this.calcPriceModel = new CalcPriceModel();
    }

    public void calcPrice(){
        Context context = calcPriceListener.currentContext();
        Subscription subscription = calcPriceModel.calcPrice(calcPriceListener.getCalcMap(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<CalcPriceBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            calcPriceListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<CalcPriceBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                calcPriceListener.onCalcSuccess(restResult.getData());
                            }else {
                                calcPriceListener.onCalcFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
