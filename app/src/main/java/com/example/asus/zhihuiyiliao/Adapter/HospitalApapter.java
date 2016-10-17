package com.example.asus.zhihuiyiliao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.util.NetImageCache;

import java.util.List;

/**
 * Created by asus on 2016/8/23.
 */
public class HospitalApapter extends BaseAdapter {

    private List<Hospital> mData;
    private Context context;

    private ImageLoader mImageLoader;//volley图片加载库

    public HospitalApapter(List<Hospital> mData, Context context) {
        this.mData = mData;
        this.context = context;
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), new NetImageCache());
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
        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_all_hospital_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(hospital.getName());
        viewHolder.address.setText("※地址："+hospital.getAddress());

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                viewHolder.img,R.drawable.big_loadpic_full_listpage_night,R.drawable.big_loadpic_full_listpage);
        mImageLoader.get(hospital.getImg(),listener);

        viewHolder.level.setText("三级甲等");
        viewHolder.level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.level.getText().equals("三级甲等")){
                    viewHolder.level.setText("二级甲");
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView address;
        ImageView img;
        TextView level;
    }
}
