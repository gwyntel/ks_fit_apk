package com.google.android.gms.internal.measurement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public abstract class zzir<E> extends zziq<E> implements List<E>, RandomAccess {
    private static final zzjs<Object> zza = new zzit(zzji.zza, 0);

    zzir() {
    }

    static <E> zzir<E> zzb(Object[] objArr, int i2) {
        return i2 == 0 ? (zzir<E>) zzji.zza : new zzji(objArr, i2);
    }

    public static <E> zzir<E> zzg() {
        return (zzir<E>) zzji.zza;
    }

    @Override // java.util.List
    @Deprecated
    public final void add(int i2, E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final boolean addAll(int i2, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@CheckForNull Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(@CheckForNull Object obj) {
        if (obj == zzhn.zza(this)) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (list instanceof RandomAccess) {
                    for (int i2 = 0; i2 < size; i2++) {
                        if (zzhl.zza(get(i2), list.get(i2))) {
                        }
                    }
                    return true;
                }
                int size2 = size();
                Iterator<E> it = list.iterator();
                int i3 = 0;
                while (true) {
                    if (i3 < size2) {
                        if (!it.hasNext()) {
                            break;
                        }
                        E e2 = get(i3);
                        i3++;
                        if (!zzhl.zza(e2, it.next())) {
                            break;
                        }
                    } else if (!it.hasNext()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        int size = size();
        int i2 = 1;
        for (int i3 = 0; i3 < size; i3++) {
            i2 = ~(~((i2 * 31) + get(i3).hashCode()));
        }
        return i2;
    }

    @Override // java.util.List
    public int indexOf(@CheckForNull Object obj) {
        if (obj == null) {
            return -1;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (obj.equals(get(i2))) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(@CheckForNull Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int size = size() - 1; size >= 0; size--) {
            if (obj.equals(get(size))) {
                return size;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public /* synthetic */ ListIterator listIterator() {
        return (zzjs) listIterator(0);
    }

    @Override // java.util.List
    @Deprecated
    public final E remove(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final E set(int i2, E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    int zza(Object[] objArr, int i2) {
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            objArr[i2 + i3] = get(i3);
        }
        return i2 + size;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    @Deprecated
    public final zzir<E> zzc() {
        return this;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    /* renamed from: zzd */
    public final zzjp<E> iterator() {
        return (zzjs) listIterator();
    }

    @Override // java.util.List
    public /* synthetic */ ListIterator listIterator(int i2) {
        zzhn.zzb(i2, size());
        return isEmpty() ? zza : new zzit(this, i2);
    }

    static <E> zzir<E> zza(Object[] objArr) {
        return zzb(objArr, objArr.length);
    }

    private static <E> zzir<E> zzb(Object... objArr) {
        zzjf.b(objArr, objArr.length);
        return zzb(objArr, objArr.length);
    }

    public static <E> zzir<E> zza(E e2) {
        return zzb(e2);
    }

    public static <E> zzir<E> zza(E e2, E e3) {
        return zzb(e2, e3);
    }

    public static <E> zzir<E> zza(E e2, E e3, E e4) {
        return zzb(e2, e3, e4);
    }

    public static <E> zzir<E> zza(E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return zzb(e2, e3, e4, e5, e6, e7, e8);
    }

    @Override // java.util.List
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public zzir<E> subList(int i2, int i3) {
        zzhn.zza(i2, i3, size());
        int i4 = i3 - i2;
        if (i4 == size()) {
            return this;
        }
        if (i4 == 0) {
            return (zzir<E>) zzji.zza;
        }
        return new zziw(this, i2, i4);
    }
}
