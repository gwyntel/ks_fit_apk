package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzji<E> extends zzir<E> {
    static final zzir<Object> zza = new zzji(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzji(Object[] objArr, int i2) {
        this.zzb = objArr;
        this.zzc = i2;
    }

    @Override // java.util.List
    public final E get(int i2) {
        zzhn.zza(i2, this.zzc);
        E e2 = (E) this.zzb[i2];
        e2.getClass();
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzir, com.google.android.gms.internal.measurement.zziq
    final int zza(Object[] objArr, int i2) {
        System.arraycopy(this.zzb, 0, objArr, i2, this.zzc);
        return i2 + this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zzb() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final Object[] zzf() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza() {
        return this.zzc;
    }
}
