package com.huawei.hmf.tasks.a;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f15649a = new a();

    /* renamed from: c, reason: collision with root package name */
    private static final int f15650c;

    /* renamed from: d, reason: collision with root package name */
    private static final int f15651d;

    /* renamed from: e, reason: collision with root package name */
    private static final int f15652e;

    /* renamed from: b, reason: collision with root package name */
    private final Executor f15653b = new ExecutorC0129a(0);

    /* renamed from: com.huawei.hmf.tasks.a.a$a, reason: collision with other inner class name */
    static class ExecutorC0129a implements Executor {
        private ExecutorC0129a() {
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }

        /* synthetic */ ExecutorC0129a(byte b2) {
            this();
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f15650c = iAvailableProcessors;
        f15651d = iAvailableProcessors + 1;
        f15652e = (iAvailableProcessors * 2) + 1;
    }

    public static ExecutorService a() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(f15651d, f15652e, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static Executor b() {
        return f15649a.f15653b;
    }
}
