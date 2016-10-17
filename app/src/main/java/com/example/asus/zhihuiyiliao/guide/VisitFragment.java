package com.example.asus.zhihuiyiliao.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.HospitalApapter;
import com.example.asus.zhihuiyiliao.Adapter.VpAdapter;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.ScrollBottomScrollView;
import com.example.asus.zhihuiyiliao.dao.HospitalDao;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.mine.ShiMingActivity;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;
import com.example.asus.zhihuiyiliao.visit.AllHospitalActivity;
import com.example.asus.zhihuiyiliao.visit.GuaHaoActivity;
import com.example.asus.zhihuiyiliao.visit.HospitalItemActivity;
import com.example.asus.zhihuiyiliao.visit.SearchHospitalActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 就诊Fragment 界面
 */
public class VisitFragment extends Fragment implements View.OnClickListener {

    View v;
    private int[] imageResId; // 图片ID
    private List<ImageView> imageViews; // 滑动的图片集合
    private List<View> dots; // 图片标题正文的那些点
    private String[] titles; // 图片标题

    private int num = 300;
    private List<Hospital> mData;
    ViewPager viewPager;
    HospitalApapter adapter1;
    ListView lv;

    HospitalDao dao;
    String cityID;
    TextView tv_title;

    DisplayMetrics dm;
    ScrollBottomScrollView scrollView;//自定义上拉底部的ScrollView
    SwipeRefreshLayout swiperefresh;//Google 下拉刷新动画组件

    boolean tf = true;//是否滑动到底部
    int page = 1;

    public VisitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new HospitalDao(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("CITY", Context.MODE_PRIVATE);
        cityID = sp.getString("cityID", "1");
    }

    //点击回顶部
    public void toTop() {
        if (scrollView != null) {
            scrollView.fullScroll(View.FOCUS_UP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_visit, container, false);
            RelativeLayout guahao = (RelativeLayout) v.findViewById(R.id.item1);
            RelativeLayout daozhentai = (RelativeLayout) v.findViewById(R.id.item2);
            RelativeLayout allhospital = (RelativeLayout) v.findViewById(R.id.item3);
            RelativeLayout querayhospital = (RelativeLayout) v.findViewById(R.id.item4);
            TextView hot = (TextView) v.findViewById(R.id.hot);
            TextView more = (TextView) v.findViewById(R.id.more);
            scrollView = (ScrollBottomScrollView) v.findViewById(R.id.bottomscrollView);
            swiperefresh = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
            tv_title = (TextView) v.findViewById(R.id.tv_title);

            guahao.setOnClickListener(this);
            daozhentai.setOnClickListener(this);
            allhospital.setOnClickListener(this);
            querayhospital.setOnClickListener(this);
            hot.setOnClickListener(this);
            more.setOnClickListener(this);

//            swiperefresh.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent,
//                    R.color.colorPrimaryDark, R.color.colorAccent);
            swiperefresh.setColorSchemeResources(R.color.ProgressColor);
//            swiperefresh.setProgressBackgroundColorSchemeColor(R.color.colorPrimary);
            swiperefresh.setProgressBackgroundColorSchemeResource(R.color.ProgressBackgroundColor);
            swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadLatast();//加载最新内容
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swiperefresh.setRefreshing(false);//刷新完成
                        }
                    }, 1500);
                }
            });
            swiperefresh.setProgressViewOffset(false, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                            .getDisplayMetrics()));

            dm = new DisplayMetrics();//显示屏幕矩阵对象
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            //初始化数据
            imageResId = new int[]{R.drawable.wallpaper_profile, R.drawable.wallpaper_profile_night, R.drawable.wallpaper_profile, R.drawable.wallpaper_profile_night};
            imageViews = new ArrayList<>();
            dots = new ArrayList<>();

            initData();

            viewPager = (ViewPager) v.findViewById(R.id.vp);
            final VpAdapter adapter = new VpAdapter(imageViews);
            viewPager.setAdapter(adapter);// 设置填充ViewPager页面的适配器

            ViewGroup.LayoutParams vp_layoutParams = viewPager.getLayoutParams();

            vp_layoutParams.height = dm.widthPixels * 3 / 7;// 设置控件的高强
            viewPager.setLayoutParams(vp_layoutParams); //使设置好的布局参数应用到控件

            //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
            //viewPager.setCurrentItem((imageViews.size()) * 100);*/
            viewPager.setCurrentItem(300);

            // 设置一个监听器，当ViewPager中的页面改变时调用
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                private int oldPosition = 0;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    num = position;
                    position = position % imageViews.size();
                    dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                    dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                    oldPosition = position;
                    tv_title.setText(titles[position]);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
                }
            });

            mData = new ArrayList<>();
            mData.addAll(dao.findAllHospital());

            lv = (ListView) v.findViewById(R.id.lv);
            adapter1 = new HospitalApapter(mData, getActivity());
            lv.setAdapter(adapter1);
            setListViewHeight(lv);//解决ScrollView中嵌套ListView滚动效果冲突问题
            loadLatast();//加载网络数据

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Hospital hos = mData.get(position);
                    Intent intent = new Intent(getActivity(), HospitalItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("hospital", hos);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    TextView fuwu1 = (TextView) view.findViewById(R.id.fuwu1);
                    TextView fuwu2 = (TextView) view.findViewById(R.id.fuwu2);

                    fuwu1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show(v);
                            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                            lp.alpha = 0.6f;
                            getActivity().getWindow().setAttributes(lp);
                        }
                    });
                    fuwu2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show(v);
                            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                            lp.alpha = 0.6f;
                            getActivity().getWindow().setAttributes(lp);
                        }
                    });
                }
            });

            //ScrollView 滑动到底部监听
            scrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
                @Override
                public void scrollBottom() {
                    if (tf) {
//                        mData.addAll(dao.findAllHospital());
//                        adapter1.notifyDataSetChanged();
//                        setListViewHeight(lv);//解决ScrollView中嵌套ListView滚动效果冲突问题
                        loadMore();
                        tf = false;
                        mHandler1.sendEmptyMessageDelayed(0, 1200);//1.2秒之后更改 tf=true;
                    }
                }
            });

            //开始就按viewPager 置顶
            viewPager.setFocusable(true);
            viewPager.setFocusableInTouchMode(true);
            viewPager.requestFocus();
        }

        return v;
    }

    //根据ViewPager活动状态 设置swiperefresh 是否可以滑动
    private void enableDisableSwipeRefresh(boolean b) {
        if (swiperefresh != null) {
            swiperefresh.setEnabled(b);
        }
    }

    //像素dp 转 px
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //像素px 转 dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //更改tf 状态
    Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tf = true;
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(mRunnable, 3000);//第一次延迟3秒钟启动
    }

    /**
     * 当离开该Fragment或Activity 时，解除mHandler，关闭子线程，不然会叠加效果
     */
    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }

    //popupwindow 窗口
    public void show(View vv) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wufu_layout, null, false);
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
            popupWindow.showAtLocation(vv, Gravity.NO_GRAVITY, 50, location[1] - popupHeight / 2 - 15);
        } else {
            popupWindow.setAnimationStyle(R.style.show_anim);
            popupWindow.showAtLocation(vv, Gravity.NO_GRAVITY, 50, location[1] + vv.getHeight() + 15);
        }

        /**
         * 设置监听
         */
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }

    /**
     * 外部调用更新ListView 数据方法
     *
     * @param id
     */
    public void update(String id) {
        cityID = id;
        loadLatast();
    }

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        public void run() {
            //每隔多长时间执行一次
            //mHandler.postDelayed(mRunnable, 1000*PhoneConstans.TIMEVALUE);
            mHandler.postDelayed(mRunnable, 1000 * 6);
            num++;//num 增加
            viewHandler.sendEmptyMessage(num);
        }

    };


    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);//修改viewPagerde 的值增加
            super.handleMessage(msg);
        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                Intent intent = new Intent(getActivity(), GuaHaoActivity.class);
                startActivity(intent);
                break;
            case R.id.item2:
                Intent shimingIntent = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent);
                break;
            case R.id.item3:
                Intent allhospitalIntent = new Intent(getActivity(), AllHospitalActivity.class);
                startActivity(allhospitalIntent);
                break;
            case R.id.item4:
                Intent searchhospitalIntent = new Intent(getActivity(), SearchHospitalActivity.class);
                startActivity(searchhospitalIntent);
                break;

            case R.id.hot:
                Intent hotIntent = new Intent(getActivity(), AllHospitalActivity.class);
                startActivity(hotIntent);
                break;

            case R.id.more:
                Intent moreIntent = new Intent(getActivity(), AllHospitalActivity.class);
                startActivity(moreIntent);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化数据
     */
    public void initData() {
        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        // 初始化点图
        dots.add(v.findViewById(R.id.v_dot0));
        dots.add(v.findViewById(R.id.v_dot1));
        dots.add(v.findViewById(R.id.v_dot2));
        dots.add(v.findViewById(R.id.v_dot3));

        titles = new String[imageResId.length];
        titles[0] = "就诊医院，找智慧医疗";
        titles[1] = "智慧医疗，实时资讯";
        titles[2] = "就诊医院，找智慧医疗";
        titles[3] = "智慧医疗，实时资讯";

        tv_title.setText(titles[0]);//

    }


    /**
     * 网络监听回调方法
     */
    private class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.clear();
            mData.addAll(parserJSON(response));
            adapter1.notifyDataSetChanged();
            setListViewHeight(lv);//解决ScrollView中嵌套ListView滚动效果冲突问题

            dao.removeHospital();//删除数据库元素
            dao.addHospital(mData);//重新写入数据
        }

        @Override
        public void onErrer(VolleyError error) {
            MedicalApplication.toast.toastShow("网络异常，请检查网络");
        }

    }


    //下拉加载最新数据
    private void loadLatast() {
        page = 1;
        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/list?id=" + cityID + "&page=" + page + "&rows=16")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    //加载更多数据
    private void loadMore() {
        page++;
        VolleyUtil.get("http://apis.baidu.com/tngou/hospital/list?id=" + cityID + "&page=" + page + "&rows=10")
                .setCallBack(new MyCallBack1())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    /**
     * 网络监听回调方法
     */
    private class MyCallBack1 implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.addAll(parserJSON(response));
            adapter1.notifyDataSetChanged();
            setListViewHeight(lv);//解决ScrollView中嵌套ListView滚动效果冲突问题

            dao.removeHospital();//删除数据库元素
            dao.addHospital(mData);//重新写入数据
        }

        @Override
        public void onErrer(VolleyError error) {
            MedicalApplication.toast.toastShow("请求失败，请检查网络");
        }

    }

    /**
     * JSON 格式化数据
     *
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
                        "http://tnfs.tngou.net/image" + jo.getString("img"),
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
