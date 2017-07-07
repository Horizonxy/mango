package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.HomeFragmentModule;
import com.mango.ui.fragment.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {

    HomeFragment inject(HomeFragment fragment);
}
