package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.UpdateRoleListener;
import rx.Subscription;

public class UpdateRolePresenter extends BasePresenter {

    MemberModel memberModel;
    BaseViewListener listener;

    public UpdateRolePresenter(MemberModel memberModel, BaseViewListener listener) {
        this.memberModel = memberModel;
        this.listener = listener;
    }

    public void checkUpgradeStudent(){
        Context context = listener.currentContext();
        Subscription  subscription = memberModel.checkUpgradeStudent(new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult != null){
                    if(restResult.isSuccess()){
                        ((UpdateRoleListener)listener).onSuccess(Constants.UserIndentity.STUDENT);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void checkUpgradeTutor(){
        Context context = listener.currentContext();
        Subscription  subscription = memberModel.checkUpgradeTutor(new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult != null){
                    if(restResult.isSuccess()){
                        ((UpdateRoleListener)listener).onSuccess(Constants.UserIndentity.TUTOR);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void checkUpgradeCompany(){
        Context context = listener.currentContext();
        Subscription  subscription = memberModel.checkUpgradeCompany(new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult != null){
                    if(restResult.isSuccess()){
                        ((UpdateRoleListener)listener).onSuccess(Constants.UserIndentity.COMPANY);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void checkUpgradeCommunity(){
        Context context = listener.currentContext();
        Subscription  subscription = memberModel.checkUpgradeCommunity(new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<Object>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                super.onNext(restResult);
                if(restResult != null){
                    if(restResult.isSuccess()){
                        ((UpdateRoleListener)listener).onSuccess(Constants.UserIndentity.COMMUNITY);
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void upgradeStudent(){
        UpdateRoleListener upgrdeListener = (UpdateRoleListener) listener;
        Context context = upgrdeListener.currentContext();
        Subscription subscription = memberModel.upgradeStudent(upgrdeListener.getUpgradeMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            upgrdeListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                upgrdeListener.onSuccess(Constants.UserIndentity.STUDENT);
                            } else {
                                upgrdeListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
