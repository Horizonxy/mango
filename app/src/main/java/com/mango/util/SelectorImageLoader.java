package com.mango.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.mango.Application;
import com.mango.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yancy.gallerypick.widget.GalleryImageView;

public class SelectorImageLoader implements com.yancy.gallerypick.inter.ImageLoader {

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
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        ImageSize imageSize = new ImageSize(width, height);
        Application.application.getImageLoader().displayImage(Constants.FILE_PREFIX + path, new ImageViewAware(galleryImageView), options, imageSize, null, null);
    }

    @Override
    public void clearMemoryCache() {

    }
}
