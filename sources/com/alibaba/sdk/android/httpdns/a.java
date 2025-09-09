package com.alibaba.sdk.android.httpdns;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
class a {

    /* renamed from: a, reason: collision with root package name */
    private static long f8789a;
    private static String sSecretKey;

    static String a(String str, String str2) {
        if (!l.b(str)) {
            return "";
        }
        try {
            return l.a(str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + sSecretKey + Constants.ACCEPT_TIME_SEPARATOR_SERVER + str2);
        } catch (Exception unused) {
            return "";
        }
    }

    static String getTimestamp() {
        return String.valueOf((System.currentTimeMillis() / 1000) + f8789a + 600);
    }

    static void setAuthCurrentTime(long j2) {
        f8789a = j2 - (System.currentTimeMillis() / 1000);
    }

    static void setSecretKey(String str) {
        sSecretKey = str;
    }

    static boolean a() {
        return !TextUtils.isEmpty(sSecretKey);
    }
}
