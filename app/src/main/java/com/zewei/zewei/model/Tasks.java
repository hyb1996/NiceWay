package com.zewei.zewei.model;

import com.zewei.zewei.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Stardust on 2018/2/19.
 */

public class Tasks {


    public static final Task LEARNING = new Task("学习", R.drawable.circle, 0, Task.STATE_UNSET, TimeUnit.MINUTES.toMillis(30));
    public static final Task WORKING = new Task("工作", R.drawable.rhombus, 0, Task.STATE_UNSET, TimeUnit.MINUTES.toMillis(30));
    public static final Task SLEEPING = new Task("睡眠", R.drawable.triangle, 0, Task.STATE_UNSET, TimeUnit.MINUTES.toMillis(30));
    public static final Task DAILY_LIFE = new Task("生活", R.drawable.square, 0, Task.STATE_UNSET, TimeUnit.MINUTES.toMillis(30));

    public static List<Task> newDefaultList() {
        return new ArrayList<>(Arrays.asList(LEARNING, WORKING, SLEEPING, DAILY_LIFE));
    }
}
