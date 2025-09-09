package org.joda.time.chrono;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.ReadablePartial;
import org.joda.time.ReadablePeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.UnsupportedDateTimeField;
import org.joda.time.field.UnsupportedDurationField;

/* loaded from: classes5.dex */
public abstract class BaseChronology extends Chronology implements Serializable {
    private static final long serialVersionUID = -7310865996721419676L;

    protected BaseChronology() {
    }

    @Override // org.joda.time.Chronology
    public long add(ReadablePeriod readablePeriod, long j2, int i2) {
        if (i2 != 0 && readablePeriod != null) {
            int size = readablePeriod.size();
            for (int i3 = 0; i3 < size; i3++) {
                long value = readablePeriod.getValue(i3);
                if (value != 0) {
                    j2 = readablePeriod.getFieldType(i3).getField(this).add(j2, value * i2);
                }
            }
        }
        return j2;
    }

    @Override // org.joda.time.Chronology
    public DurationField centuries() {
        return UnsupportedDurationField.getInstance(DurationFieldType.centuries());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField centuryOfEra() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.centuryOfEra(), centuries());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField clockhourOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.clockhourOfDay(), hours());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField clockhourOfHalfday() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.clockhourOfHalfday(), hours());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField dayOfMonth() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfMonth(), days());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField dayOfWeek() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfWeek(), days());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField dayOfYear() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfYear(), days());
    }

    @Override // org.joda.time.Chronology
    public DurationField days() {
        return UnsupportedDurationField.getInstance(DurationFieldType.days());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField era() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.era(), eras());
    }

    @Override // org.joda.time.Chronology
    public DurationField eras() {
        return UnsupportedDurationField.getInstance(DurationFieldType.eras());
    }

    @Override // org.joda.time.Chronology
    public int[] get(ReadablePartial readablePartial, long j2) {
        int size = readablePartial.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = readablePartial.getFieldType(i2).getField(this).get(j2);
        }
        return iArr;
    }

    @Override // org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        return millisOfDay().set(dayOfMonth().set(monthOfYear().set(year().set(0L, i2), i3), i4), i5);
    }

    @Override // org.joda.time.Chronology
    public abstract DateTimeZone getZone();

    @Override // org.joda.time.Chronology
    public DateTimeField halfdayOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.halfdayOfDay(), halfdays());
    }

    @Override // org.joda.time.Chronology
    public DurationField halfdays() {
        return UnsupportedDurationField.getInstance(DurationFieldType.halfdays());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField hourOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.hourOfDay(), hours());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField hourOfHalfday() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.hourOfHalfday(), hours());
    }

    @Override // org.joda.time.Chronology
    public DurationField hours() {
        return UnsupportedDurationField.getInstance(DurationFieldType.hours());
    }

    @Override // org.joda.time.Chronology
    public DurationField millis() {
        return UnsupportedDurationField.getInstance(DurationFieldType.millis());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField millisOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.millisOfDay(), millis());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField millisOfSecond() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.millisOfSecond(), millis());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField minuteOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.minuteOfDay(), minutes());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField minuteOfHour() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.minuteOfHour(), minutes());
    }

    @Override // org.joda.time.Chronology
    public DurationField minutes() {
        return UnsupportedDurationField.getInstance(DurationFieldType.minutes());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField monthOfYear() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.monthOfYear(), months());
    }

    @Override // org.joda.time.Chronology
    public DurationField months() {
        return UnsupportedDurationField.getInstance(DurationFieldType.months());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField secondOfDay() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.secondOfDay(), seconds());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField secondOfMinute() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.secondOfMinute(), seconds());
    }

    @Override // org.joda.time.Chronology
    public DurationField seconds() {
        return UnsupportedDurationField.getInstance(DurationFieldType.seconds());
    }

    @Override // org.joda.time.Chronology
    public long set(ReadablePartial readablePartial, long j2) {
        int size = readablePartial.size();
        for (int i2 = 0; i2 < size; i2++) {
            j2 = readablePartial.getFieldType(i2).getField(this).set(j2, readablePartial.getValue(i2));
        }
        return j2;
    }

    @Override // org.joda.time.Chronology
    public abstract String toString();

    @Override // org.joda.time.Chronology
    public void validate(ReadablePartial readablePartial, int[] iArr) {
        int size = readablePartial.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = iArr[i2];
            DateTimeField field = readablePartial.getField(i2);
            if (i3 < field.getMinimumValue()) {
                throw new IllegalFieldValueException(field.getType(), Integer.valueOf(i3), Integer.valueOf(field.getMinimumValue()), (Number) null);
            }
            if (i3 > field.getMaximumValue()) {
                throw new IllegalFieldValueException(field.getType(), Integer.valueOf(i3), (Number) null, Integer.valueOf(field.getMaximumValue()));
            }
        }
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = iArr[i4];
            DateTimeField field2 = readablePartial.getField(i4);
            if (i5 < field2.getMinimumValue(readablePartial, iArr)) {
                throw new IllegalFieldValueException(field2.getType(), Integer.valueOf(i5), Integer.valueOf(field2.getMinimumValue(readablePartial, iArr)), (Number) null);
            }
            if (i5 > field2.getMaximumValue(readablePartial, iArr)) {
                throw new IllegalFieldValueException(field2.getType(), Integer.valueOf(i5), (Number) null, Integer.valueOf(field2.getMaximumValue(readablePartial, iArr)));
            }
        }
    }

    @Override // org.joda.time.Chronology
    public DateTimeField weekOfWeekyear() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekOfWeekyear(), weeks());
    }

    @Override // org.joda.time.Chronology
    public DurationField weeks() {
        return UnsupportedDurationField.getInstance(DurationFieldType.weeks());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField weekyear() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekyear(), weekyears());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField weekyearOfCentury() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekyearOfCentury(), weekyears());
    }

    @Override // org.joda.time.Chronology
    public DurationField weekyears() {
        return UnsupportedDurationField.getInstance(DurationFieldType.weekyears());
    }

    @Override // org.joda.time.Chronology
    public abstract Chronology withUTC();

    @Override // org.joda.time.Chronology
    public abstract Chronology withZone(DateTimeZone dateTimeZone);

    @Override // org.joda.time.Chronology
    public DateTimeField year() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.year(), years());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField yearOfCentury() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.yearOfCentury(), years());
    }

    @Override // org.joda.time.Chronology
    public DateTimeField yearOfEra() {
        return UnsupportedDateTimeField.getInstance(DateTimeFieldType.yearOfEra(), years());
    }

    @Override // org.joda.time.Chronology
    public DurationField years() {
        return UnsupportedDurationField.getInstance(DurationFieldType.years());
    }

    @Override // org.joda.time.Chronology
    public long add(long j2, long j3, int i2) {
        return (j3 == 0 || i2 == 0) ? j2 : FieldUtils.safeAdd(j2, FieldUtils.safeMultiply(j3, i2));
    }

    @Override // org.joda.time.Chronology
    public int[] get(ReadablePeriod readablePeriod, long j2, long j3) {
        int size = readablePeriod.size();
        int[] iArr = new int[size];
        if (j2 != j3) {
            for (int i2 = 0; i2 < size; i2++) {
                DurationField field = readablePeriod.getFieldType(i2).getField(this);
                int difference = field.getDifference(j3, j2);
                j2 = field.add(j2, difference);
                iArr[i2] = difference;
            }
        }
        return iArr;
    }

    @Override // org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        return millisOfSecond().set(secondOfMinute().set(minuteOfHour().set(hourOfDay().set(dayOfMonth().set(monthOfYear().set(year().set(0L, i2), i3), i4), i5), i6), i7), i8);
    }

    @Override // org.joda.time.Chronology
    public int[] get(ReadablePeriod readablePeriod, long j2) {
        int size = readablePeriod.size();
        int[] iArr = new int[size];
        long jAdd = 0;
        if (j2 != 0) {
            for (int i2 = 0; i2 < size; i2++) {
                DurationField field = readablePeriod.getFieldType(i2).getField(this);
                if (field.isPrecise()) {
                    int difference = field.getDifference(j2, jAdd);
                    jAdd = field.add(jAdd, difference);
                    iArr[i2] = difference;
                }
            }
        }
        return iArr;
    }

    @Override // org.joda.time.Chronology
    public long getDateTimeMillis(long j2, int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        return millisOfSecond().set(secondOfMinute().set(minuteOfHour().set(hourOfDay().set(j2, i2), i3), i4), i5);
    }
}
