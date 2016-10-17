package com.example.asus.zhihuiyiliao.util;

/**
 * Created by asus on 2016/8/20.
 */
public class Constants {

    //数据库表类
    public static final class Table {
        //医院类表名
        public static final String TABLE_NAME_1 = "HOSPITAL";

        //表的字段名
        public static final String COL_ID = "ID";
        public static final String COL_NAME = "NAME";
        public static final String COL_LEVEL = "LEVEL";
        public static final String COL_URL = "HOSURL";
        public static final String COL_IMG = "IMG";
        public static final String COL_ADDRESS = "ADDRESS";
        public static final String COL_GOBUS = "GOBUS";

        //创建医院表的建表语句
        public static String getCreateTableHospital() {
            String sql = "CREATE TABLE IF NOT EXISTS "
                    + TABLE_NAME_1 +"("
                    + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NAME +" VARCHAR(100),"
                    + COL_LEVEL +" VARCHAR(50),"
                    + COL_URL +" VARCHAR(100),"
                    + COL_IMG +" VARCHAR(200),"
                    + COL_ADDRESS +" VARCHAR(100),"
                    + COL_GOBUS +" TEXT"
                    + ")";
            return sql;
        }
    }
}
