package org.joda.time;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class MonthDay extends BasePartial implements ReadablePartial, Serializable {
    public static final int DAY_OF_MONTH = 1;
    public static final int MONTH_OF_YEAR = 0;
    private static final long serialVersionUID = 2954560699050434609L;
    private static final DateTimeFieldType[] FIELD_TYPES = {DateTimeFieldType.monthOfYear(), DateTimeFieldType.dayOfMonth()};
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder().appendOptional(ISODateTimeFormat.localDateParser().getParser()).appendOptional(DateTimeFormat.forPattern("--MM-dd").getParser()).toFormatter();

    public MonthDay() {
    }

    public static MonthDay fromCalendarFields(Calendar calendar) {
        if (calendar != null) {
            return new MonthDay(calendar.get(2) + 1, calendar.get(5));
        }
        throw new IllegalArgumentException("The calendar must not be null");
    }

    public static MonthDay fromDateFields(Date date) {
        if (date != null) {
            return new MonthDay(date.getMonth() + 1, date.getDate());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static MonthDay now() {
        return new MonthDay();
    }

    @FromString
    public static MonthDay parse(String str) {
        return parse(str, PARSER);
    }

    private Object readResolve() {
        return !DateTimeZone.UTC.equals(getChronology().getZone()) ? new MonthDay(this, getChronology().withUTC()) : this;
    }

    public Property dayOfMonth() {
        return new Property(this, 1);
    }

    public int getDayOfMonth() {
        return getValue(1);
    }

    @Override // org.joda.time.base.AbstractPartial
    protected DateTimeField getField(int i2, Chronology chronology) {
        if (i2 == 0) {
            return chronology.monthOfYear();
        }
        if (i2 == 1) {
            return chronology.dayOfMonth();
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
        return getValue(0);
    }

    public MonthDay minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public MonthDay minusDays(int i2) {
        return withFieldAdded(DurationFieldType.days(), FieldUtils.safeNegate(i2));
    }

    public MonthDay minusMonths(int i2) {
        return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(i2));
    }

    public Property monthOfYear() {
        return new Property(this, 0);
    }

    public MonthDay plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public MonthDay plusDays(int i2) {
        return withFieldAdded(DurationFieldType.days(), i2);
    }

    public MonthDay plusMonths(int i2) {
        return withFieldAdded(DurationFieldType.months(), i2);
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    @Override // org.joda.time.ReadablePartial
    public int size() {
        return 2;
    }

    public LocalDate toLocalDate(int i2) {
        return new LocalDate(i2, getMonthOfYear(), getDayOfMonth(), getChronology());
    }

    @Override // org.joda.time.ReadablePartial
    @ToString
    public String toString() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(DateTimeFieldType.monthOfYear());
        arrayList.add(DateTimeFieldType.dayOfMonth());
        return ISODateTimeFormat.forFields(arrayList, true, true).print(this);
    }

    public MonthDay withChronologyRetainFields(Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (chronologyWithUTC == getChronology()) {
            return this;
        }
        MonthDay monthDay = new MonthDay(this, chronologyWithUTC);
        chronologyWithUTC.validate(monthDay, getValues());
        return monthDay;
    }

    public MonthDay withDayOfMonth(int i2) {
        return new MonthDay(this, getChronology().dayOfMonth().set(this, 1, getValues(), i2));
    }

    public MonthDay withField(DateTimeFieldType dateTimeFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i2 == getValue(iIndexOfSupported)) {
            return this;
        }
        return new MonthDay(this, getField(iIndexOfSupported).set(this, iIndexOfSupported, getValues(), i2));
    }

    public MonthDay withFieldAdded(DurationFieldType durationFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(durationFieldType);
        if (i2 == 0) {
            return this;
        }
        return new MonthDay(this, getField(iIndexOfSupported).add(this, iIndexOfSupported, getValues(), i2));
    }

    public MonthDay withMonthOfYear(int i2) {
        return new MonthDay(this, getChronology().monthOfYear().set(this, 0, getValues(), i2));
    }

    public MonthDay withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
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
        return new MonthDay(this, values);
    }

    public MonthDay(DateTimeZone dateTimeZone) {
        super(ISOChronology.getInstance(dateTimeZone));
    }

    public static MonthDay now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new MonthDay(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static MonthDay parse(String str, DateTimeFormatter dateTimeFormatter) {
        LocalDate localDate = dateTimeFormatter.parseLocalDate(str);
        return new MonthDay(localDate.getMonthOfYear(), localDate.getDayOfMonth());
    }

    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 5727734012190224363L;
        private final MonthDay iBase;
        private final int iFieldIndex;

        Property(MonthDay monthDay, int i2) {
            this.iBase = monthDay;
            this.iFieldIndex = i2;
        }

        public MonthDay addToCopy(int i2) {
            return new MonthDay(this.iBase, getField().add(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        public MonthDay addWrapFieldToCopy(int i2) {
            return new MonthDay(this.iBase, getField().addWrapField(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public int get() {
            return this.iBase.getValue(this.iFieldIndex);
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public DateTimeField getField() {
            return this.iBase.getField(this.iFieldIndex);
        }

        public MonthDay getMonthDay() {
            return this.iBase;
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        protected ReadablePartial getReadablePartial() {
            return this.iBase;
        }

        public MonthDay setCopy(int i2) {
            return new MonthDay(this.iBase, getField().set(this.iBase, this.iFieldIndex, this.iBase.getValues(), i2));
        }

        public MonthDay setCopy(String str, Locale locale) {
            return new MonthDay(this.iBase, getField().set(this.iBase, this.iFieldIndex, this.iBase.getValues(), str, locale));
        }

        public MonthDay setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public MonthDay(Chronology chronology) {
        super(chronology);
    }

    public MonthDay(long j2) {
        super(j2);
    }

    public static MonthDay now(Chronology chronology) {
        if (chronology != null) {
            return new MonthDay(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    public MonthDay(long j2, Chronology chronology) {
        super(j2, chronology);
    }

    @Override // org.joda.time.base.BasePartial
    public String toString(String str) {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).print(this);
    }

    public MonthDay(Object obj) {
        super(obj, null, ISODateTimeFormat.localDateParser());
    }

    public MonthDay(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology), ISODateTimeFormat.localDateParser());
    }

    @Override // org.joda.time.base.BasePartial
    public String toString(String str, Locale locale) throws IllegalArgumentException {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print(this);
    }

    public MonthDay(int i2, int i3) {
        this(i2, i3, null);
    }

    public MonthDay(int i2, int i3, Chronology chronology) {
        super(new int[]{i2, i3}, chronology);
    }

    MonthDay(MonthDay monthDay, int[] iArr) {
        super(monthDay, iArr);
    }

    MonthDay(MonthDay monthDay, Chronology chronology) {
        super((BasePartial) monthDay, chronology);
    }
}
