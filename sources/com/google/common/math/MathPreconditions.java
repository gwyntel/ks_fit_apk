package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import java.math.BigInteger;
import java.math.RoundingMode;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
final class MathPreconditions {
    private MathPreconditions() {
    }

    static void a(boolean z2, double d2, RoundingMode roundingMode) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("rounded value is out of range for input " + d2 + " and rounding mode " + roundingMode);
    }

    static void b(boolean z2, String str, int i2, int i3) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("overflow: " + str + "(" + i2 + ", " + i3 + ")");
    }

    static void c(boolean z2, String str, long j2, long j3) {
        if (z2) {
            return;
        }
        throw new ArithmeticException("overflow: " + str + "(" + j2 + ", " + j3 + ")");
    }

    static double d(String str, double d2) {
        if (d2 >= 0.0d) {
            return d2;
        }
        throw new IllegalArgumentException(str + " (" + d2 + ") must be >= 0");
    }

    static int e(String str, int i2) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " (" + i2 + ") must be >= 0");
    }

    static long f(String str, long j2) {
        if (j2 >= 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " (" + j2 + ") must be >= 0");
    }

    static BigInteger g(String str, BigInteger bigInteger) {
        if (bigInteger.signum() >= 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be >= 0");
    }

    static int h(String str, int i2) {
        if (i2 > 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " (" + i2 + ") must be > 0");
    }

    static long i(String str, long j2) {
        if (j2 > 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " (" + j2 + ") must be > 0");
    }

    static BigInteger j(String str, BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be > 0");
    }

    static void k(boolean z2) {
        if (!z2) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }
}
