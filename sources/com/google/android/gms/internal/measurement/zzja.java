package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public class zzja<K, V> extends zzhz<K, V> implements Serializable {
    final transient zziv<K, ? extends zziq<V>> map;
    final transient int size;

    zzja(zziv<K, ? extends zziq<V>> zzivVar, int i2) {
        this.map = zzivVar;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    public /* bridge */ /* synthetic */ boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.android.gms.internal.measurement.zzia, com.google.android.gms.internal.measurement.zzjg
    public /* synthetic */ Map zza() {
        return this.map;
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    final Map<K, Collection<V>> zzb() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    public final boolean zza(@CheckForNull Object obj) {
        return obj != null && super.zza(obj);
    }
}
