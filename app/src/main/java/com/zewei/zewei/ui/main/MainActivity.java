package com.zewei.zewei.ui.main;

import android.Manifest;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zewei.zewei.R;
import com.zewei.zewei.ui.main.discovery.DiscoveryFragment;
import com.zewei.zewei.ui.main.discovery.DiscoveryFragment_;
import com.zewei.zewei.ui.main.map.MapFragment;
import com.zewei.zewei.ui.main.me.MeFragment_;
import com.zewei.zewei.ui.main.map.MapFragment_;
import com.zewei.zewei.ui.main.search.SearchFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    @ViewById(R.id.view_pager)
    ViewPager mViewPager;

    @ViewById(R.id.bottom_bar)
    LinearLayout mBottomBar;

    private int mSelectedItem = 0;
    private FragmentPagerAdapterBuilder.StoredFragmentPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 17777);
        }
    }

    @AfterViews
    void setupViews() {
        mPageAdapter = new FragmentPagerAdapterBuilder(this)
                .add(new MapFragment_(), R.string.main_page)
                .add(new DiscoveryFragment_(), R.string.discovery)
                .add(new SearchFragment_(), R.string.search)
                .add(new MeFragment_(), R.string.me)
                .build();
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.addOnPageChangeListener(this);
        setupBottomBar();
        onPageSelected(0);
    }

    @Override
    public void onBackPressed() {
        MapFragment fragment = (MapFragment) mPageAdapter.getStoredFragment(0);
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    void setupBottomBar() {
        for (int i = 0; i < mBottomBar.getChildCount(); i++) {
            final int pos = i;
            mBottomBar.getChildAt(i).setOnClickListener(v -> mViewPager.setCurrentItem(pos));
        }
    }

    private void setBottomBarItemColor(int pos, int colorRes) {
        int color = ResourcesCompat.getColor(getResources(), colorRes, getTheme());
        ViewGroup item = (ViewGroup) mBottomBar.getChildAt(pos);
        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);
        icon.setImageTintList(ColorStateList.valueOf(color));
        label.setTextColor(color);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setBottomBarItemColor(mSelectedItem, R.color.colorBottomBarInactive);
        mSelectedItem = position;
        setBottomBarItemColor(position, R.color.colorBottomBarActive);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
