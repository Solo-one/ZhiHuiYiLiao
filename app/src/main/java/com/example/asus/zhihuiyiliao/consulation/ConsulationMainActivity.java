package com.example.asus.zhihuiyiliao.consulation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.HospitalApapter;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;
import com.example.asus.zhihuiyiliao.visit.HospitalItemActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ConsulationMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_consulation_main);
        ButterKnife.bind(this);


        loadLatast();//网络请求数据


    }

    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            Log.i("TAG", response);
        }

        @Override
        public void onErrer(VolleyError error) {

        }

    }

    //下拉加载最新数据
    private void loadLatast() {
        VolleyUtil.get("http://apis.baidu.com/tngou/info/news?id=0&classify=1&rows=20")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }


    /**
     * JSON 格式化数据
     *
     * @param s
     */
    private List<Hospital> parserJSON(String s) {

        List<Hospital> data = new ArrayList<>();

        try {
            JSONObject result = new JSONObject(s);
            boolean status = result.getBoolean("status");
            int total = result.getInt("total");

            JSONArray jsonArray = result.getJSONArray("tngou");
            //遍历json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                Hospital nt = new Hospital(
                        jo.getString("name"),
                        jo.getString("level"),
                        jo.getString("url"),
                        "http://tnfs.tngou.net/image" + jo.getString("img"),
                        jo.getString("address"),
                        jo.getString("gobus")
                );

                data.add(nt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }


}
