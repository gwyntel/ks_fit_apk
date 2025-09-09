package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzhx implements Runnable {
    private final /* synthetic */ zzbg zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzhg zzc;

    zzhx(zzhg zzhgVar, zzbg zzbgVar, String str) {
        this.zzc = zzhgVar;
        this.zza = zzbgVar;
        this.zzb = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza.z();
        this.zzc.zza.g(this.zza, this.zzb);
    }
}
