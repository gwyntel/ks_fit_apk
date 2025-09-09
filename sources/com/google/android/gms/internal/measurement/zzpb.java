package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

@Deprecated
/* loaded from: classes3.dex */
public final class zzpb extends AbstractList<String> implements zzmp, RandomAccess {
    private final zzmp zza;

    public zzpb(zzmp zzmpVar) {
        this.zza = zzmpVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        return (String) this.zza.get(i2);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzpd(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i2) {
        return new zzpa(this, i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final void zza(zzkm zzkmVar) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final Object zzb(int i2) {
        return this.zza.zzb(i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final zzmp zzd() {
        return this;
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final List<?> zze() {
        return this.zza.zze();
    }
}
