package com.alibaba.sdk.android.utils.crashdefend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
final class f {

    /* renamed from: a, reason: collision with root package name */
    private final ThreadFactory f8956a = new ThreadFactory() { // from class: com.alibaba.sdk.android.utils.crashdefend.f.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "safe_thread");
            thread.setDaemon(false);
            return thread;
        }
    };

    /* renamed from: b, reason: collision with root package name */
    private ExecutorService f8957b;

    f() {
    }

    synchronized ExecutorService a() {
        try {
            if (this.f8957b == null) {
                this.f8957b = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS, new SynchronousQueue(), this.f8956a);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f8957b;
    }
}
