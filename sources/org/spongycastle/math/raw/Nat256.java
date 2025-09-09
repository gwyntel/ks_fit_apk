package org.spongycastle.math.raw;

import c.a.d.h;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public abstract class Nat256 {
    public static final long M = 4294967295L;

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L);
        iArr3[0] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L);
        iArr3[1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L);
        iArr3[2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L);
        iArr3[3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L);
        iArr3[4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L);
        iArr3[5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[6] & 4294967295L) + (iArr2[6] & 4294967295L);
        iArr3[6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[7] & 4294967295L) + (iArr2[7] & 4294967295L);
        iArr3[7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L) + (iArr3[0] & 4294967295L);
        iArr3[0] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L) + (iArr3[1] & 4294967295L);
        iArr3[1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L) + (iArr3[2] & 4294967295L);
        iArr3[2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L) + (iArr3[3] & 4294967295L);
        iArr3[3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L) + (iArr3[4] & 4294967295L);
        iArr3[4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L) + (iArr3[5] & 4294967295L);
        iArr3[5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[6] & 4294967295L) + (iArr2[6] & 4294967295L) + (iArr3[6] & 4294967295L);
        iArr3[6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[7] & 4294967295L) + (iArr2[7] & 4294967295L) + (iArr3[7] & 4294967295L);
        iArr3[7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L);
        iArr2[0] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L);
        iArr2[1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L);
        iArr2[2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L);
        iArr2[3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L);
        iArr2[4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L);
        iArr2[5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[6] & 4294967295L) + (iArr2[6] & 4294967295L);
        iArr2[6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[7] & 4294967295L) + (4294967295L & iArr2[7]);
        iArr2[7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = (iArr[i2] & 4294967295L) + (iArr2[i3] & 4294967295L);
        int i4 = (int) j2;
        iArr[i2] = i4;
        iArr2[i3] = i4;
        long j3 = (j2 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i5 = (int) j3;
        iArr[i2 + 1] = i5;
        iArr2[i3 + 1] = i5;
        long j4 = (j3 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i6 = (int) j4;
        iArr[i2 + 2] = i6;
        iArr2[i3 + 2] = i6;
        long j5 = (j4 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i7 = (int) j5;
        iArr[i2 + 3] = i7;
        iArr2[i3 + 3] = i7;
        long j6 = (j5 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i8 = (int) j6;
        iArr[i2 + 4] = i8;
        iArr2[i3 + 4] = i8;
        long j7 = (j6 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i9 = (int) j7;
        iArr[i2 + 5] = i9;
        iArr2[i3 + 5] = i9;
        long j8 = (j7 >>> 32) + (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L);
        int i10 = (int) j8;
        iArr[i2 + 6] = i10;
        iArr2[i3 + 6] = i10;
        long j9 = (j8 >>> 32) + (iArr[r12] & 4294967295L) + (4294967295L & iArr2[r14]);
        int i11 = (int) j9;
        iArr[i2 + 7] = i11;
        iArr2[i3 + 7] = i11;
        return (int) (j9 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
        iArr2[5] = iArr[5];
        iArr2[6] = iArr[6];
        iArr2[7] = iArr[7];
    }

    public static void copy64(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
    }

    public static int[] create() {
        return new int[8];
    }

    public static long[] create64() {
        return new long[4];
    }

    public static int[] createExt() {
        return new int[16];
    }

    public static long[] createExt64() {
        return new long[8];
    }

    public static boolean diff(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        boolean zGte = gte(iArr, i2, iArr2, i3);
        if (zGte) {
            sub(iArr, i2, iArr2, i3, iArr3, i4);
        } else {
            sub(iArr2, i3, iArr, i2, iArr3, i4);
        }
        return zGte;
    }

    public static boolean eq(int[] iArr, int[] iArr2) {
        for (int i2 = 7; i2 >= 0; i2--) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static boolean eq64(long[] jArr, long[] jArr2) {
        for (int i2 = 3; i2 >= 0; i2--) {
            if (jArr[i2] != jArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 256) {
            throw new IllegalArgumentException();
        }
        int[] iArrCreate = create();
        int i2 = 0;
        while (bigInteger.signum() != 0) {
            iArrCreate[i2] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
            i2++;
        }
        return iArrCreate;
    }

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 256) {
            throw new IllegalArgumentException();
        }
        long[] jArrCreate64 = create64();
        int i2 = 0;
        while (bigInteger.signum() != 0) {
            jArrCreate64[i2] = bigInteger.longValue();
            bigInteger = bigInteger.shiftRight(64);
            i2++;
        }
        return jArrCreate64;
    }

    public static int getBit(int[] iArr, int i2) {
        int i3;
        if (i2 == 0) {
            i3 = iArr[0];
        } else {
            if ((i2 & 255) != i2) {
                return 0;
            }
            i3 = iArr[i2 >>> 5] >>> (i2 & 31);
        }
        return i3 & 1;
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i2 = 7; i2 >= 0; i2--) {
            int i3 = iArr[i2] ^ Integer.MIN_VALUE;
            int i4 = Integer.MIN_VALUE ^ iArr2[i2];
            if (i3 < i4) {
                return false;
            }
            if (i3 > i4) {
                return true;
            }
        }
        return true;
    }

    public static boolean isOne(int[] iArr) {
        if (iArr[0] != 1) {
            return false;
        }
        for (int i2 = 1; i2 < 8; i2++) {
            if (iArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOne64(long[] jArr) {
        if (jArr[0] != 1) {
            return false;
        }
        for (int i2 = 1; i2 < 4; i2++) {
            if (jArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            if (iArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero64(long[] jArr) {
        for (int i2 = 0; i2 < 4; i2++) {
            if (jArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = iArr2[0] & 4294967295L;
        long j3 = iArr2[1] & 4294967295L;
        long j4 = iArr2[2] & 4294967295L;
        long j5 = iArr2[3] & 4294967295L;
        long j6 = iArr2[4] & 4294967295L;
        long j7 = iArr2[5] & 4294967295L;
        long j8 = iArr2[6] & 4294967295L;
        long j9 = iArr2[7] & 4294967295L;
        long j10 = iArr[0] & 4294967295L;
        long j11 = j10 * j2;
        iArr3[0] = (int) j11;
        long j12 = (j11 >>> 32) + (j10 * j3);
        iArr3[1] = (int) j12;
        long j13 = (j12 >>> 32) + (j10 * j4);
        iArr3[2] = (int) j13;
        long j14 = (j13 >>> 32) + (j10 * j5);
        iArr3[3] = (int) j14;
        long j15 = (j14 >>> 32) + (j10 * j6);
        iArr3[4] = (int) j15;
        long j16 = (j15 >>> 32) + (j10 * j7);
        iArr3[5] = (int) j16;
        long j17 = (j16 >>> 32) + (j10 * j8);
        iArr3[6] = (int) j17;
        long j18 = (j17 >>> 32) + (j10 * j9);
        iArr3[7] = (int) j18;
        iArr3[8] = (int) (j18 >>> 32);
        int i2 = 1;
        for (int i3 = 8; i2 < i3; i3 = 8) {
            long j19 = iArr[i2] & 4294967295L;
            long j20 = (j19 * j2) + (iArr3[i2] & 4294967295L);
            iArr3[i2] = (int) j20;
            int i4 = i2 + 1;
            long j21 = (j20 >>> 32) + (j19 * j3) + (iArr3[i4] & 4294967295L);
            iArr3[i4] = (int) j21;
            long j22 = j3;
            long j23 = (j21 >>> 32) + (j19 * j4) + (iArr3[r18] & 4294967295L);
            iArr3[i2 + 2] = (int) j23;
            long j24 = (j23 >>> 32) + (j19 * j5) + (iArr3[r6] & 4294967295L);
            iArr3[i2 + 3] = (int) j24;
            long j25 = (j24 >>> 32) + (j19 * j6) + (iArr3[r6] & 4294967295L);
            iArr3[i2 + 4] = (int) j25;
            long j26 = (j25 >>> 32) + (j19 * j7) + (iArr3[r6] & 4294967295L);
            iArr3[i2 + 5] = (int) j26;
            long j27 = (j26 >>> 32) + (j19 * j8) + (iArr3[r6] & 4294967295L);
            iArr3[i2 + 6] = (int) j27;
            long j28 = (j27 >>> 32) + (j19 * j9) + (iArr3[r3] & 4294967295L);
            iArr3[i2 + 7] = (int) j28;
            iArr3[i2 + 8] = (int) (j28 >>> 32);
            j5 = j5;
            j2 = j2;
            i2 = i4;
            j3 = j22;
        }
    }

    public static long mul33Add(int i2, int[] iArr, int i3, int[] iArr2, int i4, int[] iArr3, int i5) {
        long j2 = i2 & 4294967295L;
        long j3 = iArr[i3] & 4294967295L;
        long j4 = (j2 * j3) + (iArr2[i4] & 4294967295L);
        iArr3[i5] = (int) j4;
        long j5 = iArr[i3 + 1] & 4294967295L;
        long j6 = (j4 >>> 32) + (j2 * j5) + j3 + (iArr2[i4 + 1] & 4294967295L);
        iArr3[i5 + 1] = (int) j6;
        long j7 = j6 >>> 32;
        long j8 = iArr[i3 + 2] & 4294967295L;
        long j9 = j7 + (j2 * j8) + j5 + (iArr2[i4 + 2] & 4294967295L);
        iArr3[i5 + 2] = (int) j9;
        long j10 = iArr[i3 + 3] & 4294967295L;
        long j11 = (j9 >>> 32) + (j2 * j10) + j8 + (iArr2[i4 + 3] & 4294967295L);
        iArr3[i5 + 3] = (int) j11;
        long j12 = iArr[i3 + 4] & 4294967295L;
        long j13 = (j11 >>> 32) + (j2 * j12) + j10 + (iArr2[i4 + 4] & 4294967295L);
        iArr3[i5 + 4] = (int) j13;
        long j14 = iArr[i3 + 5] & 4294967295L;
        long j15 = (j13 >>> 32) + (j2 * j14) + j12 + (iArr2[i4 + 5] & 4294967295L);
        iArr3[i5 + 5] = (int) j15;
        long j16 = iArr[i3 + 6] & 4294967295L;
        long j17 = (j15 >>> 32) + (j2 * j16) + j14 + (iArr2[i4 + 6] & 4294967295L);
        iArr3[i5 + 6] = (int) j17;
        long j18 = iArr[i3 + 7] & 4294967295L;
        long j19 = (j17 >>> 32) + (j2 * j18) + j16 + (4294967295L & iArr2[i4 + 7]);
        iArr3[i5 + 7] = (int) j19;
        return (j19 >>> 32) + j18;
    }

    public static int mul33DWordAdd(int i2, long j2, int[] iArr, int i3) {
        long j3 = i2 & 4294967295L;
        long j4 = j2 & 4294967295L;
        long j5 = (j3 * j4) + (iArr[i3] & 4294967295L);
        iArr[i3] = (int) j5;
        long j6 = j2 >>> 32;
        long j7 = (j3 * j6) + j4;
        long j8 = (j5 >>> 32) + j7 + (iArr[r4] & 4294967295L);
        iArr[i3 + 1] = (int) j8;
        long j9 = (j8 >>> 32) + j6 + (iArr[r4] & 4294967295L);
        iArr[i3 + 2] = (int) j9;
        long j10 = j9 >>> 32;
        long j11 = j10 + (iArr[r0] & 4294967295L);
        iArr[i3 + 3] = (int) j11;
        if ((j11 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(8, iArr, i3, 4);
    }

    public static int mul33WordAdd(int i2, int i3, int[] iArr, int i4) {
        long j2 = i2 & 4294967295L;
        long j3 = i3 & 4294967295L;
        long j4 = (j2 * j3) + (iArr[i4] & 4294967295L);
        iArr[i4] = (int) j4;
        long j5 = (j4 >>> 32) + j3 + (iArr[r5] & 4294967295L);
        iArr[i4 + 1] = (int) j5;
        long j6 = j5 >>> 32;
        long j7 = j6 + (iArr[r0] & 4294967295L);
        iArr[i4 + 2] = (int) j7;
        if ((j7 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(8, iArr, i4, 3);
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = 4294967295L;
        long j3 = iArr2[0] & 4294967295L;
        long j4 = iArr2[1] & 4294967295L;
        long j5 = iArr2[2] & 4294967295L;
        long j6 = iArr2[3] & 4294967295L;
        long j7 = iArr2[4] & 4294967295L;
        long j8 = iArr2[5] & 4294967295L;
        long j9 = iArr2[6] & 4294967295L;
        long j10 = iArr2[7] & 4294967295L;
        long j11 = 0;
        int i2 = 0;
        while (i2 < 8) {
            long j12 = j10;
            long j13 = iArr[i2] & j2;
            long j14 = j8;
            long j15 = (iArr3[i2] & j2) + (j13 * j3);
            iArr3[i2] = (int) j15;
            int i3 = i2 + 1;
            long j16 = j4;
            long j17 = (j15 >>> 32) + (j13 * j4) + (iArr3[i3] & j2);
            iArr3[i3] = (int) j17;
            long j18 = (j17 >>> 32) + (j13 * j5) + (iArr3[r6] & j2);
            iArr3[i2 + 2] = (int) j18;
            long j19 = (j18 >>> 32) + (j13 * j6) + (iArr3[r6] & j2);
            iArr3[i2 + 3] = (int) j19;
            long j20 = (j19 >>> 32) + (j13 * j7) + (iArr3[r6] & j2);
            iArr3[i2 + 4] = (int) j20;
            long j21 = (j20 >>> 32) + (j13 * j14) + (iArr3[r6] & j2);
            iArr3[i2 + 5] = (int) j21;
            long j22 = (j21 >>> 32) + (j13 * j9) + (iArr3[r6] & j2);
            iArr3[i2 + 6] = (int) j22;
            long j23 = (j22 >>> 32) + (j13 * j12) + (iArr3[r6] & j2);
            iArr3[i2 + 7] = (int) j23;
            long j24 = (j23 >>> 32) + j11 + (iArr3[r2] & j2);
            iArr3[i2 + 8] = (int) j24;
            j11 = j24 >>> 32;
            i2 = i3;
            j10 = j12;
            j8 = j14;
            j4 = j16;
            j2 = 4294967295L;
        }
        return (int) j11;
    }

    public static int mulByWord(int i2, int[] iArr) {
        long j2 = i2 & 4294967295L;
        long j3 = (iArr[0] & 4294967295L) * j2;
        iArr[0] = (int) j3;
        long j4 = (j3 >>> 32) + ((iArr[1] & 4294967295L) * j2);
        iArr[1] = (int) j4;
        long j5 = (j4 >>> 32) + ((iArr[2] & 4294967295L) * j2);
        iArr[2] = (int) j5;
        long j6 = (j5 >>> 32) + ((iArr[3] & 4294967295L) * j2);
        iArr[3] = (int) j6;
        long j7 = (j6 >>> 32) + ((iArr[4] & 4294967295L) * j2);
        iArr[4] = (int) j7;
        long j8 = (j7 >>> 32) + ((iArr[5] & 4294967295L) * j2);
        iArr[5] = (int) j8;
        long j9 = (j8 >>> 32) + ((iArr[6] & 4294967295L) * j2);
        iArr[6] = (int) j9;
        long j10 = (j9 >>> 32) + (j2 * (4294967295L & iArr[7]));
        iArr[7] = (int) j10;
        return (int) (j10 >>> 32);
    }

    public static int mulByWordAddTo(int i2, int[] iArr, int[] iArr2) {
        long j2 = i2 & 4294967295L;
        long j3 = ((iArr2[0] & 4294967295L) * j2) + (iArr[0] & 4294967295L);
        iArr2[0] = (int) j3;
        long j4 = (j3 >>> 32) + ((iArr2[1] & 4294967295L) * j2) + (iArr[1] & 4294967295L);
        iArr2[1] = (int) j4;
        long j5 = (j4 >>> 32) + ((iArr2[2] & 4294967295L) * j2) + (iArr[2] & 4294967295L);
        iArr2[2] = (int) j5;
        long j6 = (j5 >>> 32) + ((iArr2[3] & 4294967295L) * j2) + (iArr[3] & 4294967295L);
        iArr2[3] = (int) j6;
        long j7 = (j6 >>> 32) + ((iArr2[4] & 4294967295L) * j2) + (iArr[4] & 4294967295L);
        iArr2[4] = (int) j7;
        long j8 = (j7 >>> 32) + ((iArr2[5] & 4294967295L) * j2) + (iArr[5] & 4294967295L);
        iArr2[5] = (int) j8;
        long j9 = (j8 >>> 32) + ((iArr2[6] & 4294967295L) * j2) + (iArr[6] & 4294967295L);
        iArr2[6] = (int) j9;
        long j10 = (j9 >>> 32) + (j2 * (iArr2[7] & 4294967295L)) + (4294967295L & iArr[7]);
        iArr2[7] = (int) j10;
        return (int) (j10 >>> 32);
    }

    public static int mulWord(int i2, int[] iArr, int[] iArr2, int i3) {
        long j2 = i2 & 4294967295L;
        long j3 = 0;
        int i4 = 0;
        do {
            long j4 = j3 + ((iArr[i4] & 4294967295L) * j2);
            iArr2[i3 + i4] = (int) j4;
            j3 = j4 >>> 32;
            i4++;
        } while (i4 < 8);
        return (int) j3;
    }

    public static int mulWordAddTo(int i2, int[] iArr, int i3, int[] iArr2, int i4) {
        long j2 = i2 & 4294967295L;
        long j3 = ((iArr[i3] & 4294967295L) * j2) + (iArr2[i4] & 4294967295L);
        iArr2[i4] = (int) j3;
        long j4 = (j3 >>> 32) + ((iArr[i3 + 1] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 1] = (int) j4;
        long j5 = (j4 >>> 32) + ((iArr[i3 + 2] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 2] = (int) j5;
        long j6 = (j5 >>> 32) + ((iArr[i3 + 3] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 3] = (int) j6;
        long j7 = (j6 >>> 32) + ((iArr[i3 + 4] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 4] = (int) j7;
        long j8 = (j7 >>> 32) + ((iArr[i3 + 5] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 5] = (int) j8;
        long j9 = (j8 >>> 32) + ((iArr[i3 + 6] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L);
        iArr2[i4 + 6] = (int) j9;
        long j10 = (j9 >>> 32) + (j2 * (iArr[i3 + 7] & 4294967295L)) + (iArr2[r15] & 4294967295L);
        iArr2[i4 + 7] = (int) j10;
        return (int) (j10 >>> 32);
    }

    public static int mulWordDwordAdd(int i2, long j2, int[] iArr, int i3) {
        long j3 = i2 & 4294967295L;
        long j4 = ((j2 & 4294967295L) * j3) + (iArr[i3] & 4294967295L);
        iArr[i3] = (int) j4;
        long j5 = j3 * (j2 >>> 32);
        long j6 = (j4 >>> 32) + j5 + (iArr[r9] & 4294967295L);
        iArr[i3 + 1] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[r0] & 4294967295L);
        iArr[i3 + 2] = (int) j7;
        if ((j7 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(8, iArr, i3, 3);
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j2 = iArr[0] & 4294967295L;
        int i2 = 16;
        int i3 = 0;
        int i4 = 7;
        while (true) {
            int i5 = i4 - 1;
            long j3 = iArr[i4] & 4294967295L;
            long j4 = j3 * j3;
            iArr2[i2 - 1] = (i3 << 31) | ((int) (j4 >>> 33));
            i2 -= 2;
            iArr2[i2] = (int) (j4 >>> 1);
            i3 = (int) j4;
            if (i5 <= 0) {
                long j5 = j2 * j2;
                long j6 = (j5 >>> 33) | ((i3 << 31) & 4294967295L);
                iArr2[0] = (int) j5;
                long j7 = iArr[1] & 4294967295L;
                long j8 = j6 + (j7 * j2);
                int i6 = (int) j8;
                iArr2[1] = (i6 << 1) | (((int) (j5 >>> 32)) & 1);
                int i7 = i6 >>> 31;
                long j9 = (iArr2[2] & 4294967295L) + (j8 >>> 32);
                long j10 = iArr[2] & 4294967295L;
                long j11 = j9 + (j10 * j2);
                int i8 = (int) j11;
                iArr2[2] = (i8 << 1) | i7;
                long j12 = (iArr2[3] & 4294967295L) + (j11 >>> 32) + (j10 * j7);
                long j13 = (iArr2[4] & 4294967295L) + (j12 >>> 32);
                long j14 = iArr[3] & 4294967295L;
                long j15 = (iArr2[5] & 4294967295L) + (j13 >>> 32);
                long j16 = j13 & 4294967295L;
                long j17 = (iArr2[6] & 4294967295L) + (j15 >>> 32);
                long j18 = (j12 & 4294967295L) + (j14 * j2);
                int i9 = (int) j18;
                iArr2[3] = (i9 << 1) | (i8 >>> 31);
                int i10 = i9 >>> 31;
                long j19 = j16 + (j18 >>> 32) + (j14 * j7);
                long j20 = (j15 & 4294967295L) + (j19 >>> 32) + (j14 * j10);
                long j21 = j17 + (j20 >>> 32);
                long j22 = iArr[4] & 4294967295L;
                long j23 = (iArr2[7] & 4294967295L) + (j21 >>> 32);
                long j24 = j21 & 4294967295L;
                long j25 = (iArr2[8] & 4294967295L) + (j23 >>> 32);
                long j26 = (j19 & 4294967295L) + (j22 * j2);
                int i11 = (int) j26;
                iArr2[4] = (i11 << 1) | i10;
                long j27 = (j20 & 4294967295L) + (j26 >>> 32) + (j22 * j7);
                long j28 = j24 + (j27 >>> 32) + (j22 * j10);
                long j29 = (j23 & 4294967295L) + (j28 >>> 32) + (j22 * j14);
                long j30 = j25 + (j29 >>> 32);
                long j31 = j29 & 4294967295L;
                long j32 = iArr[5] & 4294967295L;
                long j33 = (iArr2[9] & 4294967295L) + (j30 >>> 32);
                long j34 = j30 & 4294967295L;
                long j35 = (iArr2[10] & 4294967295L) + (j33 >>> 32);
                long j36 = (j27 & 4294967295L) + (j32 * j2);
                int i12 = (int) j36;
                iArr2[5] = (i12 << 1) | (i11 >>> 31);
                long j37 = (j28 & 4294967295L) + (j36 >>> 32) + (j32 * j7);
                long j38 = j31 + (j37 >>> 32) + (j32 * j10);
                long j39 = j34 + (j38 >>> 32) + (j32 * j14);
                long j40 = (j33 & 4294967295L) + (j39 >>> 32) + (j32 * j22);
                long j41 = j35 + (j40 >>> 32);
                long j42 = j40 & 4294967295L;
                long j43 = iArr[6] & 4294967295L;
                long j44 = (iArr2[11] & 4294967295L) + (j41 >>> 32);
                long j45 = j41 & 4294967295L;
                long j46 = (iArr2[12] & 4294967295L) + (j44 >>> 32);
                long j47 = (j37 & 4294967295L) + (j43 * j2);
                int i13 = (int) j47;
                iArr2[6] = (i13 << 1) | (i12 >>> 31);
                long j48 = (j38 & 4294967295L) + (j47 >>> 32) + (j43 * j7);
                long j49 = (j39 & 4294967295L) + (j48 >>> 32) + (j43 * j10);
                long j50 = j48 & 4294967295L;
                long j51 = j42 + (j49 >>> 32) + (j43 * j14);
                long j52 = j45 + (j51 >>> 32) + (j43 * j22);
                long j53 = (j44 & 4294967295L) + (j52 >>> 32) + (j43 * j32);
                long j54 = j46 + (j53 >>> 32);
                long j55 = j53 & 4294967295L;
                long j56 = iArr[7] & 4294967295L;
                long j57 = (iArr2[13] & 4294967295L) + (j54 >>> 32);
                long j58 = j54 & 4294967295L;
                long j59 = (iArr2[14] & 4294967295L) + (j57 >>> 32);
                long j60 = 4294967295L & j57;
                long j61 = j50 + (j2 * j56);
                int i14 = (int) j61;
                iArr2[7] = (i13 >>> 31) | (i14 << 1);
                int i15 = i14 >>> 31;
                long j62 = (j49 & 4294967295L) + (j61 >>> 32) + (j56 * j7);
                long j63 = (j51 & 4294967295L) + (j62 >>> 32) + (j56 * j10);
                long j64 = (j52 & 4294967295L) + (j63 >>> 32) + (j56 * j14);
                long j65 = j55 + (j64 >>> 32) + (j56 * j22);
                long j66 = j58 + (j65 >>> 32) + (j56 * j32);
                long j67 = j60 + (j66 >>> 32) + (j56 * j43);
                long j68 = j59 + (j67 >>> 32);
                int i16 = (int) j62;
                iArr2[8] = i15 | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) j63;
                iArr2[9] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) j64;
                iArr2[10] = i19 | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) j65;
                iArr2[11] = i21 | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) j66;
                iArr2[12] = i23 | (i24 << 1);
                int i25 = i24 >>> 31;
                int i26 = (int) j67;
                iArr2[13] = i25 | (i26 << 1);
                int i27 = i26 >>> 31;
                int i28 = (int) j68;
                iArr2[14] = i27 | (i28 << 1);
                iArr2[15] = (i28 >>> 31) | ((iArr2[15] + ((int) (j68 >>> 32))) << 1);
                return;
            }
            i4 = i5;
        }
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (iArr[0] & 4294967295L) - (iArr2[0] & 4294967295L);
        iArr3[0] = (int) j2;
        long j3 = (j2 >> 32) + ((iArr[1] & 4294967295L) - (iArr2[1] & 4294967295L));
        iArr3[1] = (int) j3;
        long j4 = (j3 >> 32) + ((iArr[2] & 4294967295L) - (iArr2[2] & 4294967295L));
        iArr3[2] = (int) j4;
        long j5 = (j4 >> 32) + ((iArr[3] & 4294967295L) - (iArr2[3] & 4294967295L));
        iArr3[3] = (int) j5;
        long j6 = (j5 >> 32) + ((iArr[4] & 4294967295L) - (iArr2[4] & 4294967295L));
        iArr3[4] = (int) j6;
        long j7 = (j6 >> 32) + ((iArr[5] & 4294967295L) - (iArr2[5] & 4294967295L));
        iArr3[5] = (int) j7;
        long j8 = (j7 >> 32) + ((iArr[6] & 4294967295L) - (iArr2[6] & 4294967295L));
        iArr3[6] = (int) j8;
        long j9 = (j8 >> 32) + ((iArr[7] & 4294967295L) - (iArr2[7] & 4294967295L));
        iArr3[7] = (int) j9;
        return (int) (j9 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = ((iArr3[0] & 4294967295L) - (iArr[0] & 4294967295L)) - (iArr2[0] & 4294967295L);
        iArr3[0] = (int) j2;
        long j3 = (j2 >> 32) + (((iArr3[1] & 4294967295L) - (iArr[1] & 4294967295L)) - (iArr2[1] & 4294967295L));
        iArr3[1] = (int) j3;
        long j4 = (j3 >> 32) + (((iArr3[2] & 4294967295L) - (iArr[2] & 4294967295L)) - (iArr2[2] & 4294967295L));
        iArr3[2] = (int) j4;
        long j5 = (j4 >> 32) + (((iArr3[3] & 4294967295L) - (iArr[3] & 4294967295L)) - (iArr2[3] & 4294967295L));
        iArr3[3] = (int) j5;
        long j6 = (j5 >> 32) + (((iArr3[4] & 4294967295L) - (iArr[4] & 4294967295L)) - (iArr2[4] & 4294967295L));
        iArr3[4] = (int) j6;
        long j7 = (j6 >> 32) + (((iArr3[5] & 4294967295L) - (iArr[5] & 4294967295L)) - (iArr2[5] & 4294967295L));
        iArr3[5] = (int) j7;
        long j8 = (j7 >> 32) + (((iArr3[6] & 4294967295L) - (iArr[6] & 4294967295L)) - (iArr2[6] & 4294967295L));
        iArr3[6] = (int) j8;
        long j9 = (j8 >> 32) + (((iArr3[7] & 4294967295L) - (iArr[7] & 4294967295L)) - (iArr2[7] & 4294967295L));
        iArr3[7] = (int) j9;
        return (int) (j9 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j2 = (iArr2[0] & 4294967295L) - (iArr[0] & 4294967295L);
        iArr2[0] = (int) j2;
        long j3 = (j2 >> 32) + ((iArr2[1] & 4294967295L) - (iArr[1] & 4294967295L));
        iArr2[1] = (int) j3;
        long j4 = (j3 >> 32) + ((iArr2[2] & 4294967295L) - (iArr[2] & 4294967295L));
        iArr2[2] = (int) j4;
        long j5 = (j4 >> 32) + ((iArr2[3] & 4294967295L) - (iArr[3] & 4294967295L));
        iArr2[3] = (int) j5;
        long j6 = (j5 >> 32) + ((iArr2[4] & 4294967295L) - (iArr[4] & 4294967295L));
        iArr2[4] = (int) j6;
        long j7 = (j6 >> 32) + ((iArr2[5] & 4294967295L) - (iArr[5] & 4294967295L));
        iArr2[5] = (int) j7;
        long j8 = (j7 >> 32) + ((iArr2[6] & 4294967295L) - (iArr[6] & 4294967295L));
        iArr2[6] = (int) j8;
        long j9 = (j8 >> 32) + ((iArr2[7] & 4294967295L) - (4294967295L & iArr[7]));
        iArr2[7] = (int) j9;
        return (int) (j9 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[32];
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = iArr[i2];
            if (i3 != 0) {
                h.a(i3, bArr, (7 - i2) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static BigInteger toBigInteger64(long[] jArr) {
        byte[] bArr = new byte[32];
        for (int i2 = 0; i2 < 4; i2++) {
            long j2 = jArr[i2];
            if (j2 != 0) {
                h.a(j2, bArr, (3 - i2) << 3);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static void zero(int[] iArr) {
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        iArr[4] = 0;
        iArr[5] = 0;
        iArr[6] = 0;
        iArr[7] = 0;
    }

    public static boolean gte(int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 7; i4 >= 0; i4--) {
            int i5 = iArr[i2 + i4] ^ Integer.MIN_VALUE;
            int i6 = Integer.MIN_VALUE ^ iArr2[i3 + i4];
            if (i5 < i6) {
                return false;
            }
            if (i5 > i6) {
                return true;
            }
        }
        return true;
    }

    public static int add(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = (iArr[i2] & 4294967295L) + (iArr2[i3] & 4294967295L);
        iArr3[i4] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[i2 + 1] & 4294967295L) + (iArr2[i3 + 1] & 4294967295L);
        iArr3[i4 + 1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[i2 + 2] & 4294967295L) + (iArr2[i3 + 2] & 4294967295L);
        iArr3[i4 + 2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[i2 + 3] & 4294967295L) + (iArr2[i3 + 3] & 4294967295L);
        iArr3[i4 + 3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[i2 + 4] & 4294967295L) + (iArr2[i3 + 4] & 4294967295L);
        iArr3[i4 + 4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[i2 + 5] & 4294967295L) + (iArr2[i3 + 5] & 4294967295L);
        iArr3[i4 + 5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[i2 + 6] & 4294967295L) + (iArr2[i3 + 6] & 4294967295L);
        iArr3[i4 + 6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[i2 + 7] & 4294967295L) + (iArr2[i3 + 7] & 4294967295L);
        iArr3[i4 + 7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int addBothTo(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = (iArr[i2] & 4294967295L) + (iArr2[i3] & 4294967295L) + (iArr3[i4] & 4294967295L);
        iArr3[i4] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[i2 + 1] & 4294967295L) + (iArr2[i3 + 1] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[i2 + 2] & 4294967295L) + (iArr2[i3 + 2] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[i2 + 3] & 4294967295L) + (iArr2[i3 + 3] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[i2 + 4] & 4294967295L) + (iArr2[i3 + 4] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[i2 + 5] & 4294967295L) + (iArr2[i3 + 5] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[i2 + 6] & 4294967295L) + (iArr2[i3 + 6] & 4294967295L) + (iArr3[r7] & 4294967295L);
        iArr3[i4 + 6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[i2 + 7] & 4294967295L) + (iArr2[i3 + 7] & 4294967295L) + (iArr3[r15] & 4294967295L);
        iArr3[i4 + 7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int addTo(int[] iArr, int i2, int[] iArr2, int i3, int i4) {
        long j2 = (i4 & 4294967295L) + (iArr[i2] & 4294967295L) + (iArr2[i3] & 4294967295L);
        iArr2[i3] = (int) j2;
        long j3 = (j2 >>> 32) + (iArr[i2 + 1] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 1] = (int) j3;
        long j4 = (j3 >>> 32) + (iArr[i2 + 2] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 2] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[i2 + 3] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 3] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[i2 + 4] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 4] = (int) j6;
        long j7 = (j6 >>> 32) + (iArr[i2 + 5] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 5] = (int) j7;
        long j8 = (j7 >>> 32) + (iArr[i2 + 6] & 4294967295L) + (iArr2[r6] & 4294967295L);
        iArr2[i3 + 6] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[i2 + 7] & 4294967295L) + (4294967295L & iArr2[r12]);
        iArr2[i3 + 7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int sub(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = (iArr[i2] & 4294967295L) - (iArr2[i3] & 4294967295L);
        iArr3[i4] = (int) j2;
        long j3 = (j2 >> 32) + ((iArr[i2 + 1] & 4294967295L) - (iArr2[i3 + 1] & 4294967295L));
        iArr3[i4 + 1] = (int) j3;
        long j4 = (j3 >> 32) + ((iArr[i2 + 2] & 4294967295L) - (iArr2[i3 + 2] & 4294967295L));
        iArr3[i4 + 2] = (int) j4;
        long j5 = (j4 >> 32) + ((iArr[i2 + 3] & 4294967295L) - (iArr2[i3 + 3] & 4294967295L));
        iArr3[i4 + 3] = (int) j5;
        long j6 = (j5 >> 32) + ((iArr[i2 + 4] & 4294967295L) - (iArr2[i3 + 4] & 4294967295L));
        iArr3[i4 + 4] = (int) j6;
        long j7 = (j6 >> 32) + ((iArr[i2 + 5] & 4294967295L) - (iArr2[i3 + 5] & 4294967295L));
        iArr3[i4 + 5] = (int) j7;
        long j8 = (j7 >> 32) + ((iArr[i2 + 6] & 4294967295L) - (iArr2[i3 + 6] & 4294967295L));
        iArr3[i4 + 6] = (int) j8;
        long j9 = (j8 >> 32) + ((iArr[i2 + 7] & 4294967295L) - (iArr2[i3 + 7] & 4294967295L));
        iArr3[i4 + 7] = (int) j9;
        return (int) (j9 >> 32);
    }

    public static int subFrom(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = (iArr2[i3] & 4294967295L) - (iArr[i2] & 4294967295L);
        iArr2[i3] = (int) j2;
        long j3 = (j2 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 1] & 4294967295L));
        iArr2[i3 + 1] = (int) j3;
        long j4 = (j3 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 2] & 4294967295L));
        iArr2[i3 + 2] = (int) j4;
        long j5 = (j4 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 3] & 4294967295L));
        iArr2[i3 + 3] = (int) j5;
        long j6 = (j5 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 4] & 4294967295L));
        iArr2[i3 + 4] = (int) j6;
        long j7 = (j6 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 5] & 4294967295L));
        iArr2[i3 + 5] = (int) j7;
        long j8 = (j7 >> 32) + ((iArr2[r5] & 4294967295L) - (iArr[i2 + 6] & 4294967295L));
        iArr2[i3 + 6] = (int) j8;
        long j9 = (j8 >> 32) + ((iArr2[r13] & 4294967295L) - (iArr[i2 + 7] & 4294967295L));
        iArr2[i3 + 7] = (int) j9;
        return (int) (j9 >> 32);
    }

    public static int mulAddTo(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = iArr2[i3] & 4294967295L;
        long j3 = iArr2[i3 + 1] & 4294967295L;
        long j4 = iArr2[i3 + 2] & 4294967295L;
        long j5 = iArr2[i3 + 3] & 4294967295L;
        long j6 = iArr2[i3 + 4] & 4294967295L;
        long j7 = iArr2[i3 + 5] & 4294967295L;
        long j8 = iArr2[i3 + 6] & 4294967295L;
        long j9 = iArr2[i3 + 7] & 4294967295L;
        int i5 = 0;
        long j10 = 0;
        int i6 = i4;
        while (i5 < 8) {
            int i7 = i5;
            long j11 = iArr[i2 + i5] & 4294967295L;
            long j12 = j2;
            long j13 = (j11 * j2) + (iArr3[i6] & 4294967295L);
            long j14 = j9;
            iArr3[i6] = (int) j13;
            int i8 = i6 + 1;
            long j15 = (j13 >>> 32) + (j11 * j3) + (iArr3[i8] & 4294967295L);
            iArr3[i8] = (int) j15;
            long j16 = (j15 >>> 32) + (j11 * j4) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 2] = (int) j16;
            long j17 = (j16 >>> 32) + (j11 * j5) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 3] = (int) j17;
            long j18 = (j17 >>> 32) + (j11 * j6) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 4] = (int) j18;
            long j19 = (j18 >>> 32) + (j11 * j7) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 5] = (int) j19;
            long j20 = (j19 >>> 32) + (j11 * j8) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 6] = (int) j20;
            long j21 = (j20 >>> 32) + (j11 * j14) + (iArr3[r5] & 4294967295L);
            iArr3[i6 + 7] = (int) j21;
            long j22 = (j21 >>> 32) + j10 + (iArr3[r16] & 4294967295L);
            iArr3[i6 + 8] = (int) j22;
            j10 = j22 >>> 32;
            i5 = i7 + 1;
            i6 = i8;
            j9 = j14;
            j2 = j12;
            j4 = j4;
            j3 = j3;
        }
        return (int) j10;
    }

    public static void mul(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = iArr2[i3] & 4294967295L;
        long j3 = iArr2[i3 + 1] & 4294967295L;
        long j4 = iArr2[i3 + 2] & 4294967295L;
        long j5 = iArr2[i3 + 3] & 4294967295L;
        long j6 = iArr2[i3 + 4] & 4294967295L;
        long j7 = iArr2[i3 + 5] & 4294967295L;
        long j8 = iArr2[i3 + 6] & 4294967295L;
        long j9 = iArr[i2] & 4294967295L;
        long j10 = j9 * j2;
        iArr3[i4] = (int) j10;
        long j11 = (j10 >>> 32) + (j9 * j3);
        iArr3[i4 + 1] = (int) j11;
        long j12 = (j11 >>> 32) + (j9 * j4);
        iArr3[i4 + 2] = (int) j12;
        long j13 = (j12 >>> 32) + (j9 * j5);
        iArr3[i4 + 3] = (int) j13;
        long j14 = (j13 >>> 32) + (j9 * j6);
        iArr3[i4 + 4] = (int) j14;
        long j15 = (j14 >>> 32) + (j9 * j7);
        iArr3[i4 + 5] = (int) j15;
        long j16 = (j15 >>> 32) + (j9 * j8);
        iArr3[i4 + 6] = (int) j16;
        long j17 = iArr2[i3 + 7] & 4294967295L;
        long j18 = (j16 >>> 32) + (j9 * j17);
        iArr3[i4 + 7] = (int) j18;
        iArr3[i4 + 8] = (int) (j18 >>> 32);
        int i5 = 1;
        int i6 = i4;
        while (i5 < 8) {
            int i7 = i6 + 1;
            long j19 = iArr[i2 + i5] & 4294967295L;
            long j20 = j17;
            int i8 = i5;
            long j21 = (j19 * j2) + (iArr3[i7] & 4294967295L);
            iArr3[i7] = (int) j21;
            long j22 = (j21 >>> 32) + (j19 * j3) + (iArr3[r16] & 4294967295L);
            iArr3[i6 + 2] = (int) j22;
            long j23 = j4;
            long j24 = (j22 >>> 32) + (j19 * j4) + (iArr3[r16] & 4294967295L);
            iArr3[i6 + 3] = (int) j24;
            int i9 = i6;
            long j25 = (j24 >>> 32) + (j19 * j5) + (iArr3[r6] & 4294967295L);
            iArr3[i6 + 4] = (int) j25;
            long j26 = (j25 >>> 32) + (j19 * j6) + (iArr3[r5] & 4294967295L);
            iArr3[i9 + 5] = (int) j26;
            long j27 = (j26 >>> 32) + (j19 * j7) + (iArr3[r5] & 4294967295L);
            iArr3[i9 + 6] = (int) j27;
            long j28 = (j27 >>> 32) + (j19 * j8) + (iArr3[r5] & 4294967295L);
            iArr3[i9 + 7] = (int) j28;
            long j29 = (j28 >>> 32) + (j19 * j20) + (iArr3[r3] & 4294967295L);
            iArr3[i9 + 8] = (int) j29;
            iArr3[i9 + 9] = (int) (j29 >>> 32);
            i5 = i8 + 1;
            i6 = i7;
            j4 = j23;
            j17 = j20;
        }
    }

    public static void square(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = iArr[i2] & 4294967295L;
        int i4 = 0;
        int i5 = 16;
        int i6 = 7;
        while (true) {
            int i7 = i6 - 1;
            long j3 = iArr[i2 + i6] & 4294967295L;
            long j4 = j3 * j3;
            iArr2[i3 + (i5 - 1)] = (i4 << 31) | ((int) (j4 >>> 33));
            i5 -= 2;
            iArr2[i3 + i5] = (int) (j4 >>> 1);
            i4 = (int) j4;
            if (i7 <= 0) {
                long j5 = j2 * j2;
                long j6 = (j5 >>> 33) | ((i4 << 31) & 4294967295L);
                iArr2[i3] = (int) j5;
                int i8 = ((int) (j5 >>> 32)) & 1;
                long j7 = iArr[i2 + 1] & 4294967295L;
                long j8 = j6 + (j7 * j2);
                int i9 = (int) j8;
                iArr2[i3 + 1] = (i9 << 1) | i8;
                int i10 = i9 >>> 31;
                long j9 = (iArr2[r12] & 4294967295L) + (j8 >>> 32);
                long j10 = iArr[i2 + 2] & 4294967295L;
                long j11 = iArr2[r15] & 4294967295L;
                long j12 = j9 + (j10 * j2);
                int i11 = (int) j12;
                iArr2[i3 + 2] = (i11 << 1) | i10;
                long j13 = j11 + (j12 >>> 32) + (j10 * j7);
                long j14 = (iArr2[r7] & 4294967295L) + (j13 >>> 32);
                long j15 = iArr[i2 + 3] & 4294967295L;
                long j16 = (iArr2[r20] & 4294967295L) + (j14 >>> 32);
                long j17 = j14 & 4294967295L;
                long j18 = (iArr2[r24] & 4294967295L) + (j16 >>> 32);
                long j19 = (j13 & 4294967295L) + (j15 * j2);
                int i12 = (int) j19;
                iArr2[i3 + 3] = (i12 << 1) | (i11 >>> 31);
                long j20 = j17 + (j19 >>> 32) + (j15 * j7);
                long j21 = (j16 & 4294967295L) + (j20 >>> 32) + (j15 * j10);
                long j22 = j18 + (j21 >>> 32);
                long j23 = j21 & 4294967295L;
                long j24 = iArr[i2 + 4] & 4294967295L;
                long j25 = (iArr2[r3] & 4294967295L) + (j22 >>> 32);
                long j26 = (iArr2[r19] & 4294967295L) + (j25 >>> 32);
                long j27 = (j20 & 4294967295L) + (j24 * j2);
                int i13 = (int) j27;
                iArr2[i3 + 4] = (i13 << 1) | (i12 >>> 31);
                int i14 = i13 >>> 31;
                long j28 = j23 + (j27 >>> 32) + (j24 * j7);
                long j29 = (j22 & 4294967295L) + (j28 >>> 32) + (j24 * j10);
                long j30 = (j25 & 4294967295L) + (j29 >>> 32) + (j24 * j15);
                long j31 = j26 + (j30 >>> 32);
                long j32 = j30 & 4294967295L;
                long j33 = iArr[i2 + 5] & 4294967295L;
                long j34 = (iArr2[r7] & 4294967295L) + (j31 >>> 32);
                long j35 = j31 & 4294967295L;
                long j36 = (iArr2[r21] & 4294967295L) + (j34 >>> 32);
                long j37 = (j28 & 4294967295L) + (j33 * j2);
                int i15 = (int) j37;
                iArr2[i3 + 5] = (i15 << 1) | i14;
                int i16 = i15 >>> 31;
                long j38 = (j29 & 4294967295L) + (j37 >>> 32) + (j33 * j7);
                long j39 = j32 + (j38 >>> 32) + (j33 * j10);
                long j40 = j35 + (j39 >>> 32) + (j33 * j15);
                long j41 = (j34 & 4294967295L) + (j40 >>> 32) + (j33 * j24);
                long j42 = j36 + (j41 >>> 32);
                long j43 = j41 & 4294967295L;
                long j44 = iArr[i2 + 6] & 4294967295L;
                long j45 = (iArr2[r7] & 4294967295L) + (j42 >>> 32);
                long j46 = j42 & 4294967295L;
                long j47 = (iArr2[r20] & 4294967295L) + (j45 >>> 32);
                long j48 = (j38 & 4294967295L) + (j44 * j2);
                int i17 = (int) j48;
                iArr2[i3 + 6] = (i17 << 1) | i16;
                int i18 = i17 >>> 31;
                long j49 = (j39 & 4294967295L) + (j48 >>> 32) + (j44 * j7);
                long j50 = (j40 & 4294967295L) + (j49 >>> 32) + (j44 * j10);
                long j51 = j43 + (j50 >>> 32) + (j44 * j15);
                long j52 = j50 & 4294967295L;
                long j53 = j46 + (j51 >>> 32) + (j44 * j24);
                long j54 = (j45 & 4294967295L) + (j53 >>> 32) + (j44 * j33);
                long j55 = j47 + (j54 >>> 32);
                long j56 = j54 & 4294967295L;
                long j57 = iArr[i2 + 7] & 4294967295L;
                long j58 = (iArr2[r7] & 4294967295L) + (j55 >>> 32);
                long j59 = j55 & 4294967295L;
                long j60 = (iArr2[r28] & 4294967295L) + (j58 >>> 32);
                long j61 = 4294967295L & j58;
                long j62 = (j49 & 4294967295L) + (j2 * j57);
                int i19 = (int) j62;
                iArr2[i3 + 7] = (i19 << 1) | i18;
                long j63 = j52 + (j62 >>> 32) + (j7 * j57);
                long j64 = (j51 & 4294967295L) + (j63 >>> 32) + (j57 * j10);
                long j65 = (j53 & 4294967295L) + (j64 >>> 32) + (j57 * j15);
                long j66 = j56 + (j65 >>> 32) + (j57 * j24);
                long j67 = j59 + (j66 >>> 32) + (j57 * j33);
                long j68 = j61 + (j67 >>> 32) + (j57 * j44);
                long j69 = j60 + (j68 >>> 32);
                int i20 = (int) j63;
                iArr2[i3 + 8] = (i19 >>> 31) | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) j64;
                iArr2[i3 + 9] = i21 | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) j65;
                iArr2[i3 + 10] = i23 | (i24 << 1);
                int i25 = i24 >>> 31;
                int i26 = (int) j66;
                iArr2[i3 + 11] = i25 | (i26 << 1);
                int i27 = i26 >>> 31;
                int i28 = (int) j67;
                iArr2[i3 + 12] = i27 | (i28 << 1);
                int i29 = i28 >>> 31;
                int i30 = (int) j68;
                iArr2[i3 + 13] = i29 | (i30 << 1);
                int i31 = i30 >>> 31;
                int i32 = (int) j69;
                iArr2[i3 + 14] = i31 | (i32 << 1);
                int i33 = i32 >>> 31;
                int i34 = i3 + 15;
                iArr2[i34] = i33 | ((iArr2[i34] + ((int) (j69 >>> 32))) << 1);
                return;
            }
            i6 = i7;
        }
    }
}
