package com.wzp.www.base.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.wzp.www.base.bean.constant.Global;

import java.io.InputStream;

/**
 * 资源获取工具类
 *
 * @author wzp
 * @since 2017-02-28
 */
public class ResourceHelper {
    private static final String TAG = ResourceHelper.class.getSimpleName();
    private static final Context CONTEXT = Global.APP_CONTEXT;
    private static final Resources RESOURCES = CONTEXT.getResources();

    private ResourceHelper() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用Resources对象
     *
     * @return 应用Resources对象
     */
    public static Resources getResources() {
        return RESOURCES;
    }

    /**
     * 根据资源名,资源类型,应用包名获取资源Id
     *
     * @param name        资源名
     * @param defType     资源类型
     * @param packageName 应用包名
     * @return 资源Id
     */
    public static int getResId(String name, String defType, String packageName) {
        return RESOURCES.getIdentifier(name, defType, packageName);
    }

    /**
     * 根据资源名,资源类型获取本地资源Id
     *
     * @param name    资源名
     * @param defType 资源类型
     * @return 资源Id
     */
    public static int getLocalResId(String name, String defType) {
        return getResId(name, defType, Global.APP_PACKAGE_NAME);
    }

    /**
     * 根据资源名,资源类型获取系统资源Id
     *
     * @param name    资源名
     * @param defType 资源类型
     * @return 资源Id
     */
    public static int getSystemResId(String name, String defType) {
        return getResId(name, defType, "android");
    }

    /**
     * 根据string资源Id获取对应string值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static String getString(int resId) {
        try {
            return RESOURCES.getString(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_STRING;
        }
    }

    /**
     * 根据string资源名获取对应string值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static String getString(String name) {
        return getString(getLocalResId(name, "string"));
    }

    /**
     * 根据string数组资源Id获取对应string数组值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static String[] getStringArray(int resId) {
        try {
            return RESOURCES.getStringArray(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_STRING_ARRAY;
        }
    }

    /**
     * 根据string数组资源名获取对应string数组值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static String[] getStringArray(String name) {
        return getStringArray(getLocalResId(name, "array"));
    }

    /**
     * 根据integer资源Id获取对应integer值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static int getInteger(int resId) {
        try {
            return RESOURCES.getInteger(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_INTEGER;
        }
    }

    /**
     * 根据integer资源名获取对应integer值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static int getInteger(String name) {
        return getInteger(getLocalResId(name, "integer"));
    }

    /**
     * 根据integer数组资源Id获取对应integer数组值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static int[] getIntegerArray(int resId) {
        try {
            return RESOURCES.getIntArray(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_INTEGER_ARRAY;
        }
    }

    /**
     * 根据integer数组资源名获取对应integer数组值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static int[] getIntegerArray(String name) {
        return getIntegerArray(getLocalResId(name, "array"));
    }

    /**
     * 根据boolean资源Id获取对应boolean值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static boolean getBoolean(int resId) {
        try {
            return RESOURCES.getBoolean(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_BOOLEAN;
        }
    }

    /**
     * 根据boolean资源名获取对应boolean值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static boolean getBoolean(String name) {
        return getBoolean(getLocalResId(name, "bool"));
    }

    /**
     * 根据drawable资源Id获取对应drawable值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static Drawable getDrawable(int resId) {
        try {
            return ContextCompat.getDrawable(CONTEXT, resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 根据drawable资源名获取对应drawable值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static Drawable getDrawable(String name) {
        return getDrawable(getLocalResId(name, "drawable"));
    }

    /**
     * 根据color资源Id获取对应color值
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static int getColor(int resId) {
        try {
            return ContextCompat.getColor(CONTEXT, resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_INTEGER;
        }
    }

    /**
     * 根据color资源名获取对应color值
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static int getColor(String name) {
        return getColor(getLocalResId(name, "color"));
    }

    /**
     * 根据xml资源Id获取对应xml文件
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static XmlResourceParser getXml(int resId) {
        try {
            return RESOURCES.getXml(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 根据xml资源名获取对应xml文件
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static XmlResourceParser getXml(String name) {
        return getXml(getLocalResId(name, "xml"));
    }

    /**
     * 根据dimen资源Id获取对应dimen值（绝对尺寸）
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static float getDimension(int resId) {
        try {
            return RESOURCES.getDimension(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_FLOAT;
        }
    }

    /**
     * 根据dimen资源名获取对应dimen值（绝对尺寸）
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static float getDimension(String name) {
        return getDimension(getLocalResId(name, "dimen"));
    }

    /**
     * 根据dimen资源Id获取对应dimen值（绝对尺寸,截断getDimension的小数位）
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static int getDimensionPixelOffset(int resId) {
        try {
            return RESOURCES.getDimensionPixelOffset(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_INTEGER;
        }
    }

    /**
     * 根据dimen资源名获取对应dimen值（绝对尺寸,截断getDimension的小数位）
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static int getDimensionPixelOffset(String name) {
        return getDimensionPixelOffset(getLocalResId(name, "dimen"));
    }

    /**
     * 根据dimen资源Id获取对应dimen值（绝对尺寸）
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static int getDimensionPixelSize(int resId) {
        try {
            return RESOURCES.getDimensionPixelSize(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return Global.DEFAULT_INTEGER;
        }
    }

    /**
     * 根据dimen资源名获取对应dimen值（绝对尺寸）
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static int getDimensionPixelSize(String name) {
        return getDimensionPixelSize(getLocalResId(name, "dimen"));
    }

    /**
     * 获取AssetManager读取asserts文件夹下资源（可以有目录结构,不会被映射到R文件中）
     */
    public static AssetManager getAssets() {
        return RESOURCES.getAssets();
    }

    /**
     * 根据raw资源Id获取对应文件输入流
     *
     * @param resId 资源Id
     * @return 资源Id对应的资源值
     */
    public static InputStream openRawResource(int resId) {
        try {
            return RESOURCES.openRawResource(resId);
        } catch (NotFoundException e) {
            L.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 根据raw资源名获取对应文件输入流
     *
     * @param name 资源名
     * @return 资源名对应的资源值
     */
    public static InputStream openRawResource(String name) {
        return openRawResource(getLocalResId(name, "raw"));
    }

}
