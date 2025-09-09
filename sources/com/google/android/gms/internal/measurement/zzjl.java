package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzjl extends zzir<Object> {
    private final transient Object[] zza;
    private final transient int zzb;
    private final transient int zzc;

    zzjl(Object[] objArr, int i2, int i3) {
        this.zza = objArr;
        this.zzb = i2;
        this.zzc = i3;
    }

    @Override // java.util.List
    public final Object get(int i2) {
        zzhn.zza(i2, this.zzc);
        Object obj = this.zza[(i2 * 2) + this.zzb];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return true;
    }
}
