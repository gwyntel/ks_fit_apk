package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* loaded from: classes3.dex */
final class zzko implements Comparator<zzkm> {
    zzko() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzkm zzkmVar, zzkm zzkmVar2) {
        zzkm zzkmVar3 = zzkmVar;
        zzkm zzkmVar4 = zzkmVar2;
        zzks zzksVar = (zzks) zzkmVar3.iterator();
        zzks zzksVar2 = (zzks) zzkmVar4.iterator();
        while (zzksVar.hasNext() && zzksVar2.hasNext()) {
            int iCompareTo = Integer.valueOf(zzkm.zza(zzksVar.zza())).compareTo(Integer.valueOf(zzkm.zza(zzksVar2.zza())));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.valueOf(zzkmVar3.zzb()).compareTo(Integer.valueOf(zzkmVar4.zzb()));
    }
}
