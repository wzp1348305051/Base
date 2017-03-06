package com.wzp.www.base.crash;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 自定义未捕捉异常处理
 *
 * @author wzp
 * @since 2017-02-28
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler sInstance;
    /**
     * 系统默认UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {

    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            /** 如果用户没有处理则让系统默认异常处理器来处理 */
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            /** 退出程序 */
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义异常处理,收集,保存异常信息等操作
     *
     * @param throwable 异常信息
     * @return true（用户处理了异常信息）, false（用户未处理异常信息）
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        // TODO, 此处待实现自定义异常处理,如手机错误日志等
        return true;
    }

}
