package com.hihonor.push.sdk;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class b1 {

    /* renamed from: d, reason: collision with root package name */
    public static final b1 f15466d = new b1();

    /* renamed from: a, reason: collision with root package name */
    public volatile Executor f15467a;

    /* renamed from: b, reason: collision with root package name */
    public volatile ExecutorService f15468b;

    /* renamed from: c, reason: collision with root package name */
    public final Object f15469c = new Object();

    public static class a implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public static Executor a() {
        b1 b1Var = f15466d;
        if (b1Var.f15467a == null) {
            synchronized (b1Var.f15469c) {
                try {
                    if (b1Var.f15467a == null) {
                        b1Var.f15467a = new a();
                    }
                } finally {
                }
            }
        }
        return b1Var.f15467a;
    }

    public static ExecutorService c() {
        return f15466d.b();
    }

    public final ExecutorService b() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static void a(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            a().execute(runnable);
        }
    }
}
