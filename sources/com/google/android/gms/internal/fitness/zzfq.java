package com.google.android.gms.internal.fitness;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
final class zzfq extends zzfm {
    static final zzfm zza = new zzfq(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzfq(Object[] objArr, int i2) {
        this.zzb = objArr;
        this.zzc = i2;
    }

    @Override // java.util.List
    public final Object get(int i2) {
        zzff.zza(i2, this.zzc, FirebaseAnalytics.Param.INDEX);
        Object obj = this.zzb[i2];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fitness.zzfm, com.google.android.gms.internal.fitness.zzfj
    final int zza(Object[] objArr, int i2) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final Object[] zze() {
        return this.zzb;
    }
}
