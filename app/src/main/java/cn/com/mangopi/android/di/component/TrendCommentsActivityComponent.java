package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.di.module.TrendCommentsActivityModule;
import cn.com.mangopi.android.ui.activity.TrendCommentsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = TrendCommentsActivityModule.class)
public interface TrendCommentsActivityComponent {

    TrendCommentsActivity inject(TrendCommentsActivity activity);
}
