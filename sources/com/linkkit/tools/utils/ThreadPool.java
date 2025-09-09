package com.linkkit.tools.utils;

import com.linkkit.tools.a;
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

/* loaded from: classes4.dex */
public class ThreadPool {
    private static final String TAG = "ThreadPool";
    private static ExecutorService executorService;
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger poolNumber;
        private final AtomicInteger threadNumber;

        public DefaultThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            this.poolNumber = atomicInteger;
            this.threadNumber = new AtomicInteger(1);
            SecurityManager securityManager = System.getSecurityManager();
            this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = "TPE-" + atomicInteger.getAndIncrement() + "-t-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            return thread;
        }
    }

    public static void execute(Runnable runnable) {
        if (executorService == null) {
            init();
        }
        executorService.execute(runnable);
    }

    private static void init() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        a.a(TAG, "core maybe <0 which will cause crash in specific platform: " + iAvailableProcessors);
        if (iAvailableProcessors <= 0) {
            iAvailableProcessors = 1;
        }
        int iMax = Math.max(4, iAvailableProcessors);
        int iMin = Math.min(10, iAvailableProcessors * 2);
        if (iMin < iMax) {
            iMax = iMin;
        }
        a.a(TAG, "Start a ThreadPool with scale between " + iMax + " -> " + iMin + "and core:" + iAvailableProcessors);
        executorService = new ThreadPoolExecutor(iMax, iMin, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DefaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(iMax, new DefaultThreadFactory());
    }

    public static <T> Future<T> schedule(Callable<T> callable, long j2, TimeUnit timeUnit) {
        if (scheduledThreadPoolExecutor == null) {
            init();
        }
        return scheduledThreadPoolExecutor.schedule(callable, j2, timeUnit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j2, long j3, TimeUnit timeUnit) {
        if (scheduledThreadPoolExecutor == null) {
            init();
        }
        return scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, j2, j3, timeUnit);
    }

    public static Future<?> submit(Runnable runnable) {
        if (executorService == null) {
            init();
        }
        return executorService.submit(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        if (executorService == null) {
            init();
        }
        return executorService.submit(callable);
    }
}
