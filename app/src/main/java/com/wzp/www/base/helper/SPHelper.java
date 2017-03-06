package com.wzp.www.base.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wzp.www.base.bean.constant.Global;

import java.util.Map;

/**
 * SharedPreferences工具类
 *
 * @author wzp
 * @since 2017-03-02
 */
public class SPHelper {
    private static final Context CONTEXT = Global.APP_CONTEXT;
    private static final SharedPreferences SHARED_PREFERENCE = CONTEXT
            .getSharedPreferences(Global.APP_PACKAGE_NAME, Activity.MODE_PRIVATE);
    private static final Editor EDITOR = SHARED_PREFERENCE.edit();

    private SPHelper() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 异步保存数据
     *
     * @param key   键
     * @param value 值（类型有String,Integer,Boolean,Float,Long）
     */
    public static void doSyncPut(String key, Object value) {
        if (value instanceof String) {
            EDITOR.putString(key, (String) value);
        } else if (value instanceof Integer) {
            EDITOR.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            EDITOR.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            EDITOR.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            EDITOR.putLong(key, (Long) value);
        } else {
            EDITOR.putString(key, value.toString());
        }
        EDITOR.apply();
    }

    /**
     * 同步数据
     *
     * @param key   键
     * @param value 值（类型有String,Integer,Boolean,Float,Long）
     */
    public static boolean doAsyncPut(String key, Object value) {
        if (value instanceof String) {
            EDITOR.putString(key, (String) value);
        } else if (value instanceof Integer) {
            EDITOR.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            EDITOR.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            EDITOR.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            EDITOR.putLong(key, (Long) value);
        } else {
            EDITOR.putString(key, value.toString());
        }
        return EDITOR.commit();
    }

    /**
     * 获取数据
     *
     * @param key      键
     * @param defValue 默认值（类型有String,Integer,Boolean,Float,Long）
     * @return 值, 若不存在则以默认值代替
     */
    public static Object doGet(String key, Object defValue) {
        if (defValue instanceof String) {
            return SHARED_PREFERENCE.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return SHARED_PREFERENCE.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return SHARED_PREFERENCE.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return SHARED_PREFERENCE.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return SHARED_PREFERENCE.getLong(key, (Long) defValue);
        } else {
            return defValue;
        }
    }

    /**
     * 异步移除某个键对应的值
     *
     * @param key 键
     */
    public static void doSyncRemove(String key) {
        EDITOR.remove(key);
        EDITOR.apply();
    }

    /**
     * 同步移除某个键对应的值
     *
     * @param key 键
     */
    public static boolean doAsyncRemove(String key) {
        EDITOR.remove(key);
        return EDITOR.commit();
    }

    /**
     * 异步清除所有数据
     */
    public static void doSyncClear() {
        EDITOR.clear();
        EDITOR.apply();
    }

    /**
     * 同步清除所有数据
     */
    public static boolean doAsyncClear() {
        EDITOR.clear();
        return EDITOR.commit();
    }

    /**
     * 检测某个key是否已经存在
     *
     * @param key 键
     * @return true or false
     */
    public static boolean contains(String key) {
        return SHARED_PREFERENCE.contains(key);
    }

    /**
     * 返回所有键值对
     *
     * @return 键值对字典
     */
    public static Map<String, ?> getAll() {
        return SHARED_PREFERENCE.getAll();
    }

}
