package com.google.common.hash;

import com.google.common.base.Preconditions;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    /* renamed from: a, reason: collision with root package name */
    static final HashFunction f14610a = new FarmHashFingerprint64();

    FarmHashFingerprint64() {
    }

    static long a(byte[] bArr, int i2, int i3) {
        return i3 <= 32 ? i3 <= 16 ? hashLength0to16(bArr, i2, i3) : hashLength17to32(bArr, i2, i3) : i3 <= 64 ? hashLength33To64(bArr, i2, i3) : hashLength65Plus(bArr, i2, i3);
    }

    private static long hashLength0to16(byte[] bArr, int i2, int i3) {
        if (i3 >= 8) {
            long j2 = (i3 * 2) + K2;
            long jB = LittleEndianByteArray.b(bArr, i2) + K2;
            long jB2 = LittleEndianByteArray.b(bArr, (i2 + i3) - 8);
            return hashLength16((Long.rotateRight(jB2, 37) * j2) + jB, (Long.rotateRight(jB, 25) + jB2) * j2, j2);
        }
        if (i3 >= 4) {
            return hashLength16(i3 + ((LittleEndianByteArray.a(bArr, i2) & 4294967295L) << 3), LittleEndianByteArray.a(bArr, (i2 + i3) - 4) & 4294967295L, (i3 * 2) + K2);
        }
        if (i3 <= 0) {
            return K2;
        }
        return shiftMix((((bArr[i2] & 255) + ((bArr[(i3 >> 1) + i2] & 255) << 8)) * K2) ^ ((i3 + ((bArr[i2 + (i3 - 1)] & 255) << 2)) * K0)) * K2;
    }

    private static long hashLength16(long j2, long j3, long j4) {
        long j5 = (j2 ^ j3) * j4;
        long j6 = ((j5 ^ (j5 >>> 47)) ^ j3) * j4;
        return (j6 ^ (j6 >>> 47)) * j4;
    }

    private static long hashLength17to32(byte[] bArr, int i2, int i3) {
        long j2 = (i3 * 2) + K2;
        long jB = LittleEndianByteArray.b(bArr, i2) * K1;
        long jB2 = LittleEndianByteArray.b(bArr, i2 + 8);
        int i4 = i2 + i3;
        long jB3 = LittleEndianByteArray.b(bArr, i4 - 8) * j2;
        return hashLength16((LittleEndianByteArray.b(bArr, i4 - 16) * K2) + Long.rotateRight(jB + jB2, 43) + Long.rotateRight(jB3, 30), jB3 + jB + Long.rotateRight(jB2 + K2, 18), j2);
    }

    private static long hashLength33To64(byte[] bArr, int i2, int i3) {
        long j2 = (i3 * 2) + K2;
        long jB = LittleEndianByteArray.b(bArr, i2) * K2;
        long jB2 = LittleEndianByteArray.b(bArr, i2 + 8);
        int i4 = i2 + i3;
        long jB3 = LittleEndianByteArray.b(bArr, i4 - 8) * j2;
        long jRotateRight = Long.rotateRight(jB + jB2, 43) + Long.rotateRight(jB3, 30) + (LittleEndianByteArray.b(bArr, i4 - 16) * K2);
        long jHashLength16 = hashLength16(jRotateRight, jB3 + Long.rotateRight(jB2 + K2, 18) + jB, j2);
        long jB4 = LittleEndianByteArray.b(bArr, i2 + 16) * j2;
        long jB5 = LittleEndianByteArray.b(bArr, i2 + 24);
        long jB6 = (jRotateRight + LittleEndianByteArray.b(bArr, i4 - 32)) * j2;
        return hashLength16(((jHashLength16 + LittleEndianByteArray.b(bArr, i4 - 24)) * j2) + Long.rotateRight(jB4 + jB5, 43) + Long.rotateRight(jB6, 30), jB4 + Long.rotateRight(jB5 + jB, 18) + jB6, j2);
    }

    private static long hashLength65Plus(byte[] bArr, int i2, int i3) {
        long j2 = 81;
        long j3 = (j2 * K1) + 113;
        long jShiftMix = shiftMix((j3 * K2) + 113) * K2;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long jB = (j2 * K2) + LittleEndianByteArray.b(bArr, i2);
        int i4 = i3 - 1;
        int i5 = i2 + ((i4 / 64) * 64);
        int i6 = i4 & 63;
        int i7 = i5 + i6;
        int i8 = i7 - 63;
        int i9 = i2;
        while (true) {
            long jRotateRight = Long.rotateRight(jB + j3 + jArr[0] + LittleEndianByteArray.b(bArr, i9 + 8), 37) * K1;
            long jRotateRight2 = Long.rotateRight(j3 + jArr[1] + LittleEndianByteArray.b(bArr, i9 + 48), 42) * K1;
            long j4 = jRotateRight ^ jArr2[1];
            long jB2 = jRotateRight2 + jArr[0] + LittleEndianByteArray.b(bArr, i9 + 40);
            long jRotateRight3 = Long.rotateRight(jShiftMix + jArr2[0], 33) * K1;
            weakHashLength32WithSeeds(bArr, i9, jArr[1] * K1, j4 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bArr, i9 + 32, jRotateRight3 + jArr2[1], jB2 + LittleEndianByteArray.b(bArr, i9 + 16), jArr2);
            int i10 = i9 + 64;
            if (i10 == i5) {
                long j5 = K1 + ((j4 & 255) << 1);
                long j6 = jArr2[0] + i6;
                jArr2[0] = j6;
                long j7 = jArr[0] + j6;
                jArr[0] = j7;
                jArr2[0] = jArr2[0] + j7;
                long jRotateRight4 = Long.rotateRight(jRotateRight3 + jB2 + jArr[0] + LittleEndianByteArray.b(bArr, i7 - 55), 37) * j5;
                long jRotateRight5 = Long.rotateRight(jB2 + jArr[1] + LittleEndianByteArray.b(bArr, i7 - 15), 42) * j5;
                long j8 = jRotateRight4 ^ (jArr2[1] * 9);
                long jB3 = jRotateRight5 + (jArr[0] * 9) + LittleEndianByteArray.b(bArr, i7 - 23);
                long jRotateRight6 = Long.rotateRight(j4 + jArr2[0], 33) * j5;
                weakHashLength32WithSeeds(bArr, i8, jArr[1] * j5, j8 + jArr2[0], jArr);
                weakHashLength32WithSeeds(bArr, i7 - 31, jRotateRight6 + jArr2[1], jB3 + LittleEndianByteArray.b(bArr, i7 - 47), jArr2);
                return hashLength16(hashLength16(jArr[0], jArr2[0], j5) + (shiftMix(jB3) * K0) + j8, hashLength16(jArr[1], jArr2[1], j5) + jRotateRight6, j5);
            }
            i9 = i10;
            jShiftMix = j4;
            j3 = jB2;
            jB = jRotateRight3;
        }
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
        long jRotateRight = Long.rotateRight(j3 + j4 + jB4, 21) + Long.rotateRight(j5, 44);
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
        return "Hashing.farmHashFingerprint64()";
    }
}
