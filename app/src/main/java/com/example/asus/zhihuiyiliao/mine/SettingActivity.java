package com.example.asus.zhihuiyiliao.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.MyScrollView;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends MyBaseActivity {

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @BindView(R.id.footer)
    RelativeLayout footer;

    @BindView(R.id.myscrollview)
    MyScrollView myscrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        footer.setOnTouchListener(this);
        footer.setLongClickable(true);
        myscrollview.setGestureDetector(this.getmGestureDetector());

    }
}
