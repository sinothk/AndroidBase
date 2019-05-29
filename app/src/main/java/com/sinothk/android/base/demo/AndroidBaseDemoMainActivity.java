package com.sinothk.android.base.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sinothk.android.all.titlebar.TitleBarBaseActivity;

public class AndroidBaseDemoMainActivity extends TitleBarBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_android_base_demo_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBar("It's All ！");

//        TitleBarView.getString();
    }

    public void goto2(View view) {
        startActivity(new Intent(AndroidBaseDemoMainActivity.this, AndroidBaseDemoMain2Activity.class));
    }

    public void goto3(View view) {
        startActivity(new Intent(AndroidBaseDemoMainActivity.this, LoadingTipViewDemoActivity.class));
    }
}
