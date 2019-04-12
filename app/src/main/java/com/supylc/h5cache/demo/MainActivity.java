package com.supylc.h5cache.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.supylc.h5cache.utils.NetworkUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

    WebView fastWebView;
    Button submit;
    EditText input;
    BroadcastReceiver networkChangeReceiver;

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fastWebView = findViewById(R.id.fast_web_view);
        submit = findViewById(R.id.submit);
        input = findViewById(R.id.input);

        input.setText("http://www.baidu.com");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastWebView.loadUrl(input.getText().toString());
            }
        });

        rxPermissions = new RxPermissions(this);

        webViewInit();

        WebView.setWebContentsDebuggingEnabled(true);

        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(data -> {
                    fastWebView.loadUrl(input.getText().toString());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    void webViewInit() {
        fastWebView.setWebViewClient(new MyWebViewClient(fastWebView));
        WebSettings webSettings = fastWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("UTF-8");

        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getPath() + "/appcache");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(fastWebView, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        if (NetworkUtils.isAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }
}
