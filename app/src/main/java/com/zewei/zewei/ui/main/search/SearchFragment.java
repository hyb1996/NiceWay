package com.zewei.zewei.ui.main.search;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zewei.zewei.R;
import com.zewei.zewei.model.DiscoveryPost;
import com.zewei.zewei.model.Hotspot;
import com.zewei.zewei.ui.main.discovery.PostAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@EFragment(R.layout.fragment_search)
public class SearchFragment extends Fragment {

    @ViewById(R.id.hot_searches)
    RecyclerView mHotSearches;

    @SuppressLint("CheckResult")
    @AfterViews
    void setupViews() {
        mHotSearches.setLayoutManager(new LinearLayoutManager(getContext()));
        mHotSearches.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.colorDivider)
                .sizeResId(R.dimen.widthSmallDivider)
                .showLastDivider()
                .build());
        Observable.just("toplist.json")
                .map(path -> getContext().getAssets().open(path))
                .map(Hotspot::fromStream)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> mHotSearches.setAdapter(new HotspotListAdapter(items)));
    }
}
