package com.mango.di.component;

import com.mango.di.FragmentScope;
import com.mango.di.module.FoundFragmentModule;
import com.mango.di.module.TeacherFragmentModule;
import com.mango.ui.fragment.FoundFragment;
import com.mango.ui.fragment.TecaherFragment;

import dagger.Component;

@FragmentScope
@Component(modules = TeacherFragmentModule.class)
public interface TeacherFragmentComponent {

    TecaherFragment inject(TecaherFragment fragment);
}
