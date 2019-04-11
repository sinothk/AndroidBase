package com.sinothk.android.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LYT on 2017/7/12.
 * 功能：
 */
public class LogUtil {
    private static boolean inited = false;// 初始化

    private static Context mContext;

    private static boolean isPrintLogs = false;

    private static boolean isSaveIntoSdCard = false;
    private static String saveIntoSdCardPath = "";
    private static int isSaveIntoSdCardDays = 3;

    private static SimpleDateFormat simpleDateFormat;
    private static String appVersionName = "";
    private static int appVersionCode = 0;

    /**
     * 只打印在控制台
     *
     * @param context
     * @param printLogs
     */
    public static void init(Context context, boolean printLogs) {
        inited = true;

        mContext = context;
        isPrintLogs = printLogs;
    }

    /**
     * 打印到控制台和SDCard中：指定文件路径，默认保存7天
     *
     * @param context
     * @param filePath
     */
    public static void init(Context context, String filePath) {
        init(context, true);

        //
        isSaveIntoSdCard = true;
        saveIntoSdCardPath = filePath;
        isSaveIntoSdCardDays = 7;

        // 内容数据
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        appVersionName = AppUtil.getAppVersionName();
        appVersionCode = AppUtil.getAppVersionCode();

        String msg = "版本信息：V" + appVersionName + " - " + appVersionCode;
        printLog(LogUtil.class.getName(), 'e', msg, isSaveIntoSdCard);

        // 删除过期文件
        deleteOverDateLogs(filePath, isSaveIntoSdCardDays);
    }

    /**
     * 打印到控制台和SDCard中：指定文件路径，指定文件保存天数
     *
     * @param context
     * @param filePath
     * @param saveDays
     */
    public static void init(Context context, String filePath, int saveDays) {
        init(context, true);

        //
        isSaveIntoSdCard = true;
        saveIntoSdCardPath = filePath;
        isSaveIntoSdCardDays = saveDays;

        // 内容数据
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        appVersionName = AppUtil.getAppVersionName();
        appVersionCode = AppUtil.getAppVersionCode();

        String msg = "版本信息：V" + appVersionName + " - " + appVersionCode;
        printLog(LogUtil.class.getName(), 'e', msg, isSaveIntoSdCard);

        // 删除过期文件
        deleteOverDateLogs(filePath, isSaveIntoSdCardDays);
    }

    // -----------------------------是否保存到SDCard：初始化时指定------------------------------------
    public static void d(Class<?> currClass, String msg) {
        printLog(currClass.getName(), 'd', msg, isSaveIntoSdCard);
    }

    public static void i(Class<?> currClass, String msg) {
        printLog(currClass.getName(), 'i', msg, isSaveIntoSdCard);
    }

    public static void w(Class<?> currClass, String msg) {
        printLog(currClass.getName(), 'w', msg, isSaveIntoSdCard);
    }

    public static void e(Class<?> currClass, String msg) {
        printLog(currClass.getName(), 'e', msg, isSaveIntoSdCard);
    }

    // -----------------------------是否保存到SDCard：调用时指定------------------------------------
    public static void d(Class<?> currClass, String msg, boolean saveIntoSdCard) {
        printLog(currClass.getName(), 'd', msg, saveIntoSdCard);
    }

    public static void i(Class<?> currClass, String msg, boolean saveIntoSdCard) {
        printLog(currClass.getName(), 'i', msg, saveIntoSdCard);
    }

    public static void w(Class<?> currClass, String msg, boolean saveIntoSdCard) {
        printLog(currClass.getName(), 'w', msg, saveIntoSdCard);
    }

    public static void e(Class<?> currClass, String msg, boolean saveIntoSdCard) {
        printLog(currClass.getName(), 'e', msg, saveIntoSdCard);
    }

    /**
     * 统一打印
     *
     * @param tag
     * @param level
     * @param msg
     * @param saveIntoSdCard
     */
    private static void printLog(String tag, char level, String msg, boolean saveIntoSdCard) {
        if (!inited) {
            Log.e(tag, "日志工具类的初始化方法没有被调用过，要初始化后才能使用：LogUtil.init(?)");
            return;
        }

        if (!isPrintLogs) {
            return;
        }

        String LEVEL = "" + level;

        switch (level) {
            case 'd':
                Log.d(tag, msg);
                LEVEL = "DBUG";
                break;
            case 'i':
                Log.i(tag, msg);
                LEVEL = "INFO";
                break;
            case 'w':
                Log.w(tag, msg);
                LEVEL = "WARN";
                break;
            case 'e':
                Log.e(tag, msg);
                LEVEL = "ERRO";
                break;
            default:
                Log.v(tag, msg);
                LEVEL = "VERB";
                break;
        }

        if (saveIntoSdCard) {
            // 日志保存到SDCard
            // 定义文件名
            String time = simpleDateFormat.format(new Date());

            String fileName = time.subSequence(0, 10) + ".txt";
            // 拼装日志内容：2017-07-13:LEVEL:content;
            msg = time + " | " + LEVEL + " | " + msg;

            if (!writeLog2File(saveIntoSdCardPath, fileName, msg)) {
                Log.e(LogUtil.class.getName(), "保存日志到SdCard中失败....");
            }
        }
    }

    /**
     * 打开日志并写入日志文件
     *
     * @param dir      SDCard下的文件夹路径(com/xxx/yy)
     * @param fileName 文件名(ss.txt)
     * @param content  文件内容("test")
     */
    private static boolean writeLog2File(String dir, String fileName, String content) {// 新建或打开日志文件
        //调用 writeLog2File("com/xxx/yy", "log_2017-07-13.txt", "日志一行的内容")
        File file = new File(SDCardUtil.createFileDirectory(dir, true), fileName);

        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(content);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件夹下过期的所有日志文件
     *
     * @param dirStr 文件夹目录
     * @param days   过期的天数
     */
    private static void deleteOverDateLogs(String dirStr, int days) {
        Date date2 = new Date();

        File f = SDCardUtil.createFileDirectory(dirStr, true);// "appLogs/xx/yy"
        File[] files = null;
        if (f.exists()) {
            files = f.listFiles();

            for (int j = 0; j < files.length; j++) {
                try {

                    if (files[j].getName().contains(".")) {
                        String dayStr = files[j].getName().substring(0, files[j].getName().indexOf("."));

                        Date date1 = DateUtil.getDateByDateStr(dayStr, "yyyy-MM-dd");

                        if (DateUtil.getTwoDateInterval(date1, date2) > days) {
                            printLog(LogUtil.class.getName(), 'i', "删除过期日志文件：" + files[j].getPath(), isSaveIntoSdCard);
                            files[j].delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    /**
//     * 删除指定的某个日志文件
//     */
//    public static void delFile(int days) {
//        // 2014-09-23 - 7 => 2014-09-16号
//        String needDelFiel = XLOG_FILE_FORMAT.format(XDateUtil.getDateBeforeNow(days));
//        // 得到"2014-09-16"
//
//        File file = new File(FileUtil.createFileDirectory(XLOG_SDCARD_DIR, true),
//                needDelFiel + XLOG_FILE_NAME);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
}
