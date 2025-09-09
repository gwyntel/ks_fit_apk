package com.alipay.sdk.m.u;

import android.os.SystemClock;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final long f9757a = 3000;

    /* renamed from: b, reason: collision with root package name */
    public static long f9758b = -1;

    public static synchronized boolean a() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - f9758b < 3000) {
            return true;
        }
        f9758b = jElapsedRealtime;
        return false;
    }
}
