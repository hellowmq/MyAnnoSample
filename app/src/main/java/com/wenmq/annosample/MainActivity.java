package com.wenmq.annosample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.alibaba.android.alpha.OnProjectExecuteListener;
import com.wenmq.anno.NewIntent;
import com.wenmq.annosample.alpha.ConfigTest;
import com.wenmq.annosample.alpha.MyLog;
import com.wenmq.annotationsample.ANavigator;

@NewIntent(path = RouterPath.Activity.Path_MainActivity)
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Explode().setDuration(500));
//            getWindow().setExitTransition(new Explode().setDuration(500));
//        }
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView textView = findViewById(R.id.textView);
        textView.setOnClickListener(v -> startActivity(ANavigator.getIntentByPath(MainActivity.this, RouterPath.Activity.Path_SecondActivity)));
        addTestTask(textView);

    }


    private void addTestTask(final TextView text) {
        ConfigTest test = new ConfigTest(getApplicationContext());
        test.setOnProjectExecuteListener(new OnProjectExecuteListener() {
            @Override
            public void onProjectStart() {

            }

            @Override
            public void onTaskFinish(String taskName) {

            }

            @Override
            public void onProjectFinish() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(MyLog.getLogString());
                    }
                });
            }
        });
        test.start();
    }


}
