package com.example.asus.zhihuiyiliao.location;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements AMapLocationListener, LocationSource {

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @OnClick(R.id.location)
    public void location(View v) {
        Intent intent = new Intent(this, LocationMainActivity.class);
        startActivity(intent);
    }

    AMapLocationClient locationClient = null;

    private MapView mMapView;
    private AMap mAMap;
    private UiSettings mUiSetting;

    private Hospital hospital;

    private double tmpJ;
    private double tmpW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        hospital = (Hospital) getIntent().getSerializableExtra("hospital");

        locationClient = LocationUtil.configLocation(getApplicationContext());
        locationClient.setLocationListener(this);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mAMap = mMapView.getMap();

        mUiSetting = mAMap.getUiSettings();

        mAMap.setTrafficEnabled(true);//显示实时交通状况
//        mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);//卫星地图模式
        mAMap.setLocationSource(this);
        mUiSetting.setMyLocationButtonEnabled(true);
        mUiSetting.setScaleControlsEnabled(true);
        mUiSetting.setCompassEnabled(true);

        Marker marker = mAMap.addMarker(new MarkerOptions()
                        .position(new LatLng(hospital.getY(), hospital.getX()))
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapicon)))
                        .draggable(true)
        );

        LatLng marker1 = new LatLng(hospital.getY(), hospital.getX());
        //设置中心点和缩放比例
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(12));


    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        locationClient.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            Log.i("定位回调", aMapLocation.toString());
            tmpJ = aMapLocation.getLongitude();
            tmpW = aMapLocation.getLatitude();
        }
        if (aMapLocation.getErrorCode() != 0) {
            Log.i("定位回调", "错误信息" + aMapLocation.getErrorCode());
            Log.i("定位回调", "错误信息" + aMapLocation.getErrorInfo());
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        //绘制marker
        Marker marker = mAMap.addMarker(new MarkerOptions()
                        .position(new LatLng(tmpW, tmpJ))
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapicon1)))
                        .draggable(true)
        );
        LatLng marker1 = new LatLng(tmpW, tmpJ);
        //设置中心点和缩放比例
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public void deactivate() {

    }

}
