package com.huawei.hms.hatool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class b0 {

    /* renamed from: b, reason: collision with root package name */
    private static b0 f16325b;

    /* renamed from: c, reason: collision with root package name */
    private static b0 f16326c;

    /* renamed from: d, reason: collision with root package name */
    private static b0 f16327d;

    /* renamed from: a, reason: collision with root package name */
    private ThreadPoolExecutor f16328a = new ThreadPoolExecutor(0, 1, 60000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(5000), new b());

    private static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Runnable f16329a;

        public a(Runnable runnable) {
            this.f16329a = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.f16329a;
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Exception unused) {
                    v.e("hmsSdk", "InnerTask : Exception has happened,From internal operations!");
                }
            }
        }
    }

    static class b implements ThreadFactory {

        /* renamed from: d, reason: collision with root package name */
        private static final AtomicInteger f16330d = new AtomicInteger(1);

        /* renamed from: a, reason: collision with root package name */
        private final ThreadGroup f16331a;

        /* renamed from: b, reason: collision with root package name */
        private final AtomicInteger f16332b = new AtomicInteger(1);

        /* renamed from: c, reason: collision with root package name */
        private final String f16333c;

        b() {
            SecurityManager securityManager = System.getSecurityManager();
            this.f16331a = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f16333c = "FormalHASDK-base-" + f16330d.getAndIncrement();
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(this.f16331a, runnable, this.f16333c + this.f16332b.getAndIncrement(), 0L);
        }
    }

    static {
        new b0();
        new b0();
        f16325b = new b0();
        f16326c = new b0();
        f16327d = new b0();
    }

    private b0() {
    }

    public static b0 a() {
        return f16327d;
    }

    public static b0 b() {
        return f16326c;
    }

    public static b0 c() {
        return f16325b;
    }

    public void a(g gVar) {
        try {
            this.f16328a.execute(new a(gVar));
        } catch (RejectedExecutionException unused) {
            v.e("hmsSdk", "addToQueue() Exception has happened!Form rejected execution");
        }
    }
}
