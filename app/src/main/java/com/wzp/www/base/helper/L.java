package com.wzp.www.base.helper;

import android.text.TextUtils;
import android.util.Log;

import com.wzp.www.base.bean.constant.Global;

/**
 * 日志工具类，可设置日志打印级别
 *
 * @author wzp
 * @since 2017-03-02
 */

public final class L {
    private static final String TAG = L.class.getSimpleName();
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    private static final int CLOSE = 6;
    private static int LEVEL = 0;
    private static boolean isSaveToDb = false;
    private static boolean isSaveToServer = false;

    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 配置日志输出
     *
     * @param isSaveToDb     是否将日志保存至数据库
     * @param isSaveToServer 是否将日志保存至服务器
     */
    public static void config(boolean isSaveToDb, boolean isSaveToServer) {
        L.isSaveToDb = isSaveToDb;
        L.isSaveToServer = isSaveToServer;
    }

    /**
     * 配置日志输出级别
     *
     * @param level 日志输出级别,在L.(VERBOSE,DEBUG,INFO,WARN,ERROR)中选择
     */
    public static void configLevel(int level) {
        LEVEL = level;
    }

    /**
     * 关闭日志输出功能
     */
    public static void close() {
        configLevel(CLOSE);
    }

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.v(tag, msg);
                persist(tag, msg);
            }
        }
    }

    public static void v(String tag, Throwable e) {
        v(tag, getExMsg(e));
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.d(tag, msg);
                persist(tag, msg);
            }
        }
    }

    public static void d(String tag, Throwable e) {
        d(tag, getExMsg(e));
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.i(tag, msg);
                persist(tag, msg);
            }
        }
    }

    public static void i(String tag, Throwable e) {
        i(tag, getExMsg(e));
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.w(tag, msg);
                persist(tag, msg);
            }
        }
    }

    public static void w(String tag, Throwable e) {
        w(tag, getExMsg(e));
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.e(tag, msg);
                persist(tag, msg);
            }
        }
    }

    public static void e(String tag, Throwable e) {
        e(tag, getExMsg(e));
    }

    /**
     * 获取异常信息
     */
    private static String getExMsg(Throwable e) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(e.getMessage()).append(Global.LINE_SEPARATOR);
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            buffer.append(element.toString()).append(Global.LINE_SEPARATOR);
        }
        return buffer.toString();
    }

    /**
     * 持久化处理
     */
    private static void persist(String tag, String msg) {
        if (isSaveToDb) {
            saveToDb(tag, Thread.currentThread().getStackTrace()[2].getMethodName(), msg);
        }
        if (isSaveToServer) {
            saveToServer(tag, msg);
        }
    }

    /**
     * 保存日志至数据库
     */
    private static void saveToDb(String tag, String source, String msg) {
        DbHelper.getInstance().addLog(tag, source, msg);
    }

    /**
     * 保存日志至服务器
     */
    private static void saveToServer(String tag, String msg) {
        // TODO 保存至服务器逻辑
    }

}
