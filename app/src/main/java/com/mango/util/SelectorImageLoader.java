package com.mango.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.mango.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

public class SelectorImageLoader implements ImageLoader {

    Bitmap.Config mImageConfig;
    DisplayImageOptions options;

    public SelectorImageLoader() {
        this(Bitmap.Config.RGB_565);
    }

    public SelectorImageLoader(Bitmap.Config config) {
        this.mImageConfig = config;
        this.options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(true)
                .bitmapConfig(mImageConfig)
                .build();
    }

    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {

        ImageSize imageSize = new ImageSize(width, height);
        Application.application.getImageLoader().displayImage("file://" + path, new ImageViewAware(imageView), options, imageSize, null, null);
    }

    @Override
    public void clearMemoryCache() {

    }
}
