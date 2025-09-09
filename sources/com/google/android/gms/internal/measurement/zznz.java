package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zznz implements zznh {
    private final zznj zza;
    private final String zzb;
    private final Object[] zzc;
    private final int zzd;

    zznz(zznj zznjVar, String str, Object[] objArr) {
        this.zza = zznjVar;
        this.zzb = str;
        this.zzc = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.zzd = cCharAt;
            return;
        }
        int i2 = cCharAt & 8191;
        int i3 = 13;
        int i4 = 1;
        while (true) {
            int i5 = i4 + 1;
            char cCharAt2 = str.charAt(i4);
            if (cCharAt2 < 55296) {
                this.zzd = i2 | (cCharAt2 << i3);
                return;
            } else {
                i2 |= (cCharAt2 & 8191) << i3;
                i3 += 13;
                i4 = i5;
            }
        }
    }

    final String a() {
        return this.zzb;
    }

    final Object[] b() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zznh
    public final zznj zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zznh
    public final zznu zzb() {
        int i2 = this.zzd;
        return (i2 & 1) != 0 ? zznu.PROTO2 : (i2 & 4) == 4 ? zznu.EDITIONS : zznu.PROTO3;
    }

    @Override // com.google.android.gms.internal.measurement.zznh
    public final boolean zzc() {
        return (this.zzd & 2) == 2;
    }
}
