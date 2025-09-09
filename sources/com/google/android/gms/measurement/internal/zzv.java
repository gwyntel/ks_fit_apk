package com.google.android.gms.measurement.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzqx;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzv {
    private String zza;
    private boolean zzb;
    private zzfh.zzl zzc;
    private BitSet zzd;
    private BitSet zze;
    private Map<Integer, Long> zzf;
    private Map<Integer, List<Long>> zzg;
    private final /* synthetic */ zzt zzh;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.measurement.zzfh$zzc$zza, com.google.android.gms.internal.measurement.zzlw$zza] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.google.android.gms.internal.measurement.zzfh$zzl$zza] */
    final zzfh.zzc a(int i2) {
        ArrayList arrayList;
        ?? arrayList2;
        ?? Zzb = zzfh.zzc.zzb();
        Zzb.zza(i2);
        Zzb.zza(this.zzb);
        zzfh.zzl zzlVar = this.zzc;
        if (zzlVar != null) {
            Zzb.zza(zzlVar);
        }
        ?? Zzd = zzfh.zzl.zze().zzb(zzna.l(this.zzd)).zzd(zzna.l(this.zze));
        if (this.zzf == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.zzf.size());
            for (Integer num : this.zzf.keySet()) {
                int iIntValue = num.intValue();
                Long l2 = this.zzf.get(num);
                if (l2 != null) {
                    arrayList.add((zzfh.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzfh.zzd.zzc().zza(iIntValue).zza(l2.longValue()).zzab()));
                }
            }
        }
        if (arrayList != null) {
            Zzd.zza(arrayList);
        }
        if (this.zzg == null) {
            arrayList2 = Collections.emptyList();
        } else {
            arrayList2 = new ArrayList(this.zzg.size());
            for (Integer num2 : this.zzg.keySet()) {
                zzfh.zzm.zza zzaVarZza = zzfh.zzm.zzc().zza(num2.intValue());
                List<Long> list = this.zzg.get(num2);
                if (list != null) {
                    Collections.sort(list);
                    zzaVarZza.zza(list);
                }
                arrayList2.add((zzfh.zzm) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZza.zzab()));
            }
        }
        Zzd.zzc(arrayList2);
        Zzb.zza(Zzd);
        return (zzfh.zzc) ((com.google.android.gms.internal.measurement.zzlw) Zzb.zzab());
    }

    final void c(zzac zzacVar) {
        int iA = zzacVar.a();
        Boolean bool = zzacVar.f13248c;
        if (bool != null) {
            this.zze.set(iA, bool.booleanValue());
        }
        Boolean bool2 = zzacVar.f13249d;
        if (bool2 != null) {
            this.zzd.set(iA, bool2.booleanValue());
        }
        if (zzacVar.f13250e != null) {
            Long l2 = this.zzf.get(Integer.valueOf(iA));
            long jLongValue = zzacVar.f13250e.longValue() / 1000;
            if (l2 == null || jLongValue > l2.longValue()) {
                this.zzf.put(Integer.valueOf(iA), Long.valueOf(jLongValue));
            }
        }
        if (zzacVar.f13251f != null) {
            List<Long> arrayList = this.zzg.get(Integer.valueOf(iA));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzg.put(Integer.valueOf(iA), arrayList);
            }
            if (zzacVar.h()) {
                arrayList.clear();
            }
            if (zzqx.zzb() && this.zzh.zze().zzf(this.zza, zzbi.zzbg) && zzacVar.g()) {
                arrayList.clear();
            }
            if (!zzqx.zzb() || !this.zzh.zze().zzf(this.zza, zzbi.zzbg)) {
                arrayList.add(Long.valueOf(zzacVar.f13251f.longValue() / 1000));
                return;
            }
            long jLongValue2 = zzacVar.f13251f.longValue() / 1000;
            if (arrayList.contains(Long.valueOf(jLongValue2))) {
                return;
            }
            arrayList.add(Long.valueOf(jLongValue2));
        }
    }

    private zzv(zzt zztVar, String str) {
        this.zzh = zztVar;
        this.zza = str;
        this.zzb = true;
        this.zzd = new BitSet();
        this.zze = new BitSet();
        this.zzf = new ArrayMap();
        this.zzg = new ArrayMap();
    }

    private zzv(zzt zztVar, String str, zzfh.zzl zzlVar, BitSet bitSet, BitSet bitSet2, Map<Integer, Long> map, Map<Integer, Long> map2) {
        this.zzh = zztVar;
        this.zza = str;
        this.zzd = bitSet;
        this.zze = bitSet2;
        this.zzf = map;
        this.zzg = new ArrayMap();
        if (map2 != null) {
            for (Integer num : map2.keySet()) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(map2.get(num));
                this.zzg.put(num, arrayList);
            }
        }
        this.zzb = false;
        this.zzc = zzlVar;
    }
}
