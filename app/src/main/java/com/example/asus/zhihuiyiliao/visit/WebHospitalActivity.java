package com.example.asus.zhihuiyiliao.visit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class WebHospitalActivity extends SwipeBackActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_web_hospital);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        //初始化ProgressBar
        progressBar.setMax(110);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);


        //重写shouldOverrideUrlLoading（）方法 将网页交给WebView控件处理，
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//由Webview处理网页false 由系统浏览器处理
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);//加载时,进度条可见
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);//加载完成时，进度条隐藏
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);//可用 JavaScript脚本语言

        //实时设置进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        Intent intent = getIntent();//得到启动该Activity传过来的数据
        String url = intent.getStringExtra("hos");
        String URL = null;
        if (url.equals("")) {
            Toast.makeText(this,"抱歉,网站正在建设中...",Toast.LENGTH_SHORT).show();
        }else {
            String tmp = url.substring(0,7);
            if (tmp.equals("http://")){
                URL = url;
            } else {
                URL = "http://"+url;
            }

            Toast.makeText(this,"正在加载...",Toast.LENGTH_SHORT).show();
        }

        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(URL);//加载数据
        }

    }
}
