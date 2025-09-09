package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzc implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzb zzb;

    zzc(zzb zzbVar, long j2) {
        this.zzb = zzbVar;
        this.zza = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
