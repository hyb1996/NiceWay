package com.zewei.zewei.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;
import com.zewei.zewei.R;
import com.zewei.zewei.model.Task;

import java.util.concurrent.TimeUnit;

/**
 * Created by Stardust on 2018/2/19.
 */

public class TaskViewHolder extends DragSortAdapter.ViewHolder {

    TextView mName;

    ImageView mIcon;

    TextView mDuration;

    public TaskViewHolder(DragSortAdapter<TaskViewHolder> a, View itemView) {
        super(a, itemView);
        mName = itemView.findViewById(R.id.name);
        mDuration = itemView.findViewById(R.id.duration);
        mIcon = itemView.findViewById(R.id.icon);
    }

    @SuppressLint("SetTextI18n")
    public void bind(Task task) {
        mName.setText(task.getName());
        mIcon.setImageResource(task.getIcon());
        mDuration.setText(TimeUnit.MILLISECONDS.toMinutes(task.getDuration()) + "分钟");
    }
}
