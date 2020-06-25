package com.wenmq.annosample;

import android.app.Application;

//import com.eastwood.common.autoinject.AutoTarget;
import com.eastwood.common.autoinject.AutoTarget;
import com.wenmq.annosample.alpha.MyLog;
import com.wenmq.mtask.mtask_kernel.MReceptors;

/**
 * @author ifans.wen
 * @date 2020/6/23
 * @description
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        long l1 = System.currentTimeMillis();
        MyLog.d(MyApplication.class.getSimpleName(), String.format("before onAppCreate in %s", l1));
        onAppCreate();
        long l2 = System.currentTimeMillis();
        MyLog.d(MyApplication.class.getSimpleName(), String.format("after onAppCreate in %s", l2));
        MyLog.d(MyApplication.class.getSimpleName(), String.format("cost %s s", l2 - l1));
    }


    @MReceptors(receptorAddress = "App")
    @AutoTarget(name = "application")
    public void onAppCreate() {


    }
}
