package com.sinothk.android.base.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sinothk.android.widgets.LoadingTipView;

public class LoadingTipViewDemoActivity extends AppCompatActivity {

    LoadingTipView loadingTipView;
    TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_tip_view);

        contentView = this.findViewById(R.id.contentView);

        loadingTipView = this.findViewById(R.id.loadingTipView);
        loadingTipView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoadingTipViewDemoActivity.this, "重试按钮", Toast.LENGTH_SHORT).show();
            }
        });

        loadingTipView.showLoading("正在加载...");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingTipView.showNetError();
//                        loadingTipView.showNetError("");

//                        loadingTipView.showEmpty("");

//                        loadingTipView.showError("");

//                        loadingTipView.showContent(contentView);
                    }
                });
            }
        }, 5 * 1000);
    }
}
