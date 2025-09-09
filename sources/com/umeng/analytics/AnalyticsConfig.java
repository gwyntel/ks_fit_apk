package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.l;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.Map;

/* loaded from: classes4.dex */
public class AnalyticsConfig {
    public static boolean CATCH_EXCEPTION = false;
    public static boolean CHANGE_CATCH_EXCEPTION_NOTALLOW = true;
    public static boolean CLEAR_EKV_BL = false;
    public static boolean CLEAR_EKV_WL = false;
    public static final String DEBUG_KEY = "debugkey";
    public static final String DEBUG_MODE_PERIOD = "sendaging";
    public static String GPU_RENDERER = "";
    public static String GPU_VENDER = "";
    public static final String RTD_PERIOD = "period";
    public static final String RTD_START_TIME = "startTime";

    /* renamed from: a, reason: collision with root package name */
    static double[] f21197a = null;

    /* renamed from: b, reason: collision with root package name */
    private static String f21198b = null;

    /* renamed from: c, reason: collision with root package name */
    private static String f21199c = null;

    /* renamed from: d, reason: collision with root package name */
    private static String f21200d = null;

    /* renamed from: e, reason: collision with root package name */
    private static int f21201e = 0;
    public static boolean enable = true;
    public static long kContinueSessionMillis = 30000;
    public static String mWrapperType;
    public static String mWrapperVersion;
    public static final String RTD_SP_FILE = ay.b().b(ay.A);

    /* renamed from: f, reason: collision with root package name */
    private static Object f21202f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static boolean f21203g = false;

    /* renamed from: h, reason: collision with root package name */
    private static String f21204h = "";

    static void a(String str) {
        f21199c = str;
    }

    public static String getAppkey(Context context) {
        return UMUtils.getAppkey(context);
    }

    public static String getChannel(Context context) {
        return UMUtils.getChannel(context);
    }

    public static String getGameSdkVersion(Context context) {
        try {
            Class<?> cls = Class.forName("com.umeng.analytics.game.GameSdkVersion");
            return (String) cls.getDeclaredField("SDK_VERSION").get(cls);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static double[] getLocation() {
        return f21197a;
    }

    public static String getRealTimeDebugKey() {
        String str;
        synchronized (f21202f) {
            str = f21204h;
        }
        return str;
    }

    public static String getSecretKey(Context context) {
        if (TextUtils.isEmpty(f21200d)) {
            f21200d = com.umeng.common.b.a(context).c();
        }
        return f21200d;
    }

    public static int getVerticalType(Context context) {
        if (f21201e == 0) {
            f21201e = com.umeng.common.b.a(context).d();
        }
        return f21201e;
    }

    public static boolean isRealTimeDebugMode() {
        boolean z2;
        synchronized (f21202f) {
            z2 = f21203g;
        }
        return z2;
    }

    public static void turnOffRealTimeDebug() {
        synchronized (f21202f) {
            f21203g = false;
            f21204h = "";
        }
    }

    public static void turnOnRealTimeDebug(Map<String, String> map) {
        synchronized (f21202f) {
            try {
                f21203g = true;
                if (map != null && map.containsKey(DEBUG_KEY)) {
                    f21204h = map.get(DEBUG_KEY);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static void a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(l.A, 0, "\\|");
        } else {
            f21200d = str;
            com.umeng.common.b.a(context).a(f21200d);
        }
    }

    static void a(Context context, int i2) {
        f21201e = i2;
        com.umeng.common.b.a(context).a(f21201e);
    }
}
