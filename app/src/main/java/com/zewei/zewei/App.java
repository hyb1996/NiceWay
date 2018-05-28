package com.zewei.zewei;

import android.app.Application;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

public class App extends Application implements MKGeneralListener {

    private static App sApp;
    private BMapManager mBMapManager;
    private LocationClient mLocationClient;


    public static App getApp() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(this);
        mBMapManager = new BMapManager(this);
        mBMapManager.init(this);
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);

        option.setOpenGps(true);

        option.setLocationNotify(true);

        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    public LocationClient getLocationClient() {
        return mLocationClient;
    }

    @Override
    public void onGetPermissionState(int i) {

    }

}
