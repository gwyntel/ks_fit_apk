package com.huawei.hms.stats;

import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f18081a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f18082b = false;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f18083c = false;

    public static boolean a() {
        synchronized (f18081a) {
            if (!f18082b) {
                try {
                    Class.forName("com.huawei.hianalytics.process.HiAnalyticsInstance");
                } catch (ClassNotFoundException unused) {
                    HMSLog.i("HianalyticsExist", "In isHianalyticsExist, Failed to find class HiAnalyticsConfig.");
                }
                f18082b = true;
                HMSLog.i("HianalyticsExist", "hianalytics exist: " + f18083c);
            }
        }
        return f18083c;
    }
}
