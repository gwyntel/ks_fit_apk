package org.mozilla.javascript.v8dtoa;

/* loaded from: classes5.dex */
public final class DoubleConversion {
    private static final int kDenormalExponent = -1074;
    private static final int kExponentBias = 1075;
    private static final long kExponentMask = 9218868437227405312L;
    private static final long kHiddenBit = 4503599627370496L;
    private static final int kPhysicalSignificandSize = 52;
    private static final long kSignMask = Long.MIN_VALUE;
    private static final long kSignificandMask = 4503599627370495L;
    private static final int kSignificandSize = 53;

    private DoubleConversion() {
    }

    public static int doubleToInt32(double d2) {
        int i2 = (int) d2;
        if (i2 == d2) {
            return i2;
        }
        long jDoubleToLongBits = Double.doubleToLongBits(d2);
        int iExponent = exponent(jDoubleToLongBits);
        if (iExponent <= -53 || iExponent > 31) {
            return 0;
        }
        long jSignificand = significand(jDoubleToLongBits);
        return sign(jDoubleToLongBits) * ((int) (iExponent < 0 ? jSignificand >> (-iExponent) : jSignificand << iExponent));
    }

    private static int exponent(long j2) {
        return isDenormal(j2) ? kDenormalExponent : ((int) ((j2 & kExponentMask) >> 52)) - 1075;
    }

    private static boolean isDenormal(long j2) {
        return (j2 & kExponentMask) == 0;
    }

    private static int sign(long j2) {
        return (j2 & Long.MIN_VALUE) == 0 ? 1 : -1;
    }

    private static long significand(long j2) {
        long j3 = kSignificandMask & j2;
        return !isDenormal(j2) ? j3 + kHiddenBit : j3;
    }
}
