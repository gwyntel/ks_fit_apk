package org.joda.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.time.base.BaseDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class DateTime extends BaseDateTime implements ReadableDateTime, Serializable {
    private static final long serialVersionUID = -5171125899451703815L;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -6983323811635733510L;
        private DateTimeField iField;
        private DateTime iInstant;

        Property(DateTime dateTime, DateTimeField dateTimeField) {
            this.iInstant = dateTime;
            this.iField = dateTimeField;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            this.iInstant = (DateTime) objectInputStream.readObject();
            this.iField = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.iInstant.getChronology());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.iInstant);
            objectOutputStream.writeObject(this.iField.getType());
        }

        public DateTime addToCopy(int i2) {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.add(dateTime.getMillis(), i2));
        }

        public DateTime addWrapFieldToCopy(int i2) {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.addWrapField(dateTime.getMillis(), i2));
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected Chronology getChronology() {
            return this.iInstant.getChronology();
        }

        public DateTime getDateTime() {
            return this.iInstant;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        public DateTimeField getField() {
            return this.iField;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected long getMillis() {
            return this.iInstant.getMillis();
        }

        public DateTime roundCeilingCopy() {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.roundCeiling(dateTime.getMillis()));
        }

        public DateTime roundFloorCopy() {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.roundFloor(dateTime.getMillis()));
        }

        public DateTime roundHalfCeilingCopy() {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.roundHalfCeiling(dateTime.getMillis()));
        }

        public DateTime roundHalfEvenCopy() {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.roundHalfEven(dateTime.getMillis()));
        }

        public DateTime roundHalfFloorCopy() {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.roundHalfFloor(dateTime.getMillis()));
        }

        public DateTime setCopy(int i2) {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.set(dateTime.getMillis(), i2));
        }

        public DateTime withMaximumValue() {
            try {
                return setCopy(getMaximumValue());
            } catch (RuntimeException e2) {
                if (IllegalInstantException.isIllegalInstant(e2)) {
                    return new DateTime(getChronology().getZone().previousTransition(getMillis() + 86400000), getChronology());
                }
                throw e2;
            }
        }

        public DateTime withMinimumValue() {
            try {
                return setCopy(getMinimumValue());
            } catch (RuntimeException e2) {
                if (IllegalInstantException.isIllegalInstant(e2)) {
                    return new DateTime(getChronology().getZone().nextTransition(getMillis() - 86400000), getChronology());
                }
                throw e2;
            }
        }

        public DateTime addToCopy(long j2) {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.add(dateTime.getMillis(), j2));
        }

        public DateTime setCopy(String str, Locale locale) {
            DateTime dateTime = this.iInstant;
            return dateTime.withMillis(this.iField.set(dateTime.getMillis(), str, locale));
        }

        public DateTime setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public DateTime() {
    }

    public static DateTime now() {
        return new DateTime();
    }

    @FromString
    public static DateTime parse(String str) {
        return parse(str, ISODateTimeFormat.dateTimeParser().withOffsetParsed());
    }

    public Property centuryOfEra() {
        return new Property(this, getChronology().centuryOfEra());
    }

    public Property dayOfMonth() {
        return new Property(this, getChronology().dayOfMonth());
    }

    public Property dayOfWeek() {
        return new Property(this, getChronology().dayOfWeek());
    }

    public Property dayOfYear() {
        return new Property(this, getChronology().dayOfYear());
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    public Property hourOfDay() {
        return new Property(this, getChronology().hourOfDay());
    }

    public Property millisOfDay() {
        return new Property(this, getChronology().millisOfDay());
    }

    public Property millisOfSecond() {
        return new Property(this, getChronology().millisOfSecond());
    }

    public DateTime minus(long j2) {
        return withDurationAdded(j2, -1);
    }

    public DateTime minusDays(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().days().subtract(getMillis(), i2));
    }

    public DateTime minusHours(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().hours().subtract(getMillis(), i2));
    }

    public DateTime minusMillis(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().millis().subtract(getMillis(), i2));
    }

    public DateTime minusMinutes(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().minutes().subtract(getMillis(), i2));
    }

    public DateTime minusMonths(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().months().subtract(getMillis(), i2));
    }

    public DateTime minusSeconds(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().seconds().subtract(getMillis(), i2));
    }

    public DateTime minusWeeks(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().weeks().subtract(getMillis(), i2));
    }

    public DateTime minusYears(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().years().subtract(getMillis(), i2));
    }

    public Property minuteOfDay() {
        return new Property(this, getChronology().minuteOfDay());
    }

    public Property minuteOfHour() {
        return new Property(this, getChronology().minuteOfHour());
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public DateTime plus(long j2) {
        return withDurationAdded(j2, 1);
    }

    public DateTime plusDays(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().days().add(getMillis(), i2));
    }

    public DateTime plusHours(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().hours().add(getMillis(), i2));
    }

    public DateTime plusMillis(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().millis().add(getMillis(), i2));
    }

    public DateTime plusMinutes(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().minutes().add(getMillis(), i2));
    }

    public DateTime plusMonths(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().months().add(getMillis(), i2));
    }

    public DateTime plusSeconds(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().seconds().add(getMillis(), i2));
    }

    public DateTime plusWeeks(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().weeks().add(getMillis(), i2));
    }

    public DateTime plusYears(int i2) {
        return i2 == 0 ? this : withMillis(getChronology().years().add(getMillis(), i2));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        DateTimeField field = dateTimeFieldType.getField(getChronology());
        if (field.isSupported()) {
            return new Property(this, field);
        }
        throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
    }

    public Property secondOfDay() {
        return new Property(this, getChronology().secondOfDay());
    }

    public Property secondOfMinute() {
        return new Property(this, getChronology().secondOfMinute());
    }

    @Deprecated
    public DateMidnight toDateMidnight() {
        return new DateMidnight(getMillis(), getChronology());
    }

    @Override // org.joda.time.base.AbstractInstant, org.joda.time.ReadableDateTime
    public DateTime toDateTime() {
        return this;
    }

    @Override // org.joda.time.base.AbstractInstant
    public DateTime toDateTimeISO() {
        return getChronology() == ISOChronology.getInstance() ? this : super.toDateTimeISO();
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getMillis(), getChronology());
    }

    public LocalDateTime toLocalDateTime() {
        return new LocalDateTime(getMillis(), getChronology());
    }

    public LocalTime toLocalTime() {
        return new LocalTime(getMillis(), getChronology());
    }

    @Deprecated
    public TimeOfDay toTimeOfDay() {
        return new TimeOfDay(getMillis(), getChronology());
    }

    @Deprecated
    public YearMonthDay toYearMonthDay() {
        return new YearMonthDay(getMillis(), getChronology());
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public DateTime withCenturyOfEra(int i2) {
        return withMillis(getChronology().centuryOfEra().set(getMillis(), i2));
    }

    public DateTime withChronology(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        return chronology2 == getChronology() ? this : new DateTime(getMillis(), chronology2);
    }

    public DateTime withDate(int i2, int i3, int i4) {
        Chronology chronology = getChronology();
        return withMillis(chronology.dayOfMonth().set(chronology.monthOfYear().set(chronology.year().set(getMillis(), i2), i3), i4));
    }

    public DateTime withDayOfMonth(int i2) {
        return withMillis(getChronology().dayOfMonth().set(getMillis(), i2));
    }

    public DateTime withDayOfWeek(int i2) {
        return withMillis(getChronology().dayOfWeek().set(getMillis(), i2));
    }

    public DateTime withDayOfYear(int i2) {
        return withMillis(getChronology().dayOfYear().set(getMillis(), i2));
    }

    public DateTime withDurationAdded(long j2, int i2) {
        return (j2 == 0 || i2 == 0) ? this : withMillis(getChronology().add(getMillis(), j2, i2));
    }

    public DateTime withEarlierOffsetAtOverlap() {
        return withMillis(getZone().adjustOffset(getMillis(), false));
    }

    public DateTime withEra(int i2) {
        return withMillis(getChronology().era().set(getMillis(), i2));
    }

    public DateTime withField(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType != null) {
            return withMillis(dateTimeFieldType.getField(getChronology()).set(getMillis(), i2));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public DateTime withFieldAdded(DurationFieldType durationFieldType, int i2) {
        if (durationFieldType != null) {
            return i2 == 0 ? this : withMillis(durationFieldType.getField(getChronology()).add(getMillis(), i2));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public DateTime withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : withMillis(getChronology().set(readablePartial, getMillis()));
    }

    public DateTime withHourOfDay(int i2) {
        return withMillis(getChronology().hourOfDay().set(getMillis(), i2));
    }

    public DateTime withLaterOffsetAtOverlap() {
        return withMillis(getZone().adjustOffset(getMillis(), true));
    }

    public DateTime withMillis(long j2) {
        return j2 == getMillis() ? this : new DateTime(j2, getChronology());
    }

    public DateTime withMillisOfDay(int i2) {
        return withMillis(getChronology().millisOfDay().set(getMillis(), i2));
    }

    public DateTime withMillisOfSecond(int i2) {
        return withMillis(getChronology().millisOfSecond().set(getMillis(), i2));
    }

    public DateTime withMinuteOfHour(int i2) {
        return withMillis(getChronology().minuteOfHour().set(getMillis(), i2));
    }

    public DateTime withMonthOfYear(int i2) {
        return withMillis(getChronology().monthOfYear().set(getMillis(), i2));
    }

    public DateTime withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
        return (readablePeriod == null || i2 == 0) ? this : withMillis(getChronology().add(readablePeriod, getMillis(), i2));
    }

    public DateTime withSecondOfMinute(int i2) {
        return withMillis(getChronology().secondOfMinute().set(getMillis(), i2));
    }

    public DateTime withTime(int i2, int i3, int i4, int i5) {
        Chronology chronology = getChronology();
        return withMillis(chronology.millisOfSecond().set(chronology.secondOfMinute().set(chronology.minuteOfHour().set(chronology.hourOfDay().set(getMillis(), i2), i3), i4), i5));
    }

    public DateTime withTimeAtStartOfDay() {
        return toLocalDate().toDateTimeAtStartOfDay(getZone());
    }

    public DateTime withWeekOfWeekyear(int i2) {
        return withMillis(getChronology().weekOfWeekyear().set(getMillis(), i2));
    }

    public DateTime withWeekyear(int i2) {
        return withMillis(getChronology().weekyear().set(getMillis(), i2));
    }

    public DateTime withYear(int i2) {
        return withMillis(getChronology().year().set(getMillis(), i2));
    }

    public DateTime withYearOfCentury(int i2) {
        return withMillis(getChronology().yearOfCentury().set(getMillis(), i2));
    }

    public DateTime withYearOfEra(int i2) {
        return withMillis(getChronology().yearOfEra().set(getMillis(), i2));
    }

    public DateTime withZone(DateTimeZone dateTimeZone) {
        return withChronology(getChronology().withZone(dateTimeZone));
    }

    public DateTime withZoneRetainFields(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        DateTimeZone zone2 = DateTimeUtils.getZone(getZone());
        return zone == zone2 ? this : new DateTime(zone2.getMillisKeepLocal(zone, getMillis()), getChronology().withZone(zone));
    }

    public Property year() {
        return new Property(this, getChronology().year());
    }

    public Property yearOfCentury() {
        return new Property(this, getChronology().yearOfCentury());
    }

    public Property yearOfEra() {
        return new Property(this, getChronology().yearOfEra());
    }

    public DateTime(DateTimeZone dateTimeZone) {
        super(dateTimeZone);
    }

    public static DateTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new DateTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static DateTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseDateTime(str);
    }

    public DateTime minus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, -1);
    }

    public DateTime plus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, 1);
    }

    @Override // org.joda.time.base.AbstractInstant
    public DateTime toDateTime(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        return getZone() == zone ? this : super.toDateTime(zone);
    }

    public DateTime(Chronology chronology) {
        super(chronology);
    }

    public DateTime minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public DateTime plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public DateTime withDurationAdded(ReadableDuration readableDuration, int i2) {
        return (readableDuration == null || i2 == 0) ? this : withDurationAdded(readableDuration.getMillis(), i2);
    }

    public DateTime(long j2) {
        super(j2);
    }

    public static DateTime now(Chronology chronology) {
        if (chronology != null) {
            return new DateTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    public DateTime(long j2, DateTimeZone dateTimeZone) {
        super(j2, dateTimeZone);
    }

    @Override // org.joda.time.base.AbstractInstant
    public DateTime toDateTime(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        return getChronology() == chronology2 ? this : super.toDateTime(chronology2);
    }

    public DateTime(long j2, Chronology chronology) {
        super(j2, chronology);
    }

    public DateTime(Object obj) {
        super(obj, (Chronology) null);
    }

    public DateTime(Object obj, DateTimeZone dateTimeZone) {
        super(obj, dateTimeZone);
    }

    public DateTime(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology));
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6) {
        super(i2, i3, i4, i5, i6, 0, 0);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, DateTimeZone dateTimeZone) {
        super(i2, i3, i4, i5, i6, 0, 0, dateTimeZone);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, Chronology chronology) {
        super(i2, i3, i4, i5, i6, 0, 0, chronology);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7) {
        super(i2, i3, i4, i5, i6, i7, 0);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7, DateTimeZone dateTimeZone) {
        super(i2, i3, i4, i5, i6, i7, 0, dateTimeZone);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7, Chronology chronology) {
        super(i2, i3, i4, i5, i6, i7, 0, chronology);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        super(i2, i3, i4, i5, i6, i7, i8);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8, DateTimeZone dateTimeZone) {
        super(i2, i3, i4, i5, i6, i7, i8, dateTimeZone);
    }

    public DateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8, Chronology chronology) {
        super(i2, i3, i4, i5, i6, i7, i8, chronology);
    }
}
