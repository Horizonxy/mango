package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.FoundFragmentModule;
import com.mango.ui.fragment.FoundFragment;

import dagger.Component;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@FragmentScope
@Component(modules = FoundFragmentModule.class)
public interface FoundFragmentComponent {

    FoundFragment inject(FoundFragment fragment);
}
