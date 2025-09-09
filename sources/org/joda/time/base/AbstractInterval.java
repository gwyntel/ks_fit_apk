package org.joda.time.base;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.MutableInterval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public abstract class AbstractInterval implements ReadableInterval {
    protected AbstractInterval() {
    }

    protected void checkInterval(long j2, long j3) {
        if (j3 < j2) {
            throw new IllegalArgumentException("The end instant must be greater or equal to the start");
        }
    }

    public boolean contains(long j2) {
        return j2 >= getStartMillis() && j2 < getEndMillis();
    }

    public boolean containsNow() {
        return contains(DateTimeUtils.currentTimeMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReadableInterval)) {
            return false;
        }
        ReadableInterval readableInterval = (ReadableInterval) obj;
        return getStartMillis() == readableInterval.getStartMillis() && getEndMillis() == readableInterval.getEndMillis() && FieldUtils.equals(getChronology(), readableInterval.getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public DateTime getEnd() {
        return new DateTime(getEndMillis(), getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public DateTime getStart() {
        return new DateTime(getStartMillis(), getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public int hashCode() {
        long startMillis = getStartMillis();
        long endMillis = getEndMillis();
        return ((((3007 + ((int) (startMillis ^ (startMillis >>> 32)))) * 31) + ((int) (endMillis ^ (endMillis >>> 32)))) * 31) + getChronology().hashCode();
    }

    public boolean isAfter(long j2) {
        return getStartMillis() > j2;
    }

    public boolean isAfterNow() {
        return isAfter(DateTimeUtils.currentTimeMillis());
    }

    public boolean isBefore(long j2) {
        return getEndMillis() <= j2;
    }

    public boolean isBeforeNow() {
        return isBefore(DateTimeUtils.currentTimeMillis());
    }

    public boolean isEqual(ReadableInterval readableInterval) {
        return getStartMillis() == readableInterval.getStartMillis() && getEndMillis() == readableInterval.getEndMillis();
    }

    @Override // org.joda.time.ReadableInterval
    public boolean overlaps(ReadableInterval readableInterval) {
        long startMillis = getStartMillis();
        long endMillis = getEndMillis();
        if (readableInterval != null) {
            return startMillis < readableInterval.getEndMillis() && readableInterval.getStartMillis() < endMillis;
        }
        long jCurrentTimeMillis = DateTimeUtils.currentTimeMillis();
        return startMillis < jCurrentTimeMillis && jCurrentTimeMillis < endMillis;
    }

    @Override // org.joda.time.ReadableInterval
    public Duration toDuration() {
        long durationMillis = toDurationMillis();
        return durationMillis == 0 ? Duration.ZERO : new Duration(durationMillis);
    }

    @Override // org.joda.time.ReadableInterval
    public long toDurationMillis() {
        return FieldUtils.safeAdd(getEndMillis(), -getStartMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public Interval toInterval() {
        return new Interval(getStartMillis(), getEndMillis(), getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public MutableInterval toMutableInterval() {
        return new MutableInterval(getStartMillis(), getEndMillis(), getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public Period toPeriod() {
        return new Period(getStartMillis(), getEndMillis(), getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public String toString() {
        DateTimeFormatter dateTimeFormatterWithChronology = ISODateTimeFormat.dateTime().withChronology(getChronology());
        StringBuffer stringBuffer = new StringBuffer(48);
        dateTimeFormatterWithChronology.printTo(stringBuffer, getStartMillis());
        stringBuffer.append(IOUtils.DIR_SEPARATOR_UNIX);
        dateTimeFormatterWithChronology.printTo(stringBuffer, getEndMillis());
        return stringBuffer.toString();
    }

    @Override // org.joda.time.ReadableInterval
    public boolean isAfter(ReadableInstant readableInstant) {
        return readableInstant == null ? isAfterNow() : isAfter(readableInstant.getMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public boolean isBefore(ReadableInstant readableInstant) {
        return readableInstant == null ? isBeforeNow() : isBefore(readableInstant.getMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public Period toPeriod(PeriodType periodType) {
        return new Period(getStartMillis(), getEndMillis(), periodType, getChronology());
    }

    @Override // org.joda.time.ReadableInterval
    public boolean contains(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return containsNow();
        }
        return contains(readableInstant.getMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public boolean isAfter(ReadableInterval readableInterval) {
        long endMillis;
        if (readableInterval == null) {
            endMillis = DateTimeUtils.currentTimeMillis();
        } else {
            endMillis = readableInterval.getEndMillis();
        }
        return getStartMillis() >= endMillis;
    }

    @Override // org.joda.time.ReadableInterval
    public boolean isBefore(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return isBeforeNow();
        }
        return isBefore(readableInterval.getStartMillis());
    }

    @Override // org.joda.time.ReadableInterval
    public boolean contains(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return containsNow();
        }
        long startMillis = readableInterval.getStartMillis();
        long endMillis = readableInterval.getEndMillis();
        long startMillis2 = getStartMillis();
        long endMillis2 = getEndMillis();
        return startMillis2 <= startMillis && startMillis < endMillis2 && endMillis <= endMillis2;
    }
}
