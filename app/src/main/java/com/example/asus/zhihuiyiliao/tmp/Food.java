package com.example.asus.zhihuiyiliao.tmp;

/**
 * Created by asus on 2016/8/28.
 */
public class Food {
    private int id;
    private String title;
    private String imtro;
    private String ingredients;
    private String burden;
    private String albums;

    public Food() {
    }

    public Food(int id, String title, String imtro, String ingredients, String burden, String albums) {
        this.id = id;
        this.title = title;
        this.imtro = imtro;
        this.ingredients = ingredients;
        this.burden = burden;
        this.albums = albums;
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

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }
}
