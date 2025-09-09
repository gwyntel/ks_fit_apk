package org.joda.time;

/* loaded from: classes5.dex */
public interface ReadWritablePeriod extends ReadablePeriod {
    void add(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    void add(DurationFieldType durationFieldType, int i2);

    void add(ReadableInterval readableInterval);

    void add(ReadablePeriod readablePeriod);

    void addDays(int i2);

    void addHours(int i2);

    void addMillis(int i2);

    void addMinutes(int i2);

    void addMonths(int i2);

    void addSeconds(int i2);

    void addWeeks(int i2);

    void addYears(int i2);

    void clear();

    void set(DurationFieldType durationFieldType, int i2);

    void setDays(int i2);

    void setHours(int i2);

    void setMillis(int i2);

    void setMinutes(int i2);

    void setMonths(int i2);

    void setPeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    void setPeriod(ReadableInterval readableInterval);

    void setPeriod(ReadablePeriod readablePeriod);

    void setSeconds(int i2);

    void setValue(int i2, int i3);

    void setWeeks(int i2);

    void setYears(int i2);
}
