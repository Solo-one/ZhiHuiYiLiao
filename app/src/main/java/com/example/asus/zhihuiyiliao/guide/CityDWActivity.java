package com.example.asus.zhihuiyiliao.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.ExpandableListViewAdapter;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.dao.CityOrmDao;
import com.example.asus.zhihuiyiliao.entity.City;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.location.LocationUtil;
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
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class CityDWActivity extends SwipeBackActivity implements AMapLocationListener {

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @BindView(R.id.lvs)
    ExpandableListView listView;

    @BindView(R.id.dwCity)
    TextView dwcity;

    @BindView(R.id.now_city)
    TextView now_city;

    @OnClick(R.id.cardview)
    public void cardview(View v) {
        //向本地文件中写入城市设置信息
        SharedPreferences.Editor editor = sp.edit();
        if (cityName != null) {
            City c = dao.findCityByisGroup(cityName);
            if (c != null) {
                String tmpName = c.getCityName();
                String tmpID = String.valueOf(c.getCityId());

                Intent intent = getIntent();
                intent.putExtra("city", tmpName);
                intent.putExtra("cityID",tmpID);
                setResult(1, intent);

                editor.putString("city", tmpName).commit();
                editor.putString("cityID",tmpID).commit();
            } else {
                MedicalApplication.toast.toastShow("定位异常，未知结果");
            }
        } else {
            Intent intent = getIntent();
            intent.putExtra("city", "北京");
            intent.putExtra("cityID","2");
            setResult(1, intent);

            editor.putString("city", "北京").commit();
            editor.putString("cityID","2").commit();
        }

        finish();
    }

    private List<City> mData;
    private List<City> ls;

    ExpandableListViewAdapter adapter1;

    //定位模块
    AMapLocationClient locationClient=null;

    List<List<City>> city;
    List<List<City>> Tempcity;

    private SwipeBackLayout mSwipeBackLayout;

    //数据操作
    SharedPreferences sp,sp1;
    private CityOrmDao dao;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_city_dw);
        ButterKnife.bind(this);

        locationClient = LocationUtil.configLocation(getApplicationContext());
        locationClient.setLocationListener(this);

        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //数据库操作对象
        dao = new CityOrmDao(this);

        mData = new ArrayList<>();
        city = new ArrayList<>();

        sp1 = getSharedPreferences("CityList", MODE_PRIVATE);
        List<City> cities = parserJSON(sp1.getString("citylist", ""));
        if (cities.size() != 0) {
            mData.addAll(cities);//省份名
            city.addAll(Tempcity);//城市名
        }

        adapter1 = new ExpandableListViewAdapter(mData,city,CityDWActivity.this);
        listView.setAdapter(adapter1);

        sp = getSharedPreferences("CITY", MODE_PRIVATE);
        dwcity.setText(sp.getString("city", "北京"));

        loadLatast();

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TextView t = (TextView) v.findViewById(R.id.text);
                TextView tId = (TextView) v.findViewById(R.id.cityID);
                String str = t.getText().toString();
                String cityID = tId.getText().toString();
                String city = str.substring(0, str.length() - 1);
                dwcity.setText(city);

                Intent intent = getIntent();
                intent.putExtra("city", city);
                intent.putExtra("cityID", cityID);
                setResult(1, intent);

                //向本地文件中写入城市设置信息
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city", city).commit();
                editor.putString("cityID", cityID).commit();

                finish();
                return false;
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ls != null) {
            dao.deleteCities();
            dao.addCities(ls);
            dao.addCitiesMap(Tempcity);
        }

        locationClient.onDestroy();//结束定位
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null&&aMapLocation.getErrorCode()==0){
            Log.i("回调信息", aMapLocation.toString());
            cityName = aMapLocation.getCity();
            now_city.setText(cityName);
        }
        if(aMapLocation.getErrorCode()!=0){
            Log.i("回调信息", "错误信息"+aMapLocation.getErrorCode());
            Log.i("回调信息", "错误信息"+aMapLocation.getErrorInfo());
        }
    }


    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            ls = parserJSON(response);

            mData.addAll(ls);//省份名
            city.addAll(Tempcity);//城市名

            adapter1 = new ExpandableListViewAdapter(mData, city, CityDWActivity.this);
            listView.setAdapter(adapter1);

            sp1 = getSharedPreferences("CityList", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putString("citylist", response).commit();

        }

        @Override
        public void onErrer(VolleyError error) {

        }

    }

    private void loadLatast() {
        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/city?type=all")
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
    private List<City> parserJSON(String s) {

        List<City> data = new ArrayList<>();

        Tempcity = new ArrayList<>();

        try {
            JSONObject result = new JSONObject(s);
            boolean status = result.getBoolean("status");
            JSONArray jsonArray = result.getJSONArray("tngou");
            //遍历json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                JSONArray jsonArrayCitys = jo.getJSONArray("citys");
                String cityName = null;
                City ct = null;
                List<City> tempCity = new ArrayList<>();
                for (int j = 0; j < jsonArrayCitys.length() ; j++) {
                    JSONObject jo1 = jsonArrayCitys.getJSONObject(j);
                    cityName = jo1.getString("city");
                    City ct1 = new City(
                            jo1.getInt("id"),
                            cityName+"市",
                            jo1.getString("province"),
                            jo1.getDouble("x"),
                            jo1.getDouble("y")
                    );
                    tempCity.add(ct1);
                }

                Tempcity.add(tempCity);

                ct = new City(
                        jo.getInt("id"),
                        cityName,
                        jo.getString("province"),
                        jo.getDouble("x"),
                        jo.getDouble("y")
                );
                data.add(ct);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
