package com.example.asus.zhihuiyiliao.guide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.asus.zhihuiyiliao.R;

public class MyBaseActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{

    private GestureDetector mGestureDetector;

    public GestureDetector getmGestureDetector() {
        return mGestureDetector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGestureDetector = new GestureDetector(this,(GestureDetector.OnGestureListener) this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //下面两个要记得设哦，不然就没法处理轻触以外的事件了，例如抛掷动作。
        mGestureDetector.setIsLongpressEnabled(true);
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

       /* Log.i("TAG","sss"+e1.getX()+"--"+e2.getY()+"速度"+velocityX+"--"+velocityY);
        Log.i("TAG","sss"+(e1.getY() - e2.getY())+"速度"+velocityX+"--"+velocityY);*/

        if (e1.getX() - e2.getX() > SnsConstant.getFlingMinDistance()
                && Math.abs(velocityX) > SnsConstant.getFlingMinVelocity()) {

            /*//切换Activity
            Intent intent = new Intent(GestureActivity.this, ContactsActivity.class);
            startActivity(intent);*/
//            Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
        } else if ( (e2.getX() - e1.getX() > SnsConstant.getFlingMinDistance())
                && Math.abs(velocityX) > SnsConstant.getFlingMinVelocity() && (e1.getY() - e2.getY() < 150)) {

//          切换Activity
//            Intent intent = new Intent(GestureActivity.this, ContactsActivity.class);
//            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.in, R.anim.out);
//            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
        }

        return false;
    }



    public static class SnsConstant {
        private static final float FLING_MIN_DISTANCE = 30.0f;
        private static final float FLING_MIN_VELOCITY = 10.0f;

        public static float getFlingMinDistance() {
            return FLING_MIN_DISTANCE;
        }

        public static float getFlingMinVelocity() {
            return FLING_MIN_VELOCITY;
        }
    }
}
