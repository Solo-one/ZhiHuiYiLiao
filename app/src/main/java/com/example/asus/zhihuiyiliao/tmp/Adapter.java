package com.example.asus.zhihuiyiliao.tmp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.util.NetImageCache;

import java.util.List;

/**
 * Created by asus on 2016/8/28.
 */
public class Adapter extends BaseAdapter {

    private Context context;
    private List<Food> mData;
    private ImageLoader mImageLoader;//volley图片加载库

    public Adapter(Context context, List<Food> mData) {
        this.context = context;
        this.mData = mData;
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

        Food food = mData.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView =  inflater.inflate(R.layout.activity_test_item,null,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.imtro = (TextView) convertView.findViewById(R.id.imtro);
            viewHolder.ingredients = (TextView) convertView.findViewById(R.id.ingredients);
            viewHolder.burden = (TextView) convertView.findViewById(R.id.burden);
            viewHolder.albums = (ImageView) convertView.findViewById(R.id.albums);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(food.getTitle());
        viewHolder.imtro.setText(food.getImtro());
        viewHolder.ingredients.setText(food.getIngredients());
        viewHolder.burden.setText(food.getBurden());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                viewHolder.albums,R.drawable.big_loadpic_full_listpage_night,R.drawable.big_loadpic_full_listpage);

        String tmpURL = food.getAlbums();
        String url = tmpURL.substring(2, tmpURL.length() - 2).replace("\\", "");
        String url1 = url.substring(0, url.length());


        mImageLoader.get(url, listener);
//        mImageLoader.get("http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/t/1/278_459533.jpg", listener);


        return convertView;
    }

    public static class ViewHolder {
        TextView title;
        TextView imtro;
        TextView ingredients;
        TextView burden;
        ImageView albums;
    }
}
