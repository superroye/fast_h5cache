package com.supylc.h5cache.demo;

import android.app.Application;

import com.supylc.h5cache.CacheFilter;

import java.io.File;
import java.util.Set;

/**
 * @author Roye
 * @date 2019/4/4
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        H5CacheComponent.init(this);
    }
}
