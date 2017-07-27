package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.MyFragmentModule;
import cn.com.mangopi.android.ui.fragment.MyFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MyFragmentModule.class)
public interface MyFragmentComponent {

    MyFragment inject(MyFragment fragment);
}
