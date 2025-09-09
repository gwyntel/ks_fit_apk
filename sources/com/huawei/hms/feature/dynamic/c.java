package com.huawei.hms.feature.dynamic;

import com.huawei.hms.common.util.Logger;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16143a = "ExecutorsManager";

    /* renamed from: b, reason: collision with root package name */
    public static final long f16144b = 60;

    public static class a implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        public final AtomicInteger f16145a = new AtomicInteger(1);

        /* renamed from: b, reason: collision with root package name */
        public final String f16146b;

        /* renamed from: com.huawei.hms.feature.dynamic.c$a$a, reason: collision with other inner class name */
        public class C0132a implements Thread.UncaughtExceptionHandler {
            public C0132a() {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                Logger.e(c.f16143a, thread.getName() + " : " + th.getMessage());
            }
        }

        public a(String str) {
            this.f16146b = str + "-thread-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, this.f16146b + this.f16145a.getAndIncrement());
            thread.setUncaughtExceptionHandler(new C0132a());
            return thread;
        }
    }

    public static ExecutorService a(int i2, String str) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i2, i2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a(str));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}
