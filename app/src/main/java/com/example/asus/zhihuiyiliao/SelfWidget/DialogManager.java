package com.example.asus.zhihuiyiliao.SelfWidget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.asus.zhihuiyiliao.R;

/**
 * Created by asus on 2016/8/26.
 */
public class DialogManager {


    private static OnChangedListener change;

    //向外提供监听
    public static void setOnChangedListener(OnChangedListener ls) {
        change = ls;
    }


    public static void exitDialog(final Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.exitdialog);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER_VERTICAL);

        View v1 = window.findViewById(R.id.cancel);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = window.findViewById(R.id.sure);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change.OnChange();
            }
        });
    }

    /**
     * 外部监听接口
     */
    public static interface OnChangedListener {
        public void OnChange();
    }
}
