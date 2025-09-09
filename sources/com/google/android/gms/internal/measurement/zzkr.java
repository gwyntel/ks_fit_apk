package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzkr {
    private final zzld zza;
    private final byte[] zzb;

    public final zzkm zza() {
        this.zza.zzb();
        return new zzkw(this.zzb);
    }

    public final zzld zzb() {
        return this.zza;
    }

    private zzkr(int i2) {
        byte[] bArr = new byte[i2];
        this.zzb = bArr;
        this.zza = zzld.zzb(bArr);
    }
}
