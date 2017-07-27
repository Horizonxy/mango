package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.MyClassesFragmentModule;
import cn.com.mangopi.android.ui.fragment.MyClassesFragment;

import dagger.Component;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@FragmentScope
@Component(modules = MyClassesFragmentModule.class)
public interface MyClassesFragmentComponent {

    MyClassesFragment inject(MyClassesFragment fragment);
}
