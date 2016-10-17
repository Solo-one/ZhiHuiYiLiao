package com.example.asus.zhihuiyiliao.SelfWidget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by asus on 2016/8/27.
 */
public class SelfToast {

    private Context context;
    private Toast toast = null;
    public SelfToast(Context context) {
        this.context = context;
    }
    public void toastShow(String text) {
        if(toast == null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(text);
        }
        toast.show();
    }
}
