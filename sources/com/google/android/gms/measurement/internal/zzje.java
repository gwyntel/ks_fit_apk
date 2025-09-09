package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzje implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzin zzb;

    zzje(zzin zzinVar, long j2) {
        this.zzb = zzinVar;
        this.zza = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzk().zzf.zza(this.zza);
        this.zzb.zzj().zzc().zza("Session timeout duration set", Long.valueOf(this.zza));
    }
}
