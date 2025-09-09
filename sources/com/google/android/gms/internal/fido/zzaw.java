package com.google.android.gms.internal.fido;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
final class zzaw extends zzat {
    static final zzat zza = new zzaw(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzaw(Object[] objArr, int i2) {
        this.zzb = objArr;
        this.zzc = i2;
    }

    @Override // java.util.List
    public final Object get(int i2) {
        zzam.zza(i2, this.zzc, FirebaseAnalytics.Param.INDEX);
        Object obj = this.zzb[i2];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzat, com.google.android.gms.internal.fido.zzaq
    final int zza(Object[] objArr, int i2) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzaq
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.fido.zzaq
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.fido.zzaq
    final Object[] zze() {
        return this.zzb;
    }
}
