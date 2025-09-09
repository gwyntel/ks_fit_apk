package org.joda.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BasePartial;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractPartialFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class YearMonth extends BasePartial implements ReadablePartial, Serializable {
    private static final DateTimeFieldType[] FIELD_TYPES = {DateTimeFieldType.year(), DateTimeFieldType.monthOfYear()};
    public static final int MONTH_OF_YEAR = 1;
    public static final int YEAR = 0;
    private static final long serialVersionUID = 797544782896179L;

    public YearMonth() {
    }

    public static YearMonth fromCalendarFields(Calendar calendar) {
        if (calendar != null) {
            return new YearMonth(calendar.get(1), calendar.get(2) + 1);
        }
        throw new IllegalArgumentException("The calendar must not be null");
    }

    public static YearMonth fromDateFields(Date date) {
        if (date != null) {
            return new YearMonth(date.getYear() + 1900, date.getMonth() + 1);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static YearMonth now() {
        return new YearMonth();
    }

    @FromString
    public static YearMonth parse(String str) {
        return parse(str, ISODateTimeFormat.localDateParser());
    }

    private Object readResolve() {
        return !DateTimeZone.UTC.equals(getChronology().getZone()) ? new YearMonth(this, getChronology().withUTC()) : this;
    }

    @Override // org.joda.time.base.AbstractPartial
    protected DateTimeField getField(int i2, Chronology chronology) {
        if (i2 == 0) {
            return chronology.year();
        }
        if (i2 == 1) {
            return chronology.monthOfYear();
        }
        throw new IndexOutOfBoundsException("Invalid index: " + i2);
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public DateTimeFieldType getFieldType(int i2) {
        return FIELD_TYPES[i2];
    }

    @Override // org.joda.time.base.AbstractPartial
    public DateTimeFieldType[] getFieldTypes() {
        return (DateTimeFieldType[]) FIELD_TYPES.clone();
    }

    public int getMonthOfYear() {
        return getValue(1);
    }

    public int getYear() {
        return getValue(0);
    }

    public YearMonth minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public YearMonth minusMonths(int i2) {
        return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(i2));
    }

    public YearMonth minusYears(int i2) {
        return withFieldAdded(DurationFieldType.years(), FieldUtils.safeNegate(i2));
    }

    public Property monthOfYear() {
        return new Property(this, 1);
    }

    public YearMonth plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public YearMonth plusMonths(int i2) {
        return withFieldAdded(DurationFieldType.months(), i2);
    }

    public YearMonth plusYears(int i2) {
        return withFieldAdded(DurationFieldType.years(), i2);
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    @Override // org.joda.time.ReadablePartial
    public int size() {
        return 2;
    }

    public Interval toInterval() {
        return toInterval(null);
    }

    public LocalDate toLocalDate(int i2) {
        return new LocalDate(getYear(), getMonthOfYear(), i2, getChronology());
    }

    @Override // org.joda.time.ReadablePartial
    @ToString
    public String toString() {
        return ISODateTimeFormat.yearMonth().print(this);
    }

    public YearMonth withChronologyRetainFields(Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (chronologyWithUTC == getChronology()) {
            return this;
        }
        YearMonth yearMonth = new YearMonth(this, chronologyWithUTC);
        chronologyWithUTC.validate(yearMonth, getValues());
        return yearMonth;
    }

    public YearMonth withField(DateTimeFieldType dateTimeFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i2 == getValue(iIndexOfSupported)) {
            return this;
        }
        return new YearMonth(this, getField(iIndexOfSupported).set(this, iIndexOfSupported, getValues(), i2));
    }

    public YearMonth withFieldAdded(DurationFieldType durationFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(durationFieldType);
        if (i2 == 0) {
            return this;
        }
        return new YearMonth(this, getField(iIndexOfSupported).add(this, iIndexOfSupported, getValues(), i2));
    }

    public YearMonth withMonthOfYear(int i2) {
        return new YearMonth(this, getChronology().monthOfYear().set(this, 1, getValues(), i2));
    }

    public YearMonth withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
        if (readablePeriod == null || i2 == 0) {
            return this;
        }
        int[] values = getValues();
        for (int i3 = 0; i3 < readablePeriod.size(); i3++) {
            int iIndexOf = indexOf(readablePeriod.getFieldType(i3));
            if (iIndexOf >= 0) {
                values = getField(iIndexOf).add(this, iIndexOf, values, FieldUtils.safeMultiply(readablePeriod.getValue(i3), i2));
            }
        }
        return new YearMonth(this, values);
    }

    public YearMonth withYear(int i2) {
        return new YearMonth(this, getChronology().year().set(this, 0, getValues(), i2));
    }

    public Property year() {
        return new Property(this, 0);
    }

    public YearMonth(DateTimeZone dateTimeZone) {
        super(ISOChronology.getInstance(dateTimeZone));
    }

    public static YearMonth now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new YearMonth(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static YearMonth parse(String str, DateTimeFormatter dateTimeFormatter) {
        LocalDate localDate = dateTimeFormatter.parseLocalDate(str);
        return new YearMonth(localDate.getYear(), localDate.getMonthOfYear());
    }

    public Interval toInterval(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        return new Interval(toLocalDate(1).toDateTimeAtStartOfDay(zone), plusMonths(1).toLocalDate(1).toDateTimeAtStartOfDay(zone));
    }

    @Override // org.joda.time.base.BasePartial
    public String toString(String str) {
        return str == null ? toString() : DateTimeFormat.forPattern(str).print(this);
    }

    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 5727734012190224363L;
        private final YearMonth iBase;
        private final int iFieldIndex;

        Property(YearMonth yearMonth, int i2) {
            this.iBase = yearMonth;
            this.iFieldIndex = i2;
        }

        public YearMonth addToCopy(int i2) {
            return new YearMonth(this.iBase, getField().add(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        public YearMonth addWrapFieldToCopy(int i2) {
            return new YearMonth(this.iBase, getField().addWrapField(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public int get() {
            return this.iBase.getValue(this.iFieldIndex);
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public DateTimeField getField() {
            return this.iBase.getField(this.iFieldIndex);
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        protected ReadablePartial getReadablePartial() {
            return this.iBase;
        }

        public YearMonth getYearMonth() {
            return this.iBase;
        }

        public YearMonth setCopy(int i2) {
            return new YearMonth(this.iBase, getField().set(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        public YearMonth setCopy(String str, Locale locale) {
            return new YearMonth(this.iBase, getField().set(this.iBase, this.iFieldIndex, this.iBase.getValues(), str, locale));
        }

        public YearMonth setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public YearMonth(Chronology chronology) {
        super(chronology);
    }

    public YearMonth(long j2) {
        super(j2);
    }

    public static YearMonth now(Chronology chronology) {
        if (chronology != null) {
            return new YearMonth(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @Override // org.joda.time.base.BasePartial
    public String toString(String str, Locale locale) throws IllegalArgumentException {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print(this);
    }

    public YearMonth(long j2, Chronology chronology) {
        super(j2, chronology);
    }

    public YearMonth(Object obj) {
        super(obj, null, ISODateTimeFormat.localDateParser());
    }

    public YearMonth(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology), ISODateTimeFormat.localDateParser());
    }

    public YearMonth(int i2, int i3) {
        this(i2, i3, null);
    }

    public YearMonth(int i2, int i3, Chronology chronology) {
        super(new int[]{i2, i3}, chronology);
    }

    YearMonth(YearMonth yearMonth, int[] iArr) {
        super(yearMonth, iArr);
    }

    YearMonth(YearMonth yearMonth, Chronology chronology) {
        super((BasePartial) yearMonth, chronology);
    }
}
