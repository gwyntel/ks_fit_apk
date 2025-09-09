package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzpf {
    static /* synthetic */ void a(byte b2, byte b3, byte b4, byte b5, char[] cArr, int i2) throws zzme {
        if (zza(b3) || (((b2 << Ascii.FS) + (b3 + 112)) >> 30) != 0 || zza(b4) || zza(b5)) {
            throw zzme.zzd();
        }
        int i3 = ((b2 & 7) << 18) | ((b3 & 63) << 12) | ((b4 & 63) << 6) | (b5 & 63);
        cArr[i2] = (char) ((i3 >>> 10) + 55232);
        cArr[i2 + 1] = (char) ((i3 & 1023) + 56320);
    }

    static /* synthetic */ void b(byte b2, byte b3, byte b4, char[] cArr, int i2) throws zzme {
        if (zza(b3) || ((b2 == -32 && b3 < -96) || ((b2 == -19 && b3 >= -96) || zza(b4)))) {
            throw zzme.zzd();
        }
        cArr[i2] = (char) (((b2 & 15) << 12) | ((b3 & 63) << 6) | (b4 & 63));
    }

    static /* synthetic */ void c(byte b2, byte b3, char[] cArr, int i2) throws zzme {
        if (b2 < -62 || zza(b3)) {
            throw zzme.zzd();
        }
        cArr[i2] = (char) (((b2 & Ascii.US) << 6) | (b3 & 63));
    }

    static /* synthetic */ void d(byte b2, char[] cArr, int i2) {
        cArr[i2] = (char) b2;
    }

    private static boolean zza(byte b2) {
        return b2 > -65;
    }
}
