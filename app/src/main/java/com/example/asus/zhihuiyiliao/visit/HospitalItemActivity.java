package com.example.asus.zhihuiyiliao.visit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.MyScrollView;
import com.example.asus.zhihuiyiliao.dao.HospitalOrmDao;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.location.LocationMainActivity;
import com.example.asus.zhihuiyiliao.location.MapActivity;
import com.example.asus.zhihuiyiliao.mine.ShiMingActivity;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class HospitalItemActivity extends SwipeBackActivity {

    private boolean isCollec = false;//默认没有收藏
    HospitalOrmDao dao;
    private SwipeBackLayout mSwipeBackLayout;

    private Hospital hospital;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.gobus)
    TextView gobus;

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @OnClick(R.id.luxian)
    public void luxian(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("hospital", hospital);
        startActivity(intent);
    }

    @BindView(R.id.web)
    LinearLayout web;

    @OnClick(R.id.item1)
    public void item1(View v) {
        Intent intent = new Intent(this, ShiMingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item2)
    public void item2(View v) {
        Intent intent = new Intent(this, ShiMingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item3)
    public void item3(View v) {
        Intent intent = new Intent(this, ShiMingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item4)
    public void item4(View v) {
        Intent intent = new Intent(this, ShiMingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item5)
    public void item5(View v) {
        Intent intent = new Intent(this, ShiMingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item6)
    public void item6(View v) {
        Toast.makeText(this, "此功能尚未开通", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.item7)
    public void item7(View v) {
        Toast.makeText(this, "此功能尚未开通", Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.collection)
    TextView collection;

    @BindView(R.id.footer)
    RelativeLayout footer;

//    @BindView(R.id.myscrollview)
//    MyScrollView myscrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_hospital_item);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
       /* footer.setOnTouchListener(this);
        footer.setLongClickable(true);
        myscrollview.setGestureDetector(this.getmGestureDetector());*/

        dao = new HospitalOrmDao(this);
        Intent intent = getIntent();
        hospital = (Hospital) intent.getExtras().get("hospital");
        name.setText(hospital.getName());
//        level.setText(hospital.getLevel());
        address.setText("地址：" + hospital.getAddress());
        gobus.setText("乘车路线：" + hospital.getGobus());

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HospitalItemActivity.this, WebHospitalActivity.class);
                intent1.putExtra("hos", hospital.getUrl());
                startActivity(intent1);
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(HospitalItemActivity.this);
                View view = LayoutInflater.from(HospitalItemActivity.this).inflate(R.layout.self_toast, null, false);
                TextView textView = (TextView) view.findViewById(R.id.show);

                Intent intent = getIntent();
                setResult(2, intent);

                if (isCollec == false) {
                    textView.setText("收藏成功");
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    collection.setText("取消收藏");
//                    //加载动画资源，生成动画对象
//                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
//                    love.startAnimation(animation);
                    isCollec = true;//已经收藏
                } else {
                    textView.setText("取消收藏");
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    collection.setText("收藏");
//                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
//                    love.startAnimation(animation);
                    isCollec = false;//取消收藏
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Hospital hospital = (Hospital) intent.getExtras().get("hospital");

        boolean tf = dao.findHospital(hospital.getUrl(), hospital.getName());

        if (tf == true) {
            collection.setText("取消收藏");
            isCollec = true;
        } else {
            collection.setText("收藏");
            isCollec = false;
        }
    }

    /**
     * 修改数据库
     */
    @Override
    protected void onPause() {
        super.onPause();
        //收藏成功
        Intent intent = getIntent();
        if (isCollec == true) {
            Hospital hospital = (Hospital) intent.getExtras().get("hospital");

            boolean tf = dao.findHospital(hospital.getUrl(), hospital.getName());

            if (tf == false) {
                dao.addHospital(hospital);
            }

        } else {
            Hospital hospital = (Hospital) intent.getExtras().get("hospital");
            dao.deleteHospital(hospital.getUrl(), hospital.getName());
        }
    }

}
