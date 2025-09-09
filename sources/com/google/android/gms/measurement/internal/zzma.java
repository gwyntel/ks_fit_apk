package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzma implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzly zzb;

    zzma(zzly zzlyVar, long j2) {
        this.zzb = zzlyVar;
        this.zza = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzly.c(this.zzb, this.zza);
    }
}
