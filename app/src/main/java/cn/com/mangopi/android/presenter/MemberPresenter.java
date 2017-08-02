package cn.com.mangopi.android.presenter;

import android.content.Context;
import android.text.TextUtils;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.fragment.MyFragment;
import cn.com.mangopi.android.ui.viewlistener.AddBlankCardListener;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.SetNickNameListener;
import cn.com.mangopi.android.util.AppUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class MemberPresenter extends BasePresenter {

    MemberModel memberModel;
    BaseViewListener listener;

    public MemberPresenter(MemberModel memberModel, BaseViewListener listener) {
        this.memberModel = memberModel;
        this.listener = listener;
    }

    public void updateMember(){
        SetNickNameListener listener = (SetNickNameListener) this.listener;
        String nickName = listener.getNickName();
        int gender = listener.getGender();
        Context context = listener.currentContext();
        if(gender != Constants.GENDER_MAN && gender != Constants.GENDER_FEMALE){
            AppUtils.showToast(context, context.getResources().getString(R.string.please_select_gender));
            return;
        }
        if(TextUtils.isEmpty(nickName)){
            AppUtils.showToast(context, context.getResources().getString(R.string.please_input_nick_name));
            return;
        }
        String sessId = Application.application.getSessId();
        Subscription subscription = memberModel.updateMember(nickName, gender, sessId, new Action0() {
            @Override
            public void call() {
                createLoading(context, context.getString(R.string.please_wait));
            }
        }, new Subscriber<RestResult<Object>>() {
            @Override
            public void onCompleted() {
                dimissLoading(listener.currentContext());
            }

            @Override
            public void onError(Throwable e) {
                dimissLoading(listener.currentContext());
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<Object> restResult) {
                if(restResult == null){
                    listener.onFailure(context.getResources().getString(R.string.update_member_failure));
                } else {
                    if(restResult.isSuccess()){
                        MemberBean member = Application.application.getMember();
                        member.setNick_name(nickName);
                        member.setGender(gender);
                        Application.application.saveMember(member, sessId);
                        listener.onSuccess();
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getMember(){
        MyFragment listener = (MyFragment) this.listener;
        Subscription subscription = memberModel.getMember(listener.getMemberId(), new Action0() {
            @Override
            public void call() {

            }
        }, new Subscriber<RestResult<MemberBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RestResult<MemberBean> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    listener.onSuccess(restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }

    public void addBlankCard(){
        AddBlankCardListener listener = (AddBlankCardListener) this.listener;
        Context context = listener.currentContext();
        Subscription subscription = memberModel.addBlankCard(listener.getBlankName(), listener.getCardNo(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            listener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                listener.onSuccess();
                            } else {
                                listener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
