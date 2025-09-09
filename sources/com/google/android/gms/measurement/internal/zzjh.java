package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjh implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzin zzb;

    zzjh(zzin zzinVar, long j2) {
        this.zzb = zzinVar;
        this.zza = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.c(this.zza, true);
        this.zzb.zzo().zza(new AtomicReference<>());
    }
}
