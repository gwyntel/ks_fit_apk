package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final ExecutorService f8808a;

    /* renamed from: a, reason: collision with other field name */
    private static final ThreadFactory f9a;

    /* renamed from: a, reason: collision with other field name */
    private static final TimeUnit f10a;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        f10a = timeUnit;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.c.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("httpdns worker");
                thread.setDaemon(false);
                thread.setUncaughtExceptionHandler(new k());
                return thread;
            }
        };
        f9a = threadFactory;
        f8808a = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, timeUnit, new SynchronousQueue(), threadFactory);
    }

    public static ExecutorService a() {
        return f8808a;
    }
}
