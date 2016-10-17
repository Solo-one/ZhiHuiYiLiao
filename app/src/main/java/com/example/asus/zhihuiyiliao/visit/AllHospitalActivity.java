package com.example.asus.zhihuiyiliao.visit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.HospitalApapter;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.dao.HospitalDao;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AllHospitalActivity extends SwipeBackActivity {

    @BindView(R.id.pull_lv)
    PullToRefreshListView listView;
    @OnClick(R.id.topText)
    public void topText(View v) {
       listView.getRefreshableView().setSelection(0);
    }

    private List<Hospital> mData;

    HospitalApapter adapter;
    DisplayMetrics dm;

    String cityID;
    int page = 1;

    /*@BindView(R.id.back)
    ImageView back;
    */
    HospitalDao dao;

    @OnClick(R.id.back)
    public void back(View v){
        finish();
    }

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_all_hospital);
        ButterKnife.bind(this);

        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        SharedPreferences sp = getSharedPreferences("CITY", MODE_PRIVATE);
        cityID = sp.getString("cityID","1");
        dao = new HospitalDao(this);

        dm = new DisplayMetrics();//显示矩阵对象
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        mData = new ArrayList<>();
        mData.addAll(dao.findAllHospital());
        adapter = new HospitalApapter(mData,AllHospitalActivity.this);
        listView.setAdapter(adapter);

        loadLatast();//网络请求数据

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setScrollingWhileRefreshingEnabled(true);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadLatast();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });

        initRefreshListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital hos = mData.get(position-1);
                Intent intent = new Intent(AllHospitalActivity.this, HospitalItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hospital", hos);
//                intent.putExtra("url", nn.getUrl());
                intent.putExtras(bundle);
                startActivity(intent);

                TextView fuwu1 = (TextView) view.findViewById(R.id.fuwu1);
                TextView fuwu2 = (TextView) view.findViewById(R.id.fuwu2);

                fuwu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v);
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 0.6f;
                        getWindow().setAttributes(lp);
                    }
                });
                fuwu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v);
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 0.6f;
                        getWindow().setAttributes(lp);
                    }
                });
            }
        });
    }

    //popupwindow 窗口
    public void show(View vv) {
        View view = LayoutInflater.from(this).inflate(R.layout.wufu_layout, null, false);
        final PopupWindow popupWindow = new PopupWindow(view,
                dm.widthPixels - 100, ViewGroup.LayoutParams.WRAP_CONTENT);


        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();//获得View 控件高度

        popupWindow.setFocusable(true);//弹出Window 得到焦点，不能点击其他按钮

        popupWindow.setOutsideTouchable(true);//点击Window 外部位置获得焦点
        //6.0以下系统 添加以下代码实现点击空白处，Window消失
        ColorDrawable colorDrawable = new ColorDrawable(0);
        popupWindow.setBackgroundDrawable(colorDrawable);

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //计算View 在屏幕上的坐标
        int[] location = new int[2];
        vv.getLocationOnScreen(location);


        if (location[1] > dm.heightPixels / 2) {
            popupWindow.setAnimationStyle(R.style.show_anim_fuben);
            popupWindow.showAtLocation(vv, Gravity.NO_GRAVITY, 50, location[1]-popupHeight/2-15);
        } else {
            popupWindow.setAnimationStyle(R.style.show_anim);
            popupWindow.showAtLocation(vv, Gravity.NO_GRAVITY, 50, location[1]+vv.getHeight()+15);
        }

        /**
         * 设置监听
         */
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

    }


    /**
     * 下拉刷新 提示文字
     */
    public void initRefreshListView() {

        /*ILoadingLayout downLabels = listView.getLoadingLayoutProxy(true,false);
        downLabels.setPullLabel("正在为您加载更多");
        downLabels.setRefreshingLabel("请稍等...");
        downLabels.setReleaseLabel("放开刷新");*/

        ILoadingLayout startLabels = listView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在载入...");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");

    }

    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.clear();
            mData.addAll(parserJSON(response));
            adapter.notifyDataSetChanged();
            listView.onRefreshComplete();

            dao.removeHospital();//删除数据库元素
            dao.addHospital(mData);//重新写入数据
        }

        @Override
        public void onErrer(VolleyError error) {
            listView.onRefreshComplete();
        }

    }

    //下拉加载最新数据
    private void loadLatast() {
        page = 1;
        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/list?id="+cityID+"&page="+page+"&rows=16")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    //上拉加载更多数据
    private void loadMore() {
        page++;
        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/list?id="+cityID+"&page="+page+"&rows=10")
                .setCallBack(new MyCallBack1())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    private class MyCallBack1 implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.addAll(parserJSON(response));
            adapter.notifyDataSetChanged();
            listView.onRefreshComplete();
        }

        @Override
        public void onErrer(VolleyError error) {
            MedicalApplication.toast.toastShow("网络异常，请检查网络");
            listView.onRefreshComplete();
        }

    }


    /**
     * JSON 格式化数据
     * @param s
     */
    private List<Hospital> parserJSON(String s) {

        List<Hospital> data = new ArrayList<>();

        try {
            JSONObject result = new JSONObject(s);
            boolean status = result.getBoolean("status");
            int total = result.getInt("total");

            JSONArray jsonArray = result.getJSONArray("tngou");
            //遍历json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                Hospital nt = new Hospital(
                        jo.getString("name"),
                        jo.getString("level"),
                        jo.getString("url"),
                        "http://tnfs.tngou.net/image"+jo.getString("img"),
                        jo.getString("address"),
                        jo.getString("gobus"),
                        jo.getDouble("x"),
                        jo.getDouble("y")
                );

                data.add(nt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

}
