package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjf implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ boolean zzb;
    private final /* synthetic */ zzin zzc;

    zzjf(zzin zzinVar, AtomicReference atomicReference, boolean z2) {
        this.zzc = zzinVar;
        this.zza = atomicReference;
        this.zzb = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzc.zzo().p(this.zza, this.zzb);
    }
}
