package com.alibaba.ailabs.iot.aisbase.utils;

import com.alibaba.ailabs.tg.utils.LogUtils;
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
    public static ExecutorService f8582a;

    /* renamed from: b, reason: collision with root package name */
    public static ScheduledThreadPoolExecutor f8583b;

    public static class DefaultThreadFactory implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        public final AtomicInteger f8584a;

        /* renamed from: b, reason: collision with root package name */
        public final ThreadGroup f8585b;

        /* renamed from: c, reason: collision with root package name */
        public final AtomicInteger f8586c;

        /* renamed from: d, reason: collision with root package name */
        public final String f8587d;

        public DefaultThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            this.f8584a = atomicInteger;
            this.f8586c = new AtomicInteger(1);
            SecurityManager securityManager = System.getSecurityManager();
            this.f8585b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f8587d = "AIoTShared-" + atomicInteger.getAndIncrement() + "-t-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.f8585b, runnable, this.f8587d + this.f8586c.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            return thread;
        }
    }

    public static void a() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        LogUtils.d("[aiot]ThreadPool", "ThreadPoolcore maybe <0 which will cause crash in specific platform: " + iAvailableProcessors);
        if (iAvailableProcessors <= 0) {
            iAvailableProcessors = 1;
        }
        int iMax = Math.max(4, iAvailableProcessors);
        int iMin = Math.min(10, iAvailableProcessors * 2);
        if (iMin < iMax) {
            iMax = iMin;
        }
        LogUtils.d("[aiot]ThreadPool", "ThreadPool Start a ThreadPool with scale between " + iMax + " -> " + iMin + "and core:" + iAvailableProcessors);
        f8582a = new ThreadPoolExecutor(iMax, iMin, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DefaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        f8583b = new ScheduledThreadPoolExecutor(iMax, new DefaultThreadFactory());
    }

    public static void execute(Runnable runnable) {
        if (f8582a == null) {
            a();
        }
        f8582a.execute(runnable);
    }

    public static <T> Future<T> schedule(Callable<T> callable, long j2, TimeUnit timeUnit) {
        if (f8583b == null) {
            a();
        }
        return f8583b.schedule(callable, j2, timeUnit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j2, long j3, TimeUnit timeUnit) {
        if (f8583b == null) {
            a();
        }
        return f8583b.scheduleAtFixedRate(runnable, j2, j3, timeUnit);
    }

    public static Future<?> submit(Runnable runnable) {
        if (f8582a == null) {
            a();
        }
        return f8582a.submit(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        if (f8582a == null) {
            a();
        }
        return f8582a.submit(callable);
    }
}
