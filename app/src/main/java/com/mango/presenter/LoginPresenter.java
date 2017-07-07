package com.mango.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.mango.Application;
import com.mango.R;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.MemberModel;
import com.mango.ui.viewlistener.LoginListener;
import com.mango.util.Inputvalidator;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class LoginPresenter extends BasePresenter {

    MemberModel memberModel;
    LoginListener<RegistBean> viewListener;
    String sessId;

    public LoginPresenter(MemberModel memberModel, LoginListener<RegistBean> viewListener) {
        this.memberModel = memberModel;
        this.viewListener = viewListener;
    }

    public void getVerifyCode(String mobile){
        Context context = viewListener.currentContext();
        if(TextUtils.isEmpty(mobile)){
            viewListener.onFailure(context.getString(R.string.please_input_phone));
            return;
        } else if(!Inputvalidator.checkPhone(mobile)){
            viewListener.onFailure(context.getString(R.string.please_input_right_phone));
            return;
        }
        if(!hasNet()){
            return;
        }
        Subscription subscription = memberModel.getLoginVerifyCode(mobile, new Action0() {
            @Override
            public void call() {
                createLoading(context, context.getString(R.string.please_wait));
            }
        }, new Subscriber<RestResult<RegistBean>>() {
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
            public void onNext(RestResult<RegistBean> restResult) {
                if(restResult.isFailure()
                        && restResult.getData() != null
                        && "BIZ_ERR_LOGIN_SMSCODE_SEND".equals(restResult.getError_code())){
                    sessId = restResult.getData().getLst_sessid();
                    viewListener.onSuccess(null);
                } else {
                    viewListener.onFailure(context.getString(R.string.get_verify_code_failure));
                }
            }
        });
        addSubscription(subscription);
    }

    public void quickLogin(String mobile, String smsCode){
        Context context = viewListener.currentContext();
        if(TextUtils.isEmpty(smsCode)){
            viewListener.onFailure(context.getString(R.string.please_input_smscode));
            return;
        }
        if(!hasNet()){
            return;
        }
        Subscription subscription = memberModel.quickLogin(mobile, smsCode, sessId, new Action0() {
            @Override
            public void call() {
                createLoading(context, context.getString(R.string.please_wait));
            }
        }, new Subscriber<RestResult<RegistBean>>() {
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
            public void onNext(RestResult<RegistBean> restResult) {
                if(restResult.isSuccess() && restResult.getData() != null){
                    MemberBean member = restResult.getData().getMember();
                    String sessId = restResult.getData().getLst_sessid();
                    Application.application.saveMember(member, sessId);
                    if(member.getGender() == null || TextUtils.isEmpty(member.getNick_name())){
                        viewListener.startSetNickName();
                    } else {
                        viewListener.startMain();
                    }
                } else {
                    if(restResult != null){
                        viewListener.onFailure(restResult.getRet_msg());
                    } else {
                        viewListener.onFailure(context.getString(R.string.login_failure));
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
