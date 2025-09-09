package com.umeng.analytics.pro;

import com.umeng.commonsdk.debug.UMRTLog;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class av {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21362a = "UMExecutor";

    /* renamed from: b, reason: collision with root package name */
    private static volatile ScheduledThreadPoolExecutor f21363b;

    /* renamed from: c, reason: collision with root package name */
    private static final ThreadFactory f21364c = new ThreadFactory() { // from class: com.umeng.analytics.pro.av.1

        /* renamed from: a, reason: collision with root package name */
        private final AtomicInteger f21365a = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "ccg-" + this.f21365a.incrementAndGet());
        }
    };

    private static ScheduledThreadPoolExecutor a() {
        if (f21363b == null) {
            synchronized (av.class) {
                try {
                    if (f21363b == null) {
                        f21363b = new ScheduledThreadPoolExecutor(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors(), 4)), f21364c);
                        f21363b.setKeepAliveTime(3L, TimeUnit.SECONDS);
                        f21363b.allowCoreThreadTimeOut(true);
                    }
                } finally {
                }
            }
        }
        return f21363b;
    }

    public static void a(Runnable runnable, long j2, TimeUnit timeUnit) {
        try {
            a().schedule(runnable, j2, timeUnit);
        } catch (Throwable th) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "schedule error:" + th.getMessage());
        }
    }
}
