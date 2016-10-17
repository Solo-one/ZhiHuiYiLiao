package com.example.asus.zhihuiyiliao.mine;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asus.zhihuiyiliao.Adapter.FavoriteAdapter;
import com.example.asus.zhihuiyiliao.Adapter.HospitalApapter;
import com.example.asus.zhihuiyiliao.Adapter.NewsAdapter;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.consulation.NewsDetailActivity;
import com.example.asus.zhihuiyiliao.dao.HospitalOrmDao;
import com.example.asus.zhihuiyiliao.dao.NewsOrmDao;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.News;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;
import com.example.asus.zhihuiyiliao.visit.HospitalItemActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class FavoriteActivity extends SwipeBackActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.rg)
    RadioGroup rg;

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    FavoriteAdapter adapter;
    private List<View> mData;//集合存放 View 对象
    HospitalOrmDao dao;
    NewsOrmDao dao1;
    int news;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        dao = new HospitalOrmDao(this);
        dao1 = new NewsOrmDao(this);
        Intent intent = getIntent();
        news = intent.getIntExtra("news",0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        adapter = new FavoriteAdapter(mData);

        vp.setAdapter(adapter);

        if (news == 0) {
            ((RadioButton) rg.getChildAt(0)).setChecked(true);
            vp.setCurrentItem(0);
        } else if (news == 1) {
            ((RadioButton) rg.getChildAt(1)).setChecked(true);
            vp.setCurrentItem(1);
        }

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton tb = (RadioButton) rg.getChildAt(position);
                tb.setChecked(true);//设置为选中
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.item1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.item2:
                        vp.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();

        final List<Hospital> favHos = new ArrayList<>();
        final List<News> favNews = new ArrayList<>();

        View view1 = LayoutInflater.from(this)
                .inflate(R.layout.favorite_layout, null, false);
        ListView listView1 = (ListView) view1.findViewById(R.id.lv);
        favHos.addAll(favHosData());
        HospitalApapter adapter1 = new HospitalApapter(favHos,this);
        listView1.setAdapter(adapter1);
        mData.add(view1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital hos = favHos.get(position);
                Intent intent = new Intent(FavoriteActivity.this, HospitalItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hospital", hos);
                intent.putExtras(bundle);
                startActivity(intent);
                news = 0;
            }
        });


        View view2 = LayoutInflater.from(this)
                .inflate(R.layout.favorite_layout, null, false);
        ListView listView2 = (ListView) view2.findViewById(R.id.lv);
        favNews.addAll(favNewsData());
        NewsAdapter adapter2 = new NewsAdapter(favNews,this);
        listView2.setAdapter(adapter2);
        mData.add(view2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News newss = favNews.get(position);
                Intent intent = new Intent(FavoriteActivity.this,NewsDetailActivity.class);
                intent.putExtra("id", newss.getId());
                intent.putExtra("url",newss.getUrlsource());
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", newss);
                intent.putExtras(bundle);
                startActivity(intent);
                news = 1;
            }
        });

    }



    public List<Hospital> favHosData() {
        List<Hospital> data = new ArrayList<>();
        data.addAll(dao.findAllHospital());
        return data;
    }

    public List<News> favNewsData() {
        List<News> data = new ArrayList<>();
        data.addAll(dao1.findAllNews());
        return data;
    }



}
