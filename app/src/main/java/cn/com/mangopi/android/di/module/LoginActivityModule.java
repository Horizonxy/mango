package cn.com.mangopi.android.di.module;

import android.app.Activity;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.LoginPresenter;
import cn.com.mangopi.android.ui.viewlistener.LoginListener;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    Activity activity;

    public LoginActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public LoginPresenter provideLoginPresenter(){
        LoginPresenter loginPresenter = new LoginPresenter(new MemberModel(), (LoginListener<RegistBean>) activity);
        loginPresenter.setContext(activity);
        return loginPresenter;
    }
}
