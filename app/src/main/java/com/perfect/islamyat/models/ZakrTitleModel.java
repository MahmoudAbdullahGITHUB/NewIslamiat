package com.perfect.islamyat.models;

public class ZakrTitleModel {
    int id = 0;
    String title = "";
    String color="";

    public ZakrTitleModel (int id, String title,String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
