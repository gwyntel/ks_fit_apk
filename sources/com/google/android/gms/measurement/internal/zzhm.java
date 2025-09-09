package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhm implements Runnable {
    private final /* synthetic */ zzad zza;
    private final /* synthetic */ zzhg zzb;

    zzhm(zzhg zzhgVar, zzad zzadVar) {
        this.zzb = zzhgVar;
        this.zza = zzadVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.z();
        if (this.zza.zzc.zza() == null) {
            this.zzb.zza.d(this.zza);
        } else {
            this.zzb.zza.u(this.zza);
        }
    }
}
