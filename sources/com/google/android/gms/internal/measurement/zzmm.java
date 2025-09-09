package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
public final class zzmm extends zzkg<String> implements zzmp, RandomAccess {
    private static final zzmm zza;

    @Deprecated
    private static final zzmp zzb;
    private final List<Object> zzc;

    static {
        zzmm zzmmVar = new zzmm(false);
        zza = zzmmVar;
        zzb = zzmmVar;
    }

    public zzmm() {
        this(10);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i2, Object obj) {
        a();
        this.zzc.add(i2, (String) obj);
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        a();
        this.zzc.clear();
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        Object obj = this.zzc.get(i2);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzkm) {
            zzkm zzkmVar = (zzkm) obj;
            String strZzc = zzkmVar.zzc();
            if (zzkmVar.zzd()) {
                this.zzc.set(i2, strZzc);
            }
            return strZzc;
        }
        byte[] bArr = (byte[]) obj;
        String strZzb = zzlz.zzb(bArr);
        if (zzlz.zzc(bArr)) {
            this.zzc.set(i2, strZzb);
        }
        return strZzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        a();
        Object objRemove = this.zzc.remove(i2);
        ((AbstractList) this).modCount++;
        return zza(objRemove);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        a();
        return zza(this.zzc.set(i2, (String) obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc.size();
    }

    @Override // com.google.android.gms.internal.measurement.zzmf
    public final /* synthetic */ zzmf zza(int i2) {
        if (i2 < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i2);
        arrayList.addAll(this.zzc);
        return new zzmm((ArrayList<Object>) arrayList);
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final Object zzb(int i2) {
        return this.zzc.get(i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, com.google.android.gms.internal.measurement.zzmf
    public final /* bridge */ /* synthetic */ boolean zzc() {
        return super.zzc();
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final zzmp zzd() {
        return zzc() ? new zzpb(this) : this;
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final List<?> zze() {
        return Collections.unmodifiableList(this.zzc);
    }

    public zzmm(int i2) {
        this((ArrayList<Object>) new ArrayList(i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final boolean addAll(int i2, Collection<? extends String> collection) {
        a();
        if (collection instanceof zzmp) {
            collection = ((zzmp) collection).zze();
        }
        boolean zAddAll = this.zzc.addAll(i2, collection);
        ((AbstractList) this).modCount++;
        return zAddAll;
    }

    private zzmm(ArrayList<Object> arrayList) {
        this.zzc = arrayList;
    }

    private zzmm(boolean z2) {
        super(false);
        this.zzc = Collections.emptyList();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    private static String zza(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzkm) {
            return ((zzkm) obj).zzc();
        }
        return zzlz.zzb((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzmp
    public final void zza(zzkm zzkmVar) {
        a();
        this.zzc.add(zzkmVar);
        ((AbstractList) this).modCount++;
    }
}
