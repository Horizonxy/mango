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
        Subscription subscription = memberModel.getRegistVerifyCode(mobile, new Action0() {
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
            public void onNext(RestResult<RegistBean> objectRestResult) {
                if(objectRestResult.isFailure()
                        && objectRestResult.getData() != null
                        && "BIZ_ERR_LOGIN_SMSCODE_SEND".equals(objectRestResult.getError_code())){
                    sessId = objectRestResult.getData().getLst_sessid();
                    viewListener.onSuccess(null);
                } else {
                    viewListener.onFailure(context.getString(R.string.get_verify_code_failure));
                }
            }
        });
        addSubscription(subscription);
    }

    public void login(String mobile, String smsCode){
        Context context = viewListener.currentContext();
        if(TextUtils.isEmpty(smsCode)){
            viewListener.onFailure(context.getString(R.string.please_input_smscode));
            return;
        }
        if(!hasNet()){
            return;
        }
        Subscription subscription = memberModel.login(mobile, smsCode, sessId, new Action0() {
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
            public void onNext(RestResult<RegistBean> registBeanRestResult) {
                if(registBeanRestResult.isSuccess() && registBeanRestResult.getData() != null){
                    MemberBean member = registBeanRestResult.getData().getMember();
                    String sessId = registBeanRestResult.getData().getLst_sessid();
                    Application.application.saveMember(member, sessId);
                    if(member.getNick_name() == null || TextUtils.isEmpty(member.getNick_name())){
                        viewListener.startSetNickName();
                    } else {
                        viewListener.startMain();
                    }
                } else {
                    viewListener.onFailure(context.getString(R.string.login_failure));
                }
            }
        });
        addSubscription(subscription);
    }
}
