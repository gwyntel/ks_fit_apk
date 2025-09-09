package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class DoubleMath {
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9d;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18d;
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9d;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18d;
    private static final double LN_2 = Math.log(2.0d);

    /* renamed from: a, reason: collision with root package name */
    static final double[] f14691a = {1.0d, 2.0922789888E13d, 2.631308369336935E35d, 1.2413915592536073E61d, 1.2688693218588417E89d, 7.156945704626381E118d, 9.916779348709496E149d, 1.974506857221074E182d, 3.856204823625804E215d, 5.5502938327393044E249d, 4.7147236359920616E284d};

    /* renamed from: com.google.common.math.DoubleMath$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14692a;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            f14692a = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14692a[RoundingMode.FLOOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f14692a[RoundingMode.CEILING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f14692a[RoundingMode.DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f14692a[RoundingMode.UP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f14692a[RoundingMode.HALF_EVEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f14692a[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f14692a[RoundingMode.HALF_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private DoubleMath() {
    }

    static double a(double d2, RoundingMode roundingMode) {
        if (!DoubleUtils.d(d2)) {
            throw new ArithmeticException("input is infinite or NaN");
        }
        switch (AnonymousClass1.f14692a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.k(isMathematicalInteger(d2));
                return d2;
            case 2:
                return (d2 >= 0.0d || isMathematicalInteger(d2)) ? d2 : ((long) d2) - 1;
            case 3:
                return (d2 <= 0.0d || isMathematicalInteger(d2)) ? d2 : ((long) d2) + 1;
            case 4:
                return d2;
            case 5:
                if (isMathematicalInteger(d2)) {
                    return d2;
                }
                return ((long) d2) + (d2 > 0.0d ? 1 : -1);
            case 6:
                return Math.rint(d2);
            case 7:
                double dRint = Math.rint(d2);
                return Math.abs(d2 - dRint) == 0.5d ? d2 + Math.copySign(0.5d, d2) : dRint;
            case 8:
                double dRint2 = Math.rint(d2);
                return Math.abs(d2 - dRint2) == 0.5d ? d2 : dRint2;
            default:
                throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    private static double checkFinite(double d2) {
        Preconditions.checkArgument(DoubleUtils.d(d2));
        return d2;
    }

    public static double factorial(int i2) {
        MathPreconditions.e("n", i2);
        if (i2 > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double d2 = 1.0d;
        for (int i3 = (i2 & (-16)) + 1; i3 <= i2; i3++) {
            d2 *= i3;
        }
        return d2 * f14691a[i2 >> 4];
    }

    public static int fuzzyCompare(double d2, double d3, double d4) {
        if (fuzzyEquals(d2, d3, d4)) {
            return 0;
        }
        if (d2 < d3) {
            return -1;
        }
        if (d2 > d3) {
            return 1;
        }
        return Boolean.compare(Double.isNaN(d2), Double.isNaN(d3));
    }

    public static boolean fuzzyEquals(double d2, double d3, double d4) {
        MathPreconditions.d("tolerance", d4);
        return Math.copySign(d2 - d3, 1.0d) <= d4 || d2 == d3 || (Double.isNaN(d2) && Double.isNaN(d3));
    }

    @GwtIncompatible
    public static boolean isMathematicalInteger(double d2) {
        return DoubleUtils.d(d2) && (d2 == 0.0d || 52 - Long.numberOfTrailingZeros(DoubleUtils.c(d2)) <= Math.getExponent(d2));
    }

    @GwtIncompatible
    public static boolean isPowerOfTwo(double d2) {
        if (d2 <= 0.0d || !DoubleUtils.d(d2)) {
            return false;
        }
        long jC = DoubleUtils.c(d2);
        return (jC & (jC - 1)) == 0;
    }

    public static double log2(double d2) {
        return Math.log(d2) / LN_2;
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(double... dArr) {
        Preconditions.checkArgument(dArr.length > 0, "Cannot take mean of 0 values");
        double dCheckFinite = checkFinite(dArr[0]);
        long j2 = 1;
        for (int i2 = 1; i2 < dArr.length; i2++) {
            checkFinite(dArr[i2]);
            j2++;
            dCheckFinite += (dArr[i2] - dCheckFinite) / j2;
        }
        return dCheckFinite;
    }

    @GwtIncompatible
    public static BigInteger roundToBigInteger(double d2, RoundingMode roundingMode) {
        double dA = a(d2, roundingMode);
        if ((MIN_LONG_AS_DOUBLE - dA < 1.0d) && (dA < MAX_LONG_AS_DOUBLE_PLUS_ONE)) {
            return BigInteger.valueOf((long) dA);
        }
        BigInteger bigIntegerShiftLeft = BigInteger.valueOf(DoubleUtils.c(dA)).shiftLeft(Math.getExponent(dA) - 52);
        return dA < 0.0d ? bigIntegerShiftLeft.negate() : bigIntegerShiftLeft;
    }

    @GwtIncompatible
    public static int roundToInt(double d2, RoundingMode roundingMode) {
        double dA = a(d2, roundingMode);
        MathPreconditions.a((dA > -2.147483649E9d) & (dA < 2.147483648E9d), d2, roundingMode);
        return (int) dA;
    }

    @GwtIncompatible
    public static long roundToLong(double d2, RoundingMode roundingMode) {
        double dA = a(d2, roundingMode);
        MathPreconditions.a((MIN_LONG_AS_DOUBLE - dA < 1.0d) & (dA < MAX_LONG_AS_DOUBLE_PLUS_ONE), d2, roundingMode);
        return (long) dA;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    @com.google.common.annotations.GwtIncompatible
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int log2(double r5, java.math.RoundingMode r7) {
        /*
            r0 = 0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L10
            boolean r0 = com.google.common.math.DoubleUtils.d(r5)
            if (r0 == 0) goto L10
            r0 = r2
            goto L11
        L10:
            r0 = r1
        L11:
            java.lang.String r3 = "x must be positive and finite"
            com.google.common.base.Preconditions.checkArgument(r0, r3)
            int r0 = java.lang.Math.getExponent(r5)
            boolean r3 = com.google.common.math.DoubleUtils.e(r5)
            if (r3 != 0) goto L2a
            r0 = 4841369599423283200(0x4330000000000000, double:4.503599627370496E15)
            double r5 = r5 * r0
            int r5 = log2(r5, r7)
            int r5 = r5 + (-52)
            return r5
        L2a:
            int[] r3 = com.google.common.math.DoubleMath.AnonymousClass1.f14692a
            int r7 = r7.ordinal()
            r7 = r3[r7]
            switch(r7) {
                case 1: goto L61;
                case 2: goto L68;
                case 3: goto L5a;
                case 4: goto L52;
                case 5: goto L48;
                case 6: goto L3b;
                case 7: goto L3b;
                case 8: goto L3b;
                default: goto L35;
            }
        L35:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            r5.<init>()
            throw r5
        L3b:
            double r5 = com.google.common.math.DoubleUtils.g(r5)
            double r5 = r5 * r5
            r3 = 4611686018427387904(0x4000000000000000, double:2.0)
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 <= 0) goto L68
            r1 = r2
            goto L68
        L48:
            if (r0 < 0) goto L4b
            r1 = r2
        L4b:
            boolean r5 = isPowerOfTwo(r5)
        L4f:
            r5 = r5 ^ r2
            r1 = r1 & r5
            goto L68
        L52:
            if (r0 >= 0) goto L55
            r1 = r2
        L55:
            boolean r5 = isPowerOfTwo(r5)
            goto L4f
        L5a:
            boolean r5 = isPowerOfTwo(r5)
            r1 = r5 ^ 1
            goto L68
        L61:
            boolean r5 = isPowerOfTwo(r5)
            com.google.common.math.MathPreconditions.k(r5)
        L68:
            if (r1 == 0) goto L6c
            int r0 = r0 + 1
        L6c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.DoubleMath.log2(double, java.math.RoundingMode):int");
    }

    @Deprecated
    public static double mean(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0, "Cannot take mean of 0 values");
        long j2 = 0;
        for (int i2 : iArr) {
            j2 += i2;
        }
        return j2 / iArr.length;
    }

    @Deprecated
    public static double mean(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0, "Cannot take mean of 0 values");
        double d2 = jArr[0];
        long j2 = 1;
        for (int i2 = 1; i2 < jArr.length; i2++) {
            j2++;
            d2 += (jArr[i2] - d2) / j2;
        }
        return d2;
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterable<? extends Number> iterable) {
        return mean(iterable.iterator());
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterator<? extends Number> it) {
        Preconditions.checkArgument(it.hasNext(), "Cannot take mean of 0 values");
        double dCheckFinite = checkFinite(it.next().doubleValue());
        long j2 = 1;
        while (it.hasNext()) {
            j2++;
            dCheckFinite += (checkFinite(it.next().doubleValue()) - dCheckFinite) / j2;
        }
        return dCheckFinite;
    }
}
