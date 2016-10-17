package com.example.asus.zhihuiyiliao.mine;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.DatePickerDialog;
import com.example.asus.zhihuiyiliao.SelfWidget.MyScrollView;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiMingActivity extends MyBaseActivity implements View.OnClickListener {

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @OnClick(R.id.save)
    public void save(View v) {
        Toast.makeText(this, "保存失败,请重试", Toast.LENGTH_LONG).show();
        finish();
    }

    @BindView(R.id.item1)
    RelativeLayout item1;

    @BindView(R.id.item2)
    RelativeLayout item2;

    @BindView(R.id.item3)
    RelativeLayout item3;

    @BindView(R.id.item4)
    RelativeLayout item4;

    @BindView(R.id.item5)
    RelativeLayout item5;

    @BindView(R.id.item6)
    RelativeLayout item6;

    @BindView(R.id.item7)
    RelativeLayout item7;

    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.card)
    TextView card;

    @BindView(R.id.expand)
    TextView expand;

    @BindView(R.id.expand_oo)
    LinearLayout expand_oo;
    @BindView(R.id.date)
    TextView date;

    boolean flag = true;

    @BindView(R.id.footer)
    RelativeLayout footer;

    @BindView(R.id.myscrollview)
    MyScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_shi_ming);
        ButterKnife.bind(this);

        footer.setOnTouchListener(this);
        footer.setLongClickable(true);
        scrollView.setGestureDetector(this.getmGestureDetector());

        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
        item6.setOnClickListener(this);
        item7.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                break;
            case R.id.item2:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.show();
                Window window = dialog.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();

                window.setContentView(R.layout.sex_layout);
//                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER_VERTICAL);

                /*lp.width = 500; // 宽度
                lp.height = 600; // 高度
                lp.alpha = 0.7f; // 透明度
                window.setAttributes(lp);*/

                /*
                 * 将对话框的大小按屏幕大小的百分比设置
                 */
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
                window.setAttributes(p);

                final TextView v1 = (TextView) window.findViewById(R.id.male);

                v1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sex.setText(v1.getText().toString());
                        dialog.dismiss();
                    }
                });
                final TextView v2 = (TextView) window.findViewById(R.id.female);
                v2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sex.setText(v2.getText());
                        dialog.dismiss();
                    }
                });
                View v3 = window.findViewById(R.id.cancel);
                v3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.item3:
                Calendar c = Calendar.getInstance();

                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DatePickerDialog(ShiMingActivity.this, 0, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth) {
                        String textString = String.format("%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth);
                        date.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();


                break;
            case R.id.item4:
                final AlertDialog dialog4 = new AlertDialog.Builder(this).create();
                dialog4.show();
                Window window4 = dialog4.getWindow();
                window4.setContentView(R.layout.cardtype_layout);
                window4.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window4.setGravity(Gravity.CENTER_VERTICAL);
                /*
                 * 将对话框的大小按屏幕大小的百分比设置
                 */
                WindowManager m4 = getWindowManager();
                Display d4 = m4.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p4 = window4.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
                p4.width = (int) (d4.getWidth() * 0.8); // 宽度设置为屏幕的0.65
                window4.setAttributes(p4);


                final TextView card1 = (TextView) window4.findViewById(R.id.card1);
                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.setText(card1.getText());
                        dialog4.dismiss();
                    }
                });
                final TextView card2 = (TextView) window4.findViewById(R.id.card2);
                card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.setText(card2.getText());
                        dialog4.dismiss();
                    }
                });
                final TextView card3 = (TextView) window4.findViewById(R.id.card3);
                card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.setText(card3.getText());
                        dialog4.dismiss();
                    }
                });

                View cancel = window4.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                    }
                });

                break;
            case R.id.item5:
                break;
            case R.id.item6:
                break;
            case R.id.item7:
                if (flag == true) {
                    expand_oo.setVisibility(View.VISIBLE);

                    Drawable drawable = getResources().getDrawable(R.drawable.mine_icon_delete);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    expand.setCompoundDrawables(null, null, drawable, null);

                    flag = false;
                } else {
                    expand_oo.setVisibility(View.GONE);
                    Drawable drawable = getResources().getDrawable(R.drawable.mine_icon_add);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    expand.setCompoundDrawables(null, null, drawable, null);

                    flag = true;
                }
                break;
            default:
                break;
        }
    }
}
