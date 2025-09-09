package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzjo<E> extends zzjc<E> {
    static final zzjo<Object> zza;
    private static final Object[] zzb;
    private final transient Object[] zzc;
    private final transient int zzd;
    private final transient Object[] zze;
    private final transient int zzf;
    private final transient int zzg;

    static {
        Object[] objArr = new Object[0];
        zzb = objArr;
        zza = new zzjo<>(objArr, 0, objArr, 0, 0);
    }

    zzjo(Object[] objArr, int i2, Object[] objArr2, int i3, int i4) {
        this.zzc = objArr;
        this.zzd = i2;
        this.zze = objArr2;
        this.zzf = i3;
        this.zzg = i4;
    }

    @Override // com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        Object[] objArr = this.zze;
        if (obj == null || objArr.length == 0) {
            return false;
        }
        int iB = zzin.b(obj);
        while (true) {
            int i2 = iB & this.zzf;
            Object obj2 = objArr[i2];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iB = i2 + 1;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc, com.google.android.gms.internal.measurement.zziq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza(Object[] objArr, int i2) {
        System.arraycopy(this.zzc, 0, objArr, i2, this.zzg);
        return i2 + this.zzg;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zzb() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    /* renamed from: zzd */
    public final zzjp<E> iterator() {
        return (zzjp) zzc().iterator();
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final Object[] zzf() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    final zzir<E> zzg() {
        return zzir.zzb(this.zzc, this.zzg);
    }

    @Override // com.google.android.gms.internal.measurement.zzjc
    final boolean zzh() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza() {
        return this.zzg;
    }
}
