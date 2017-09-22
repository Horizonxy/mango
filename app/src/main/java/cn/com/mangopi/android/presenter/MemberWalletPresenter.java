package cn.com.mangopi.android.presenter;


import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.MemeberWalletListener;
import rx.Subscription;

public class MemberWalletPresenter extends BasePresenter {

    MemberModel memberModel;
    MemeberWalletListener listener;

    public MemberWalletPresenter(MemberModel memberModel, MemeberWalletListener listener) {
        this.memberModel = memberModel;
        this.listener = listener;
    }

    public void getWallet(){
        Context context = listener.currentContext();
        Subscription subscription = memberModel.getWallet(new CreateLoading(context, context.getResources().getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<MemberWalletBean>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<MemberWalletBean> restResult) {
                super.onNext(restResult);
                if(restResult == null){
                    listener.onFailure(context.getResources().getString(R.string.get_member_wallet_failure));
                } else {
                    if(restResult.isSuccess()){
                        listener.onWalletSuccess(restResult.getData());
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getCardList(){
        Context context = listener.currentContext();
        Subscription subscription = memberModel.getCardList(new CreateLoading(context, context.getResources().getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<List<MemberCardBean>>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<MemberCardBean>> restResult) {
                super.onNext(restResult);
                if(restResult == null){
                    listener.onFailure(context.getResources().getString(R.string.get_member_wallet_failure));
                } else {
                    if(restResult.isSuccess()){
                        listener.onCardListSuccess(restResult.getData());
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void delCard(MemberCardBean card){
        Context context = listener.currentContext();
        Subscription subscription = memberModel.delCard(card.getId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e !=null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        listener.onDelSuccess(card);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
