package com.example.asus.zhihuiyiliao.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.zhihuiyiliao.entity.NewsType;

import java.util.List;

/**
 * Created by asus on 2016/8/25.
 * 资讯ViewPager上加载Fragment 的Adapter
 */
public class ConItemAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;
    private List<NewsType> newsTypes;

    public ConItemAdapter(FragmentManager fm, List<Fragment> mData) {
        super(fm);
        this.mData = mData;
    }

    public ConItemAdapter(FragmentManager fm, List<Fragment> mData, List<NewsType> newsTypes) {
        super(fm);
        this.mData = mData;
        this.newsTypes = newsTypes;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newsTypes.get(position).getName();
    }
}
