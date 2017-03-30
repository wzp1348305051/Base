package com.wzp.www.base.ui;

import android.app.Application;
import android.content.Context;

import com.wzp.www.base.BuildConfig;
import com.wzp.www.base.crash.CrashHandler;
import com.wzp.www.base.helper.L;

/**
 * 自定义Application
 *
 * @author wzp
 * @since 2017-03-02
 */
public class MyApplication extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        /**自定义异常处理*/
        CrashHandler.getInstance().init();
        /**自定义日志处理*/
        if (BuildConfig.DEBUG) {
            L.configLevel(L.VERBOSE);
        } else {
            L.close();
        }
    }
}
