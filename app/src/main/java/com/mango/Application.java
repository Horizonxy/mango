package com.mango;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.mango.di.component.DaggerAppComponent;
import com.mango.di.module.ApiModule;
import com.mango.di.module.AppModule;
import com.mango.model.api.ApiService;
import com.mcxiaoke.bus.Bus;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

public class Application extends MultiDexApplication {

    public static Application application;

    @Inject
    Lazy<DisplayImageOptions> defaultOptions;
    @Inject
    Lazy<ImageLoader> imageLoader;
    @Inject
    Lazy<ApiService> apiService;

    @Inject
    Lazy<List<Activity>> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        //AppUtils.initCarsh(this);
        DaggerAppComponent.builder().apiModule(new ApiModule()).appModule(new AppModule(this)).build().inject(this);
        application = this;
        Logger.init(getResources().getString(R.string.app_name));

        Bus.getDefault().setStrictMode(true);
        Bus.getDefault().setDebug(BuildConfig.DEBUG);
    }

    public DisplayImageOptions getDefaultOptions() {
        return defaultOptions.get();
    }

    public ImageLoader getImageLoader() {
        return imageLoader.get();
    }

    public ApiService getApiService(){
        return apiService.get();
    }

    public void addActivity(Activity activity){
        activities.get().add(activity);
    }

    public void removeActivity(Activity activity){
        activities.get().remove(activity);
    }

    public void exit(){
        for (Activity activity : activities.get()){
            if(activity != null && !activity.isFinishing()){
                activity.finish();
            }
            System.exit(0);
        }
    }
}
