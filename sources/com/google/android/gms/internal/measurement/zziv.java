package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public abstract class zziv<K, V> implements Serializable, Map<K, V> {
    private static final Map.Entry<?, ?>[] zza = new Map.Entry[0];

    @CheckForNull
    private transient zzjc<Map.Entry<K, V>> zzb;

    @CheckForNull
    private transient zzjc<K> zzc;

    @CheckForNull
    private transient zziq<V> zzd;

    zziv() {
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        return ((zziq) values()).contains(obj);
    }

    @Override // java.util.Map
    public /* synthetic */ Set entrySet() {
        zzjc<Map.Entry<K, V>> zzjcVar = this.zzb;
        if (zzjcVar != null) {
            return zzjcVar;
        }
        zzjc<Map.Entry<K, V>> zzjcVarZzb = zzb();
        this.zzb = zzjcVarZzb;
        return zzjcVarZzb;
    }

    @Override // java.util.Map
    public boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    @CheckForNull
    public abstract V get(@CheckForNull Object obj);

    @Override // java.util.Map
    @CheckForNull
    public final V getOrDefault(@CheckForNull Object obj, @CheckForNull V v2) {
        V v3 = get(obj);
        return v3 != null ? v3 : v2;
    }

    @Override // java.util.Map
    public int hashCode() {
        return zzjn.a((zzjc) entrySet());
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public /* synthetic */ Set keySet() {
        zzjc<K> zzjcVar = this.zzc;
        if (zzjcVar != null) {
            return zzjcVar;
        }
        zzjc<K> zzjcVarZzc = zzc();
        this.zzc = zzjcVarZzc;
        return zzjcVarZzc;
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final V put(K k2, V v2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final V remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        int size = size();
        zzic.a(size, "size");
        StringBuilder sb = new StringBuilder((int) Math.min(size << 3, 1073741824L));
        sb.append('{');
        boolean z2 = true;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append(com.alipay.sdk.m.n.a.f9565h);
            sb.append(entry.getValue());
            z2 = false;
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Map
    public /* synthetic */ Collection values() {
        zziq<V> zziqVar = this.zzd;
        if (zziqVar != null) {
            return zziqVar;
        }
        zziq<V> zziqVarZza = zza();
        this.zzd = zziqVarZza;
        return zziqVarZza;
    }

    abstract zziq<V> zza();

    abstract zzjc<Map.Entry<K, V>> zzb();

    abstract zzjc<K> zzc();
}
