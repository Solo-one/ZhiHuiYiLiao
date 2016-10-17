package com.example.asus.zhihuiyiliao.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by asus on 2016/8/19.
 * 引导页 ViewPager 适配器
 */
public class VPGuideFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mData;

    public VPGuideFragmentAdapter(FragmentManager fm,List<Fragment> mData) {
        super(fm);
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }*/
}
