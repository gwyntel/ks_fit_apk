package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.SmoothRateLimiter;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

@Beta
@ElementTypesAreNonnullByDefault
@GwtIncompatible
@J2ktIncompatible
/* loaded from: classes3.dex */
public abstract class RateLimiter {

    @CheckForNull
    private volatile Object mutexDoNotUseDirectly;
    private final SleepingStopwatch stopwatch;

    static abstract class SleepingStopwatch {
        protected SleepingStopwatch() {
        }

        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() { // from class: com.google.common.util.concurrent.RateLimiter.SleepingStopwatch.1

                /* renamed from: a, reason: collision with root package name */
                final Stopwatch f14899a = Stopwatch.createStarted();

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                protected long a() {
                    return this.f14899a.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                protected void b(long j2) {
                    if (j2 > 0) {
                        Uninterruptibles.sleepUninterruptibly(j2, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }

        protected abstract long a();

        protected abstract void b(long j2);
    }

    RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = (SleepingStopwatch) Preconditions.checkNotNull(sleepingStopwatch);
    }

    static RateLimiter a(double d2, long j2, TimeUnit timeUnit, double d3, SleepingStopwatch sleepingStopwatch) {
        SmoothRateLimiter.SmoothWarmingUp smoothWarmingUp = new SmoothRateLimiter.SmoothWarmingUp(sleepingStopwatch, j2, timeUnit, d3);
        smoothWarmingUp.setRate(d2);
        return smoothWarmingUp;
    }

    static RateLimiter b(double d2, SleepingStopwatch sleepingStopwatch) {
        SmoothRateLimiter.SmoothBursty smoothBursty = new SmoothRateLimiter.SmoothBursty(sleepingStopwatch, 1.0d);
        smoothBursty.setRate(d2);
        return smoothBursty;
    }

    private boolean canAcquire(long j2, long j3) {
        return e(j2) - j3 <= j2;
    }

    private static void checkPermits(int i2) {
        Preconditions.checkArgument(i2 > 0, "Requested permits (%s) must be positive", i2);
    }

    public static RateLimiter create(double d2) {
        return b(d2, SleepingStopwatch.createFromSystemTimer());
    }

    private Object mutex() {
        Object obj = this.mutexDoNotUseDirectly;
        if (obj == null) {
            synchronized (this) {
                try {
                    obj = this.mutexDoNotUseDirectly;
                    if (obj == null) {
                        obj = new Object();
                        this.mutexDoNotUseDirectly = obj;
                    }
                } finally {
                }
            }
        }
        return obj;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    abstract double c();

    abstract void d(double d2, long j2);

    abstract long e(long j2);

    final long f(int i2) {
        long jG;
        checkPermits(i2);
        synchronized (mutex()) {
            jG = g(i2, this.stopwatch.a());
        }
        return jG;
    }

    final long g(int i2, long j2) {
        return Math.max(h(i2, j2) - j2, 0L);
    }

    public final double getRate() {
        double dC;
        synchronized (mutex()) {
            dC = c();
        }
        return dC;
    }

    abstract long h(int i2, long j2);

    public final void setRate(double d2) {
        Preconditions.checkArgument(d2 > 0.0d, "rate must be positive");
        synchronized (mutex()) {
            d(d2, this.stopwatch.a());
        }
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", Double.valueOf(getRate()));
    }

    public boolean tryAcquire(long j2, TimeUnit timeUnit) {
        return tryAcquire(1, j2, timeUnit);
    }

    public static RateLimiter create(double d2, long j2, TimeUnit timeUnit) {
        Preconditions.checkArgument(j2 >= 0, "warmupPeriod must not be negative: %s", j2);
        return a(d2, j2, timeUnit, 3.0d, SleepingStopwatch.createFromSystemTimer());
    }

    @CanIgnoreReturnValue
    public double acquire(int i2) {
        long jF = f(i2);
        this.stopwatch.b(jF);
        return (jF * 1.0d) / TimeUnit.SECONDS.toMicros(1L);
    }

    public boolean tryAcquire(int i2) {
        return tryAcquire(i2, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int i2, long j2, TimeUnit timeUnit) {
        long jMax = Math.max(timeUnit.toMicros(j2), 0L);
        checkPermits(i2);
        synchronized (mutex()) {
            try {
                long jA = this.stopwatch.a();
                if (!canAcquire(jA, jMax)) {
                    return false;
                }
                this.stopwatch.b(g(i2, jA));
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
