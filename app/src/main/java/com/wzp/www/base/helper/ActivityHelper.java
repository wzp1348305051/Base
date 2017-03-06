package com.wzp.www.base.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.wzp.www.base.bean.constant.ActivityRequestCode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityHelper {
    private static final String TAG = ActivityHelper.class.getSimpleName();
    public static List<Activity> mActivities = new ArrayList<>();

    /**
     * 添加当前活动至活动栈
     */
    public static void add(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 从活动栈中移除当前活动
     */
    public static void remove(Activity activity) {
        mActivities.remove(activity);
    }

    /**
     * 清空当前活动栈中所有活动
     */
    public static void finishAll() {
        for (Activity activity : mActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 设置Activity没有标题
     */
    public static void setNoTitle(Activity activity) {
        if (activity != null) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 程序推到后台(即返回主页)
     */
    public static void backToDesk(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            activity.startActivity(intent);
        }
    }

    /**
     * 程序从后台推到前台
     *
     * @param activity 包上下文
     * @param clazz    程序启动类
     */
    public static void moveToFront(Activity activity, Class<?> clazz) {
        if (activity != null && clazz != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClass(activity, clazz);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                        .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                activity.startActivity(intent);
            } catch (Exception e) {
                L.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 隐藏输入法(解决首次进入包含输入框界面时软键盘自动弹出问题,以及软键盘部分遮挡文本框问题)
     */
    public static void setInputMethodHidden(Activity activity) {
        if (activity != null) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams
                    .SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams
                    .SOFT_INPUT_STATE_HIDDEN);
        }
    }

    /**
     * 调用系统功能拨打电话
     */
    public static void callPhone(Activity activity, String phone) {
        if (activity != null && !TextUtils.isEmpty(phone)) {
            try {
                Uri uri = Uri.parse("tel:" + phone);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            } catch (SecurityException e) {
                L.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 调用系统拨号界面
     */
    public static void openSysTel(Activity activity, String phone) {
        if (activity != null && !TextUtils.isEmpty(phone)) {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    /**
     * 调用系统短信界面
     *
     * @param activity  应用上下文
     * @param receivers 短信发送号码
     * @param content   短信内容
     */
    public static void openSysSms(Activity activity, String[] receivers, String content) {
        if (activity != null) {
            Uri smsUri = Uri.parse("smsto:");
            Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
            if (receivers != null) {
                if (receivers.length == 1) {
                    intent.putExtra("address", receivers[0]);
                } else {
                    String separator = ";";
                    if (Build.MODEL != null && Build.MODEL.toUpperCase(Locale
                            .getDefault()).contains("SCH-I939")) {
                        separator = ",";
                    }
                    StringBuffer target = new StringBuffer();
                    for (int i = 0; i < receivers.length; i++) {
                        target.append(receivers[i]).append(separator);
                    }
                    intent.putExtra("address", target.toString());
                }
            }
            if (!TextUtils.isEmpty(content)) {
                intent.putExtra("sms_body", content);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("vnd.android-dir/mms-sms");
            activity.startActivity(intent);
        }
    }

    /**
     * 调用系统相册
     */
    public static void openSysAlbum(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            activity.startActivityForResult(intent, ActivityRequestCode
                    .PICK_PHOTO_FROM_SYSTEM_ALBUM);
        }
    }

    /**
     * 调用系统相机
     *
     * @param activity 活动
     * @param save     照片本地保存路径,自定义照片存储路径,解决拍照图片被压缩问题
     */
    public static void openSysCamera(Activity activity, File save) {
        if (activity != null && save != null) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(save));
            activity.startActivityForResult(intent, ActivityRequestCode
                    .TAKE_PHOTO_FROM_SYSTEM_CAMERA);
        }
    }

    /**
     * 调用系统图片裁剪
     *
     * @param activity 活动
     * @param uri      照片Uri(调用重置照片路径的系统相机：Uri.fromFile(new File(照片路径))或调用系统相册：data
     *                 .getData())
     * @param width    裁剪后图片宽
     * @param height   裁剪后图片高
     */
    public static void openSysZoom(Activity activity, Uri uri, int width, int height) {
        if (activity != null && uri != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            /** 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪 */
            intent.putExtra("crop", "true");
            /** aspectX aspectY 是宽高的比例 */
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            /** outputX outputY 是裁剪图片宽高 */
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, ActivityRequestCode
                    .CROP_PHOTO_FROM_SYSTEM_ALBUM);
        }
    }

    /**
     * 打开URL下的视频文件
     *
     * @param activity 上下文
     * @param url      视频文件URL
     */
    public static void openSysVideo(Activity activity, String url) {
        if (activity != null && !TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("video/*");
            intent.setDataAndType(uri, "video/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    /**
     * 添加快捷方式
     *
     * @param activity     上下文环境
     * @param target       添加快捷方式的目标类
     * @param shortcutIcon 图标资源
     * @param shortcutName 图标名称
     */
    public static void addShortcut(Activity activity, Class<?> target, int
            shortcutIcon, String shortcutName) {
        if (activity != null && target != null && shortcutIcon != 0 && !TextUtils
                .isEmpty(shortcutName)) {
            /** 与应用绑定,当应用被删除后快捷方式也会被删除 */
            Intent intentBind = new Intent();
            intentBind.setClass(activity, target);
            intentBind.setAction(Intent.ACTION_MAIN);
            intentBind.addCategory(Intent.CATEGORY_LAUNCHER);
            Intent intentAdd = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(activity,
                    shortcutIcon);
            intentAdd.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
            intentAdd.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intentBind);
            intentAdd.putExtra("duplicate", false);
            intentAdd.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            activity.sendBroadcast(intentAdd);
        }
    }

    /**
     * 判断当前手机是否处于静音状态
     */
    public static boolean isSilentMode(Context context) {
        if (context != null) {
            int ringerMode = SystemServiceHelper.getAudioManager(context)
                    .getRingerMode();
            return ((AudioManager.RINGER_MODE_SILENT == ringerMode) || (AudioManager
                    .RINGER_MODE_VIBRATE == ringerMode));
        } else {
            return false;
        }
    }

    /**
     * 安装apk
     *
     * @param activity
     * @param path     安装包本地路径
     */
    public static void installApk(Activity activity, String path) {
        if (activity != null && new File(path).exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android" +
                    ".package-archive");
            activity.startActivity(intent);
        }
    }

}
