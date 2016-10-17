package com.example.asus.zhihuiyiliao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.zhihuiyiliao.entity.City;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.entity.News;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by asus on 2016/8/27.
 * ormlite 数据库帮助类
 */
public class HosOrmLiteSqliteOpenHelper extends OrmLiteSqliteOpenHelper {


    private static final String DB_NAME = "myhospital.db";//数据库名称
    private static final int DB_VERSION = 1;//数据库版本

    /**
     * 单例模式 私有化构造函数
     * @param context
     */
    private HosOrmLiteSqliteOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    private static HosOrmLiteSqliteOpenHelper dataBaseHelper;//保证只实例化一个 DataBaseHelper
    /**
     * synchronized //线程同步，保证线程安全
     * @param context
     * @return
     */
    public synchronized static HosOrmLiteSqliteOpenHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new HosOrmLiteSqliteOpenHelper(context);
        }
        return dataBaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Hospital.class);
            TableUtils.createTableIfNotExists(connectionSource, News.class);
            TableUtils.createTableIfNotExists(connectionSource, City.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,Hospital.class,true);
            TableUtils.createTable(connectionSource, Hospital.class);//更新新版本，需要重新创建表
            TableUtils.dropTable(connectionSource, News.class, true);
            TableUtils.createTable(connectionSource,News.class);//更新新版本，需要重新创建表
            TableUtils.dropTable(connectionSource, City.class, true);
            TableUtils.createTable(connectionSource,City.class);//更新新版本，需要重新创建表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }

}
