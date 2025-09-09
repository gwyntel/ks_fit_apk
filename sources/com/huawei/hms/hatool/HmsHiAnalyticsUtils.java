package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public class HmsHiAnalyticsUtils {
    public static void enableLog() {
        c.a();
    }

    public static boolean getInitFlag() {
        return a.b();
    }

    public static void init(Context context, boolean z2, boolean z3, boolean z4, String str, String str2) {
        new b(context).a(z2).c(z3).b(z4).a(0, str).a(1, str).a(str2).a();
    }

    public static void onEvent(Context context, String str, String str2) {
        a.a(context, str, str2);
    }

    public static void onReport() {
        a.c();
    }

    public static void onStreamEvent(int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        a.b(i2, str, linkedHashMap);
    }

    public static void onEvent(int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        a.a(i2, str, linkedHashMap);
    }
}
