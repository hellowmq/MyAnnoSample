package com.wenmq.annosample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.wenmq.anno.NewIntent;
import com.wenmq.annosample.R;
import com.wenmq.annotationsample.ANavigator;


@NewIntent(path = RouterPath.Activity.Path_SplashActivity)
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int delayMillis = 3000;
        SplashActivity.class.getCanonicalName();
        new Handler().postDelayed(
                () -> {
                    finish();
                    Log.e("SplashActivity", "waitFor" + delayMillis);
                    Activity context = SplashActivity.this;
                    Intent intent = ANavigator.getIntentByPath(context, RouterPath.Activity.Path_MainActivity); //generated class, method
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
//                    } else {
                        startActivity(intent);
//                    }
                }, delayMillis
        );
    }
}
