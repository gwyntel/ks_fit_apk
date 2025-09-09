package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzmu implements Callable<String> {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzmq zzb;

    zzmu(zzmq zzmqVar, zzo zzoVar) {
        this.zzb = zzmqVar;
        this.zza = zzoVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        if (!this.zzb.s((String) Preconditions.checkNotNull(this.zza.zza)).zzh() || !zzie.zza(this.zza.zzt).zzh()) {
            this.zzb.zzj().zzp().zza("Analytics storage consent denied. Returning null app instance id");
            return null;
        }
        zzh zzhVarB = this.zzb.b(this.zza);
        if (zzhVarB != null) {
            return zzhVarB.zzy();
        }
        this.zzb.zzj().zzu().zza("App info was null when attempting to get app instance id");
        return null;
    }
}
