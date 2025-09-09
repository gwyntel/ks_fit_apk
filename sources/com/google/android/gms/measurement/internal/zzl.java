package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzl implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ AppMeasurementDynamiteService zzd;

    zzl(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2) {
        this.zzd = appMeasurementDynamiteService;
        this.zza = zzcvVar;
        this.zzb = str;
        this.zzc = str2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzd.f13245a.zzr().c(this.zza, this.zzb, this.zzc);
    }
}
