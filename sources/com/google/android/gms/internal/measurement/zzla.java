package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* loaded from: classes3.dex */
final class zzla extends zzkx {
    private final byte[] zzd;
    private final boolean zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;

    private final void zzaa() {
        int i2 = this.zzf + this.zzg;
        this.zzf = i2;
        int i3 = i2 - this.zzi;
        int i4 = this.zzk;
        if (i3 <= i4) {
            this.zzg = 0;
            return;
        }
        int i5 = i3 - i4;
        this.zzg = i5;
        this.zzf = i2 - i5;
    }

    private final byte zzv() throws IOException {
        int i2 = this.zzh;
        if (i2 == this.zzf) {
            throw zzme.zzh();
        }
        byte[] bArr = this.zzd;
        this.zzh = i2 + 1;
        return bArr[i2];
    }

    private final int zzw() throws IOException {
        int i2 = this.zzh;
        if (this.zzf - i2 < 4) {
            throw zzme.zzh();
        }
        byte[] bArr = this.zzd;
        this.zzh = i2 + 4;
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    private final int zzx() throws IOException {
        int i2;
        int i3 = this.zzh;
        int i4 = this.zzf;
        if (i4 != i3) {
            byte[] bArr = this.zzd;
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            if (b2 >= 0) {
                this.zzh = i5;
                return b2;
            }
            if (i4 - i5 >= 9) {
                int i6 = i3 + 2;
                int i7 = (bArr[i5] << 7) ^ b2;
                if (i7 < 0) {
                    i2 = i7 ^ (-128);
                } else {
                    int i8 = i3 + 3;
                    int i9 = (bArr[i6] << 14) ^ i7;
                    if (i9 >= 0) {
                        i2 = i9 ^ 16256;
                    } else {
                        int i10 = i3 + 4;
                        int i11 = i9 ^ (bArr[i8] << Ascii.NAK);
                        if (i11 < 0) {
                            i2 = (-2080896) ^ i11;
                        } else {
                            i8 = i3 + 5;
                            byte b3 = bArr[i10];
                            int i12 = (i11 ^ (b3 << Ascii.FS)) ^ 266354560;
                            if (b3 < 0) {
                                i10 = i3 + 6;
                                if (bArr[i8] < 0) {
                                    i8 = i3 + 7;
                                    if (bArr[i10] < 0) {
                                        i10 = i3 + 8;
                                        if (bArr[i8] < 0) {
                                            i8 = i3 + 9;
                                            if (bArr[i10] < 0) {
                                                int i13 = i3 + 10;
                                                if (bArr[i8] >= 0) {
                                                    i6 = i13;
                                                    i2 = i12;
                                                }
                                            }
                                        }
                                    }
                                }
                                i2 = i12;
                            }
                            i2 = i12;
                        }
                        i6 = i10;
                    }
                    i6 = i8;
                }
                this.zzh = i6;
                return i2;
            }
        }
        return (int) b();
    }

    private final long zzy() throws IOException {
        int i2 = this.zzh;
        if (this.zzf - i2 < 8) {
            throw zzme.zzh();
        }
        byte[] bArr = this.zzd;
        this.zzh = i2 + 8;
        return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
    }

    private final long zzz() throws IOException {
        long j2;
        long j3;
        long j4;
        int i2 = this.zzh;
        int i3 = this.zzf;
        if (i3 != i2) {
            byte[] bArr = this.zzd;
            int i4 = i2 + 1;
            byte b2 = bArr[i2];
            if (b2 >= 0) {
                this.zzh = i4;
                return b2;
            }
            if (i3 - i4 >= 9) {
                int i5 = i2 + 2;
                int i6 = (bArr[i4] << 7) ^ b2;
                if (i6 < 0) {
                    j2 = i6 ^ (-128);
                } else {
                    int i7 = i2 + 3;
                    int i8 = (bArr[i5] << 14) ^ i6;
                    if (i8 >= 0) {
                        j2 = i8 ^ 16256;
                        i5 = i7;
                    } else {
                        int i9 = i2 + 4;
                        int i10 = i8 ^ (bArr[i7] << Ascii.NAK);
                        if (i10 < 0) {
                            long j5 = (-2080896) ^ i10;
                            i5 = i9;
                            j2 = j5;
                        } else {
                            long j6 = i10;
                            i5 = i2 + 5;
                            long j7 = j6 ^ (bArr[i9] << 28);
                            if (j7 >= 0) {
                                j4 = 266354560;
                            } else {
                                int i11 = i2 + 6;
                                long j8 = j7 ^ (bArr[i5] << 35);
                                if (j8 < 0) {
                                    j3 = -34093383808L;
                                } else {
                                    i5 = i2 + 7;
                                    j7 = j8 ^ (bArr[i11] << 42);
                                    if (j7 >= 0) {
                                        j4 = 4363953127296L;
                                    } else {
                                        i11 = i2 + 8;
                                        j8 = j7 ^ (bArr[i5] << 49);
                                        if (j8 < 0) {
                                            j3 = -558586000294016L;
                                        } else {
                                            i5 = i2 + 9;
                                            long j9 = (j8 ^ (bArr[i11] << 56)) ^ 71499008037633920L;
                                            if (j9 < 0) {
                                                int i12 = i2 + 10;
                                                if (bArr[i5] >= 0) {
                                                    i5 = i12;
                                                }
                                            }
                                            j2 = j9;
                                        }
                                    }
                                }
                                j2 = j8 ^ j3;
                                i5 = i11;
                            }
                            j2 = j7 ^ j4;
                        }
                    }
                }
                this.zzh = i5;
                return j2;
            }
        }
        return b();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    final long b() throws IOException {
        long j2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            j2 |= (r3 & Byte.MAX_VALUE) << i2;
            if ((zzv() & 128) == 0) {
                return j2;
            }
        }
        throw zzme.zze();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final double zza() throws IOException {
        return Double.longBitsToDouble(zzy());
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final float zzb() throws IOException {
        return Float.intBitsToFloat(zzw());
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzc() {
        return this.zzh - this.zzi;
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzd() throws IOException {
        return zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zze() throws IOException {
        return zzw();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzf() throws IOException {
        return zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzg() throws IOException {
        return zzw();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzh() throws IOException {
        return zzkx.zza(zzx());
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzi() throws IOException {
        if (zzt()) {
            this.zzj = 0;
            return 0;
        }
        int iZzx = zzx();
        this.zzj = iZzx;
        if ((iZzx >>> 3) != 0) {
            return iZzx;
        }
        throw zzme.zzc();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzj() throws IOException {
        return zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final long zzk() throws IOException {
        return zzy();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final long zzl() throws IOException {
        return zzz();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final long zzn() throws IOException {
        return zzy();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final long zzo() throws IOException {
        return zzkx.zza(zzz());
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final long zzp() throws IOException {
        return zzz();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    @Override // com.google.android.gms.internal.measurement.zzkx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.measurement.zzkm zzq() throws java.io.IOException {
        /*
            r3 = this;
            int r0 = r3.zzx()
            if (r0 <= 0) goto L19
            int r1 = r3.zzf
            int r2 = r3.zzh
            int r1 = r1 - r2
            if (r0 > r1) goto L19
            byte[] r1 = r3.zzd
            com.google.android.gms.internal.measurement.zzkm r1 = com.google.android.gms.internal.measurement.zzkm.zza(r1, r2, r0)
            int r2 = r3.zzh
            int r2 = r2 + r0
            r3.zzh = r2
            return r1
        L19:
            if (r0 != 0) goto L1e
            com.google.android.gms.internal.measurement.zzkm r0 = com.google.android.gms.internal.measurement.zzkm.zza
            return r0
        L1e:
            if (r0 <= 0) goto L31
            int r1 = r3.zzf
            int r2 = r3.zzh
            int r1 = r1 - r2
            if (r0 > r1) goto L31
            int r0 = r0 + r2
            r3.zzh = r0
            byte[] r1 = r3.zzd
            byte[] r0 = java.util.Arrays.copyOfRange(r1, r2, r0)
            goto L37
        L31:
            if (r0 > 0) goto L41
            if (r0 != 0) goto L3c
            byte[] r0 = com.google.android.gms.internal.measurement.zzlz.zzb
        L37:
            com.google.android.gms.internal.measurement.zzkm r0 = com.google.android.gms.internal.measurement.zzkm.zza(r0)
            return r0
        L3c:
            com.google.android.gms.internal.measurement.zzme r0 = com.google.android.gms.internal.measurement.zzme.zzf()
            throw r0
        L41:
            com.google.android.gms.internal.measurement.zzme r0 = com.google.android.gms.internal.measurement.zzme.zzh()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzla.zzq():com.google.android.gms.internal.measurement.zzkm");
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final String zzr() throws IOException {
        int iZzx = zzx();
        if (iZzx > 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (iZzx <= i2 - i3) {
                String str = new String(this.zzd, i3, iZzx, zzlz.f13231a);
                this.zzh += iZzx;
                return str;
            }
        }
        if (iZzx == 0) {
            return "";
        }
        if (iZzx < 0) {
            throw zzme.zzf();
        }
        throw zzme.zzh();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final String zzs() throws IOException {
        int iZzx = zzx();
        if (iZzx > 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (iZzx <= i2 - i3) {
                String strE = zzpg.e(this.zzd, i3, iZzx);
                this.zzh += iZzx;
                return strE;
            }
        }
        if (iZzx == 0) {
            return "";
        }
        if (iZzx <= 0) {
            throw zzme.zzf();
        }
        throw zzme.zzh();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final boolean zzt() throws IOException {
        return this.zzh == this.zzf;
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final boolean zzu() throws IOException {
        return zzz() != 0;
    }

    private zzla(byte[] bArr, int i2, int i3, boolean z2) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzd = bArr;
        this.zzf = i3 + i2;
        this.zzh = i2;
        this.zzi = i2;
        this.zze = z2;
    }

    private final void zzf(int i2) throws IOException {
        if (i2 >= 0) {
            int i3 = this.zzf;
            int i4 = this.zzh;
            if (i2 <= i3 - i4) {
                this.zzh = i4 + i2;
                return;
            }
        }
        if (i2 >= 0) {
            throw zzme.zzh();
        }
        throw zzme.zzf();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final int zzb(int i2) throws zzme {
        if (i2 < 0) {
            throw zzme.zzf();
        }
        int iZzc = i2 + zzc();
        if (iZzc < 0) {
            throw zzme.zzg();
        }
        int i3 = this.zzk;
        if (iZzc > i3) {
            throw zzme.zzh();
        }
        this.zzk = iZzc;
        zzaa();
        return i3;
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final void zzc(int i2) throws zzme {
        if (this.zzj != i2) {
            throw zzme.zzb();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final void zzd(int i2) {
        this.zzk = i2;
        zzaa();
    }

    @Override // com.google.android.gms.internal.measurement.zzkx
    public final boolean zze(int i2) throws IOException {
        int iZzi;
        int i3 = i2 & 7;
        int i4 = 0;
        if (i3 == 0) {
            if (this.zzf - this.zzh < 10) {
                while (i4 < 10) {
                    if (zzv() < 0) {
                        i4++;
                    }
                }
                throw zzme.zze();
            }
            while (i4 < 10) {
                byte[] bArr = this.zzd;
                int i5 = this.zzh;
                this.zzh = i5 + 1;
                if (bArr[i5] < 0) {
                    i4++;
                }
            }
            throw zzme.zze();
            return true;
        }
        if (i3 == 1) {
            zzf(8);
            return true;
        }
        if (i3 == 2) {
            zzf(zzx());
            return true;
        }
        if (i3 != 3) {
            if (i3 == 4) {
                return false;
            }
            if (i3 != 5) {
                throw zzme.zza();
            }
            zzf(4);
            return true;
        }
        do {
            iZzi = zzi();
            if (iZzi == 0) {
                break;
            }
        } while (zze(iZzi));
        zzc(((i2 >>> 3) << 3) | 4);
        return true;
    }
}
