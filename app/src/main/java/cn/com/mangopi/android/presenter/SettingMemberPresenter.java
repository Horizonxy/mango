package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.ProfileSettingListener;
import rx.Subscription;

public class SettingMemberPresenter extends BasePresenter {

    MemberModel memberModel;
    ProfileSettingListener settingListener;

    public SettingMemberPresenter(MemberModel memberModel, ProfileSettingListener settingListener) {
        this.memberModel = memberModel;
        this.settingListener = settingListener;
    }

    public void settingMember() {
        Context context = settingListener.currentContext();
        Subscription subscription = memberModel.settingMember(settingListener.getMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            settingListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                settingListener.onSuccess();
                            } else {
                                settingListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
