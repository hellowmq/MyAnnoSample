package com.wenmq.annosample;

import android.app.Application;

import com.wenmq.annosample.alpha.ConfigTest;

/**
 * @author ifans.wen
 * @date 2020/6/23
 * @description
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new ConfigTest(this).start();
    }
}
