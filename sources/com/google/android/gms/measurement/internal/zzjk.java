package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjk implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzin zzb;

    zzjk(zzin zzinVar, AtomicReference atomicReference) {
        this.zzb = zzinVar;
        this.zza = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zza) {
            try {
                try {
                    this.zza.set(this.zzb.zze().h(this.zzb.zzg().f()));
                } finally {
                    this.zza.notify();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
