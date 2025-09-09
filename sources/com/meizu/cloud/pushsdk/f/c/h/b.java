package com.meizu.cloud.pushsdk.f.c.h;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static ExecutorService f19582a = null;

    /* renamed from: b, reason: collision with root package name */
    private static int f19583b = 2;

    public static ExecutorService a() {
        synchronized (b.class) {
            try {
                if (f19582a == null) {
                    f19582a = Executors.newScheduledThreadPool(f19583b);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f19582a;
    }

    public static Future a(Callable callable) {
        return a().submit(callable);
    }

    public static void a(int i2) {
        f19583b = i2;
    }

    public static void a(Runnable runnable) {
        a().execute(runnable);
    }
}
