package com.sinothk.android.base.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sinothk.android.base.view.TitleBarView;

public class AndroidBaseDemoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_base_demo_main);

        TitleBarView.getString();
    }
}
