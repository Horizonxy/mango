package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.di.module.InteractAreaActivityModule;
import cn.com.mangopi.android.ui.activity.InteractAreaActivity;

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
