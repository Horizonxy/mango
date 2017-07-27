package cn.com.mangopi.android.di.component;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.module.TeacherFragmentModule;
import cn.com.mangopi.android.ui.fragment.TecaherFragment;

import dagger.Component;

@FragmentScope
@Component(modules = TeacherFragmentModule.class)
public interface TeacherFragmentComponent {

    TecaherFragment inject(TecaherFragment fragment);
}
