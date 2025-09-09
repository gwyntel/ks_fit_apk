package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjp implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzin zzb;

    zzjp(zzin zzinVar, AtomicReference atomicReference) {
        this.zzb = zzinVar;
        this.zza = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zza) {
            try {
                try {
                    this.zza.set(Long.valueOf(this.zzb.zze().zzc(this.zzb.zzg().f(), zzbi.zzam)));
                } finally {
                    this.zza.notify();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
