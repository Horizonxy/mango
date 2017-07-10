package com.mango.di.module;

import android.support.v4.app.Fragment;

import com.mango.Application;
import com.mango.di.FragmentScope;
import com.mango.model.bean.MemberBean;
import com.mango.model.data.MemberModel;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.viewlistener.MyFragmentListener;

import dagger.Module;
import dagger.Provides;

@Module
public class MyFragmentModule {

    Fragment fragment;

    public MyFragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    public MemberPresenter provideMemberPresenter(){
        return new MemberPresenter(new MemberModel(), (MyFragmentListener)fragment);
    }

    @FragmentScope
    @Provides
    public MemberBean provideMemberBean(){
        return Application.application.getMember();
    }

}
