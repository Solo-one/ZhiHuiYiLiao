package com.example.asus.zhihuiyiliao.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.zhihuiyiliao.util.Constants;

/**
 * Created by asus on 2016/8/24.
 * 常规数据库
 */
public class HospitalSQLiteOpenHelper extends SQLiteOpenHelper{

    //数据库名称
    private static final String DB_NAME = "zhihuiyiliao.db";//最先开始初始化 static
    //数据库版本号
    private static final int DB_VERSION = 1 ;

    public  HospitalSQLiteOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }


    public HospitalSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public HospitalSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = Constants.Table.getCreateTableHospital();
        db.execSQL(sql);//创建HOSPITAL 表格
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constants.Table.TABLE_NAME_1);//注意空格
        onCreate(db);//在此调用onCreate（）建表
    }
}
