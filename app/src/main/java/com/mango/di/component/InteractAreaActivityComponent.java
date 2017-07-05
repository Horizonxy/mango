package com.mango.di.component;

import com.mango.di.ActivityScope;
import com.mango.di.module.InteractAreaActivityModule;
import com.mango.ui.activity.InteractAreaActivity;

import dagger.Component;

/**
 * @author 蒋先明
 * @date 2017/7/5
 */

@ActivityScope
@Component(modules = InteractAreaActivityModule.class)
public interface InteractAreaActivityComponent {

    InteractAreaActivity inject(InteractAreaActivity activity);
}
