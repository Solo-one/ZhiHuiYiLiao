package com.example.asus.zhihuiyiliao.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by asus on 2016/8/30.
 * 更改手机状态栏颜色
 */
public class StatusBarUtils {

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Window window = activity.getWindow();

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(activity.getResources().getColor(colorResId));



                //底部导航栏

                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Window window = dialog.getWindow();

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));



                //底部导航栏

                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
