package cn.com.mangopi.android.di.module;

import android.support.v4.app.Fragment;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.MemberDetailListener;

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
        return new MemberPresenter(new MemberModel(), (MemberDetailListener)fragment);
    }

    @FragmentScope
    @Provides
    public MemberBean provideMemberBean(){
        return Application.application.getMember();
    }

}
