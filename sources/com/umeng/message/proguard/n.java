package com.umeng.message.proguard;

import java.lang.ref.WeakReference;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    private static WeakReference<Future<?>> f22871a;

    public static synchronized void a() {
        Future<?> future;
        WeakReference<Future<?>> weakReference = f22871a;
        if (weakReference == null || (future = weakReference.get()) == null || future.isDone() || future.isCancelled()) {
            f22871a = new WeakReference<>(b.b(new m()));
        }
    }
}
