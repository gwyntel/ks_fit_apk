package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzki {
    static double a(byte[] bArr, int i2) {
        return Double.longBitsToDouble(r(bArr, i2));
    }

    static int b(int i2, byte[] bArr, int i3, int i4, zzkh zzkhVar) {
        if ((i2 >>> 3) == 0) {
            throw zzme.zzc();
        }
        int i5 = i2 & 7;
        if (i5 == 0) {
            return q(bArr, i3, zzkhVar);
        }
        if (i5 == 1) {
            return i3 + 8;
        }
        if (i5 == 2) {
            return p(bArr, i3, zzkhVar) + zzkhVar.zza;
        }
        if (i5 != 3) {
            if (i5 == 5) {
                return i3 + 4;
            }
            throw zzme.zzc();
        }
        int i6 = (i2 & (-8)) | 4;
        int i7 = 0;
        while (i3 < i4) {
            i3 = p(bArr, i3, zzkhVar);
            i7 = zzkhVar.zza;
            if (i7 == i6) {
                break;
            }
            i3 = b(i7, bArr, i3, i4, zzkhVar);
        }
        if (i3 > i4 || i7 != i6) {
            throw zzme.zzg();
        }
        return i3;
    }

    static int c(int i2, byte[] bArr, int i3, int i4, zzmf zzmfVar, zzkh zzkhVar) {
        zzlx zzlxVar = (zzlx) zzmfVar;
        int iP = p(bArr, i3, zzkhVar);
        zzlxVar.zzd(zzkhVar.zza);
        while (iP < i4) {
            int iP2 = p(bArr, iP, zzkhVar);
            if (i2 != zzkhVar.zza) {
                break;
            }
            iP = p(bArr, iP2, zzkhVar);
            zzlxVar.zzd(zzkhVar.zza);
        }
        return iP;
    }

    static int d(int i2, byte[] bArr, int i3, int i4, zzoz zzozVar, zzkh zzkhVar) {
        if ((i2 >>> 3) == 0) {
            throw zzme.zzc();
        }
        int i5 = i2 & 7;
        if (i5 == 0) {
            int iQ = q(bArr, i3, zzkhVar);
            zzozVar.c(i2, Long.valueOf(zzkhVar.zzb));
            return iQ;
        }
        if (i5 == 1) {
            zzozVar.c(i2, Long.valueOf(r(bArr, i3)));
            return i3 + 8;
        }
        if (i5 == 2) {
            int iP = p(bArr, i3, zzkhVar);
            int i6 = zzkhVar.zza;
            if (i6 < 0) {
                throw zzme.zzf();
            }
            if (i6 > bArr.length - iP) {
                throw zzme.zzh();
            }
            if (i6 == 0) {
                zzozVar.c(i2, zzkm.zza);
            } else {
                zzozVar.c(i2, zzkm.zza(bArr, iP, i6));
            }
            return iP + i6;
        }
        if (i5 != 3) {
            if (i5 != 5) {
                throw zzme.zzc();
            }
            zzozVar.c(i2, Integer.valueOf(o(bArr, i3)));
            return i3 + 4;
        }
        zzoz zzozVarF = zzoz.f();
        int i7 = (i2 & (-8)) | 4;
        int i8 = 0;
        while (true) {
            if (i3 >= i4) {
                break;
            }
            int iP2 = p(bArr, i3, zzkhVar);
            int i9 = zzkhVar.zza;
            i8 = i9;
            if (i9 == i7) {
                i3 = iP2;
                break;
            }
            int iD = d(i8, bArr, iP2, i4, zzozVarF, zzkhVar);
            i8 = i9;
            i3 = iD;
        }
        if (i3 > i4 || i8 != i7) {
            throw zzme.zzg();
        }
        zzozVar.c(i2, zzozVarF);
        return i3;
    }

    static int e(int i2, byte[] bArr, int i3, zzkh zzkhVar) {
        int i4 = i2 & 127;
        int i5 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzkhVar.zza = i4 | (b2 << 7);
            return i5;
        }
        int i6 = i4 | ((b2 & Byte.MAX_VALUE) << 7);
        int i7 = i3 + 2;
        byte b3 = bArr[i5];
        if (b3 >= 0) {
            zzkhVar.zza = i6 | (b3 << 14);
            return i7;
        }
        int i8 = i6 | ((b3 & Byte.MAX_VALUE) << 14);
        int i9 = i3 + 3;
        byte b4 = bArr[i7];
        if (b4 >= 0) {
            zzkhVar.zza = i8 | (b4 << Ascii.NAK);
            return i9;
        }
        int i10 = i8 | ((b4 & Byte.MAX_VALUE) << 21);
        int i11 = i3 + 4;
        byte b5 = bArr[i9];
        if (b5 >= 0) {
            zzkhVar.zza = i10 | (b5 << Ascii.FS);
            return i11;
        }
        int i12 = i10 | ((b5 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i13 = i11 + 1;
            if (bArr[i11] >= 0) {
                zzkhVar.zza = i12;
                return i13;
            }
            i11 = i13;
        }
    }

    static int f(zzob zzobVar, int i2, byte[] bArr, int i3, int i4, zzmf zzmfVar, zzkh zzkhVar) {
        int iH = h(zzobVar, bArr, i3, i4, zzkhVar);
        zzmfVar.add(zzkhVar.zzc);
        while (iH < i4) {
            int iP = p(bArr, iH, zzkhVar);
            if (i2 != zzkhVar.zza) {
                break;
            }
            iH = h(zzobVar, bArr, iP, i4, zzkhVar);
            zzmfVar.add(zzkhVar.zzc);
        }
        return iH;
    }

    static int g(zzob zzobVar, byte[] bArr, int i2, int i3, int i4, zzkh zzkhVar) {
        Object objZza = zzobVar.zza();
        int i5 = i(objZza, zzobVar, bArr, i2, i3, i4, zzkhVar);
        zzobVar.zzc(objZza);
        zzkhVar.zzc = objZza;
        return i5;
    }

    static int h(zzob zzobVar, byte[] bArr, int i2, int i3, zzkh zzkhVar) {
        Object objZza = zzobVar.zza();
        int iJ = j(objZza, zzobVar, bArr, i2, i3, zzkhVar);
        zzobVar.zzc(objZza);
        zzkhVar.zzc = objZza;
        return iJ;
    }

    static int i(Object obj, zzob zzobVar, byte[] bArr, int i2, int i3, int i4, zzkh zzkhVar) {
        int iA = ((zznn) zzobVar).a(obj, bArr, i2, i3, i4, zzkhVar);
        zzkhVar.zzc = obj;
        return iA;
    }

    static int j(Object obj, zzob zzobVar, byte[] bArr, int i2, int i3, zzkh zzkhVar) {
        int iE = i2 + 1;
        int i4 = bArr[i2];
        if (i4 < 0) {
            iE = e(i4, bArr, iE, zzkhVar);
            i4 = zzkhVar.zza;
        }
        int i5 = iE;
        if (i4 < 0 || i4 > i3 - i5) {
            throw zzme.zzh();
        }
        int i6 = i4 + i5;
        zzobVar.zza(obj, bArr, i5, i6, zzkhVar);
        zzkhVar.zzc = obj;
        return i6;
    }

    static int k(byte[] bArr, int i2, zzkh zzkhVar) {
        int iP = p(bArr, i2, zzkhVar);
        int i3 = zzkhVar.zza;
        if (i3 < 0) {
            throw zzme.zzf();
        }
        if (i3 > bArr.length - iP) {
            throw zzme.zzh();
        }
        if (i3 == 0) {
            zzkhVar.zzc = zzkm.zza;
            return iP;
        }
        zzkhVar.zzc = zzkm.zza(bArr, iP, i3);
        return iP + i3;
    }

    static int l(byte[] bArr, int i2, zzmf zzmfVar, zzkh zzkhVar) {
        zzlx zzlxVar = (zzlx) zzmfVar;
        int iP = p(bArr, i2, zzkhVar);
        int i3 = zzkhVar.zza + iP;
        while (iP < i3) {
            iP = p(bArr, iP, zzkhVar);
            zzlxVar.zzd(zzkhVar.zza);
        }
        if (iP == i3) {
            return iP;
        }
        throw zzme.zzh();
    }

    static float m(byte[] bArr, int i2) {
        return Float.intBitsToFloat(o(bArr, i2));
    }

    static int n(byte[] bArr, int i2, zzkh zzkhVar) {
        int iP = p(bArr, i2, zzkhVar);
        int i3 = zzkhVar.zza;
        if (i3 < 0) {
            throw zzme.zzf();
        }
        if (i3 == 0) {
            zzkhVar.zzc = "";
            return iP;
        }
        zzkhVar.zzc = zzpg.e(bArr, iP, i3);
        return iP + i3;
    }

    static int o(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    static int p(byte[] bArr, int i2, zzkh zzkhVar) {
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        if (b2 < 0) {
            return e(b2, bArr, i3, zzkhVar);
        }
        zzkhVar.zza = b2;
        return i3;
    }

    static int q(byte[] bArr, int i2, zzkh zzkhVar) {
        int i3 = i2 + 1;
        long j2 = bArr[i2];
        if (j2 >= 0) {
            zzkhVar.zzb = j2;
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
        zzkhVar.zzb = j3;
        return i4;
    }

    static long r(byte[] bArr, int i2) {
        return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
    }
}
