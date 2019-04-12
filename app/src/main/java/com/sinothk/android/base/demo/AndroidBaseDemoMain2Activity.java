package com.sinothk.android.base.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sinothk.android.all.titlebar.TitleBarBaseActivity;

/**
 * https://github.com/bumptech/glide
 */
public class AndroidBaseDemoMain2Activity extends TitleBarBaseActivity {

    ImageView iv001;


    @Override
    protected int getLayout() {
        return R.layout.activity_android_base_demo_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBar("Hello !");

        iv001 = findViewById(R.id.iv001);
    }

    public void goto2(View view) {
    }
}
