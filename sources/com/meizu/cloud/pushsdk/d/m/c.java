package com.meizu.cloud.pushsdk.d.m;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class c implements Executor {

    /* renamed from: a, reason: collision with root package name */
    private final ThreadPoolExecutor f19263a;

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static c f19264a = new c();
    }

    private c() {
        this.f19263a = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new d().a("single-pool-%d").a());
    }

    public static c a() {
        return b.f19264a;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.f19263a.execute(runnable);
    }
}
