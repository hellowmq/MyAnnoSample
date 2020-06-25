package com.wenmq.annosample.alpha;

import android.content.Context;
import android.os.Handler;

import com.alibaba.android.alpha.OnProjectExecuteListener;
import com.eastwood.common.autoinject.AutoBowArrow;
import com.eastwood.common.autoinject.IAutoBowArrow;
import com.wenmq.annosample.util.TimeUtil;


/**
 * @author ifans.wen
 * @date 2020/6/23
 * @description
 */
public class ApplicationTaskFactory {


    private static final String TAG = ApplicationTaskFactory.class.getSimpleName();
    private Context context;
    private OnProjectExecuteListener listener;

    public ApplicationTaskFactory(Context context) {
        this.context = context;
    }

    @AutoBowArrow(target = "application")
    public static class AutoTaskA implements IAutoBowArrow {
        public AutoTaskA() {
        }

        @Override
        public void shoot() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    TimeUtil.randomSleep();
                    MyLog.d(TAG, String.format("run %s in %s", AutoTaskA.class.getSimpleName(), Thread.currentThread().getName()));
                    MyLog.d(TAG, String.format("%s end at %s", AutoTaskA.class, System.currentTimeMillis()));
                }
            });
        }
    }

    @AutoBowArrow(target = "application")
    public static class AutoTaskB implements IAutoBowArrow {

        public AutoTaskB() {
        }

        @Override
        public void shoot() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    TimeUtil.randomSleep();
                    MyLog.d(TAG, String.format("run %s in %s", AutoTaskB.class.getSimpleName(), Thread.currentThread().getName()));
                    MyLog.d(TAG, String.format("%s end at %s", AutoTaskB.class.getSimpleName(), System.currentTimeMillis()));
                }
            });
        }
    }

    @AutoBowArrow(target = "application")
    public static class AutoTaskC implements IAutoBowArrow {
        public AutoTaskC() {
        }

        @Override
        public void shoot() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    TimeUtil.randomSleep();
                    String name = Thread.currentThread().getName();
                    MyLog.d(TAG, String.format("run %s in %s", AutoTaskC.class.getSimpleName(), name));
                    MyLog.d(TAG, String.format("%s end at %s", AutoTaskC.class.getSimpleName(), System.currentTimeMillis()));
                }
            });
        }
    }


}
