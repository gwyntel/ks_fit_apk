package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhz implements Runnable {
    private final /* synthetic */ zzmz zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ zzhg zzc;

    zzhz(zzhg zzhgVar, zzmz zzmzVar, zzo zzoVar) {
        this.zzc = zzhgVar;
        this.zza = zzmzVar;
        this.zzb = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza.z();
        if (this.zza.zza() == null) {
            this.zzc.zza.p(this.zza.zza, this.zzb);
        } else {
            this.zzc.zza.j(this.zza, this.zzb);
        }
    }
}
