package com.example.asus.zhihuiyiliao.consulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.MyScrollView;
import com.example.asus.zhihuiyiliao.dao.NewsOrmDao;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.News;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailActivity extends MyBaseActivity {

    private int id;
    private String url = "http://apis.baidu.com/tngou/info/list";

    private boolean isCollec = false;//默认没有收藏
    NewsOrmDao dao;

    @BindView(R.id.detail)
    WebView detail;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.liulan)
    TextView liulan;
    @BindView(R.id.comment)
    TextView comment;

    @BindView(R.id.newsdetail)
    TextView collection;

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @BindView(R.id.footer)
    RelativeLayout footer;

    @BindView(R.id.myscrollview)
    MyScrollView myscrollview;

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        footer.setOnTouchListener(this);
        footer.setLongClickable(true);
        myscrollview.setGestureDetector(this.getmGestureDetector());

        dao = new NewsOrmDao(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 10);
        //组装URL地址
        String tmpUrl = intent.getStringExtra("url");
        url =  tmpUrl.substring(0,tmpUrl.length()-4)+"show";

        Bundle bundle = intent.getExtras();
        news = (News) bundle.get("news");

        title.setText(news.getTitle());
//        description.setText("     "+news.getDescription());
        liulan.setText((news.getCount() * 6 + news.getCount()) + "人浏览过");
        comment.setText("评论数:" + news.getCount());
        loadLatast();

        detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//由Webview处理网页false 由系统浏览器处理
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                progressBar.setVisibility(View.VISIBLE);//加载时,进度条可见
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                progressBar.setVisibility(View.GONE);//加载完成时，进度条隐藏
            }
        });

       /* WebSettings webSettings = detail.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);*/

        detail.getSettings().setDefaultTextEncodingName("UTF-8");
        detail.getSettings().setJavaScriptEnabled(true);//可用 JavaScript脚本语言

        //实时设置进度
        detail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                progressBar.setProgress(newProgress);
//                Log.i("jindu", newProgress + "**");
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(NewsDetailActivity.this);
                View view = LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.self_toast, null, false);
                TextView textView = (TextView) view.findViewById(R.id.show);

                if (isCollec == false) {
                    textView.setText("收藏成功");
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    collection.setText("取消收藏");
                    isCollec = true;//已经收藏
                } else {
                    textView.setText("取消收藏");
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    collection.setText("收藏");
                    isCollec = false;//取消收藏
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        News news = (News) intent.getExtras().get("news");
        boolean tf = dao.findNews(news.getTitle());
        if (tf == true) {
            collection.setText("取消收藏");
            isCollec = true;
        } else {
            collection.setText("收藏");
            isCollec = false;
        }
    }

    /**
     * 修改数据库
     */
    @Override
    protected void onPause() {
        super.onPause();
        //收藏成功
        Intent intent = getIntent();
        if (isCollec == true) {
            News news = (News) intent.getExtras().get("news");

            boolean tf = dao.findNews(news.getTitle());

            if (tf == false) {
                dao.addNews(news);
            }

        } else {
            News news = (News) intent.getExtras().get("news");
            dao.deleteNews(news.getTitle());
        }
    }


    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            //注意编码格式
            detail.loadData(parserJSON(response), "text/html;charset=UTF-8", null);
        }

        @Override
        public void onErrer(VolleyError error) {

        }

    }


    //下拉加载最新数据
    private void loadLatast() {
        VolleyUtil.get(url+"?id=" + id)
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }


    /**
     * 初始化数据
     *
     * @return
     */
    private String parserJSON(String s) {

        String message = null;
        try {
            JSONObject result = new JSONObject(s);
            message = result.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

}
