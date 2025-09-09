package anet.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.entity.ENV;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import anet.channel.util.Utils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class GlobalAppRuntimeInfo {

    /* renamed from: a, reason: collision with root package name */
    private static Context f6604a;

    /* renamed from: e, reason: collision with root package name */
    private static String f6608e;

    /* renamed from: f, reason: collision with root package name */
    private static String f6609f;

    /* renamed from: g, reason: collision with root package name */
    private static String f6610g;

    /* renamed from: k, reason: collision with root package name */
    private static volatile long f6614k;

    /* renamed from: l, reason: collision with root package name */
    private static String f6615l;

    /* renamed from: b, reason: collision with root package name */
    private static ENV f6605b = ENV.ONLINE;

    /* renamed from: c, reason: collision with root package name */
    private static String f6606c = "";

    /* renamed from: d, reason: collision with root package name */
    private static String f6607d = "";

    /* renamed from: h, reason: collision with root package name */
    private static volatile boolean f6611h = true;

    /* renamed from: i, reason: collision with root package name */
    private static SharedPreferences f6612i = null;

    /* renamed from: j, reason: collision with root package name */
    private static volatile CopyOnWriteArrayList<String> f6613j = null;

    public static void addBucketInfo(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str.length() > 32 || str2.length() > 32) {
            return;
        }
        synchronized (GlobalAppRuntimeInfo.class) {
            try {
                if (f6613j == null) {
                    f6613j = new CopyOnWriteArrayList<>();
                }
                f6613j.add(str);
                f6613j.add(str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static CopyOnWriteArrayList<String> getBucketInfo() {
        return f6613j;
    }

    public static Context getContext() {
        return f6604a;
    }

    public static String getCurrentProcess() {
        return f6607d;
    }

    public static ENV getEnv() {
        return f6605b;
    }

    @Deprecated
    public static long getInitTime() {
        return f6614k;
    }

    @Deprecated
    public static int getStartType() {
        anet.channel.fulltrace.b sceneInfo = anet.channel.fulltrace.a.a().getSceneInfo();
        if (sceneInfo != null) {
            return sceneInfo.f6786a;
        }
        return -1;
    }

    public static String getTtid() {
        return f6608e;
    }

    public static String getUserId() {
        return f6609f;
    }

    public static String getUtdid() {
        Context context;
        if (f6610g == null && (context = f6604a) != null) {
            f6610g = Utils.getDeviceId(context);
        }
        return f6610g;
    }

    public static boolean isAppBackground() {
        if (f6604a == null) {
            return true;
        }
        return f6611h;
    }

    public static boolean isTargetProcess() {
        if (TextUtils.isEmpty(f6606c) || TextUtils.isEmpty(f6607d)) {
            return true;
        }
        return f6606c.equalsIgnoreCase(f6607d);
    }

    public static void setBackground(boolean z2) {
        f6611h = z2;
    }

    public static void setContext(Context context) {
        f6604a = context;
        if (context != null) {
            if (TextUtils.isEmpty(f6607d)) {
                f6607d = Utils.getProcessName(context, Process.myPid());
            }
            if (TextUtils.isEmpty(f6606c)) {
                f6606c = Utils.getMainProcessName(context);
            }
            if (f6612i == null) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                f6612i = defaultSharedPreferences;
                f6609f = defaultSharedPreferences.getString("UserId", null);
            }
            ALog.e("awcn.GlobalAppRuntimeInfo", "", null, "CurrentProcess", f6607d, "TargetProcess", f6606c);
        }
    }

    public static void setCurrentProcess(String str) {
        f6607d = str;
    }

    public static void setEnv(ENV env) {
        f6605b = env;
    }

    @Deprecated
    public static void setInitTime(long j2) {
        f6614k = j2;
    }

    public static void setTargetProcess(String str) {
        f6606c = str;
    }

    public static void setTtid(String str) {
        f6608e = str;
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            int iIndexOf = str.indexOf("@");
            String strSubstring = null;
            String strSubstring2 = iIndexOf != -1 ? str.substring(0, iIndexOf) : null;
            String strSubstring3 = str.substring(iIndexOf + 1);
            int iLastIndexOf = strSubstring3.lastIndexOf(OpenAccountUIConstants.UNDER_LINE);
            if (iLastIndexOf != -1) {
                String strSubstring4 = strSubstring3.substring(0, iLastIndexOf);
                strSubstring = strSubstring3.substring(iLastIndexOf + 1);
                strSubstring3 = strSubstring4;
            }
            f6615l = strSubstring;
            AmdcRuntimeInfo.setAppInfo(strSubstring3, strSubstring, strSubstring2);
        } catch (Exception unused) {
        }
    }

    public static void setUserId(String str) {
        String str2 = f6609f;
        if (str2 == null || !str2.equals(str)) {
            f6609f = str;
            StrategyCenter.getInstance().forceRefreshStrategy(DispatchConstants.getAmdcServerDomain());
            SharedPreferences sharedPreferences = f6612i;
            if (sharedPreferences != null) {
                sharedPreferences.edit().putString("UserId", str).apply();
            }
        }
    }

    public static void setUtdid(String str) {
        String str2 = f6610g;
        if (str2 == null || !str2.equals(str)) {
            f6610g = str;
        }
    }

    public static boolean isTargetProcess(String str) {
        if (TextUtils.isEmpty(f6606c) || TextUtils.isEmpty(str)) {
            return true;
        }
        return f6606c.equalsIgnoreCase(str);
    }
}
