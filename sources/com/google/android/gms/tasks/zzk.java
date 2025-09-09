package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzk implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13349a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzl f13350b;

    zzk(zzl zzlVar, Task task) {
        this.f13350b = zzlVar;
        this.f13349a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.f13350b.zzb) {
            try {
                zzl zzlVar = this.f13350b;
                if (zzlVar.zzc != null) {
                    zzlVar.zzc.onFailure((Exception) Preconditions.checkNotNull(this.f13349a.getException()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
