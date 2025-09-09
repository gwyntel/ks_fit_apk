package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
public final class zzfu {
    private final int zza;
    private final boolean zzb;
    private final boolean zzc;
    private final /* synthetic */ zzfs zzd;

    zzfu(zzfs zzfsVar, int i2, boolean z2, boolean z3) {
        this.zzd = zzfsVar;
        this.zza = i2;
        this.zzb = z2;
        this.zzc = z3;
    }

    public final void zza(String str) {
        this.zzd.g(this.zza, this.zzb, this.zzc, str, null, null, null);
    }

    public final void zza(String str, Object obj) {
        this.zzd.g(this.zza, this.zzb, this.zzc, str, obj, null, null);
    }

    public final void zza(String str, Object obj, Object obj2) {
        this.zzd.g(this.zza, this.zzb, this.zzc, str, obj, obj2, null);
    }

    public final void zza(String str, Object obj, Object obj2, Object obj3) throws IllegalStateException {
        this.zzd.g(this.zza, this.zzb, this.zzc, str, obj, obj2, obj3);
    }
}
