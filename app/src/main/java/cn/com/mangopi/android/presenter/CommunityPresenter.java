package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.CommunityClassifyBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CommunityModel;
import cn.com.mangopi.android.ui.viewlistener.CommunityListener;
import rx.Subscription;

public class CommunityPresenter extends BasePresenter {

    CommunityModel communityModel;
    CommunityListener communityListener;

    public CommunityPresenter(CommunityModel communityModel, CommunityListener communityListener) {
        this.communityModel = communityModel;
        this.communityListener = communityListener;
    }

    public void getTypeList(){
        Context context = communityListener.currentContext();
        Subscription subscription = communityModel.getCommunityTypeList(new CreateLoading(context), new BaseLoadingSubscriber<RestResult<List<CommunityTypeBean>>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    communityListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<CommunityTypeBean>> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        communityListener.onTypeListSuccess(restResult.getData());
                    } else {
                        communityListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getClassifyList(){
        Context context = communityListener.currentContext();
        Subscription subscription = communityModel.getCommunityClassifyList(new CreateLoading(context), new BaseLoadingSubscriber<RestResult<List<CommunityClassifyBean>>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    communityListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<CommunityClassifyBean>> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        communityListener.onClassifyListSuccess(restResult.getData());
                    } else {
                        communityListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
