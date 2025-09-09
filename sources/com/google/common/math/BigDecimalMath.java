package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public class BigDecimalMath {

    private static class BigDecimalToDoubleRounder extends ToDoubleRounder<BigDecimal> {

        /* renamed from: a, reason: collision with root package name */
        static final BigDecimalToDoubleRounder f14687a = new BigDecimalToDoubleRounder();

        private BigDecimalToDoubleRounder() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: f, reason: merged with bridge method [inline-methods] */
        public BigDecimal a(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
            return bigDecimal.subtract(bigDecimal2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public double c(BigDecimal bigDecimal) {
            return bigDecimal.doubleValue();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: h, reason: merged with bridge method [inline-methods] */
        public int d(BigDecimal bigDecimal) {
            return bigDecimal.signum();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        /* renamed from: i, reason: merged with bridge method [inline-methods] */
        public BigDecimal e(double d2, RoundingMode roundingMode) {
            return new BigDecimal(d2);
        }
    }

    private BigDecimalMath() {
    }

    public static double roundToDouble(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return BigDecimalToDoubleRounder.f14687a.b(bigDecimal, roundingMode);
    }
}
