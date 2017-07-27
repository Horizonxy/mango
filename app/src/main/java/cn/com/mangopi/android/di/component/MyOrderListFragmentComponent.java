package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.MyOrderListFragmentModule;
import cn.com.mangopi.android.ui.fragment.MyOrderListFragment;

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
