package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzhw implements Callable<byte[]> {
    private final /* synthetic */ zzbg zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzhg zzc;

    zzhw(zzhg zzhgVar, zzbg zzbgVar, String str) {
        this.zzc = zzhgVar;
        this.zza = zzbgVar;
        this.zzb = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() throws Exception {
        this.zzc.zza.z();
        return this.zzc.zza.zzm().zza(this.zza, this.zzb);
    }
}
