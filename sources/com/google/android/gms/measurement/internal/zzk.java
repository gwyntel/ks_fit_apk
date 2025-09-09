package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzk implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ zzbg zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ AppMeasurementDynamiteService zzd;

    zzk(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzcv zzcvVar, zzbg zzbgVar, String str) {
        this.zzd = appMeasurementDynamiteService;
        this.zza = zzcvVar;
        this.zzb = zzbgVar;
        this.zzc = str;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzd.f13245a.zzr().zza(this.zza, this.zzb, this.zzc);
    }
}
