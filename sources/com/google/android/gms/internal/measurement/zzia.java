package com.google.android.gms.internal.measurement;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
abstract class zzia<K, V> implements zzjg<K, V> {

    @CheckForNull
    private transient Map<K, Collection<V>> zza;

    zzia() {
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzjg) {
            return zza().equals(((zzjg) obj).zza());
        }
        return false;
    }

    public int hashCode() {
        return zza().hashCode();
    }

    public String toString() {
        return zza().toString();
    }

    @Override // com.google.android.gms.internal.measurement.zzjg
    public Map<K, Collection<V>> zza() {
        Map<K, Collection<V>> map = this.zza;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> mapZzb = zzb();
        this.zza = mapZzb;
        return mapZzb;
    }

    abstract Map zzb();

    public boolean zza(@CheckForNull Object obj) {
        Iterator<Collection<V>> it = zza().values().iterator();
        while (it.hasNext()) {
            if (it.next().contains(obj)) {
                return true;
            }
        }
        return false;
    }
}
