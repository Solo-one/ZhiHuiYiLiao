package com.example.asus.zhihuiyiliao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.City;
import com.example.asus.zhihuiyiliao.entity.Hospital;

import java.util.List;

/**
 * Created by asus on 2016/8/31.
 */
public class SelectAdapterRight extends BaseAdapter {
    private List<Hospital> mData;
    private Context mContext;

    public SelectAdapterRight(List<Hospital> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hospital hospital = mData.get(position);
        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.select_item_right, null, false);
            viewHolder = new ViewHolder();
            viewHolder.cityName = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.province = (TextView) convertView.findViewById(R.id.province);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.cityName.setText(hospital.getName());
//        viewHolder.province.setText(city.getProvince());

        return convertView;
    }

    static class ViewHolder {
        TextView cityName;
        TextView province;
    }
}
