package com.google.android.gms.internal.measurement;

import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzk extends zzal {
    private final zzac zzk;

    public zzk(zzac zzacVar) {
        super("internal.eventLogger");
        this.zzk = zzacVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zza(this.f13176a, 3, list);
        String strZzf = zzhVar.zza(list.get(0)).zzf();
        long jZza = (long) zzg.zza(zzhVar.zza(list.get(1)).zze().doubleValue());
        zzaq zzaqVarZza = zzhVar.zza(list.get(2));
        this.zzk.zza(strZzf, jZza, zzaqVarZza instanceof zzap ? zzg.zza((zzap) zzaqVarZza) : new HashMap<>());
        return zzaq.zzc;
    }
}
