package com.example.asus.zhihuiyiliao.visit;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.SelectAdapter;
import com.example.asus.zhihuiyiliao.Adapter.SelectAdapterRight;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.City;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {

    List<Hospital> mData;
    SelectAdapterRight adapter;
    String cityID;
//    int page = 1;


    public RightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("CITY", Context.MODE_PRIVATE);
        cityID = sp.getString("cityID", "1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_right, container, false);
        ListView lv = (ListView) v.findViewById(R.id.lv);
        mData = new ArrayList<>();
        adapter = new SelectAdapterRight(mData,getActivity());
        lv.setAdapter(adapter);

        loadMore(cityID, 1 + "");
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital hos = mData.get(position);
                Intent intent = new Intent(getActivity(), HospitalItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hospital", hos);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return v;
    }

    public void refresh(int index) {
        loadMore(cityID, index + "");
    }

    //加载更多数据
    private void loadMore(String cityID,String page) {

        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/list?id=" + cityID + "&page=" + page + "&rows=20")
                .setCallBack(new MyCallBack1())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    /**
     * 网络监听回调方法
     */
    private class MyCallBack1 implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.clear();
            mData.addAll(parserJSON(response));
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onErrer(VolleyError error) {
            MedicalApplication.toast.toastShow("网络异常，请检查网络");
        }

    }

    /**
     * JSON 格式化数据
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
                        "http://tnfs.tngou.net/image"+jo.getString("img"),
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
