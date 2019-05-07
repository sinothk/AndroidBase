package com.sinothk.android.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by LYT on 2017/8/11.
 * 功能：处理
 */
@Deprecated
public class ViewUtil {

    /**
     * 根据手机宽度和指定的宽高比来动态设置View的高度(父类为LinearLayout)，主要使用ImageView显示。
     *
     * @param mContext
     * @param baseView 要设置高度的View
     * @param ratio    显示比例: w/h = ratio
     */
    public static void createNewViewHeight(Context mContext, View baseView, float ratio) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) baseView.getLayoutParams();
        params.height = (int) (width / ratio);
        baseView.setLayoutParams(params);
    }

    /**
     * EditText中有数据时，将光标移到最后
     *
     * @param editText
     */
    public static void focusMoveToEnd(EditText editText) {
        // 有内容，将光标移最后
        Editable eText = editText.getText();
        Selection.setSelection(eText, eText.length());
    }
}
