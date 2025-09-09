package org.android.spdy;

import android.util.Log;

/* loaded from: classes5.dex */
public class spduLog {
    private static long savedTraffic;

    public static void Logd(String str, String str2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.d(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void Loge(String str, String str2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.e(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void Logf(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        Log.d(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void Logi(String str, String str2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.i(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void Logv(String str, String str2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.v(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void Logw(String str, String str2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.w(str, Thread.currentThread().getId() + " - " + str2);
    }

    public static void addTraffic(long j2) {
        savedTraffic += j2;
    }

    public static long getSavedTraffic() {
        return savedTraffic;
    }

    public static long now() {
        if (SpdyAgent.enableDebug) {
            return System.nanoTime();
        }
        return 0L;
    }

    public static void Logd(String str, String str2, long j2) {
        if (!SpdyAgent.enableDebug || str == null || str2 == null) {
            return;
        }
        Log.i(str, Thread.currentThread().getId() + " - " + str2 + ((System.nanoTime() - j2) / 1000000));
    }
}
