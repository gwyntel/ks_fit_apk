package com.google.android.gms.internal.auth;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzhk {
    static /* bridge */ /* synthetic */ void a(byte b2, byte b3, byte b4, byte b5, char[] cArr, int i2) throws zzfb {
        if (zze(b3) || (((b2 << Ascii.FS) + (b3 + 112)) >> 30) != 0 || zze(b4) || zze(b5)) {
            throw zzfb.zzb();
        }
        int i3 = ((b2 & 7) << 18) | ((b3 & 63) << 12) | ((b4 & 63) << 6) | (b5 & 63);
        cArr[i2] = (char) ((i3 >>> 10) + 55232);
        cArr[i2 + 1] = (char) ((i3 & 1023) + 56320);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0016 A[PHI: r2
      0x0016: PHI (r2v3 byte) = (r2v2 byte), (r2v9 byte) binds: [B:9:0x0011, B:11:0x0015] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* bridge */ /* synthetic */ void b(byte r2, byte r3, byte r4, char[] r5, int r6) throws com.google.android.gms.internal.auth.zzfb {
        /*
            boolean r0 = zze(r3)
            if (r0 != 0) goto L2c
            r0 = -96
            r1 = -32
            if (r2 != r1) goto Lf
            if (r3 < r0) goto L2c
            r2 = r1
        Lf:
            r1 = -19
            if (r2 != r1) goto L16
            if (r3 >= r0) goto L2c
            r2 = r1
        L16:
            boolean r0 = zze(r4)
            if (r0 != 0) goto L2c
            r2 = r2 & 15
            r3 = r3 & 63
            r4 = r4 & 63
            int r2 = r2 << 12
            int r3 = r3 << 6
            r2 = r2 | r3
            r2 = r2 | r4
            char r2 = (char) r2
            r5[r6] = r2
            return
        L2c:
            com.google.android.gms.internal.auth.zzfb r2 = com.google.android.gms.internal.auth.zzfb.zzb()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzhk.b(byte, byte, byte, char[], int):void");
    }

    static /* bridge */ /* synthetic */ void c(byte b2, byte b3, char[] cArr, int i2) throws zzfb {
        if (b2 < -62 || zze(b3)) {
            throw zzfb.zzb();
        }
        cArr[i2] = (char) (((b2 & Ascii.US) << 6) | (b3 & 63));
    }

    static /* bridge */ /* synthetic */ boolean d(byte b2) {
        return b2 >= 0;
    }

    private static boolean zze(byte b2) {
        return b2 > -65;
    }
}
