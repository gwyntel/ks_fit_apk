package com.google.android.gms.internal.measurement;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
abstract class zzhx<K, V> implements Map.Entry<K, V> {
    zzhx() {
    }

    @Override // java.util.Map.Entry
    public boolean equals(@CheckForNull Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            if (zzhl.zza(getKey(), entry.getKey()) && zzhl.zza(getValue(), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public abstract K getKey();

    @Override // java.util.Map.Entry
    public abstract V getValue();

    @Override // java.util.Map.Entry
    public int hashCode() {
        K key = getKey();
        V value = getValue();
        return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public V setValue(V v2) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(getKey()) + ContainerUtils.KEY_VALUE_DELIMITER + String.valueOf(getValue());
    }
}
