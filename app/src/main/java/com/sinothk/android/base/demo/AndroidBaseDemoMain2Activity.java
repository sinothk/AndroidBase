package com.sinothk.android.base.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sinothk.android.all.titlebar.TitleBarBaseActivity;
import com.sinothk.android.utils.glide.ImageLoader;
import com.sinothk.smartImage.FaceImageUtil;

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

        ImageLoader.show(this, "https://github.com/aryarohit07/GlideFaceDetectionTransformation/blob/master/images/original_image4.jpg?raw=true", new ImageLoader.GlideLoadBitmapCallback() {
            @Override
            public void getBitmapCallback(Bitmap bitmap) {
                Bitmap clipBitmap = FaceImageUtil.cropToBitmap(AndroidBaseDemoMain2Activity.this, bitmap, 100, 100);
                iv001.setImageBitmap(clipBitmap);
            }
        });
    }

    public void goto2(View view) {
    }
}
