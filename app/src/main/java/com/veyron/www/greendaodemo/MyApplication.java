package com.veyron.www.greendaodemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Veyron on 2017/2/22.
 * Function：第一次使用Stetho，通过这个方便查看数据库
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
