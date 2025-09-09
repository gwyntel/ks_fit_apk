package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;

/* loaded from: classes5.dex */
public class Curve25519Field {
    public static final long M = 4294967295L;
    public static final int P7 = Integer.MAX_VALUE;
    public static final int PInv = 19;
    public static final int[] P = {-19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE};
    public static final int[] PExt = {361, 0, 0, 0, 0, 0, 0, 0, -19, -1, -1, -1, -1, -1, -1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        Nat256.add(iArr, iArr2, iArr3);
        if (Nat256.gte(iArr3, P)) {
            subPFrom(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        Nat.add(16, iArr, iArr2, iArr3);
        if (Nat.gte(16, iArr3, PExt)) {
            subPExtFrom(iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        Nat.inc(8, iArr, iArr2);
        if (Nat256.gte(iArr2, P)) {
            subPFrom(iArr2);
        }
    }

    public static int addPExtTo(int[] iArr) {
        int[] iArr2 = PExt;
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L);
        iArr[0] = (int) j2;
        long jIncAt = j2 >> 32;
        if (jIncAt != 0) {
            jIncAt = Nat.incAt(8, iArr, 1);
        }
        long j3 = jIncAt + ((iArr[8] & 4294967295L) - 19);
        iArr[8] = (int) j3;
        long jDecAt = j3 >> 32;
        if (jDecAt != 0) {
            jDecAt = Nat.decAt(15, iArr, 9);
        }
        long j4 = jDecAt + (iArr[15] & 4294967295L) + (4294967295L & (iArr2[15] + 1));
        iArr[15] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int addPTo(int[] iArr) {
        long j2 = (iArr[0] & 4294967295L) - 19;
        iArr[0] = (int) j2;
        long jDecAt = j2 >> 32;
        if (jDecAt != 0) {
            jDecAt = Nat.decAt(7, iArr, 1);
        }
        long j3 = jDecAt + (4294967295L & iArr[7]) + 2147483648L;
        iArr[7] = (int) j3;
        return (int) (j3 >> 32);
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = Nat256.fromBigInteger(bigInteger);
        while (true) {
            int[] iArr = P;
            if (!Nat256.gte(iArrFromBigInteger, iArr)) {
                return iArrFromBigInteger;
            }
            Nat256.subFrom(iArr, iArrFromBigInteger);
        }
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            Nat256.add(iArr, P, iArr2);
            Nat.shiftDownBit(8, iArr2, 0);
        }
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreateExt = Nat256.createExt();
        Nat256.mul(iArr, iArr2, iArrCreateExt);
        reduce(iArrCreateExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        Nat256.mulAddTo(iArr, iArr2, iArr3);
        if (Nat.gte(16, iArr3, PExt)) {
            subPExtFrom(iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (Nat256.isZero(iArr)) {
            Nat256.zero(iArr2);
        } else {
            Nat256.sub(P, iArr, iArr2);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        int i2 = iArr[7];
        Nat.shiftUpBit(8, iArr, 8, i2, iArr2, 0);
        int iMulByWordAddTo = Nat256.mulByWordAddTo(19, iArr, iArr2) << 1;
        int i3 = iArr2[7];
        iArr2[7] = (i3 & Integer.MAX_VALUE) + Nat.addWordTo(7, (iMulByWordAddTo + ((i3 >>> 31) - (i2 >>> 31))) * 19, iArr2);
        if (Nat256.gte(iArr2, P)) {
            subPFrom(iArr2);
        }
    }

    public static void reduce27(int i2, int[] iArr) {
        int i3 = iArr[7];
        iArr[7] = (i3 & Integer.MAX_VALUE) + Nat.addWordTo(7, ((i2 << 1) | (i3 >>> 31)) * 19, iArr);
        if (Nat256.gte(iArr, P)) {
            subPFrom(iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreateExt = Nat256.createExt();
        Nat256.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
    }

    public static void squareN(int[] iArr, int i2, int[] iArr2) {
        int[] iArrCreateExt = Nat256.createExt();
        Nat256.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
        while (true) {
            i2--;
            if (i2 <= 0) {
                return;
            }
            Nat256.square(iArr2, iArrCreateExt);
            reduce(iArrCreateExt, iArr2);
        }
    }

    public static int subPExtFrom(int[] iArr) {
        int[] iArr2 = PExt;
        long j2 = (iArr[0] & 4294967295L) - (iArr2[0] & 4294967295L);
        iArr[0] = (int) j2;
        long jDecAt = j2 >> 32;
        if (jDecAt != 0) {
            jDecAt = Nat.decAt(8, iArr, 1);
        }
        long j3 = jDecAt + (iArr[8] & 4294967295L) + 19;
        iArr[8] = (int) j3;
        long jIncAt = j3 >> 32;
        if (jIncAt != 0) {
            jIncAt = Nat.incAt(15, iArr, 9);
        }
        long j4 = jIncAt + ((iArr[15] & 4294967295L) - (4294967295L & (iArr2[15] + 1)));
        iArr[15] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int subPFrom(int[] iArr) {
        long j2 = (iArr[0] & 4294967295L) + 19;
        iArr[0] = (int) j2;
        long jIncAt = j2 >> 32;
        if (jIncAt != 0) {
            jIncAt = Nat.incAt(7, iArr, 1);
        }
        long j3 = jIncAt + ((4294967295L & iArr[7]) - 2147483648L);
        iArr[7] = (int) j3;
        return (int) (j3 >> 32);
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.sub(iArr, iArr2, iArr3) != 0) {
            addPTo(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(16, iArr, iArr2, iArr3) != 0) {
            addPExtTo(iArr3);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        Nat.shiftUpBit(8, iArr, 0, iArr2);
        if (Nat256.gte(iArr2, P)) {
            subPFrom(iArr2);
        }
    }
}
