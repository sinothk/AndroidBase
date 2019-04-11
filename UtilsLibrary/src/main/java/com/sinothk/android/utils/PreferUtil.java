package com.sinothk.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

import static com.sinothk.android.utils.BuildConfig.DEBUG;

/**
 * 首选项设置
 */
public class PreferUtil{

    private static Context mContext;
    private static String TAG = "Preferences";

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * @param key   key
     * @param value 只能是Boolean，Integer，Long，Float,String;
     * @return 保存状态
     */
    @Deprecated //使用set代替
    public static boolean setPreferences(String key, Object value) {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = settings.edit();
                if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (long) value);
                } else if (value instanceof Float || value instanceof Double) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else {
                    Log.e(TAG, "Unexpected type:" + key + "=" + value);
                }
                return editor.commit();
            } else {
                initTip();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * store preference settings:
     * value 只能是Boolean，Integer，Long，Float,String;
     */
    public static boolean set(String key, Object value) {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = settings.edit();
                if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (long) value);
                } else if (value instanceof Float || value instanceof Double) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else {
                    Log.e(TAG, "Unexpected type:" + key + "=" + value);
                }
                return editor.commit();
            } else {
                initTip();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object get(String key, Object deft) {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                Map<String, ?> settingMap = settings.getAll();
                Object obj = settingMap.get(key);
                return obj != null ? obj : deft;
            } else {
                initTip();
                return deft;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deft;
        }
    }

    private static void initTip() {
        if (mContext == null) {
            if (DEBUG) {
                throw new NullPointerException("mContext == null或参数为null, 请在调用前初始化：init(context)");
            }
            return;
        }
    }

    /**
     * get preference settings
     */
    @Deprecated // 使用get代替
    public static Object getPreferences(String key, Object deft) {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                Map<String, ?> settingMap = settings.getAll();
                Object obj = settingMap.get(key);
                return obj != null ? obj : deft;
            } else {
                initTip();
                return deft;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deft;
        }
    }

    /**
     * remove preference settings
     */
    public static boolean remove(String key) {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove(key);
                return editor.commit();
            } else {
                initTip();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除SP中所有数据
     *
     * @return
     */
    public static boolean clearAll() {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear().apply();
                return true;
            } else {
                initTip();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取SP中所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        try {
            if (mContext != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                return settings.getAll();
            } else {
                initTip();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean exist(String key) {
        try {
            if (mContext != null) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
                return sp.contains(key);
            } else {
                initTip();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}