package com.workouts.activity;

import android.app.Application;
import android.support.multidex.MultiDex;

import static com.workouts.constant.AppConstant.mContext;

/**
 * Created by AdnanAli on 1/2/2018.
 */

public class LightApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext=getApplicationContext();
    }
}
