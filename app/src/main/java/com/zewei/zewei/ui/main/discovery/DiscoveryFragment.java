package com.zewei.zewei.ui.main.discovery;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@EFragment(R.layout.fragment_discovery)
public class DiscoveryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @ViewById(R.id.posts)
    RecyclerView mPostsView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @SuppressLint("CheckResult")
    @AfterViews
    void setupViews() {
        mPostsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPostsView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.colorDivider)
                .sizeResId(R.dimen.widthDivider)
                .showLastDivider()
                .build());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getContext().getTheme()));
        Observable.just("posts.json")
                .map(path -> getContext().getAssets().open(path))
                .map(DiscoveryPost::fromStream)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> mPostsView.setAdapter(new PostAdapter(posts)));
    }



    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 1500);
    }
}
