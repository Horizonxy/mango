package com.mango.di.module;

import android.app.Activity;

import com.mango.di.ActivityScope;
import com.mango.model.bean.RegistBean;
import com.mango.model.data.MemberModel;
import com.mango.presenter.LoginPresenter;
import com.mango.ui.viewlistener.LoginListener;

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
