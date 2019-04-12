package com.supylc.h5cache.demo;

import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

/**
 * @author Roye
 * @date 2019/4/2
 */
public class WebViewClient1 extends android.webkit.WebViewClient {

    public WebViewClient1(WebView webView){

    }

    @Override
    public void onLoadResource(WebView view, String url) {

        //android.util.Log.d("web","onLoadResource======"+url);
        super.onLoadResource(view, url);
    }


    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
        android.util.Log.d("web","shouldInterceptRequest======"+request.getUrl().toString());
        return super.shouldInterceptRequest(view, request);
    }


}
