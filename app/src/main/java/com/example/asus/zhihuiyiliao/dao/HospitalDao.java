package com.example.asus.zhihuiyiliao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.zhihuiyiliao.db.HospitalSQLiteOpenHelper;
import com.example.asus.zhihuiyiliao.entity.Hospital;
import com.example.asus.zhihuiyiliao.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/8/24.
 */
public class HospitalDao {
    private HospitalSQLiteOpenHelper openHelper;

    //开启数据库
    public HospitalDao(Context context) {
        this.openHelper = new HospitalSQLiteOpenHelper(context);
    }

    /**
     * 查找所有hospital
     * @param
     * @return
     */
    public List<Hospital> findAllHospital(){
        List<Hospital> list = new ArrayList<>();
        SQLiteDatabase db = this.openHelper.getReadableDatabase();
        Cursor cursor = db.query(
                Constants.Table.TABLE_NAME_1,
                new String[]{
                        Constants.Table.COL_ID,
                        Constants.Table.COL_NAME,
                        Constants.Table.COL_LEVEL,
                        Constants.Table.COL_URL,
                        Constants.Table.COL_IMG,
                        Constants.Table.COL_ADDRESS,
                        Constants.Table.COL_GOBUS
                },null,null,null,null,null,null
        );
        while(cursor.moveToNext()){
            Hospital nn = new Hospital();
            nn.setName(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_NAME)));
            nn.setLevel(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_LEVEL)));
            nn.setUrl(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_URL)));
            nn.setImg(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_IMG)));
            nn.setAddress(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_ADDRESS)));
            nn.setGobus(cursor.getString(cursor.getColumnIndex(Constants.Table.COL_GOBUS)));
            list.add(nn);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 向数据中写入单条数据
     * @param nn
     * @return
     */
    public long addHospital(Hospital nn){
        SQLiteDatabase db = this.openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.Table.COL_NAME,nn.getName());
        values.put(Constants.Table.COL_LEVEL,nn.getLevel());
        values.put(Constants.Table.COL_URL,nn.getUrl());
        values.put(Constants.Table.COL_IMG,nn.getImg());
        values.put(Constants.Table.COL_ADDRESS,nn.getAddress());
        values.put(Constants.Table.COL_GOBUS, nn.getGobus());
        long id = db.insert(
                Constants.Table.TABLE_NAME_1,
                null,
                values
        );
        db.close();
        return id;
    }

    /**
     * 向数据库写入多条数据
     * @param
     */
    public void addHospital(List<Hospital> HospitalList){
        for(Hospital nn : HospitalList){
            addHospital(nn);
        }
    }

    /**
     * 根据医院url删除指定数据
     */
    public void removeHospital(String url) {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();
        db.delete(Constants.Table.TABLE_NAME_1,Constants.Table.COL_URL+" = ?",
                new String[]{url});
        db.close();
    }

    /**
     * 删除所有数据
     * @param
     */
    public void removeHospital() {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();
        db.delete(Constants.Table.TABLE_NAME_1,null, null);
        db.close();
    }
}
