package org.joda.time;

/* loaded from: classes5.dex */
public interface ReadWritableInstant extends ReadableInstant {
    void add(long j2);

    void add(DurationFieldType durationFieldType, int i2);

    void add(ReadableDuration readableDuration);

    void add(ReadableDuration readableDuration, int i2);

    void add(ReadablePeriod readablePeriod);

    void add(ReadablePeriod readablePeriod, int i2);

    void set(DateTimeFieldType dateTimeFieldType, int i2);

    void setChronology(Chronology chronology);

    void setMillis(long j2);

    void setMillis(ReadableInstant readableInstant);

    void setZone(DateTimeZone dateTimeZone);

    void setZoneRetainFields(DateTimeZone dateTimeZone);
}
