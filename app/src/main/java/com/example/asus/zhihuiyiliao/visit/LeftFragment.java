package com.example.asus.zhihuiyiliao.visit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.Adapter.SelectAdapter;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {

    List<City> mData;
    SelectAdapter adapter;


    public LeftFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_left, container, false);
        ListView lv = (ListView) v.findViewById(R.id.lv);
        mData = initData();
        adapter = new SelectAdapter(mData, getActivity());
        lv.setAdapter(adapter);

        adapter.changeSelect(0);//设置第1项被选中

//        guaHaoActivity.right_frgment.refresh(1);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelect(position);
                GuaHaoActivity guaHaoActivity = (GuaHaoActivity) getActivity();
                guaHaoActivity.right_frgment.refresh(position+1);
            }
        });

        return v;
    }

    public List<City> initData() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("推荐"));
        cities.add(new City("朝阳区"));
        cities.add(new City("东城区"));
        cities.add(new City("西城区"));
        cities.add(new City("崇文区"));
        cities.add(new City("宣武区"));
        cities.add(new City("丰台区"));
        cities.add(new City("海淀区"));
        cities.add(new City("房山区"));
        cities.add(new City("石景山区"));
        cities.add(new City("门头沟区"));
        cities.add(new City("通州区"));
        cities.add(new City("昌平区"));
        cities.add(new City("大兴区"));
        cities.add(new City("平谷区"));
        cities.add(new City("怀柔区"));
        cities.add(new City("密云区"));
        cities.add(new City("延庆区"));
        return cities;
    }
}
