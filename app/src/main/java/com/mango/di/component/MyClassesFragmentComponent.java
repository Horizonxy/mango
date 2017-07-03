package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.MyClassesFragmentModule;
import com.mango.ui.fragment.MyClassesFragment;

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
