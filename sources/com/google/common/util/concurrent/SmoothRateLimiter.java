package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.math.LongMath;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
abstract class SmoothRateLimiter extends RateLimiter {

    /* renamed from: a, reason: collision with root package name */
    double f14926a;

    /* renamed from: b, reason: collision with root package name */
    double f14927b;

    /* renamed from: c, reason: collision with root package name */
    double f14928c;
    private long nextFreeTicketMicros;

    static final class SmoothBursty extends SmoothRateLimiter {

        /* renamed from: d, reason: collision with root package name */
        final double f14929d;

        SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch, double d2) {
            super(sleepingStopwatch);
            this.f14929d = d2;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        double i() {
            return this.f14928c;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        void j(double d2, double d3) {
            double d4 = this.f14927b;
            double d5 = this.f14929d * d2;
            this.f14927b = d5;
            if (d4 == Double.POSITIVE_INFINITY) {
                this.f14926a = d5;
            } else {
                this.f14926a = d4 != 0.0d ? (this.f14926a * d5) / d4 : 0.0d;
            }
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        long l(double d2, double d3) {
            return 0L;
        }
    }

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long j2, TimeUnit timeUnit, double d2) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(j2);
            this.coldFactor = d2;
        }

        private double permitsToTime(double d2) {
            return this.f14928c + (d2 * this.slope);
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        double i() {
            return this.warmupPeriodMicros / this.f14927b;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        void j(double d2, double d3) {
            double d4 = this.f14927b;
            double d5 = this.coldFactor * d3;
            long j2 = this.warmupPeriodMicros;
            double d6 = (j2 * 0.5d) / d3;
            this.thresholdPermits = d6;
            double d7 = ((j2 * 2.0d) / (d3 + d5)) + d6;
            this.f14927b = d7;
            this.slope = (d5 - d3) / (d7 - d6);
            if (d4 == Double.POSITIVE_INFINITY) {
                this.f14926a = 0.0d;
                return;
            }
            if (d4 != 0.0d) {
                d7 = (this.f14926a * d7) / d4;
            }
            this.f14926a = d7;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        long l(double d2, double d3) {
            long jPermitsToTime;
            double d4 = d2 - this.thresholdPermits;
            if (d4 > 0.0d) {
                double dMin = Math.min(d4, d3);
                jPermitsToTime = (long) (((permitsToTime(d4) + permitsToTime(d4 - dMin)) * dMin) / 2.0d);
                d3 -= dMin;
            } else {
                jPermitsToTime = 0;
            }
            return jPermitsToTime + ((long) (this.f14928c * d3));
        }
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final double c() {
        return TimeUnit.SECONDS.toMicros(1L) / this.f14928c;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final void d(double d2, long j2) {
        k(j2);
        double micros = TimeUnit.SECONDS.toMicros(1L) / d2;
        this.f14928c = micros;
        j(d2, micros);
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long e(long j2) {
        return this.nextFreeTicketMicros;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long h(int i2, long j2) {
        k(j2);
        long j3 = this.nextFreeTicketMicros;
        double d2 = i2;
        double dMin = Math.min(d2, this.f14926a);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, l(this.f14926a, dMin) + ((long) ((d2 - dMin) * this.f14928c)));
        this.f14926a -= dMin;
        return j3;
    }

    abstract double i();

    abstract void j(double d2, double d3);

    void k(long j2) {
        if (j2 > this.nextFreeTicketMicros) {
            this.f14926a = Math.min(this.f14927b, this.f14926a + ((j2 - r0) / i()));
            this.nextFreeTicketMicros = j2;
        }
    }

    abstract long l(double d2, double d3);

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.nextFreeTicketMicros = 0L;
    }
}
