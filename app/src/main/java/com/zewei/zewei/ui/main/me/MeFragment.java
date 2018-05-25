package com.zewei.zewei.ui.main.me;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;
import com.zewei.zewei.ui.main.discovery.DiscoveryFragment;
import com.zewei.zewei.ui.main.discovery.PostAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@EFragment(R.layout.fragment_me)
public class MeFragment extends Fragment {


    @ViewById(R.id.posts)
    RecyclerView mPostsView;

    @ViewById(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressLint("CheckResult")
    @AfterViews
    void setupViews() {
        mPostsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPostsView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.colorDivider)
                .sizeResId(R.dimen.widthDivider)
                .showLastDivider()
                .build());
        Observable.just("posts.json")
                .map(path -> getContext().getAssets().open(path))
                .map(DiscoveryPost::fromStream)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> mPostsView.setAdapter(new PostAdapter(posts)));
    }
}
