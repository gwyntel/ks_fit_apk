package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzj implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ AppMeasurementDynamiteService zze;

    zzj(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2, boolean z2) {
        this.zze = appMeasurementDynamiteService;
        this.zza = zzcvVar;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zze.f13245a.zzr().d(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
