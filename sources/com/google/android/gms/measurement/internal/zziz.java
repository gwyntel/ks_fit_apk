package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zziz implements com.google.android.gms.internal.measurement.zzjt<Object> {
    private final /* synthetic */ zzmi zza;
    private final /* synthetic */ zzin zzb;

    zziz(zzin zzinVar, zzmi zzmiVar) {
        this.zzb = zzinVar;
        this.zza = zzmiVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzjt
    public final void zza(Throwable th) {
        this.zzb.zzt();
        this.zzb.zzh = false;
        this.zzb.p();
        this.zzb.zzj().zzg().zza("registerTriggerAsync failed with throwable", th);
    }

    @Override // com.google.android.gms.internal.measurement.zzjt
    public final void zza(Object obj) {
        this.zzb.zzt();
        this.zzb.zzh = false;
        this.zzb.p();
        this.zzb.zzj().zzc().zza("registerTriggerAsync ran. uri", this.zza.zza);
    }
}
