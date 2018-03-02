package com.zewei.zewei.model;

/**
 * Created by Stardust on 2018/2/19.
 */

public class Task {


    public static final int STATE_UNSET = 0;
    public static final int STATE_IN_PROGRESS = 1;
    public static final int STATE_FINISHED = 2;

    private String mName;

    private int mIcon;

    private long mStartTime;


    private int mState;

    private long mDuration;


    public Task(String name, int icon, long startTime, int state, long duration) {
        mName = name;
        mIcon = icon;
        mStartTime = startTime;
        mState = state;
        mDuration = duration;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }
}
