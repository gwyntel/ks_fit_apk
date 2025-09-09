package com.google.common.math;

import androidx.media3.exoplayer.analytics.AnalyticsListener;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
final class DoubleUtils {
    private DoubleUtils() {
    }

    static double a(BigInteger bigInteger) {
        BigInteger bigIntegerAbs = bigInteger.abs();
        int iBitLength = bigIntegerAbs.bitLength();
        int i2 = iBitLength - 1;
        if (i2 < 63) {
            return bigInteger.longValue();
        }
        if (i2 > 1023) {
            return bigInteger.signum() * Double.POSITIVE_INFINITY;
        }
        int i3 = iBitLength - 54;
        long jLongValue = bigIntegerAbs.shiftRight(i3).longValue();
        long j2 = jLongValue >> 1;
        long j3 = 4503599627370495L & j2;
        if ((jLongValue & 1) != 0 && ((j2 & 1) != 0 || bigIntegerAbs.getLowestSetBit() < i3)) {
            j3++;
        }
        return Double.longBitsToDouble((((iBitLength + AnalyticsListener.EVENT_DRM_SESSION_ACQUIRED) << 52) + j3) | (bigInteger.signum() & Long.MIN_VALUE));
    }

    static double b(double d2) {
        Preconditions.checkArgument(!Double.isNaN(d2));
        return Math.max(d2, 0.0d);
    }

    static long c(double d2) {
        Preconditions.checkArgument(d(d2), "not a normal value");
        int exponent = Math.getExponent(d2);
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d2) & 4503599627370495L;
        return exponent == -1023 ? jDoubleToRawLongBits << 1 : jDoubleToRawLongBits | 4503599627370496L;
    }

    static boolean d(double d2) {
        return Math.getExponent(d2) <= 1023;
    }

    static boolean e(double d2) {
        return Math.getExponent(d2) >= -1022;
    }

    static double f(double d2) {
        return -Math.nextUp(-d2);
    }

    static double g(double d2) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(d2) & 4503599627370495L) | 4607182418800017408L);
    }
}
