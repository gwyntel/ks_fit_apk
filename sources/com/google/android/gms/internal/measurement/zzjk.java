package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzjk<K, V> extends zzjc<Map.Entry<K, V>> {
    private final transient zziv<K, V> zza;
    private final transient Object[] zzb;
    private final transient int zzc = 0;
    private final transient int zzd;

    zzjk(zziv<K, V> zzivVar, Object[] objArr, int i2, int i3) {
        this.zza = zzivVar;
        this.zzb = objArr;
        this.zzd = i3;
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && value.equals(this.zza.get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    /* renamed from: zzd */
    public final zzjp<Map.Entry<K, V>> iterator() {
        return (zzjp) zzc().iterator();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    final zzir<Map.Entry<K, V>> zzg() {
        return new zzjj(this);
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza(Object[] objArr, int i2) {
        return zzc().zza(objArr, i2);
    }
}
