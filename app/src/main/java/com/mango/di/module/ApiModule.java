package com.mango.di.module;

import com.mango.model.api.ApiService;
import com.mango.util.RetrofitUtil;
import javax.inject.Singleton;
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
