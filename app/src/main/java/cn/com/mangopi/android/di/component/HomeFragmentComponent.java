package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.HomeFragmentModule;
import cn.com.mangopi.android.ui.fragment.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {

    HomeFragment inject(HomeFragment fragment);
}
