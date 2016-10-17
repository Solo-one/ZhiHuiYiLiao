package com.example.asus.zhihuiyiliao.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.News;
import com.example.asus.zhihuiyiliao.util.NetImageCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2016/8/25.
 */
public class NewsAdapter extends BaseAdapter {
    private List<News> mData;
    private Context context;

    private ImageLoader mImageLoader;//volley图片加载库

    public NewsAdapter(List<News> mData, Context context) {
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
        News news = mData.get(position);
        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_consulation_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.fuwu1);
            viewHolder.visited = (TextView) convertView.findViewById(R.id.fuwu2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(news.getTitle());
        viewHolder.address.setText("※" + news.getDescription());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                viewHolder.img,R.drawable.big_loadpic_full_listpage_night,R.drawable.big_loadpic_full_listpage);
        mImageLoader.get(news.getPicUrl(), listener);

        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:MM:ss");
        String sd = sdf.format(new Date(Long.parseLong(news.getTime())));//格式化时间戳
        viewHolder.level.setText(sd);
        viewHolder.comment.setText("评论:"+news.getCount());
        viewHolder.visited.setText("浏览:"+(news.getCount()*6+news.getCount())+"");

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView address;
        ImageView img;
        TextView level;
        TextView comment;
        TextView visited;
    }
}
