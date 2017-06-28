package com.mango.di.component;

import com.mango.Application;
import com.mango.di.module.ApiModule;
import com.mango.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppComponent {
    Application inject(Application application);
}
