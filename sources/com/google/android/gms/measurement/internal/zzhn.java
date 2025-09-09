package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhn implements Runnable {
    private final /* synthetic */ zzad zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ zzhg zzc;

    zzhn(zzhg zzhgVar, zzad zzadVar, zzo zzoVar) {
        this.zzc = zzhgVar;
        this.zza = zzadVar;
        this.zzb = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza.z();
        if (this.zza.zzc.zza() == null) {
            this.zzc.zza.e(this.zza, this.zzb);
        } else {
            this.zzc.zza.v(this.zza, this.zzb);
        }
    }
}
