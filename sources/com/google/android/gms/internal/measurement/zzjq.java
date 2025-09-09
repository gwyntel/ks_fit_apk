package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzjq<E> extends zzjc<E> {
    private final transient E zza;

    zzjq(E e2) {
        this.zza = (E) zzhn.zza(e2);
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.equals(obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return "[" + this.zza.toString() + "]";
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza(Object[] objArr, int i2) {
        objArr[i2] = this.zza;
        return i2 + 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq
    public final zzir<E> zzc() {
        return zzir.zza(this.zza);
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    /* renamed from: zzd */
    public final zzjp<E> iterator() {
        return new zzjd(this.zza);
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return false;
    }
}
