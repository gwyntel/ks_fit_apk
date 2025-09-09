package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzav implements Runnable {
    private final /* synthetic */ zzic zza;
    private final /* synthetic */ zzaw zzb;

    zzav(zzaw zzawVar, zzic zzicVar) {
        this.zzb = zzawVar;
        this.zza = zzicVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zza.zzd();
        if (zzae.zza()) {
            this.zza.zzl().zzb(this);
            return;
        }
        boolean zZzc = this.zzb.zzc();
        this.zzb.zzd = 0L;
        if (zZzc) {
            this.zzb.zzb();
        }
    }
}
