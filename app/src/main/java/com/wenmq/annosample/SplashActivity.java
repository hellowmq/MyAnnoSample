package com.wenmq.annosample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.wenmq.anno.NewIntent;
import com.wenmq.annotationsample.ANavigator;


@NewIntent(path = RouterPath.Activity.Path_SplashActivity)
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int delayMillis = 3000;
        new Handler().postDelayed(
                () -> {
                    finish();
                    Log.e("SplashActivity", "waitFor" + delayMillis);
                    Activity context = SplashActivity.this;
                    Intent intent = ANavigator.getIntentByPath(context, RouterPath.Activity.Path_MainActivity); //generated class, method
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        startActivity(intent);
                    }
                }, delayMillis
        );
    }
}