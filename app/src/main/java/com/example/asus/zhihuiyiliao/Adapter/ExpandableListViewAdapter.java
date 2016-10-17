package com.example.asus.zhihuiyiliao.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.entity.City;
import com.example.asus.zhihuiyiliao.entity.Hospital;

import java.util.List;

/**
 * Created by asus on 2016/8/22.
 * 可扩展性ListView
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    private Context context;

    // 组名称
    private String[] groups;
    //组内子项
    private City[][] children;

    public ExpandableListViewAdapter(Context context) {
        this.context = context;
    }

    public ExpandableListViewAdapter(List<City> cityGroups, List<List<City>> citychildren, Context context) {
        this.context = context;

        //构建 组数据
        groups = new String[cityGroups.size()];

        for (int i = 0; i < groups.length; i++) {
            groups[i] = cityGroups.get(i).getProvince();
        }

        //构建 组内子项数据
        children = new City[citychildren.size()][];

        for (int i = 0; i < groups.length; i++) {
            List<City> ct = citychildren.get(i);
            children[i] = new City[ct.size()];
            for (int j = 0; j < ct.size(); j++) {
                children[i][j] = ct.get(j);
            }
        }
    }

    //获得指定组中的指定索引的子项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children[groupPosition][childPosition];
    }

    //获得指定子项数据的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    //TextView 组件
    private TextView buildTextView() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this.context);
        textView.setLayoutParams(params);
        textView.setTextSize(20.0f);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(60, 5, 3, 3);
        return textView;
    }

    //获得指定子项的view组件
  /*  @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        //生成一个新的textView
        TextView textView = buildTextView();
        //设置子选项的显示内容
        textView.setText(getChild(groupPosition, childPosition).toString());


        return textView;
    }*/


    //获得指定子项的view组件
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dwgroupcity, null, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.cityID = (TextView) convertView.findViewById(R.id.cityID);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.text.setText(((City) getChild(groupPosition, childPosition)).getCityName());
        viewHolder.cityID.setText(((City) getChild(groupPosition, childPosition)).getCityId()+"");
        return convertView;
    }

    static class ViewHolder {
        TextView text;
        TextView cityID;
    }

    //指定组中，所有子项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return children[groupPosition].length;
    }

    //取得指定组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    //取得所有组的个数
    @Override
    public int getGroupCount() {
        return groups.length;
    }

    //取得指定索引组的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获得指定组的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //将自定义的布局，转换为View使用。
        View view = LayoutInflater.from(this.context).inflate(R.layout.group_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(groups[groupPosition].toString());
        return view;
    }

    //以下两种方法，通常设置返回为true就可以。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
