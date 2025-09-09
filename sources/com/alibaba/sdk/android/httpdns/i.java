package com.alibaba.sdk.android.httpdns;

import android.util.Log;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static ILogger f8859a = null;

    /* renamed from: c, reason: collision with root package name */
    private static int f8860c = -1;

    /* renamed from: c, reason: collision with other field name */
    private static boolean f21c = false;

    public static void a(Throwable th) {
        if (!f21c || th == null) {
            return;
        }
        th.printStackTrace();
    }

    private static String c() {
        try {
            if (f8860c == -1) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                int length = stackTrace.length;
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (stackTrace[i2].getMethodName().equals("getTraceInfo")) {
                        f8860c = i3 + 1;
                        break;
                    }
                    i3++;
                    i2++;
                }
            }
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[f8860c + 1];
            return stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " - [" + stackTraceElement.getMethodName() + "]";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static void d(String str) {
        if (f21c && str != null) {
            Log.d("HttpDnsSDK", Thread.currentThread().getId() + " - " + c() + " - " + str);
        }
        ILogger iLogger = f8859a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    public static void e(String str) {
        if (f21c && str != null) {
            Log.i("HttpDnsSDK", Thread.currentThread().getId() + " - " + c() + " - " + str);
        }
        ILogger iLogger = f8859a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    public static void f(String str) {
        if (f21c && str != null) {
            Log.e("HttpDnsSDK", Thread.currentThread().getId() + " - " + c() + " - " + str);
        }
        ILogger iLogger = f8859a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    static synchronized void setLogEnabled(boolean z2) {
        f21c = z2;
    }

    static void setLogger(ILogger iLogger) {
        f8859a = iLogger;
    }
}
