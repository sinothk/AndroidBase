package com.sinothk.android.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sinothk.android.utils.R;

/**
 * <pre>
 *  创建:  梁玉涛 2019/4/12 on 11:30
 *  项目:  AndroidBaseLib
 *  描述:
 *  更新:
 * <pre>
 */
public class ImageLoader {

    public static void show(Context mContext, String url, ImageView imageView) {
        if (mContext == null || url == null || imageView == null) {
            return;
        }
        Glide.with(mContext).load(url).centerCrop().placeholder(R.color.app_bg).into(imageView);
    }

    public static void show(Context mContext, String url, final GlideLoadBitmapCallback callback) {
        if (mContext == null || url == null) {
            return;
        }
        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (callback != null) {
                    callback.getBitmapCallback(resource);
                }
            }
        });
    }

    public interface GlideLoadBitmapCallback {
        void getBitmapCallback(Bitmap bitmap);
    }

}
