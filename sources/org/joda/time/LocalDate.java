package org.joda.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseLocal;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.PartialConverter;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class LocalDate extends BaseLocal implements ReadablePartial, Serializable {
    private static final Set<DurationFieldType> DATE_DURATION_TYPES;
    private static final int DAY_OF_MONTH = 2;
    private static final int MONTH_OF_YEAR = 1;
    private static final int YEAR = 0;
    private static final long serialVersionUID = -8775358157899L;
    private final Chronology iChronology;
    private volatile transient int iHash;
    private final long iLocalMillis;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -3193829732634L;
        private transient DateTimeField iField;
        private transient LocalDate iInstant;

        Property(LocalDate localDate, DateTimeField dateTimeField) {
            this.iInstant = localDate;
            this.iField = dateTimeField;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            this.iInstant = (LocalDate) objectInputStream.readObject();
            this.iField = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.iInstant.getChronology());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.iInstant);
            objectOutputStream.writeObject(this.iField.getType());
        }

        public LocalDate addToCopy(int i2) {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.add(localDate.getLocalMillis(), i2));
        }

        public LocalDate addWrapFieldToCopy(int i2) {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.addWrapField(localDate.getLocalMillis(), i2));
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected Chronology getChronology() {
            return this.iInstant.getChronology();
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        public DateTimeField getField() {
            return this.iField;
        }

        public LocalDate getLocalDate() {
            return this.iInstant;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected long getMillis() {
            return this.iInstant.getLocalMillis();
        }

        public LocalDate roundCeilingCopy() {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.roundCeiling(localDate.getLocalMillis()));
        }

        public LocalDate roundFloorCopy() {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.roundFloor(localDate.getLocalMillis()));
        }

        public LocalDate roundHalfCeilingCopy() {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.roundHalfCeiling(localDate.getLocalMillis()));
        }

        public LocalDate roundHalfEvenCopy() {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.roundHalfEven(localDate.getLocalMillis()));
        }

        public LocalDate roundHalfFloorCopy() {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.roundHalfFloor(localDate.getLocalMillis()));
        }

        public LocalDate setCopy(int i2) {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.set(localDate.getLocalMillis(), i2));
        }

        public LocalDate withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public LocalDate withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public LocalDate setCopy(String str, Locale locale) {
            LocalDate localDate = this.iInstant;
            return localDate.withLocalMillis(this.iField.set(localDate.getLocalMillis(), str, locale));
        }

        public LocalDate setCopy(String str) {
            return setCopy(str, null);
        }
    }

    static {
        HashSet hashSet = new HashSet();
        DATE_DURATION_TYPES = hashSet;
        hashSet.add(DurationFieldType.days());
        hashSet.add(DurationFieldType.weeks());
        hashSet.add(DurationFieldType.months());
        hashSet.add(DurationFieldType.weekyears());
        hashSet.add(DurationFieldType.years());
        hashSet.add(DurationFieldType.centuries());
        hashSet.add(DurationFieldType.eras());
    }

    public LocalDate() {
        this(DateTimeUtils.currentTimeMillis(), ISOChronology.getInstance());
    }

    public static LocalDate fromCalendarFields(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("The calendar must not be null");
        }
        int i2 = calendar.get(0);
        int i3 = calendar.get(1);
        if (i2 != 1) {
            i3 = 1 - i3;
        }
        return new LocalDate(i3, calendar.get(2) + 1, calendar.get(5));
    }

    public static LocalDate fromDateFields(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date.getTime() >= 0) {
            return new LocalDate(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return fromCalendarFields(gregorianCalendar);
    }

    public static LocalDate now() {
        return new LocalDate();
    }

    @FromString
    public static LocalDate parse(String str) {
        return parse(str, ISODateTimeFormat.localDateParser());
    }

    private Object readResolve() {
        Chronology chronology = this.iChronology;
        return chronology == null ? new LocalDate(this.iLocalMillis, ISOChronology.getInstanceUTC()) : !DateTimeZone.UTC.equals(chronology.getZone()) ? new LocalDate(this.iLocalMillis, this.iChronology.withUTC()) : this;
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
        if (obj instanceof LocalDate) {
            LocalDate localDate = (LocalDate) obj;
            if (this.iChronology.equals(localDate.iChronology)) {
                return this.iLocalMillis == localDate.iLocalMillis;
            }
        }
        return super.equals(obj);
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public int get(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        if (isSupported(dateTimeFieldType)) {
            return dateTimeFieldType.getField(getChronology()).get(getLocalMillis());
        }
        throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
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
        throw new IndexOutOfBoundsException("Invalid index: " + i2);
    }

    @Override // org.joda.time.base.BaseLocal
    protected long getLocalMillis() {
        return this.iLocalMillis;
    }

    public int getMonthOfYear() {
        return getChronology().monthOfYear().get(getLocalMillis());
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

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public int hashCode() {
        int i2 = this.iHash;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = super.hashCode();
        this.iHash = iHashCode;
        return iHashCode;
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public boolean isSupported(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            return false;
        }
        DurationFieldType durationType = dateTimeFieldType.getDurationType();
        if (DATE_DURATION_TYPES.contains(durationType) || durationType.getField(getChronology()).getUnitMillis() >= getChronology().days().getUnitMillis()) {
            return dateTimeFieldType.getField(getChronology()).isSupported();
        }
        return false;
    }

    public LocalDate minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public LocalDate minusDays(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().days().subtract(getLocalMillis(), i2));
    }

    public LocalDate minusMonths(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().months().subtract(getLocalMillis(), i2));
    }

    public LocalDate minusWeeks(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().weeks().subtract(getLocalMillis(), i2));
    }

    public LocalDate minusYears(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().years().subtract(getLocalMillis(), i2));
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public LocalDate plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public LocalDate plusDays(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().days().add(getLocalMillis(), i2));
    }

    public LocalDate plusMonths(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().months().add(getLocalMillis(), i2));
    }

    public LocalDate plusWeeks(int i2) {
        return i2 == 0 ? this : withLocalMillis(getChronology().weeks().add(getLocalMillis(), i2));
    }

    public LocalDate plusYears(int i2) {
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

    @Override // org.joda.time.ReadablePartial
    public int size() {
        return 3;
    }

    public Date toDate() {
        int dayOfMonth = getDayOfMonth();
        Date date = new Date(getYear() - 1900, getMonthOfYear() - 1, dayOfMonth);
        LocalDate localDateFromDateFields = fromDateFields(date);
        if (!localDateFromDateFields.isBefore(this)) {
            if (!localDateFromDateFields.equals(this)) {
                return date;
            }
            Date date2 = new Date(date.getTime() - TimeZone.getDefault().getDSTSavings());
            return date2.getDate() == dayOfMonth ? date2 : date;
        }
        while (!localDateFromDateFields.equals(this)) {
            date.setTime(date.getTime() + 3600000);
            localDateFromDateFields = fromDateFields(date);
        }
        while (date.getDate() == dayOfMonth) {
            date.setTime(date.getTime() - 1000);
        }
        date.setTime(date.getTime() + 1000);
        return date;
    }

    @Deprecated
    public DateMidnight toDateMidnight() {
        return toDateMidnight(null);
    }

    public DateTime toDateTime(LocalTime localTime) {
        return toDateTime(localTime, null);
    }

    public DateTime toDateTimeAtCurrentTime() {
        return toDateTimeAtCurrentTime(null);
    }

    @Deprecated
    public DateTime toDateTimeAtMidnight() {
        return toDateTimeAtMidnight(null);
    }

    public DateTime toDateTimeAtStartOfDay() {
        return toDateTimeAtStartOfDay(null);
    }

    public Interval toInterval() {
        return toInterval(null);
    }

    public LocalDateTime toLocalDateTime(LocalTime localTime) {
        if (localTime == null) {
            throw new IllegalArgumentException("The time must not be null");
        }
        if (getChronology() == localTime.getChronology()) {
            return new LocalDateTime(getLocalMillis() + localTime.getLocalMillis(), getChronology());
        }
        throw new IllegalArgumentException("The chronology of the time does not match");
    }

    @Override // org.joda.time.ReadablePartial
    @ToString
    public String toString() {
        return ISODateTimeFormat.date().print(this);
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public LocalDate withCenturyOfEra(int i2) {
        return withLocalMillis(getChronology().centuryOfEra().set(getLocalMillis(), i2));
    }

    public LocalDate withDayOfMonth(int i2) {
        return withLocalMillis(getChronology().dayOfMonth().set(getLocalMillis(), i2));
    }

    public LocalDate withDayOfWeek(int i2) {
        return withLocalMillis(getChronology().dayOfWeek().set(getLocalMillis(), i2));
    }

    public LocalDate withDayOfYear(int i2) {
        return withLocalMillis(getChronology().dayOfYear().set(getLocalMillis(), i2));
    }

    public LocalDate withEra(int i2) {
        return withLocalMillis(getChronology().era().set(getLocalMillis(), i2));
    }

    public LocalDate withField(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        }
        if (isSupported(dateTimeFieldType)) {
            return withLocalMillis(dateTimeFieldType.getField(getChronology()).set(getLocalMillis(), i2));
        }
        throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
    }

    public LocalDate withFieldAdded(DurationFieldType durationFieldType, int i2) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        }
        if (isSupported(durationFieldType)) {
            return i2 == 0 ? this : withLocalMillis(durationFieldType.getField(getChronology()).add(getLocalMillis(), i2));
        }
        throw new IllegalArgumentException("Field '" + durationFieldType + "' is not supported");
    }

    public LocalDate withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : withLocalMillis(getChronology().set(readablePartial, getLocalMillis()));
    }

    LocalDate withLocalMillis(long j2) {
        long jRoundFloor = this.iChronology.dayOfMonth().roundFloor(j2);
        return jRoundFloor == getLocalMillis() ? this : new LocalDate(jRoundFloor, getChronology());
    }

    public LocalDate withMonthOfYear(int i2) {
        return withLocalMillis(getChronology().monthOfYear().set(getLocalMillis(), i2));
    }

    public LocalDate withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
        if (readablePeriod == null || i2 == 0) {
            return this;
        }
        long localMillis = getLocalMillis();
        Chronology chronology = getChronology();
        for (int i3 = 0; i3 < readablePeriod.size(); i3++) {
            long jSafeMultiply = FieldUtils.safeMultiply(readablePeriod.getValue(i3), i2);
            DurationFieldType fieldType = readablePeriod.getFieldType(i3);
            if (isSupported(fieldType)) {
                localMillis = fieldType.getField(chronology).add(localMillis, jSafeMultiply);
            }
        }
        return withLocalMillis(localMillis);
    }

    public LocalDate withWeekOfWeekyear(int i2) {
        return withLocalMillis(getChronology().weekOfWeekyear().set(getLocalMillis(), i2));
    }

    public LocalDate withWeekyear(int i2) {
        return withLocalMillis(getChronology().weekyear().set(getLocalMillis(), i2));
    }

    public LocalDate withYear(int i2) {
        return withLocalMillis(getChronology().year().set(getLocalMillis(), i2));
    }

    public LocalDate withYearOfCentury(int i2) {
        return withLocalMillis(getChronology().yearOfCentury().set(getLocalMillis(), i2));
    }

    public LocalDate withYearOfEra(int i2) {
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

    public LocalDate(DateTimeZone dateTimeZone) {
        this(DateTimeUtils.currentTimeMillis(), ISOChronology.getInstance(dateTimeZone));
    }

    public static LocalDate now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new LocalDate(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static LocalDate parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseLocalDate(str);
    }

    @Override // org.joda.time.base.AbstractPartial, java.lang.Comparable
    public int compareTo(ReadablePartial readablePartial) {
        if (this == readablePartial) {
            return 0;
        }
        if (readablePartial instanceof LocalDate) {
            LocalDate localDate = (LocalDate) readablePartial;
            if (this.iChronology.equals(localDate.iChronology)) {
                long j2 = this.iLocalMillis;
                long j3 = localDate.iLocalMillis;
                if (j2 < j3) {
                    return -1;
                }
                return j2 == j3 ? 0 : 1;
            }
        }
        return super.compareTo(readablePartial);
    }

    @Deprecated
    public DateMidnight toDateMidnight(DateTimeZone dateTimeZone) {
        return new DateMidnight(getYear(), getMonthOfYear(), getDayOfMonth(), getChronology().withZone(DateTimeUtils.getZone(dateTimeZone)));
    }

    public DateTime toDateTime(LocalTime localTime, DateTimeZone dateTimeZone) {
        if (localTime != null && getChronology() != localTime.getChronology()) {
            throw new IllegalArgumentException("The chronology of the time does not match");
        }
        Chronology chronologyWithZone = getChronology().withZone(dateTimeZone);
        long j2 = chronologyWithZone.set(this, DateTimeUtils.currentTimeMillis());
        if (localTime != null) {
            j2 = chronologyWithZone.set(localTime, j2);
        }
        return new DateTime(j2, chronologyWithZone);
    }

    public DateTime toDateTimeAtCurrentTime(DateTimeZone dateTimeZone) {
        Chronology chronologyWithZone = getChronology().withZone(DateTimeUtils.getZone(dateTimeZone));
        return new DateTime(chronologyWithZone.set(this, DateTimeUtils.currentTimeMillis()), chronologyWithZone);
    }

    @Deprecated
    public DateTime toDateTimeAtMidnight(DateTimeZone dateTimeZone) {
        return new DateTime(getYear(), getMonthOfYear(), getDayOfMonth(), 0, 0, 0, 0, getChronology().withZone(DateTimeUtils.getZone(dateTimeZone)));
    }

    public DateTime toDateTimeAtStartOfDay(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        Chronology chronologyWithZone = getChronology().withZone(zone);
        return new DateTime(chronologyWithZone.dayOfMonth().roundFloor(zone.convertLocalToUTC(getLocalMillis() + 21600000, false)), chronologyWithZone);
    }

    public Interval toInterval(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        return new Interval(toDateTimeAtStartOfDay(zone), plusDays(1).toDateTimeAtStartOfDay(zone));
    }

    public String toString(String str) {
        return str == null ? toString() : DateTimeFormat.forPattern(str).print(this);
    }

    public LocalDate(Chronology chronology) {
        this(DateTimeUtils.currentTimeMillis(), chronology);
    }

    public LocalDate(long j2) {
        this(j2, ISOChronology.getInstance());
    }

    public static LocalDate now(Chronology chronology) {
        if (chronology != null) {
            return new LocalDate(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            return false;
        }
        DurationField field = durationFieldType.getField(getChronology());
        if (DATE_DURATION_TYPES.contains(durationFieldType) || field.getUnitMillis() >= getChronology().days().getUnitMillis()) {
            return field.isSupported();
        }
        return false;
    }

    public String toString(String str, Locale locale) throws IllegalArgumentException {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print(this);
    }

    public LocalDate(long j2, DateTimeZone dateTimeZone) {
        this(j2, ISOChronology.getInstance(dateTimeZone));
    }

    public LocalDate(long j2, Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        long millisKeepLocal = chronology2.getZone().getMillisKeepLocal(DateTimeZone.UTC, j2);
        Chronology chronologyWithUTC = chronology2.withUTC();
        this.iLocalMillis = chronologyWithUTC.dayOfMonth().roundFloor(millisKeepLocal);
        this.iChronology = chronologyWithUTC;
    }

    public LocalDate(Object obj) {
        this(obj, (Chronology) null);
    }

    public LocalDate(Object obj, DateTimeZone dateTimeZone) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology = DateTimeUtils.getChronology(partialConverter.getChronology(obj, dateTimeZone));
        Chronology chronologyWithUTC = chronology.withUTC();
        this.iChronology = chronologyWithUTC;
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology, ISODateTimeFormat.localDateParser());
        this.iLocalMillis = chronologyWithUTC.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], 0);
    }

    public LocalDate(Object obj, Chronology chronology) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology2 = DateTimeUtils.getChronology(partialConverter.getChronology(obj, chronology));
        Chronology chronologyWithUTC = chronology2.withUTC();
        this.iChronology = chronologyWithUTC;
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology2, ISODateTimeFormat.localDateParser());
        this.iLocalMillis = chronologyWithUTC.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], 0);
    }

    public LocalDate(int i2, int i3, int i4) {
        this(i2, i3, i4, ISOChronology.getInstanceUTC());
    }

    public LocalDate(int i2, int i3, int i4, Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        long dateTimeMillis = chronologyWithUTC.getDateTimeMillis(i2, i3, i4, 0);
        this.iChronology = chronologyWithUTC;
        this.iLocalMillis = dateTimeMillis;
    }
}
