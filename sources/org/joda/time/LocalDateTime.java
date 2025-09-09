package org.joda.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseLocal;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.PartialConverter;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class LocalDateTime extends BaseLocal implements ReadablePartial, Serializable {
    private static final int DAY_OF_MONTH = 2;
    private static final int MILLIS_OF_DAY = 3;
    private static final int MONTH_OF_YEAR = 1;
    private static final int YEAR = 0;
    private static final long serialVersionUID = -268716875315837168L;
    private final Chronology iChronology;
    private final long iLocalMillis;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -358138762846288L;
        private transient DateTimeField iField;
        private transient LocalDateTime iInstant;

        Property(LocalDateTime localDateTime, DateTimeField dateTimeField) {
            this.iInstant = localDateTime;
            this.iField = dateTimeField;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            this.iInstant = (LocalDateTime) objectInputStream.readObject();
            this.iField = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.iInstant.getChronology());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.iInstant);
            objectOutputStream.writeObject(this.iField.getType());
        }

        public LocalDateTime addToCopy(int i2) {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.add(localDateTime.getLocalMillis(), i2));
        }

        public LocalDateTime addWrapFieldToCopy(int i2) {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.addWrapField(localDateTime.getLocalMillis(), i2));
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected Chronology getChronology() {
            return this.iInstant.getChronology();
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        public DateTimeField getField() {
            return this.iField;
        }

        public LocalDateTime getLocalDateTime() {
            return this.iInstant;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected long getMillis() {
            return this.iInstant.getLocalMillis();
        }

        public LocalDateTime roundCeilingCopy() {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.roundCeiling(localDateTime.getLocalMillis()));
        }

        public LocalDateTime roundFloorCopy() {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.roundFloor(localDateTime.getLocalMillis()));
        }

        public LocalDateTime roundHalfCeilingCopy() {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.roundHalfCeiling(localDateTime.getLocalMillis()));
        }

        public LocalDateTime roundHalfEvenCopy() {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.roundHalfEven(localDateTime.getLocalMillis()));
        }

        public LocalDateTime roundHalfFloorCopy() {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.roundHalfFloor(localDateTime.getLocalMillis()));
        }

        public LocalDateTime setCopy(int i2) {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.set(localDateTime.getLocalMillis(), i2));
        }

        public LocalDateTime withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public LocalDateTime withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public LocalDateTime addToCopy(long j2) {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.add(localDateTime.getLocalMillis(), j2));
        }

        public LocalDateTime setCopy(String str, Locale locale) {
            LocalDateTime localDateTime = this.iInstant;
            return localDateTime.withLocalMillis(this.iField.set(localDateTime.getLocalMillis(), str, locale));
        }

        public LocalDateTime setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public LocalDateTime() {
        this(DateTimeUtils.currentTimeMillis(), ISOChronology.getInstance());
    }

    private Date correctDstTransition(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        LocalDateTime localDateTimeFromCalendarFields = fromCalendarFields(calendar);
        if (localDateTimeFromCalendarFields.isBefore(this)) {
            while (localDateTimeFromCalendarFields.isBefore(this)) {
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 60000);
                localDateTimeFromCalendarFields = fromCalendarFields(calendar);
            }
            while (!localDateTimeFromCalendarFields.isBefore(this)) {
                calendar.setTimeInMillis(calendar.getTimeInMillis() - 1000);
                localDateTimeFromCalendarFields = fromCalendarFields(calendar);
            }
            calendar.setTimeInMillis(calendar.getTimeInMillis() + 1000);
        } else if (localDateTimeFromCalendarFields.equals(this)) {
            Calendar calendar2 = Calendar.getInstance(timeZone);
            calendar2.setTimeInMillis(calendar.getTimeInMillis() - timeZone.getDSTSavings());
            if (fromCalendarFields(calendar2).equals(this)) {
                calendar = calendar2;
            }
        }
        return calendar.getTime();
    }

    public static LocalDateTime fromCalendarFields(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("The calendar must not be null");
        }
        int i2 = calendar.get(0);
        int i3 = calendar.get(1);
        if (i2 != 1) {
            i3 = 1 - i3;
        }
        return new LocalDateTime(i3, calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13), calendar.get(14));
    }

    public static LocalDateTime fromDateFields(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date.getTime() >= 0) {
            return new LocalDateTime(date.getYear() + 1900, date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), (((int) (date.getTime() % 1000)) + 1000) % 1000);
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return fromCalendarFields(gregorianCalendar);
    }

    public static LocalDateTime now() {
        return new LocalDateTime();
    }

    @FromString
    public static LocalDateTime parse(String str) {
        return parse(str, ISODateTimeFormat.localDateOptionalTimeParser());
    }

    private Object readResolve() {
        Chronology chronology = this.iChronology;
        return chronology == null ? new LocalDateTime(this.iLocalMillis, ISOChronology.getInstanceUTC()) : !DateTimeZone.UTC.equals(chronology.getZone()) ? new LocalDateTime(this.iLocalMillis, this.iChronology.withUTC()) : this;
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

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) obj;
            if (this.iChronology.equals(localDateTime.iChronology)) {
                return this.iLocalMillis == localDateTime.iLocalMillis;
            }
        }
        return super.equals(obj);
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public int get(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return dateTimeFieldType.getField(getChronology()).get(getLocalMillis());
        }
        throw new IllegalArgumentException("The DateTimeFieldType must not be null");
    }

    public int getCenturyOfEra() {
        return getChronology().centuryOfEra().get(getLocalMillis());
    }

    @Override // org.joda.time.ReadablePartial
    public Chronology getChronology() {
        return this.iChronology;
    }

    public int getDayOfMonth() {
        return getChronology().dayOfMonth().get(getLocalMillis());
    }

    public int getDayOfWeek() {
        return getChronology().dayOfWeek().get(getLocalMillis());
    }

    public int getDayOfYear() {
        return getChronology().dayOfYear().get(getLocalMillis());
    }

    public int getEra() {
        return getChronology().era().get(getLocalMillis());
    }

    @Override // org.joda.time.base.AbstractPartial
    protected DateTimeField getField(int i2, Chronology chronology) {
        if (i2 == 0) {
            return chronology.year();
        }
        if (i2 == 1) {
            return chronology.monthOfYear();
        }
        if (i2 == 2) {
            return chronology.dayOfMonth();
        }
        if (i2 == 3) {
            return chronology.millisOfDay();
        }
        throw new IndexOutOfBoundsException("Invalid index: " + i2);
    }

    public int getHourOfDay() {
        return getChronology().hourOfDay().get(getLocalMillis());
    }

    @Override // org.joda.time.base.BaseLocal
    protected long getLocalMillis() {
        return this.iLocalMillis;
    }

    public int getMillisOfDay() {
        return getChronology().millisOfDay().get(getLocalMillis());
    }

    public int getMillisOfSecond() {
        return getChronology().millisOfSecond().get(getLocalMillis());
    }

    public int getMinuteOfHour() {
        return getChronology().minuteOfHour().get(getLocalMillis());
    }

    public int getMonthOfYear() {
        return getChronology().monthOfYear().get(getLocalMillis());
    }

    public int getSecondOfMinute() {
        return getChronology().secondOfMinute().get(getLocalMillis());
    }

    @Override // org.joda.time.ReadablePartial
    public int getValue(int i2) {
        if (i2 == 0) {
            return getChronology().year().get(getLocalMillis());
        }
        if (i2 == 1) {
            return getChronology().monthOfYear().get(getLocalMillis());
        }
        if (i2 == 2) {
            return getChronology().dayOfMonth().get(getLocalMillis());
        }
        if (i2 == 3) {
            return getChronology().millisOfDay().get(getLocalMillis());
        }
        throw new IndexOutOfBoundsException("Invalid index: " + i2);
    }

    public int getWeekOfWeekyear() {
        return getChronology().weekOfWeekyear().get(getLocalMillis());
    }

    public int getWeekyear() {
        return getChronology().weekyear().get(getLocalMillis());
    }

    public int getYear() {
        return getChronology().year().get(getLocalMillis());
    }

    public int getYearOfCentury() {
        return getChronology().yearOfCentury().get(getLocalMillis());
    }

    public int getYearOfEra() {
        return getChronology().yearOfEra().get(getLocalMillis());
    }

    public Property hourOfDay() {
        return new Property(this, getChronology().hourOfDay());
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public boolean isSupported(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            return false;
        }
        return dateTimeFieldType.getField(getChronology()).isSupported();
    }

    public Property millisOfDay() {
        return new Property(this, getChronology().millisOfDay());
    }

    public Property millisOfSecond() {
        return new Property(this, getChronology().millisOfSecond());
    }

    public LocalDateTime minus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, -1);
    }

    public LocalDateTime minusDays(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().days().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusHours(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().hours().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusMillis(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().millis().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusMinutes(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().minutes().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusMonths(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().months().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusSeconds(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().seconds().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusWeeks(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().weeks().subtract(getLocalMillis(), i2));
    }

    public LocalDateTime minusYears(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().years().subtract(getLocalMillis(), i2));
    }

    public Property minuteOfHour() {
        return new Property(this, getChronology().minuteOfHour());
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public LocalDateTime plus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, 1);
    }

    public LocalDateTime plusDays(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().days().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusHours(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().hours().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusMillis(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().millis().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusMinutes(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().minutes().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusMonths(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().months().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusSeconds(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().seconds().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusWeeks(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().weeks().add(getLocalMillis(), i2));
    }

    public LocalDateTime plusYears(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().years().add(getLocalMillis(), i2));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        if (isSupported(dateTimeFieldType)) {
            return new Property(this, dateTimeFieldType.getField(getChronology()));
        }
        throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
    }

    public Property secondOfMinute() {
        return new Property(this, getChronology().secondOfMinute());
    }

    @Override // org.joda.time.ReadablePartial
    public int size() {
        return 4;
    }

    public Date toDate() {
        Date date = new Date(getYear() - 1900, getMonthOfYear() - 1, getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute());
        date.setTime(date.getTime() + getMillisOfSecond());
        return correctDstTransition(date, TimeZone.getDefault());
    }

    public DateTime toDateTime() {
        return toDateTime((DateTimeZone) null);
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getLocalMillis(), getChronology());
    }

    public LocalTime toLocalTime() {
        return new LocalTime(getLocalMillis(), getChronology());
    }

    @Override // org.joda.time.ReadablePartial
    @ToString
    public String toString() {
        return ISODateTimeFormat.dateTime().print(this);
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public LocalDateTime withCenturyOfEra(int i2) {
        return withLocalMillis(getChronology().centuryOfEra().set(getLocalMillis(), i2));
    }

    public LocalDateTime withDate(int i2, int i3, int i4) {
        Chronology chronology = getChronology();
        return withLocalMillis(chronology.dayOfMonth().set(chronology.monthOfYear().set(chronology.year().set(getLocalMillis(), i2), i3), i4));
    }

    public LocalDateTime withDayOfMonth(int i2) {
        return withLocalMillis(getChronology().dayOfMonth().set(getLocalMillis(), i2));
    }

    public LocalDateTime withDayOfWeek(int i2) {
        return withLocalMillis(getChronology().dayOfWeek().set(getLocalMillis(), i2));
    }

    public LocalDateTime withDayOfYear(int i2) {
        return withLocalMillis(getChronology().dayOfYear().set(getLocalMillis(), i2));
    }

    public LocalDateTime withDurationAdded(ReadableDuration readableDuration, int i2) {
        return (readableDuration == null || i2 == 0) ? this : withLocalMillis(getChronology().add(getLocalMillis(), readableDuration.getMillis(), i2));
    }

    public LocalDateTime withEra(int i2) {
        return withLocalMillis(getChronology().era().set(getLocalMillis(), i2));
    }

    public LocalDateTime withField(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType != null) {
            return withLocalMillis(dateTimeFieldType.getField(getChronology()).set(getLocalMillis(), i2));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public LocalDateTime withFieldAdded(DurationFieldType durationFieldType, int i2) {
        if (durationFieldType != null) {
            return i2 == 0 ? this : withLocalMillis(durationFieldType.getField(getChronology()).add(getLocalMillis(), i2));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public LocalDateTime withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : withLocalMillis(getChronology().set(readablePartial, getLocalMillis()));
    }

    public LocalDateTime withHourOfDay(int i2) {
        return withLocalMillis(getChronology().hourOfDay().set(getLocalMillis(), i2));
    }

    LocalDateTime withLocalMillis(long j2) {
        return j2 == getLocalMillis() ? this : new LocalDateTime(j2, getChronology());
    }

    public LocalDateTime withMillisOfDay(int i2) {
        return withLocalMillis(getChronology().millisOfDay().set(getLocalMillis(), i2));
    }

    public LocalDateTime withMillisOfSecond(int i2) {
        return withLocalMillis(getChronology().millisOfSecond().set(getLocalMillis(), i2));
    }

    public LocalDateTime withMinuteOfHour(int i2) {
        return withLocalMillis(getChronology().minuteOfHour().set(getLocalMillis(), i2));
    }

    public LocalDateTime withMonthOfYear(int i2) {
        return withLocalMillis(getChronology().monthOfYear().set(getLocalMillis(), i2));
    }

    public LocalDateTime withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
        return (readablePeriod == null || i2 == 0) ? this : withLocalMillis(getChronology().add(readablePeriod, getLocalMillis(), i2));
    }

    public LocalDateTime withSecondOfMinute(int i2) {
        return withLocalMillis(getChronology().secondOfMinute().set(getLocalMillis(), i2));
    }

    public LocalDateTime withTime(int i2, int i3, int i4, int i5) {
        Chronology chronology = getChronology();
        return withLocalMillis(chronology.millisOfSecond().set(chronology.secondOfMinute().set(chronology.minuteOfHour().set(chronology.hourOfDay().set(getLocalMillis(), i2), i3), i4), i5));
    }

    public LocalDateTime withWeekOfWeekyear(int i2) {
        return withLocalMillis(getChronology().weekOfWeekyear().set(getLocalMillis(), i2));
    }

    public LocalDateTime withWeekyear(int i2) {
        return withLocalMillis(getChronology().weekyear().set(getLocalMillis(), i2));
    }

    public LocalDateTime withYear(int i2) {
        return withLocalMillis(getChronology().year().set(getLocalMillis(), i2));
    }

    public LocalDateTime withYearOfCentury(int i2) {
        return withLocalMillis(getChronology().yearOfCentury().set(getLocalMillis(), i2));
    }

    public LocalDateTime withYearOfEra(int i2) {
        return withLocalMillis(getChronology().yearOfEra().set(getLocalMillis(), i2));
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

    public LocalDateTime(DateTimeZone dateTimeZone) {
        this(DateTimeUtils.currentTimeMillis(), ISOChronology.getInstance(dateTimeZone));
    }

    public static LocalDateTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new LocalDateTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static LocalDateTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseLocalDateTime(str);
    }

    @Override // org.joda.time.base.AbstractPartial, java.lang.Comparable
    public int compareTo(ReadablePartial readablePartial) {
        if (this == readablePartial) {
            return 0;
        }
        if (readablePartial instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) readablePartial;
            if (this.iChronology.equals(localDateTime.iChronology)) {
                long j2 = this.iLocalMillis;
                long j3 = localDateTime.iLocalMillis;
                if (j2 < j3) {
                    return -1;
                }
                return j2 == j3 ? 0 : 1;
            }
        }
        return super.compareTo(readablePartial);
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            return false;
        }
        return durationFieldType.getField(getChronology()).isSupported();
    }

    public LocalDateTime minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public LocalDateTime plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public DateTime toDateTime(DateTimeZone dateTimeZone) {
        return new DateTime(getYear(), getMonthOfYear(), getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute(), getMillisOfSecond(), this.iChronology.withZone(DateTimeUtils.getZone(dateTimeZone)));
    }

    public String toString(String str) {
        return str == null ? toString() : DateTimeFormat.forPattern(str).print(this);
    }

    public LocalDateTime(Chronology chronology) {
        this(DateTimeUtils.currentTimeMillis(), chronology);
    }

    public LocalDateTime(long j2) {
        this(j2, ISOChronology.getInstance());
    }

    public static LocalDateTime now(Chronology chronology) {
        if (chronology != null) {
            return new LocalDateTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    public String toString(String str, Locale locale) throws IllegalArgumentException {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print(this);
    }

    public LocalDateTime(long j2, DateTimeZone dateTimeZone) {
        this(j2, ISOChronology.getInstance(dateTimeZone));
    }

    public Date toDate(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.clear();
        calendar.set(getYear(), getMonthOfYear() - 1, getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute());
        Date time = calendar.getTime();
        time.setTime(time.getTime() + getMillisOfSecond());
        return correctDstTransition(time, timeZone);
    }

    public LocalDateTime(long j2, Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.iLocalMillis = chronology2.getZone().getMillisKeepLocal(DateTimeZone.UTC, j2);
        this.iChronology = chronology2.withUTC();
    }

    public LocalDateTime(Object obj) {
        this(obj, (Chronology) null);
    }

    public LocalDateTime(Object obj, DateTimeZone dateTimeZone) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology = DateTimeUtils.getChronology(partialConverter.getChronology(obj, dateTimeZone));
        Chronology chronologyWithUTC = chronology.withUTC();
        this.iChronology = chronologyWithUTC;
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology, ISODateTimeFormat.localDateOptionalTimeParser());
        this.iLocalMillis = chronologyWithUTC.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalDateTime(Object obj, Chronology chronology) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology2 = DateTimeUtils.getChronology(partialConverter.getChronology(obj, chronology));
        Chronology chronologyWithUTC = chronology2.withUTC();
        this.iChronology = chronologyWithUTC;
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology2, ISODateTimeFormat.localDateOptionalTimeParser());
        this.iLocalMillis = chronologyWithUTC.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalDateTime(int i2, int i3, int i4, int i5, int i6) {
        this(i2, i3, i4, i5, i6, 0, 0, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i2, int i3, int i4, int i5, int i6, int i7) {
        this(i2, i3, i4, i5, i6, i7, 0, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this(i2, i3, i4, i5, i6, i7, i8, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8, Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        long dateTimeMillis = chronologyWithUTC.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        this.iChronology = chronologyWithUTC;
        this.iLocalMillis = dateTimeMillis;
    }
}
