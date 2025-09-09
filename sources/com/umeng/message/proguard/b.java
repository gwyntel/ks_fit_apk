package com.umeng.message.proguard;

import com.umeng.message.common.UPLog;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ScheduledThreadPoolExecutor f22813a;

    /* renamed from: b, reason: collision with root package name */
    private static volatile ExecutorService f22814b;

    /* renamed from: c, reason: collision with root package name */
    private static volatile ExecutorService f22815c;

    static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private final Runnable f22816a;

        public a(Runnable runnable) {
            this.f22816a = runnable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                Runnable runnable = this.f22816a;
                if (runnable != null) {
                    runnable.run();
                }
            } catch (Throwable th) {
                UPLog.e("Executors", th);
            }
        }
    }

    /* renamed from: com.umeng.message.proguard.b$b, reason: collision with other inner class name */
    static final class ThreadFactoryC0187b implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        private final AtomicInteger f22817a = new AtomicInteger();

        /* renamed from: b, reason: collision with root package name */
        private final String f22818b;

        ThreadFactoryC0187b(String str) {
            this.f22818b = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, this.f22818b + " " + this.f22817a.incrementAndGet());
        }
    }

    private static ScheduledThreadPoolExecutor a() {
        if (f22813a == null) {
            synchronized (b.class) {
                try {
                    if (f22813a == null) {
                        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors(), 4)), new ThreadFactoryC0187b("pool"));
                        f22813a = scheduledThreadPoolExecutor;
                        scheduledThreadPoolExecutor.setKeepAliveTime(3L, TimeUnit.SECONDS);
                        f22813a.allowCoreThreadTimeOut(true);
                    }
                } finally {
                }
            }
        }
        return f22813a;
    }

    private static ExecutorService b() {
        if (f22814b == null) {
            synchronized (b.class) {
                try {
                    if (f22814b == null) {
                        f22814b = Executors.newSingleThreadExecutor(new ThreadFactoryC0187b("single"));
                    }
                } finally {
                }
            }
        }
        return f22814b;
    }

    private static ExecutorService c() {
        if (f22815c == null) {
            synchronized (b.class) {
                try {
                    if (f22815c == null) {
                        f22815c = Executors.newSingleThreadExecutor(new ThreadFactoryC0187b("msg"));
                    }
                } finally {
                }
            }
        }
        return f22815c;
    }

    private static Runnable d(Runnable runnable) {
        return new a(runnable);
    }

    public static Future<?> b(Runnable runnable) {
        try {
            return b().submit(d(runnable));
        } catch (Throwable th) {
            UPLog.e("Executors", th);
            return null;
        }
    }

    public static void c(Runnable runnable) {
        try {
            a().execute(d(runnable));
        } catch (Throwable th) {
            UPLog.e("Executors", th);
        }
    }

    public static void a(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        try {
            c().execute(d(runnable));
        } catch (Throwable th) {
            UPLog.e("Executors", th);
        }
    }

    public static ScheduledFuture<?> a(Runnable runnable, long j2, TimeUnit timeUnit) {
        try {
            return a().schedule(d(runnable), j2, timeUnit);
        } catch (Throwable th) {
            UPLog.e("Executors", th);
            return null;
        }
    }
}
