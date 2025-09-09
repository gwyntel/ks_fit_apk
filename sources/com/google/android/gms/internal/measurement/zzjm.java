package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzjm<K> extends zzjc<K> {
    private final transient zziv<K, ?> zza;
    private final transient zzir<K> zzb;

    zzjm(zziv<K, ?> zzivVar, zzir<K> zzirVar) {
        this.zza = zzivVar;
        this.zzb = zzirVar;
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.get(obj) != null;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza(Object[] objArr, int i2) {
        return zzc().zza(objArr, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq
    public final zzir<K> zzc() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    /* renamed from: zzd */
    public final zzjp<K> iterator() {
        return (zzjp) zzc().iterator();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return true;
    }
}
