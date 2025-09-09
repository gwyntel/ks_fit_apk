package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzev;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzqx;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
final class zzt extends zzml {
    private String zza;
    private Set<Integer> zzb;
    private Map<Integer, zzv> zzc;
    private Long zzd;
    private Long zze;

    zzt(zzmq zzmqVar) {
        super(zzmqVar);
    }

    private final zzv zza(Integer num) {
        if (this.zzc.containsKey(num)) {
            return this.zzc.get(num);
        }
        zzv zzvVar = new zzv(this, this.zza);
        this.zzc.put(num, zzvVar);
        return zzvVar;
    }

    final List a(String str, List list, List list2, Long l2, Long l3) throws IllegalStateException {
        boolean z2;
        zzev.zze zzeVar;
        zzbc zzbcVar;
        zzx zzxVar;
        ArrayMap arrayMap;
        Map map;
        List<zzev.zzb> list3;
        Map map2;
        Iterator<zzfh.zzm> it;
        Map map3;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(list2);
        this.zza = str;
        this.zzb = new HashSet();
        this.zzc = new ArrayMap();
        this.zzd = l2;
        this.zze = l3;
        Iterator it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                z2 = false;
                break;
            }
            if ("_s".equals(((zzfh.zze) it2.next()).zzg())) {
                z2 = true;
                break;
            }
        }
        boolean z3 = zzqx.zzb() && zze().zzf(this.zza, zzbi.zzbg);
        boolean z4 = zzqx.zzb() && zze().zzf(this.zza, zzbi.zzbf);
        if (z2) {
            zzao zzaoVarZzh = zzh();
            String str2 = this.zza;
            zzaoVarZzh.zzak();
            zzaoVarZzh.zzt();
            Preconditions.checkNotEmpty(str2);
            ContentValues contentValues = new ContentValues();
            contentValues.put("current_session_count", (Integer) 0);
            try {
                zzaoVarZzh.a().update("events", contentValues, "app_id = ?", new String[]{str2});
            } catch (SQLiteException e2) {
                zzaoVarZzh.zzj().zzg().zza("Error resetting session-scoped event counts. appId", zzfs.d(str2), e2);
            }
        }
        Map mapEmptyMap = Collections.emptyMap();
        if (z4 && z3) {
            mapEmptyMap = zzh().u(this.zza);
        }
        Map mapT = zzh().t(this.zza);
        if (!mapT.isEmpty()) {
            HashSet hashSet = new HashSet(mapT.keySet());
            if (z2) {
                String str3 = this.zza;
                Map mapV = zzh().v(this.zza);
                Preconditions.checkNotEmpty(str3);
                Preconditions.checkNotNull(mapT);
                Map arrayMap2 = new ArrayMap();
                if (!mapT.isEmpty()) {
                    for (Integer num : mapT.keySet()) {
                        num.intValue();
                        zzfh.zzl zzlVar = (zzfh.zzl) mapT.get(num);
                        List list4 = (List) mapV.get(num);
                        if (list4 == null || list4.isEmpty()) {
                            map3 = mapV;
                            arrayMap2.put(num, zzlVar);
                            mapV = map3;
                        } else {
                            List listM = g_().m(zzlVar.zzi(), list4);
                            if (!listM.isEmpty()) {
                                zzfh.zzl.zza zzaVarZzb = zzlVar.zzby().zzb().zzb(listM);
                                zzaVarZzb.zzd().zzd(g_().m(zzlVar.zzk(), list4));
                                ArrayList arrayList = new ArrayList();
                                for (zzfh.zzd zzdVar : zzlVar.zzh()) {
                                    Map map4 = mapV;
                                    if (!list4.contains(Integer.valueOf(zzdVar.zza()))) {
                                        arrayList.add(zzdVar);
                                    }
                                    mapV = map4;
                                }
                                map3 = mapV;
                                zzaVarZzb.zza().zza(arrayList);
                                ArrayList arrayList2 = new ArrayList();
                                for (zzfh.zzm zzmVar : zzlVar.zzj()) {
                                    if (!list4.contains(Integer.valueOf(zzmVar.zzb()))) {
                                        arrayList2.add(zzmVar);
                                    }
                                }
                                zzaVarZzb.zzc().zzc(arrayList2);
                                arrayMap2.put(num, (zzfh.zzl) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzb.zzab()));
                                mapV = map3;
                            }
                        }
                    }
                }
                map = arrayMap2;
            } else {
                map = mapT;
            }
            Iterator it3 = hashSet.iterator();
            while (it3.hasNext()) {
                Integer num2 = (Integer) it3.next();
                num2.intValue();
                zzfh.zzl zzlVar2 = (zzfh.zzl) map.get(num2);
                BitSet bitSet = new BitSet();
                BitSet bitSet2 = new BitSet();
                ArrayMap arrayMap3 = new ArrayMap();
                if (zzlVar2 != null && zzlVar2.zza() != 0) {
                    for (zzfh.zzd zzdVar2 : zzlVar2.zzh()) {
                        if (zzdVar2.zzf()) {
                            arrayMap3.put(Integer.valueOf(zzdVar2.zza()), zzdVar2.zze() ? Long.valueOf(zzdVar2.zzb()) : null);
                        }
                    }
                }
                ArrayMap arrayMap4 = new ArrayMap();
                if (zzlVar2 != null && zzlVar2.zzc() != 0) {
                    Iterator<zzfh.zzm> it4 = zzlVar2.zzj().iterator();
                    while (it4.hasNext()) {
                        zzfh.zzm next = it4.next();
                        if (!next.zzf() || next.zza() <= 0) {
                            it = it4;
                        } else {
                            it = it4;
                            arrayMap4.put(Integer.valueOf(next.zzb()), Long.valueOf(next.zza(next.zza() - 1)));
                        }
                        it4 = it;
                    }
                }
                if (zzlVar2 != null) {
                    int i2 = 0;
                    while (i2 < (zzlVar2.zzd() << 6)) {
                        if (zzna.u(zzlVar2.zzk(), i2)) {
                            map2 = map;
                            zzj().zzp().zza("Filter already evaluated. audience ID, filter ID", num2, Integer.valueOf(i2));
                            bitSet2.set(i2);
                            if (zzna.u(zzlVar2.zzi(), i2)) {
                                bitSet.set(i2);
                            }
                            i2++;
                            map = map2;
                        } else {
                            map2 = map;
                        }
                        arrayMap3.remove(Integer.valueOf(i2));
                        i2++;
                        map = map2;
                    }
                }
                Map map5 = map;
                zzfh.zzl zzlVar3 = (zzfh.zzl) mapT.get(num2);
                if (z4 && z3 && (list3 = (List) mapEmptyMap.get(num2)) != null && this.zze != null && this.zzd != null) {
                    for (zzev.zzb zzbVar : list3) {
                        int iZzb = zzbVar.zzb();
                        long jLongValue = this.zze.longValue() / 1000;
                        if (zzbVar.zzi()) {
                            jLongValue = this.zzd.longValue() / 1000;
                        }
                        if (arrayMap3.containsKey(Integer.valueOf(iZzb))) {
                            arrayMap3.put(Integer.valueOf(iZzb), Long.valueOf(jLongValue));
                        }
                        if (arrayMap4.containsKey(Integer.valueOf(iZzb))) {
                            arrayMap4.put(Integer.valueOf(iZzb), Long.valueOf(jLongValue));
                        }
                    }
                }
                this.zzc.put(num2, new zzv(this, this.zza, zzlVar3, bitSet, bitSet2, arrayMap3, arrayMap4));
                it3 = it3;
                map = map5;
            }
        }
        if (!list.isEmpty()) {
            zzx zzxVar2 = new zzx(this);
            ArrayMap arrayMap5 = new ArrayMap();
            Iterator it5 = list.iterator();
            while (it5.hasNext()) {
                zzfh.zze zzeVar2 = (zzfh.zze) it5.next();
                zzfh.zze zzeVarA = zzxVar2.a(this.zza, zzeVar2);
                if (zzeVarA != null) {
                    zzao zzaoVarZzh2 = zzh();
                    String str4 = this.zza;
                    String strZzg = zzeVarA.zzg();
                    zzbc zzbcVarZzd = zzaoVarZzh2.zzd(str4, zzeVar2.zzg());
                    if (zzbcVarZzd == null) {
                        zzaoVarZzh2.zzj().zzu().zza("Event aggregate wasn't created during raw event logging. appId, event", zzfs.d(str4), zzaoVarZzh2.zzi().c(strZzg));
                        zzbcVar = new zzbc(str4, zzeVar2.zzg(), 1L, 1L, 1L, zzeVar2.zzd(), 0L, null, null, null, null);
                    } else {
                        zzbcVar = new zzbc(zzbcVarZzd.f13265a, zzbcVarZzd.f13266b, zzbcVarZzd.f13267c + 1, zzbcVarZzd.f13268d + 1, zzbcVarZzd.f13269e + 1, zzbcVarZzd.f13270f, zzbcVarZzd.f13271g, zzbcVarZzd.f13272h, zzbcVarZzd.f13273i, zzbcVarZzd.f13274j, zzbcVarZzd.f13275k);
                    }
                    zzh().zza(zzbcVar);
                    long j2 = zzbcVar.f13267c;
                    String strZzg2 = zzeVarA.zzg();
                    Map mapR = (Map) arrayMap5.get(strZzg2);
                    if (mapR == null) {
                        mapR = zzh().r(this.zza, strZzg2);
                        arrayMap5.put(strZzg2, mapR);
                    }
                    for (Integer num3 : mapR.keySet()) {
                        int iIntValue = num3.intValue();
                        if (this.zzb.contains(num3)) {
                            zzj().zzp().zza("Skipping failed audience ID", num3);
                        } else {
                            Iterator it6 = ((List) mapR.get(num3)).iterator();
                            boolean zI = true;
                            while (true) {
                                if (!it6.hasNext()) {
                                    zzxVar = zzxVar2;
                                    arrayMap = arrayMap5;
                                    break;
                                }
                                zzev.zzb zzbVar2 = (zzev.zzb) it6.next();
                                zzxVar = zzxVar2;
                                zzz zzzVar = new zzz(this, this.zza, iIntValue, zzbVar2);
                                arrayMap = arrayMap5;
                                zI = zzzVar.i(this.zzd, this.zze, zzeVarA, j2, zzbcVar, zza(iIntValue, zzbVar2.zzb()));
                                if (!zI) {
                                    this.zzb.add(num3);
                                    break;
                                }
                                zza(num3).c(zzzVar);
                                zzxVar2 = zzxVar;
                                arrayMap5 = arrayMap;
                            }
                            if (!zI) {
                                this.zzb.add(num3);
                            }
                            zzxVar2 = zzxVar;
                            arrayMap5 = arrayMap;
                        }
                    }
                }
            }
        }
        if (!list2.isEmpty()) {
            ArrayMap arrayMap6 = new ArrayMap();
            Iterator it7 = list2.iterator();
            while (it7.hasNext()) {
                zzfh.zzn zznVar = (zzfh.zzn) it7.next();
                String strZzg3 = zznVar.zzg();
                Map mapS = (Map) arrayMap6.get(strZzg3);
                if (mapS == null) {
                    mapS = zzh().s(this.zza, strZzg3);
                    arrayMap6.put(strZzg3, mapS);
                }
                Iterator it8 = mapS.keySet().iterator();
                while (true) {
                    if (it8.hasNext()) {
                        Integer num4 = (Integer) it8.next();
                        int iIntValue2 = num4.intValue();
                        if (this.zzb.contains(num4)) {
                            zzj().zzp().zza("Skipping failed audience ID", num4);
                            break;
                        }
                        Iterator it9 = ((List) mapS.get(num4)).iterator();
                        boolean zI2 = true;
                        while (true) {
                            if (!it9.hasNext()) {
                                break;
                            }
                            zzeVar = (zzev.zze) it9.next();
                            if (zzj().j(2)) {
                                zzj().zzp().zza("Evaluating filter. audience, filter, property", num4, zzeVar.zzi() ? Integer.valueOf(zzeVar.zza()) : null, zzi().e(zzeVar.zze()));
                                zzj().zzp().zza("Filter definition", g_().j(zzeVar));
                            }
                            if (!zzeVar.zzi() || zzeVar.zza() > 256) {
                                break;
                            }
                            zzab zzabVar = new zzab(this, this.zza, iIntValue2, zzeVar);
                            zI2 = zzabVar.i(this.zzd, this.zze, zznVar, zza(iIntValue2, zzeVar.zza()));
                            if (!zI2) {
                                this.zzb.add(num4);
                                break;
                            }
                            zza(num4).c(zzabVar);
                        }
                        zzj().zzu().zza("Invalid property filter ID. appId, id", zzfs.d(this.zza), String.valueOf(zzeVar.zzi() ? Integer.valueOf(zzeVar.zza()) : null));
                        zI2 = false;
                        if (!zI2) {
                            this.zzb.add(num4);
                        }
                    }
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Set<Integer> setKeySet = this.zzc.keySet();
        setKeySet.removeAll(this.zzb);
        for (Integer num5 : setKeySet) {
            int iIntValue3 = num5.intValue();
            zzv zzvVar = this.zzc.get(num5);
            Preconditions.checkNotNull(zzvVar);
            zzfh.zzc zzcVarA = zzvVar.a(iIntValue3);
            arrayList3.add(zzcVarA);
            zzao zzaoVarZzh3 = zzh();
            String str5 = this.zza;
            zzfh.zzl zzlVarZzd = zzcVarA.zzd();
            zzaoVarZzh3.zzak();
            zzaoVarZzh3.zzt();
            Preconditions.checkNotEmpty(str5);
            Preconditions.checkNotNull(zzlVarZzd);
            byte[] bArrZzbv = zzlVarZzd.zzbv();
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("app_id", str5);
            contentValues2.put("audience_id", num5);
            contentValues2.put("current_results", bArrZzbv);
            try {
            } catch (SQLiteException e3) {
                e = e3;
            }
            try {
                if (zzaoVarZzh3.a().insertWithOnConflict("audience_filter_values", null, contentValues2, 5) == -1) {
                    zzaoVarZzh3.zzj().zzg().zza("Failed to insert filter results (got -1). appId", zzfs.d(str5));
                }
            } catch (SQLiteException e4) {
                e = e4;
                zzaoVarZzh3.zzj().zzg().zza("Error storing filter results. appId", zzfs.d(str5), e);
            }
        }
        return arrayList3;
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    protected final boolean zzc() {
        return false;
    }

    private final boolean zza(int i2, int i3) {
        zzv zzvVar = this.zzc.get(Integer.valueOf(i2));
        if (zzvVar == null) {
            return false;
        }
        return zzvVar.zzd.get(i3);
    }
}
