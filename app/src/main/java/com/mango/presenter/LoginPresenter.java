package com.mango.presenter;

import android.text.TextUtils;

import com.mango.R;
import com.mango.model.bean.RestResult;
import com.mango.model.data.MemberModel;
import com.mango.ui.viewlistener.LoginListener;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class LoginPresenter extends BasePresenter {

    MemberModel memberModel;
    LoginListener<Object> viewListener;

    public LoginPresenter(MemberModel memberModel, LoginListener<Object> viewListener) {
        this.memberModel = memberModel;
        this.viewListener = viewListener;
    }

    public void getVerifyCode(String mobile){
        if(TextUtils.isEmpty(mobile)){
            viewListener.onFailure("手机号不能未空");
            return;
        }
        Subscription subscription = memberModel.getRegistVerifyCode(mobile, new Action0() {
            @Override
            public void call() {
                createLoading(viewListener.currentContext(), viewListener.currentContext().getString(R.string.please_wait));
            }
        }, new Subscriber<RestResult<Object>>() {
            @Override
            public void onCompleted() {
                dimissLoading(viewListener.currentContext());
            }

            @Override
            public void onError(Throwable e) {
                dimissLoading(viewListener.currentContext());
                if(e != null) {
                    viewListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> objectRestResult) {
                if(objectRestResult.isFailure() && "BIZ_ERR_LOGIN_SMSCODE_SEND".equals(objectRestResult.getError_code())){
                    viewListener.onSuccess("验证码已发送至手机");
                } else {
                    viewListener.onFailure("获取验证码失败，请稍后重试");
                }
            }
        });
        addSubscription(subscription);
    }
}
