package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.MyOrderListFragmentModule;
import com.mango.ui.fragment.MyOrderListFragment;

import dagger.Component;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@FragmentScope
@Component(modules = MyOrderListFragmentModule.class)
public interface MyOrderListFragmentComponent {

    MyOrderListFragment inject(MyOrderListFragment fragment);
}
