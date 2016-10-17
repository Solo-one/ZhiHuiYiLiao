package com.example.asus.zhihuiyiliao.SelfWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by asus on 2016/9/3.
 * 自定义ScrollView 解决 手势操作产生的冲突问题
 */
public class MyScrollView extends ScrollView {

    GestureDetector gestureDetector;

    public MyScrollView(Context context) {
        super(context); // TODO Auto-generated constructor stub
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs); // TODO Auto-generated constructor stub

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); // TODO Auto-generated constructor stub }
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        super.onTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return true;
    }

}
