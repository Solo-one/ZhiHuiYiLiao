package com.example.asus.zhihuiyiliao.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2016/8/25.
 */
public class NewsType implements Parcelable {
    private int type;
    private String name;
    private String url;

    /**
     * Parcelable 序列化对象，传递效率比Serializable 高
     * 实现Parcelable 接口的方法
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(name);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<NewsType> CREATOR = new Parcelable.Creator<NewsType>(){

        @Override
        public NewsType createFromParcel(Parcel source) {
            NewsType news = new NewsType();
            //按照writeToParcel（）写入顺序 读取数据
            news.setType(source.readInt());
            news.setName(source.readString());
            news.setUrl(source.readString());
            return news;
        }

        @Override
        public NewsType[] newArray(int size) {
            return new NewsType[0];
        }
    };



    public NewsType() {
    }

    public NewsType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public NewsType(int type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
