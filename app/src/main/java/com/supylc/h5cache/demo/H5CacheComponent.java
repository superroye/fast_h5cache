package com.supylc.h5cache.demo;

import android.content.Context;

import com.supylc.h5cache.CacheFilter;
import com.supylc.h5cache.H5CacheClient;

import java.io.File;
import java.util.Set;

/**
 * @author Roye
 * @date 2019/4/8
 */
public class H5CacheComponent {


    public static void init(Context context) {
        H5CacheClient.setDebug(true);
        H5CacheClient.with(context)
                .cacheDir(context.getFilesDir() + File.separator + "fastWebviewCache")
                .cacheSize(200 * 1024 * 1024)
                .connectTimeoutSecond(10)
                .readTimeoutSecond(10)
                .filter(new CacheFilter() {
                    //拦截URL, 忽略queryString
                    @Override
                    public void fillUrlInFilter(Set<String> urlStartRegex) {
                        urlStartRegex.add("^(http)[s]?://(\\w)+.taobao.((com|net)/?)$");
                        urlStartRegex.add("^(http)[s]?://(\\w)+.taobao.(com|net)/account/order.(php)$");
                    }

                    @Override
                    public void fillExtensionInFilter(Set<String> extensions) {

                    }

                    @Override
                    public int cacheMode() {
                        //缓存开关，CACHE_NONE 关闭，CACHE_ALL 全开，CACHE_FILTER 根据过滤条件
                        return CacheFilter.CACHE_ALL;
                    }
                }).build();
    }
}
