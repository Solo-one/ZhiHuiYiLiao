package com.example.asus.zhihuiyiliao.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.asus.zhihuiyiliao.R;

public class LocationMainActivity extends AppCompatActivity implements AMapLocationListener {

    AMapLocationClient locationClient=null;
    AMapLocationClientOption clientOption;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_main);
        String s=SHA1.sHA1(this);//调试版 SHA1 值
        Log.i("TAG", s);
        show=(TextView)findViewById(R.id.show);
        locationClient=new AMapLocationClient(getApplicationContext());
        clientOption=new AMapLocationClientOption();
        //设置定位参数
        setLocationClient();
        locationClient.setLocationOption(clientOption);
        locationClient.startLocation();
        locationClient.setLocationListener(this);
    }

    private void setLocationClient() {
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        clientOption.setOnceLocation(false);
        clientOption.setNeedAddress(true);
        clientOption.setInterval(20000);
        clientOption.setHttpTimeOut(30000);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null&&aMapLocation.getErrorCode()==0){
            Log.i("定位回调", aMapLocation.toString());
            show.setText(aMapLocation.toString());
        }
        if(aMapLocation.getErrorCode()!=0){
            Log.i("定位回调", "错误信息"+aMapLocation.getErrorCode());
            Log.i("定位回调", "错误信息"+aMapLocation.getErrorInfo());
        }
    }
}
