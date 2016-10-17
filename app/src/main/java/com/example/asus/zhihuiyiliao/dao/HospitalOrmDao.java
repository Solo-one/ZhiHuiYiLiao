package com.example.asus.zhihuiyiliao.dao;

import android.content.Context;

import com.example.asus.zhihuiyiliao.db.HosOrmLiteSqliteOpenHelper;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/8/27.
 */
public class HospitalOrmDao {
    //OrmLiteSqlite 封装的数据库操作对象
    private Dao<Hospital, Integer> hospitaldao;

    /**
     * @param context
     */
    public HospitalOrmDao(Context context) {
        HosOrmLiteSqliteOpenHelper dataBaseHelper = HosOrmLiteSqliteOpenHelper.getInstance(context);//获得DataBaseHelper实例
        try {
            hospitaldao = dataBaseHelper.getDao(Hospital.class);//getDao()方法获得userdao对象
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
    public long addHospital(Hospital favoriteHos) {
        int id = 0;
        try {
            id = hospitaldao.create(favoriteHos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 批量写入
     * @param favoriteHosList
     */
    public void addHospital(List<Hospital> favoriteHosList) {
        for (Hospital f : favoriteHosList) {
            addHospital(f);
        }
    }


    /**
     * 通过dao对象查找所有数据库数据
     *
     * @return
     */
    public List<Hospital> findAllHospital() {
        try {
            return hospitaldao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找指定条件的数据库
     * @param url
     * @param name
     * @return
     */
    public boolean findHospital(String url,String name){
        boolean flag = false;
        List<Hospital> is = new ArrayList<>();

        QueryBuilder<Hospital, Integer> builder = hospitaldao.queryBuilder();
        try {
          is = builder.where()
                    .eq("url", url)
                    .and()
                    .eq("name", name).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (is.size() == 0) {
            flag = false;
        }else {
            flag = true;
        }
        return  flag;
    }

    /**
     * 删除指定的数据元素
     * @param url
     * @param name
     */
    public void deleteHospital(String url,String name) {
        DeleteBuilder<Hospital, Integer> builder = hospitaldao.deleteBuilder();
        try {
            builder.where()
                    .eq("url", url)
                    .and()
                    .eq("name", name);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
