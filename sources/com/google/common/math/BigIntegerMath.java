package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class BigIntegerMath {

    /* renamed from: a, reason: collision with root package name */
    static final BigInteger f14688a = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double LN_10 = Math.log(10.0d);
    private static final double LN_2 = Math.log(2.0d);

    /* renamed from: com.google.common.math.BigIntegerMath$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14689a;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            f14689a = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14689a[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f14689a[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f14689a[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f14689a[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f14689a[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f14689a[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f14689a[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @GwtIncompatible
    private static class BigIntegerToDoubleRounder extends ToDoubleRounder<BigInteger> {

        /* renamed from: a, reason: collision with root package name */
        static final BigIntegerToDoubleRounder f14690a = new BigIntegerToDoubleRounder();

        private BigIntegerToDoubleRounder() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: f, reason: merged with bridge method [inline-methods] */
        public BigInteger a(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger.subtract(bigInteger2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public double c(BigInteger bigInteger) {
            return DoubleUtils.a(bigInteger);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: h, reason: merged with bridge method [inline-methods] */
        public int d(BigInteger bigInteger) {
            return bigInteger.signum();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: i, reason: merged with bridge method [inline-methods] */
        public BigInteger e(double d2, RoundingMode roundingMode) {
            return DoubleMath.roundToBigInteger(d2, roundingMode);
        }
    }

    private BigIntegerMath() {
    }

    static boolean a(BigInteger bigInteger) {
        return bigInteger.bitLength() <= 63;
    }

    static BigInteger b(List list) {
        return c(list, 0, list.size());
    }

    public static BigInteger binomial(int i2, int i3) {
        int i4;
        MathPreconditions.e("n", i2);
        MathPreconditions.e("k", i3);
        int i5 = 1;
        Preconditions.checkArgument(i3 <= i2, "k (%s) > n (%s)", i3, i2);
        if (i3 > (i2 >> 1)) {
            i3 = i2 - i3;
        }
        int[] iArr = LongMath.f14708e;
        if (i3 < iArr.length && i2 <= iArr[i3]) {
            return BigInteger.valueOf(LongMath.binomial(i2, i3));
        }
        BigInteger bigIntegerDivide = BigInteger.ONE;
        long j2 = i2;
        int iLog2 = LongMath.log2(j2, RoundingMode.CEILING);
        long j3 = 1;
        while (true) {
            int i6 = iLog2;
            while (i5 < i3) {
                i4 = i2 - i5;
                i5++;
                i6 += iLog2;
                if (i6 >= 63) {
                    break;
                }
                j2 *= i4;
                j3 *= i5;
            }
            return bigIntegerDivide.multiply(BigInteger.valueOf(j2)).divide(BigInteger.valueOf(j3));
            bigIntegerDivide = bigIntegerDivide.multiply(BigInteger.valueOf(j2)).divide(BigInteger.valueOf(j3));
            j2 = i4;
            j3 = i5;
        }
    }

    static BigInteger c(List list, int i2, int i3) {
        int i4 = i3 - i2;
        if (i4 == 0) {
            return BigInteger.ONE;
        }
        if (i4 == 1) {
            return (BigInteger) list.get(i2);
        }
        if (i4 == 2) {
            return ((BigInteger) list.get(i2)).multiply((BigInteger) list.get(i2 + 1));
        }
        if (i4 == 3) {
            return ((BigInteger) list.get(i2)).multiply((BigInteger) list.get(i2 + 1)).multiply((BigInteger) list.get(i2 + 2));
        }
        int i5 = (i3 + i2) >>> 1;
        return c(list, i2, i5).multiply(c(list, i5, i3));
    }

    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.CEILING));
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int i2) {
        MathPreconditions.e("n", i2);
        long[] jArr = LongMath.f14707d;
        if (i2 < jArr.length) {
            return BigInteger.valueOf(jArr[i2]);
        }
        RoundingMode roundingMode = RoundingMode.CEILING;
        ArrayList arrayList = new ArrayList(IntMath.divide(IntMath.log2(i2, roundingMode) * i2, 64, roundingMode));
        int length = jArr.length;
        long j2 = jArr[length - 1];
        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j2);
        long j3 = j2 >> iNumberOfTrailingZeros;
        RoundingMode roundingMode2 = RoundingMode.FLOOR;
        int iLog2 = LongMath.log2(j3, roundingMode2) + 1;
        long j4 = length;
        int iLog22 = LongMath.log2(j4, roundingMode2);
        int i3 = iLog22 + 1;
        int i4 = 1 << iLog22;
        while (j4 <= i2) {
            if ((j4 & i4) != 0) {
                i4 <<= 1;
                i3++;
            }
            int iNumberOfTrailingZeros2 = Long.numberOfTrailingZeros(j4);
            long j5 = j4 >> iNumberOfTrailingZeros2;
            iNumberOfTrailingZeros += iNumberOfTrailingZeros2;
            if ((i3 - iNumberOfTrailingZeros2) + iLog2 >= 64) {
                arrayList.add(BigInteger.valueOf(j3));
                j3 = 1;
            }
            j3 *= j5;
            iLog2 = LongMath.log2(j3, RoundingMode.FLOOR) + 1;
            j4++;
        }
        if (j3 > 1) {
            arrayList.add(BigInteger.valueOf(j3));
        }
        return b(arrayList).shiftLeft(iNumberOfTrailingZeros);
    }

    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }

    @GwtIncompatible
    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        int iCompareTo;
        MathPreconditions.j("x", bigInteger);
        if (a(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int iLog2 = (int) ((log2(bigInteger, RoundingMode.FLOOR) * LN_2) / LN_10);
        BigInteger bigInteger2 = BigInteger.TEN;
        BigInteger bigIntegerPow = bigInteger2.pow(iLog2);
        int iCompareTo2 = bigIntegerPow.compareTo(bigInteger);
        if (iCompareTo2 > 0) {
            do {
                iLog2--;
                bigIntegerPow = bigIntegerPow.divide(BigInteger.TEN);
                iCompareTo = bigIntegerPow.compareTo(bigInteger);
            } while (iCompareTo > 0);
        } else {
            BigInteger bigIntegerMultiply = bigInteger2.multiply(bigIntegerPow);
            int i2 = iCompareTo2;
            int iCompareTo3 = bigIntegerMultiply.compareTo(bigInteger);
            while (iCompareTo3 <= 0) {
                iLog2++;
                BigInteger bigIntegerMultiply2 = BigInteger.TEN.multiply(bigIntegerMultiply);
                int iCompareTo4 = bigIntegerMultiply2.compareTo(bigInteger);
                bigIntegerPow = bigIntegerMultiply;
                bigIntegerMultiply = bigIntegerMultiply2;
                i2 = iCompareTo3;
                iCompareTo3 = iCompareTo4;
            }
            iCompareTo = i2;
        }
        switch (AnonymousClass1.f14689a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(iCompareTo == 0);
            case 2:
            case 3:
                return iLog2;
            case 4:
            case 5:
                return bigIntegerPow.equals(bigInteger) ? iLog2 : iLog2 + 1;
            case 6:
            case 7:
            case 8:
                return bigInteger.pow(2).compareTo(bigIntegerPow.pow(2).multiply(BigInteger.TEN)) <= 0 ? iLog2 : iLog2 + 1;
            default:
                throw new AssertionError();
        }
    }

    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.j("x", (BigInteger) Preconditions.checkNotNull(bigInteger));
        int iBitLength = bigInteger.bitLength();
        int i2 = iBitLength - 1;
        switch (AnonymousClass1.f14689a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(isPowerOfTwo(bigInteger));
            case 2:
            case 3:
                return i2;
            case 4:
            case 5:
                return isPowerOfTwo(bigInteger) ? i2 : iBitLength;
            case 6:
            case 7:
            case 8:
                return i2 < 256 ? bigInteger.compareTo(f14688a.shiftRight(256 - i2)) <= 0 ? i2 : iBitLength : bigInteger.pow(2).bitLength() + (-1) < (i2 * 2) + 1 ? i2 : iBitLength;
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    public static double roundToDouble(BigInteger bigInteger, RoundingMode roundingMode) {
        return BigIntegerToDoubleRounder.f14690a.b(bigInteger, roundingMode);
    }

    @GwtIncompatible
    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.g("x", bigInteger);
        if (a(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger bigIntegerSqrtFloor = sqrtFloor(bigInteger);
        switch (AnonymousClass1.f14689a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(bigIntegerSqrtFloor.pow(2).equals(bigInteger));
            case 2:
            case 3:
                return bigIntegerSqrtFloor;
            case 4:
            case 5:
                int iIntValue = bigIntegerSqrtFloor.intValue();
                return (iIntValue * iIntValue == bigInteger.intValue() && bigIntegerSqrtFloor.pow(2).equals(bigInteger)) ? bigIntegerSqrtFloor : bigIntegerSqrtFloor.add(BigInteger.ONE);
            case 6:
            case 7:
            case 8:
                return bigIntegerSqrtFloor.pow(2).add(bigIntegerSqrtFloor).compareTo(bigInteger) >= 0 ? bigIntegerSqrtFloor : bigIntegerSqrtFloor.add(BigInteger.ONE);
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    private static BigInteger sqrtApproxWithDoubles(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.a(bigInteger)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    private static BigInteger sqrtFloor(BigInteger bigInteger) {
        BigInteger bigIntegerShiftLeft;
        int iLog2 = log2(bigInteger, RoundingMode.FLOOR);
        if (iLog2 < 1023) {
            bigIntegerShiftLeft = sqrtApproxWithDoubles(bigInteger);
        } else {
            int i2 = (iLog2 - 52) & (-2);
            bigIntegerShiftLeft = sqrtApproxWithDoubles(bigInteger.shiftRight(i2)).shiftLeft(i2 >> 1);
        }
        BigInteger bigIntegerShiftRight = bigIntegerShiftLeft.add(bigInteger.divide(bigIntegerShiftLeft)).shiftRight(1);
        if (bigIntegerShiftLeft.equals(bigIntegerShiftRight)) {
            return bigIntegerShiftLeft;
        }
        while (true) {
            BigInteger bigIntegerShiftRight2 = bigIntegerShiftRight.add(bigInteger.divide(bigIntegerShiftRight)).shiftRight(1);
            if (bigIntegerShiftRight2.compareTo(bigIntegerShiftRight) >= 0) {
                return bigIntegerShiftRight;
            }
            bigIntegerShiftRight = bigIntegerShiftRight2;
        }
    }
}
