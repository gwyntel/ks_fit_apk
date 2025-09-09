package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zza implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzb zzc;

    zza(zzb zzbVar, String str, long j2) {
        this.zzc = zzbVar;
        this.zza = str;
        this.zzb = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzb.b(this.zzc, this.zza, this.zzb);
    }
}
