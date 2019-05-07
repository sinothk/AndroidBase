package com.sinothk.android.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import static com.sinothk.android.utils.BuildConfig.DEBUG;

/**
 * <pre>
 *  创建:  梁玉涛 2019/5/7 on 10:50
 *  项目:  AndroidBaseLib
 *  描述:
 *  更新:
 * <pre>
 */
public class XUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void init(Context baseContext) {
        mContext = baseContext;
    }

    /*
     * ============================================ String =================================================
     */

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 计量单位相关
     */
    public static class UnitUtil {
        private static final DecimalFormat amountFormat = new DecimalFormat("####,####,###0.00");

        /**
         * 四舍五入
         *
         * @param value 数值
         * @param digit 保留小数位
         * @return
         */
        public static String getRoundUp(BigDecimal value, int digit) {
            return value.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 四舍五入
         *
         * @param value 数值
         * @param digit 保留小数位
         * @return
         */
        public static String getRoundUp(double value, int digit) {
            BigDecimal result = new BigDecimal(value);
            return result.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 四舍五入
         *
         * @param value 数值
         * @param digit 保留小数位
         * @return
         */
        public static String getRoundUp(String value, int digit) {
            BigDecimal result = new BigDecimal(Double.parseDouble(value));
            return result.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 获取百分比（乘100）
         *
         * @param value 数值
         * @param digit 保留小数位
         * @return
         */
        public static String getPercentValue(BigDecimal value, int digit) {
            BigDecimal result = value.multiply(new BigDecimal(100));
            return getRoundUp(result, digit);
        }

        /**
         * 获取百分比（乘100）
         *
         * @param value 数值
         * @param digit 保留小数位
         * @return
         */
        public static String getPercentValue(double value, int digit) {
            BigDecimal result = new BigDecimal(value);
            return getPercentValue(result, digit);
        }

        /**
         * 获取百分比（乘100,保留两位小数）
         *
         * @param value 数值
         * @return
         */
        public static String getPercentValue(double value) {
            BigDecimal result = new BigDecimal(value);
            return getPercentValue(result, 2);
        }

        /**
         * 金额格式化
         *
         * @param value 数值
         * @return
         */
        public static String getMoneyValue(double value) {
            return amountFormat.format(value);
        }

        /**
         * 金额格式化
         *
         * @param value 数值
         * @return
         */
        public static String getMoneyValue(String value) {
            return amountFormat.format(Double.parseDouble(value));
        }

        /**
         * int -tostring
         *
         * @param value 数值
         * @return
         */
        public static String getIntegerValue(int value) {
            return Integer.valueOf(value).toString();
        }

        /**
         * onTextChanged
         *
         * @param sequence (CharSequenc s
         * @param editText
         */
        public static void formatDot(CharSequence sequence, EditText editText) {
            String s = sequence.toString();
            if (s.contains(".")) {
                /**
                 * 如果小数点位数大于两位 截取后两位
                 */
                if (s.length() - 1 - s.indexOf(".") > 2) {
                    s = s.substring(0, (s.indexOf(".") + 3));
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }
            /**
             * 如果第一个输入为小数点 ，自动补零
             */
            if (s.trim().substring(0).equals(".")) {
                s = "0" + s;
                editText.setText(s);
                editText.setSelection(s.length());
            }
            /**
             * 如果第一个第二个均为0
             */
            if (s.startsWith("0") && s.trim().length() > 1) {
                if (!s.substring(1, 2).equals(".")) {
                    editText.setText(s.substring(0, 1));
                    editText.setSelection(1);
                    return;
                }
            }
        }

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

        /**
         * 获得某个日期是星期几
         *
         * @param date
         * @return
         */
        public static String getDateWeek(Date date) {
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

            return Week;
        }

        public static String getFileSize(long size) {
            //获取到的size为：1705230
            long TB = 1024 * 1024 * 1024 * 1024L;//定义TB的计算常量
            int GB = 1024 * 1024 * 1024;//定义GB的计算常量
            int MB = 1024 * 1024;//定义MB的计算常量
            int KB = 1024;//定义KB的计算常量

            DecimalFormat df = new DecimalFormat("0.0");//格式化小数
            String resultSize;

            if (size / TB >= 1) {
                //如果当前Byte的值大于等于1GB
                resultSize = df.format(size / (float) TB) + "TB";

            } else if (size / GB >= 1) {
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

    /**
     * View 工具
     */
    public static class ViewUtil {

        /**
         * 根据手机宽度和指定的宽高比来动态设置View的高度(父类为LinearLayout)，主要使用ImageView显示。
         *
         * @param baseView 要设置高度的View
         * @param ratio    显示比例: w/h = ratio
         */
        public static void createNewViewHeight(View baseView, float ratio) {
            if (mContext == null) {
                throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context), 请在调用前初始化：init(context)");
            }

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

        /**
         * 输入框内容提示
         *
         * @param context
         * @param editText
         * @param max_length
         * @param err_msg
         */
        public static void inputMaxTip(final Context context, final EditText editText, final TextView inputMaxTipTv, final int max_length, final String err_msg) {

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    int len = s.toString().length();

                    inputMaxTipTv.setText(len + "/" + max_length);
                }
            });

            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(max_length) {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    int destLen = dest.toString().length();
                    int sourceLen = source.toString().length();

                    if (destLen + sourceLen > max_length + 1) {
                        Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
                        return "";
                    }
                    return source;
                }
            };
            editText.setFilters(filters);
        }

        /**
         * @param content
         * @return
         * @description 获取一段字符串的字符个数(包含中英文, 一个中文算2个字符)
         */
        private static int getCharacterNum(final String content) {
            if (null == content || "".equals(content)) {
                return 0;
            } else {
                return (content.length() + getChineseNum(content));
            }
        }

        /**
         * @param s
         * @return
         * @description 返回字符串里中文字或者全角字符的个数
         */
        private static int getChineseNum(String s) {

            int num = 0;
            char[] myChar = s.toCharArray();
            for (int i = 0; i < myChar.length; i++) {
                if ((char) (byte) myChar[i] != myChar[i]) {
                    num++;
                }
            }
            return num;
        }
    }

    /**
     * <pre>
     *  创建:  LiangYT 2018/5/18/018 on 16:02
     *  项目: Integration
     *  描述:
     *      StatusBarUtil.transparencyBar(this) //设置状态栏全透明
     *      StatusBarUtil.StatusBarLightMode(this) //设置白底黑字
     *  更新:
     * <pre>
     */
    public static class StatusBarUtil {
        /**
         * 修改状态栏为全透明
         *
         * @param activity
         */
        @TargetApi(19)
        public static void transparencyBar(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = activity.getWindow();
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            // 配合Theme
            //        <!-- Base application theme. -->
            //    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
            //        <!-- Customize your theme here. -->
            //        <item name="colorPrimary">@color/colorPrimary</item>
            //        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
            //        <item name="colorAccent">@color/colorAccent</item>
            //        <item name="windowActionBar">false</item>
            //        <item name="windowNoTitle">true</item>
            //        <item name="android:screenOrientation">portrait</item>
            //        <item name="android:windowSoftInputMode">stateHidden</item>
            //    </style>
        }

        /**
         * 状态栏亮色模式，设置状态栏黑色文字、图标，
         * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
         *
         * @param activity
         * @return 1:MIUUI 2:Flyme 3:android6.0
         */
        public static int StatusBarLightMode(Activity activity) {
            int result = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (MIUISetStatusBarLightMode(activity, true)) {
                    //小米
                    result = 1;
                } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                    //魅族
                    result = 2;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //6.0以上
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    result = 3;
                } else {
                    //其他的都设置状态栏成半透明的,以下设置半透明是调用第三方的，根据个人需求更改
                    //    ImmersionBar.with(activity).statusBarDarkFont(true, 0.5f).init();
                }
            }
            return result;
        }

        /**
         * 设置状态栏图标为深色和魅族特定的文字风格
         * 可以用来判断是否为Flyme用户
         *
         * @param window 需要设置的窗口
         * @param dark   是否把状态栏文字及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
            boolean result = false;
            if (window != null) {
                try {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    Field darkFlag = WindowManager.LayoutParams.class
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                    Field meizuFlags = WindowManager.LayoutParams.class
                            .getDeclaredField("meizuFlags");
                    darkFlag.setAccessible(true);
                    meizuFlags.setAccessible(true);
                    int bit = darkFlag.getInt(null);
                    int value = meizuFlags.getInt(lp);
                    if (dark) {
                        value |= bit;
                    } else {
                        value &= ~bit;
                    }
                    meizuFlags.setInt(lp, value);
                    window.setAttributes(lp);
                    result = true;
                } catch (Exception e) {

                }
            }
            return result;
        }

        /**
         * 需要MIUIV6以上
         *
         * @param activity
         * @param dark     是否把状态栏文字及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
            boolean result = false;
            Window window = activity.getWindow();
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                    result = true;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                        if (dark) {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        } else {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        }
                    }
                } catch (Exception e) {

                }
            }
            return result;
        }
    }

    /**
     * <pre>
     *     author: Blankj
     *     blog  : http://blankj.com
     *     time  : 2016/8/2
     *     desc  : 键盘相关工具类
     * </pre>
     */
    public static class KeyboardUtil {

        private KeyboardUtil() {
            throw new UnsupportedOperationException("u can't fuck me...");
        }

        /**
         * 避免输入法面板遮挡
         * <p>在manifest.xml中activity中设置</p>
         * <p>android:windowSoftInputMode="stateVisible|adjustResize"</p>
         */

        /**
         * 动态隐藏软键盘
         *
         * @param activity activity
         */
        public static void hideSoftInput(Activity activity) {
            View view = activity.getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        /**
         * 动态隐藏软键盘
         *
         * @param context 上下文
         * @param edit    输入框
         */
        public static void hideSoftInput(Context context, EditText edit) {
            edit.clearFocus();
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        }

        /**
         * 点击屏幕空白区域隐藏软键盘（方法1）
         * <p>在onTouch中处理，未获焦点则隐藏</p>
         * <p>参照以下注释代码</p>
         */
        public static void clickBlankArea2HideSoftInput0() {
            Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (null != this.getCurrentFocus()) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            return super.onTouchEvent(event);
        }
        */
        }

        /**
         * 点击屏幕空白区域隐藏软键盘（方法2）
         * <p>根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
         * <p>需重写dispatchTouchEvent</p>
         * <p>参照以下注释代码</p>
         */
        public static void clickBlankArea2HideSoftInput1() {
            Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }

        // 获取InputMethodManager，隐藏软键盘
        private void hideKeyboard(IBinder token) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        */
        }

        /**
         * 动态显示软键盘
         *
         * @param context 上下文
         * @param edit    输入框
         */
        public static void showSoftInput(Context context, EditText edit) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edit, 0);
        }

        /**
         * 切换键盘显示与否状态
         *
         * @param context 上下文
         * @param edit    输入框
         */
        public static void toggleSoftInput(Context context, EditText edit) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * <pre>
     *     author: Blankj
     *     blog  : http://blankj.com
     *     time  : 2016/8/13
     *     desc  : 转换相关工具类
     * </pre>
     */
    public static class ConvertUtil {

        static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        /**
         * byteArr转hexString
         * <p>例如：</p>
         * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
         *
         * @param bytes byte数组
         * @return 16进制大写字符串
         */
        public static String bytes2HexString(byte[] bytes) {
            char[] res = new char[bytes.length << 1];
            for (int i = 0, j = 0; i < bytes.length; i++) {
                res[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
                res[j++] = hexDigits[bytes[i] & 0x0f];
            }
            return new String(res);
        }

        /**
         * hexString转byteArr
         * <p>例如：</p>
         * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
         *
         * @param hexString 十六进制字符串
         * @return 字节数组
         */
        public static byte[] hexString2Bytes(String hexString) {
            int len = hexString.length();
            if (len % 2 != 0) {
                throw new IllegalArgumentException("长度不是偶数");
            }
            char[] hexBytes = hexString.toUpperCase().toCharArray();
            byte[] res = new byte[len >>> 1];
            for (int i = 0; i < len; i += 2) {
                res[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
            }
            return res;
        }

        /**
         * hexChar转int
         *
         * @param hexChar hex单个字节
         * @return 0..15
         */
        private static int hex2Dec(char hexChar) {
            if (hexChar >= '0' && hexChar <= '9') {
                return hexChar - '0';
            } else if (hexChar >= 'A' && hexChar <= 'F') {
                return hexChar - 'A' + 10;
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * charArr转byteArr
         *
         * @param chars 字符数组
         * @return 字节数组
         */
        public static byte[] chars2Bytes(char[] chars) {
            int len = chars.length;
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) (chars[i]);
            }
            return bytes;
        }

        /**
         * byteArr转charArr
         *
         * @param bytes 字节数组
         * @return 字符数组
         */
        public static char[] bytes2Chars(byte[] bytes) {
            int len = bytes.length;
            char[] chars = new char[len];
            for (int i = 0; i < len; i++) {
                chars[i] = (char) (bytes[i] & 0xff);
            }
            return chars;
        }

        /**
         * inputStream转outputStream
         *
         * @param is 输入流
         * @return outputStream子类
         */
        public static ByteArrayOutputStream input2OutputStream(InputStream is) {
            if (is == null) return null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] b = new byte[ConstantData.KB];
                int len;
                while ((len = is.read(b, 0, ConstantData.KB)) != -1) {
                    os.write(b, 0, len);
                }
                return os;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                FileUtil.closeIO(is);
            }
        }

        /**
         * outputStream转inputStream
         *
         * @param out 输出流
         * @return inputStream子类
         */
        public ByteArrayInputStream output2InputStream(OutputStream out) {
            if (out == null) return null;
            return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        }

        /**
         * inputStream转byteArr
         *
         * @param is 输入流
         * @return 字节数组
         */
        public static byte[] inputStream2Bytes(InputStream is) {
            return input2OutputStream(is).toByteArray();
        }

        /**
         * byteArr转inputStream
         *
         * @param bytes 字节数组
         * @return 输入流
         */
        public static InputStream bytes2InputStream(byte[] bytes) {
            return new ByteArrayInputStream(bytes);
        }

        /**
         * outputStream转byteArr
         *
         * @param out 输出流
         * @return 字节数组
         */
        public static byte[] outputStream2Bytes(OutputStream out) {
            if (out == null) return null;
            return ((ByteArrayOutputStream) out).toByteArray();
        }

        /**
         * outputStream转byteArr
         *
         * @param bytes 字节数组
         * @return 字节数组
         */
        public static OutputStream bytes2OutputStream(byte[] bytes) {
            ByteArrayOutputStream os = null;
            try {
                os = new ByteArrayOutputStream();
                os.write(bytes);
                return os;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                FileUtil.closeIO(os);
            }
        }

//    /**
//     * inputStream转string按编码
//     *
//     * @param is          输入流
//     * @param charsetName 编码格式
//     * @return 字符串
//     */
//    public static String inputStream2String(InputStream is, String charsetName) {
//        if (is == null || StringUtils.isSpace(charsetName)) return null;
//        try {
//            return new String(inputStream2Bytes(is), charsetName);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * string转inputStream按编码
//     *
//     * @param string      字符串
//     * @param charsetName 编码格式
//     * @return 输入流
//     */
//    public static InputStream string2InputStream(String string, String charsetName) {
//        if (string == null || StringUtils.isSpace(charsetName)) return null;
//        try {
//            return new ByteArrayInputStream(string.getBytes(charsetName));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

        /**
         * outputStream转string按编码
         *
         * @param out         输出流
         * @param charsetName 编码格式
         * @return 字符串
         */
        public static String outputStream2String(OutputStream out, String charsetName) {
            if (out == null) return null;
            try {
                return new String(outputStream2Bytes(out), charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

//    /**
//     * string转outputStream按编码
//     *
//     * @param string      字符串
//     * @param charsetName 编码格式
//     * @return 输入流
//     */
//    public static OutputStream string2OutputStream(String string, String charsetName) {
//        if (string == null || StringUtils.isSpace(charsetName)) return null;
//        try {
//            return bytes2OutputStream(string.getBytes(charsetName));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

        /**
         * bitmap转byteArr
         *
         * @param bitmap bitmap对象
         * @param format 格式
         * @return 字节数组
         */
        public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
            if (bitmap == null) return null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                bitmap.compress(format, 100, baos);
                return baos.toByteArray();
            } finally {
                FileUtil.closeIO(baos);
            }
        }

        /**
         * byteArr转bitmap
         *
         * @param bytes 字节数组
         * @return bitmap对象
         */
        public static Bitmap bytes2Bitmap(byte[] bytes) {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        /**
         * drawable转bitmap
         *
         * @param drawable drawable对象
         * @return bitmap对象
         */
        public static Bitmap drawable2Bitmap(Drawable drawable) {
            return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
        }

        /**
         * bitmap转drawable
         *
         * @param resources resources对象
         * @param bitmap    bitmap对象
         * @return drawable对象
         */
        public static Drawable bitmap2Drawable(Resources resources, Bitmap bitmap) {
            return bitmap == null ? null : new BitmapDrawable(resources, bitmap);
        }

        /**
         * drawable转byteArr
         *
         * @param drawable drawable对象
         * @param format   格式
         * @return 字节数组
         */
        public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format) {
            return bitmap2Bytes(drawable2Bitmap(drawable), format);
        }

        /**
         * byteArr转drawable
         *
         * @param resources resources对象
         * @param bytes     字节数组
         * @return drawable对象
         */
        public static Drawable bytes2Drawable(Resources resources, byte[] bytes) {
            return bitmap2Drawable(resources, bytes2Bitmap(bytes));
        }

        /**
         * dp转px
         *
         * @param context 上下文
         * @param dpValue dp值
         * @return px值
         */
        public static int dp2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * px转dp
         *
         * @param context 上下文
         * @param pxValue px值
         * @return dp值
         */
        public static int px2dp(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        /**
         * sp转px
         *
         * @param context 上下文
         * @param spValue sp值
         * @return px值
         */
        public static int sp2px(Context context, float spValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }

        /**
         * px转sp
         *
         * @param context 上下文
         * @param pxValue px值
         * @return sp值
         */
        public static int px2sp(Context context, float pxValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }
    }

    /**
     * <pre>
     *  @author 梁玉涛 (https://github.com/sinothk)
     *  @Create 2018/2/9 15:52
     *  @Project: OUtilsLib
     *  @Description:
     *  @Update:
     * <pre>
     */

    public static class CollectUtil<E> {

        public static boolean isNotEmpty(List<?> list) {
            return !(list == null || list.isEmpty());
        }

        public static boolean isEmpty4Map(Map<?, ?> map) {
            return !(map == null || map.isEmpty());
        }

        public static boolean isEmpty4Arrays(Object[] array) {
            return !(array == null || array.length == 0);
        }

        public static final String DESC = "desc";
        public static final String ASC = "asc";

        /**
         * 实体列表中，按中文关键字排序:默认升序
         *
         * @param list
         * @param method 属性的get方法名
         */
        public void sortByChineseKeyword(List<E> list, final String method) {
            sortByChineseKeyword(list, method, "asc");

//            // ================实体列表中，按中文关键字排序=================
//            ArrayList<Bank> dList = new ArrayList<Bank>();
//            dList.add(new Bank("11", "啊商银行"));
//            dList.add(new Bank("08", "上海农商银行"));
//            dList.add(new Bank("13", "波业银行"));
//            dList.add(new Bank("04", "中国工商银行"));
//
//            // 排序
//            new CollectUtil<Bank>().sortByChineseKeyword(dList, "getBankName");
//
//            // 获得字母字符串数组
//            String[] pinyins = new CollectUtil<Bank>().getChineseUppercase(dList, "getBankName");
//
//            for (int i = 0; i < pinyins.length; i++) {
//                System.out.println("uppercase = " + pinyins[i]);
//            }
        }

        /**
         * 实体列表中，按中文关键字排序:根据指定的排序方式排序
         *
         * @param list
         * @param method 属性的get方法名
         * @param sort
         */
        public void sortByChineseKeyword(List<E> list, final String method, final String sort) {
            Collections.sort(list, new Comparator<Object>() {
                Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

                public int compare(Object a, Object b) {
                    int ret = 0;
                    try {
                        Method m1 = ((E) a).getClass().getMethod(method, null);
                        Method m2 = ((E) b).getClass().getMethod(method, null);

                        String v1 = m1.invoke(((E) a), null).toString();
                        String v2 = m2.invoke(((E) b), null).toString();

                        if (sort != null && "desc".equals(sort)) {
                            // 倒序
                            ret = cmp.compare(v2, v1);
                        } else {
                            // 正序 {}
                            ret = cmp.compare(v1, v2);
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ne) {
                        System.out.println(ne);
                    }
                    return ret;
                }
            });

//        ArrayList<Bank> dList = new ArrayList<Bank>();
//        dList.add(new Bank("11", "啊商银行"));
//        dList.add(new Bank("12", "上海农商银行"));
//        dList.add(new Bank("13", "波业银行"));
//        dList.add(new Bank("14", "中国工商银行"));
//        new OCollectUtil<Bank>().sort4ChineseKeyword(dList, "getBankName", OCollectUtil.ASC);
//        for (Bank b : dList) {
//            System.out.println("code = " + b.getBankCode() + "，name = " + b.getBankName());
//        }
        }

        /**
         * 纯文字列表的排序
         *
         * @param list
         */
        public static void sort4ChineseStringList(ArrayList<String> list) {
            Collections.sort(list, Collator.getInstance(java.util.Locale.CHINA));
//        // ================列表中，按中文关键字排序=================
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("一鸣惊人");
//        list.add("人山人海");
//        list.add("海阔天空");
//        list.add("空前绝后");
//        list.add("后来居上");
//        OCollectUtil.sort4ChineseStringList(list);
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("MainActivity", i + " = " + list.get(i));
//        }
        }

        /**
         * 获得列表中，实体内某个中文属性的首文字字母：使用于通信录中，获得联系人字母导航。
         *
         * @param dList  实体列表
         * @param method 实体中获得属性值的get方法名
         * @return
         */
        public String[] getChineseUppercase(ArrayList<E> dList, String method) {

            String[] pinyinArray = new String[dList.size()];

            for (int i = 0; i < dList.size(); i++) {
                try {
                    Method m1 = ((E) dList.get(i)).getClass().getMethod(method, null);
                    String v1 = m1.invoke(((E) dList.get(i)), null).toString();

                    char word = v1.charAt(0);
                    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
                    format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

                    String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(word, format);

                    String firstPinYin = pinyin[0];
                    pinyinArray[i] = firstPinYin.charAt(0) + "";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return pinyinArray;
//
////        // ================实体列表中，按中文关键字排序=================
////        ArrayList<Bank> dList = new ArrayList<Bank>();
////        dList.add(new Bank("11", "啊商银行"));
////        dList.add(new Bank("08", "上海农商银行"));
////        dList.add(new Bank("13", "波业银行"));
////        dList.add(new Bank("04", "中国工商银行"));
////
////        // 排序
////        new OCollectUtil<Bank>().sortByChineseKeyword(dList, "getBankName");
////
////        // 获得字母字符串数组
////        String[] pinyins =  new OCollectUtil<Bank>().getChineseUppercase(dList, "getBankName");
////
////        for (int i = 0; i < pinyins.length; i++) {
////            Log.e("MainActivity", "uppercase = " + pinyins[i]);
        }

        /**
         * Activity操作
         */
        public static class ActivityUtil {

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

        /**
         * 正则工具类-http://blog.csdn.net/xyang81/article/details/7706408
         * 提供验证邮箱、手机号、电话号码、身份证号码、数字等方法
         * Created by LYT on 2017/8/14.
         * 功能：
         */
        public final static class RegexUtils {

            /**
             * 手机号码，中间4位星号替换
             *
             * @param phone 手机号
             * @return
             */
            public static String phoneNoHide(String phone) {
                // 括号表示组，被替换的部分$n表示第n组的内容
                // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
                // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
                // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
                // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
                return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            }

            /**
             * 银行卡号，保留最后4位，其他星号替换
             *
             * @param cardId 卡号
             * @return
             */
            public static String cardIdHide(String cardId) {
                return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
            }

            /**
             * 是否为车牌号（沪A88888）
             */
            public static boolean checkVehicleNo(String vehicleNo) {
                Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$");
                return pattern.matcher(vehicleNo).find();

            }

            /**
             * 验证身份证号码
             *
             * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkIdCard(String idCard) {
                String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
                return Pattern.matches(regex, idCard);
            }


            /**
             * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
             *
             * @param mobile 移动、联通、电信运营商的号码段
             *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
             *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
             *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
             *               <p>电信的号段：133、153、180（未启用）、189</p>
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkMobile(String mobile) {
                String regex = "(\\+\\d+)?1[3458]\\d{9}$";
                return Pattern.matches(regex, mobile);
            }

            /**
             * 验证固定电话号码
             *
             * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
             *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
             *              数字之后是空格分隔的国家（地区）代码。</p>
             *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
             *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
             *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkPhone(String phone) {
                String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
                return Pattern.matches(regex, phone);
            }

            /**
             * 验证Email
             *
             * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkEmail(String email) {
                String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
                return Pattern.matches(regex, email);
            }

            /**
             * 验证整数（正整数和负整数）
             *
             * @param digit 一位或多位0-9之间的整数
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkDigit(String digit) {
                String regex = "\\-?[1-9]\\d+";
                return Pattern.matches(regex, digit);
            }

            /**
             * 验证整数和浮点数（正负整数和正负浮点数）
             *
             * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkDecimals(String decimals) {
                String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
                return Pattern.matches(regex, decimals);
            }

            /**
             * 验证空白字符
             *
             * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkBlankSpace(String blankSpace) {
                String regex = "\\s+";
                return Pattern.matches(regex, blankSpace);
            }

            /**
             * 验证中文
             *
             * @param chinese 中文字符
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkChinese(String chinese) {
                String regex = "^[\u4E00-\u9FA5]+$";
                return Pattern.matches(regex, chinese);
            }

            /**
             * 验证日期（年月日）
             *
             * @param birthday 日期，格式：1992-09-03，或1992.09.03
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkBirthday(String birthday) {
                String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
                return Pattern.matches(regex, birthday);
            }

            /**
             * 验证URL地址
             *
             * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkURL(String url) {
                String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
                return Pattern.matches(regex, url);
            }

            /**
             * 匹配中国邮政编码
             *
             * @param postcode 邮政编码
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkPostcode(String postcode) {
                String regex = "[1-9]\\d{5}";
                return Pattern.matches(regex, postcode);
            }

            /**
             * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
             *
             * @param ipAddress IPv4标准地址
             * @return 验证成功返回true，验证失败返回false
             */
            public static boolean checkIpAddress(String ipAddress) {
                String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
                return Pattern.matches(regex, ipAddress);
            }

            /**
             * 验证手机号（简单）
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isMobileSimple(String string) {
                return isMatch(ConstantData.REGEX_MOBILE_SIMPLE, string);
            }

            /**
             * 验证手机号（精确）
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isMobileExact(String string) {
                return isMatch(ConstantData.REGEX_MOBILE_EXACT, string);
            }

            /**
             * 验证电话号码
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isTel(String string) {
                return isMatch(ConstantData.REGEX_TEL, string);
            }

            /**
             * 验证身份证号码15位
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isIDCard15(String string) {
                return isMatch(ConstantData.REGEX_IDCARD15, string);
            }

            /**
             * 验证身份证号码18位
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isIDCard18(String string) {
                return isMatch(ConstantData.REGEX_IDCARD18, string);
            }

            /**
             * 验证邮箱
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isEmail(String string) {
                return isMatch(ConstantData.REGEX_EMAIL, string);
            }

            /**
             * 验证URL
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isURL(String string) {
                return isMatch(ConstantData.REGEX_URL, string);
            }

            /**
             * 验证汉字
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isChz(String string) {
                return isMatch(ConstantData.REGEX_CHZ, string);
            }

            /**
             * 验证用户名
             * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isUsername(String string) {
                return isMatch(ConstantData.REGEX_USERNAME, string);
            }

            /**
             * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isDate(String string) {
                return isMatch(ConstantData.REGEX_DATE, string);
            }

            /**
             * 验证IP地址
             *
             * @param string 待验证文本
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isIP(String string) {
                return isMatch(ConstantData.REGEX_IP, string);
            }

            /**
             * string是否匹配regex
             *
             * @param regex  正则表达式字符串
             * @param string 要匹配的字符串
             * @return {@code true}: 匹配<br>{@code false}: 不匹配
             */
            public static boolean isMatch(String regex, String string) {
                return !StringUtil.isEmpty(string) && Pattern.matches(regex, string);
            }
        }
    }

    /**
     * <pre>
     *  创建:  LiangYT 2018/6/20/020 on 16:00
     *  项目: gxqptAndroid
     *  描述:
     *  更新:
     * <pre>
     */
    public static class NativeUtil {

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

    /**
     * Company 汉合瑞信.
     * Created by 梁玉涛 on 2015/9/22.
     */
    public static class AppUtil {

        private static Context mContext;

        public static void init(Context context) {
            mContext = context;
        }


        /**
         * 获取应用程序版本名称信息
         */
        public static String getAppVersionName() {

            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return null;
            }

            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                return pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 获取app版本号
         */
        public static int getAppVersionCode() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return 0;
            }

            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                return pi.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return 0;
        }

        /**
         * 强制隐藏输入法键盘
         */
        public static void hideInput(View view) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        /**
         * 切换软键盘的状态 如当前为收起变为弹出,若当前为弹出变为收起
         */
        private void toggleInput() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

        /**
         * 判断Apk是调试版还是正式版
         *
         * @return
         */
        public static boolean isDebug() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return false;
            }

            try {
                ApplicationInfo info = mContext.getApplicationInfo();
                return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            } catch (Exception e) {
            }
            return false;
        }

        /**
         * 安装App
         * <p>根据路径安装App</p>
         *
         * @param filePath 文件路径
         */
        public static void installApp(String filePath) {
            installApp(new File(filePath));
        }

        /**
         * 安装App
         * <p>根据文件安装App</p>
         *
         * @param file 文件
         */
        public static void installApp(File file) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

        /**
         * 卸载指定包名的App
         *
         * @param packageName 包名
         */
        public void uninstallApp(String packageName) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

        /**
         * 封装App信息的Bean类
         */
        public static class AppInfo {

            private String name;
            private Drawable icon;
            private String packageName;
            private String packagePath;
            private String versionName;
            private int versionCode;
            private boolean isSD;
            private boolean isUser;

            public Drawable getIcon() {
                return icon;
            }

            public void setIcon(Drawable icon) {
                this.icon = icon;
            }

            public boolean isSD() {
                return isSD;
            }

            public void setSD(boolean SD) {
                isSD = SD;
            }

            public boolean isUser() {
                return isUser;
            }

            public void setUser(boolean user) {
                isUser = user;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packagName) {
                this.packageName = packagName;
            }

            public String getPackagePath() {
                return packagePath;
            }

            public void setPackagePath(String packagePath) {
                this.packagePath = packagePath;
            }

            public int getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(int versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersionName() {
                return versionName;
            }

            public void setVersionName(String versionName) {
                this.versionName = versionName;
            }

            /**
             * @param name        名称
             * @param icon        图标
             * @param packageName 包名
             * @param packagePath 包路径
             * @param versionName 版本号
             * @param versionCode 版本Code
             * @param isSD        是否安装在SD卡
             * @param isUser      是否是用户程序
             */
            public AppInfo(String name, Drawable icon, String packageName, String packagePath,
                           String versionName, int versionCode, boolean isSD, boolean isUser) {
                this.setName(name);
                this.setIcon(icon);
                this.setPackageName(packageName);
                this.setPackagePath(packagePath);
                this.setVersionName(versionName);
                this.setVersionCode(versionCode);
                this.setSD(isSD);
                this.setUser(isUser);
            }

//        @Override
//        public String toString() {
//            return getName() + "\n"
//                    + getIcon() + "\n"
//                    + getPackageName() + "\n"
//                    + getPackagePath() + "\n"
//                    + getVersionName() + "\n"
//                    + getVersionCode() + "\n"
//                    + isSD() + "\n"
//                    + isUser() + "\n";
//        }
        }

        /**
         * 获取当前App信息
         * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）</p>
         *
         * @return 当前应用的AppInfo
         */
        public static AppInfo getAppInfo() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return null;
            }

            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(mContext.getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return pi != null ? getBean(pm, pi) : null;
        }

        /**
         * 得到AppInfo的Bean
         *
         * @param pm 包的管理
         * @param pi 包的信息
         * @return AppInfo类
         */
        private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
            ApplicationInfo ai = pi.applicationInfo;
            String name = ai.loadLabel(pm).toString();
            Drawable icon = ai.loadIcon(pm);
            String packageName = pi.packageName;
            String packagePath = ai.sourceDir;
            String versionName = pi.versionName;
            int versionCode = pi.versionCode;
            boolean isSD = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
            boolean isUser = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
            return new AppInfo(name, icon, packageName, packagePath, versionName, versionCode, isSD, isUser);
        }

        /**
         * 获取所有已安装App信息
         * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本Code，是否安装在SD卡，是否是用户程序）</p>
         * <p>依赖上面的getBean方法</p>
         *
         * @return 所有已安装的AppInfo列表
         */
        public static List<AppInfo> getAllAppsInfo() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return null;
            }

            List<AppInfo> list = new ArrayList<>();
            PackageManager pm = mContext.getPackageManager();
            // 获取系统中安装的所有软件信息
            List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
            for (PackageInfo pi : installedPackages) {
                if (pi != null) {
                    list.add(getBean(pm, pi));
                }
            }
            return list;
        }

        /**
         * 根据包名获取意图
         *
         * @param packageName 包名
         * @return 意图
         */
        private static Intent getIntentByPackageName(String packageName) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return null;
            }

            return mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        }

        /**
         * 根据包名判断App是否安装
         *
         * @param packageName 包名
         * @return {@code true}: 已安装<br>{@code false}: 未安装
         */
        public static boolean isInstallApp(String packageName) {
            return getIntentByPackageName(packageName) != null;
        }

        /**
         * 打开指定包名的App
         *
         * @param packageName 包名
         * @return {@code true}: 打开成功<br>{@code false}: 打开失败
         */
        public static boolean openAppByPackageName(String packageName) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return false;
            }

            Intent intent = getIntentByPackageName(packageName);
            if (intent != null) {
                mContext.startActivity(intent);
                return true;
            }
            return false;
        }

        /**
         * 打开指定包名的App应用信息界面
         *
         * @param packageName 包名
         */
        public static void openAppInfo(String packageName) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + packageName));
            mContext.startActivity(intent);
        }

        /**
         * 可用来做App信息分享
         *
         * @param info 分享信息
         */
        public static void shareAppInfo(String info) {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
                }
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, info);
            mContext.startActivity(intent);
        }

        /**
         * 判断当前App处于前台还是后台
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.GET_TASKS"/>}</p>
         * <p>并且必须是系统应用该方法才有效</p>
         *
         * @return {@code true}: 后台<br>{@code false}: 前台
         */
        public static boolean isAppBackground() {
            if (mContext == null) return false;

            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            @SuppressWarnings("deprecation")
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 获取屏幕的宽度px
         *
         * @param context 上下文
         * @return 屏幕宽px
         */
        public static int getScreenWidth(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
            windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
            return outMetrics.widthPixels;
        }

        /**
         * 获取屏幕的高度px
         *
         * @param context 上下文
         * @return 屏幕高px
         */
        public static int getScreenHeight(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
            windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
            return outMetrics.heightPixels;
        }

        /**
         * 设置透明状态栏(api大于19方可使用)
         * <p>可在Activity的onCreat()中调用</p>
         * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下</p>
         * <p>android:clipToPadding="true"</p>
         * <p>android:fitsSystemWindows="true"</p>
         *
         * @param activity activity
         */
        public static void setTransparentStatusBar(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }

        /**
         * 隐藏状态栏
         * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
         * <p>此方法Activity可以继承AppCompatActivity</p>
         * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
         * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
         * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
         *
         * @param activity activity
         */
        public static void hideStatusBar(Activity activity) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        /**
         * 获取状态栏高度
         *
         * @param context 上下文
         * @return 状态栏高度
         */
        public static int getStatusBarHeight(Context context) {
            int result = 0;
            int resourceId = context.getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        /**
         * 判断状态栏是否存在
         *
         * @param activity activity
         * @return {@code true}: 存在<br>{@code false}: 不存在
         */
        public static boolean isStatusBarExists(Activity activity) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }

        /**
         * 获取ActionBar高度
         *
         * @param activity activity
         * @return ActionBar高度
         */
        public static int getActionBarHeight(Activity activity) {
            TypedValue tv = new TypedValue();
            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
            return 0;
        }

//    /**
//     * 显示通知栏
//     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
//     *
//     * @param context        上下文
//     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
//     */
//    public static void showNotificationBar(Context context, boolean isSettingPanel) {
//        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
//                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
//        invokePanels(context, methodName);
//    }
//
//    /**
//     * 隐藏通知栏
//     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
//     *
//     * @param context 上下文
//     */
//    public static void hideNotificationBar(Context context) {
//        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
//        invokePanels(context, methodName);
//    }
//    /**
//     * 反射唤醒通知栏
//     *
//     * @param context    上下文
//     * @param methodName 方法名
//     */
//    private static void invokePanels(Context context, String methodName) {
//        try {
//            Object service = context.getSystemService("statusbar");
//            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
//            Method expand = statusBarManager.getMethod(methodName);
//            expand.invoke(service);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

        /**
         * 设置屏幕为横屏
         * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
         * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
         * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
         * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
         * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
         *
         * @param activity activity
         */
        public static void setLandscape(Activity activity) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity activity
         * @return Bitmap
         */
        public static Bitmap captureWithStatusBar(Activity activity) {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            int width = getScreenWidth(activity);
            int height = getScreenHeight(activity);
            Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
            view.destroyDrawingCache();
            return bp;
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         * <p>需要用到上面获取状态栏高度getStatusBarHeight的方法</p>
         *
         * @param activity activity
         * @return Bitmap
         */
        public static Bitmap captureWithoutStatusBar(Activity activity) {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            int statusBarHeight = getStatusBarHeight(activity);
            int width = getScreenWidth(activity);
            int height = getScreenHeight(activity);
            Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
            view.destroyDrawingCache();
            return bp;
        }

        /**
         * 判断是否锁屏
         *
         * @param context 上下文
         * @return {@code true}: 是<br>{@code false}: 否
         */
        public static boolean isScreenLock(Context context) {
            KeyguardManager km = (KeyguardManager) context
                    .getSystemService(Context.KEYGUARD_SERVICE);
            return km.inKeyguardRestrictedInputMode();
        }

        public static int getScreenWidth1(Context context) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }

        public static int getScreenHeight1(Context context) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        }

        public static int getStatusHeight(Context context) {
            int statusHeight = -1;
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return statusHeight;
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         */
        public static Bitmap snapShotWithStatusBar(Activity activity) {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            int width = getScreenWidth1(activity);
            int height = getScreenHeight1(activity);
            Bitmap bp = null;
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
            view.destroyDrawingCache();
            return bp;
        }

        /**
         * 获取当前屏幕截图，不包含状态栏 *
         */
        public static Bitmap snapShotWithoutStatusBar(Activity activity) {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int width = getScreenWidth1(activity);
            int height = getScreenHeight1(activity);
            Bitmap bp = null;
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
            view.destroyDrawingCache();
            return bp;
        }
    }

    /**
     * <pre>
     *  @author 梁玉涛 (https://github.com/sinothk)
     *  @Create 2018/1/18 17:40
     *  @Project: OUtilsLib
     *  @Description:
     *  @Update:
     * <pre>
     */
    public static class CacheCleanUtil {

        /**
         * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
         */
        public static void cleanInternalCache(Context context) {
            deleteFilesByDirectory(context.getCacheDir());
        }

        /**
         * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
         */
        public static void cleanDatabases(Context context) {
            deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
        }

        /**
         * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
         * context
         */
        public static void cleanSharedPreference(Context context) {
            deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
        }

        /**
         * 按名字清除本应用数据库 * * @param context * @param dbName
         */
        public static void cleanDatabaseByName(Context context, String dbName) {
            context.deleteDatabase(dbName);
        }

        /**
         * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
         */
        public static void cleanFiles(Context context) {
            deleteFilesByDirectory(context.getFilesDir());
        }

        /**
         * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
         * context
         */
        public static void cleanExternalCache(Context context) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                deleteFilesByDirectory(context.getExternalCacheDir());
            }
        }

        /**
         * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
         */
        public static void cleanCustomCache(String filePath) {
            deleteFilesByDirectory(new File(filePath));
        }

        /**
         * 清除本应用所有的数据 * * @param context * @param filepath
         */
        public static void cleanApplicationData(Context context, String... filepath) {
            cleanInternalCache(context);
            cleanExternalCache(context);
            cleanDatabases(context);
            cleanSharedPreference(context);
            cleanFiles(context);
            for (String filePath : filepath) {
                cleanCustomCache(filePath);
            }
        }

        /**
         * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
         */
        private static void deleteFilesByDirectory(File directory) {
            if (directory != null && directory.exists() && directory.isDirectory()) {
                for (File item : directory.listFiles()) {
                    item.delete();
                }
            }
        }
    }

    /**
     * Created by LYT on 2017/8/11.
     * 功能：
     */
    public static class PhoneUtil {

        private static Context mContext;

        public static void init(Context context) {
            mContext = context;
        }

        public static int getPhoneWidth(Context mContext) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            return width;
        }

        public static int getPhoneHeight(Context mContext) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            return height;
        }

        /**
         * 判断设备是否是手机
         *
         * @param context 上下文
         * @return {@code true}: 是<br>{@code false}: 否
         */
        public static boolean isPhone(Context context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
        }

        /**
         * 获取手机的IMIE
         * <p>需与{@link #isPhone(Context)}一起使用</p>
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
         *
         * @param context 上下文
         * @return IMIE码
         */
        @SuppressLint("MissingPermission")
        public static String getPhoneIMEI(Context context) {
            String deviceId;
            if (isPhone(context)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tm.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return deviceId;
        }

        /**
         * 获取手机状态信息
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
         *
         * @param context 上下文
         * @return DeviceId(IMEI) = 99000311726612<br>
         * DeviceSoftwareVersion = 00<br>
         * Line1Number =<br>
         * NetworkCountryIso = cn<br>
         * NetworkOperator = 46003<br>
         * NetworkOperatorName = 中国电信<br>
         * NetworkType = 6<br>
         * honeType = 2<br>
         * SimCountryIso = cn<br>
         * SimOperator = 46003<br>
         * SimOperatorName = 中国电信<br>
         * SimSerialNumber = 89860315045710604022<br>
         * SimState = 5<br>
         * SubscriberId(IMSI) = 460030419724900<br>
         * VoiceMailNumber = *86<br>
         */
        @SuppressLint("MissingPermission")
        public static String getPhoneStatus(Context context) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String str = "";
            str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
            str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
            str += "Line1Number = " + tm.getLine1Number() + "\n";
            str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
            str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
            str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
            str += "NetworkType = " + tm.getNetworkType() + "\n";
            str += "honeType = " + tm.getPhoneType() + "\n";
            str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
            str += "SimOperator = " + tm.getSimOperator() + "\n";
            str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
            str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
            str += "SimState = " + tm.getSimState() + "\n";
            str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
            str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
            return str;
        }

        /**
         * 跳至填充好phoneNumber的拨号界面
         *
         * @param context     上下文
         * @param phoneNumber 电话号码
         */
        public static void dial(Context context, String phoneNumber) {
            context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
        }

        /**
         * 拨打phoneNumber
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
         *
         * @param context     上下文
         * @param phoneNumber 电话号码
         */
        public static void call(Context context, String phoneNumber) {
            context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
        }

        /**
         * 发送短信
         *
         * @param context     上下文
         * @param phoneNumber 电话号码
         * @param content     内容
         */
        public static void sendSms(Context context, String phoneNumber, String content) {
            Uri uri = Uri.parse("smsto:" + (StringUtil.isEmpty(phoneNumber) ? "" : phoneNumber));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", StringUtil.isEmpty(content) ? "" : content);
            context.startActivity(intent);
        }

        /**
         * 获取手机联系人
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
         *
         * @param context 上下文;
         * @return 联系人链表
         */
        public static List<HashMap<String, String>> getAllContactInfo(Context context) {
            SystemClock.sleep(3000);
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            // 1.获取内容解析者
            ContentResolver resolver = context.getContentResolver();
            // 2.获取内容提供者的地址:com.android.contacts
            // raw_contacts表的地址 :raw_contacts
            // view_data表的地址 : data
            // 3.生成查询地址
            Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
            Uri date_uri = Uri.parse("content://com.android.contacts/data");
            // 4.查询操作,先查询raw_contacts,查询contact_id
            // projection : 查询的字段
            Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"},
                    null, null, null);
            // 5.解析cursor
            while (cursor.moveToNext()) {
                // 6.获取查询的数据
                String contact_id = cursor.getString(0);
                // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
                // 判断contact_id是否为空
                if (!StringUtil.isEmpty(contact_id)) {//null   ""
                    // 7.根据contact_id查询view_data表中的数据
                    // selection : 查询条件
                    // selectionArgs :查询条件的参数
                    // sortOrder : 排序
                    // 空指针: 1.null.方法 2.参数为null
                    Cursor c = resolver.query(date_uri, new String[]{"data1",
                                    "mimetype"}, "raw_contact_id=?",
                            new String[]{contact_id}, null);
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 8.解析c
                    while (c.moveToNext()) {
                        // 9.获取数据
                        String data1 = c.getString(0);
                        String mimetype = c.getString(1);
                        // 10.根据类型去判断获取的data1数据并保存
                        if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            // 电话
                            map.put("phone", data1);
                        } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                            // 姓名
                            map.put("name", data1);
                        }
                    }
                    // 11.添加到集合中数据
                    list.add(map);
                    // 12.关闭cursor
                    c.close();
                }
            }
            // 12.关闭cursor
            cursor.close();
            return list;
        }

        /**
         * 打开手机联系人界面点击联系人后便获取该号码
         * <p>参照以下注释代码</p>
         */
        public static void getContantNum() {
            Log.i("tips", "U should copy the following code.");
        /*
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri uri = data.getData();
                String num = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri,
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    num = cursor.getString(cursor.getColumnIndex("data1"));
                }
                cursor.close();
                num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
            }
        }
        */
        }

        /**
         * 获取手机短信并保存到xml中
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
         *
         * @param context 上下文
         */
        public static void getAllSMS(Context context) {
            // 1.获取短信
            // 1.1获取内容解析者
            ContentResolver resolver = context.getContentResolver();
            // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
            // 1.3获取查询路径
            Uri uri = Uri.parse("content://sms");
            // 1.4.查询操作
            // projection : 查询的字段
            // selection : 查询的条件
            // selectionArgs : 查询条件的参数
            // sortOrder : 排序
            Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
            // 设置最大进度
            int count = cursor.getCount();//获取短信的个数
            // 2.备份短信
            // 2.1获取xml序列器
            XmlSerializer xmlSerializer = Xml.newSerializer();
            try {
                // 2.2设置xml文件保存的路径
                // os : 保存的位置
                // encoding : 编码格式
                xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
                // 2.3设置头信息
                // standalone : 是否独立保存
                xmlSerializer.startDocument("utf-8", true);
                // 2.4设置根标签
                xmlSerializer.startTag(null, "smss");
                // 1.5.解析cursor
                while (cursor.moveToNext()) {
                    SystemClock.sleep(1000);
                    // 2.5设置短信的标签
                    xmlSerializer.startTag(null, "sms");
                    // 2.6设置文本内容的标签
                    xmlSerializer.startTag(null, "address");
                    String address = cursor.getString(0);
                    // 2.7设置文本内容
                    xmlSerializer.text(address);
                    xmlSerializer.endTag(null, "address");
                    xmlSerializer.startTag(null, "date");
                    String date = cursor.getString(1);
                    xmlSerializer.text(date);
                    xmlSerializer.endTag(null, "date");
                    xmlSerializer.startTag(null, "type");
                    String type = cursor.getString(2);
                    xmlSerializer.text(type);
                    xmlSerializer.endTag(null, "type");
                    xmlSerializer.startTag(null, "body");
                    String body = cursor.getString(3);
                    xmlSerializer.text(body);
                    xmlSerializer.endTag(null, "body");
                    xmlSerializer.endTag(null, "sms");
                    System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
                }
                xmlSerializer.endTag(null, "smss");
                xmlSerializer.endDocument();
                // 2.8将数据刷新到文件中
                xmlSerializer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取设备MAC地址
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
         *
         * @param context 上下文
         * @return MAC地址
         */
        public static String getMacAddress(Context context) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                String macAddress = info.getMacAddress();
                if (macAddress != null) {
                    return macAddress.replace(":", "");
                }
            }
            return null;
        }

        /**
         * 获取设备MAC地址
         * <p/>
         * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
         *
         * @return MAC地址
         */

        public static String getMacAddress() {
            String macAddress = null;
            LineNumberReader lnr = null;
            InputStreamReader isr = null;
            try {
                Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
                isr = new InputStreamReader(pp.getInputStream());
                lnr = new LineNumberReader(isr);
                macAddress = lnr.readLine().replace(":", "");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                FileUtil.closeIO(lnr, isr);
            }
            return macAddress == null ? "" : macAddress;
        }

        /**
         * 获取设备厂商，如Xiaomi
         *
         * @return 设备厂商
         */
        public static String getManufacturer() {
            return Build.MANUFACTURER;
        }

        /**
         * 获取设备型号，如MI2SC
         *
         * @return 设备型号
         */
        public static String getModel() {
            String model = Build.MODEL;
            if (model != null) {
                model = model.trim().replaceAll("\\s*", "");
            } else {
                model = "";
            }
            return model;
        }

        /**
         * 拨打电话
         */
        public static void call(Activity activity, String phoneNumber) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        }

        //跳到拨号界面
        public static void jumpDialUI(Activity activity, String phoneNumber) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }

        /**
         * 跳转至拨号界面
         */
        public static void callDial(Activity activity, String phoneNumber) {
            activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
        }

        /**
         * 发送短信
         */
        public static void sendSms(Activity activity, String phoneNumber, String content) {
            Uri uri = Uri.parse("smsto:"
                    + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
            activity.startActivity(intent);
        }

        /**
         * 判断当前App处于前台还是后台状态
         */
        public static boolean isApplicationBackground() {
            if (mContext == null) {
                if (DEBUG) {
                    throw new NullPointerException("mContext == null");
                }
                return false;
            }

            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            @SuppressWarnings("deprecation")
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 判断当前手机是否处于锁屏(睡眠)状态
         */
        public static boolean isSleeping(Context mContext) {
            KeyguardManager kgMgr = (KeyguardManager) mContext
                    .getSystemService(Context.KEYGUARD_SERVICE);
            boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
            return isSleeping;
        }
    }
    
    public static void main(String args[]) {
    }
}