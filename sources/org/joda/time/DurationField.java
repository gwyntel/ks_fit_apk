package org.joda.time;

/* loaded from: classes5.dex */
public abstract class DurationField implements Comparable<DurationField> {
    public abstract long add(long j2, int i2);

    public abstract long add(long j2, long j3);

    public abstract int getDifference(long j2, long j3);

    public abstract long getDifferenceAsLong(long j2, long j3);

    public abstract long getMillis(int i2);

    public abstract long getMillis(int i2, long j2);

    public abstract long getMillis(long j2);

    public abstract long getMillis(long j2, long j3);

    public abstract String getName();

    public abstract DurationFieldType getType();

    public abstract long getUnitMillis();

    public abstract int getValue(long j2);

    public abstract int getValue(long j2, long j3);

    public abstract long getValueAsLong(long j2);

    public abstract long getValueAsLong(long j2, long j3);

    public abstract boolean isPrecise();

    public abstract boolean isSupported();

    public long subtract(long j2, int i2) {
        return i2 == Integer.MIN_VALUE ? subtract(j2, i2) : add(j2, -i2);
    }

    public abstract String toString();

    public long subtract(long j2, long j3) {
        if (j3 != Long.MIN_VALUE) {
            return add(j2, -j3);
        }
        throw new ArithmeticException("Long.MIN_VALUE cannot be negated");
    }
}
