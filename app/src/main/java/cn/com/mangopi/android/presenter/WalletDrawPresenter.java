package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.WalletDrawListener;
import rx.Subscription;

public class WalletDrawPresenter extends BasePresenter {

    MemberModel memberModel;
    WalletDrawListener walletDrawListener;

    public WalletDrawPresenter(MemberModel memberModel, WalletDrawListener walletDrawListener) {
        this.memberModel = memberModel;
        this.walletDrawListener = walletDrawListener;
    }

    public void walletDraw(){
        Context context = walletDrawListener.currentContext();
        Subscription subscription = memberModel.walletDraw(walletDrawListener.getCardId(), walletDrawListener.getAmount(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){

                });
        addSubscription(subscription);
    }
}
