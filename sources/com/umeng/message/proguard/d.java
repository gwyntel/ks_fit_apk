package com.umeng.message.proguard;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.webkit.ProxyConfig;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.AnalyticsEvents;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UPLog;
import com.umeng.ut.device.UTDevice;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    public static String f22827a;

    /* renamed from: b, reason: collision with root package name */
    public static String f22828b;

    /* renamed from: c, reason: collision with root package name */
    public static String f22829c;

    /* renamed from: d, reason: collision with root package name */
    private static String f22830d;

    /* renamed from: e, reason: collision with root package name */
    private static Boolean f22831e;

    /* renamed from: f, reason: collision with root package name */
    private static Boolean f22832f;

    /* renamed from: g, reason: collision with root package name */
    private static String f22833g;

    /* renamed from: h, reason: collision with root package name */
    private static Boolean f22834h;

    public static String a(Context context) {
        return UMUtils.getAppVersionCode(context);
    }

    public static String b(Context context) {
        return UMUtils.getAppVersionName(context);
    }

    public static String c(Context context) {
        String str = "";
        try {
            String imeiNew = DeviceConfig.getImeiNew(context);
            try {
                if (TextUtils.isEmpty(imeiNew)) {
                    return "";
                }
                String messageAppkey = PushAgent.getInstance(context).getMessageAppkey();
                if (messageAppkey == null || messageAppkey.length() < 16) {
                    return imeiNew;
                }
                return ax.a(imeiNew, messageAppkey.substring(0, 16), as.a(new String(new byte[]{98, 109, Constants.CMD_TYPE.CMD_STATUS_REPORT, 108, 100, 87, 99, 117, 90, 106, 107, 118, 84, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 114, 84, 68, 103, 121, 77, 119, 61, 61})));
            } catch (Exception e2) {
                e = e2;
                str = imeiNew;
                UPLog.e("DeviceConfig", e);
                return str;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static String d(Context context) {
        String str = "";
        try {
            String strS = s(context);
            try {
                if (TextUtils.isEmpty(strS)) {
                    return "";
                }
                String messageAppkey = PushAgent.getInstance(context).getMessageAppkey();
                if (messageAppkey == null || messageAppkey.length() < 16) {
                    return strS;
                }
                return ax.a(strS, messageAppkey.substring(0, 16), as.a(new String(new byte[]{98, 109, Constants.CMD_TYPE.CMD_STATUS_REPORT, 108, 100, 87, 99, 117, 90, 106, 107, 118, 84, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 114, 84, 68, 103, 121, 77, 119, 61, 61})));
            } catch (Exception e2) {
                e = e2;
                str = strS;
                UPLog.e("DeviceConfig", e);
                return str;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static String e(Context context) {
        return DeviceConfig.getAndroidId(context);
    }

    public static String f(Context context) {
        return UMUtils.MD5(s(context));
    }

    public static String[] g(Context context) {
        return UMUtils.getNetworkAccessMode(context);
    }

    public static boolean h(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
            return true;
        } catch (Throwable th) {
            UPLog.e("DeviceConfig", th);
            return true;
        }
    }

    public static int i(Context context) {
        try {
            return Calendar.getInstance(t(context)).getTimeZone().getRawOffset() / 3600000;
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            return 8;
        }
    }

    public static String[] j(Context context) {
        String[] strArr = new String[2];
        try {
            Locale localeT = t(context);
            strArr[0] = localeT.getCountry();
            strArr[1] = localeT.getLanguage();
            if (TextUtils.isEmpty(strArr[0])) {
                strArr[0] = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            if (TextUtils.isEmpty(strArr[1])) {
                strArr[1] = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            return strArr;
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            return strArr;
        }
    }

    public static String k(Context context) {
        String uMId = UMUtils.getUMId(context);
        return uMId == null ? "" : uMId;
    }

    public static String l(Context context) {
        int iA;
        int iA2;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            if ((context.getApplicationInfo().flags & 8192) == 0) {
                iA2 = a(displayMetrics, "noncompatWidthPixels");
                iA = a(displayMetrics, "noncompatHeightPixels");
            } else {
                iA = 0;
                iA2 = -1;
            }
            if (iA2 == -1 || iA == -1) {
                iA2 = displayMetrics.widthPixels;
                iA = displayMetrics.heightPixels;
            }
            return iA2 + ProxyConfig.MATCH_ALL_SCHEMES + iA;
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public static String m(Context context) {
        try {
            return UMUtils.getOperator(context);
        } catch (Throwable unused) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public static String n(Context context) {
        String channelByXML = UMUtils.getChannelByXML(context);
        return !TextUtils.isEmpty(channelByXML) ? channelByXML : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    }

    public static String o(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Throwable th) {
            UPLog.e("DeviceConfig", "utdid failed! " + th.getMessage());
            return "";
        }
    }

    public static String p(Context context) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        int iQ = q(context);
        return iQ == 0 ? Boolean.toString(false) : iQ == 1 ? Boolean.toString(true) : "unknown";
    }

    public static int q(Context context) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        if (f.b()) {
            UPLog.d("DeviceConfig", "silent mode disabled");
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                return ((NotificationManager) context.getSystemService("notification")).areNotificationsEnabled() ? 1 : 0;
            } catch (Throwable th) {
                UPLog.e("DeviceConfig", th);
                return -1;
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
            return ((Integer) method.invoke(appOpsManager, num, Integer.valueOf(i2), packageName)).intValue() == 0 ? 1 : 0;
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            return -1;
        }
    }

    public static boolean r(Context context) {
        if (f.b()) {
            UPLog.d("DeviceConfig", "silent mode disabled");
            return false;
        }
        try {
            Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
            if (Build.VERSION.SDK_INT >= 26) {
                intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                intent.putExtra("android.provider.extra.CHANNEL_ID", context.getApplicationInfo().uid);
            } else {
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
            }
            intent.setFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Throwable th) {
            UPLog.e("DeviceConfig", th);
            try {
                Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
                intent2.setFlags(268435456);
                context.startActivity(intent2);
                return true;
            } catch (Throwable th2) {
                UPLog.e("DeviceConfig", th2);
                return false;
            }
        }
    }

    private static String s(Context context) {
        if (!TextUtils.isEmpty(f22830d)) {
            return f22830d;
        }
        String imeiNew = DeviceConfig.getImeiNew(context);
        f22830d = imeiNew;
        if (!TextUtils.isEmpty(imeiNew)) {
            return f22830d;
        }
        String androidId = DeviceConfig.getAndroidId(context);
        f22830d = androidId;
        if (!TextUtils.isEmpty(androidId)) {
            return f22830d;
        }
        String serial = DeviceConfig.getSerial();
        f22830d = serial;
        if (TextUtils.isEmpty(serial)) {
            f22830d = "";
        }
        return f22830d;
    }

    private static Locale t(Context context) {
        Locale locale;
        try {
            Configuration configuration = new Configuration();
            Settings.System.getConfiguration(context.getContentResolver(), configuration);
            locale = configuration.locale;
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            locale = null;
        }
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String a() {
        return UMUtils.getCPU();
    }

    public static String b() {
        return DeviceConfig.getSerial();
    }

    public static String e() {
        if (TextUtils.isEmpty(f22829c)) {
            f22829c = Build.BOARD;
        }
        return f22829c;
    }

    public static String f() {
        if (TextUtils.isEmpty(f22828b)) {
            String str = Build.BRAND;
            f22828b = str;
            if (TextUtils.isEmpty(str)) {
                f22828b = Build.MANUFACTURER;
            }
        }
        return f22828b;
    }

    public static boolean g() {
        String strF;
        Boolean bool = f22831e;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            strF = f();
        } catch (Throwable unused) {
        }
        if ("huawei".equalsIgnoreCase(strF)) {
            f22831e = Boolean.TRUE;
            return true;
        }
        if ("honor".equalsIgnoreCase(strF)) {
            f22831e = Boolean.TRUE;
            return true;
        }
        f22831e = Boolean.FALSE;
        return false;
    }

    public static String a(Context context, String str) {
        try {
            String string = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(str);
            if (string != null) {
                return string.trim();
            }
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
        }
        UPLog.w("DeviceConfig", String.format("Could not read meta-data %s from AndroidManifest.xml.", str));
        return null;
    }

    private static String k() {
        String str = f22833g;
        if (str != null) {
            return str;
        }
        if (!"vivo".equalsIgnoreCase(f())) {
            f22833g = "";
            return "";
        }
        String strA = a("ro.vivo.os.build.display.id");
        f22833g = strA;
        if (!TextUtils.isEmpty(strA)) {
            return f22833g;
        }
        String strA2 = a("ro.iqoo.os.build.display.id");
        f22833g = strA2;
        if (!TextUtils.isEmpty(strA2)) {
            return f22833g;
        }
        f22833g = "";
        return "";
    }

    public static boolean h() {
        Boolean bool = f22832f;
        if (bool != null) {
            return bool.booleanValue();
        }
        if ("vivo".equalsIgnoreCase(f())) {
            f22832f = Boolean.TRUE;
            return true;
        }
        String strK = k();
        if (TextUtils.isEmpty(strK)) {
            f22832f = Boolean.FALSE;
            return false;
        }
        if (strK.startsWith("OriginOS") || strK.startsWith("Funtouch")) {
            f22832f = Boolean.TRUE;
            return true;
        }
        f22832f = Boolean.FALSE;
        return false;
    }

    public static boolean i() {
        Boolean bool = f22834h;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            boolean zEqualsIgnoreCase = Utils.HARMONY_OS.equalsIgnoreCase((String) cls.getMethod("getOsBrand", null).invoke(cls, null));
            f22834h = Boolean.valueOf(zEqualsIgnoreCase);
            return zEqualsIgnoreCase;
        } catch (Throwable unused) {
            f22834h = Boolean.FALSE;
            return false;
        }
    }

    private static int a(Object obj, String str) throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(obj);
        } catch (Exception e2) {
            UPLog.e("DeviceConfig", e2);
            return -1;
        }
    }

    public static String c() {
        return "02:00:00:00:00:00";
    }

    public static String d() {
        if (TextUtils.isEmpty(f22827a)) {
            f22827a = Build.MODEL;
        }
        return f22827a;
    }

    public static String j() {
        return a(com.alipay.sdk.m.c.a.f9196b);
    }

    private static String a(String str) {
        try {
            return (String) ba.a("android.os.SystemProperties", TmpConstant.PROPERTY_IDENTIFIER_GET, new Class[]{String.class}, null, new Object[]{str});
        } catch (Throwable unused) {
            return "";
        }
    }
}
