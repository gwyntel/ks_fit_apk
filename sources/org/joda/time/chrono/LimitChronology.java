package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.DecoratedDurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class LimitChronology extends AssembledChronology {
    private static final long serialVersionUID = 7670866536893052522L;
    final DateTime iLowerLimit;
    final DateTime iUpperLimit;
    private transient LimitChronology iWithUTC;

    private class LimitException extends IllegalArgumentException {
        private static final long serialVersionUID = -5924689995607498581L;
        private final boolean iIsLow;

        LimitException(String str, boolean z2) {
            super(str);
            this.iIsLow = z2;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            StringBuffer stringBuffer = new StringBuffer(85);
            stringBuffer.append("The");
            String message = super.getMessage();
            if (message != null) {
                stringBuffer.append(' ');
                stringBuffer.append(message);
            }
            stringBuffer.append(" instant is ");
            DateTimeFormatter dateTimeFormatterWithChronology = ISODateTimeFormat.dateTime().withChronology(LimitChronology.this.getBase());
            if (this.iIsLow) {
                stringBuffer.append("below the supported minimum of ");
                dateTimeFormatterWithChronology.printTo(stringBuffer, LimitChronology.this.getLowerLimit().getMillis());
            } else {
                stringBuffer.append("above the supported maximum of ");
                dateTimeFormatterWithChronology.printTo(stringBuffer, LimitChronology.this.getUpperLimit().getMillis());
            }
            stringBuffer.append(" (");
            stringBuffer.append(LimitChronology.this.getBase());
            stringBuffer.append(')');
            return stringBuffer.toString();
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "IllegalArgumentException: " + getMessage();
        }
    }

    private LimitChronology(Chronology chronology, DateTime dateTime, DateTime dateTime2) {
        super(chronology, null);
        this.iLowerLimit = dateTime;
        this.iUpperLimit = dateTime2;
    }

    private DurationField convertField(DurationField durationField, HashMap<Object, Object> map) {
        if (durationField == null || !durationField.isSupported()) {
            return durationField;
        }
        if (map.containsKey(durationField)) {
            return (DurationField) map.get(durationField);
        }
        LimitDurationField limitDurationField = new LimitDurationField(durationField);
        map.put(durationField, limitDurationField);
        return limitDurationField;
    }

    public static LimitChronology getInstance(Chronology chronology, ReadableDateTime readableDateTime, ReadableDateTime readableDateTime2) {
        if (chronology == null) {
            throw new IllegalArgumentException("Must supply a chronology");
        }
        DateTime dateTime = readableDateTime == null ? null : readableDateTime.toDateTime();
        DateTime dateTime2 = readableDateTime2 != null ? readableDateTime2.toDateTime() : null;
        if (dateTime == null || dateTime2 == null || dateTime.isBefore(dateTime2)) {
            return new LimitChronology(chronology, dateTime, dateTime2);
        }
        throw new IllegalArgumentException("The lower limit must be come before than the upper limit");
    }

    @Override // org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        HashMap<Object, Object> map = new HashMap<>();
        fields.eras = convertField(fields.eras, map);
        fields.centuries = convertField(fields.centuries, map);
        fields.years = convertField(fields.years, map);
        fields.months = convertField(fields.months, map);
        fields.weekyears = convertField(fields.weekyears, map);
        fields.weeks = convertField(fields.weeks, map);
        fields.days = convertField(fields.days, map);
        fields.halfdays = convertField(fields.halfdays, map);
        fields.hours = convertField(fields.hours, map);
        fields.minutes = convertField(fields.minutes, map);
        fields.seconds = convertField(fields.seconds, map);
        fields.millis = convertField(fields.millis, map);
        fields.year = convertField(fields.year, map);
        fields.yearOfEra = convertField(fields.yearOfEra, map);
        fields.yearOfCentury = convertField(fields.yearOfCentury, map);
        fields.centuryOfEra = convertField(fields.centuryOfEra, map);
        fields.era = convertField(fields.era, map);
        fields.dayOfWeek = convertField(fields.dayOfWeek, map);
        fields.dayOfMonth = convertField(fields.dayOfMonth, map);
        fields.dayOfYear = convertField(fields.dayOfYear, map);
        fields.monthOfYear = convertField(fields.monthOfYear, map);
        fields.weekOfWeekyear = convertField(fields.weekOfWeekyear, map);
        fields.weekyear = convertField(fields.weekyear, map);
        fields.weekyearOfCentury = convertField(fields.weekyearOfCentury, map);
        fields.millisOfSecond = convertField(fields.millisOfSecond, map);
        fields.millisOfDay = convertField(fields.millisOfDay, map);
        fields.secondOfMinute = convertField(fields.secondOfMinute, map);
        fields.secondOfDay = convertField(fields.secondOfDay, map);
        fields.minuteOfHour = convertField(fields.minuteOfHour, map);
        fields.minuteOfDay = convertField(fields.minuteOfDay, map);
        fields.hourOfDay = convertField(fields.hourOfDay, map);
        fields.hourOfHalfday = convertField(fields.hourOfHalfday, map);
        fields.clockhourOfDay = convertField(fields.clockhourOfDay, map);
        fields.clockhourOfHalfday = convertField(fields.clockhourOfHalfday, map);
        fields.halfdayOfDay = convertField(fields.halfdayOfDay, map);
    }

    void checkLimits(long j2, String str) {
        DateTime dateTime = this.iLowerLimit;
        if (dateTime != null && j2 < dateTime.getMillis()) {
            throw new LimitException(str, true);
        }
        DateTime dateTime2 = this.iUpperLimit;
        if (dateTime2 != null && j2 >= dateTime2.getMillis()) {
            throw new LimitException(str, false);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LimitChronology)) {
            return false;
        }
        LimitChronology limitChronology = (LimitChronology) obj;
        return getBase().equals(limitChronology.getBase()) && FieldUtils.equals(getLowerLimit(), limitChronology.getLowerLimit()) && FieldUtils.equals(getUpperLimit(), limitChronology.getUpperLimit());
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        long dateTimeMillis = getBase().getDateTimeMillis(i2, i3, i4, i5);
        checkLimits(dateTimeMillis, "resulting");
        return dateTimeMillis;
    }

    public DateTime getLowerLimit() {
        return this.iLowerLimit;
    }

    public DateTime getUpperLimit() {
        return this.iUpperLimit;
    }

    public int hashCode() {
        return (getLowerLimit() != null ? getLowerLimit().hashCode() : 0) + 317351877 + (getUpperLimit() != null ? getUpperLimit().hashCode() : 0) + (getBase().hashCode() * 7);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LimitChronology[");
        sb.append(getBase().toString());
        sb.append(", ");
        sb.append(getLowerLimit() == null ? "NoLimit" : getLowerLimit().toString());
        sb.append(", ");
        sb.append(getUpperLimit() != null ? getUpperLimit().toString() : "NoLimit");
        sb.append(']');
        return sb.toString();
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return withZone(DateTimeZone.UTC);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        LimitChronology limitChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
        if (dateTimeZone == dateTimeZone2 && (limitChronology = this.iWithUTC) != null) {
            return limitChronology;
        }
        DateTime dateTime = this.iLowerLimit;
        if (dateTime != null) {
            MutableDateTime mutableDateTime = dateTime.toMutableDateTime();
            mutableDateTime.setZoneRetainFields(dateTimeZone);
            dateTime = mutableDateTime.toDateTime();
        }
        DateTime dateTime2 = this.iUpperLimit;
        if (dateTime2 != null) {
            MutableDateTime mutableDateTime2 = dateTime2.toMutableDateTime();
            mutableDateTime2.setZoneRetainFields(dateTimeZone);
            dateTime2 = mutableDateTime2.toDateTime();
        }
        LimitChronology limitChronology2 = getInstance(getBase().withZone(dateTimeZone), dateTime, dateTime2);
        if (dateTimeZone == dateTimeZone2) {
            this.iWithUTC = limitChronology2;
        }
        return limitChronology2;
    }

    private class LimitDurationField extends DecoratedDurationField {
        private static final long serialVersionUID = 8049297699408782284L;

        LimitDurationField(DurationField durationField) {
            super(durationField, durationField.getType());
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long add(long j2, int i2) {
            LimitChronology.this.checkLimits(j2, null);
            long jAdd = getWrappedField().add(j2, i2);
            LimitChronology.this.checkLimits(jAdd, "resulting");
            return jAdd;
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getDifference(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, "minuend");
            LimitChronology.this.checkLimits(j3, "subtrahend");
            return getWrappedField().getDifference(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long getDifferenceAsLong(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, "minuend");
            LimitChronology.this.checkLimits(j3, "subtrahend");
            return getWrappedField().getDifferenceAsLong(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long getMillis(int i2, long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getMillis(i2, j2);
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getValue(long j2, long j3) {
            LimitChronology.this.checkLimits(j3, null);
            return getWrappedField().getValue(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long getValueAsLong(long j2, long j3) {
            LimitChronology.this.checkLimits(j3, null);
            return getWrappedField().getValueAsLong(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long getMillis(long j2, long j3) {
            LimitChronology.this.checkLimits(j3, null);
            return getWrappedField().getMillis(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long add(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, null);
            long jAdd = getWrappedField().add(j2, j3);
            LimitChronology.this.checkLimits(jAdd, "resulting");
            return jAdd;
        }
    }

    private class LimitDateTimeField extends DecoratedDateTimeField {
        private static final long serialVersionUID = -2435306746995699312L;
        private final DurationField iDurationField;
        private final DurationField iLeapDurationField;
        private final DurationField iRangeDurationField;

        LimitDateTimeField(DateTimeField dateTimeField, DurationField durationField, DurationField durationField2, DurationField durationField3) {
            super(dateTimeField, dateTimeField.getType());
            this.iDurationField = durationField;
            this.iRangeDurationField = durationField2;
            this.iLeapDurationField = durationField3;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, int i2) {
            LimitChronology.this.checkLimits(j2, null);
            long jAdd = getWrappedField().add(j2, i2);
            LimitChronology.this.checkLimits(jAdd, "resulting");
            return jAdd;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long addWrapField(long j2, int i2) {
            LimitChronology.this.checkLimits(j2, null);
            long jAddWrapField = getWrappedField().addWrapField(j2, i2);
            LimitChronology.this.checkLimits(jAddWrapField, "resulting");
            return jAddWrapField;
        }

        @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int get(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().get(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsShortText(long j2, Locale locale) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getAsShortText(j2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(long j2, Locale locale) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getAsText(j2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getDifference(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, "minuend");
            LimitChronology.this.checkLimits(j3, "subtrahend");
            return getWrappedField().getDifference(j2, j3);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long getDifferenceAsLong(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, "minuend");
            LimitChronology.this.checkLimits(j3, "subtrahend");
            return getWrappedField().getDifferenceAsLong(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getDurationField() {
            return this.iDurationField;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getLeapAmount(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getLeapAmount(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getLeapDurationField() {
            return this.iLeapDurationField;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumShortTextLength(Locale locale) {
            return getWrappedField().getMaximumShortTextLength(locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumTextLength(Locale locale) {
            return getWrappedField().getMaximumTextLength(locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getMaximumValue(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().getMinimumValue(j2);
        }

        @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getRangeDurationField() {
            return this.iRangeDurationField;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public boolean isLeap(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            return getWrappedField().isLeap(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long remainder(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRemainder = getWrappedField().remainder(j2);
            LimitChronology.this.checkLimits(jRemainder, "resulting");
            return jRemainder;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundCeiling(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRoundCeiling = getWrappedField().roundCeiling(j2);
            LimitChronology.this.checkLimits(jRoundCeiling, "resulting");
            return jRoundCeiling;
        }

        @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundFloor(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRoundFloor = getWrappedField().roundFloor(j2);
            LimitChronology.this.checkLimits(jRoundFloor, "resulting");
            return jRoundFloor;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundHalfCeiling(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRoundHalfCeiling = getWrappedField().roundHalfCeiling(j2);
            LimitChronology.this.checkLimits(jRoundHalfCeiling, "resulting");
            return jRoundHalfCeiling;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundHalfEven(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRoundHalfEven = getWrappedField().roundHalfEven(j2);
            LimitChronology.this.checkLimits(jRoundHalfEven, "resulting");
            return jRoundHalfEven;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundHalfFloor(long j2) {
            LimitChronology.this.checkLimits(j2, null);
            long jRoundHalfFloor = getWrappedField().roundHalfFloor(j2);
            LimitChronology.this.checkLimits(jRoundHalfFloor, "resulting");
            return jRoundHalfFloor;
        }

        @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, int i2) {
            LimitChronology.this.checkLimits(j2, null);
            long j3 = getWrappedField().set(j2, i2);
            LimitChronology.this.checkLimits(j3, "resulting");
            return j3;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, long j3) {
            LimitChronology.this.checkLimits(j2, null);
            long jAdd = getWrappedField().add(j2, j3);
            LimitChronology.this.checkLimits(jAdd, "resulting");
            return jAdd;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, String str, Locale locale) {
            LimitChronology.this.checkLimits(j2, null);
            long j3 = getWrappedField().set(j2, str, locale);
            LimitChronology.this.checkLimits(j3, "resulting");
            return j3;
        }
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        long dateTimeMillis = getBase().getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        checkLimits(dateTimeMillis, "resulting");
        return dateTimeMillis;
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(long j2, int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        checkLimits(j2, null);
        long dateTimeMillis = getBase().getDateTimeMillis(j2, i2, i3, i4, i5);
        checkLimits(dateTimeMillis, "resulting");
        return dateTimeMillis;
    }

    private DateTimeField convertField(DateTimeField dateTimeField, HashMap<Object, Object> map) {
        if (dateTimeField == null || !dateTimeField.isSupported()) {
            return dateTimeField;
        }
        if (map.containsKey(dateTimeField)) {
            return (DateTimeField) map.get(dateTimeField);
        }
        LimitDateTimeField limitDateTimeField = new LimitDateTimeField(dateTimeField, convertField(dateTimeField.getDurationField(), map), convertField(dateTimeField.getRangeDurationField(), map), convertField(dateTimeField.getLeapDurationField(), map));
        map.put(dateTimeField, limitDateTimeField);
        return limitDateTimeField;
    }
}
