package com.example.asus.zhihuiyiliao.entity;

import java.io.Serializable;

/**
 * Created by asus on 2016/9/1.
 */
public class User implements Serializable{
    private String nickname;//昵称
    private String gender;//性别
    private String figureurl_qq_1;//qq头像

    public User() {
    }

    public User(String nickname, String gender, String figureurl_qq_1) {
        this.nickname = nickname;
        this.gender = gender;
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }
}
