package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzql;

/* loaded from: classes3.dex */
final class zzhs implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzhg zzb;

    zzhs(zzhg zzhgVar, zzo zzoVar) {
        this.zzb = zzhgVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws NumberFormatException {
        this.zzb.zza.z();
        zzmq zzmqVar = this.zzb.zza;
        zzo zzoVar = this.zza;
        zzmqVar.zzl().zzt();
        zzmqVar.A();
        Preconditions.checkNotEmpty(zzoVar.zza);
        zzie zzieVarZza = zzie.zza(zzoVar.zzt, (zzql.zzb() && zzmqVar.zze().zza(zzbi.zzcm)) ? zzoVar.zzy : 100);
        zzie zzieVarS = zzmqVar.s(zzoVar.zza);
        zzmqVar.zzj().zzp().zza("Setting consent, package, consent", zzoVar.zza, zzieVarZza);
        zzmqVar.o(zzoVar.zza, zzieVarZza);
        if (zzieVarZza.zzc(zzieVarS)) {
            zzmqVar.x(zzoVar);
        }
        if (zzql.zzb() && zzmqVar.zze().zza(zzbi.zzcm)) {
            zzay zzayVarZza = zzay.zza(zzoVar.zzz);
            if (zzay.zza.equals(zzayVarZza)) {
                return;
            }
            zzmqVar.zzj().zzp().zza("Setting DMA consent. package, consent", zzoVar.zza, zzayVarZza.zzf());
            zzmqVar.n(zzoVar.zza, zzayVarZza);
        }
    }
}
