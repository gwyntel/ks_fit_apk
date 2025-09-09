package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzn implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzn(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.f13245a.zzt().zza(this.zza, this.zzb.f13245a.zzab());
    }
}
