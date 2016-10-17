package com.example.asus.zhihuiyiliao.SelfWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2016/8/24.
 * 多个Marquee （跑马灯效果）
 */
public class MarqueeTextView extends TextView{
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
