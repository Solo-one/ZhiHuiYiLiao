package com.example.asus.zhihuiyiliao.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by asus on 2016/8/25.
 */
@DatabaseTable(tableName = "favoriteNews")
public class News implements Serializable{


    @DatabaseField
    private int id;

    @DatabaseField
    private String title;
    @DatabaseField
    private String time;
    @DatabaseField
    private String description;
    @DatabaseField
    private String picUrl;

    @DatabaseField
    private String urlsource;

    @DatabaseField
    private int count;

    private int newstype;

    //需要有无参构造函数
    public News(){
    }

    public News(String title, String time, String description, String picUrl) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.picUrl = picUrl;
    }

    public News(int id, String title, String time, String description, String picUrl) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.description = description;
        this.picUrl = picUrl;
    }


    public News(int id, String title, String time, String description, String picUrl, int count) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.description = description;
        this.picUrl = picUrl;
        this.count = count;
    }

    public News(int id, String title, String time, String description, String picUrl, String urlsource, int count) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.description = description;
        this.picUrl = picUrl;
        this.urlsource = urlsource;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNewstype() {
        return newstype;
    }

    public void setNewstype(int newstype) {
        this.newstype = newstype;
    }

    public String getUrlsource() {
        return urlsource;
    }

    public void setUrlsource(String urlsource) {
        this.urlsource = urlsource;
    }
}
