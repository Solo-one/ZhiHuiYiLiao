package com.example.asus.zhihuiyiliao.visit;

import android.os.Bundle;
import android.view.View;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SearchHospitalActivity extends SwipeBackActivity {

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
}
