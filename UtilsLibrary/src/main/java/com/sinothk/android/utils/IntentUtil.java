package com.sinothk.android.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by 梁玉涛 on 2017/11/22/022.
 * 描述：
 */
public class IntentUtil {

    private static long lasttime;

    public static IntentBuilder openActivity(Activity currActivity, Class<?> gotoActivity) {
        return new IntentBuilder(currActivity, gotoActivity);
    }

    public static void openInnerActivity(Activity currActivity, String pkg, String activity) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(pkg, activity);
        intent.setComponent(cn);
        currActivity.startActivity(intent);
    }

    public static void openOtherApp(Activity currActivity, String pkg, String activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        ComponentName cn = new ComponentName(pkg, activity);
        intent.setComponent(cn);
        currActivity.startActivity(intent);
    }

    public static String openOtherApp(Activity currActivity, String pkg, Bundle bundle) {
        PackageManager packageManager = currActivity.getPackageManager();

        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(pkg, 0);
            if (packageInfo != null) {
                Intent intent = packageManager.getLaunchIntentForPackage(pkg);
                if (bundle != null) {
                    assert intent != null;
                    intent.putExtras(bundle);
                }
                currActivity.startActivity(intent);
                return "";
            } else {
                return "应用未安装";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "应用未安装";
        }
    }

    public static class IntentBuilder {

        private boolean isFinish;
        private Bundle mDataBundle;
        private int mEnterAnimRes;
        private int mExitAnimRes;
        private int mFlags;
        private Activity currActivity;
        private Intent mIntent;
        private int mRequestCode = -999;
        private Class<?> mToActivity;

        public IntentBuilder(Activity currActivity, Class<?> paramClass) {
            this.currActivity = currActivity;
            this.mToActivity = paramClass;
            this.mIntent = new Intent(this.currActivity, this.mToActivity);
        }

        public IntentBuilder anim(int paramInt1, int paramInt2) {
            this.mEnterAnimRes = paramInt1;
            this.mExitAnimRes = paramInt2;
            return this;
        }

        public IntentBuilder finish(boolean paramBoolean) {
            this.isFinish = paramBoolean;
            return this;
        }

        public IntentBuilder putBooleanExtra(String paramString, boolean paramBoolean) {
            this.mIntent.putExtra(paramString, paramBoolean);
            return this;
        }

        public IntentBuilder putExtras(Bundle paramBundle) {
            this.mDataBundle = paramBundle;
            return this;
        }

        public IntentBuilder putIntExtra(String paramString, int paramInt) {
            this.mIntent.putExtra(paramString, paramInt);
            return this;
        }

        public IntentBuilder putSerializableExtra(String paramString, Serializable paramSerializable) {
            this.mIntent.putExtra(paramString, paramSerializable);
            return this;
        }

        public IntentBuilder putStringExtra(String paramString1, String paramString2) {
            this.mIntent.putExtra(paramString1, paramString2);
            return this;
        }

        public IntentBuilder requestCode(int paramInt) {
            this.mRequestCode = paramInt;
            return this;
        }

        public IntentBuilder setFlags(int paramInt) {
            this.mFlags = paramInt;
            return this;
        }

        public void start() {
            if (System.currentTimeMillis() - IntentUtil.lasttime <= 0L)
                return;

            IntentUtil.lasttime = System.currentTimeMillis();

            if (this.mDataBundle != null)
                this.mIntent.putExtras(this.mDataBundle);

            if (this.mFlags != 0)
                this.mIntent.setFlags(this.mFlags);

            if (mRequestCode == -999) {
                this.currActivity.startActivity(this.mIntent);
            } else {
                this.currActivity.startActivityForResult(mIntent, mRequestCode);
            }

            if ((this.mEnterAnimRes != 0) && (this.mExitAnimRes != 0)) {
                currActivity.overridePendingTransition(this.mEnterAnimRes, this.mExitAnimRes);
            } else {
//                currActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                currActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }

            if (this.isFinish)
                currActivity.finish();
            //
            mDataBundle = null;
            mIntent = null;
        }

        public void startInFragment(Fragment fragment) {

            if (System.currentTimeMillis() - IntentUtil.lasttime <= 0L)
                return;

            IntentUtil.lasttime = System.currentTimeMillis();

            if (mDataBundle != null)
                mIntent.putExtras(mDataBundle);

            if (mFlags != 0)
                mIntent.setFlags(mFlags);

            if (mRequestCode == -999) {
                fragment.startActivity(mIntent);
            } else {
                fragment.startActivityForResult(mIntent, mRequestCode);
            }
            //
            mDataBundle = null;
            mIntent = null;
        }
    }
}
