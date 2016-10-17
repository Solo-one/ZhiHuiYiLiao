package com.example.asus.zhihuiyiliao.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2016/8/20.
 */
public class NetRequest extends StringRequest {

    public NetRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        init();
    }

    public NetRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        init();
    }

    private Priority priority = Priority.HIGH; //设置优先级
    private Map<String, String> headers;//请求头
    private Map<String, String> params;//请求参数

    //初始化全局变量
    private void init() {
        priority = Priority.NORMAL;
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public NetRequest setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    //请求头 重写该方法
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    public NetRequest addRequestHeadrs(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    //请求参数 重写该方法
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public NetRequest addRequestParams(String key, String value) {
        this.params.put(key,value);
        return this;
    }

    @Override
    public NetRequest setTag(Object tag) {
        super.setTag(tag);
        return this;
    }

    //调用VolleyUtil.start(this)  将请求对象NetRequest 加入到 请求队列
    public void start(){
        VolleyUtil.start(this);
    }

    /**
     * 静态内部类 Builder
     * 设计模式  构造者模式
     */
    public static class Builder{
        private String url;
        private int method;
        private CallBack callBack;

        /**
         * 设置url
         * @param url
         * @return
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;//返回 Builder 对象 实现链式 编程赋值
        }

        /**
         * 设置请求方式
         * @param method
         * @return
         */
        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        /**
         * 请求监听
         * @param callBack
         * @return
         */
        public Builder setCallBack(CallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        /**
         * 构建请求
         * @return
         */
        public NetRequest build() {

            NetRequest nr = new NetRequest(method, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (callBack != null) {
                        callBack.onSuccess(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (callBack != null) {
                        callBack.onErrer(error);
                    }
                }
            });

            return nr;
        }

    }
}
