package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.di.module.LoginActivityModule;
import cn.com.mangopi.android.ui.activity.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginActivityModule.class)
public interface LoginActivityComponent {

    LoginActivity inject(LoginActivity activity);
}
