package com.huawei.hms.support.hianalytics;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.huawei.hianalytics.process.HiAnalyticsManager;
import com.huawei.hianalytics.util.HiAnalyticTools;
import com.huawei.hms.hatool.HmsHiAnalyticsUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.AnalyticsSwitchHolder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HiAnalyticsUtils {

    /* renamed from: c, reason: collision with root package name */
    private static final Object f18116c = new Object();

    /* renamed from: d, reason: collision with root package name */
    private static final Object f18117d = new Object();

    /* renamed from: e, reason: collision with root package name */
    private static HiAnalyticsUtils f18118e;

    /* renamed from: a, reason: collision with root package name */
    private int f18119a = 0;

    /* renamed from: b, reason: collision with root package name */
    private final boolean f18120b = com.huawei.hms.stats.c.a();

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18121a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18122b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ Map f18123c;

        a(Context context, String str, Map map) {
            this.f18121a = context;
            this.f18122b = str;
            this.f18123c = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onEvent(this.f18121a, this.f18122b, this.f18123c);
        }
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18125a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18126b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f18127c;

        b(Context context, String str, String str2) {
            this.f18125a = context;
            this.f18126b = str;
            this.f18127c = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onEvent2(this.f18125a, this.f18126b, this.f18127c);
        }
    }

    class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18129a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18130b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ Map f18131c;

        c(Context context, String str, Map map) {
            this.f18129a = context;
            this.f18130b = str;
            this.f18131c = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onNewEvent(this.f18129a, this.f18130b, this.f18131c);
        }
    }

    class d implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18133a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18134b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ Map f18135c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ int f18136d;

        d(Context context, String str, Map map, int i2) {
            this.f18133a = context;
            this.f18134b = str;
            this.f18135c = map;
            this.f18136d = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onNewEvent(this.f18133a, this.f18134b, this.f18135c, this.f18136d);
        }
    }

    class e implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18138a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18139b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ Map f18140c;

        e(Context context, String str, Map map) {
            this.f18138a = context;
            this.f18139b = str;
            this.f18140c = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onReport(this.f18138a, this.f18139b, this.f18140c);
        }
    }

    class f implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f18142a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f18143b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ Map f18144c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ int f18145d;

        f(Context context, String str, Map map, int i2) {
            this.f18142a = context;
            this.f18143b = str;
            this.f18144c = map;
            this.f18145d = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            HiAnalyticsUtils.getInstance().onReport(this.f18142a, this.f18143b, this.f18144c, this.f18145d);
        }
    }

    private HiAnalyticsUtils() {
    }

    private static LinkedHashMap<String, String> a(Map<String, String> map) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    private void b(Context context) {
        synchronized (f18117d) {
            try {
                int i2 = this.f18119a;
                if (i2 < 60) {
                    this.f18119a = i2 + 1;
                } else {
                    this.f18119a = 0;
                    if (this.f18120b) {
                        com.huawei.hms.stats.b.a(context, 0);
                        com.huawei.hms.stats.b.a(context, 1);
                    } else {
                        HmsHiAnalyticsUtils.onReport();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void c(Context context, String str, Map map) {
        try {
            com.huawei.hms.stats.a.c().a(new e(context.getApplicationContext(), str, map));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnReportToCache> failed. " + th.getMessage());
        }
    }

    public static HiAnalyticsUtils getInstance() {
        HiAnalyticsUtils hiAnalyticsUtils;
        synchronized (f18116c) {
            try {
                if (f18118e == null) {
                    f18118e = new HiAnalyticsUtils();
                }
                hiAnalyticsUtils = f18118e;
            } catch (Throwable th) {
                throw th;
            }
        }
        return hiAnalyticsUtils;
    }

    public static String versionCodeToName(String str) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && (str.length() == 8 || str.length() == 9)) {
            try {
                Integer.parseInt(str);
                return Integer.parseInt(str.substring(0, str.length() - 7)) + "." + Integer.parseInt(str.substring(str.length() - 7, str.length() - 5)) + "." + Integer.parseInt(str.substring(str.length() - 5, str.length() - 3)) + "." + Integer.parseInt(str.substring(str.length() - 3));
            } catch (NumberFormatException unused) {
            }
        }
        return "";
    }

    public void enableLog(Context context) {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (this.f18120b) {
            HiAnalyticTools.enableLog(context);
        } else {
            HmsHiAnalyticsUtils.enableLog();
        }
    }

    public boolean getInitFlag() {
        return !this.f18120b ? HmsHiAnalyticsUtils.getInitFlag() : HiAnalyticsManager.getInitFlag(HiAnalyticsConstant.HA_SERVICE_TAG);
    }

    public int getOobeAnalyticsState(Context context) throws Settings.SettingNotFoundException {
        if (context == null) {
            return 0;
        }
        int iA = a(context);
        if (iA == 1) {
            return iA;
        }
        Bundle bundle = new Bundle();
        bundle.putString("hms_cp_bundle_key", "content://com.huawei.hms.contentprovider/com.huawei.hms.privacy.HmsAnalyticsStateProvider");
        try {
            Bundle bundleCall = context.getApplicationContext().getContentResolver().call(Uri.parse("content://com.huawei.hms.contentprovider"), "getAnalyticsState", (String) null, bundle);
            if (bundleCall == null) {
                return iA;
            }
            iA = bundleCall.getInt("SWITCH_IS_CHECKED");
            HMSLog.i("HiAnalyticsUtils", "get hms analyticsOobe state " + iA);
            return iA;
        } catch (IllegalArgumentException unused) {
            HMSLog.i("HiAnalyticsUtils", "getOobeAnalyticsState IllegalArgumentException ");
            return iA;
        } catch (SecurityException unused2) {
            HMSLog.i("HiAnalyticsUtils", "getOobeAnalyticsState SecurityException ");
            return iA;
        } catch (Exception unused3) {
            HMSLog.i("HiAnalyticsUtils", "getOobeAnalyticsState Exception ");
            return iA;
        }
    }

    public boolean hasError(Context context) {
        return AnalyticsSwitchHolder.isAnalyticsDisabled(context);
    }

    public void onBuoyEvent(Context context, String str, String str2) {
        onEvent2(context, str, str2);
    }

    public void onEvent(Context context, String str, Map<String, String> map) {
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (map == null || map.isEmpty() || context == null) {
            HMSLog.e("HiAnalyticsUtils", "<onEvent> map or context is null, state: " + andRefreshAnalyticsState);
            return;
        }
        boolean initFlag = getInitFlag();
        if (a(initFlag, andRefreshAnalyticsState != 2, map)) {
            a(context, str, map);
        }
        if (andRefreshAnalyticsState == 1 && initFlag) {
            if (this.f18120b) {
                com.huawei.hms.stats.b.a(context, 0, str, a(map));
                com.huawei.hms.stats.b.a(context, 1, str, a(map));
            } else {
                HmsHiAnalyticsUtils.onEvent(0, str, a(map));
                HmsHiAnalyticsUtils.onEvent(1, str, a(map));
            }
            b(context);
        }
    }

    public void onEvent2(Context context, String str, String str2) {
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (context == null) {
            HMSLog.e("HiAnalyticsUtils", "<onEvent2> context is null, state: " + andRefreshAnalyticsState);
            return;
        }
        boolean initFlag = getInitFlag();
        if (!initFlag && andRefreshAnalyticsState != 2 && a(str2)) {
            a(context, str, str2);
        }
        if (andRefreshAnalyticsState == 1 && initFlag) {
            if (this.f18120b) {
                com.huawei.hms.stats.b.a(context, str, str2);
            } else {
                HmsHiAnalyticsUtils.onEvent(context, str, str2);
            }
        }
    }

    public void onNewEvent(Context context, String str, Map map) {
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (map == null || map.isEmpty() || context == null) {
            HMSLog.e("HiAnalyticsUtils", "<onNewEvent> map or context is null, state: " + andRefreshAnalyticsState);
            return;
        }
        boolean initFlag = getInitFlag();
        if (a(initFlag, andRefreshAnalyticsState != 2, (Map<?, ?>) map)) {
            b(context, str, map);
        }
        if (andRefreshAnalyticsState == 1 && initFlag) {
            if (this.f18120b) {
                com.huawei.hms.stats.b.a(context, 0, str, a((Map<String, String>) map));
                com.huawei.hms.stats.b.a(context, 1, str, a((Map<String, String>) map));
            } else {
                HmsHiAnalyticsUtils.onEvent(0, str, a((Map<String, String>) map));
                HmsHiAnalyticsUtils.onEvent(1, str, a((Map<String, String>) map));
            }
            b(context);
        }
    }

    public void onReport(Context context, String str, Map map) {
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (map == null || map.isEmpty() || context == null) {
            HMSLog.e("HiAnalyticsUtils", "<onReport> map or context is null, state: " + andRefreshAnalyticsState);
            return;
        }
        boolean initFlag = getInitFlag();
        if (a(initFlag, andRefreshAnalyticsState != 2, (Map<?, ?>) map)) {
            c(context, str, map);
        }
        if (andRefreshAnalyticsState == 1 && initFlag) {
            if (this.f18120b) {
                com.huawei.hms.stats.b.b(context, 0, str, a((Map<String, String>) map));
                com.huawei.hms.stats.b.b(context, 1, str, a((Map<String, String>) map));
            } else {
                HmsHiAnalyticsUtils.onStreamEvent(0, str, a((Map<String, String>) map));
                HmsHiAnalyticsUtils.onStreamEvent(1, str, a((Map<String, String>) map));
            }
        }
    }

    public void enableLog() {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (!this.f18120b) {
            HmsHiAnalyticsUtils.enableLog();
        } else {
            HMSLog.i("HiAnalyticsUtils", "cp needs to pass in the context, this method is not supported");
        }
    }

    private int a(Context context) throws Settings.SettingNotFoundException {
        int i2 = 0;
        try {
            i2 = Settings.Secure.getInt(context.getContentResolver(), "hw_app_analytics_state");
            HMSLog.i("HiAnalyticsUtils", "getOobeStateForSettings value is " + i2);
            return i2;
        } catch (Settings.SettingNotFoundException e2) {
            HMSLog.i("HiAnalyticsUtils", "Settings.SettingNotFoundException " + e2.getMessage());
            return i2;
        }
    }

    private boolean a(boolean z2, boolean z3, Map<?, ?> map) {
        return !z2 && z3 && b(map);
    }

    private boolean b(Map<?, ?> map) {
        try {
            Iterator<?> it = map.values().iterator();
            long length = 0;
            while (it.hasNext()) {
                if (it.next() instanceof String) {
                    length += ((String) r3).getBytes(Charset.forName("UTF-8")).length;
                }
            }
            return length <= 512;
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<isValidSize map> Exception: " + th.getMessage());
            return false;
        }
    }

    private boolean a(String str) {
        if (str == null) {
            return false;
        }
        try {
            return str.getBytes(Charset.forName("UTF-8")).length <= 512;
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<isValidSize String> Exception: " + th.getMessage());
            return false;
        }
    }

    public void onReport(Context context, String str, Map map, int i2) {
        if (i2 != 0 && i2 != 1) {
            HMSLog.e("HiAnalyticsUtils", "<onReport with type> Data reporting type is not supported");
            return;
        }
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (map != null && !map.isEmpty() && context != null) {
            boolean initFlag = getInitFlag();
            if (a(initFlag, andRefreshAnalyticsState != 2, (Map<?, ?>) map)) {
                b(context, str, map, i2);
            }
            if (andRefreshAnalyticsState == 1 && initFlag) {
                if (!this.f18120b) {
                    HmsHiAnalyticsUtils.onStreamEvent(i2, str, a((Map<String, String>) map));
                    return;
                } else {
                    com.huawei.hms.stats.b.b(context, i2, str, a((Map<String, String>) map));
                    return;
                }
            }
            return;
        }
        HMSLog.e("HiAnalyticsUtils", "<onReport with type> map or context is null, state: " + andRefreshAnalyticsState);
    }

    private void a(Context context, String str, Map<String, String> map) {
        try {
            com.huawei.hms.stats.a.c().a(new a(context.getApplicationContext(), str, map));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnEventToCache> failed. " + th.getMessage());
        }
    }

    public void onNewEvent(Context context, String str, Map map, int i2) {
        if (i2 != 0 && i2 != 1) {
            HMSLog.e("HiAnalyticsUtils", "<onNewEvent with type> Data reporting type is not supported");
            return;
        }
        int andRefreshAnalyticsState = AnalyticsSwitchHolder.getAndRefreshAnalyticsState(context);
        if (map != null && !map.isEmpty() && context != null) {
            boolean initFlag = getInitFlag();
            if (a(initFlag, andRefreshAnalyticsState != 2, (Map<?, ?>) map)) {
                a(context, str, map, i2);
            }
            if (andRefreshAnalyticsState == 1 && initFlag) {
                if (!this.f18120b) {
                    HmsHiAnalyticsUtils.onEvent(i2, str, a((Map<String, String>) map));
                } else {
                    com.huawei.hms.stats.b.a(context, i2, str, a((Map<String, String>) map));
                }
                b(context);
                return;
            }
            return;
        }
        HMSLog.e("HiAnalyticsUtils", "<onNewEvent with type> map or context is null, state: " + andRefreshAnalyticsState);
    }

    private void b(Context context, String str, Map map) {
        try {
            com.huawei.hms.stats.a.c().a(new c(context.getApplicationContext(), str, map));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnNewEventToCache> failed. " + th.getMessage());
        }
    }

    private void a(Context context, String str, String str2) {
        try {
            com.huawei.hms.stats.a.c().a(new b(context.getApplicationContext(), str, str2));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnEvent2ToCache> Failed. " + th.getMessage());
        }
    }

    private void b(Context context, String str, Map map, int i2) {
        try {
            com.huawei.hms.stats.a.c().a(new f(context.getApplicationContext(), str, map, i2));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnReportToCache with type> failed. " + th.getMessage());
        }
    }

    private void a(Context context, String str, Map map, int i2) {
        try {
            com.huawei.hms.stats.a.c().a(new d(context.getApplicationContext(), str, map, i2));
        } catch (Throwable th) {
            HMSLog.e("HiAnalyticsUtils", "<addOnNewEventToCache with type> failed. " + th.getMessage());
        }
    }
}
