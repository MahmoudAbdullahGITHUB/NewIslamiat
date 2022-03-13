package com.perfect.islamyat.models;

public class infoModel {
    int id = 0;
    String title = "";
    String description = "";

    public infoModel (int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
