package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* loaded from: classes3.dex */
final class zzib<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Object zza = new Object();

    @CheckForNull
    private transient Object zzb;

    @CheckForNull
    private transient int[] zzc;

    @CheckForNull
    private transient Object[] zzd;

    @CheckForNull
    private transient Object[] zze;
    private transient int zzf;
    private transient int zzg;

    @CheckForNull
    private transient Set<K> zzh;

    @CheckForNull
    private transient Set<Map.Entry<K, V>> zzi;

    @CheckForNull
    private transient Collection<V> zzj;

    zzib() {
        zzhn.zza(true, (Object) "Expected size must be >= 0");
        this.zzf = zzjr.zza(3, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    static int zza(int i2, int i3) {
        return i2 - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zzi() {
        return (1 << (this.zzf & 31)) - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object zzj() {
        Object obj = this.zzb;
        obj.getClass();
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int[] zzk() {
        int[] iArr = this.zzc;
        iArr.getClass();
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object[] zzl() {
        Object[] objArr = this.zzd;
        objArr.getClass();
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object[] zzm() {
        Object[] objArr = this.zze;
        objArr.getClass();
        return objArr;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (zzh()) {
            return;
        }
        zzg();
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            this.zzf = zzjr.zza(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            mapZzf.clear();
            this.zzb = null;
            this.zzg = 0;
            return;
        }
        Arrays.fill(zzl(), 0, this.zzg, (Object) null);
        Arrays.fill(zzm(), 0, this.zzg, (Object) null);
        Object objZzj = zzj();
        if (objZzj instanceof byte[]) {
            Arrays.fill((byte[]) objZzj, (byte) 0);
        } else if (objZzj instanceof short[]) {
            Arrays.fill((short[]) objZzj, (short) 0);
        } else {
            Arrays.fill((int[]) objZzj, 0);
        }
        Arrays.fill(zzk(), 0, this.zzg, 0);
        this.zzg = 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(@CheckForNull Object obj) {
        Map<K, V> mapZzf = zzf();
        return mapZzf != null ? mapZzf.containsKey(obj) : zza(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(@CheckForNull Object obj) {
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            return mapZzf.containsValue(obj);
        }
        for (int i2 = 0; i2 < this.zzg; i2++) {
            if (zzhl.zza(obj, zzm()[i2])) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.zzi;
        if (set != null) {
            return set;
        }
        zzif zzifVar = new zzif(this);
        this.zzi = zzifVar;
        return zzifVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V get(@CheckForNull Object obj) {
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            return mapZzf.get(obj);
        }
        int iZza = zza(obj);
        if (iZza == -1) {
            return null;
        }
        return (V) zzm()[iZza];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        Set<K> set = this.zzh;
        if (set != null) {
            return set;
        }
        zzik zzikVar = new zzik(this);
        this.zzh = zzikVar;
        return zzikVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V put(K k2, V v2) {
        int iMin;
        if (zzh()) {
            zzhn.zzb(zzh(), "Arrays already allocated");
            int i2 = this.zzf;
            int iMax = Math.max(i2 + 1, 2);
            int iHighestOneBit = Integer.highestOneBit(iMax);
            int iMax2 = Math.max(4, (iMax <= ((int) (((double) iHighestOneBit) * 1.0d)) || (iHighestOneBit = iHighestOneBit << 1) > 0) ? iHighestOneBit : 1073741824);
            this.zzb = zzil.f(iMax2);
            zzb(iMax2 - 1);
            this.zzc = new int[i2];
            this.zzd = new Object[i2];
            this.zze = new Object[i2];
        }
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            return mapZzf.put(k2, v2);
        }
        int[] iArrZzk = zzk();
        Object[] objArrZzl = zzl();
        Object[] objArrZzm = zzm();
        int i3 = this.zzg;
        int i4 = i3 + 1;
        int iB = zzin.b(k2);
        int iZzi = zzi();
        int i5 = iB & iZzi;
        int iC = zzil.c(zzj(), i5);
        if (iC != 0) {
            int i6 = ~iZzi;
            int i7 = iB & i6;
            int i8 = 0;
            while (true) {
                int i9 = iC - 1;
                int i10 = iArrZzk[i9];
                if ((i10 & i6) == i7 && zzhl.zza(k2, objArrZzl[i9])) {
                    V v3 = (V) objArrZzm[i9];
                    objArrZzm[i9] = v2;
                    return v3;
                }
                int i11 = i10 & iZzi;
                Object[] objArr = objArrZzl;
                int i12 = i8 + 1;
                if (i11 != 0) {
                    i8 = i12;
                    iC = i11;
                    objArrZzl = objArr;
                } else {
                    if (i12 >= 9) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap(zzi() + 1, 1.0f);
                        int iZza = zza();
                        while (iZza >= 0) {
                            linkedHashMap.put(zzl()[iZza], zzm()[iZza]);
                            iZza = zza(iZza);
                        }
                        this.zzb = linkedHashMap;
                        this.zzc = null;
                        this.zzd = null;
                        this.zze = null;
                        zzg();
                        return (V) linkedHashMap.put(k2, v2);
                    }
                    if (i4 > iZzi) {
                        iZzi = zza(iZzi, zzil.a(iZzi), iB, i3);
                    } else {
                        iArrZzk[i9] = zzil.b(i10, i4, iZzi);
                    }
                }
            }
        } else if (i4 > iZzi) {
            iZzi = zza(iZzi, zzil.a(iZzi), iB, i3);
        } else {
            zzil.e(zzj(), i5, i4);
        }
        int length = zzk().length;
        if (i4 > length && (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, 1 | (Math.max(1, length >>> 1) + length))) != length) {
            this.zzc = Arrays.copyOf(zzk(), iMin);
            this.zzd = Arrays.copyOf(zzl(), iMin);
            this.zze = Arrays.copyOf(zzm(), iMin);
        }
        zzk()[i3] = zzil.b(iB, 0, iZzi);
        zzl()[i3] = k2;
        zzm()[i3] = v2;
        this.zzg = i4;
        zzg();
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V remove(@CheckForNull Object obj) {
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            return mapZzf.remove(obj);
        }
        V v2 = (V) zzb(obj);
        if (v2 == zza) {
            return null;
        }
        return v2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        Map<K, V> mapZzf = zzf();
        return mapZzf != null ? mapZzf.size() : this.zzg;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzj;
        if (collection != null) {
            return collection;
        }
        zzim zzimVar = new zzim(this);
        this.zzj = zzimVar;
        return zzimVar;
    }

    final boolean zzh() {
        return this.zzb == null;
    }

    final Iterator<K> zzd() {
        Map<K, V> mapZzf = zzf();
        return mapZzf != null ? mapZzf.keySet().iterator() : new zzie(this);
    }

    final Iterator<V> zze() {
        Map<K, V> mapZzf = zzf();
        return mapZzf != null ? mapZzf.values().iterator() : new zzig(this);
    }

    @CheckForNull
    final Map<K, V> zzf() {
        Object obj = this.zzb;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    final void zzg() {
        this.zzf += 32;
    }

    final Iterator<Map.Entry<K, V>> zzc() {
        Map<K, V> mapZzf = zzf();
        if (mapZzf != null) {
            return mapZzf.entrySet().iterator();
        }
        return new zzid(this);
    }

    static /* synthetic */ Object zzb(zzib zzibVar, int i2) {
        return zzibVar.zzm()[i2];
    }

    final int zza() {
        return isEmpty() ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object zzb(@CheckForNull Object obj) {
        if (zzh()) {
            return zza;
        }
        int iZzi = zzi();
        int iD = zzil.d(obj, null, iZzi, zzj(), zzk(), zzl(), null);
        if (iD == -1) {
            return zza;
        }
        Object obj2 = zzm()[iD];
        zzb(iD, iZzi);
        this.zzg--;
        zzg();
        return obj2;
    }

    final int zza(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.zzg) {
            return i3;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zza(@CheckForNull Object obj) {
        if (zzh()) {
            return -1;
        }
        int iB = zzin.b(obj);
        int iZzi = zzi();
        int iC = zzil.c(zzj(), iB & iZzi);
        if (iC == 0) {
            return -1;
        }
        int i2 = ~iZzi;
        int i3 = iB & i2;
        do {
            int i4 = iC - 1;
            int i5 = zzk()[i4];
            if ((i5 & i2) == i3 && zzhl.zza(obj, zzl()[i4])) {
                return i4;
            }
            iC = i5 & iZzi;
        } while (iC != 0);
        return -1;
    }

    private final int zza(int i2, int i3, int i4, int i5) {
        Object objF = zzil.f(i3);
        int i6 = i3 - 1;
        if (i5 != 0) {
            zzil.e(objF, i4 & i6, i5 + 1);
        }
        Object objZzj = zzj();
        int[] iArrZzk = zzk();
        for (int i7 = 0; i7 <= i2; i7++) {
            int iC = zzil.c(objZzj, i7);
            while (iC != 0) {
                int i8 = iC - 1;
                int i9 = iArrZzk[i8];
                int i10 = ((~i2) & i9) | i7;
                int i11 = i10 & i6;
                int iC2 = zzil.c(objF, i11);
                zzil.e(objF, i11, iC);
                iArrZzk[i8] = zzil.b(i10, iC2, i6);
                iC = i9 & i2;
            }
        }
        this.zzb = objF;
        zzb(i6);
        return i6;
    }

    final void zzb(int i2, int i3) {
        Object objZzj = zzj();
        int[] iArrZzk = zzk();
        Object[] objArrZzl = zzl();
        Object[] objArrZzm = zzm();
        int size = size();
        int i4 = size - 1;
        if (i2 < i4) {
            Object obj = objArrZzl[i4];
            objArrZzl[i2] = obj;
            objArrZzm[i2] = objArrZzm[i4];
            objArrZzl[i4] = null;
            objArrZzm[i4] = null;
            iArrZzk[i2] = iArrZzk[i4];
            iArrZzk[i4] = 0;
            int iB = zzin.b(obj) & i3;
            int iC = zzil.c(objZzj, iB);
            if (iC == size) {
                zzil.e(objZzj, iB, i2 + 1);
                return;
            }
            while (true) {
                int i5 = iC - 1;
                int i6 = iArrZzk[i5];
                int i7 = i6 & i3;
                if (i7 == size) {
                    iArrZzk[i5] = zzil.b(i6, i2 + 1, i3);
                    return;
                }
                iC = i7;
            }
        } else {
            objArrZzl[i2] = null;
            objArrZzm[i2] = null;
            iArrZzk[i2] = 0;
        }
    }

    static /* synthetic */ Object zza(zzib zzibVar, int i2) {
        return zzibVar.zzl()[i2];
    }

    static /* synthetic */ void zza(zzib zzibVar, int i2, Object obj) {
        zzibVar.zzm()[i2] = obj;
    }

    private final void zzb(int i2) {
        this.zzf = zzil.b(this.zzf, 32 - Integer.numberOfLeadingZeros(i2), 31);
    }
}
