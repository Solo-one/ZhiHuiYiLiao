package com.example.asus.zhihuiyiliao.Adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 2016/8/20.
 * 自动循环滑动ViewPager
 */
public class VpAdapter extends PagerAdapter {

    private List<ImageView> mData;

    public VpAdapter(List<ImageView> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 类似与 ListView  getView()方法
     *
     * @param container 参数就是ViewPage
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       /* View v= mData.get(position);
        container.addView(v);
        return v;*/
        try{
            ((ViewPager) container).addView((View) mData.get(position % mData.size()),0);
        }catch (Exception e) {
            // TODO: handle exception
        }

        return mData.get(position % mData.size());
    }

    /**
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mData.get(position));//删除指定位置的ViewPage
        ((ViewPager) container).removeView(mData.get(position % mData.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }


}
