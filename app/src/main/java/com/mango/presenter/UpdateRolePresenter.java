package com.mango.presenter;

import android.content.Context;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.RestResult;
import com.mango.model.data.MemberModel;
import com.mango.ui.viewlistener.BaseViewListener;
import com.mango.ui.viewlistener.UpdateRoleListener;

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
}
