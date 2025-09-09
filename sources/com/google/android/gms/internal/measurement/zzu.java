package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class zzu extends zzal {
    private boolean zzk;
    private boolean zzl;
    private final /* synthetic */ zzr zzm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzu(zzr zzrVar, boolean z2, boolean z3) {
        super("log");
        this.zzm = zzrVar;
        this.zzk = z2;
        this.zzl = z3;
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zzb("log", 1, list);
        if (list.size() == 1) {
            this.zzm.zzk.zza(zzs.INFO, zzhVar.zza(list.get(0)).zzf(), Collections.emptyList(), this.zzk, this.zzl);
            return zzaq.zzc;
        }
        zzs zzsVarZza = zzs.zza(zzg.zzb(zzhVar.zza(list.get(0)).zze().doubleValue()));
        String strZzf = zzhVar.zza(list.get(1)).zzf();
        if (list.size() == 2) {
            this.zzm.zzk.zza(zzsVarZza, strZzf, Collections.emptyList(), this.zzk, this.zzl);
            return zzaq.zzc;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 2; i2 < Math.min(list.size(), 5); i2++) {
            arrayList.add(zzhVar.zza(list.get(i2)).zzf());
        }
        this.zzm.zzk.zza(zzsVarZza, strZzf, arrayList, this.zzk, this.zzl);
        return zzaq.zzc;
    }
}
