package com.google.android.gms.internal.measurement;

import java.util.Map;

/* loaded from: classes3.dex */
final class zzmi<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzmj> zza;

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zza.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.zza.getValue() == null) {
            return null;
        }
        return zzmj.zza();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj instanceof zznj) {
            return this.zza.getValue().zza((zznj) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzmj zza() {
        return this.zza.getValue();
    }

    private zzmi(Map.Entry<K, zzmj> entry) {
        this.zza = entry;
    }
}
