package com.example.asus.zhihuiyiliao.guide;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.ConItemAdapter;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.consulation.ConItemFragment;
import com.example.asus.zhihuiyiliao.entity.DataManager;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.NewsType;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsulationFragment extends Fragment {

    private Unbinder unbinder;
    View v;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.vp)
    ViewPager vp;

    List<Fragment> mData;
    ConItemAdapter adapter;
    int tmpPosition = 0;

    public ConsulationFragment() {
        // Required empty public constructor
    }

    //回顶部
    public void listViewToTop() {
        ((ConItemFragment) mData.get(tmpPosition)).toTop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_consulation, container, false);
            unbinder = ButterKnife.bind(this, v);
            tabs = (TabLayout) v.findViewById(R.id.tabs);
            vp = (ViewPager) v.findViewById(R.id.vp);

            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);//设置可以滑动
            tabs.setTabGravity(TabLayout.GRAVITY_FILL);

            initData();//初始化数据

            //适配器
            adapter = new
                    ConItemAdapter(getChildFragmentManager(), mData, DataManager.initType());
            vp.setAdapter(adapter);
            //关联tabs 和 ViewPage
            tabs.setupWithViewPager(vp);

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tmpPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        return v;
    }

    //解绑后 其他地方会报错，不知道原因？
   /* @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//解绑
    }
    */

    /**
     * 初始化数据信息，
     * 将Fragment 添加到ViewPager 上面去
     */
    public void initData() {
        mData = new ArrayList<>();
        List<NewsType> types = DataManager.initType();
        for (int i = 0; i < types.size(); i++) {
            ConItemFragment home = new ConItemFragment();
            Bundle bundle = new Bundle();//通过Bundle 携带数据 给 碎片onCreateView()方法使用
            bundle.putInt("type", types.get(i).getType());
            bundle.putString("url", types.get(i).getUrl());
//            //通过序列化传递数据
//            bundle.putSerializable("newsType", types.get(i));
            //通过Parcelable传递数据
            bundle.putParcelable("newsType_parcelable", types.get(i));
            home.setArguments(bundle);
            mData.add(home);
        }

    }

}
