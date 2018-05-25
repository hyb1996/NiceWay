package com.zewei.zewei;

import android.app.Application;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;

public class App extends Application implements MKGeneralListener {

    private BMapManager mBMapManager;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        mBMapManager = new BMapManager(this);
        mBMapManager.init(this);
    }

    @Override
    public void onGetPermissionState(int i) {

    }
}
