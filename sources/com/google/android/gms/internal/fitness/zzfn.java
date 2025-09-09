package com.google.android.gms.internal.fitness;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public abstract class zzfn extends zzfj implements Set {

    @CheckForNull
    private transient zzfm zza;

    zzfn() {
    }

    static int zzf(int i2) {
        int iMax = Math.max(i2, 2);
        if (iMax >= 751619276) {
            if (iMax < 1073741824) {
                return 1073741824;
            }
            throw new IllegalArgumentException("collection too large");
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1);
        do {
            iHighestOneBit += iHighestOneBit;
        } while (iHighestOneBit * 0.7d < iMax);
        return iHighestOneBit;
    }

    public static zzfn zzi(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return zzk(5, obj, obj2, obj3, obj4, obj5);
    }

    private static zzfn zzk(int i2, Object... objArr) {
        if (i2 == 0) {
            return zzfr.zza;
        }
        if (i2 == 1) {
            Object obj = objArr[0];
            obj.getClass();
            return new zzfs(obj);
        }
        int iZzf = zzf(i2);
        Object[] objArr2 = new Object[iZzf];
        int i3 = iZzf - 1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            Object obj2 = objArr[i6];
            zzfp.a(obj2, i6);
            int iHashCode = obj2.hashCode();
            int iA = zzfi.a(iHashCode);
            while (true) {
                int i7 = iA & i3;
                Object obj3 = objArr2[i7];
                if (obj3 == null) {
                    objArr[i5] = obj2;
                    objArr2[i7] = obj2;
                    i4 += iHashCode;
                    i5++;
                    break;
                }
                if (obj3.equals(obj2)) {
                    break;
                }
                iA++;
            }
        }
        Arrays.fill(objArr, i5, i2, (Object) null);
        if (i5 == 1) {
            Object obj4 = objArr[0];
            obj4.getClass();
            return new zzfs(obj4);
        }
        if (zzf(i5) < iZzf / 2) {
            return zzk(i5, objArr);
        }
        if (i5 < 3) {
            objArr = Arrays.copyOf(objArr, i5);
        }
        return new zzfr(objArr, i4, objArr2, i3, i5);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzfn) && zzj() && ((zzfn) obj).zzj() && hashCode() != obj.hashCode()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size()) {
                    if (containsAll(set)) {
                        return true;
                    }
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        Iterator it = iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            Object next = it.next();
            iHashCode += next != null ? next.hashCode() : 0;
        }
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* renamed from: zzd */
    public abstract zzft iterator();

    public final zzfm zzg() {
        zzfm zzfmVar = this.zza;
        if (zzfmVar != null) {
            return zzfmVar;
        }
        zzfm zzfmVarZzh = zzh();
        this.zza = zzfmVarZzh;
        return zzfmVarZzh;
    }

    zzfm zzh() {
        return zzfm.zzg(toArray());
    }

    boolean zzj() {
        return false;
    }
}
