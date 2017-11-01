package cn.com.mangopi.android.presenter;

import android.content.Context;
import android.text.TextUtils;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.LoginListener;
import cn.com.mangopi.android.util.Inputvalidator;

import cn.com.mangopi.android.util.PreUtils;
import rx.Subscription;

public class LoginPresenter extends BasePresenter {

    MemberModel memberModel;
    LoginListener<RegistBean> viewListener;

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
        Subscription subscription = memberModel.getLoginVerifyCode(mobile, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<RegistBean>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    viewListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<RegistBean> restResult) {
                if(restResult != null && restResult.isFailure()){
                    if(restResult.getData() != null && "BIZ_ERR_LOGIN_SMSCODE_SEND".equals(restResult.getError_code())){
                        String sessId = restResult.getData().getLst_sessid();
                        PreUtils.putString(context, Constants.SESS_ID, sessId);
                        viewListener.onSuccess(null);
                    } else {
                        viewListener.onFailure(restResult.getRet_msg());
                    }
                } else {
                    viewListener.onFailure(context.getString(R.string.get_verify_code_failure));
                }
            }
        });
        addSubscription(subscription);
    }

    public void quickLogin(String smsCode){
        Context context = viewListener.currentContext();
        if(TextUtils.isEmpty(smsCode)){
            viewListener.onFailure(context.getString(R.string.please_input_smscode));
            return;
        }
        if(!hasNet()){
            return;
        }
        Subscription subscription = memberModel.quickLogin(viewListener.getLoginParams(), Application.application.getSessId(), new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<RegistBean>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
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

    public void  wxLogin(String openId){
        Context context = viewListener.currentContext();
        Subscription subscription = memberModel.wxLogin(openId, new CreateLoading(context), new BaseLoadingSubscriber<RestResult<RegistBean>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
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
                    /*if(member.getMobile() == null){
                        viewListener.startRegist();
                    } else */if(member.getGender() == null || TextUtils.isEmpty(member.getNick_name())){
                        viewListener.startSetNickName();
                    } else {
                        viewListener.startMain();
                    }
                } else {
                    if(restResult != null){
                        if("BIZ_ERR_MEMBER_NONEXISTENT".equals(restResult.getError_code())){
                            viewListener.startRegist(openId, "");
                        } else {
                            viewListener.onFailure(restResult.getRet_msg());
                        }
                    } else {
                        viewListener.onFailure(context.getString(R.string.login_failure));
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void  wxLogin(String openId, String unionId){
        Context context = viewListener.currentContext();
        Subscription subscription = memberModel.wxLogin(openId, unionId, new CreateLoading(context), new BaseLoadingSubscriber<RestResult<RegistBean>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
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
                    /*if(member.getMobile() == null){
                        viewListener.startRegist();
                    } else */if(member.getGender() == null || TextUtils.isEmpty(member.getNick_name())){
                        viewListener.startSetNickName();
                    } else {
                        viewListener.startMain();
                    }
                } else {
                    if(restResult != null){
                        if("BIZ_ERR_MEMBER_NONEXISTENT".equals(restResult.getError_code())){
                            viewListener.startRegist(openId, unionId);
                        } else {
                            viewListener.onFailure(restResult.getRet_msg());
                        }
                    } else {
                        viewListener.onFailure(context.getString(R.string.login_failure));
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
