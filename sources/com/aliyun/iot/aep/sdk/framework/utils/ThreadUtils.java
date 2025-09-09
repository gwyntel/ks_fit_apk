package com.aliyun.iot.aep.sdk.framework.utils;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes3.dex */
public class ThreadUtils {

    /* renamed from: a, reason: collision with root package name */
    private static Handler f11836a;

    public static void remove(Runnable runnable) {
        if (f11836a == null) {
            f11836a = new Handler(Looper.getMainLooper());
        }
        try {
            f11836a.removeCallbacks(runnable);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void retryInMain(Runnable runnable, long j2) {
        if (f11836a == null) {
            f11836a = new Handler(Looper.getMainLooper());
        }
        try {
            f11836a.removeCallbacks(runnable);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            f11836a.postDelayed(runnable, j2);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
