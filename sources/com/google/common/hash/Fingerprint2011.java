package com.google.common.hash;

import com.google.common.base.Preconditions;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Fingerprint2011 extends AbstractNonStreamingHashFunction {
    private static final long K0 = -6505348102511208375L;
    private static final long K1 = -8261664234251669945L;
    private static final long K2 = -4288712594273399085L;
    private static final long K3 = -4132994306676758123L;

    /* renamed from: a, reason: collision with root package name */
    static final HashFunction f14611a = new Fingerprint2011();

    Fingerprint2011() {
    }

    static long a(byte[] bArr, int i2, int i3) {
        long jC = i3 <= 32 ? c(bArr, i2, i3, -1397348546323613475L) : i3 <= 64 ? hashLength33To64(bArr, i2, i3) : fullFingerprint(bArr, i2, i3);
        long jB = K0;
        long jB2 = i3 >= 8 ? LittleEndianByteArray.b(bArr, i2) : -6505348102511208375L;
        if (i3 >= 9) {
            jB = LittleEndianByteArray.b(bArr, (i2 + i3) - 8);
        }
        long jB3 = b(jC + jB, jB2);
        return (jB3 == 0 || jB3 == 1) ? jB3 - 2 : jB3;
    }

    static long b(long j2, long j3) {
        long j4 = (j3 ^ j2) * K3;
        long j5 = (j2 ^ (j4 ^ (j4 >>> 47))) * K3;
        return (j5 ^ (j5 >>> 47)) * K3;
    }

    static long c(byte[] bArr, int i2, int i3, long j2) {
        int i4 = i3 & (-8);
        int i5 = i3 & 7;
        long jC = j2 ^ (i3 * K3);
        for (int i6 = 0; i6 < i4; i6 += 8) {
            jC = (jC ^ (shiftMix(LittleEndianByteArray.b(bArr, i2 + i6) * K3) * K3)) * K3;
        }
        if (i5 != 0) {
            jC = (LittleEndianByteArray.c(bArr, i2 + i4, i5) ^ jC) * K3;
        }
        return shiftMix(shiftMix(jC) * K3);
    }

    private static long fullFingerprint(byte[] bArr, int i2, int i3) {
        long jB = LittleEndianByteArray.b(bArr, i2);
        int i4 = i2 + i3;
        long jB2 = LittleEndianByteArray.b(bArr, i4 - 16) ^ K1;
        long jB3 = LittleEndianByteArray.b(bArr, i4 - 56) ^ K0;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long j2 = i3;
        weakHashLength32WithSeeds(bArr, i4 - 64, j2, jB2, jArr);
        weakHashLength32WithSeeds(bArr, i4 - 32, j2 * K1, K0, jArr2);
        long jShiftMix = jB3 + (shiftMix(jArr[1]) * K1);
        long jRotateRight = Long.rotateRight(jShiftMix + jB, 39) * K1;
        long jRotateRight2 = Long.rotateRight(jB2, 33) * K1;
        int i5 = i2;
        int i6 = (i3 - 1) & (-64);
        while (true) {
            long jRotateRight3 = Long.rotateRight(jRotateRight + jRotateRight2 + jArr[0] + LittleEndianByteArray.b(bArr, i5 + 16), 37) * K1;
            long jRotateRight4 = Long.rotateRight(jRotateRight2 + jArr[1] + LittleEndianByteArray.b(bArr, i5 + 48), 42) * K1;
            long j3 = jRotateRight3 ^ jArr2[1];
            long j4 = jRotateRight4 ^ jArr[0];
            long jRotateRight5 = Long.rotateRight(jShiftMix ^ jArr2[0], 33);
            weakHashLength32WithSeeds(bArr, i5, jArr[1] * K1, j3 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bArr, i5 + 32, jArr2[1] + jRotateRight5, j4, jArr2);
            i5 += 64;
            i6 -= 64;
            if (i6 == 0) {
                return b(b(jArr[0], jArr2[0]) + (shiftMix(j4) * K1) + j3, b(jArr[1], jArr2[1]) + jRotateRight5);
            }
            jRotateRight = jRotateRight5;
            jShiftMix = j3;
            jRotateRight2 = j4;
        }
    }

    private static long hashLength33To64(byte[] bArr, int i2, int i3) {
        long jB = LittleEndianByteArray.b(bArr, i2 + 24);
        int i4 = i2 + i3;
        int i5 = i4 - 16;
        long jB2 = LittleEndianByteArray.b(bArr, i2) + ((i3 + LittleEndianByteArray.b(bArr, i5)) * K0);
        long jRotateRight = Long.rotateRight(jB2 + jB, 52);
        long jRotateRight2 = Long.rotateRight(jB2, 37);
        long jB3 = jB2 + LittleEndianByteArray.b(bArr, i2 + 8);
        long jRotateRight3 = jRotateRight2 + Long.rotateRight(jB3, 7);
        int i6 = i2 + 16;
        long jB4 = jB3 + LittleEndianByteArray.b(bArr, i6);
        long j2 = jB + jB4;
        long jRotateRight4 = jRotateRight + Long.rotateRight(jB4, 31) + jRotateRight3;
        long jB5 = LittleEndianByteArray.b(bArr, i6) + LittleEndianByteArray.b(bArr, i4 - 32);
        long jB6 = LittleEndianByteArray.b(bArr, i4 - 8);
        long jRotateRight5 = Long.rotateRight(jB5 + jB6, 52);
        long jRotateRight6 = Long.rotateRight(jB5, 37);
        long jB7 = jB5 + LittleEndianByteArray.b(bArr, i4 - 24);
        long jRotateRight7 = jRotateRight6 + Long.rotateRight(jB7, 7);
        long jB8 = jB7 + LittleEndianByteArray.b(bArr, i5);
        return shiftMix((shiftMix(((j2 + jRotateRight5 + Long.rotateRight(jB8, 31) + jRotateRight7) * K2) + ((jB6 + jB8 + jRotateRight4) * K0)) * K0) + jRotateRight4) * K2;
    }

    private static long shiftMix(long j2) {
        return j2 ^ (j2 >>> 47);
    }

    private static void weakHashLength32WithSeeds(byte[] bArr, int i2, long j2, long j3, long[] jArr) {
        long jB = LittleEndianByteArray.b(bArr, i2);
        long jB2 = LittleEndianByteArray.b(bArr, i2 + 8);
        long jB3 = LittleEndianByteArray.b(bArr, i2 + 16);
        long jB4 = LittleEndianByteArray.b(bArr, i2 + 24);
        long j4 = j2 + jB;
        long j5 = jB2 + j4 + jB3;
        long jRotateRight = Long.rotateRight(j3 + j4 + jB4, 51) + Long.rotateRight(j5, 23);
        jArr[0] = j5 + jB4;
        jArr[1] = jRotateRight + j4;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        return HashCode.fromLong(a(bArr, i2, i3));
    }

    public String toString() {
        return "Hashing.fingerprint2011()";
    }
}
