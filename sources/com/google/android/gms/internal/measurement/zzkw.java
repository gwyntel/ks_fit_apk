package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
class zzkw extends zzkt {
    protected final byte[] zzb;

    zzkw(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkm) || zzb() != ((zzkm) obj).zzb()) {
            return false;
        }
        if (zzb() == 0) {
            return true;
        }
        if (!(obj instanceof zzkw)) {
            return obj.equals(this);
        }
        zzkw zzkwVar = (zzkw) obj;
        int iZza = zza();
        int iZza2 = zzkwVar.zza();
        if (iZza == 0 || iZza2 == 0 || iZza == iZza2) {
            return zza(zzkwVar, 0, zzb());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    public byte zza(int i2) {
        return this.zzb[i2];
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    byte zzb(int i2) {
        return this.zzb[i2];
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    public final boolean zzd() {
        int iZze = zze();
        return zzpg.f(this.zzb, iZze, zzb() + iZze);
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    public final zzkm zza(int i2, int i3) {
        int iZza = zzkm.zza(0, i3, zzb());
        return iZza == 0 ? zzkm.zza : new zzkq(this.zzb, zze(), iZza);
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    protected final int zzb(int i2, int i3, int i4) {
        return zzlz.a(i2, this.zzb, zze(), i4);
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    public int zzb() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zzb(), charset);
    }

    @Override // com.google.android.gms.internal.measurement.zzkm
    final void zza(zzkj zzkjVar) throws IOException {
        zzkjVar.zza(this.zzb, zze(), zzb());
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final boolean zza(zzkm zzkmVar, int i2, int i3) {
        if (i3 <= zzkmVar.zzb()) {
            if (i3 <= zzkmVar.zzb()) {
                if (zzkmVar instanceof zzkw) {
                    zzkw zzkwVar = (zzkw) zzkmVar;
                    byte[] bArr = this.zzb;
                    byte[] bArr2 = zzkwVar.zzb;
                    int iZze = zze() + i3;
                    int iZze2 = zze();
                    int iZze3 = zzkwVar.zze();
                    while (iZze2 < iZze) {
                        if (bArr[iZze2] != bArr2[iZze3]) {
                            return false;
                        }
                        iZze2++;
                        iZze3++;
                    }
                    return true;
                }
                return zzkmVar.zza(0, i3).equals(zza(0, i3));
            }
            throw new IllegalArgumentException("Ran off end of other: 0, " + i3 + ", " + zzkmVar.zzb());
        }
        throw new IllegalArgumentException("Length too large: " + i3 + zzb());
    }
}
