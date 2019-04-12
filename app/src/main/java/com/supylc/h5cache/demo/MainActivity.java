package com.supylc.h5cache.demo;

import android.content.BroadcastReceiver;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.supylc.h5cache.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    WebView fastWebView;
    Button submit;
    EditText input;
    BroadcastReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fastWebView = findViewById(R.id.fast_web_view);
        submit = findViewById(R.id.submit);
        input = findViewById(R.id.input);

        //input.setText("http://m.zuzuche.net/");
        input.setText("http://m.zuzuche.net");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastWebView.loadUrl(input.getText().toString());
            }
        });

        webViewInit();

        fastWebView.loadUrl(input.getText().toString());

        WebView.setWebContentsDebuggingEnabled(true);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (NetworkUtils.isAvailable(fastWebView.getContext())) {
//                    Toast.makeText(context, "网络Good", Toast.LENGTH_SHORT).show();
//                    fastWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//                } else {
//                    Toast.makeText(context, "网络Bad", Toast.LENGTH_SHORT).show();
//                    fastWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//                }
//            }
//        };
//        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    void webViewInit() {
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
