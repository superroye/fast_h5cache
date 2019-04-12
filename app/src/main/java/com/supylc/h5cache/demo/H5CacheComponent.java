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
                    @Override
                    public void fillUrlInFilter(Set<String> urlStartRegex) {
                        urlStartRegex.add("^(http)[s]?://(\\w)+.zuzuche.((com|net)/?)$");
                        urlStartRegex.add("^(http)[s]?://(\\w)+.zuzuche.(com|net)/account/order.(php)$");
                        urlStartRegex.add("^(http)[s]?://(\\w)+.zuzuche.(com|net)/w/voucher.(php)$");
                        urlStartRegex.add("^(http)[s]?://(\\w)+.zuzuche.(com|net)/w/book/order.(php)$");
                        //^(http)[s]?://(\w)+.zuzuche.(com|net)/w/select/select_city.(php)$
                    }

                    @Override
                    public void fillExtensionInFilter(Set<String> extensions) {

                    }

                    @Override
                    public int cacheMode() {
                        return CacheFilter.CACHE_ALL;
                    }
                }).build();
    }
}
