package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzjg implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Object zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzin zze;

    zzjg(zzin zzinVar, String str, String str2, Object obj, long j2) {
        this.zze = zzinVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = obj;
        this.zzd = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zze.n(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
