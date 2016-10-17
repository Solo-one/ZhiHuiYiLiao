package com.example.asus.zhihuiyiliao.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by asus on 2016/8/23.
 */
@DatabaseTable(tableName = "favoritehos")
public class Hospital implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;
    @DatabaseField
    private String level;
    @DatabaseField
    private String url;
    @DatabaseField
    private String img;
    @DatabaseField
    private String address;
    @DatabaseField
    private String gobus;
    @DatabaseField
    private double x;//经度
    @DatabaseField
    private double y;//维度

    public Hospital() {
    }

    public Hospital(String name, String level, String url, String img, String address, String gobus) {
        this.name = name;
        this.level = level;
        this.url = url;
        this.img = img;
        this.address = address;
        this.gobus = gobus;
    }


    public Hospital(String name, String level, String url, String img, String address, String gobus, double x, double y) {
        this.name = name;
        this.level = level;
        this.url = url;
        this.img = img;
        this.address = address;
        this.gobus = gobus;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGobus() {
        return gobus;
    }

    public void setGobus(String gobus) {
        this.gobus = gobus;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
