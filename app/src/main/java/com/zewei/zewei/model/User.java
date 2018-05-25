package com.zewei.zewei.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
