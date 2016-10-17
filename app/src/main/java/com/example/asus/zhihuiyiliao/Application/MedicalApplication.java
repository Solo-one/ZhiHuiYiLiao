package com.example.asus.zhihuiyiliao.Application;

import android.app.Application;

import com.example.asus.zhihuiyiliao.SelfWidget.SelfToast;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

/**
 * Created by asus on 2016/8/20.
 */
public class MedicalApplication extends Application {

    public static SelfToast toast;//自定义Toast

    @Override
    public void onCreate() {
        super.onCreate();
        //网络请求
        VolleyUtil.init(this);//初始化requestQueue 对象 只实例化一次
        toast = new SelfToast(getApplicationContext());
    }
}
