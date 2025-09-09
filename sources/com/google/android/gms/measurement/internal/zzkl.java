package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzkl implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzki zzb;

    zzkl(zzki zzkiVar, long j2) {
        this.zzb = zzkiVar;
        this.zza = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzc().zza(this.zza);
        this.zzb.f13300b = null;
    }
}
