package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhl implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzhg zzb;

    zzhl(zzhg zzhgVar, zzo zzoVar) {
        this.zzb = zzhgVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.z();
        this.zzb.zza.w(this.zza);
    }
}
