package com.example.asus.zhihuiyiliao.BootAnimation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.guide.GuideActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BootActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示，防止错位
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boot);

//        intent = new Intent(BootActivity.this, GuideActivity.class);

        /**
         * 定时器 方法一
         */
       /* Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                ///定时器暂停设置，动画会有异常
                overridePendingTransition(R.anim.up, R.anim.pause);//动画设置没有用
                finish();
            }
        };
        timer.schedule(task, 1200);//定时器 将要执行的 定时器任务 暂停1.2秒钟 执行*/

           new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BootActivity.this, GuideActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.up, R.anim.pause);
                finish();
            }
        }, 1200);// 暂停1.2秒钟 执行


    }
}
