package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.MyFragmentModule;
import com.mango.ui.fragment.MyFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MyFragmentModule.class)
public interface MyFragmentComponent {

    MyFragment inject(MyFragment fragment);
}
