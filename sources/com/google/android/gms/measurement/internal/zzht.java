package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzht implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzhg zzb;

    zzht(zzhg zzhgVar, zzo zzoVar) {
        this.zzb = zzhgVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.z();
        this.zzb.zza.x(this.zza);
    }
}
