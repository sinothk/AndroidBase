package com.sinothk.android.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.sinothk.android.utils.XUtil;

/**
 * <pre>
 *  创建:  梁玉涛 2018/12/6 on 16:08
 *  项目:  android
 *  描述:  使用自定义view继承自ScrollView
 *  更新:
 * <pre>
 */
public class ScrollChangeView extends ScrollView {

    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public ScrollChangeView(Context context) {
        super(context);
    }

    public ScrollChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChange(final Context context, final View titleView, final View baseView, final int normalColor, int red, int green, int blue) {

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int i = XUtil.UnitUtil.dip2px(context, scrollY);
                int dp = XUtil.UnitUtil.px2dp(context, i);
                if (dp > 100) {
                    titleView.setBackgroundResource(normalColor);
                } else {
                    float scale = (float) scrollY / baseView.getHeight();
                    int alpha = (int) (255 * scale);
                    titleView.setBackgroundColor(Color.argb(alpha, 62, 122, 220));
                }
            }
        });
    }

    //设置接口
    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {

            //这里我只传了垂直滑动的距离
            listener.onScroll(t);
        }
    }
}

