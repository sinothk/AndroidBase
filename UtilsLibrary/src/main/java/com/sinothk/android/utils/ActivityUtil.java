package com.sinothk.android.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * <pre>
 *  创建:  梁玉涛 2019/1/13 on 14:34
 *  项目:  FaYuanAndroid
 *  描述:  Activity Util
 *  更新:
 * <pre>
 */

@Deprecated
public class ActivityUtil {

    private static Stack<Activity> activityList = new Stack<>();

    public static void addActivity(Activity currActivity) {
        activityList.add(currActivity);
    }

    public static void removeActivity(Activity currActivity) {
        activityList.remove(currActivity);
    }

    public static void finishAll() {

        if (activityList == null) {
            return;
        }

        for (Activity activity : activityList) {
            if (activity == null) {
                continue;
            }
            activity.finish();
        }
    }
}
