package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzkq extends zzkw {
    private final int zzc;
    private final int zzd;

    zzkq(byte[] bArr, int i2, int i3) {
        super(bArr);
        zzkm.zza(i2, i2 + i3, bArr.length);
        this.zzc = i2;
        this.zzd = i3;
    }

    @Override // com.google.android.gms.internal.measurement.zzkw, com.google.android.gms.internal.measurement.zzkm
    public final byte zza(int i2) {
        int iZzb = zzb();
        if (((iZzb - (i2 + 1)) | i2) >= 0) {
            return this.zzb[this.zzc + i2];
        }
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i2);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i2 + ", " + iZzb);
    }

    @Override // com.google.android.gms.internal.measurement.zzkw, com.google.android.gms.internal.measurement.zzkm
    final byte zzb(int i2) {
        return this.zzb[this.zzc + i2];
    }

    @Override // com.google.android.gms.internal.measurement.zzkw
    protected final int zze() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzkw, com.google.android.gms.internal.measurement.zzkm
    public final int zzb() {
        return this.zzd;
    }
}
