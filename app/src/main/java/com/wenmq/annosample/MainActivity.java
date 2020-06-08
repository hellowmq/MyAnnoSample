package com.wenmq.annosample;

import android.os.Bundle;
import android.widget.TextView;

import com.wenmq.anno.NewIntent;
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


    }
}
