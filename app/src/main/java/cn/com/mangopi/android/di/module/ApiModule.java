package cn.com.mangopi.android.di.module;

import javax.inject.Singleton;

import cn.com.mangopi.android.model.api.ApiService;
import cn.com.mangopi.android.util.RetrofitUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Singleton
    @Provides
    public ApiService provideApiService(){
        return  RetrofitUtil.createRetrofit().create(ApiService.class);
    }
}
