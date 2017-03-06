package com.wzp.www.base.helper;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期处理工具类
 *
 * @author wzp
 * @since 2015-09-06
 */
public class DateHelper {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_FORMAT = YYYY_MM_DD_HH_MM_SS;
    /**
     * 默认日期显示时区
     */
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private DateHelper() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        return formatDate(null, null, null);
    }

    /**
     * 获取当前日期
     *
     * @param format 日期格式，若为null，则以默认值替代
     */
    public static String getCurrentDate(String format) {
        if (TextUtils.isEmpty(format)) {
            format = YYYY_MM_DD;
        }
        return formatDate(null, format, null);
    }

    /**
     * 获取当前日期
     *
     * @param format 日期格式，若为null，则以默认值替代
     * @param locale 日期时区，若为null，则以默认值替代
     */
    public static String getCurrentDate(String format, Locale locale) {
        if (TextUtils.isEmpty(format)) {
            format = YYYY_MM_DD;
        }
        return formatDate(null, format, locale);
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        return formatDate(null, YYYY_MM_DD_HH_MM_SS, null);
    }

    /**
     * 获取当前时间
     *
     * @param format 日期格式，若为null，则以默认值替代
     */
    public static String getCurrentTime(String format) {
        if (TextUtils.isEmpty(format)) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        return formatDate(null, format, null);
    }

    /**
     * 获取当前时间
     *
     * @param format 日期格式，若为null，则以默认值替代
     * @param locale 日期时区，若为null，则以默认值替代
     */
    public static String getCurrentTime(String format, Locale locale) {
        if (format == null) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        return formatDate(null, format, locale);
    }

    /**
     * 格式化指定日期
     *
     * @param date   日期，若为null，则以当前时间替代
     * @param format 日期格式，若为null，则以默认值替代
     * @param locale 日期时区，若为null，则以默认值替代
     */
    public static String formatDate(Date date, String format, Locale locale) {
        if (date == null) {
            date = new Date();
        }
        if (TextUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        return new SimpleDateFormat(format, locale).format(date);
    }

}
