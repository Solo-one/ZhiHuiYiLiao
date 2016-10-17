package com.example.asus.zhihuiyiliao.consulation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.asus.zhihuiyiliao.Adapter.HospitalApapter;
import com.example.asus.zhihuiyiliao.Adapter.NewsAdapter;
import com.example.asus.zhihuiyiliao.Application.MedicalApplication;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.News;
import com.example.asus.zhihuiyiliao.http.CallBack;
import com.example.asus.zhihuiyiliao.http.NetRequest;
import com.example.asus.zhihuiyiliao.util.VolleyUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConItemFragment extends Fragment {

    private View v;//定义视图为全局变量，在 解决视图重复被加载

    private List<News> mData;
    private NewsAdapter adapter;
    private PullToRefreshListView listView;
    private int page = 1;

    protected boolean isVisiable = false;//Fragment 是否可见
    private boolean isPrepared = false;//要加载视图是否准备好
    private boolean isFirst = true;//是否是第一次加载

    private int type = 7;
    private String url = "http://apis.baidu.com/tngou/info/list";

    public ConItemFragment() {
        // Required empty public constructor
    }


    //点击回顶部
    public void toTop() {
        listView.getRefreshableView().setSelection(0);
    }


    /**
     * 当Frament 要可见时 调用此方法
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisiable = true;
            lazyInitData();//调用懒加载
        }else {
            isVisiable = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_con_item, container, false);
            listView = (PullToRefreshListView) v.findViewById(R.id.listview_pull);

            mData = new ArrayList<>();

            adapter = new NewsAdapter(mData, getActivity());
            listView.setAdapter(adapter);

            listView.setMode(PullToRefreshBase.Mode.BOTH);//下拉模式 两种
            listView.setScrollingWhileRefreshingEnabled(true);//刷新模式仍可以滑动

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News news = mData.get(position-1);
                    Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                    intent.putExtra("id", news.getId());
                    intent.putExtra("url",url);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news",news);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

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

            //界面已经准备完毕 开始加载
            isPrepared = true;
            lazyInitData();//进行懒加载
        }

            return v;
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

    public void lazyInitData() {
        if (isPrepared && isVisiable && isFirst) {
            listView.setRefreshing();//自动下拉
            /**
             * 根据=新闻类型找到对应的数据
             */
//            List<NewsType_home> list = dao.findNewsByType(type);
//            mData.clear();
////            mData.addAll(list);
//            adapter.notifyDataSetChanged();
            loadLatast();
            isFirst = false;
        }

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
        }

        @Override
        public void onErrer(VolleyError error) {
            MedicalApplication.toast.toastShow("网络异常，请检查网络");
            listView.onRefreshComplete();
        }

    }

    //下拉加载最新数据
    private void loadLatast() {
        page = 1;
        VolleyUtil.get(url+"?id="+type+"&page="+page+"&rows=16")
                .setCallBack(new MyCallBack())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }

    //上拉加载更多数据
    private void loadMore() {
        page++;
        VolleyUtil.get(url+"?id="+type+"&page="+page+"&rows=10")
                .setCallBack(new MyCallBack1())
                .build()
                .setPriority(Request.Priority.HIGH)
                .addRequestHeadrs("apikey", "4600fe45a7f631f4800368013fb1a76e")
                .start();
    }


    /**
     * 网络监听回调方法MyCallBack1
     */
    private class MyCallBack1 implements CallBack {

        @Override
        public void onSuccess(String response) {
            mData.addAll(parserJSON(response));
            adapter.notifyDataSetChanged();
            listView.onRefreshComplete();
        }

        @Override
        public void onErrer(VolleyError error) {

        }

    }

    /**
     * 初始化数据
     *
     * @return
     */
    private List<News> parserJSON(String s) {

        List<News> data = new ArrayList<>();

        try {
            JSONObject result = new JSONObject(s);
            boolean status = result.getBoolean("status");
            int total = result.getInt("total");

            JSONArray jsonArray = result.getJSONArray("tngou");
            //遍历json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                News nt = new News(
                        jo.getInt("id"),
                        jo.getString("title"),
                        jo.getString("time"),
                        jo.getString("description"),
                        "http://tnfs.tngou.net/image"+jo.getString("img"),
                        url,
                        jo.getInt("count")
                );

                data.add(nt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

}
