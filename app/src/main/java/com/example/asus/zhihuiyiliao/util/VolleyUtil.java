package com.example.asus.zhihuiyiliao.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.http.NetRequest;

/**
 * 网络请求封装类
 * Volley 网络加载库
 * Created by asus on 2016/8/20.
 */
public class VolleyUtil {
    //请求队列
    private static RequestQueue requestQueue;

    //初始化请求队列
    public static void init(Context mContext) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext);//初始化requestQueue 对象 只实例化一次
        }
    }

    /**
     * GET  请求
     * @param url
     * @return
     */
    public static NetRequest.Builder get(String url) {
        NetRequest.Builder builder = new NetRequest.Builder();
        builder.setMethod(Request.Method.GET).setUrl(url);
        return builder;
    }

    /**
     * POST 请求
     * @param url
     * @return
     */
    public static NetRequest.Builder post(String url) {
        NetRequest.Builder builder = new NetRequest.Builder();
        builder.setMethod(Request.Method.POST).setUrl(url);
        return builder;
    }

    //将请求对象 加入到 请求队列
    public static void start(NetRequest nr){
        requestQueue.add(nr);
    }

    public static void cancel(Object tag){
        requestQueue.cancelAll(tag);//取消请求
    }

    public static void cancel(RequestQueue.RequestFilter filter) {
        requestQueue.cancelAll(filter);
    }
}
