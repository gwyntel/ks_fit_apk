package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhu implements Runnable {
    private final /* synthetic */ zzbg zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ zzhg zzc;

    zzhu(zzhg zzhgVar, zzbg zzbgVar, zzo zzoVar) {
        this.zzc = zzhgVar;
        this.zza = zzbgVar;
        this.zzb = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.e(this.zzc.d(this.zza, this.zzb), this.zzb);
    }
}
