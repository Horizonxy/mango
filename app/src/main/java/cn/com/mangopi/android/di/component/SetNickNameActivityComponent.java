package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.di.module.SetNickNameActivityModule;
import cn.com.mangopi.android.ui.activity.SetNickNameActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SetNickNameActivityModule.class)
public interface SetNickNameActivityComponent {

    SetNickNameActivity inject(SetNickNameActivity activity);
}
