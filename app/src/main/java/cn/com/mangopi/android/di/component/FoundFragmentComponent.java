package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.FoundFragmentModule;
import cn.com.mangopi.android.ui.fragment.FoundFragment;

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
