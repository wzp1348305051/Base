package com.wzp.www.base.bean.constant;

import android.content.Context;
import android.os.Environment;

import com.wzp.www.base.ui.MyApplication;

/**
 * 全局变量集合
 *
 * @author wzp
 * @since 2017-03-02
 */

public class Global {
    /**
     * APP上下文环境
     */
    public static final Context APP_CONTEXT = MyApplication.sContext;
    /**
     * APP包名
     */
    public static final String APP_PACKAGE_NAME = APP_CONTEXT.getPackageName();
    /**
     * 系统文件分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * 系统路径分隔符
     */
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    /**
     * 系统行分隔符
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * 外置存储卡根路径
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_ROOT = Environment
            .getExternalStorageDirectory().getAbsolutePath() + FILE_SEPARATOR;
    /**
     * 外存储卡的应用程序根目录
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_HOME =
            EXTERNAL_STORAGE_DIRECTORY_ROOT + APP_PACKAGE_NAME + FILE_SEPARATOR;
    /**
     * 外存储卡的应用程序文件目录
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_FILE =
            EXTERNAL_STORAGE_DIRECTORY_HOME + "FILE" + FILE_SEPARATOR;
    /**
     * 外存储卡的应用程序图片目录
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_IMAGE =
            EXTERNAL_STORAGE_DIRECTORY_HOME + "IMAGE" + FILE_SEPARATOR;
    /**
     * 外存储卡的应用程序日志目录
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_LOG =
            EXTERNAL_STORAGE_DIRECTORY_HOME + "LOG" + FILE_SEPARATOR;
    /**
     * 外存储卡的应用程序缓存目录
     */
    public static final String EXTERNAL_STORAGE_DIRECTORY_CACHE =
            EXTERNAL_STORAGE_DIRECTORY_HOME + "CACHE" + FILE_SEPARATOR;
    /**
     * 默认String
     */
    public static final String DEFAULT_STRING = String.valueOf("");
    /**
     * 默认String[]
     */
    public static final String[] DEFAULT_STRING_ARRAY = new String[0];
    /**
     * 默认Integer
     */
    public static final Integer DEFAULT_INTEGER = Integer.valueOf(0);
    /**
     * 默认Int[]
     */
    public static final int[] DEFAULT_INTEGER_ARRAY = new int[0];
    /**
     * 默认Double
     */
    public static final Double DEFAULT_DOUBLE = Double.valueOf(0);
    /**
     * 默认Float
     */
    public static final Float DEFAULT_FLOAT = Float.valueOf(0);
    /**
     * 默认Long
     */
    public static final Long DEFAULT_LONG = Long.valueOf(0);
    /**
     * 默认Boolean
     */
    public static final Boolean DEFAULT_BOOLEAN = Boolean.valueOf(false);
}
