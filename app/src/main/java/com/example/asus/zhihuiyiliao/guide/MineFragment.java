package com.example.asus.zhihuiyiliao.guide;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.SelfWidget.CircleImageView;
import com.example.asus.zhihuiyiliao.SelfWidget.DialogManager;
import com.example.asus.zhihuiyiliao.dao.HospitalOrmDao;
import com.example.asus.zhihuiyiliao.dao.NewsOrmDao;
import com.example.asus.zhihuiyiliao.entity.User;
import com.example.asus.zhihuiyiliao.login.LoginActivity;
import com.example.asus.zhihuiyiliao.mine.FavoriteActivity;
import com.example.asus.zhihuiyiliao.mine.SettingActivity;
import com.example.asus.zhihuiyiliao.mine.ShiMingActivity;
import com.example.asus.zhihuiyiliao.util.NetImageCache;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    View v;
    LinearLayout favoriteHos;
    LinearLayout favoriteNews;
    TextView num1;
    TextView num2;
    HospitalOrmDao dao1;
    NewsOrmDao dao2;

    ImageView headerPic;
    TextView[] items;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_mine, container, false);
            dao1 = new HospitalOrmDao(getActivity());
            dao2 = new NewsOrmDao(getActivity());

            items = new TextView[10];
            items[0] = (TextView) v.findViewById(R.id.item1);
            items[1] = (TextView) v.findViewById(R.id.item2);
            items[2] = (TextView) v.findViewById(R.id.item3);
            items[3] = (TextView) v.findViewById(R.id.item4);
            items[4] = (TextView) v.findViewById(R.id.item5);
            items[5] = (TextView) v.findViewById(R.id.item6);
            items[6] = (TextView) v.findViewById(R.id.item7);
            items[7] = (TextView) v.findViewById(R.id.item8);
            items[8] = (TextView) v.findViewById(R.id.item9);
            items[9] = (TextView) v.findViewById(R.id.item10);
            for (int i = 0; i < 10; i++) {
                items[i].setOnClickListener(this);
            }
            favoriteHos = (LinearLayout) v.findViewById(R.id.favoriteHos);
            favoriteNews = (LinearLayout) v.findViewById(R.id.favoriteNews);
            favoriteHos.setOnClickListener(this);
            favoriteNews.setOnClickListener(this);

            headerPic = (CircleImageView) v.findViewById(R.id.headerPIC);
            headerPic.setOnClickListener(this);

            num1 = (TextView) v.findViewById(R.id.num1);
            num2 = (TextView) v.findViewById(R.id.num2);

            updateHeader();

        }

        return v;
    }

    /**
     * 更新头像
     */
    public void updateHeader() {
        SharedPreferences sp = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
        String nickName = sp.getString("nickName", "立即登录");
        String figureurl_qq_2 = sp.getString("figureUrl","");

        ImageLoader mImageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), new NetImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                headerPic,R.drawable.head_man,R.drawable.head_man);
        mImageLoader.get(figureurl_qq_2, listener);
        items[0].setText(nickName);
    }

    @Override
    public void onResume() {
        super.onResume();
        num1.setText(dao1.findAllHospital().size() + "");
        num2.setText(dao2.findAllNews().size() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 2);
//                startActivity(intent);
                break;

            case R.id.headerPIC:
                Intent intentheaderPIC = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intentheaderPIC, 2);
                break;

            case R.id.item2:
                Intent shimingIntent = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent);
                break;
            case R.id.item3:
                Intent shimingIntent1 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent1);
                break;
            case R.id.item4:
                Intent shimingIntent2 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent2);
                break;
            case R.id.item5:
                Intent shimingIntent3 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent3);
                break;

            case R.id.item6:
                Intent shimingIntent4 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent4);
                break;
            case R.id.item7:
                Intent shimingIntent5 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent5);
                break;
            case R.id.item8:
                Intent shimingIntent6 = new Intent(getActivity(), ShiMingActivity.class);
                startActivity(shimingIntent6);
                break;
            case R.id.item9:
                Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(settingIntent);
                break;

            case R.id.item10:
                DialogManager.exitDialog(getActivity());
                DialogManager.setOnChangedListener(new DialogManager.OnChangedListener() {
                    @Override
                    public void OnChange() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("nickName","立即登录").putString("figureUrl","").commit();
                        getActivity().finish();
                    }
                });
                break;

            case R.id.favoriteHos:
                Intent favoriteHos = new Intent(getActivity(), FavoriteActivity.class);

                startActivity(favoriteHos);
                break;

            case R.id.favoriteNews:
                Intent favoriteNews = new Intent(getActivity(), FavoriteActivity.class);
                favoriteNews.putExtra("news", 1);
                startActivity(favoriteNews);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            updateHeader();

            //更新侧边栏的头像
            GuideActivity guideActivity = (GuideActivity) getActivity();
            guideActivity.updateHeader();
        }
    }
}
