package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.lang.Comparable;
import java.lang.Number;
import java.math.RoundingMode;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
abstract class ToDoubleRounder<X extends Number & Comparable<X>> {

    /* renamed from: com.google.common.math.ToDoubleRounder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14711a;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            f14711a = iArr;
            try {
                iArr[RoundingMode.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14711a[RoundingMode.HALF_EVEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f14711a[RoundingMode.HALF_DOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f14711a[RoundingMode.HALF_UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f14711a[RoundingMode.FLOOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f14711a[RoundingMode.CEILING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f14711a[RoundingMode.UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f14711a[RoundingMode.UNNECESSARY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    ToDoubleRounder() {
    }

    abstract Number a(Number number, Number number2);

    /* JADX WARN: Multi-variable type inference failed */
    final double b(Number number, RoundingMode roundingMode) {
        Number numberE;
        double dNextUp;
        Preconditions.checkNotNull(number, "x");
        Preconditions.checkNotNull(roundingMode, "mode");
        double dC = c(number);
        if (Double.isInfinite(dC)) {
            switch (AnonymousClass1.f14711a[roundingMode.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return d(number) * Double.MAX_VALUE;
                case 5:
                    return dC == Double.POSITIVE_INFINITY ? Double.MAX_VALUE : Double.NEGATIVE_INFINITY;
                case 6:
                    return dC == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : -1.7976931348623157E308d;
                case 7:
                    return dC;
                case 8:
                    throw new ArithmeticException(number + " cannot be represented precisely as a double");
            }
        }
        Number numberE2 = e(dC, RoundingMode.UNNECESSARY);
        int iCompareTo = ((Comparable) number).compareTo(numberE2);
        int[] iArr = AnonymousClass1.f14711a;
        switch (iArr[roundingMode.ordinal()]) {
            case 1:
                return d(number) >= 0 ? iCompareTo >= 0 ? dC : DoubleUtils.f(dC) : iCompareTo <= 0 ? dC : Math.nextUp(dC);
            case 2:
            case 3:
            case 4:
                if (iCompareTo >= 0) {
                    dNextUp = Math.nextUp(dC);
                    if (dNextUp == Double.POSITIVE_INFINITY) {
                        return dC;
                    }
                    numberE = e(dNextUp, RoundingMode.CEILING);
                } else {
                    double dF = DoubleUtils.f(dC);
                    if (dF == Double.NEGATIVE_INFINITY) {
                        return dC;
                    }
                    Number numberE3 = e(dF, RoundingMode.FLOOR);
                    numberE = numberE2;
                    numberE2 = numberE3;
                    dNextUp = dC;
                    dC = dF;
                }
                int iCompareTo2 = ((Comparable) a(number, numberE2)).compareTo(a(numberE, number));
                if (iCompareTo2 < 0) {
                    return dC;
                }
                if (iCompareTo2 > 0) {
                    return dNextUp;
                }
                int i2 = iArr[roundingMode.ordinal()];
                if (i2 == 2) {
                    return (Double.doubleToRawLongBits(dC) & 1) == 0 ? dC : dNextUp;
                }
                if (i2 == 3) {
                    return d(number) >= 0 ? dC : dNextUp;
                }
                if (i2 == 4) {
                    return d(number) >= 0 ? dNextUp : dC;
                }
                throw new AssertionError("impossible");
            case 5:
                return iCompareTo >= 0 ? dC : DoubleUtils.f(dC);
            case 6:
                return iCompareTo <= 0 ? dC : Math.nextUp(dC);
            case 7:
                return d(number) >= 0 ? iCompareTo <= 0 ? dC : Math.nextUp(dC) : iCompareTo >= 0 ? dC : DoubleUtils.f(dC);
            case 8:
                MathPreconditions.k(iCompareTo == 0);
                return dC;
            default:
                throw new AssertionError("impossible");
        }
    }

    abstract double c(Number number);

    abstract int d(Number number);

    abstract Number e(double d2, RoundingMode roundingMode);
}
