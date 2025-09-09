package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzko implements Runnable {
    private final /* synthetic */ zzkf zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzki zzc;

    zzko(zzki zzkiVar, zzkf zzkfVar, long j2) {
        this.zzc = zzkiVar;
        this.zza = zzkfVar;
        this.zzb = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza(this.zza, false, this.zzb);
        zzki zzkiVar = this.zzc;
        zzkiVar.f13300b = null;
        zzkiVar.zzo().i(null);
    }
}
