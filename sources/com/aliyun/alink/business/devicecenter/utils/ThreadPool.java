package com.aliyun.alink.business.devicecenter.utils;

import com.aliyun.alink.business.devicecenter.log.ALog;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ThreadPool {

    /* renamed from: a, reason: collision with root package name */
    public static ExecutorService f10655a;

    /* renamed from: b, reason: collision with root package name */
    public static ScheduledThreadPoolExecutor f10656b;

    public static class DefaultThreadFactory implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        public final AtomicInteger f10657a;

        /* renamed from: b, reason: collision with root package name */
        public final ThreadGroup f10658b;

        /* renamed from: c, reason: collision with root package name */
        public final AtomicInteger f10659c;

        /* renamed from: d, reason: collision with root package name */
        public final String f10660d;

        public DefaultThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            this.f10657a = atomicInteger;
            this.f10659c = new AtomicInteger(1);
            SecurityManager securityManager = System.getSecurityManager();
            this.f10658b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f10660d = "Shared-" + atomicInteger.getAndIncrement() + "-t-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.f10658b, runnable, this.f10660d + this.f10659c.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            return thread;
        }
    }

    public static void a() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        ALog.d("ThreadPool", "core maybe <0 which will cause crash in specific platform: " + iAvailableProcessors);
        if (iAvailableProcessors <= 0) {
            iAvailableProcessors = 1;
        }
        int iMax = Math.max(4, iAvailableProcessors);
        int iMin = Math.min(10, iAvailableProcessors * 2);
        if (iMin < iMax) {
            iMax = iMin;
        }
        ALog.d("ThreadPool", "Start a ThreadPool with scale between " + iMax + " -> " + iMin + "and core:" + iAvailableProcessors);
        f10655a = new ThreadPoolExecutor(iMax, iMin, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DefaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        f10656b = new ScheduledThreadPoolExecutor(iMax, new DefaultThreadFactory());
    }

    public static void execute(Runnable runnable) {
        if (f10655a == null) {
            a();
        }
        f10655a.execute(runnable);
    }

    public static <T> Future<T> schedule(Callable<T> callable, long j2, TimeUnit timeUnit) {
        if (f10656b == null) {
            a();
        }
        return f10656b.schedule(callable, j2, timeUnit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j2, long j3, TimeUnit timeUnit) {
        if (f10656b == null) {
            a();
        }
        return f10656b.scheduleAtFixedRate(runnable, j2, j3, timeUnit);
    }

    public static Future<?> submit(Runnable runnable) {
        if (f10655a == null) {
            a();
        }
        return f10655a.submit(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        if (f10655a == null) {
            a();
        }
        return f10655a.submit(callable);
    }
}
