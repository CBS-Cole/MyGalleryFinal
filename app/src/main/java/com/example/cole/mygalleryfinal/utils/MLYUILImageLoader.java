package com.example.cole.mygalleryfinal.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * 创建人: cole
 * 创建时间: 2016/11/11 下午3:35
 * 描述: 图片加载器
 */

public class MLYUILImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

    private Bitmap.Config mImageConfig;

    public MLYUILImageLoader() {
        this(Bitmap.Config.RGB_565);
    }

    public MLYUILImageLoader(Bitmap.Config config) {
        this.mImageConfig = config;
    }

    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {

        ImageCacheUtil.getInstance().initImageCacheData(activity);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .bitmapConfig(mImageConfig)
                .build();

        ImageLoader.getInstance().displayImage("file://" + path, new ImageViewAware(imageView), options, null, null);
    }

    @Override
    public void clearMemoryCache() {

    }
}
