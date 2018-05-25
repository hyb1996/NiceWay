package com.zewei.zewei.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DiscoveryPost {

    static final Gson GSON = new Gson();


    public static class Comment {

        @SerializedName("user")
        private User mUser;
        @SerializedName("content")
        private String mContent;

        public User getUser() {
            return mUser;
        }

        public void setUser(User user) {
            mUser = user;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }
    }

    @SerializedName("user")
    private User mUser;

    @SerializedName("pictureUrl")
    private String mPictureUrl;

    @SerializedName("likeCount")
    private int mLikeCount;
    @SerializedName("starCount")
    private int mStarCount;

    @SerializedName("comments")
    private List<Comment> mComments;

    @SerializedName("commentCount")
    private int mCommentCount;

    @SerializedName("content")
    private String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }


    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void loadPictureInto(ImageView imageView) {
        Glide.with(imageView)
                .load(mPictureUrl)
                .into(imageView);
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        mLikeCount = likeCount;
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarCount(int starCount) {
        mStarCount = starCount;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public static List<DiscoveryPost> fromJson(String json) {
        return GSON.fromJson(json, new TypeToken<List<DiscoveryPost>>() {
        }.getType());
    }


    public static List<DiscoveryPost> fromStream(InputStream s) throws IOException {
        return fromJson(readInputStream(s));
    }


    private static String readInputStream(InputStream stream) throws IOException {
        byte[] data = new byte[stream.available()];
        stream.read(data);
        stream.close();
        return new String(data);
    }
}
