package com.umeng.commonsdk.statistics.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import androidx.webkit.ProxyConfig;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.facebook.internal.AnalyticsEvents;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.analytics.pro.aa;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.idtracking.h;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.d;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes4.dex */
public class DeviceConfig {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    protected static final String LOG_TAG = "com.umeng.commonsdk.statistics.common.DeviceConfig";
    public static final String MOBILE_NETWORK = "2G/3G";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";
    private static DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.DEFAULT;
    private static volatile String sWifiMac = "";
    private static volatile String sImei = "";
    private static volatile String sMeid = "";
    private static volatile String sImsi = "";
    private static volatile String sSerialNo = "";
    private static volatile String sAndroidID = "";
    private static volatile String sIDFA = "";
    private static volatile String sOAID = "";
    private static volatile String sHonorOAID = "";
    private static volatile String sSecondImei = "";
    private static volatile String sSimSerialNumber = "";
    private static volatile boolean hasReadImeiOrMeid = false;
    private static volatile boolean hasReadSimSerialNumber = false;
    private static volatile boolean hasReadIMEI2 = false;
    private static volatile boolean hasReadSerialNo = false;
    private static volatile boolean hasReadAndroidID = false;
    private static volatile boolean hasReadMac = false;
    private static volatile boolean hasReadImsi = false;
    private static volatile boolean hasReadOAID = false;
    private static volatile boolean hasReadHonorOAID = false;
    private static volatile boolean hasReadIDFA = false;
    private static volatile String sAppName = "";
    private static volatile String sAppPkgName = "";
    private static volatile boolean hasReadOperator = false;
    private static volatile String sOperator = "";
    private static volatile boolean hasReadOperatorName = false;
    private static volatile String sOperatorName = "";
    private static volatile String sCustomAgt = "";

    private static String byte2HexFormatted(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            String hexString = Integer.toHexString(bArr[i2]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase(Locale.getDefault()));
            if (i2 < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String getAndroidId(Context context) {
        if (!UMConfigure.shouldCollectAid()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read aid.");
            return null;
        }
        if (!TextUtils.isEmpty(sAndroidID)) {
            return sAndroidID;
        }
        if (hasReadAndroidID) {
            return null;
        }
        if (FieldManager.allow(d.f22565i) && context != null) {
            try {
                try {
                    sAndroidID = Settings.Secure.getString(context.getContentResolver(), "android_id");
                } catch (Throwable unused) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.w("can't read android id");
                    }
                }
            } finally {
                hasReadAndroidID = true;
            }
        }
        return sAndroidID;
    }

    public static String getAppHashKey(Context context) {
        try {
            PackageInfo packageInfoA = com.umeng.commonsdk.utils.b.a().a(context, getPackageName(context), 64);
            if (packageInfoA == null) {
                return null;
            }
            Signature[] signatureArr = packageInfoA.signatures;
            if (signatureArr.length <= 0) {
                return null;
            }
            Signature signature = signatureArr[0];
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(signature.toByteArray());
            return Base64.encodeToString(messageDigest.digest(), 0).trim();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppMD5Signature(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageInfo packageInfoA = com.umeng.commonsdk.utils.b.a().a(context, getPackageName(context), 64);
            if (packageInfoA == null) {
                return null;
            }
            return byte2HexFormatted(MessageDigest.getInstance(Utils.MD5).digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(packageInfoA.signatures[0].toByteArray()))).getEncoded()));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppName(Context context) {
        if (!TextUtils.isEmpty(sAppName)) {
            return sAppName;
        }
        if (context == null) {
            return null;
        }
        try {
            PackageInfo packageInfoA = com.umeng.commonsdk.utils.b.a().a(context, context.getPackageName(), 64);
            if (packageInfoA != null) {
                sAppName = packageInfoA.applicationInfo.loadLabel(context.getPackageManager()).toString();
            }
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, th);
            }
        }
        return sAppName;
    }

    public static String getAppSHA1Key(Context context) {
        try {
            PackageInfo packageInfoA = com.umeng.commonsdk.utils.b.a().a(context, getPackageName(context), 64);
            if (packageInfoA == null) {
                return null;
            }
            return byte2HexFormatted(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(packageInfoA.signatures[0].toByteArray()))).getEncoded()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getAppVersionCode(Context context) {
        return UMUtils.getAppVersionCode(context);
    }

    public static String getAppVersionName(Context context) {
        return UMUtils.getAppVersionName(context);
    }

    public static String getApplicationLable(Context context) {
        return context == null ? "" : context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    private static Properties getBuildProp() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            } catch (Throwable unused) {
            }
            try {
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (Throwable unused2) {
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                return properties;
            }
        } catch (Throwable unused3) {
        }
        return properties;
    }

    public static String getCPU() {
        String line = null;
        try {
            FileReader fileReader = new FileReader("/proc/cpuinfo");
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
                line = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();
            } catch (Throwable th) {
                MLog.e(LOG_TAG, "Could not read from file /proc/cpuinfo", th);
            }
        } catch (FileNotFoundException e2) {
            MLog.e(LOG_TAG, "Could not open file /proc/cpuinfo", e2);
        }
        return line != null ? line.substring(line.indexOf(58) + 1).trim() : "";
    }

    public static String getCustomAgt() {
        if (!TextUtils.isEmpty(sCustomAgt)) {
            return sCustomAgt;
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append("Dalvik/");
        sb.append(System.getProperty("java.vm.version"));
        sb.append(" (Linux; U; Android ");
        sb.append(")");
        sCustomAgt = sb.toString();
        return sCustomAgt;
    }

    public static String getDBencryptID(Context context) {
        return UMUtils.genId();
    }

    public static String getDeviceId(Context context) {
        return AnalyticsConstants.getDeviceType() == 2 ? getDeviceIdForBox(context) : getDeviceIdForGeneral(context);
    }

    public static String getDeviceIdForBox(Context context) {
        String androidId = "";
        if (context == null) {
            return "";
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 == 23) {
                if (FieldManager.allow(d.f22565i)) {
                    androidId = getAndroidId(context);
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + androidId);
                    }
                }
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String macByJavaAPI = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum2 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum2;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = deviceTypeEnum2;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = deviceTypeEnum2;
                    }
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo)) {
                    return serialNo;
                }
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                return imei;
            }
            if (i2 >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId2 = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId2)) {
                    return androidId2;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo2)) {
                    return serialNo2;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum3 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum3;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = deviceTypeEnum3;
                return macBySystemInterface;
            }
            if (FieldManager.allow(d.f22565i)) {
                androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId: ANDROID_ID: " + androidId);
                }
            }
            if (!TextUtils.isEmpty(androidId)) {
                return androidId;
            }
            String serialNo3 = getSerialNo();
            deviceTypeEnum = DeviceTypeEnum.SERIALNO;
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            String imei2 = getIMEI(context);
            deviceTypeEnum = DeviceTypeEnum.IMEI;
            if (!TextUtils.isEmpty(imei2)) {
                return imei2;
            }
            String macByJavaAPI3 = getMacByJavaAPI();
            DeviceTypeEnum deviceTypeEnum4 = DeviceTypeEnum.MAC;
            deviceTypeEnum = deviceTypeEnum4;
            if (!TextUtils.isEmpty(macByJavaAPI3)) {
                return macByJavaAPI3;
            }
            String macBySystemInterface2 = getMacBySystemInterface(context);
            deviceTypeEnum = deviceTypeEnum4;
            if (!AnalyticsConstants.UM_DEBUG) {
                return macBySystemInterface2;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + macBySystemInterface2);
            return macBySystemInterface2;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDeviceIdForGeneral(Context context) {
        if (context == null) {
            return "";
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 == 23) {
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei)) {
                    return imei;
                }
                String macByJavaAPI = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum2 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum2;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = deviceTypeEnum2;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = deviceTypeEnum2;
                    }
                }
                boolean z2 = AnalyticsConstants.UM_DEBUG;
                if (z2) {
                    MLog.i(LOG_TAG, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                if (FieldManager.allow(d.f22565i)) {
                    macByJavaAPI = getAndroidId(context);
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (z2) {
                        MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + macByJavaAPI);
                    }
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                return serialNo;
            }
            if (i2 >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo2)) {
                    return serialNo2;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                DeviceTypeEnum deviceTypeEnum3 = DeviceTypeEnum.MAC;
                deviceTypeEnum = deviceTypeEnum3;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = deviceTypeEnum3;
                return macBySystemInterface;
            }
            String imei2 = getIMEI(context);
            deviceTypeEnum = DeviceTypeEnum.IMEI;
            if (!TextUtils.isEmpty(imei2)) {
                return imei2;
            }
            String serialNo3 = getSerialNo();
            deviceTypeEnum = DeviceTypeEnum.SERIALNO;
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            if (FieldManager.allow(d.f22565i)) {
                serialNo3 = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + serialNo3);
                }
            }
            if (!TextUtils.isEmpty(serialNo3)) {
                return serialNo3;
            }
            String macByJavaAPI3 = getMacByJavaAPI();
            DeviceTypeEnum deviceTypeEnum4 = DeviceTypeEnum.MAC;
            deviceTypeEnum = deviceTypeEnum4;
            if (!TextUtils.isEmpty(macByJavaAPI3)) {
                return macByJavaAPI3;
            }
            String macBySystemInterface2 = getMacBySystemInterface(context);
            deviceTypeEnum = deviceTypeEnum4;
            if (!AnalyticsConstants.UM_DEBUG) {
                return macBySystemInterface2;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + macBySystemInterface2);
            return macBySystemInterface2;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDeviceIdType() {
        return deviceTypeEnum.getDeviceIdType();
    }

    public static String getDeviceIdUmengMD5(Context context) {
        return HelperUtils.getUmengMD5(getDeviceId(context));
    }

    public static String getDeviceType(Context context) {
        if (context == null) {
            return "Phone";
        }
        try {
            return (context.getResources().getConfiguration().screenLayout & 15) >= 3 ? "Tablet" : "Phone";
        } catch (Throwable unused) {
            return "Phone";
        }
    }

    public static String getDisplayResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                int i2 = displayMetrics.widthPixels;
                return String.valueOf(displayMetrics.heightPixels) + ProxyConfig.MATCH_ALL_SCHEMES + String.valueOf(i2);
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    private static String getEmuiVersion(Properties properties) {
        try {
            return properties.getProperty(KEY_EMUI_VERSION_CODE, null);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getFlymeVersion(Properties properties) {
        try {
            String lowerCase = properties.getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
            if (lowerCase.contains("flyme os")) {
                return lowerCase.split(" ")[2];
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String[] getGPU(GL10 gl10) {
        try {
            return new String[]{gl10.glGetString(7936), gl10.glGetString(7937)};
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.e(LOG_TAG, "Could not read gpu infor:", th);
            }
            return new String[0];
        }
    }

    public static Activity getGlobleActivity(Context context) {
        Activity activity = null;
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(null, null);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            for (Object obj : ((Map) declaredField.get(objInvoke)).values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField(PushConstants.INTENT_ACTIVITY_NAME);
                    declaredField3.setAccessible(true);
                    activity = (Activity) declaredField3.get(obj);
                }
            }
        } catch (Throwable unused) {
        }
        return activity;
    }

    public static String getHonorOaid(Context context) {
        if (!TextUtils.isEmpty(sHonorOAID)) {
            return sHonorOAID;
        }
        if (hasReadHonorOAID) {
            return "";
        }
        if (FieldManager.allow(d.G)) {
            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences(com.umeng.commonsdk.statistics.idtracking.c.f22382a, 0);
                if (sharedPreferences != null) {
                    sHonorOAID = sharedPreferences.getString(com.umeng.commonsdk.statistics.idtracking.c.f22383b, "");
                }
            } catch (Throwable unused) {
            }
            hasReadHonorOAID = true;
        }
        return sHonorOAID;
    }

    private static String getIMEI(Context context) {
        String deviceId = "";
        if (!UMConfigure.shouldCollectImei()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read IMEI.");
            return "";
        }
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        if (hasReadImeiOrMeid) {
            return "";
        }
        if (FieldManager.allow(d.f22563g)) {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                try {
                    if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                        deviceId = telephonyManager.getDeviceId();
                        if (AnalyticsConstants.UM_DEBUG) {
                            MLog.i(LOG_TAG, "getDeviceId, IMEI: " + deviceId);
                        }
                    }
                } finally {
                    try {
                    } finally {
                    }
                }
            }
        }
        sImei = deviceId;
        return sImei;
    }

    public static String getIdfa(Context context) {
        return "";
    }

    public static String getImei(Context context) {
        TelephonyManager telephonyManager;
        String deviceId = null;
        if (!UMConfigure.shouldCollectImei()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read IMEI.");
            return null;
        }
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        if (hasReadImeiOrMeid) {
            return null;
        }
        try {
            if (FieldManager.allow(d.f22563g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                deviceId = telephonyManager.getDeviceId();
            }
        } finally {
            try {
                sImei = deviceId;
                return deviceId;
            } finally {
            }
        }
        sImei = deviceId;
        return deviceId;
    }

    public static String getImeiNew(Context context) {
        TelephonyManager telephonyManager;
        String deviceId = null;
        if (!UMConfigure.shouldCollectImei()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read IMEI.");
            return null;
        }
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        if (hasReadImeiOrMeid) {
            return null;
        }
        try {
            if (FieldManager.allow(d.f22563g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                if (Build.VERSION.SDK_INT >= 26) {
                    try {
                        Method method = telephonyManager.getClass().getMethod("getImei", null);
                        method.setAccessible(true);
                        deviceId = (String) method.invoke(telephonyManager, null);
                    } catch (Throwable unused) {
                    }
                    if (TextUtils.isEmpty(deviceId)) {
                        deviceId = telephonyManager.getDeviceId();
                    }
                } else {
                    deviceId = telephonyManager.getDeviceId();
                }
            }
        } finally {
            try {
                sImei = deviceId;
                return deviceId;
            } finally {
            }
        }
        sImei = deviceId;
        return deviceId;
    }

    public static String getImsi(Context context) {
        String subscriberId = null;
        if (!UMConfigure.shouldCollectImsi()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read IMSI.");
            return null;
        }
        if (!TextUtils.isEmpty(sImsi)) {
            return sImsi;
        }
        if (hasReadImsi || context == null) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (FieldManager.allow(d.al)) {
            try {
                if (checkPermission(context, "android.permission.READ_PHONE_STATE") && telephonyManager != null) {
                    subscriberId = telephonyManager.getSubscriberId();
                }
            } catch (Throwable unused) {
            }
            hasReadImsi = true;
        }
        sImsi = subscriberId;
        return sImsi;
    }

    private static Locale getLocale(Context context) {
        Locale locale;
        if (context == null) {
            return Locale.getDefault();
        }
        try {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            Settings.System.getConfiguration(context.getContentResolver(), configuration);
            locale = configuration.locale;
        } catch (Throwable unused) {
            MLog.e(LOG_TAG, "fail to read user config locale");
            locale = null;
        }
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String[] getLocaleInfo(Context context) {
        String[] strArr = {AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN};
        if (context == null) {
            return strArr;
        }
        try {
            Locale locale = getLocale(context);
            if (locale != null) {
                strArr[0] = locale.getCountry();
                strArr[1] = locale.getLanguage();
            }
            if (TextUtils.isEmpty(strArr[0])) {
                strArr[0] = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            if (TextUtils.isEmpty(strArr[1])) {
                strArr[1] = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            return strArr;
        } catch (Throwable th) {
            MLog.e(LOG_TAG, "error in getLocaleInfo", th);
            return strArr;
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static String getMCCMNC(Context context) {
        if (context == null) {
            return null;
        }
        if (getImsi(context) == null) {
            return null;
        }
        int i2 = context.getResources().getConfiguration().mcc;
        int i3 = context.getResources().getConfiguration().mnc;
        if (i2 != 0) {
            String strValueOf = String.valueOf(i3);
            if (i3 < 10) {
                strValueOf = String.format("%02d", Integer.valueOf(i3));
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.valueOf(i2));
            stringBuffer.append(strValueOf);
            return stringBuffer.toString();
        }
        return null;
    }

    public static String getMac(Context context) {
        String macBySystemInterface;
        if (!UMConfigure.shouldCollectWifiMac()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read WiFi Mac.");
            return "";
        }
        if (!FieldManager.allow(d.f22564h) || context == null) {
            return "";
        }
        try {
            if (Build.VERSION.SDK_INT == 23) {
                String macByJavaAPI = getMacByJavaAPI();
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                macBySystemInterface = AnalyticsConstants.CHECK_DEVICE ? getMacShell() : getMacBySystemInterface(context);
            } else {
                String macByJavaAPI2 = getMacByJavaAPI();
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                macBySystemInterface = getMacBySystemInterface(context);
            }
            return macBySystemInterface;
        } catch (Throwable unused) {
            return "";
        }
    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        if (!TextUtils.isEmpty(sWifiMac)) {
            return sWifiMac;
        }
        if (hasReadMac) {
            return "";
        }
        try {
            if (FieldManager.allow(d.f22564h)) {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if ("wlan0".equals(networkInterfaceNextElement.getName()) || "eth0".equals(networkInterfaceNextElement.getName())) {
                        byte[] hardwareAddress = networkInterfaceNextElement.getHardwareAddress();
                        if (hardwareAddress == null || hardwareAddress.length == 0) {
                            sWifiMac = "";
                        }
                        StringBuilder sb = new StringBuilder();
                        for (byte b2 : hardwareAddress) {
                            sb.append(String.format("%02X:", Byte.valueOf(b2)));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        sWifiMac = sb.toString().toLowerCase(Locale.getDefault());
                    }
                }
            }
        } catch (Throwable unused) {
        }
        hasReadMac = true;
        return sWifiMac;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static String getMacBySystemInterface(Context context) {
        if (!TextUtils.isEmpty(sWifiMac)) {
            return sWifiMac;
        }
        if (hasReadMac || context == null) {
            return "";
        }
        try {
            if (FieldManager.allow(d.f22564h)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (!checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
                    }
                    sWifiMac = "";
                } else if (wifiManager != null) {
                    sWifiMac = wifiManager.getConnectionInfo().getMacAddress();
                } else {
                    sWifiMac = "";
                }
            }
        } finally {
            try {
                return sWifiMac;
            } finally {
            }
        }
        return sWifiMac;
    }

    private static String getMacShell() {
        if (!TextUtils.isEmpty(sWifiMac)) {
            return sWifiMac;
        }
        if (hasReadMac) {
            return "";
        }
        try {
            if (FieldManager.allow(d.f22564h)) {
                String[] strArr = {"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
                for (int i2 = 0; i2 < 3; i2++) {
                    try {
                        String strReaMac = reaMac(strArr[i2]);
                        if (strReaMac != null) {
                            sWifiMac = strReaMac;
                        }
                    } catch (Throwable th) {
                        if (AnalyticsConstants.UM_DEBUG) {
                            MLog.e(LOG_TAG, "open file  Failed", th);
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        hasReadMac = true;
        return sWifiMac;
    }

    public static String getMeid(Context context) {
        String strMeid = null;
        if (context == null || ((TelephonyManager) context.getSystemService("phone")) == null) {
            return null;
        }
        if (FieldManager.allow(d.am)) {
            try {
                if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                    if (Build.VERSION.SDK_INT < 26) {
                        strMeid = getIMEI(context);
                    } else {
                        strMeid = meid(context);
                        if (TextUtils.isEmpty(strMeid)) {
                            strMeid = getIMEI(context);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return strMeid;
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = {"", ""};
        if (context == null) {
            return strArr;
        }
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            strArr[0] = "";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "";
            return strArr;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "Wi-Fi";
            return strArr;
        }
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if (networkInfo2 != null && networkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "2G/3G";
            strArr[1] = networkInfo2.getSubtypeName();
        }
        return strArr;
    }

    public static String getNetworkOperatorName(Context context) {
        if (!TextUtils.isEmpty(sOperatorName)) {
            return sOperatorName;
        }
        if (hasReadOperatorName || context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (checkPermission(context, "android.permission.READ_PHONE_STATE") && telephonyManager != null) {
                sOperatorName = telephonyManager.getNetworkOperatorName();
            }
        } catch (Throwable unused) {
        }
        hasReadOperatorName = true;
        return sOperatorName;
    }

    public static int getNetworkType(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                return telephonyManager.getNetworkType();
            }
            return 0;
        } catch (Exception unused) {
            return -100;
        }
    }

    public static String getNotificationStatus(Context context) {
        boolean z2 = true;
        if (!FieldManager.allow(d.J)) {
            return "unknown";
        }
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                return Boolean.toString(((NotificationManager) context.getSystemService("notification")).areNotificationsEnabled());
            } catch (Throwable unused) {
                return "unknown";
            }
        }
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String packageName = context.getApplicationContext().getPackageName();
            int i2 = applicationInfo.uid;
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            Class<?> cls2 = Integer.TYPE;
            Method method = cls.getMethod("checkOpNoThrow", cls2, cls2, String.class);
            Integer num = (Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(appOpsManager);
            num.intValue();
            if (((Integer) method.invoke(appOpsManager, num, Integer.valueOf(i2), packageName)).intValue() != 0) {
                z2 = false;
            }
            return Boolean.toString(z2);
        } catch (Throwable unused2) {
            return "unknown";
        }
    }

    public static String getOaid(Context context) {
        if (!TextUtils.isEmpty(sOAID)) {
            return sOAID;
        }
        if (hasReadOAID) {
            return "";
        }
        if (FieldManager.allow(d.G)) {
            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences(h.f22404a, 0);
                if (sharedPreferences != null) {
                    sOAID = sharedPreferences.getString(h.f22405b, "");
                }
            } catch (Throwable unused) {
            }
            hasReadOAID = true;
        }
        return sOAID;
    }

    public static String getPackageName(Context context) {
        if (!TextUtils.isEmpty(sAppPkgName)) {
            return sAppPkgName;
        }
        if (context == null) {
            return null;
        }
        sAppPkgName = context.getPackageName();
        return sAppPkgName;
    }

    public static int getRandNumber(int i2, int i3) {
        return new Random().nextInt((i3 - i2) + 1) + i2;
    }

    public static String getRegisteredOperator(Context context) {
        if (!TextUtils.isEmpty(sOperator)) {
            return sOperator;
        }
        if (hasReadOperator || context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (checkPermission(context, "android.permission.READ_PHONE_STATE") && telephonyManager != null) {
                sOperator = telephonyManager.getNetworkOperator();
            }
        } catch (Throwable unused) {
        }
        hasReadOperator = true;
        return sOperator;
    }

    public static int[] getResolutionArray(Context context) {
        if (context == null) {
            return null;
        }
        int[] iArr = new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            try {
                Method method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class);
                if (method != null) {
                    method.invoke(defaultDisplay, displayMetrics);
                    int i2 = displayMetrics.widthPixels;
                    int i3 = displayMetrics.heightPixels;
                    if (i2 > i3) {
                        iArr[0] = i3;
                        iArr[1] = i2;
                    } else {
                        iArr[0] = i2;
                        iArr[1] = i3;
                    }
                    iArr[0] = i2;
                    iArr[1] = i3;
                    return iArr;
                }
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static int getRingerMode(Context context) {
        if (!FieldManager.allow(d.K)) {
            return -1;
        }
        try {
            return ((AudioManager) context.getSystemService("audio")).getRingerMode();
        } catch (Throwable unused) {
            return -1;
        }
    }

    public static String getSecondSimIMEi(Context context) {
        return null;
    }

    public static String getSerial() {
        return getSerialNo();
    }

    @TargetApi(9)
    private static String getSerialNo() {
        return "";
    }

    public static String getSid(Context context) {
        return aa.a().d(context);
    }

    public static String getSimICCID(Context context) {
        if (!UMConfigure.shouldCollectIccid()) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "disallow read ICCID.");
            return null;
        }
        if (!TextUtils.isEmpty(sSimSerialNumber)) {
            return sSimSerialNumber;
        }
        if (hasReadSimSerialNumber) {
            return null;
        }
        if (FieldManager.allow(d.ap)) {
            if (context != null) {
                try {
                    if (UMUtils.checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                        if (telephonyManager == null) {
                            hasReadSimSerialNumber = true;
                            return null;
                        }
                        sSimSerialNumber = telephonyManager.getSimSerialNumber();
                    }
                } catch (Throwable unused) {
                }
            }
            hasReadSimSerialNumber = true;
        }
        return sSimSerialNumber;
    }

    public static String getSubOSName(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            return TextUtils.isEmpty(property) ? isFlyMe() ? "Flyme" : isEmui(buildProp) ? "Emui" : !TextUtils.isEmpty(getYunOSVersion(buildProp)) ? "YunOS" : property : "MIUI";
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getSubOSVersion(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            try {
                property = isFlyMe() ? getFlymeVersion(buildProp) : isEmui(buildProp) ? getEmuiVersion(buildProp) : getYunOSVersion(buildProp);
                return property;
            } catch (Throwable unused) {
                return property;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    public static int getTimeZone(Context context) {
        if (context == null) {
            return 8;
        }
        try {
            Calendar calendar = Calendar.getInstance(getLocale(context));
            if (calendar != null) {
                return calendar.getTimeZone().getRawOffset() / 3600000;
            }
        } catch (Throwable th) {
            MLog.i(LOG_TAG, "error in getTimeZone", th);
        }
        return 8;
    }

    private static String getYunOSVersion(Properties properties) {
        try {
            String property = properties.getProperty("ro.yunos.version");
            if (TextUtils.isEmpty(property)) {
                return null;
            }
            return property;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean hasRequestPermission(Context context, String str) {
        if (!FieldManager.allow(d.av)) {
            return false;
        }
        try {
            for (String str2 : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
                if (str2.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isChineseAera(Context context) {
        String strImprintProperty;
        if (context == null) {
            return false;
        }
        try {
            strImprintProperty = UMEnvelopeBuild.imprintProperty(context, "country", "");
        } catch (Throwable unused) {
        }
        if (!TextUtils.isEmpty(strImprintProperty)) {
            return strImprintProperty.equals(AdvanceSetting.CLEAR_NOTIFICATION);
        }
        if (getImsi(context) != null) {
            int i2 = context.getResources().getConfiguration().mcc;
            if (i2 != 460 && i2 != 461) {
                if (i2 == 0) {
                    String str = getLocaleInfo(context)[0];
                    if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
                    }
                }
            }
            return true;
        }
        String str2 = getLocaleInfo(context)[0];
        if (!TextUtils.isEmpty(str2) && str2.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
            return true;
        }
        return false;
    }

    private static boolean isEmui(Properties properties) {
        return properties.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
    }

    private static boolean isFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar", null);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isHarmony(Context context) {
        try {
            return context.getString(Resources.getSystem().getIdentifier("config_os_brand", "string", "android")).equals(com.alibaba.ailabs.iot.mesh.utils.Utils.HARMONY_OS);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isHonorDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            return false;
        }
        try {
            if (checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") && (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static boolean isWiFiAvailable(Context context) {
        if (context == null) {
            return false;
        }
        return "Wi-Fi".equals(getNetworkAccessMode(context)[0]);
    }

    private static String meid(Context context) {
        if (!TextUtils.isEmpty(sMeid)) {
            return sMeid;
        }
        String str = null;
        if (hasReadImeiOrMeid || context == null) {
            return null;
        }
        try {
            Object objInvoke = Class.forName("android.telephony.TelephonyManager").getMethod("getMeid", null).invoke(null, null);
            if (objInvoke != null && (objInvoke instanceof String)) {
                str = (String) objInvoke;
            }
        } finally {
            try {
                sMeid = str;
                return sMeid;
            } finally {
            }
        }
        sMeid = str;
        return sMeid;
    }

    private static String reaMac(String str) {
        BufferedReader bufferedReader;
        String line = null;
        try {
            FileReader fileReader = new FileReader(str);
            try {
                bufferedReader = new BufferedReader(fileReader, 1024);
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
            }
            try {
                line = bufferedReader.readLine();
                try {
                    fileReader.close();
                } catch (Throwable unused) {
                }
                bufferedReader.close();
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileReader.close();
                } catch (Throwable unused2) {
                }
                if (bufferedReader == null) {
                    throw th;
                }
                try {
                    bufferedReader.close();
                    throw th;
                } catch (Throwable unused3) {
                    throw th;
                }
            }
        } catch (Throwable unused4) {
        }
        return line;
    }

    private static int reflectMetrics(Object obj, String str) {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(obj);
        } catch (Throwable unused) {
            return -1;
        }
    }
}
