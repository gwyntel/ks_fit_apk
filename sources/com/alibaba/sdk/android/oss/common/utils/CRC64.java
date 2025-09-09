package com.alibaba.sdk.android.oss.common.utils;

import java.lang.reflect.Array;
import java.util.zip.Checksum;

/* loaded from: classes2.dex */
public class CRC64 implements Checksum {
    private static final int GF2_DIM = 64;
    private static final long POLY = -3932672073523589310L;
    private static final long[][] table = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 8, 256);
    private long value = 0;

    static {
        for (int i2 = 0; i2 < 256; i2++) {
            long j2 = i2;
            for (int i3 = 0; i3 < 8; i3++) {
                j2 = (j2 & 1) == 1 ? (j2 >>> 1) ^ POLY : j2 >>> 1;
            }
            table[0][i2] = j2;
        }
        for (int i4 = 0; i4 < 256; i4++) {
            long j3 = table[0][i4];
            for (int i5 = 1; i5 < 8; i5++) {
                long[][] jArr = table;
                j3 = (j3 >>> 8) ^ jArr[0][(int) (255 & j3)];
                jArr[i5][i4] = j3;
            }
        }
    }

    public static long combine(long j2, long j3, long j4) {
        if (j4 == 0) {
            return j2;
        }
        long[] jArr = new long[64];
        long[] jArr2 = new long[64];
        jArr2[0] = -3932672073523589310L;
        long j5 = 1;
        for (int i2 = 1; i2 < 64; i2++) {
            jArr2[i2] = j5;
            j5 <<= 1;
        }
        gf2MatrixSquare(jArr, jArr2);
        gf2MatrixSquare(jArr2, jArr);
        long jGf2MatrixTimes = j2;
        long j6 = j4;
        do {
            gf2MatrixSquare(jArr, jArr2);
            if ((j6 & 1) == 1) {
                jGf2MatrixTimes = gf2MatrixTimes(jArr, jGf2MatrixTimes);
            }
            long j7 = j6 >>> 1;
            if (j7 == 0) {
                break;
            }
            gf2MatrixSquare(jArr2, jArr);
            if ((j7 & 1) == 1) {
                jGf2MatrixTimes = gf2MatrixTimes(jArr2, jGf2MatrixTimes);
            }
            j6 >>>= 2;
        } while (j6 != 0);
        return jGf2MatrixTimes ^ j3;
    }

    private static void gf2MatrixSquare(long[] jArr, long[] jArr2) {
        for (int i2 = 0; i2 < 64; i2++) {
            jArr[i2] = gf2MatrixTimes(jArr2, jArr2[i2]);
        }
    }

    private static long gf2MatrixTimes(long[] jArr, long j2) {
        int i2 = 0;
        long j3 = 0;
        while (j2 != 0) {
            if ((j2 & 1) == 1) {
                j3 ^= jArr[i2];
            }
            j2 >>>= 1;
            i2++;
        }
        return j3;
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.value;
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.value = 0L;
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        update(new byte[]{(byte) (i2 & 255)}, 1);
    }

    public void update(byte[] bArr, int i2) {
        update(bArr, 0, i2);
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        this.value = ~this.value;
        int i4 = i2;
        int i5 = i3;
        while (i5 >= 8) {
            long[][] jArr = table;
            long[] jArr2 = jArr[7];
            long j2 = this.value;
            this.value = ((((((jArr[6][(int) ((bArr[i4 + 1] & 255) ^ ((j2 >>> 8) & 255))] ^ jArr2[(int) ((j2 & 255) ^ (bArr[i4] & 255))]) ^ jArr[5][(int) (((j2 >>> 16) & 255) ^ (bArr[i4 + 2] & 255))]) ^ jArr[4][(int) (((j2 >>> 24) & 255) ^ (bArr[i4 + 3] & 255))]) ^ jArr[3][(int) (((j2 >>> 32) & 255) ^ (bArr[i4 + 4] & 255))]) ^ jArr[2][(int) (((j2 >>> 40) & 255) ^ (bArr[i4 + 5] & 255))]) ^ jArr[1][(int) ((255 & (j2 >>> 48)) ^ (bArr[i4 + 6] & 255))]) ^ jArr[0][(int) ((j2 >>> 56) ^ (bArr[i4 + 7] & 255))];
            i4 += 8;
            i5 -= 8;
        }
        while (i5 > 0) {
            long[] jArr3 = table[0];
            long j3 = this.value;
            this.value = (j3 >>> 8) ^ jArr3[(int) ((bArr[i4] ^ j3) & 255)];
            i4++;
            i5--;
        }
        this.value = ~this.value;
    }
}
