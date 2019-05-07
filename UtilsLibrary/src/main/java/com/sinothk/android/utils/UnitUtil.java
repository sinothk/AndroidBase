package com.sinothk.android.utils;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 *  创建:  梁玉涛 2018/12/17 on 16:38
 *  项目:  WuMinAndroid
 *  描述:
 *  更新:
 * <pre>
 */
@Deprecated
public class UnitUtil {
    //将px转换为dp
    public static int px2dp(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //将像素转换为px
    public static int dip2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 格式化价格信息，精确到小数点后两位
     */
    public static String distanceFormat(double dis) {
        if (dis < 1000) {
            return (int) dis + "米";
        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(dis / 1000.0) + "千米";
        }
    }

    public static String getWeek(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);

        String Week = "";

        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week = "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week = "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week = "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week = "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week = "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week = "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week = "六";
        }

        return "星 期 " + Week;
    }

    public static String getFileSize(long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }
}
