package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzhr implements Callable<List<zzad>> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ zzhg zzd;

    zzhr(zzhg zzhgVar, String str, String str2, String str3) {
        this.zzd = zzhgVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzad> call() throws Exception {
        this.zzd.zza.z();
        return this.zzd.zza.zzf().zza(this.zza, this.zzb, this.zzc);
    }
}
