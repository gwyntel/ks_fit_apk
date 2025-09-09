package com.umeng.commonsdk.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import androidx.webkit.ProxyConfig;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.AnalyticsEvents;
import com.google.common.base.Ascii;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.idtracking.h;
import com.umeng.message.PushAgent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
/* loaded from: classes4.dex */
public class UMUtils {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_APP_KEY = "appkey";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_LAST_APP_KEY = "last_appkey";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_SESSION_ID = "session_id";
    public static final String MOBILE_NETWORK = "2G/3G";
    private static final String SD_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String TAG = "UMUtils";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";
    private static final String KEY_SHARED_PREFERENCES_NAME = ay.b().b(ay.f21374i);
    private static final Pattern pattern = Pattern.compile("UTDID\">([^<]+)");
    private static final String SP_FILE_NAME = ay.b().b(ay.f21376k);
    private static Object spLock = new Object();
    public static String VALUE_ANALYTICS_VERSION = "";
    public static String VALUE_GAME_VERSION = "";
    public static String VALUE_PUSH_VERSION = "";
    public static String VALUE_SHARE_VERSION = "";
    public static String VALUE_APM_VERSION = "";
    public static String VALUE_VERIFY_VERSION = "";
    public static String VALUE_SMS_VERSION = "";
    public static String VALUE_REC_VERSION_NAME = "";
    public static String VALUE_VISUAL_VERSION = "";
    public static String VALUE_ASMS_VERSION = "";
    public static String VALUE_LINK_VERSION = "";
    public static String VALUE_ABTEST_VERSION = "";
    public static String VALUE_ANTI_VERSION = "";
    private static volatile String sAppVersionName = "";
    private static volatile String sAppVersionCode = "";

    public static String MD5(String str) {
        try {
            if (str == null) {
                return null;
            }
            try {
                try {
                    byte[] bytes = str.getBytes();
                    MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
                    messageDigest.reset();
                    messageDigest.update(bytes);
                    byte[] bArrDigest = messageDigest.digest();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (byte b2 : bArrDigest) {
                        stringBuffer.append(String.format("%02X", Byte.valueOf(b2)));
                    }
                    return stringBuffer.toString();
                } catch (Exception unused) {
                    return str.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
                }
            } catch (Exception e2) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "MD5 e is " + e2);
                }
                return null;
            }
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "MD5 e is " + th);
            }
            return null;
        }
    }

    @SuppressLint({"DefaultLocale"})
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
            sb.append(hexString.toUpperCase());
            if (i2 < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    private static String bytes2Hex(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                str = str + "0";
            }
            str = str + hexString;
        }
        return str;
    }

    public static boolean checkAndroidManifest(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getApplicationContext().getPackageManager().getActivityInfo(new ComponentName(context.getApplicationContext().getPackageName(), str), 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean checkIntentFilterData(Context context, String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setData(Uri.parse("tencent" + str + ":"));
            List<ResolveInfo> listQueryIntentActivities = context.getApplicationContext().getPackageManager().queryIntentActivities(intent, 64);
            if (listQueryIntentActivities.size() > 0) {
                Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
                while (it.hasNext()) {
                    ActivityInfo activityInfo = it.next().activityInfo;
                    if (activityInfo != null && activityInfo.packageName.equals(context.getApplicationContext().getPackageName())) {
                        return true;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static boolean checkMetaData(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            ApplicationInfo applicationInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getApplicationContext().getPackageName(), 128);
            if (applicationInfo != null) {
                if (applicationInfo.metaData.get(str) != null) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static boolean checkPath(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return false;
        }
    }

    public static boolean checkResource(Context context, String str, String str2) {
        try {
            return context.getApplicationContext().getResources().getIdentifier(str, str2, context.getApplicationContext().getPackageName()) > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String encryptBySHA1(String str) {
        try {
            try {
                byte[] bytes = str.getBytes();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    messageDigest.update(bytes);
                    return bytes2Hex(messageDigest.digest());
                } catch (Exception unused) {
                    return null;
                }
            } catch (Throwable th) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "encrypt by SHA1 e is " + th);
                }
                return null;
            }
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "encrypt by SHA1 e is " + e2);
            }
            return null;
        }
    }

    public static String genId() {
        return "1234567890";
    }

    public static byte[] genIv() {
        byte[] miniArray = getMiniArray();
        return new byte[]{10, 1, (byte) (10 + 1), 5, 4, (byte) (10 + 5), 7, 9, Ascii.ETB, 3, miniArray[0], miniArray[1], miniArray[2], miniArray[3], miniArray[4], (byte) (100 - 9)};
    }

    public static byte[] genSin() {
        byte[] miniArray = getMiniArray();
        return new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 69, (byte) (miniArray[4] + Constants.CMD_TYPE.CMD_NOTIFY_STATUS), Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, (byte) (55 + 57), 97, (byte) (69 + 43), 83, 57, 97, (byte) (97 + 2), 90, 120, (byte) (97 + miniArray[1]), (byte) (miniArray[0] + 72), (byte) (90 + miniArray[2])};
    }

    public static byte[] genUmc() {
        return new byte[]{Ascii.US, -117, 8, 0, 0, 0, 0, 0, 0, 0, 1, -27, 0, Ascii.SUB, -1, 76, 102, Constants.CMD_TYPE.CMD_REQUEST_OTA, 19, 78, 70, Ascii.ETB, 58, 85, Ascii.SUB, 6, 56, 16, 68, Constants.CMD_TYPE.CMD_OTA_RES, 2, 73, 32, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, 79, 16, 90, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, 43, 79, 12, Ascii.FS, 57, 16, 88, 123, 95, 100, 70, 91, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, 39, 104, 59, 61, Ascii.DC4, 57, Ascii.EM, 2, 63, 113, Ascii.GS, 18, 100, 102, 120, 100, 5, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 98, 123, 0, 39, 57, Ascii.CAN, 40, Ascii.EM, 112, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 93, Ascii.SYN, 73, Ascii.DC4, 46, 83, 73, 71, 117, 70, 69, 110, 73, 73, 117, 114, 2, 75, 65, 72, 112, 18, Ascii.SYN, 120, 98, 71, 85, 98, 83, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 105, 64, 63, 117, 99, 115, 70, 115, Constants.CMD_TYPE.CMD_OTA_RES, 43, 73, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 5, 108, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 80, 112, Constants.CMD_TYPE.CMD_REQUEST_OTA, 103, 123, 115, 74, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 58, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 3, 98, 56, 13, 63, -42, -86, -91, -61, -101, -115, -110, -6, -48, -117, -118, -66, -127, -124, -77, -98, -119, -107, -78, -62, -117, -127, -120, -80, -20, -103, -105, -96, -125, -97, -91, -69, -30, -112, -108, -29, -79, -26, -6, -42, -29, -22, -13, -45, -77, -81, -109, -97, -30, -121, -124, -19, -80, -10, -22, -58, -5, -6, -29, -61, -91, -76, -109, -81, -104, -19, -27, -33, -43, -57, -43, -5, -124, -58, -35, -30, -102, -21, -28, -50, -120, -31, -1, -107, -23, -43, -56, -27, -122, -35, -53, -62, -59, -42, -5, -33, -74, -57, -54, -13, -23, -88, -43, 111, Constants.CMD_TYPE.CMD_SIGNATURE_RES, -27, 0, 0, 0};
    }

    public static String[] getActiveUser(Context context) {
        return com.umeng.analytics.c.a(context);
    }

    public static String getApmFlag() {
        Method declaredMethod;
        try {
            Class<?> cls = getClass("com.umeng.umcrash.UMCrash");
            if (cls == null || (declaredMethod = cls.getDeclaredMethod("getUMAPMFlag", null)) == null) {
                return "";
            }
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(cls, null);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getAppHashKey(Context context) {
        return DeviceConfig.getAppHashKey(context);
    }

    public static String getAppMD5Signature(Context context) {
        String strReplace = "";
        try {
            String appMD5Signature = DeviceConfig.getAppMD5Signature(context);
            try {
                if (TextUtils.isEmpty(appMD5Signature)) {
                    return appMD5Signature;
                }
                strReplace = appMD5Signature.replace(":", "");
                return strReplace.toLowerCase();
            } catch (Throwable unused) {
                return appMD5Signature;
            }
        } catch (Throwable unused2) {
            return strReplace;
        }
    }

    public static String getAppName(Context context) {
        return DeviceConfig.getAppName(context);
    }

    public static String getAppSHA1Key(Context context) {
        return DeviceConfig.getAppSHA1Key(context);
    }

    public static String getAppVersinoCode(Context context, String str) {
        if (context != null && str != null) {
            try {
                PackageInfo packageInfoA = b.a().a(context, str, 64);
                if (packageInfoA != null) {
                    return String.valueOf(packageInfoA.versionCode);
                }
            } catch (Exception e2) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get app version code e is " + e2);
                }
            } catch (Throwable th) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get app version code e is " + th);
                }
            }
        }
        return "";
    }

    public static String getAppVersionCode(Context context) {
        if (!TextUtils.isEmpty(sAppVersionCode)) {
            return sAppVersionCode;
        }
        if (context == null) {
            return "";
        }
        try {
            PackageInfo packageInfoA = b.a().a(context, context.getPackageName(), 64);
            if (packageInfoA == null) {
                return "";
            }
            String strValueOf = String.valueOf(packageInfoA.versionCode);
            sAppVersionCode = strValueOf;
            return strValueOf;
        } catch (Exception e2) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            Log.e(TAG, "get app version code e is " + e2);
            return "";
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            Log.e(TAG, "get app version code e is " + th);
            return "";
        }
    }

    public static String getAppVersionName(Context context) {
        if (!TextUtils.isEmpty(sAppVersionName)) {
            return sAppVersionName;
        }
        if (context == null) {
            return "";
        }
        try {
            PackageInfo packageInfoA = b.a().a(context, context.getPackageName(), 64);
            if (packageInfoA == null) {
                return "";
            }
            String str = packageInfoA.versionName;
            sAppVersionName = str;
            return str;
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            Log.e(TAG, "get app version name e is " + th);
            return "";
        }
    }

    public static String getAppkey(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return !TextUtils.isEmpty(UMConfigure.sAppkey) ? UMConfigure.sAppkey : getMultiProcessSP(context, "appkey");
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app key e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return null;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app key e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String getAppkeyByXML(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return null;
            }
            String string = applicationInfo.metaData.getString("UMENG_APPKEY");
            if (string != null) {
                return string.trim();
            }
            if (!AnalyticsConstants.UM_DEBUG) {
                return null;
            }
            MLog.i(AnalyticsConstants.LOG_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Properties getBuildProp() throws Throwable {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                try {
                    properties.load(fileInputStream2);
                    fileInputStream2.close();
                } catch (IOException unused) {
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return properties;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
        return properties;
    }

    public static String getCPU() {
        String line = null;
        try {
            try {
                FileReader fileReader = new FileReader("/proc/cpuinfo");
                try {
                    BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
                    line = bufferedReader.readLine();
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException e2) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        Log.e(TAG, "Could not read from file /proc/cpuinfo, e is " + e2);
                    }
                }
            } catch (Exception e3) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get cpu e is " + e3);
                }
                return "";
            } catch (Throwable th) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get cpu e is " + th);
                }
                return "";
            }
        } catch (FileNotFoundException e4) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not read from file /proc/cpuinfo, e is " + e4);
            }
        }
        return line != null ? line.substring(line.indexOf(58) + 1).trim() : "";
    }

    public static String getChannel(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return !TextUtils.isEmpty(UMConfigure.sChannel) ? UMConfigure.sChannel : getMultiProcessSP(context, "channel");
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get channel e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return null;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get channel e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String getChannelByXML(Context context) {
        Bundle bundle;
        Object obj;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || (obj = bundle.get(OpenAccountConstants.CHANNEL_META_CONFIG_KEY_UMENG)) == null) {
                return null;
            }
            String string = obj.toString();
            if (string != null) {
                return string.trim();
            }
            if (!AnalyticsConstants.UM_DEBUG) {
                return null;
            }
            MLog.i(AnalyticsConstants.LOG_TAG, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getDeviceToken(Context context) {
        Object objInvoke;
        Method method;
        Object objInvoke2;
        if (context == null) {
            return null;
        }
        Context applicationContext = context.getApplicationContext();
        try {
            int i2 = PushAgent.f22603a;
            Method method2 = PushAgent.class.getMethod("getInstance", Context.class);
            if (method2 == null || (objInvoke = method2.invoke(PushAgent.class, applicationContext)) == null || (method = PushAgent.class.getMethod("getRegistrationId", null)) == null || (objInvoke2 = method.invoke(objInvoke, null)) == null || !(objInvoke2 instanceof String)) {
                return null;
            }
            return (String) objInvoke2;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getDeviceType(Context context) {
        if (context == null) {
            return "Phone";
        }
        try {
            return (context.getResources().getConfiguration().screenLayout & 15) >= 3 ? "Tablet" : "Phone";
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get device type e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return null;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get device type e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String getDisplayResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                return "";
            }
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i2 = displayMetrics.widthPixels;
            return String.valueOf(displayMetrics.heightPixels) + ProxyConfig.MATCH_ALL_SCHEMES + String.valueOf(i2);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get display resolution e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get display resolution e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return "";
        }
    }

    public static String getFileMD5(File file) {
        try {
            try {
                byte[] bArr = new byte[1024];
                try {
                    if (!file.isFile()) {
                        return "";
                    }
                    MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    while (true) {
                        int i2 = fileInputStream.read(bArr, 0, 1024);
                        if (i2 == -1) {
                            fileInputStream.close();
                            return String.format("%1$032x", new BigInteger(1, messageDigest.digest()));
                        }
                        messageDigest.update(bArr, 0, i2);
                    }
                } catch (Exception unused) {
                    return null;
                }
            } catch (Exception e2) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get file MD5 e is " + e2);
                }
                return null;
            }
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get file MD5 e is " + th);
            }
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
        } catch (Exception unused) {
            return null;
        }
    }

    public static String[] getGPU(GL10 gl10) {
        try {
            return new String[]{gl10.glGetString(7936), gl10.glGetString(7937)};
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not read gpu infor, e is " + e2);
            }
            return new String[0];
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not read gpu infor, e is " + th);
            }
            return new String[0];
        }
    }

    public static String getLastAppkey(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return getMultiProcessSP(context, KEY_LAST_APP_KEY);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get last app key e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return null;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get last app key e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static Locale getLocale(Context context) {
        Locale locale;
        if (context == null) {
            return null;
        }
        try {
            try {
                Configuration configuration = new Configuration();
                configuration.setToDefaults();
                Settings.System.getConfiguration(context.getContentResolver(), configuration);
                locale = configuration.locale;
            } catch (Throwable th) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get locale e is " + th);
                }
                UMCrashManager.reportCrash(context, th);
                return null;
            }
        } catch (Exception e2) {
            try {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "fail to read user config locale, e is " + e2);
                }
                locale = null;
            } catch (Exception e3) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get locale e is " + e3);
                }
                UMCrashManager.reportCrash(context, e3);
                return null;
            }
        }
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String getMac(Context context) {
        if (context == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return null;
            }
            if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            Log.e(TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return "";
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get mac e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
            return null;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get mac e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    private static byte[] getMiniArray() {
        return new byte[]{1, 6, 8, 12, 13};
    }

    public static JSONObject getModuleVer() {
        JSONObject jSONObject = null;
        try {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(bc.bi, com.umeng.commonsdk.internal.a.f22241e);
                if (!TextUtils.isEmpty(VALUE_ANALYTICS_VERSION)) {
                    jSONObject2.put(bc.bj, VALUE_ANALYTICS_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_GAME_VERSION)) {
                    jSONObject2.put(bc.bk, VALUE_GAME_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_PUSH_VERSION)) {
                    jSONObject2.put(bc.bl, VALUE_PUSH_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_SHARE_VERSION)) {
                    jSONObject2.put(bc.bm, VALUE_SHARE_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_APM_VERSION)) {
                    jSONObject2.put(bc.bn, VALUE_APM_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_VERIFY_VERSION)) {
                    jSONObject2.put(bc.bo, VALUE_VERIFY_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_SMS_VERSION)) {
                    jSONObject2.put(bc.bp, VALUE_SMS_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_REC_VERSION_NAME)) {
                    jSONObject2.put(bc.bq, VALUE_REC_VERSION_NAME);
                }
                if (!TextUtils.isEmpty(VALUE_VISUAL_VERSION)) {
                    jSONObject2.put(bc.br, VALUE_VISUAL_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_ASMS_VERSION)) {
                    jSONObject2.put(bc.bs, VALUE_ASMS_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_LINK_VERSION)) {
                    jSONObject2.put(bc.bt, VALUE_LINK_VERSION);
                }
                if (!TextUtils.isEmpty(VALUE_ABTEST_VERSION)) {
                    jSONObject2.put(bc.bu, VALUE_ABTEST_VERSION);
                }
                if (TextUtils.isEmpty(VALUE_ANTI_VERSION)) {
                    return jSONObject2;
                }
                jSONObject2.put(bc.bv, VALUE_ANTI_VERSION);
                return jSONObject2;
            } catch (Throwable unused) {
                jSONObject = jSONObject2;
                return jSONObject;
            }
        } catch (Throwable unused2) {
        }
    }

    public static String getMultiProcessSP(Context context, String str) {
        SharedPreferences sharedPreferences;
        try {
            synchronized (spLock) {
                if (context != null) {
                    try {
                        if (!TextUtils.isEmpty(str)) {
                            if (isMainProgress(context)) {
                                sharedPreferences = context.getApplicationContext().getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, 0);
                            } else {
                                String subProcessName = UMFrUtils.getSubProcessName(context);
                                sharedPreferences = context.getApplicationContext().getSharedPreferences(subProcessName + OpenAccountUIConstants.UNDER_LINE + KEY_SHARED_PREFERENCES_NAME, 0);
                            }
                            if (sharedPreferences == null) {
                                return null;
                            }
                            return sharedPreferences.getString(str, null);
                        }
                    } finally {
                    }
                }
                return null;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = {"", ""};
        if (context == null) {
            return strArr;
        }
        try {
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network access mode e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network access mode e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
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
            return strArr;
        }
        return strArr;
    }

    public static String getNetworkOperatorName(Context context) {
        return DeviceConfig.getNetworkOperatorName(context);
    }

    public static String getOaidRequiredTime(Context context) {
        if (!FieldManager.allow(d.G)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(h.f22404a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(h.f22406c, "");
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getOperator(Context context) {
        String registeredOperator = DeviceConfig.getRegisteredOperator(context);
        return TextUtils.isEmpty(registeredOperator) ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : registeredOperator;
    }

    public static String getRegisteredOperator(Context context) {
        return DeviceConfig.getRegisteredOperator(context);
    }

    public static String getSubOSName(Context context) {
        String str;
        if (context == null) {
            return null;
        }
        try {
            try {
                Properties buildProp = getBuildProp();
                try {
                    String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
                    if (!TextUtils.isEmpty(property)) {
                        str = "MIUI";
                    } else if (isFlyMe()) {
                        str = "Flyme";
                    } else {
                        if (TextUtils.isEmpty(getYunOSVersion(buildProp))) {
                            return property;
                        }
                        str = "YunOS";
                    }
                    return str;
                } catch (Exception e2) {
                    UMCrashManager.reportCrash(context, e2);
                    return null;
                }
            } catch (Exception e3) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get sub os name e is " + e3);
                }
                UMCrashManager.reportCrash(context, e3);
                return null;
            }
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get sub os name e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String getSubOSVersion(Context context) {
        if (context == null) {
            return null;
        }
        try {
            try {
                Properties buildProp = getBuildProp();
                try {
                    String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
                    if (TextUtils.isEmpty(property)) {
                        try {
                            return isFlyMe() ? getFlymeVersion(buildProp) : getYunOSVersion(buildProp);
                        } catch (Exception unused) {
                        }
                    }
                    return property;
                } catch (Exception e2) {
                    UMCrashManager.reportCrash(context, e2);
                    return null;
                }
            } catch (Exception e3) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get sub os version e is " + e3);
                }
                UMCrashManager.reportCrash(context, e3);
                return null;
            }
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get sub os version e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(null, str, str2);
        } catch (Throwable unused) {
            return str2;
        }
    }

    public static int getTargetSdkVersion(Context context) {
        try {
            return context.getApplicationInfo().targetSdkVersion;
        } catch (Throwable unused) {
            return 0;
        }
    }

    public static String getUMId(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return UMEnvelopeBuild.imprintProperty(context.getApplicationContext(), bc.f21408g, null);
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return null;
        }
    }

    public static String getUUIDForZid(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, 0);
        return sharedPreferences != null ? sharedPreferences.getString("session_id", "") : "";
    }

    public static String getUmengToken(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return UMEnvelopeBuild.imprintProperty(context.getApplicationContext(), "ztoken", null);
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return null;
        }
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

    public static String getZid(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (UMConfigure.needSendZcfgEnv(applicationContext)) {
            return null;
        }
        return com.umeng.commonsdk.internal.b.a(applicationContext).a().a();
    }

    public static boolean isAppInstalled(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return b.a().a(context, str, 0) != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isApplication(Context context) {
        try {
            String name = context.getApplicationContext().getClass().getSuperclass().getName();
            if (TextUtils.isEmpty(name)) {
                return false;
            }
            return name.equals("android.app.Application");
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isDebug(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return false;
        }
    }

    private static boolean isFlyMe() throws NoSuchMethodException, SecurityException {
        try {
            Build.class.getMethod("hasSmartBar", null);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isMainProgress(Context context) {
        try {
            String currentProcessName = UMFrUtils.getCurrentProcessName(context);
            String packageName = context.getApplicationContext().getPackageName();
            if (TextUtils.isEmpty(currentProcessName) || TextUtils.isEmpty(packageName)) {
                return false;
            }
            return currentProcessName.equals(packageName);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isSdCardWrittenable() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Throwable unused) {
            return false;
        }
    }

    private static String parseId(String str) {
        if (str == null) {
            return null;
        }
        try {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    private static String readStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] cArr = new char[1024];
        StringWriter stringWriter = new StringWriter();
        while (true) {
            int i2 = inputStreamReader.read(cArr);
            if (-1 == i2) {
                return stringWriter.toString();
            }
            stringWriter.write(cArr, 0, i2);
        }
    }

    private static void safeClose(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void saveSDKComponent() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("a");
        if (UMConfigure.isDebugLog()) {
            UMLog.mutlInfo(2, "统计SDK版本号: 9.7.4.1+000");
        }
        VALUE_ANALYTICS_VERSION = "9.7.4.1+000";
        String strB = com.umeng.commonsdk.internal.c.b();
        if (!TextUtils.isEmpty(strB)) {
            VALUE_ASMS_VERSION = strB;
            if (UMConfigure.isDebugLog()) {
                UMLog.mutlInfo(2, "ZID SDK版本号: " + strB);
            }
        }
        Class<?> cls = getClass("com.umeng.analytics.game.GameSdkVersion");
        if (cls != null) {
            stringBuffer.append("g");
            try {
                String str = (String) cls.getDeclaredField("SDK_VERSION").get(cls);
                if (!TextUtils.isEmpty(str)) {
                    VALUE_GAME_VERSION = str;
                    if (UMConfigure.isDebugLog()) {
                        UMLog.mutlInfo(2, "游戏统计SDK版本号: " + str);
                    }
                }
            } catch (Throwable unused) {
            }
        }
        Class<?> cls2 = getClass("com.umeng.vt.V");
        if (cls2 != null) {
            stringBuffer.append("v");
            try {
                String str2 = (String) cls2.getDeclaredField("VERSION").get(cls2);
                if (!TextUtils.isEmpty(str2)) {
                    VALUE_VISUAL_VERSION = str2;
                    if (UMConfigure.isDebugLog()) {
                        UMLog.mutlInfo(2, "可视化埋点SDK版本号: " + str2);
                    }
                }
            } catch (Throwable unused2) {
            }
        }
        if (getClass("com.umeng.message.PushAgent") != null) {
            stringBuffer.append("p");
            Class<?> cls3 = getClass("com.umeng.message.MsgConstant");
            if (cls3 != null) {
                try {
                    String str3 = (String) cls3.getDeclaredField("SDK_VERSION").get(cls3);
                    if (!TextUtils.isEmpty(str3)) {
                        VALUE_PUSH_VERSION = str3;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "推送SDK版本号: " + str3);
                        }
                    }
                } catch (Throwable unused3) {
                }
            }
        }
        Class<?> cls4 = getClass("com.umeng.socialize.UMShareAPI");
        if (cls4 != null) {
            stringBuffer.append("s");
            Class<?> cls5 = getClass("com.umeng.a");
            if (cls5 != null) {
                try {
                    String str4 = (String) cls5.getDeclaredField("g").get(cls5);
                    if (!TextUtils.isEmpty(str4)) {
                        VALUE_SHARE_VERSION = str4;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "分享SDK版本号: " + str4);
                        }
                    }
                } catch (Throwable unused4) {
                }
            }
            if (TextUtils.isEmpty(VALUE_SHARE_VERSION)) {
                try {
                    Method declaredMethod = cls4.getDeclaredMethod("getSdkVersion", null);
                    declaredMethod.setAccessible(true);
                    VALUE_SHARE_VERSION = (String) declaredMethod.invoke(cls4, null);
                } catch (Throwable unused5) {
                }
            }
        }
        if (getClass("com.umeng.error.UMError") != null) {
            stringBuffer.append("e");
        }
        if (getClass("com.umeng.umzid.ZIDManager") != null) {
            stringBuffer.append(bc.aJ);
        }
        stringBuffer.append("i");
        if (SdkVersion.SDK_TYPE != 1 && getClass("com.umeng.commonsdk.internal.UMOplus") != null) {
            stringBuffer.append("o");
        }
        if (getClass("com.umeng.airec.RecAgent") != null) {
            stringBuffer.append(bc.aN);
            Class<?> cls6 = getClass("com.umeng.airec.BuildConfig");
            if (cls6 != null) {
                try {
                    String str5 = (String) cls6.getDeclaredField("VERSION_NAME").get(cls6);
                    if (!TextUtils.isEmpty(str5)) {
                        VALUE_REC_VERSION_NAME = str5;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "智能推荐SDK版本号: " + str5);
                        }
                    }
                } catch (Throwable unused6) {
                }
            }
        }
        if (getClass("com.umeng.umverify.UMVerifyHelper") != null) {
            stringBuffer.append("n");
        }
        Class<?> cls7 = getClass("com.umeng.sms.UMSMS");
        if (cls7 != null) {
            stringBuffer.append("m");
            try {
                Method declaredMethod2 = cls7.getDeclaredMethod("getVersion", null);
                if (declaredMethod2 != null) {
                    String str6 = (String) declaredMethod2.invoke(cls7, null);
                    if (!TextUtils.isEmpty(str6)) {
                        VALUE_SMS_VERSION = str6;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "短信验证码SDK版本号: " + str6);
                        }
                    }
                }
            } catch (Throwable unused7) {
            }
        }
        try {
            Class<?> cls8 = getClass("com.umeng.umcrash.UMCrash");
            if (cls8 != null) {
                stringBuffer.append(bc.aL);
                Field declaredField = cls8.getDeclaredField("crashSdkVersion");
                declaredField.setAccessible(true);
                String str7 = (String) declaredField.get(cls8);
                if (!TextUtils.isEmpty(str7)) {
                    VALUE_APM_VERSION = str7;
                    if (UMConfigure.isDebugLog()) {
                        UMLog.mutlInfo(2, "APM SDK版本号: " + str7);
                    }
                }
            }
        } catch (Throwable unused8) {
        }
        Class<?> cls9 = getClass("com.umeng.umlink.MobclickLink");
        if (cls9 != null) {
            stringBuffer.append("l");
            try {
                Method declaredMethod3 = cls9.getDeclaredMethod("getVersion", null);
                if (declaredMethod3 != null) {
                    String str8 = (String) declaredMethod3.invoke(cls9, null);
                    if (!TextUtils.isEmpty(str8)) {
                        VALUE_LINK_VERSION = str8;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "ULink SDK版本号: " + str8);
                        }
                    }
                }
            } catch (Throwable unused9) {
            }
        }
        Class<?> cls10 = getClass("com.umeng.cconfig.UMRemoteConfig");
        if (cls10 != null) {
            try {
                Method declaredMethod4 = cls10.getDeclaredMethod("getVersion", null);
                if (declaredMethod4 != null) {
                    stringBuffer.append("t");
                    String str9 = (String) declaredMethod4.invoke(cls10, null);
                    if (!TextUtils.isEmpty(str9)) {
                        VALUE_ABTEST_VERSION = str9;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "UABTEST SDK版本号: " + str9);
                        }
                    }
                }
            } catch (Throwable unused10) {
            }
        }
        Class<?> cls11 = getClass("com.uyumao.sdk.UYMManager");
        if (cls11 != null) {
            try {
                Method declaredMethod5 = cls11.getDeclaredMethod("getSdkVersion", null);
                if (declaredMethod5 != null) {
                    stringBuffer.append("r");
                    String str10 = (String) declaredMethod5.invoke(cls11, null);
                    if (!TextUtils.isEmpty(str10)) {
                        VALUE_ANTI_VERSION = str10;
                        if (UMConfigure.isDebugLog()) {
                            UMLog.mutlInfo(2, "ANTI SDK版本号: " + str10);
                        }
                    }
                }
            } catch (Throwable unused11) {
            }
        }
        if (TextUtils.isEmpty(stringBuffer)) {
            return;
        }
        com.umeng.commonsdk.statistics.b.f22322a = stringBuffer.toString();
        Log.i(AnalyticsConstants.LOG_TAG, "module init:" + com.umeng.commonsdk.statistics.b.f22322a);
    }

    public static void setAppkey(Context context, String str) {
        if (context == null || str == null) {
            return;
        }
        try {
            setMultiProcessSP(context, "appkey", str);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set app key e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set app key e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static void setChannel(Context context, String str) {
        if (context == null || str == null) {
            return;
        }
        try {
            setMultiProcessSP(context, "channel", str);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set channel e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set channel e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static void setLastAppkey(Context context, String str) {
        if (context == null || str == null) {
            return;
        }
        try {
            setMultiProcessSP(context, KEY_LAST_APP_KEY, str);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set last app key e is " + e2);
            }
            UMCrashManager.reportCrash(context, e2);
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "set last app key e is " + th);
            }
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static void setMultiProcessSP(Context context, String str, String str2) {
        SharedPreferences sharedPreferences;
        try {
            synchronized (spLock) {
                if (context != null) {
                    try {
                        if (!TextUtils.isEmpty(str) && str2 != null) {
                            if (isMainProgress(context)) {
                                sharedPreferences = context.getApplicationContext().getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, 0);
                            } else {
                                String subProcessName = UMFrUtils.getSubProcessName(context);
                                sharedPreferences = context.getApplicationContext().getSharedPreferences(subProcessName + OpenAccountUIConstants.UNDER_LINE + KEY_SHARED_PREFERENCES_NAME, 0);
                            }
                            if (sharedPreferences != null) {
                                sharedPreferences.edit().putString(str, str2).commit();
                            }
                        }
                    } finally {
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static void setUUIDForZid(Context context) {
        String string;
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, 0);
        try {
            string = UUID.randomUUID().toString();
        } catch (Throwable unused) {
            string = "";
        }
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("session_id", string).commit();
        }
    }

    public static String getAppVersionName(Context context, String str) {
        if (context != null && str != null) {
            try {
                PackageInfo packageInfoA = b.a().a(context, str, 64);
                if (packageInfoA != null) {
                    return packageInfoA.versionName;
                }
            } catch (Throwable th) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "get app version name e is " + th);
                }
                UMCrashManager.reportCrash(context, th);
            }
        }
        return "";
    }
}
