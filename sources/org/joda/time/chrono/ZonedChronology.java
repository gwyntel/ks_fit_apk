package org.joda.time.chrono;

import com.heytap.mcssdk.constant.Constants;
import java.util.HashMap;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;
import org.joda.time.ReadablePartial;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.BaseDurationField;

/* loaded from: classes5.dex */
public final class ZonedChronology extends AssembledChronology {
    private static final long serialVersionUID = -1079258847191166848L;

    static final class ZonedDateTimeField extends BaseDateTimeField {
        private static final long serialVersionUID = -3968986277775529794L;

        /* renamed from: a, reason: collision with root package name */
        final DateTimeField f26697a;

        /* renamed from: b, reason: collision with root package name */
        final DateTimeZone f26698b;

        /* renamed from: c, reason: collision with root package name */
        final DurationField f26699c;

        /* renamed from: d, reason: collision with root package name */
        final boolean f26700d;

        /* renamed from: e, reason: collision with root package name */
        final DurationField f26701e;

        /* renamed from: f, reason: collision with root package name */
        final DurationField f26702f;

        ZonedDateTimeField(DateTimeField dateTimeField, DateTimeZone dateTimeZone, DurationField durationField, DurationField durationField2, DurationField durationField3) {
            super(dateTimeField.getType());
            if (!dateTimeField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.f26697a = dateTimeField;
            this.f26698b = dateTimeZone;
            this.f26699c = durationField;
            this.f26700d = ZonedChronology.useTimeArithmetic(durationField);
            this.f26701e = durationField2;
            this.f26702f = durationField3;
        }

        private int getOffsetToAdd(long j2) {
            int offset = this.f26698b.getOffset(j2);
            long j3 = offset;
            if (((j2 + j3) ^ j2) >= 0 || (j2 ^ j3) < 0) {
                return offset;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, int i2) {
            if (this.f26700d) {
                long offsetToAdd = getOffsetToAdd(j2);
                return this.f26697a.add(j2 + offsetToAdd, i2) - offsetToAdd;
            }
            return this.f26698b.convertLocalToUTC(this.f26697a.add(this.f26698b.convertUTCToLocal(j2), i2), false, j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long addWrapField(long j2, int i2) {
            if (this.f26700d) {
                long offsetToAdd = getOffsetToAdd(j2);
                return this.f26697a.addWrapField(j2 + offsetToAdd, i2) - offsetToAdd;
            }
            return this.f26698b.convertLocalToUTC(this.f26697a.addWrapField(this.f26698b.convertUTCToLocal(j2), i2), false, j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int get(long j2) {
            return this.f26697a.get(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsShortText(long j2, Locale locale) {
            return this.f26697a.getAsShortText(this.f26698b.convertUTCToLocal(j2), locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(long j2, Locale locale) {
            return this.f26697a.getAsText(this.f26698b.convertUTCToLocal(j2), locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getDifference(long j2, long j3) {
            return this.f26697a.getDifference(j2 + (this.f26700d ? r0 : getOffsetToAdd(j2)), j3 + getOffsetToAdd(j3));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long getDifferenceAsLong(long j2, long j3) {
            return this.f26697a.getDifferenceAsLong(j2 + (this.f26700d ? r0 : getOffsetToAdd(j2)), j3 + getOffsetToAdd(j3));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getDurationField() {
            return this.f26699c;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getLeapAmount(long j2) {
            return this.f26697a.getLeapAmount(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getLeapDurationField() {
            return this.f26702f;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumShortTextLength(Locale locale) {
            return this.f26697a.getMaximumShortTextLength(locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumTextLength(Locale locale) {
            return this.f26697a.getMaximumTextLength(locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue() {
            return this.f26697a.getMaximumValue();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue() {
            return this.f26697a.getMinimumValue();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public final DurationField getRangeDurationField() {
            return this.f26701e;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public boolean isLeap(long j2) {
            return this.f26697a.isLeap(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.DateTimeField
        public boolean isLenient() {
            return this.f26697a.isLenient();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long remainder(long j2) {
            return this.f26697a.remainder(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundCeiling(long j2) {
            if (this.f26700d) {
                long offsetToAdd = getOffsetToAdd(j2);
                return this.f26697a.roundCeiling(j2 + offsetToAdd) - offsetToAdd;
            }
            return this.f26698b.convertLocalToUTC(this.f26697a.roundCeiling(this.f26698b.convertUTCToLocal(j2)), false, j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundFloor(long j2) {
            if (this.f26700d) {
                long offsetToAdd = getOffsetToAdd(j2);
                return this.f26697a.roundFloor(j2 + offsetToAdd) - offsetToAdd;
            }
            return this.f26698b.convertLocalToUTC(this.f26697a.roundFloor(this.f26698b.convertUTCToLocal(j2)), false, j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, int i2) {
            long j3 = this.f26697a.set(this.f26698b.convertUTCToLocal(j2), i2);
            long jConvertLocalToUTC = this.f26698b.convertLocalToUTC(j3, false, j2);
            if (get(jConvertLocalToUTC) == i2) {
                return jConvertLocalToUTC;
            }
            IllegalInstantException illegalInstantException = new IllegalInstantException(j3, this.f26698b.getID());
            IllegalFieldValueException illegalFieldValueException = new IllegalFieldValueException(this.f26697a.getType(), Integer.valueOf(i2), illegalInstantException.getMessage());
            illegalFieldValueException.initCause(illegalInstantException);
            throw illegalFieldValueException;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(long j2) {
            return this.f26697a.getMaximumValue(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(long j2) {
            return this.f26697a.getMinimumValue(this.f26698b.convertUTCToLocal(j2));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsShortText(int i2, Locale locale) {
            return this.f26697a.getAsShortText(i2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(int i2, Locale locale) {
            return this.f26697a.getAsText(i2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(ReadablePartial readablePartial) {
            return this.f26697a.getMaximumValue(readablePartial);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(ReadablePartial readablePartial) {
            return this.f26697a.getMinimumValue(readablePartial);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.f26697a.getMaximumValue(readablePartial, iArr);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.f26697a.getMinimumValue(readablePartial, iArr);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, long j3) {
            if (this.f26700d) {
                long offsetToAdd = getOffsetToAdd(j2);
                return this.f26697a.add(j2 + offsetToAdd, j3) - offsetToAdd;
            }
            return this.f26698b.convertLocalToUTC(this.f26697a.add(this.f26698b.convertUTCToLocal(j2), j3), false, j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, String str, Locale locale) {
            return this.f26698b.convertLocalToUTC(this.f26697a.set(this.f26698b.convertUTCToLocal(j2), str, locale), false, j2);
        }
    }

    static class ZonedDurationField extends BaseDurationField {
        private static final long serialVersionUID = -485345310999208286L;
        final DurationField iField;
        final boolean iTimeField;
        final DateTimeZone iZone;

        ZonedDurationField(DurationField durationField, DateTimeZone dateTimeZone) {
            super(durationField.getType());
            if (!durationField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.iField = durationField;
            this.iTimeField = ZonedChronology.useTimeArithmetic(durationField);
            this.iZone = dateTimeZone;
        }

        private long addOffset(long j2) {
            return this.iZone.convertUTCToLocal(j2);
        }

        private int getOffsetFromLocalToSubtract(long j2) {
            int offsetFromLocal = this.iZone.getOffsetFromLocal(j2);
            long j3 = offsetFromLocal;
            if (((j2 - j3) ^ j2) >= 0 || (j2 ^ j3) >= 0) {
                return offsetFromLocal;
            }
            throw new ArithmeticException("Subtracting time zone offset caused overflow");
        }

        private int getOffsetToAdd(long j2) {
            int offset = this.iZone.getOffset(j2);
            long j3 = offset;
            if (((j2 + j3) ^ j2) >= 0 || (j2 ^ j3) < 0) {
                return offset;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        @Override // org.joda.time.DurationField
        public long add(long j2, int i2) {
            int offsetToAdd = getOffsetToAdd(j2);
            long jAdd = this.iField.add(j2 + offsetToAdd, i2);
            if (!this.iTimeField) {
                offsetToAdd = getOffsetFromLocalToSubtract(jAdd);
            }
            return jAdd - offsetToAdd;
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getDifference(long j2, long j3) {
            return this.iField.getDifference(j2 + (this.iTimeField ? r0 : getOffsetToAdd(j2)), j3 + getOffsetToAdd(j3));
        }

        @Override // org.joda.time.DurationField
        public long getDifferenceAsLong(long j2, long j3) {
            return this.iField.getDifferenceAsLong(j2 + (this.iTimeField ? r0 : getOffsetToAdd(j2)), j3 + getOffsetToAdd(j3));
        }

        @Override // org.joda.time.DurationField
        public long getMillis(int i2, long j2) {
            return this.iField.getMillis(i2, addOffset(j2));
        }

        @Override // org.joda.time.DurationField
        public long getUnitMillis() {
            return this.iField.getUnitMillis();
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getValue(long j2, long j3) {
            return this.iField.getValue(j2, addOffset(j3));
        }

        @Override // org.joda.time.DurationField
        public long getValueAsLong(long j2, long j3) {
            return this.iField.getValueAsLong(j2, addOffset(j3));
        }

        @Override // org.joda.time.DurationField
        public boolean isPrecise() {
            return this.iTimeField ? this.iField.isPrecise() : this.iField.isPrecise() && this.iZone.isFixed();
        }

        @Override // org.joda.time.DurationField
        public long getMillis(long j2, long j3) {
            return this.iField.getMillis(j2, addOffset(j3));
        }

        @Override // org.joda.time.DurationField
        public long add(long j2, long j3) {
            int offsetToAdd = getOffsetToAdd(j2);
            long jAdd = this.iField.add(j2 + offsetToAdd, j3);
            if (!this.iTimeField) {
                offsetToAdd = getOffsetFromLocalToSubtract(jAdd);
            }
            return jAdd - offsetToAdd;
        }
    }

    private ZonedChronology(Chronology chronology, DateTimeZone dateTimeZone) {
        super(chronology, dateTimeZone);
    }

    private DurationField convertField(DurationField durationField, HashMap<Object, Object> map) {
        if (durationField == null || !durationField.isSupported()) {
            return durationField;
        }
        if (map.containsKey(durationField)) {
            return (DurationField) map.get(durationField);
        }
        ZonedDurationField zonedDurationField = new ZonedDurationField(durationField, getZone());
        map.put(durationField, zonedDurationField);
        return zonedDurationField;
    }

    public static ZonedChronology getInstance(Chronology chronology, DateTimeZone dateTimeZone) {
        if (chronology == null) {
            throw new IllegalArgumentException("Must supply a chronology");
        }
        Chronology chronologyWithUTC = chronology.withUTC();
        if (chronologyWithUTC == null) {
            throw new IllegalArgumentException("UTC chronology must not be null");
        }
        if (dateTimeZone != null) {
            return new ZonedChronology(chronologyWithUTC, dateTimeZone);
        }
        throw new IllegalArgumentException("DateTimeZone must not be null");
    }

    private long localToUTC(long j2) {
        DateTimeZone zone = getZone();
        int offsetFromLocal = zone.getOffsetFromLocal(j2);
        long j3 = j2 - offsetFromLocal;
        if (offsetFromLocal == zone.getOffset(j3)) {
            return j3;
        }
        throw new IllegalInstantException(j3, zone.getID());
    }

    static boolean useTimeArithmetic(DurationField durationField) {
        return durationField != null && durationField.getUnitMillis() < Constants.MILLS_OF_LAUNCH_INTERVAL;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonedChronology)) {
            return false;
        }
        ZonedChronology zonedChronology = (ZonedChronology) obj;
        return getBase().equals(zonedChronology.getBase()) && getZone().equals(zonedChronology.getZone());
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        return localToUTC(getBase().getDateTimeMillis(i2, i3, i4, i5));
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public DateTimeZone getZone() {
        return (DateTimeZone) getParam();
    }

    public int hashCode() {
        return (getZone().hashCode() * 11) + 326565 + (getBase().hashCode() * 7);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        return "ZonedChronology[" + getBase() + ", " + getZone().getID() + ']';
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return getBase();
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getParam() ? this : dateTimeZone == DateTimeZone.UTC ? getBase() : new ZonedChronology(getBase(), dateTimeZone);
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        return localToUTC(getBase().getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8));
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(long j2, int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        return localToUTC(getBase().getDateTimeMillis(getZone().getOffset(j2) + j2, i2, i3, i4, i5));
    }

    private DateTimeField convertField(DateTimeField dateTimeField, HashMap<Object, Object> map) {
        if (dateTimeField == null || !dateTimeField.isSupported()) {
            return dateTimeField;
        }
        if (map.containsKey(dateTimeField)) {
            return (DateTimeField) map.get(dateTimeField);
        }
        ZonedDateTimeField zonedDateTimeField = new ZonedDateTimeField(dateTimeField, getZone(), convertField(dateTimeField.getDurationField(), map), convertField(dateTimeField.getRangeDurationField(), map), convertField(dateTimeField.getLeapDurationField(), map));
        map.put(dateTimeField, zonedDateTimeField);
        return zonedDateTimeField;
    }
}
