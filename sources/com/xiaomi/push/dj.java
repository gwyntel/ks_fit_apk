package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class dj {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f23579a;

    public static void a(String str, String str2) {
    }

    public static boolean a(Context context) {
        if (f23579a == null) {
            if (!j.m550a(context)) {
                f23579a = Boolean.FALSE;
            }
            String strM803a = com.xiaomi.push.service.v.m803a(context);
            if (TextUtils.isEmpty(strM803a) || strM803a.length() < 3) {
                f23579a = Boolean.FALSE;
            } else {
                String strSubstring = strM803a.substring(strM803a.length() - 3);
                a("shouldSampling uuid suffix = " + strSubstring);
                f23579a = Boolean.valueOf(TextUtils.equals(strSubstring, "001"));
            }
            a("shouldSampling = " + f23579a);
        }
        return f23579a.booleanValue();
    }

    static void a(String str) {
        a("Push-ConnectionQualityStatsHelper", str);
    }
}
