package com.google.android.gms.internal.measurement;

import java.util.List;

/* loaded from: classes3.dex */
public final class zzbg extends zzay {
    protected zzbg() {
        this.f13178a.add(zzbv.AND);
        this.f13178a.add(zzbv.NOT);
        this.f13178a.add(zzbv.OR);
    }

    @Override // com.google.android.gms.internal.measurement.zzay
    public final zzaq zza(String str, zzh zzhVar, List<zzaq> list) {
        int i2 = zzbj.f13183a[zzg.zza(str).ordinal()];
        if (i2 == 1) {
            zzg.zza(zzbv.AND, 2, list);
            zzaq zzaqVarZza = zzhVar.zza(list.get(0));
            return !zzaqVarZza.zzd().booleanValue() ? zzaqVarZza : zzhVar.zza(list.get(1));
        }
        if (i2 == 2) {
            zzg.zza(zzbv.NOT, 1, list);
            return new zzag(Boolean.valueOf(!zzhVar.zza(list.get(0)).zzd().booleanValue()));
        }
        if (i2 != 3) {
            return super.a(str);
        }
        zzg.zza(zzbv.OR, 2, list);
        zzaq zzaqVarZza2 = zzhVar.zza(list.get(0));
        return zzaqVarZza2.zzd().booleanValue() ? zzaqVarZza2 : zzhVar.zza(list.get(1));
    }
}
