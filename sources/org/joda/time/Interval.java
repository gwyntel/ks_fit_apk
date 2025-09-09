package org.joda.time;

import java.io.Serializable;
import org.joda.time.base.BaseInterval;
import org.joda.time.chrono.ISOChronology;

/* loaded from: classes5.dex */
public final class Interval extends BaseInterval implements ReadableInterval, Serializable {
    private static final long serialVersionUID = 4922451897541386752L;

    public Interval(long j2, long j3) {
        super(j2, j3, null);
    }

    public static Interval parse(String str) {
        return new Interval(str);
    }

    public boolean abuts(ReadableInterval readableInterval) {
        if (readableInterval != null) {
            return readableInterval.getEndMillis() == getStartMillis() || getEndMillis() == readableInterval.getStartMillis();
        }
        long jCurrentTimeMillis = DateTimeUtils.currentTimeMillis();
        return getStartMillis() == jCurrentTimeMillis || getEndMillis() == jCurrentTimeMillis;
    }

    public Interval gap(ReadableInterval readableInterval) {
        ReadableInterval readableInterval2 = DateTimeUtils.getReadableInterval(readableInterval);
        long startMillis = readableInterval2.getStartMillis();
        long endMillis = readableInterval2.getEndMillis();
        long startMillis2 = getStartMillis();
        long endMillis2 = getEndMillis();
        if (startMillis2 > endMillis) {
            return new Interval(endMillis, startMillis2, getChronology());
        }
        if (startMillis > endMillis2) {
            return new Interval(endMillis2, startMillis, getChronology());
        }
        return null;
    }

    public Interval overlap(ReadableInterval readableInterval) {
        ReadableInterval readableInterval2 = DateTimeUtils.getReadableInterval(readableInterval);
        if (overlaps(readableInterval2)) {
            return new Interval(Math.max(getStartMillis(), readableInterval2.getStartMillis()), Math.min(getEndMillis(), readableInterval2.getEndMillis()), getChronology());
        }
        return null;
    }

    @Override // org.joda.time.base.AbstractInterval, org.joda.time.ReadableInterval
    public Interval toInterval() {
        return this;
    }

    public Interval withChronology(Chronology chronology) {
        return getChronology() == chronology ? this : new Interval(getStartMillis(), getEndMillis(), chronology);
    }

    public Interval withDurationAfterStart(ReadableDuration readableDuration) {
        long durationMillis = DateTimeUtils.getDurationMillis(readableDuration);
        if (durationMillis == toDurationMillis()) {
            return this;
        }
        Chronology chronology = getChronology();
        long startMillis = getStartMillis();
        return new Interval(startMillis, chronology.add(startMillis, durationMillis, 1), chronology);
    }

    public Interval withDurationBeforeEnd(ReadableDuration readableDuration) {
        long durationMillis = DateTimeUtils.getDurationMillis(readableDuration);
        if (durationMillis == toDurationMillis()) {
            return this;
        }
        Chronology chronology = getChronology();
        long endMillis = getEndMillis();
        return new Interval(chronology.add(endMillis, durationMillis, -1), endMillis, chronology);
    }

    public Interval withEnd(ReadableInstant readableInstant) {
        return withEndMillis(DateTimeUtils.getInstantMillis(readableInstant));
    }

    public Interval withEndMillis(long j2) {
        return j2 == getEndMillis() ? this : new Interval(getStartMillis(), j2, getChronology());
    }

    public Interval withPeriodAfterStart(ReadablePeriod readablePeriod) {
        if (readablePeriod == null) {
            return withDurationAfterStart(null);
        }
        Chronology chronology = getChronology();
        long startMillis = getStartMillis();
        return new Interval(startMillis, chronology.add(readablePeriod, startMillis, 1), chronology);
    }

    public Interval withPeriodBeforeEnd(ReadablePeriod readablePeriod) {
        if (readablePeriod == null) {
            return withDurationBeforeEnd(null);
        }
        Chronology chronology = getChronology();
        long endMillis = getEndMillis();
        return new Interval(chronology.add(readablePeriod, endMillis, -1), endMillis, chronology);
    }

    public Interval withStart(ReadableInstant readableInstant) {
        return withStartMillis(DateTimeUtils.getInstantMillis(readableInstant));
    }

    public Interval withStartMillis(long j2) {
        return j2 == getStartMillis() ? this : new Interval(j2, getEndMillis(), getChronology());
    }

    public Interval(long j2, long j3, DateTimeZone dateTimeZone) {
        super(j2, j3, ISOChronology.getInstance(dateTimeZone));
    }

    public Interval(long j2, long j3, Chronology chronology) {
        super(j2, j3, chronology);
    }

    public Interval(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        super(readableInstant, readableInstant2);
    }

    public Interval(ReadableInstant readableInstant, ReadableDuration readableDuration) {
        super(readableInstant, readableDuration);
    }

    public Interval(ReadableDuration readableDuration, ReadableInstant readableInstant) {
        super(readableDuration, readableInstant);
    }

    public Interval(ReadableInstant readableInstant, ReadablePeriod readablePeriod) {
        super(readableInstant, readablePeriod);
    }

    public Interval(ReadablePeriod readablePeriod, ReadableInstant readableInstant) {
        super(readablePeriod, readableInstant);
    }

    public Interval(Object obj) {
        super(obj, (Chronology) null);
    }

    public Interval(Object obj, Chronology chronology) {
        super(obj, chronology);
    }
}
