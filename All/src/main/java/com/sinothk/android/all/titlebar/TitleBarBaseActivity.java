package com.sinothk.android.all.titlebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinothk.android.all.R;
import com.sinothk.android.utils.ActivityUtil;
import com.sinothk.android.utils.StatusBarUtil;
import com.sinothk.android.utils.StringUtil;

public abstract class TitleBarBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ActivityUtil.addActivity(this);

        setTitleTransparencyBar();
    }

    protected void setTitleTransparencyBar() {
        StatusBarUtil.transparencyBar(this);
    }

    protected abstract int getLayout();

    protected void setTitleBar(String title) {
        if (StringUtil.isNotEmpty(title)) {
            TextView titleBarTxt = findViewById(R.id.titleBarTxt);
            titleBarTxt.setText(title);
        }

        RelativeLayout titleBarLeft = findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView gonnaBtn = findViewById(R.id.gonnaBtn);
        TextView gonnaTxt = findViewById(R.id.gonnaTxt);

        gonnaBtn.setVisibility(View.GONE);
        gonnaTxt.setVisibility(View.INVISIBLE);
    }

    protected void setTitleBar(String title, View.OnClickListener listener) {
        if (StringUtil.isNotEmpty(title)) {
            TextView titleBarTxt = findViewById(R.id.titleBarTxt);
            titleBarTxt.setText(title);
        }

        RelativeLayout titleBarLeft = findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(listener);

        ImageView gonnaBtn = findViewById(R.id.gonnaBtn);
        TextView gonnaTxt = findViewById(R.id.gonnaTxt);

        gonnaBtn.setVisibility(View.GONE);
        gonnaTxt.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置标题:左返回，标题，右文本事件
     */
    protected void setTitleBar(String title, String rightBtnTxt, View.OnClickListener listener) {

        if (StringUtil.isNotEmpty(title)) {
            TextView titleBarTxt = findViewById(R.id.titleBarTxt);
            titleBarTxt.setText(title);
        }

        RelativeLayout titleBarLeft = findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView gonnaBtn = findViewById(R.id.gonnaBtn);
        TextView gonnaTxt = findViewById(R.id.gonnaTxt);

        if (StringUtil.isNotEmpty(rightBtnTxt)) {
            gonnaBtn.setVisibility(View.GONE);
            gonnaTxt.setVisibility(View.VISIBLE);

            gonnaTxt.setText(rightBtnTxt);

            if (listener != null) {
                gonnaTxt.setOnClickListener(listener);
            }
        }
    }

    /**
     * 设置标题:左返回，标题，右文本事件
     */
    protected void setTitleBar(String title, int rightBtnIcon, View.OnClickListener listener) {

        if (StringUtil.isNotEmpty(title)) {
            TextView titleBarTxt = findViewById(R.id.titleBarTxt);
            titleBarTxt.setText(title);
        }

        RelativeLayout titleBarLeft = findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView gonnaBtn = findViewById(R.id.gonnaBtn);
        TextView gonnaTxt = findViewById(R.id.gonnaTxt);

        gonnaBtn.setVisibility(View.VISIBLE);
        gonnaTxt.setVisibility(View.GONE);
        gonnaBtn.setImageResource(rightBtnIcon);

        if (listener != null) {
            gonnaTxt.setOnClickListener(listener);
        }
    }
}
