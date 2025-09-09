package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzdz extends zzec {
    private final int zzc;

    zzdz(byte[] bArr, int i2, int i3) {
        super(bArr);
        zzef.zzi(0, i3, bArr.length);
        this.zzc = i3;
    }

    @Override // com.google.android.gms.internal.auth.zzec, com.google.android.gms.internal.auth.zzef
    public final byte zza(int i2) {
        int i3 = this.zzc;
        if (((i3 - (i2 + 1)) | i2) >= 0) {
            return this.zza[i2];
        }
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i2);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i2 + ", " + i3);
    }

    @Override // com.google.android.gms.internal.auth.zzec, com.google.android.gms.internal.auth.zzef
    final byte zzb(int i2) {
        return this.zza[i2];
    }

    @Override // com.google.android.gms.internal.auth.zzec
    protected final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.auth.zzec, com.google.android.gms.internal.auth.zzef
    public final int zzd() {
        return this.zzc;
    }
}
