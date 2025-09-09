package com.google.android.gms.internal.auth;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzdu {
    static int a(byte[] bArr, int i2, zzdt zzdtVar) {
        int iH = h(bArr, i2, zzdtVar);
        int i3 = zzdtVar.zza;
        if (i3 < 0) {
            throw zzfb.zzc();
        }
        if (i3 > bArr.length - iH) {
            throw zzfb.zzf();
        }
        if (i3 == 0) {
            zzdtVar.zzc = zzef.zzb;
            return iH;
        }
        zzdtVar.zzc = zzef.zzk(bArr, iH, i3);
        return iH + i3;
    }

    static int b(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = bArr[i2 + 1] & 255;
        int i5 = bArr[i2 + 2] & 255;
        return ((bArr[i2 + 3] & 255) << 24) | (i4 << 8) | i3 | (i5 << 16);
    }

    static int c(zzgi zzgiVar, byte[] bArr, int i2, int i3, int i4, zzdt zzdtVar) {
        Object objZzd = zzgiVar.zzd();
        int iL = l(objZzd, zzgiVar, bArr, i2, i3, i4, zzdtVar);
        zzgiVar.zze(objZzd);
        zzdtVar.zzc = objZzd;
        return iL;
    }

    static int d(zzgi zzgiVar, byte[] bArr, int i2, int i3, zzdt zzdtVar) {
        Object objZzd = zzgiVar.zzd();
        int iM = m(objZzd, zzgiVar, bArr, i2, i3, zzdtVar);
        zzgiVar.zze(objZzd);
        zzdtVar.zzc = objZzd;
        return iM;
    }

    static int e(zzgi zzgiVar, int i2, byte[] bArr, int i3, int i4, zzez zzezVar, zzdt zzdtVar) {
        int iD = d(zzgiVar, bArr, i3, i4, zzdtVar);
        zzezVar.add(zzdtVar.zzc);
        while (iD < i4) {
            int iH = h(bArr, iD, zzdtVar);
            if (i2 != zzdtVar.zza) {
                break;
            }
            iD = d(zzgiVar, bArr, iH, i4, zzdtVar);
            zzezVar.add(zzdtVar.zzc);
        }
        return iD;
    }

    static int f(byte[] bArr, int i2, zzez zzezVar, zzdt zzdtVar) {
        zzew zzewVar = (zzew) zzezVar;
        int iH = h(bArr, i2, zzdtVar);
        int i3 = zzdtVar.zza + iH;
        while (iH < i3) {
            iH = h(bArr, iH, zzdtVar);
            zzewVar.zze(zzdtVar.zza);
        }
        if (iH == i3) {
            return iH;
        }
        throw zzfb.zzf();
    }

    static int g(int i2, byte[] bArr, int i3, int i4, zzha zzhaVar, zzdt zzdtVar) {
        if ((i2 >>> 3) == 0) {
            throw zzfb.zza();
        }
        int i5 = i2 & 7;
        if (i5 == 0) {
            int iK = k(bArr, i3, zzdtVar);
            zzhaVar.f(i2, Long.valueOf(zzdtVar.zzb));
            return iK;
        }
        if (i5 == 1) {
            zzhaVar.f(i2, Long.valueOf(n(bArr, i3)));
            return i3 + 8;
        }
        if (i5 == 2) {
            int iH = h(bArr, i3, zzdtVar);
            int i6 = zzdtVar.zza;
            if (i6 < 0) {
                throw zzfb.zzc();
            }
            if (i6 > bArr.length - iH) {
                throw zzfb.zzf();
            }
            if (i6 == 0) {
                zzhaVar.f(i2, zzef.zzb);
            } else {
                zzhaVar.f(i2, zzef.zzk(bArr, iH, i6));
            }
            return iH + i6;
        }
        if (i5 != 3) {
            if (i5 != 5) {
                throw zzfb.zza();
            }
            zzhaVar.f(i2, Integer.valueOf(b(bArr, i3)));
            return i3 + 4;
        }
        int i7 = (i2 & (-8)) | 4;
        zzha zzhaVarC = zzha.c();
        int i8 = 0;
        while (true) {
            if (i3 >= i4) {
                break;
            }
            int iH2 = h(bArr, i3, zzdtVar);
            int i9 = zzdtVar.zza;
            i8 = i9;
            if (i9 == i7) {
                i3 = iH2;
                break;
            }
            int iG = g(i8, bArr, iH2, i4, zzhaVarC, zzdtVar);
            i8 = i9;
            i3 = iG;
        }
        if (i3 > i4 || i8 != i7) {
            throw zzfb.zzd();
        }
        zzhaVar.f(i2, zzhaVarC);
        return i3;
    }

    static int h(byte[] bArr, int i2, zzdt zzdtVar) {
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        if (b2 < 0) {
            return i(b2, bArr, i3, zzdtVar);
        }
        zzdtVar.zza = b2;
        return i3;
    }

    static int i(int i2, byte[] bArr, int i3, zzdt zzdtVar) {
        byte b2 = bArr[i3];
        int i4 = i3 + 1;
        int i5 = i2 & 127;
        if (b2 >= 0) {
            zzdtVar.zza = i5 | (b2 << 7);
            return i4;
        }
        int i6 = i5 | ((b2 & Byte.MAX_VALUE) << 7);
        int i7 = i3 + 2;
        byte b3 = bArr[i4];
        if (b3 >= 0) {
            zzdtVar.zza = i6 | (b3 << 14);
            return i7;
        }
        int i8 = i6 | ((b3 & Byte.MAX_VALUE) << 14);
        int i9 = i3 + 3;
        byte b4 = bArr[i7];
        if (b4 >= 0) {
            zzdtVar.zza = i8 | (b4 << Ascii.NAK);
            return i9;
        }
        int i10 = i8 | ((b4 & Byte.MAX_VALUE) << 21);
        int i11 = i3 + 4;
        byte b5 = bArr[i9];
        if (b5 >= 0) {
            zzdtVar.zza = i10 | (b5 << Ascii.FS);
            return i11;
        }
        int i12 = i10 | ((b5 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i13 = i11 + 1;
            if (bArr[i11] >= 0) {
                zzdtVar.zza = i12;
                return i13;
            }
            i11 = i13;
        }
    }

    static int j(int i2, byte[] bArr, int i3, int i4, zzez zzezVar, zzdt zzdtVar) {
        zzew zzewVar = (zzew) zzezVar;
        int iH = h(bArr, i3, zzdtVar);
        zzewVar.zze(zzdtVar.zza);
        while (iH < i4) {
            int iH2 = h(bArr, iH, zzdtVar);
            if (i2 != zzdtVar.zza) {
                break;
            }
            iH = h(bArr, iH2, zzdtVar);
            zzewVar.zze(zzdtVar.zza);
        }
        return iH;
    }

    static int k(byte[] bArr, int i2, zzdt zzdtVar) {
        long j2 = bArr[i2];
        int i3 = i2 + 1;
        if (j2 >= 0) {
            zzdtVar.zzb = j2;
            return i3;
        }
        int i4 = i2 + 2;
        byte b2 = bArr[i3];
        long j3 = (j2 & 127) | ((b2 & Byte.MAX_VALUE) << 7);
        int i5 = 7;
        while (b2 < 0) {
            int i6 = i4 + 1;
            i5 += 7;
            j3 |= (r10 & Byte.MAX_VALUE) << i5;
            b2 = bArr[i4];
            i4 = i6;
        }
        zzdtVar.zzb = j3;
        return i4;
    }

    static int l(Object obj, zzgi zzgiVar, byte[] bArr, int i2, int i3, int i4, zzdt zzdtVar) {
        int iA = ((zzga) zzgiVar).a(obj, bArr, i2, i3, i4, zzdtVar);
        zzdtVar.zzc = obj;
        return iA;
    }

    static int m(Object obj, zzgi zzgiVar, byte[] bArr, int i2, int i3, zzdt zzdtVar) {
        int i4 = i2 + 1;
        int i5 = bArr[i2];
        if (i5 < 0) {
            i4 = i(i5, bArr, i4, zzdtVar);
            i5 = zzdtVar.zza;
        }
        int i6 = i4;
        if (i5 < 0 || i5 > i3 - i6) {
            throw zzfb.zzf();
        }
        int i7 = i5 + i6;
        zzgiVar.zzg(obj, bArr, i6, i7, zzdtVar);
        zzdtVar.zzc = obj;
        return i7;
    }

    static long n(byte[] bArr, int i2) {
        return (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48) | ((bArr[i2 + 7] & 255) << 56);
    }
}
