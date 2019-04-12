package com.supylc.h5cache.demo;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.supylc.h5cache.H5CacheClient;
import com.supylc.h5cache.WebViewRequestInterceptor;

/**
 * @author Roye
 * @date 2019/4/2
 */
public class MyWebViewClient extends android.webkit.WebViewClient {

    WebViewRequestInterceptor webViewRequestInterceptor;

    public MyWebViewClient(WebView webView) {
        webViewRequestInterceptor = H5CacheClient.getWebViewInterceptor(webView);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (webViewRequestInterceptor.needCache(request.getUrl().toString())) {
                WebResourceResponse response = webViewRequestInterceptor.interceptRequest(request);
                if (response != null) {
                    return response;
                }
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        webViewRequestInterceptor.setOriginalUrl(url);
    }
}
