package com.mango.di.module;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;

import com.mango.Application;
import com.mango.Constants;
import com.mango.model.db.CommonDaoImpl;
import com.mango.util.FileUtils;
import com.mango.util.PermissionUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Singleton
    @Provides
    public List<Activity> provideListActivity(){
        return new ArrayList<Activity>();
    }

    @Singleton
    @Provides
    public ImageLoader provideImageLoader(){
        ImageLoader imageLoader = ImageLoader.getInstance();
        final String IMG_CACHE_PATH = FileUtils.getEnvPath(application, true, Constants.IMG_CACHE_DIR);
        File imgFile = new File(IMG_CACHE_PATH);
        if(!imgFile.exists()){
            imgFile.mkdirs();
        }
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(application)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(provideDisplayImageOptions())
                .imageDownloader(new BaseImageDownloader(application, 5 * 1000, 10 * 1000))
                .writeDebugLogs();
        if(PermissionUtils.checkPermissions(application, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})){
            builder.discCache(new UnlimitedDiskCache(imgFile));
        }
        imageLoader.init(builder.build());

        return imageLoader;
    }

    @Singleton
    @Provides
    public DisplayImageOptions provideDisplayImageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(2000))
                .build();
        return options;
    }

    @Singleton
    @Provides
    public CommonDaoImpl provideCommonDaoImpl(){
        return new CommonDaoImpl(application);
    }
}
