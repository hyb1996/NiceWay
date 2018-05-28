package com.zewei.zewei.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.zewei.zewei.model.DiscoveryPost.GSON;
import static com.zewei.zewei.model.DiscoveryPost.readInputStream;

public class Hotspot {

    @SerializedName("content")
    private String mContent;

    @SerializedName("heat")
    private int mHeat;

    @SerializedName("tag")
    private String mTag;

    public Hotspot() {
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getHeat() {
        return mHeat;
    }

    public void setHeat(int heat) {
        mHeat = heat;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public static List<Hotspot> fromJson(String json) {
        return GSON.fromJson(json, new TypeToken<List<Hotspot>>() {
        }.getType());
    }


    public static List<Hotspot> fromStream(InputStream s) throws IOException {
        return fromJson(readInputStream(s));
    }
}
