package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzhv implements Callable<zzam> {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzhg zzb;

    zzhv(zzhg zzhgVar, zzo zzoVar) {
        this.zzb = zzhgVar;
        this.zza = zzoVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ zzam call() throws Exception {
        this.zzb.zza.z();
        return new zzam(this.zzb.zza.a(this.zza.zza));
    }
}
