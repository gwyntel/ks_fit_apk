package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzhn {
    public static final /* synthetic */ int zza = 0;
    private static final zzhl zzb;

    static {
        if (zzhj.u() && zzhj.v()) {
            int i2 = zzds.zza;
        }
        zzb = new zzhm();
    }

    static /* bridge */ /* synthetic */ int a(byte[] bArr, int i2, int i3) {
        int i4 = i3 - i2;
        byte b2 = bArr[i2 - 1];
        if (i4 != 0) {
            if (i4 == 1) {
                byte b3 = bArr[i2];
                if (b2 <= -12 && b3 <= -65) {
                    return b2 ^ (b3 << 8);
                }
            } else {
                if (i4 != 2) {
                    throw new AssertionError();
                }
                byte b4 = bArr[i2];
                byte b5 = bArr[i2 + 1];
                if (b2 <= -12 && b4 <= -65 && b5 <= -65) {
                    return ((b4 << 8) ^ b2) ^ (b5 << 16);
                }
            }
        } else if (b2 <= -12) {
            return b2;
        }
        return -1;
    }

    static boolean b(byte[] bArr) {
        return zzb.b(bArr, 0, bArr.length);
    }

    static boolean c(byte[] bArr, int i2, int i3) {
        return zzb.b(bArr, i2, i3);
    }
}
