package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes2.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static ScheduledExecutorService f8861a;

    /* renamed from: a, reason: collision with other field name */
    private static final ThreadFactory f22a = new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.j.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("httpdns worker");
            thread.setDaemon(false);
            thread.setUncaughtExceptionHandler(new k());
            return thread;
        }
    };

    public static synchronized ScheduledExecutorService a() {
        try {
            if (f8861a == null) {
                f8861a = new ScheduledThreadPoolExecutor(1, f22a);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f8861a;
    }
}
