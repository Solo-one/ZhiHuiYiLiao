package com.example.asus.zhihuiyiliao.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asus on 2016/9/1.
 */
public class LoginHelper {

    //appid
    private static String APP_ID = "1105662816";
    //获取权限列表。所有权限为 all
    private static String SCOPE = "all";

    private Context mContext;
    public static Tencent mTencent;//属于腾讯SDK架包文件
    //回调接口
    public static IUiListener loginListener;//登录接口
    public static IUiListener userInfoListener;//用户信息接口
    public static IUiListener shareListener;//分享接口

    public LoginHelper(final Context mContext) {
        this.mContext = mContext;
        this.mTencent = Tencent.createInstance(APP_ID, mContext);
    }

    /**
     * 设置 qq登录监听
     * @param loginListener
     */
    public void setLoginListener(IUiListener loginListener) {
        this.loginListener = loginListener;
    }

    /**
     * 设置用户信息监听
     * @param userInfoListener
     */
    public void setUserInfoListener(IUiListener userInfoListener) {
        this.userInfoListener = userInfoListener;
    }

    /**
     * 分享监听
     * @param shareListener
     */
    public void setShareListener(IUiListener shareListener) {
        this.shareListener = shareListener;
    }

    //qq登录功能
    public void qqLogin() {
        mTencent.login((Activity) mContext, SCOPE, loginListener);
    }

    //获取用户信息
    public void getUserInfo(){
        UserInfo info = new UserInfo(mContext, mTencent.getQQToken());
        info.getUserInfo(userInfoListener);
    }

    //qq分享
    public void qqshare(){
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "qq第三方登录");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "android实现qq第三方登录，并获取用户信息");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/u013451048/article/details/52352810");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
        mTencent.shareToQQ((Activity) mContext, params, shareListener);
    }

}
