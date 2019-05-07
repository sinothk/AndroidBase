package com.sinothk.android.utils;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * <pre>
 *  创建:  LiangYT 2018/6/20/020 on 16:00
 *  项目: gxqptAndroid
 *  描述:
 *  更新:
 * <pre>
 */
@Deprecated
public class AndroidNativeUtil extends AppUtil {

//    /**
//     * 打开其他App
//     *
//     * @param mContext
//     * @param packName
//     * @param targetActivity
//     * @param bundle
//     */
//    public static String startOtherApp(Context mContext, String packName, String targetActivity, Bundle bundle) {
//
//        try {
//            //第一种方式
////            ComponentName cn = new ComponentName(packName, targetActivity);
////            intent.setComponent(cn);
//            //第二种方式
//            Intent intent = new Intent();
//            intent.setClassName(packName, targetActivity);
//            if (bundle != null) {
//                intent.putExtras(bundle);
//            }
//            mContext.startActivity(intent);
//            return "";
//        } catch (Exception e) {
//            // 可以在这里提示用户没有安装应用或找不到指定Activity，或者是做其他的操作
//            Toast.makeText(mContext, "没有安装：" + packName, Toast.LENGTH_SHORT).show();
//            return "没有安装";
//        }
//    }

    /**
     * 打开其他App
     *
     * @param mContext
     * @param packName
     */
    public static String startOtherApp(Context mContext, String packName, Bundle bundle) {
        PackageManager packageManager = mContext.getPackageManager();

        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(packName, 0);
            if (packageInfo != null) {
                Intent intent = packageManager.getLaunchIntentForPackage(packName);
                if (bundle != null) {
                    assert intent != null;
                    intent.putExtras(bundle);
                }
                mContext.startActivity(intent);
                return "";
            } else {
                return "没有安装";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "没有安装";
        }
    }

    /**
     * 通过调用浏览器下载文件！
     *
     * @param mContext
     * @param url
     */
    public static void downLoadByBrowser(Context mContext, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        // 走自己的服务器
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }

    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public static boolean copyIntoBox(Context mContext, String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
