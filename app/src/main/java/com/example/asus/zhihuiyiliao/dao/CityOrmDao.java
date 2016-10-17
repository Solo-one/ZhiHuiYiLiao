package com.example.asus.zhihuiyiliao.dao;

import android.content.Context;
import android.util.Log;

import com.example.asus.zhihuiyiliao.db.HosOrmLiteSqliteOpenHelper;
import com.example.asus.zhihuiyiliao.entity.City;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/6.
 */
public class CityOrmDao {
    //OrmLiteSqlite 封装的数据库操作对象
    private Dao<City, Integer> citydao;

    /**
     * @param context
     */
    public CityOrmDao(Context context) {
        HosOrmLiteSqliteOpenHelper dataBaseHelper = HosOrmLiteSqliteOpenHelper.getInstance(context);//获得DataBaseHelper实例
        try {
            citydao = dataBaseHelper.getDao(City.class);//getDao()方法获得userdao对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过dao对象向数据库写入数据
     *
     * @param
     * @return
     */
    public long addCity(City city) {
        int id = 0;
        try {
            id = citydao.create(city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 批量写入
     *
     * @param
     */
    public void addCities(List<City> cities) {
        for (City f : cities) {
            addCity(f);
        }
    }

    /**
     * 批量写入MAp
     *
     * @param
     */
    public void addCitiesMap(List<List<City>> cities) {
        for (List<City> f : cities) {
            for (City fs : f) {
                addCity(fs);
            }
        }
    }


    /**
     * 通过dao对象查找数据库数据
     *
     * @return
     */
    public City findCityByisGroup(String cityName) {

        City city = null;
        List<City> is;

        QueryBuilder<City, Integer> builder = citydao.queryBuilder();

        try {
            is = builder.where().eq("cityName", cityName).query();

            if (is.size() != 0) {
                city = is.get(0);
            }

            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     */
    public void deleteCities() {
        DeleteBuilder<City, Integer> builder = citydao.deleteBuilder();
        try {
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
