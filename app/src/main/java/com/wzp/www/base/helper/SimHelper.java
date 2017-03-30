package com.wzp.www.base.helper;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.wzp.www.base.bean.constant.Global;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sim卡工具类
 */

public class SimHelper {
    private static final String TAG = SimHelper.class.getSimpleName();
    private static SimHelper sInstance;
    private static Context sContext;
    private TelephonyManager sTelephonyManager;

    public enum CARRIER_TYPE {
        CHINA_MOBILE, CHINA_UNICOM, CHINA_TELCOM, OTHER
    }

    private SimHelper() {
        sContext = Global.APP_CONTEXT;
        sTelephonyManager = SystemServiceHelper.getTelephonyManager(sContext);
    }

    public static SimHelper getInstance() {
        if (sInstance == null) {
            synchronized (SimHelper.class) {
                if (sInstance == null) {
                    sInstance = new SimHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取本地手机号码
     */
    public String getLocalCallNumber() {
        try {
            if (sTelephonyManager != null) {
                String phoneNumber = sTelephonyManager.getLine1Number();
                if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() >= 11) {
                    phoneNumber = phoneNumber.substring(phoneNumber.length() - 11);
                    if (isPhoneNumber(phoneNumber)) {
                        return phoneNumber;
                    }
                }
            }
        } catch (Exception e) {
            L.e(TAG, e.getMessage());
        }
        return "";
    }

    /**
     * 获取Sim卡号
     */
    public String getSimCardNumber() {
        if (sTelephonyManager != null) {
            return sTelephonyManager.getSubscriberId();
        } else {
            return "";
        }
    }

    /**
     * 获取运营商类型
     */
    public CARRIER_TYPE getCarrierType() {
        if (isChinaMobile()) {
            return CARRIER_TYPE.CHINA_MOBILE;
        } else if (isChinaUnicom()) {
            return CARRIER_TYPE.CHINA_UNICOM;
        } else if (isChinaTelcom()) {
            return CARRIER_TYPE.CHINA_TELCOM;
        } else {
            return CARRIER_TYPE.OTHER;
        }
    }

    public boolean isChinaMobile() {
        if (sTelephonyManager != null) {
            String operator = sTelephonyManager.getSimOperator();
            if (operator != null && (operator.equalsIgnoreCase("46000") || operator
                    .equalsIgnoreCase("46002") || operator.equalsIgnoreCase("46007"))) {
                return true;
            }
            if (isChinaMobilePhoneNumber(getLocalCallNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChinaUnicom() {
        if (sTelephonyManager != null) {
            String operator = sTelephonyManager.getSimOperator();
            if (operator != null && (operator.equalsIgnoreCase("46001") || operator
                    .equalsIgnoreCase("46006") || operator.equalsIgnoreCase("46009"))) {
                return true;
            }
            if (isChinaUnicomPhoneNumber(getLocalCallNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChinaTelcom() {
        if (sTelephonyManager != null) {
            String operator = sTelephonyManager.getSimOperator();
            if (operator != null && (operator.equalsIgnoreCase("46003") || operator
                    .equalsIgnoreCase("46005") || operator.equalsIgnoreCase("46011"))) {
                return true;
            }
            if (isChinaTelcomPhoneNumber(getLocalCallNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChinaMobilePhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        Pattern p = Pattern.compile("^1(" +
                "(35|36|37|38|39|47|50|51|52|57|58|59|78|82|83|84|87|88)" +
                "[0-9]|705|34[0-8])[0-9]{7}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public boolean isChinaUnicomPhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        Pattern p = Pattern.compile("^1((30|31|32|45|55|56|76|85|86)[0-9]|709)[0-9]{7}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public boolean isChinaTelcomPhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        Pattern p = Pattern.compile("^1((33|53|73|77|80|81|89)[0-9]|349|700)[0-9]{7}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public boolean isPhoneNumber(String number) {
        if (isChinaMobilePhoneNumber(number) || isChinaUnicomPhoneNumber(number) ||
                isChinaTelcomPhoneNumber(number)) {
            return true;
        } else {
            return false;
        }
    }

}
