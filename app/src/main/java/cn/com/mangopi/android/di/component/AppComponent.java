package cn.com.mangopi.android.di.component;

import javax.inject.Singleton;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.di.module.ApiModule;
import cn.com.mangopi.android.di.module.AppModule;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppComponent {
    Application inject(Application application);
}
