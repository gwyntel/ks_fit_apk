package com.umeng.message.proguard;

import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public abstract class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected Future<?> f22826a;

    public final c a() {
        this.f22826a = b();
        return this;
    }

    protected abstract Future<?> b();

    public final void c() {
        Future<?> future = this.f22826a;
        if (future != null) {
            try {
                if (!future.isCancelled() && !future.isDone()) {
                    future.cancel(false);
                }
            } catch (Throwable unused) {
            }
        }
        this.f22826a = null;
    }

    public final boolean d() {
        Future<?> future = this.f22826a;
        return (future == null || future.isDone()) ? false : true;
    }
}
