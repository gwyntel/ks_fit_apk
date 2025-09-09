package com.google.android.gms.internal.measurement;

import kotlin.UShort;

/* loaded from: classes3.dex */
final class zzil {
    static int a(int i2) {
        return (i2 < 32 ? 4 : 2) * (i2 + 1);
    }

    static int b(int i2, int i3, int i4) {
        return (i2 & (~i4)) | (i3 & i4);
    }

    static int c(Object obj, int i2) {
        return obj instanceof byte[] ? ((byte[]) obj)[i2] & 255 : obj instanceof short[] ? ((short[]) obj)[i2] & UShort.MAX_VALUE : ((int[]) obj)[i2];
    }

    static int d(Object obj, Object obj2, int i2, Object obj3, int[] iArr, Object[] objArr, Object[] objArr2) {
        int i3;
        int i4;
        int iB = zzin.b(obj);
        int i5 = iB & i2;
        int iC = c(obj3, i5);
        if (iC == 0) {
            return -1;
        }
        int i6 = ~i2;
        int i7 = iB & i6;
        int i8 = -1;
        while (true) {
            i3 = iC - 1;
            i4 = iArr[i3];
            if ((i4 & i6) == i7 && zzhl.zza(obj, objArr[i3]) && (objArr2 == null || zzhl.zza(obj2, objArr2[i3]))) {
                break;
            }
            int i9 = i4 & i2;
            if (i9 == 0) {
                return -1;
            }
            i8 = i3;
            iC = i9;
        }
        int i10 = i4 & i2;
        if (i8 == -1) {
            e(obj3, i5, i10);
        } else {
            iArr[i8] = b(iArr[i8], i10, i2);
        }
        return i3;
    }

    static void e(Object obj, int i2, int i3) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i2] = (byte) i3;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i2] = (short) i3;
        } else {
            ((int[]) obj)[i2] = i3;
        }
    }

    static Object f(int i2) {
        if (i2 >= 2 && i2 <= 1073741824 && Integer.highestOneBit(i2) == i2) {
            return i2 <= 256 ? new byte[i2] : i2 <= 65536 ? new short[i2] : new int[i2];
        }
        throw new IllegalArgumentException("must be power of 2 between 2^1 and 2^30: " + i2);
    }
}
