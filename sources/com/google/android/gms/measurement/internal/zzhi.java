package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzhi implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzhg zzb;

    zzhi(zzhg zzhgVar, zzo zzoVar) {
        this.zzb = zzhgVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.z();
        zzmq zzmqVar = this.zzb.zza;
        zzo zzoVar = this.zza;
        zzmqVar.zzl().zzt();
        zzmqVar.A();
        Preconditions.checkNotEmpty(zzoVar.zza);
        zzmqVar.b(zzoVar);
    }
}
