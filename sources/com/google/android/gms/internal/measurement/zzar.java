package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzar extends zzal implements zzak {
    private final List<String> zzk;
    private final List<zzaq> zzl;
    private zzh zzm;

    private zzar(zzar zzarVar) {
        super(zzarVar.f13176a);
        ArrayList arrayList = new ArrayList(zzarVar.zzk.size());
        this.zzk = arrayList;
        arrayList.addAll(zzarVar.zzk);
        ArrayList arrayList2 = new ArrayList(zzarVar.zzl.size());
        this.zzl = arrayList2;
        arrayList2.addAll(zzarVar.zzl);
        this.zzm = zzarVar.zzm;
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzh zzhVarZza = this.zzm.zza();
        for (int i2 = 0; i2 < this.zzk.size(); i2++) {
            if (i2 < list.size()) {
                zzhVarZza.zza(this.zzk.get(i2), zzhVar.zza(list.get(i2)));
            } else {
                zzhVarZza.zza(this.zzk.get(i2), zzaq.zzc);
            }
        }
        for (zzaq zzaqVar : this.zzl) {
            zzaq zzaqVarZza = zzhVarZza.zza(zzaqVar);
            if (zzaqVarZza instanceof zzat) {
                zzaqVarZza = zzhVarZza.zza(zzaqVar);
            }
            if (zzaqVarZza instanceof zzaj) {
                return ((zzaj) zzaqVarZza).zza();
            }
        }
        return zzaq.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzal, com.google.android.gms.internal.measurement.zzaq
    public final zzaq zzc() {
        return new zzar(this);
    }

    public zzar(String str, List<zzaq> list, List<zzaq> list2, zzh zzhVar) {
        super(str);
        this.zzk = new ArrayList();
        this.zzm = zzhVar;
        if (!list.isEmpty()) {
            Iterator<zzaq> it = list.iterator();
            while (it.hasNext()) {
                this.zzk.add(it.next().zzf());
            }
        }
        this.zzl = new ArrayList(list2);
    }
}
