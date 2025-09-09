package com.google.common.math;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.luck.picture.lib.config.FileSizeUnit;
import java.math.RoundingMode;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class IntMath {

    /* renamed from: a, reason: collision with root package name */
    static final byte[] f14693a = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};

    /* renamed from: b, reason: collision with root package name */
    static final int[] f14694b = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, FileSizeUnit.ACCURATE_GB};

    /* renamed from: c, reason: collision with root package name */
    static final int[] f14695c = {3, 31, TypedValues.AttributesType.TYPE_PATH_ROTATE, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    private static final int[] factorials = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};

    /* renamed from: d, reason: collision with root package name */
    static int[] f14696d = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    /* renamed from: com.google.common.math.IntMath$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14697a;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            f14697a = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14697a[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f14697a[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f14697a[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f14697a[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f14697a[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f14697a[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f14697a[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private IntMath() {
    }

    static int a(int i2, int i3) {
        return (~(~(i2 - i3))) >>> 31;
    }

    public static int binomial(int i2, int i3) {
        MathPreconditions.e("n", i2);
        MathPreconditions.e("k", i3);
        int i4 = 0;
        Preconditions.checkArgument(i3 <= i2, "k (%s) > n (%s)", i3, i2);
        if (i3 > (i2 >> 1)) {
            i3 = i2 - i3;
        }
        int[] iArr = f14696d;
        if (i3 >= iArr.length || i2 > iArr[i3]) {
            return Integer.MAX_VALUE;
        }
        if (i3 == 0) {
            return 1;
        }
        if (i3 == 1) {
            return i2;
        }
        long j2 = 1;
        while (i4 < i3) {
            long j3 = j2 * (i2 - i4);
            i4++;
            j2 = j3 / i4;
        }
        return (int) j2;
    }

    public static int ceilingPowerOfTwo(int i2) {
        MathPreconditions.h("x", i2);
        if (i2 <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i2 - 1));
        }
        throw new ArithmeticException("ceilingPowerOfTwo(" + i2 + ") not representable as an int");
    }

    public static int checkedAdd(int i2, int i3) {
        long j2 = i2 + i3;
        int i4 = (int) j2;
        MathPreconditions.b(j2 == ((long) i4), "checkedAdd", i2, i3);
        return i4;
    }

    public static int checkedMultiply(int i2, int i3) {
        long j2 = i2 * i3;
        int i4 = (int) j2;
        MathPreconditions.b(j2 == ((long) i4), "checkedMultiply", i2, i3);
        return i4;
    }

    public static int checkedPow(int i2, int i3) {
        MathPreconditions.e("exponent", i3);
        if (i2 == -2) {
            MathPreconditions.b(i3 < 32, "checkedPow", i2, i3);
            return (i3 & 1) == 0 ? 1 << i3 : (-1) << i3;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            MathPreconditions.b(i3 < 31, "checkedPow", i2, i3);
            return 1 << i3;
        }
        int iCheckedMultiply = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return checkedMultiply(iCheckedMultiply, i2);
            }
            if ((i3 & 1) != 0) {
                iCheckedMultiply = checkedMultiply(iCheckedMultiply, i2);
            }
            i3 >>= 1;
            if (i3 > 0) {
                MathPreconditions.b((-46340 <= i2) & (i2 <= 46340), "checkedPow", i2, i3);
                i2 *= i2;
            }
        }
        return iCheckedMultiply;
    }

    public static int checkedSubtract(int i2, int i3) {
        long j2 = i2 - i3;
        int i4 = (int) j2;
        MathPreconditions.b(j2 == ((long) i4), "checkedSubtract", i2, i3);
        return i4;
    }

    public static int divide(int i2, int i3, RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        if (i3 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int i4 = i2 / i3;
        int i5 = i2 - (i3 * i4);
        if (i5 == 0) {
            return i4;
        }
        int i6 = ((i2 ^ i3) >> 31) | 1;
        switch (AnonymousClass1.f14697a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(i5 == 0);
                return i4;
            case 2:
                return i4;
            case 3:
                if (i6 >= 0) {
                    return i4;
                }
                break;
            case 4:
                break;
            case 5:
                if (i6 <= 0) {
                    return i4;
                }
                break;
            case 6:
            case 7:
            case 8:
                int iAbs = Math.abs(i5);
                int iAbs2 = iAbs - (Math.abs(i3) - iAbs);
                if (iAbs2 == 0) {
                    if (roundingMode != RoundingMode.HALF_UP) {
                        if (!((roundingMode == RoundingMode.HALF_EVEN) & ((i4 & 1) != 0))) {
                            return i4;
                        }
                    }
                } else if (iAbs2 <= 0) {
                    return i4;
                }
                break;
            default:
                throw new AssertionError();
        }
        return i4 + i6;
    }

    public static int factorial(int i2) {
        MathPreconditions.e("n", i2);
        int[] iArr = factorials;
        if (i2 < iArr.length) {
            return iArr[i2];
        }
        return Integer.MAX_VALUE;
    }

    public static int floorPowerOfTwo(int i2) {
        MathPreconditions.h("x", i2);
        return Integer.highestOneBit(i2);
    }

    public static int gcd(int i2, int i3) {
        MathPreconditions.e("a", i2);
        MathPreconditions.e("b", i3);
        if (i2 == 0) {
            return i3;
        }
        if (i3 == 0) {
            return i2;
        }
        int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
        int iNumberOfTrailingZeros2 = i2 >> iNumberOfTrailingZeros;
        int iNumberOfTrailingZeros3 = Integer.numberOfTrailingZeros(i3);
        int i4 = i3 >> iNumberOfTrailingZeros3;
        while (iNumberOfTrailingZeros2 != i4) {
            int i5 = iNumberOfTrailingZeros2 - i4;
            int i6 = (i5 >> 31) & i5;
            int i7 = (i5 - i6) - i6;
            i4 += i6;
            iNumberOfTrailingZeros2 = i7 >> Integer.numberOfTrailingZeros(i7);
        }
        return iNumberOfTrailingZeros2 << Math.min(iNumberOfTrailingZeros, iNumberOfTrailingZeros3);
    }

    public static boolean isPowerOfTwo(int i2) {
        return (i2 > 0) & ((i2 & (i2 + (-1))) == 0);
    }

    @GwtIncompatible
    public static boolean isPrime(int i2) {
        return LongMath.isPrime(i2);
    }

    @GwtIncompatible
    public static int log10(int i2, RoundingMode roundingMode) {
        int iA;
        MathPreconditions.h("x", i2);
        int iLog10Floor = log10Floor(i2);
        int i3 = f14694b[iLog10Floor];
        switch (AnonymousClass1.f14697a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(i2 == i3);
            case 2:
            case 3:
                return iLog10Floor;
            case 4:
            case 5:
                iA = a(i3, i2);
                return iLog10Floor + iA;
            case 6:
            case 7:
            case 8:
                iA = a(f14695c[iLog10Floor], i2);
                return iLog10Floor + iA;
            default:
                throw new AssertionError();
        }
    }

    private static int log10Floor(int i2) {
        byte b2 = f14693a[Integer.numberOfLeadingZeros(i2)];
        return b2 - a(i2, f14694b[b2]);
    }

    public static int log2(int i2, RoundingMode roundingMode) {
        MathPreconditions.h("x", i2);
        switch (AnonymousClass1.f14697a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(isPowerOfTwo(i2));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i2 - 1);
            case 6:
            case 7:
            case 8:
                int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i2);
                return (31 - iNumberOfLeadingZeros) + a((-1257966797) >>> iNumberOfLeadingZeros, i2);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i2);
    }

    public static int mean(int i2, int i3) {
        return (i2 & i3) + ((i2 ^ i3) >> 1);
    }

    public static int mod(int i2, int i3) {
        if (i3 > 0) {
            int i4 = i2 % i3;
            return i4 >= 0 ? i4 : i4 + i3;
        }
        throw new ArithmeticException("Modulus " + i3 + " must be > 0");
    }

    @GwtIncompatible
    public static int pow(int i2, int i3) {
        MathPreconditions.e("exponent", i3);
        if (i2 == -2) {
            if (i3 < 32) {
                return (i3 & 1) == 0 ? 1 << i3 : -(1 << i3);
            }
            return 0;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            if (i3 < 32) {
                return 1 << i3;
            }
            return 0;
        }
        int i4 = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return i2 * i4;
            }
            i4 *= (i3 & 1) == 0 ? 1 : i2;
            i2 *= i2;
            i3 >>= 1;
        }
        return i4;
    }

    public static int saturatedAdd(int i2, int i3) {
        return Ints.saturatedCast(i2 + i3);
    }

    public static int saturatedMultiply(int i2, int i3) {
        return Ints.saturatedCast(i2 * i3);
    }

    public static int saturatedPow(int i2, int i3) {
        MathPreconditions.e("exponent", i3);
        if (i2 == -2) {
            return i3 >= 32 ? (i3 & 1) + Integer.MAX_VALUE : (i3 & 1) == 0 ? 1 << i3 : (-1) << i3;
        }
        if (i2 == -1) {
            return (i3 & 1) == 0 ? 1 : -1;
        }
        if (i2 == 0) {
            return i3 == 0 ? 1 : 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            if (i3 >= 31) {
                return Integer.MAX_VALUE;
            }
            return 1 << i3;
        }
        int i4 = ((i2 >>> 31) & i3 & 1) + Integer.MAX_VALUE;
        int iSaturatedMultiply = 1;
        while (i3 != 0) {
            if (i3 == 1) {
                return saturatedMultiply(iSaturatedMultiply, i2);
            }
            if ((i3 & 1) != 0) {
                iSaturatedMultiply = saturatedMultiply(iSaturatedMultiply, i2);
            }
            i3 >>= 1;
            if (i3 > 0) {
                if ((-46340 > i2) || (i2 > 46340)) {
                    return i4;
                }
                i2 *= i2;
            }
        }
        return iSaturatedMultiply;
    }

    public static int saturatedSubtract(int i2, int i3) {
        return Ints.saturatedCast(i2 - i3);
    }

    @GwtIncompatible
    public static int sqrt(int i2, RoundingMode roundingMode) {
        int iA;
        MathPreconditions.e("x", i2);
        int iSqrtFloor = sqrtFloor(i2);
        switch (AnonymousClass1.f14697a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(iSqrtFloor * iSqrtFloor == i2);
            case 2:
            case 3:
                return iSqrtFloor;
            case 4:
            case 5:
                iA = a(iSqrtFloor * iSqrtFloor, i2);
                return iSqrtFloor + iA;
            case 6:
            case 7:
            case 8:
                iA = a((iSqrtFloor * iSqrtFloor) + iSqrtFloor, i2);
                return iSqrtFloor + iA;
            default:
                throw new AssertionError();
        }
    }

    private static int sqrtFloor(int i2) {
        return (int) Math.sqrt(i2);
    }
}
