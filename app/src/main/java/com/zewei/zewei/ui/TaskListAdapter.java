package com.zewei.zewei.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;
import com.zewei.zewei.R;
import com.zewei.zewei.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stardust on 2018/2/19.
 */

public class TaskListAdapter extends DragSortAdapter<TaskViewHolder> {

    private List<Task> mTaskList = new ArrayList<>();

    public TaskListAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList.clear();
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPositionForId(long id) {
        return (int) id;
    }

    @Override
    public boolean move(int fromPosition, int toPosition) {
        Task task = mTaskList.remove(fromPosition);
        if (fromPosition < toPosition) {
            toPosition--;
        }
        mTaskList.add(toPosition, task);
        return true;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
