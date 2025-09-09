package anet.channel.strategy.utils;

import anet.channel.util.ALog;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicInteger f7058a = new AtomicInteger(0);

    /* renamed from: b, reason: collision with root package name */
    private static ScheduledThreadPoolExecutor f7059b = null;

    static ScheduledThreadPoolExecutor a() {
        if (f7059b == null) {
            synchronized (a.class) {
                try {
                    if (f7059b == null) {
                        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2, new b());
                        f7059b = scheduledThreadPoolExecutor;
                        scheduledThreadPoolExecutor.setKeepAliveTime(60L, TimeUnit.SECONDS);
                        f7059b.allowCoreThreadTimeOut(true);
                    }
                } finally {
                }
            }
        }
        return f7059b;
    }

    public static void a(Runnable runnable) {
        try {
            a().submit(runnable);
        } catch (Exception e2) {
            ALog.e(anet.channel.strategy.dispatch.a.TAG, "submit task failed", null, e2, new Object[0]);
        }
    }

    public static void a(Runnable runnable, long j2) {
        try {
            a().schedule(runnable, j2, TimeUnit.MILLISECONDS);
        } catch (Exception e2) {
            ALog.e(anet.channel.strategy.dispatch.a.TAG, "schedule task failed", null, e2, new Object[0]);
        }
    }
}
