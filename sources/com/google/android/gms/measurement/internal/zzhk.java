package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhk implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzhg zze;

    zzhk(zzhg zzhgVar, String str, String str2, String str3, long j2) {
        this.zze = zzhgVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str = this.zza;
        if (str == null) {
            this.zze.zza.zza(this.zzb, (zzkf) null);
        } else {
            this.zze.zza.zza(this.zzb, new zzkf(this.zzc, str, this.zzd));
        }
    }
}
