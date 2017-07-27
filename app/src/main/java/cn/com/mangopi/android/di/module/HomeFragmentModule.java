package cn.com.mangopi.android.di.module;

import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.model.data.AdvertModel;
import cn.com.mangopi.android.model.data.BulletinModel;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.HomePresenter;
import cn.com.mangopi.android.ui.fragment.HomeFragment;

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
        return new HomePresenter(new CourseModel(), new BulletinModel(), new AdvertModel(), fragment);
    }
}
