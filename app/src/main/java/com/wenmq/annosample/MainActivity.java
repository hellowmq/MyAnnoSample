package com.wenmq.annosample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.alibaba.android.alpha.OnProjectExecuteListener;
import com.wenmq.anno.NewIntent;
import com.wenmq.annosample.activity.BaseActivity;
import com.wenmq.annosample.activity.RouterPath;
import com.wenmq.annosample.alpha.ConfigTest;
import com.wenmq.annosample.alpha.MyLog;
import com.wenmq.annotationsample.ANavigator;

@NewIntent(path = RouterPath.Activity.Path_MainActivity)
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView btnTestTask = findViewById(R.id.btn_test_task);
        btnTestTask.setText(MyLog.getLogString());
        btnTestTask.setOnClickListener(v -> addTestTask((TextView) v));
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(v -> startActivity(ANavigator.getIntentByPath(MainActivity.this, RouterPath.Activity.Path_SecondActivity)));
    }


    private void addTestTask(final TextView text) {
        MyLog.clear();
        ConfigTest test = new ConfigTest(getApplicationContext());
        test.setOnProjectExecuteListener(new OnProjectExecuteListener() {
            @Override
            public void onProjectStart() {

            }

            @Override
            public void onTaskFinish(String taskName) {
                new Handler(Looper.getMainLooper()).post(() -> text.setText(MyLog.getLogString()));
            }

            @Override
            public void onProjectFinish() {
                new Handler(Looper.getMainLooper()).post(() -> text.setText(MyLog.getLogString()));
            }
        });
        test.start();
    }


}
