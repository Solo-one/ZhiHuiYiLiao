package com.example.asus.zhihuiyiliao.dao;

import android.content.Context;
import android.util.Log;

import com.example.asus.zhihuiyiliao.db.HosOrmLiteSqliteOpenHelper;
import com.example.asus.zhihuiyiliao.entity.News;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/8/27.
 */
public class NewsOrmDao {
    //OrmLiteSqlite 封装的数据库操作对象
    private Dao<News, Integer> newsdao;

    /**
     * @param context
     */
    public NewsOrmDao(Context context) {
        HosOrmLiteSqliteOpenHelper dataBaseHelper = HosOrmLiteSqliteOpenHelper.getInstance(context);//获得DataBaseHelper实例
        try {
            newsdao = dataBaseHelper.getDao(News.class);//getDao()方法获得userdao对象
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
    public long addNews(News favoriteNews) {
        int id = 0;
        try {
            id = newsdao.create(favoriteNews);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 批量写入
     * @param
     */
    public void addNews(List<News> favoriteNewsList) {
        for (News f : favoriteNewsList) {
            addNews(f);
        }
    }


    /**
     * 通过dao对象查找数据库数据
     *
     * @return
     */
    public List<News> findAllNews() {
        try {
            return newsdao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param title
     * @return
     */
    public boolean findNews(String title){
        boolean flag = false;
        List<News> is = new ArrayList<>();

        QueryBuilder<News, Integer> builder = newsdao.queryBuilder();
        try {
            is = builder.where()
                    .eq("title", title).query();
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
     *
     * @param
     */
    public void deleteNews(String title) {
        DeleteBuilder<News, Integer> builder = newsdao.deleteBuilder();
        try {
            builder.where()
                    .eq("title", title);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
