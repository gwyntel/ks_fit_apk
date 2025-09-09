package com.alibaba.ailabs.tg.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ThreadPoolExecutorFactory {
    private static int CORE_POOL_SIZE = 3;
    private static int KEEP_ALIVE_TIME = 60;
    private static int MAX_POOL_SIZE = 5;
    private static final int QUEENCOUNT = 30;
    private static final AtomicInteger integer = new AtomicInteger();
    private static int prop = 5;
    private static ThreadPoolExecutor threadPoolExecutor;

    static class TBThreadFactory implements ThreadFactory {
        private int priority;

        public TBThreadFactory(int i2) {
            this.priority = i2;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "TgTask:" + ThreadPoolExecutorFactory.integer.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    public static ThreadPoolExecutor createExecutor(int i2, int i3, int i4, int i5, int i6) {
        return new ThreadPoolExecutor(i3, i4, i5, TimeUnit.SECONDS, i6 > 0 ? new LinkedBlockingQueue(i6) : new LinkedBlockingQueue(), new TBThreadFactory(i2), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public static synchronized ThreadPoolExecutor getDefaulThreadPoolExecutor() {
        try {
            if (threadPoolExecutor == null) {
                int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
                int i2 = (iAvailableProcessors * 2) - 1;
                if (iAvailableProcessors < 4) {
                    iAvailableProcessors = CORE_POOL_SIZE;
                    i2 = MAX_POOL_SIZE;
                }
                threadPoolExecutor = createExecutor(prop, iAvailableProcessors, i2, KEEP_ALIVE_TIME, 30);
            }
        } catch (Throwable th) {
            throw th;
        }
        return threadPoolExecutor;
    }
}
