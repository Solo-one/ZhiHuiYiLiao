package com.example.asus.zhihuiyiliao.visit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class GuaHaoActivity extends SwipeBackActivity {


    public LeftFragment left_frgment;
    public RightFragment right_frgment;

    private SwipeBackLayout mSwipeBackLayout;

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_gua_hao);
        ButterKnife.bind(this);

        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        left_frgment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.left_fragment);
        right_frgment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.right_fragment);
//        footer.setOnTouchListener(this);
//        footer.setLongClickable(true);


    }
}
