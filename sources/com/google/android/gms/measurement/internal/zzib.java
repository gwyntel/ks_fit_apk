package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzib implements Callable<List<zznb>> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ zzhg zzb;

    zzib(zzhg zzhgVar, String str) {
        this.zzb = zzhgVar;
        this.zza = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zznb> call() throws Exception {
        this.zzb.zza.z();
        return this.zzb.zza.zzf().zzi(this.zza);
    }
}
