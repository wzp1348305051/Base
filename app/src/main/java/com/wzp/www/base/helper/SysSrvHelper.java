package com.wzp.www.base.helper;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 获取各种系统服务管理类
 *
 * @author wzp
 * @since 2016-01-26
 */
public class SysSrvHelper {

    private SysSrvHelper() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取窗口管理器
     *
     * @param context 应用程序上下文
     * @return The top-level window manager in which you can place custom windows. The
     * returned object is a android.view.WindowManager.
     */
    public static WindowManager getWindowManager(Context context) {
        if (context != null) {
            return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取xml布局解析器
     *
     * @param context 应用程序上下文
     * @return A android.view.LayoutInflater for inflating layout resources in this
     * context.
     */
    public static LayoutInflater getLayoutInflater(Context context) {
        if (context != null) {
            return (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取活动管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.ActivityManager for interacting with the global activity
     * state of the system.
     */
    public static ActivityManager getActivityManager(Context context) {
        if (context != null) {
            return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取电源服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.os.PowerManager for controlling power management.
     */
    public static PowerManager getPowerManger(Context context) {
        if (context != null) {
            return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取闹钟服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.AlarmManager for receiving intents at the time of your
     * choosing.
     */
    public static AlarmManager getAlarmManager(Context context) {
        if (context != null) {
            return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取状态栏服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.NotificationManager for informing the user of background
     * events.
     */
    public static NotificationManager getNotificationManager(Context context) {
        if (context != null) {
            return (NotificationManager) context.getSystemService(Context
                    .NOTIFICATION_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取键盘锁服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.KeyguardManager for controlling keyguard.
     */
    public static KeyguardManager getKeyguardManager(Context context) {
        if (context != null) {
            return (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取位置服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.location.LocationManager for controlling location (e.g., GPS)
     * updates.
     */
    public static LocationManager getLocationManager(Context context) {
        if (context != null) {
            return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取搜索服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.SearchManager for handling search.
     */
    public static SearchManager getSearchManager(Context context) {
        if (context != null) {
            return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取感应器服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.hardware.SensorManager for controlling sensors.
     */
    public static SensorManager getSensorManager(Context context) {
        if (context != null) {
            return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取存储服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.os.storage.StorageManager for controlling different storage.
     */
    public static StorageManager getStorageManager(Context context) {
        if (context != null) {
            return (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取震动服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.os.Vibrator for interacting with the vibrator hardware.
     */
    public static Vibrator getVibrator(Context context) {
        if (context != null) {
            return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取网络连接服务管理器
     *
     * @param context 应用程序上下文
     * @return A ConnectivityManager for handling management of network
     * connections.
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        if (context != null) {
            return (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取Wifi服务管理器
     *
     * @param context 应用程序上下文
     * @return A WifiManager for management of Wifi connectivity.
     */
    public static WifiManager getWifiManager(Context context) {
        if (context != null) {
            return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取音频服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.media.AudioManager for controlling audio services.
     */
    public static AudioManager getAudioManager(Context context) {
        if (context != null) {
            return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取媒体路由服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.media.MediaRouter for controlling media router.
     */
    public static MediaRouter getMediaRouter(Context context) {
        if (context != null) {
            return (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取电话服务管理器
     *
     * @param context 应用程序上下文
     * @return A android.telephony.TelephonyManager for controlling cell phone.
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        if (context != null) {
            return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取键盘管理器
     *
     * @param context 应用程序上下文
     * @return A android.view.inputmethod.InputMethodManager for controlling keyboard.
     */
    public static InputMethodManager getInputMethodManager(Context context) {
        if (context != null) {
            return (InputMethodManager) context.getSystemService(Context
                    .INPUT_METHOD_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取UI模式（夜间模式和行车模式）管理器
     *
     * @param context 应用程序上下文
     * @return A android.view.inputmethod.InputMethodManager for controlling keyboard.
     */
    public static UiModeManager getUiModeManager(Context context) {
        if (context != null) {
            return (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        } else {
            return null;
        }
    }

    /**
     * 获取下载管理器
     *
     * @param context 应用程序上下文
     * @return A android.app.DownloadManager for controlling download operation.
     */
    public static DownloadManager getDownloadManager(Context context) {
        if (context != null) {
            return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        } else {
            return null;
        }
    }

}
