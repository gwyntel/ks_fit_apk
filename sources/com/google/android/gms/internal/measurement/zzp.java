package com.google.android.gms.internal.measurement;

import java.util.List;

/* loaded from: classes3.dex */
final class zzp extends zzal {
    private final /* synthetic */ zzo zzk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzp(zzm zzmVar, String str, zzo zzoVar) {
        super(str);
        this.zzk = zzoVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zza("getValue", 2, list);
        zzaq zzaqVarZza = zzhVar.zza(list.get(0));
        zzaq zzaqVarZza2 = zzhVar.zza(list.get(1));
        String strZza = this.zzk.zza(zzaqVarZza.zzf());
        return strZza != null ? new zzas(strZza) : zzaqVarZza2;
    }
}
