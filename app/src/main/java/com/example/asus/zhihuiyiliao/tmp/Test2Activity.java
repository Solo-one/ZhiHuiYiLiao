package com.example.asus.zhihuiyiliao.tmp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.asus.zhihuiyiliao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class Test2Activity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.circle)
    CircleIndicator circleIndicator;

    Adapter2 adapter;
    ArrayList<ImageView> arrayList;
    private int[] imageResId; // 图片ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
        //初始化数据
        imageResId = new int[]{R.drawable.wallpaper_profile_night, R.drawable.wallpaper_profile, R.drawable.wallpaper_profile_night};

        initData();

        adapter = new Adapter2(arrayList);

        vp.setAdapter(adapter);
        circleIndicator.setViewPager(vp);
    }

    public ArrayList<ImageView> initData() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            arrayList.add(imageView);
        }

        return arrayList;
    }
}
