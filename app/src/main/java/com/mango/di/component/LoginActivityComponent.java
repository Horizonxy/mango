package com.mango.di.component;

import com.mango.di.ActivityScope;
import com.mango.di.module.LoginActivityModule;
import com.mango.ui.activity.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginActivityModule.class)
public interface LoginActivityComponent {

    LoginActivity inject(LoginActivity activity);
}
