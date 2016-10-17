package com.example.asus.zhihuiyiliao.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by asus on 2016/8/22.
 */
@DatabaseTable(tableName = "cityTable")
public class City {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int cityId;
    @DatabaseField
    private String cityName;
    @DatabaseField
    private String province;
    @DatabaseField
    private double x;
    @DatabaseField
    private double y;

    public City() {
    }

    public City(String cityName) {
        this.cityName = cityName;
    }

    public City(int cityId, String cityName, String province, double x, double y) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.province = province;
        this.x = x;
        this.y = y;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
