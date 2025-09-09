package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzgb implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzgc zzb;

    zzgb(zzgc zzgcVar, boolean z2) {
        this.zzb = zzgcVar;
        this.zza = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzb.q(this.zza);
    }
}
