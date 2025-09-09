package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzeh extends zzej {
    private final byte[] zzb;
    private int zzc;
    private int zzd;
    private int zze;

    /* synthetic */ zzeh(byte[] bArr, int i2, int i3, boolean z2, zzeg zzegVar) {
        super(null);
        this.zze = Integer.MAX_VALUE;
        this.zzb = bArr;
        this.zzc = 0;
    }

    public final int zza(int i2) throws zzfb {
        int i3 = this.zze;
        this.zze = 0;
        int i4 = this.zzc + this.zzd;
        this.zzc = i4;
        if (i4 > 0) {
            this.zzd = i4;
            this.zzc = 0;
        } else {
            this.zzd = 0;
        }
        return i3;
    }
}
