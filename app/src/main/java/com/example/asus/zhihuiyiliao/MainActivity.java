package com.example.asus.zhihuiyiliao;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.zhihuiyiliao.BootAnimation.BootActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, BootActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.up, R.anim.up);//启动Activity动画，此动画异常
                finish();
            }
        }, 1200);// 暂停1.2秒钟 执行

    }
}
