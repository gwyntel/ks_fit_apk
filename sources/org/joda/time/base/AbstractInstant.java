package org.joda.time.base;

import java.util.Date;
import org.joda.convert.ToString;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public abstract class AbstractInstant implements ReadableInstant {
    protected AbstractInstant() {
    }

    @Override // org.joda.time.ReadableInstant
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReadableInstant)) {
            return false;
        }
        ReadableInstant readableInstant = (ReadableInstant) obj;
        return getMillis() == readableInstant.getMillis() && FieldUtils.equals(getChronology(), readableInstant.getChronology());
    }

    @Override // org.joda.time.ReadableInstant
    public int get(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return dateTimeFieldType.getField(getChronology()).get(getMillis());
        }
        throw new IllegalArgumentException("The DateTimeFieldType must not be null");
    }

    @Override // org.joda.time.ReadableInstant
    public DateTimeZone getZone() {
        return getChronology().getZone();
    }

    @Override // org.joda.time.ReadableInstant
    public int hashCode() {
        return ((int) (getMillis() ^ (getMillis() >>> 32))) + getChronology().hashCode();
    }

    public boolean isAfter(long j2) {
        return getMillis() > j2;
    }

    public boolean isAfterNow() {
        return isAfter(DateTimeUtils.currentTimeMillis());
    }

    public boolean isBefore(long j2) {
        return getMillis() < j2;
    }

    public boolean isBeforeNow() {
        return isBefore(DateTimeUtils.currentTimeMillis());
    }

    public boolean isEqual(long j2) {
        return getMillis() == j2;
    }

    public boolean isEqualNow() {
        return isEqual(DateTimeUtils.currentTimeMillis());
    }

    @Override // org.joda.time.ReadableInstant
    public boolean isSupported(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            return false;
        }
        return dateTimeFieldType.getField(getChronology()).isSupported();
    }

    public Date toDate() {
        return new Date(getMillis());
    }

    public DateTime toDateTime() {
        return new DateTime(getMillis(), getZone());
    }

    public DateTime toDateTimeISO() {
        return new DateTime(getMillis(), ISOChronology.getInstance(getZone()));
    }

    @Override // org.joda.time.ReadableInstant
    public Instant toInstant() {
        return new Instant(getMillis());
    }

    public MutableDateTime toMutableDateTime() {
        return new MutableDateTime(getMillis(), getZone());
    }

    public MutableDateTime toMutableDateTimeISO() {
        return new MutableDateTime(getMillis(), ISOChronology.getInstance(getZone()));
    }

    @Override // org.joda.time.ReadableInstant
    @ToString
    public String toString() {
        return ISODateTimeFormat.dateTime().print(this);
    }

    @Override // java.lang.Comparable
    public int compareTo(ReadableInstant readableInstant) {
        if (this == readableInstant) {
            return 0;
        }
        long millis = readableInstant.getMillis();
        long millis2 = getMillis();
        if (millis2 == millis) {
            return 0;
        }
        return millis2 < millis ? -1 : 1;
    }

    @Override // org.joda.time.ReadableInstant
    public boolean isAfter(ReadableInstant readableInstant) {
        return isAfter(DateTimeUtils.getInstantMillis(readableInstant));
    }

    @Override // org.joda.time.ReadableInstant
    public boolean isBefore(ReadableInstant readableInstant) {
        return isBefore(DateTimeUtils.getInstantMillis(readableInstant));
    }

    @Override // org.joda.time.ReadableInstant
    public boolean isEqual(ReadableInstant readableInstant) {
        return isEqual(DateTimeUtils.getInstantMillis(readableInstant));
    }

    public DateTime toDateTime(DateTimeZone dateTimeZone) {
        return new DateTime(getMillis(), DateTimeUtils.getChronology(getChronology()).withZone(dateTimeZone));
    }

    public MutableDateTime toMutableDateTime(DateTimeZone dateTimeZone) {
        return new MutableDateTime(getMillis(), DateTimeUtils.getChronology(getChronology()).withZone(dateTimeZone));
    }

    public String toString(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter == null ? toString() : dateTimeFormatter.print(this);
    }

    public int get(DateTimeField dateTimeField) {
        if (dateTimeField != null) {
            return dateTimeField.get(getMillis());
        }
        throw new IllegalArgumentException("The DateTimeField must not be null");
    }

    public DateTime toDateTime(Chronology chronology) {
        return new DateTime(getMillis(), chronology);
    }

    public MutableDateTime toMutableDateTime(Chronology chronology) {
        return new MutableDateTime(getMillis(), chronology);
    }
}
