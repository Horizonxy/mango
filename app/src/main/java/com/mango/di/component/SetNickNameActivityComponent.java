package com.mango.di.component;

import com.mango.di.ActivityScope;
import com.mango.di.module.SetNickNameActivityModule;
import com.mango.ui.activity.SetNickNameActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SetNickNameActivityModule.class)
public interface SetNickNameActivityComponent {

    SetNickNameActivity inject(SetNickNameActivity activity);
}
