package com.example.asus.zhihuiyiliao.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.asus.zhihuiyiliao.R;
import com.example.asus.zhihuiyiliao.guide.MyBaseActivity;
import com.example.asus.zhihuiyiliao.util.StatusBarUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class LoginActivity extends SwipeBackActivity {

    private LoginHelper loginHelper;//登录帮助类

    private Tencent mTencent;
    //appid
    private static String APP_ID = "1105662816";

    //shareSDK网站给出的appkey
    private static String APP_KEY = "16a5ca3b82915";

    //获取权限列表。所有权限为 all
    private static String SCOPE = "all";
    //回调接口
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private IUiListener shareListener;


    @OnClick(R.id.back)
    public void back(View v) {
        finish();
//        overridePendingTransition(R.anim.right_out,R.anim.in);
    }

    @OnClick(R.id.denglu)
    public void login(View v) {
        Toast.makeText(this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
        finish();
    }

    @BindView(R.id.footer)
    RelativeLayout footer;

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.myblack);//修改状态栏颜色
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        /*footer.setOnTouchListener(this);
        footer.setLongClickable(true);*/

        ShareSDK.initSDK(this, APP_KEY);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }

    @OnClick(R.id.login_share)
    public void shareSDK(View view) {
        shareToQQByShareSDK();
    }

    private void shareToQQByShareSDK() {
        ShareSDK.initSDK(this);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://blog.csdn.net/u013451048");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("功能测试，请自动忽略");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        //oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("功能测试：qq空间评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("功能测试：app name");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        // 启动分享GUI
        oks.show(this);
    }




    @OnClick(R.id.login_qq)
    public void loginQQ(View v) {
        initQqLogin();//初始化QQ登录分享的需要的资源
        qqLogin(v);
    }

    //初始化QQ登录分享的需要的资源
    private void initQqLogin() {
        mTencent = Tencent.createInstance(APP_ID, this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后回调该方法
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                //设置openid，如果不设置这一步的话无法获取用户信息
                JSONObject jo = (JSONObject) o;
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };
        //获取用户信息的回调接口
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                try {
                    JSONObject info = (JSONObject) o;
                    Log.e("JO:", jo.toString());
                    String nickName = info.getString("nickname");
                    String gender = info.getString("gender");
                    String figureurl_qq_2 = info.getString("figureurl_qq_2");

                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("nickName", "Hi~ ," + nickName).putString("figureUrl", figureurl_qq_2).commit();//写入本地文件数据

                    setResult(2, new Intent());
                    Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                    finish();//关闭当前Activtiy

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }

        };

        //qq分享的回调接口
        shareListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //分享成功后回调
                Toast.makeText(LoginActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError uiError) {
                //分享失败后回调
            }

            @Override
            public void onCancel() {
                //取消分享后回调
            }
        };
    }

    //qq登录功能
    public void qqLogin(View view) {
        mTencent.login(this, SCOPE, loginListener);
    }

    //获取用户信息
    private void getUserInfo() {
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(userInfoListener);
    }

    //qq分享
    public void qqShare(View view) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "qq第三方登录");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "android实现qq第三方登录，并获取用户信息");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/u013451048/article/details/52352810");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
        mTencent.shareToQQ(this, params, shareListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //官方文档上面的是错误的
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
        //resultCode 是log出来的，官方文档里给的那个属性是没有的
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                getUserInfo();
            }
        }
    }

    /**
     * QQ 登录按钮
     * @param v
     */
   /* @OnClick(R.id.login_qq)
    public void loginQQ(View v) {
        loginHelper = new LoginHelper(this);//初始化QQ登录 资源
        loginHelper.qqLogin();//QQ 登录

        loginHelper.setLoginListener(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    loginHelper.mTencent.setOpenId(openID);
                    loginHelper.mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(LoginActivity.this, "登录失败，请检查网络。", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        });

        //设置用户登录 监听
        loginHelper.setUserInfoListener(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                try {
                    JSONObject info = (JSONObject) o;
                    Log.e("JO:", jo.toString());
                    String nickName = info.getString("nickname");
                    String gender = info.getString("gender");
                    String figureurl_qq_2 = info.getString("figureurl_qq_2");

                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("nickName","Hi~ ,"+nickName).putString("figureUrl",figureurl_qq_2).commit();//写入本地文件数据

                    setResult(2,new Intent());

                    Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                    finish();//关闭当前Activtiy
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //官方文档上面的是错误的
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
        //resultCode 是log出来的，官方文档里给的那个属性是没有的
        super.onActivityResult(requestCode, resultCode, data);//必须调用super方法

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, LoginHelper.loginListener);
                Tencent.handleResultData(data, LoginHelper.loginListener);
                loginHelper.getUserInfo();
            }
        }
    }
*/
}
