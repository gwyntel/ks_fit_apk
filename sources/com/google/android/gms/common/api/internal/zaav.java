package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;

/* loaded from: classes3.dex */
abstract class zaav implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaw f12711a;

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        this.f12711a.zab.lock();
        try {
            try {
                if (!Thread.interrupted()) {
                    zaa();
                }
            } catch (RuntimeException e2) {
                this.f12711a.zaa.g(e2);
            }
        } finally {
            this.f12711a.zab.unlock();
        }
    }

    protected abstract void zaa();
}
