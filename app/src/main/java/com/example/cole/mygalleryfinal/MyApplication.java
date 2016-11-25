package com.example.cole.mygalleryfinal;

import android.app.Application;

import com.example.cole.mygalleryfinal.utils.ImageFileUtil;

/**
 * 创建人: cole
 * 创建时间: 2016/11/25 下午9:53
 * 描述:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageFileUtil.initImageFileUtil(this, null);
    }


}
