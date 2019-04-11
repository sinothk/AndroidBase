package com.sinothk.android.base.demo;

import android.os.Bundle;

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

}
