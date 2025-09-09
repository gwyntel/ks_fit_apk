package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public abstract class zzjc<E> extends zziq<E> implements Set<E> {

    @CheckForNull
    private transient zzir<E> zza;

    zzjc() {
    }

    static int zza(int i2) {
        int iMax = Math.max(i2, 2);
        if (iMax >= 751619276) {
            zzhn.zza(iMax < 1073741824, "collection too large");
            return 1073741824;
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1;
        while (iHighestOneBit * 0.7d < iMax) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzjc) && zzh() && ((zzjc) obj).zzh() && hashCode() != obj.hashCode()) {
            return false;
        }
        return zzjn.b(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return zzjn.a(this);
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    public zzir<E> zzc() {
        zzir<E> zzirVar = this.zza;
        if (zzirVar != null) {
            return zzirVar;
        }
        zzir<E> zzirVarZzg = zzg();
        this.zza = zzirVarZzg;
        return zzirVarZzg;
    }

    zzir<E> zzg() {
        return zzir.zza(toArray());
    }

    boolean zzh() {
        return false;
    }

    private static <E> zzjc<E> zza(int i2, Object... objArr) {
        while (i2 != 0) {
            if (i2 != 1) {
                int iZza = zza(i2);
                Object[] objArr2 = new Object[iZza];
                int i3 = iZza - 1;
                int i4 = 0;
                int i5 = 0;
                for (int i6 = 0; i6 < i2; i6++) {
                    Object objA = zzjf.a(objArr[i6], i6);
                    int iHashCode = objA.hashCode();
                    int iA = zzin.a(iHashCode);
                    while (true) {
                        int i7 = iA & i3;
                        Object obj = objArr2[i7];
                        if (obj == null) {
                            objArr[i5] = objA;
                            objArr2[i7] = objA;
                            i4 += iHashCode;
                            i5++;
                            break;
                        }
                        if (!obj.equals(objA)) {
                            iA++;
                        }
                    }
                }
                Arrays.fill(objArr, i5, i2, (Object) null);
                if (i5 == 1) {
                    Object obj2 = objArr[0];
                    obj2.getClass();
                    return new zzjq(obj2);
                }
                if (zza(i5) >= iZza / 2) {
                    int length = objArr.length;
                    if (i5 < (length >> 1) + (length >> 2)) {
                        objArr = Arrays.copyOf(objArr, i5);
                    }
                    return new zzjo(objArr, i4, objArr2, i3, i5);
                }
                i2 = i5;
            } else {
                Object obj3 = objArr[0];
                obj3.getClass();
                return new zzjq(obj3);
            }
        }
        return zzjo.zza;
    }

    public static <E> zzjc<E> zza(Collection<? extends E> collection) {
        if ((collection instanceof zzjc) && !(collection instanceof SortedSet)) {
            zzjc<E> zzjcVar = (zzjc) collection;
            if (!zzjcVar.zze()) {
                return zzjcVar;
            }
        }
        Object[] array = collection.toArray();
        return zza(array.length, array);
    }

    public static <E> zzjc<E> zza(E e2, E e3, E e4) {
        return zza(3, e2, e3, e4);
    }

    @SafeVarargs
    public static <E> zzjc<E> zza(E e2, E e3, E e4, E e5, E e6, E e7, E... eArr) {
        zzhn.zza(eArr.length <= 2147483641, "the total number of elements must fit in an int");
        int length = eArr.length + 6;
        Object[] objArr = new Object[length];
        objArr[0] = e2;
        objArr[1] = e3;
        objArr[2] = e4;
        objArr[3] = e5;
        objArr[4] = e6;
        objArr[5] = e7;
        System.arraycopy(eArr, 0, objArr, 6, eArr.length);
        return zza(length, objArr);
    }
}
