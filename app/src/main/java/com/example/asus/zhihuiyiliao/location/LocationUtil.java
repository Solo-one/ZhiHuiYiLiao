package com.example.asus.zhihuiyiliao.location;


import android.app.Activity;
import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.example.asus.zhihuiyiliao.R;

/**
 * Created by asus on 2016/10/6.
 */
public class LocationUtil {

    public static AMapLocationClient configLocation(Context context) {

        AMapLocationClient locationClient=null;
        AMapLocationClientOption clientOption;

        locationClient=new AMapLocationClient(context);
        clientOption=new AMapLocationClientOption();

        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        clientOption.setOnceLocation(false);
        clientOption.setNeedAddress(true);
        clientOption.setInterval(20000);
        clientOption.setHttpTimeOut(30000);

        locationClient.setLocationOption(clientOption);
        locationClient.startLocation();

        return locationClient;
    }
}
