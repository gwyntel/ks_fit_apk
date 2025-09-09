package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.internal.AppMeasurementDynamiteService;

/* loaded from: classes3.dex */
final class zzm implements Runnable {
    private final /* synthetic */ AppMeasurementDynamiteService.zza zza;
    private final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzm(AppMeasurementDynamiteService appMeasurementDynamiteService, AppMeasurementDynamiteService.zza zzaVar) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.f13245a.zzp().zza(this.zza);
    }
}
