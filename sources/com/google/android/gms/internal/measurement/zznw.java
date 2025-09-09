package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zznw<E> extends zzkg<E> implements RandomAccess {
    private static final zznw<Object> zza = new zznw<>(new Object[0], 0, false);
    private E[] zzb;
    private int zzc;

    private zznw(E[] eArr, int i2, boolean z2) {
        super(z2);
        this.zzb = eArr;
        this.zzc = i2;
    }

    private final String zzb(int i2) {
        return "Index:" + i2 + ", Size:" + this.zzc;
    }

    private final void zzc(int i2) {
        if (i2 < 0 || i2 >= this.zzc) {
            throw new IndexOutOfBoundsException(zzb(i2));
        }
    }

    public static <E> zznw<E> zzd() {
        return (zznw<E>) zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final void add(int i2, E e2) {
        int i3;
        a();
        if (i2 < 0 || i2 > (i3 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzb(i2));
        }
        E[] eArr = this.zzb;
        if (i3 < eArr.length) {
            System.arraycopy(eArr, i2, eArr, i2 + 1, i3 - i2);
        } else {
            E[] eArr2 = (E[]) new Object[((i3 * 3) / 2) + 1];
            System.arraycopy(eArr, 0, eArr2, 0, i2);
            System.arraycopy(this.zzb, i2, eArr2, i2 + 1, this.zzc - i2);
            this.zzb = eArr2;
        }
        this.zzb[i2] = e2;
        this.zzc++;
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final E get(int i2) {
        zzc(i2);
        return this.zzb[i2];
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final E remove(int i2) {
        a();
        zzc(i2);
        E[] eArr = this.zzb;
        E e2 = eArr[i2];
        if (i2 < this.zzc - 1) {
            System.arraycopy(eArr, i2 + 1, eArr, i2, (r2 - i2) - 1);
        }
        this.zzc--;
        ((AbstractList) this).modCount++;
        return e2;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.List
    public final E set(int i2, E e2) {
        a();
        zzc(i2);
        E[] eArr = this.zzb;
        E e3 = eArr[i2];
        eArr[i2] = e2;
        ((AbstractList) this).modCount++;
        return e3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzmf
    public final /* synthetic */ zzmf zza(int i2) {
        if (i2 >= this.zzc) {
            return new zznw(Arrays.copyOf(this.zzb, i2), this.zzc, true);
        }
        throw new IllegalArgumentException();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(E e2) {
        a();
        int i2 = this.zzc;
        E[] eArr = this.zzb;
        if (i2 == eArr.length) {
            this.zzb = (E[]) Arrays.copyOf(eArr, ((i2 * 3) / 2) + 1);
        }
        E[] eArr2 = this.zzb;
        int i3 = this.zzc;
        this.zzc = i3 + 1;
        eArr2[i3] = e2;
        ((AbstractList) this).modCount++;
        return true;
    }
}
