package com.example.asus.zhihuiyiliao.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.Adapter.VPGuideFragmentAdapter;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.CircleImageView;
import com.example.asus.zhihuiyiliao.SelfWidget.DialogManager;
import com.example.asus.zhihuiyiliao.chat.ZXingActivity;
import com.example.asus.zhihuiyiliao.chat.example.qr_codescan.MipcaActivityCapture;
import com.example.asus.zhihuiyiliao.dao.HospitalOrmDao;
import com.example.asus.zhihuiyiliao.dao.NewsOrmDao;
import com.example.asus.zhihuiyiliao.login.LoginActivity;
import com.example.asus.zhihuiyiliao.mine.FavoriteActivity;
import com.example.asus.zhihuiyiliao.mine.SettingActivity;
import com.example.asus.zhihuiyiliao.mine.ShiMingActivity;
import com.example.asus.zhihuiyiliao.util.NetImageCache;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int SCANNIN_GREQUEST_CODE = 3;

    private List<Fragment> fragments;

    VisitFragment visitFragment;
    ConsulationFragment consulationFragment;
    ChatFragment chatFragment;
    MineFragment mineFragment;

//    @BindView(R.id.drawer)
//    DrawerLayout drawerLayout;//抽屉布局
    @BindView(R.id.drawer)
    SlidingPaneLayout slidingPane;//滑动抽屉布局
    @BindView(R.id.start)
    RelativeLayout start;
    @BindView(R.id.main)
    RelativeLayout main;

    @BindView(R.id.dingwei)
    TextView dingwei;
    @BindView(R.id.scan)
    ImageView scan;
    @BindView(R.id.menuDrawer)
    ImageView menu;
    @BindView(R.id.topText)
    TextView show;

    @BindView(R.id.item11)
    TextView login;

    @OnClick(R.id.scan)
    public void scan(View v) {
        Intent intent = new Intent(this, MipcaActivityCapture.class);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    String cityId;
    ViewPager vp;
    TextView num1;
    TextView num2;
    HospitalOrmDao dao1;
    NewsOrmDao dao2;
    LinearLayout favoriteHos;
    LinearLayout favoriteNews;

    ImageView headerPic;
    TextView[] items;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        dao1 = new HospitalOrmDao(this);
        dao2 = new NewsOrmDao(this);
        vp = (ViewPager) findViewById(R.id.viewpage);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.ViewPageRadioGroup);
        final TextView topText = (TextView) findViewById(R.id.topText);
        //回到顶部
        topText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    visitFragment.toTop();
                }else if (position == 1) {
                    consulationFragment.listViewToTop();
                }
            }
        });

        items = new TextView[10];
        items[0] = (TextView) findViewById(R.id.item1);
        items[1] = (TextView) findViewById(R.id.item2);
        items[2] = (TextView) findViewById(R.id.item3);
        items[3] = (TextView) findViewById(R.id.item4);
        items[4] = (TextView) findViewById(R.id.item5);
        items[5] = (TextView) findViewById(R.id.item6);
        items[6] = (TextView) findViewById(R.id.item7);
        items[7] = (TextView) findViewById(R.id.item8);
        items[8] = (TextView) findViewById(R.id.item9);
        items[9] = (TextView) findViewById(R.id.item10);
        for (int i = 0; i < 10; i++) {
            items[i].setOnClickListener(this);
        }
        //收藏监听
        favoriteHos = (LinearLayout) findViewById(R.id.favoriteHos);
        favoriteNews = (LinearLayout) findViewById(R.id.favoriteNews);
        favoriteHos.setOnClickListener(this);
        favoriteNews.setOnClickListener(this);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);

        //自定义圆形ImageView头像
        headerPic = (CircleImageView) findViewById(R.id.headerPIC);
        headerPic.setOnClickListener(this);

        updateHeader();//更新头像

        /**
         * 获取定位城市
         */
        SharedPreferences sp = getSharedPreferences("CITY", MODE_PRIVATE);
        dingwei.setText(sp.getString("city", "北京"));
        dingwei.setOnClickListener(this);

        //设置滑动背景
        slidingPane.setSliderFadeColor(getResources().getColor(R.color.slidingPaneColor));
        //这个方法？
        slidingPane.setCoveredFadeColor(getResources().getColor(R.color.colorAccent));

        slidingPane.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                start.setScaleY(slideOffset / 2 + 0.5F);
                start.setScaleX(slideOffset / 2 + 0.5F);
                start.setAlpha(slideOffset);
                main.setScaleY(1 - slideOffset / 5);
            }

            @Override
            public void onPanelOpened(View arg0) {
            }

            @Override
            public void onPanelClosed(View arg0) {
            }

        });
        //抽屉布局
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                drawerLayout.openDrawer(Gravity.LEFT);
                slidingPane.openPane();
            }
        });

        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        RelativeLayout start = (RelativeLayout) findViewById(R.id.start);
        start.setMinimumWidth(dm.widthPixels * 3 / 5);

        fragments = new ArrayList<>();
        initFragmentData();//初始化Fragment数据
        VPGuideFragmentAdapter adapter = new VPGuideFragmentAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                RadioButton rb = (RadioButton) rg.getChildAt(position);
                rb.setChecked(true);

                switch (position) {
                    case 0:
                        topText.setText(rb.getText());
                        break;
                    case 1:
                        topText.setText(rb.getText());
                        break;
                    case 2:
                        topText.setText(rb.getText());
                        break;
                    case 3:
                        topText.setText(rb.getText());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((RadioButton) rg.getChildAt(0)).setChecked(true);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.item11:
                        vp.setCurrentItem(0, false);
                        position = 0;
//                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        dingwei.setVisibility(View.VISIBLE);
                        scan.setVisibility(View.GONE);
                        menu.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item22:
                        vp.setCurrentItem(1, false);
                        position = 1;
//                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        dingwei.setVisibility(View.GONE);
                        scan.setVisibility(View.GONE);
                        menu.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item33:
                        vp.setCurrentItem(2, false);
                        position = 2;
//                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        dingwei.setVisibility(View.GONE);
                        scan.setVisibility(View.VISIBLE);
                        menu.setVisibility(View.GONE);
                        break;
                    case R.id.item44:
                        vp.setCurrentItem(3, false);
                        position = 3;
//                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        dingwei.setVisibility(View.GONE);
                        scan.setVisibility(View.GONE);
                        menu.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 更新头像
     */
    public void updateHeader() {
        /**
         * 获取头像
         */
        SharedPreferences sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String nickName = sp1.getString("nickName", "立即登录");
        String figureurl_qq_2 = sp1.getString("figureUrl","");
        //Volley图片加载库 三级缓存机制
        ImageLoader mImageLoader = new ImageLoader(Volley.newRequestQueue(this), new NetImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                headerPic,R.drawable.head_man,R.drawable.head_man);
        mImageLoader.get(figureurl_qq_2, listener);
        items[0].setText(nickName);

    }

    @Override
    protected void onResume() {
        super.onResume();
        num1.setText(dao1.findAllHospital().size() + "");
        num2.setText(dao2.findAllNews().size() + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dingwei:
                Intent intent = new Intent(this, CityDWActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.up_down,R.anim.pause);
                break;

            case R.id.item1:
                Intent loginintent = new Intent(this, LoginActivity.class);
                startActivityForResult(loginintent, 2);
                break;
            case R.id.headerPIC:
                Intent intentheaderPIC = new Intent(this, LoginActivity.class);
                startActivityForResult(intentheaderPIC, 2);
                break;

            case R.id.item2:
                Intent shimingIntent = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent);
                break;
            case R.id.item3:
                Intent shimingIntent1 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent1);
                break;
            case R.id.item4:
                Intent shimingIntent2 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent2);
                break;
            case R.id.item5:
                Intent shimingIntent3 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent3);
                break;

            case R.id.item6:
                Intent shimingIntent4 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent4);
                break;
            case R.id.item7:
                Intent shimingIntent5 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent5);
                break;
            case R.id.item8:
                Intent shimingIntent6 = new Intent(this, ShiMingActivity.class);
                startActivity(shimingIntent6);
                break;
            case R.id.item9:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.item10:
                DialogManager.exitDialog(this);
                DialogManager.setOnChangedListener(new DialogManager.OnChangedListener() {
                    @Override
                    public void OnChange() {
                        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("nickName","立即登录").putString("figureUrl","").commit();
                        finish();
                    }
                });
                break;

            case R.id.favoriteHos:
                Intent favoriteHos = new Intent(this, FavoriteActivity.class);

                startActivity(favoriteHos);
                break;

            case R.id.favoriteNews:
                Intent favoriteNews = new Intent(this, FavoriteActivity.class);
                favoriteNews.putExtra("news", 1);
                startActivity(favoriteNews);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String city = data.getStringExtra("city");
            dingwei.setText(city);
            cityId = data.getStringExtra("cityID");
            visitFragment.update(cityId);//调用Fragment中的更行数据方法
        }
        if (requestCode == 2 && resultCode == 2) {
            updateHeader();
        }

        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //显示扫描到的内容
            Toast.makeText(this,bundle.getString("result"),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化加载碎片数据
     */
    public void initFragmentData() {
        visitFragment = new VisitFragment();
        fragments.add(visitFragment);

        consulationFragment = new ConsulationFragment();
        fragments.add(consulationFragment);

        chatFragment = new ChatFragment();
        fragments.add(chatFragment);

        mineFragment = new MineFragment();
        fragments.add(mineFragment);
    }


    private static boolean isExit = false;
    //按两次退出
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    //物理按键点击方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 1500);//两秒之后更改 isExit=false;
        } else {
            finish();//结束当前Activity
            System.exit(0);//退出程序
        }
    }

}
