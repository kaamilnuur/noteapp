package com.example.mynoteapp;

import android.widget.RelativeLayout;

public class model {
    String title, discription;
    String id;

    public model(String id, String title, String discription) {
        this.title = title;
        this.discription = discription;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscription() {
        return discription;
    }

    public String getid() {
        return id;
    }
}
