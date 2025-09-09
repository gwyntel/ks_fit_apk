package anet.channel.strategy.dispatch;

import android.content.Context;
import anet.channel.util.ALog;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AmdcRuntimeInfo {
    private static final String TAG = "awcn.AmdcRuntimeInfo";
    private static volatile int amdcLimitLevel = 0;
    private static volatile long amdcLimitTime = 0;
    public static volatile String appChannel = null;
    public static volatile String appName = null;
    public static volatile String appVersion = null;
    private static volatile Context context = null;
    private static volatile boolean forceHttps = false;
    private static IAmdcSign iSign;
    public static volatile double latitude;
    public static volatile double longitude;
    private static Map<String, String> params;

    public static int getAmdcLimitLevel() {
        if (amdcLimitLevel > 0 && System.currentTimeMillis() - amdcLimitTime > 0) {
            amdcLimitTime = 0L;
            amdcLimitLevel = 0;
        }
        return amdcLimitLevel;
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized Map<String, String> getParams() {
        if (params == null) {
            return Collections.EMPTY_MAP;
        }
        return new HashMap(params);
    }

    public static IAmdcSign getSign() {
        return iSign;
    }

    public static boolean isForceHttps() {
        return forceHttps;
    }

    public static void setAppInfo(String str, String str2, String str3) {
        appName = str;
        appVersion = str2;
        appChannel = str3;
    }

    public static void setContext(Context context2) {
        context = context2;
    }

    public static void setForceHttps(boolean z2) {
        forceHttps = z2;
    }

    public static synchronized void setParam(String str, String str2) {
        try {
            if (params == null) {
                params = new HashMap();
            }
            params.put(str, str2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void setSign(IAmdcSign iAmdcSign) {
        iSign = iAmdcSign;
    }

    public static void updateAmdcLimit(int i2, int i3) {
        ALog.i(TAG, "set amdc limit", null, FirebaseAnalytics.Param.LEVEL, Integer.valueOf(i2), "time", Integer.valueOf(i3));
        if (i2 < 0 || i2 > 3) {
            return;
        }
        amdcLimitLevel = i2;
        amdcLimitTime = System.currentTimeMillis() + (i3 * 1000);
    }

    public static void updateLocation(double d2, double d3) {
        latitude = d2;
        longitude = d3;
    }
}
