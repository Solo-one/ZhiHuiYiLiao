package com.example.asus.zhihuiyiliao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.City;

import java.util.List;

/**
 * Created by asus on 2016/8/31.
 */
public class SelectAdapter extends BaseAdapter{
    private List<City> mData;
    private Context mContext;
    private int mLastPosition = -1;

    public SelectAdapter(List<City> mData, Context mContext) {
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
        City city = mData.get(position);
        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.select_item, null, false);
            viewHolder = new ViewHolder();

            viewHolder.cityName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.v = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.cityName.setText(city.getCityName());

        if (position == mLastPosition) {
            viewHolder.v.setBackground(mContext.getResources().getDrawable(R.drawable.bg7_1));
//            viewHolder.v.setBackgroundColor(mContext.getResources().getColor(R.color.select));
        } else {
            viewHolder.v.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView cityName;
        TextView province;
        View v;
    }

    //选中的位置
    public void changeSelect(int position) {

        //点击原处也会改变状态
        /*if(position != mLastPosition) {
            mLastPosition = position;
        } else {
            mLastPosition = -1;
        }
        notifyDataSetChanged();//刷新适配器*/

        //点击原处不会改变状态
        if(position != mLastPosition) {
            mLastPosition = position;
            notifyDataSetChanged();//刷新适配器
        }

    }
}
