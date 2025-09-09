package com.alibaba.sdk.android.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static boolean f8944b = false;

    public static void a(String str, String str2) {
        if (f8944b) {
            Log.d(str, str2);
        }
    }

    public static void setLogEnabled(boolean z2) {
        f8944b = z2;
    }
}
