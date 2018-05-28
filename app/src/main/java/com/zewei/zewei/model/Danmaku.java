package com.zewei.zewei.model;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.IDanmakuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import moe.haruue.reflect.EasyReflect;
import moe.haruue.reflect.Setter;

import static com.zewei.zewei.model.DiscoveryPost.GSON;
import static com.zewei.zewei.model.DiscoveryPost.readInputStream;

public class Danmaku {

    public interface DanmakuItemReflect {

        @Setter(name = "mTextColor")
        void setTextColor(int textColor);
    }

    static final Random RANDOM = new Random();

    @SerializedName("content")
    private String mContent;

    @SerializedName("textColor")
    private String mTextColor;

    @SerializedName("textSize")
    private int mTextSize = 15;

    @SerializedName("startX")
    private int mStartX;

    public Danmaku(String content) {
        mContent = content;
    }

    public Danmaku() {
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getTextColor() {
        return mTextColor;
    }

    public void setTextColor(String textColor) {
        mTextColor = textColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public int getStartX() {
        return mStartX;
    }

    public void setStartX(int startX) {
        mStartX = startX;
    }

    public IDanmakuItem asItem(Context context) {
        if (mStartX == 0) {
            mStartX = RANDOM.nextInt(1000);
        }
        DanmakuItem item = new DanmakuItem(context, getContent(), mStartX);
        DanmakuItemReflect reflect = EasyReflect.from(item, getClass().getClassLoader(), DanmakuItemReflect.class);
        if (mTextColor != null) {
            reflect.setTextColor(Color.parseColor(mTextColor));
        }
        if (mTextSize != 0) {
            item.setTextSize(mTextSize);
        }
        return item;
    }

    public static List<IDanmakuItem> jsonStreamToDanmakuItems(Context context, InputStream jsonStream) throws IOException {
        return jsonToDanmakuItems(context, readInputStream(jsonStream));

    }

    public static List<IDanmakuItem> jsonToDanmakuItems(Context context, String json) {
        return toDanmakuItems(context, GSON.fromJson(json, new TypeToken<List<Danmaku>>() {
        }.getType()));
    }

    private static List<IDanmakuItem> toDanmakuItems(Context context, List<Danmaku> list) {
        ArrayList<IDanmakuItem> danmakuItems = new ArrayList<>(list.size());
        for (Danmaku danmaku : list) {
            danmakuItems.add(danmaku.asItem(context));
        }
        return danmakuItems;
    }

}

