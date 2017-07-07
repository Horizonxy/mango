package com.mango.di.module;

import com.mango.di.FragmentScope;
import com.mango.model.data.AdvertModel;
import com.mango.presenter.HomePresenter;
import com.mango.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {

    HomeFragment fragment;

    public HomeFragmentModule(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public HomePresenter provideHomePresenter(){
        return new HomePresenter(new AdvertModel(), fragment);
    }
}
