package com.zewei.zewei.ui.main.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.zewei.zewei.App;
import com.zewei.zewei.R;
import com.zewei.zewei.model.Danmaku;
import com.zewei.zewei.model.DiscoveryPost;
import com.zewei.zewei.ui.main.FloatingActionMenu;
import com.zewei.zewei.ui.main.discovery.PostAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.logging.Handler;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@EFragment(R.layout.fragment_map)
public class MapFragment extends Fragment implements PanoramaViewListener, FloatingActionMenu.OnFloatingActionButtonClickListener {

    private static final LatLng DYC_LAT_LNG = new LatLng(39.930772, 116.524819);
    private static final String LOG_TAG = "MapFragment";

    @ViewById(R.id.floating_action_menu)
    FloatingActionMenu mFloatingActionMenu;

    @ViewById(R.id.fab)
    FloatingActionButton mFab;

    @ViewById(R.id.panorama_view)
    PanoramaView mPanoramaView;

    @ViewById(R.id.panorama_container)
    FrameLayout mPanoramaContainer;

    @ViewById(R.id.map_view)
    MapView mMapView;


    @ViewById(R.id.root)
    FrameLayout mRootView;

    @ViewById(R.id.danmakuView)
    DanmakuView mDanmakuView;

    private BDLocation mLocation;
    private boolean mMapShowing = false;

    private BaiduMap mBaiduMap;
    private BDAbstractLocationListener mLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(LOG_TAG, "onReceiveLocation: " + location);
            if (mBaiduMap == null || mLocation != null)
                return;
            mLocation = location;
            setLocation(location);
            App.getApp().getLocationClient().stop();
        }
    };

    private void setLocation(BDLocation location) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        mBaiduMap.setMyLocationData(locData);
    }

    @Click(R.id.fab)
    void onFabClick() {
        if (mFloatingActionMenu.isExpanded()) {
            mFloatingActionMenu.collapse();
        } else {
            mFloatingActionMenu.expand();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPanoramaView != null) {
            mPanoramaView.onResume();
        }
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPanoramaView != null) {
            mPanoramaView.onPause();
        }
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPanoramaView != null) {
            mPanoramaView.destroy();
        }
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        App.getApp().getLocationClient().unRegisterLocationListener(mLocationListener);
    }

    @SuppressLint("CheckResult")
    @AfterViews
    void setupViews() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
        App.getApp().getLocationClient().start();
        App.getApp().getLocationClient().registerLocationListener(mLocationListener);
        mBaiduMap.setOnMapLongClickListener(this::showPanorama);


        mPanoramaView.enableFastMove(true);
        mPanoramaView.setIndoorAlbumVisible();
        mPanoramaView.setShowTopoLink(true);
        mPanoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
        mPanoramaView.setPanoramaViewListener(this);
        mFloatingActionMenu.getState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean expanding) {
                        mFab.animate()
                                .rotation(expanding ? 45 : 0)
                                .setDuration(300)
                                .start();
                    }
                });

        Observable.just("danmu.json")
                .map(getContext().getAssets()::open)
                .map(stream -> Danmaku.jsonStreamToDanmakuItems(getContext(), stream))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(danmu -> mDanmakuView.addItem(danmu, true));

        mFloatingActionMenu.setOnFloatingActionButtonClickListener(this);

        showMap();

    }

    public boolean onBackPressed() {
        if (!mMapShowing) {
            showMap();
            return true;
        }
        return false;
    }

    private void showPanorama(LatLng latLng) {
        if (!mMapShowing)
            return;
        if (DistanceUtil.getDistance(latLng, DYC_LAT_LNG) < 1000) {
            mDanmakuView.show();
        }
        mMapView.setVisibility(View.GONE);
        mRootView.addView(mPanoramaContainer, 0);
        mPanoramaContainer.requestFocus();
        mPanoramaView.setPanorama(latLng.longitude, latLng.latitude);
        mMapShowing = false;
    }

    private void showMap() {
        if (mMapShowing)
            return;
        mMapView.setVisibility(View.VISIBLE);
        mDanmakuView.hide();
        mRootView.removeView(mPanoramaContainer);
        mMapShowing = true;
    }

    @Override
    public void onDescriptionLoadEnd(String s) {

    }

    @Override
    public void onLoadPanoramaBegin() {

    }

    @Override
    public void onLoadPanoramaEnd(String s) {

    }

    @Override
    public void onLoadPanoramaError(String s) {

    }

    @Override
    public void onMessage(String s, int i) {

    }

    @Override
    public void onCustomMarkerClick(String s) {

    }

    @Override
    public void onMoveStart() {

    }

    @Override
    public void onMoveEnd() {

    }

    @Override
    public void onClick(FloatingActionButton button, int pos) {
        if (pos == 1) {
            if (mMapShowing) {
                MyLocationData locData = new MyLocationData.Builder()
                        .direction(100)
                        .longitude(DYC_LAT_LNG.longitude)
                        .latitude(DYC_LAT_LNG.latitude)
                        .build();
                mBaiduMap.setMyLocationData(locData);
            } else {
                mPanoramaView.setPanorama("09002200121707031109112332I");
                mDanmakuView.show();
            }
        }
    }

    private static class SimpleObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}


