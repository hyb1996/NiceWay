package com.zewei.zewei.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.afollestad.materialdialogs.MaterialDialog;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zewei.zewei.R;
import com.zewei.zewei.model.Tasks;
import com.zewei.zewei.ui.timing.TimingActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Stardust on 2018/2/19.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.task_list)
    UltimateRecyclerView mTaskListView;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void setupViews() {
        setSupportActionBar(mToolbar);

        mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        TaskListAdapter adapter = new TaskListAdapter();
        mTaskListView.setAdapter(adapter);
        adapter.setTaskList(Tasks.newDefaultList());
        mTaskListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .margin(R.dimen.margin_divider, R.dimen.margin_divider)
                .colorResId(R.color.color_divider)
                .sizeResId(R.dimen.size_divider)
                .build());
    }

    @Click(R.id.fab)
    void fab() {
        showTaskEditDialog();
    }

    public void showTaskEditDialog() {
        new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_task_edit, false)
                .title("学习")
                .positiveText(R.string.ok)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
