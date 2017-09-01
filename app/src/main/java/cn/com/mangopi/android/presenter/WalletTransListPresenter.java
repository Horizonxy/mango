package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.WalletTransListListener;
import rx.Subscription;
import rx.functions.Action1;

public class WalletTransListPresenter extends BasePresenter {

    MemberModel memberModel;
    WalletTransListListener transListListener;

    public WalletTransListPresenter(WalletTransListListener transListListener) {
        this.memberModel = new MemberModel();
        this.transListListener = transListListener;
    }

    public void walletTransList(){
        Subscription subscription = memberModel.walletTransList(transListListener.getPageNo(), new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                transListListener.onFailure(null);
            }
        }, new BaseSubscriber<RestResult<List<TransListBean>>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    transListListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<TransListBean>> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        transListListener.onTransListSuccess(restResult.getData());
                    } else {
                        transListListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
