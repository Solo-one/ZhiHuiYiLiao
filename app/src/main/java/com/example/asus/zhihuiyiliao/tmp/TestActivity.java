package com.example.asus.zhihuiyiliao.tmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    List<Food> mData;
    Adapter adapter;

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.edit)
    EditText edit;
    @OnClick(R.id.search)
    public void search(View v) {
        loadLatast();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mData = new ArrayList<>();
        adapter = new Adapter(this, mData);
        lv.setAdapter(adapter);
    }


    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            Log.i("TAG", response);
            mData.clear();
            mData.addAll(parserJSON(response));
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onErrer(VolleyError error) {
            Log.i("TAG", error+"失败");
        }

    }

  /*  //下拉加载最新数据
    private void loadLatast() {
        VolleyUtil.get("http://apis.juhe.cn/cook/query?key=9df4afabfb31cf04fb24888f7ec746df&menu="+edit.getText().toString())
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .start();
    }*/

    //下拉加载最新数据
    private void loadLatast() {
        VolleyUtil.get("http://op.juhe.cn/yi18/news/list?key=bf6775e97e19aea5f678b89d541b6d31")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .start();
    }

    /*private void loadLatast() {
        VolleyUtil.get("http://apis.baidu.com/3023/weather/weather?id=101010300")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }*/

    /**
     * JSON 格式化数据
     *
     * @param s
     */
    private List<Food> parserJSON(String s) {

        List<Food> data = new ArrayList<>();

        try {
            JSONObject aa = new JSONObject(s);
            JSONObject result = aa.getJSONObject("result");

            JSONArray jsonArray = result.getJSONArray("data");
            //遍历json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                Food nt = new Food(
                        jo.getInt("id"),
                        jo.getString("title"),
                        jo.getString("imtro"),
                        jo.getString("ingredients"),
                        jo.getString("burden"),
                        jo.getString("albums")
                );

                data.add(nt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
