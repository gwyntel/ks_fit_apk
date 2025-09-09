package com.huawei.hms.stats;

import android.content.Context;
import com.huawei.hianalytics.process.HiAnalyticsInstance;
import com.huawei.hms.utils.HMSBIInitializer;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static HiAnalyticsInstance f18080a;

    private static HiAnalyticsInstance a(Context context) {
        HiAnalyticsInstance analyticsInstance = HMSBIInitializer.getInstance(context).getAnalyticsInstance();
        f18080a = analyticsInstance;
        return analyticsInstance;
    }

    public static void b(Context context, int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a(context) != null) {
            f18080a.onStreamEvent(i2, str, linkedHashMap);
        }
    }

    public static void a(Context context, String str, String str2) {
        if (a(context) != null) {
            f18080a.onEvent(context, str, str2);
        }
    }

    public static void a(Context context, int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a(context) != null) {
            f18080a.onEvent(i2, str, linkedHashMap);
        }
    }

    public static void a(Context context, int i2) {
        if (a(context) != null) {
            f18080a.onReport(i2);
        }
    }
}
