package com.google.android.gms.internal.measurement;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class zzje<K, V> extends zziz<K, V> {
    public final zzjb<K, V> zza() {
        Set<Map.Entry<K, V>> setEntrySet = this.f13219a.entrySet();
        if (setEntrySet.isEmpty()) {
            return zzio.zza;
        }
        zziy zziyVar = new zziy(setEntrySet.size());
        int size = 0;
        for (Map.Entry<K, V> entry : setEntrySet) {
            K key = entry.getKey();
            zzjc zzjcVarZza = zzjc.zza((Collection) entry.getValue());
            if (!zzjcVarZza.isEmpty()) {
                zziyVar.zza(key, zzjcVarZza);
                size += zzjcVarZza.size();
            }
        }
        return new zzjb<>(zziyVar.zza(), size, null);
    }
}
