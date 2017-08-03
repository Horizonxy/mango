package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.BulletinModel;
import cn.com.mangopi.android.ui.viewlistener.BulletinDetailListener;
import rx.Subscription;

public class BulletinPresenter extends BasePresenter {
    BulletinModel bulletinModel;
    BulletinDetailListener detailListener;

    public BulletinPresenter(BulletinModel bulletinModel, BulletinDetailListener detailListener) {
        this.bulletinModel = bulletinModel;
        this.detailListener = detailListener;
    }

    public void getBulletin(){
        Context context = detailListener.currentContext();
        Subscription subscription = bulletinModel.getBulletin(detailListener.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<BulletinBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            detailListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<BulletinBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                detailListener.onSuccess(restResult.getData());
                            } else {
                                detailListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
