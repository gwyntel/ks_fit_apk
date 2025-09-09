package org.joda.time.chrono;

import com.heytap.mcssdk.constant.Constants;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.ZeroIsMaxDateTimeField;

/* loaded from: classes5.dex */
abstract class BasicChronology extends AssembledChronology {
    private static final int CACHE_MASK = 1023;
    private static final int CACHE_SIZE = 1024;
    private static final DateTimeField cClockhourOfDayField;
    private static final DateTimeField cClockhourOfHalfdayField;
    private static final DurationField cDaysField;
    private static final DateTimeField cHalfdayOfDayField;
    private static final DurationField cHalfdaysField;
    private static final DateTimeField cHourOfDayField;
    private static final DateTimeField cHourOfHalfdayField;
    private static final DurationField cHoursField;
    private static final DurationField cMillisField;
    private static final DateTimeField cMillisOfDayField;
    private static final DateTimeField cMillisOfSecondField;
    private static final DateTimeField cMinuteOfDayField;
    private static final DateTimeField cMinuteOfHourField;
    private static final DurationField cMinutesField;
    private static final DateTimeField cSecondOfDayField;
    private static final DateTimeField cSecondOfMinuteField;
    private static final DurationField cSecondsField;
    private static final DurationField cWeeksField;
    private static final long serialVersionUID = 8283225332206808863L;
    private final int iMinDaysInFirstWeek;
    private final transient YearInfo[] iYearInfoCache;

    private static class HalfdayField extends PreciseDateTimeField {
        private static final long serialVersionUID = 581601443656929254L;

        HalfdayField() {
            super(DateTimeFieldType.halfdayOfDay(), BasicChronology.cHalfdaysField, BasicChronology.cDaysField);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(int i2, Locale locale) {
            return GJLocaleSymbols.forLocale(locale).halfdayValueToText(i2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumTextLength(Locale locale) {
            return GJLocaleSymbols.forLocale(locale).getHalfdayMaxTextLength();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, String str, Locale locale) {
            return set(j2, GJLocaleSymbols.forLocale(locale).halfdayTextToValue(str));
        }
    }

    private static class YearInfo {
        public final long iFirstDayMillis;
        public final int iYear;

        YearInfo(int i2, long j2) {
            this.iYear = i2;
            this.iFirstDayMillis = j2;
        }
    }

    static {
        DurationField durationField = MillisDurationField.INSTANCE;
        cMillisField = durationField;
        PreciseDurationField preciseDurationField = new PreciseDurationField(DurationFieldType.seconds(), 1000L);
        cSecondsField = preciseDurationField;
        PreciseDurationField preciseDurationField2 = new PreciseDurationField(DurationFieldType.minutes(), 60000L);
        cMinutesField = preciseDurationField2;
        PreciseDurationField preciseDurationField3 = new PreciseDurationField(DurationFieldType.hours(), 3600000L);
        cHoursField = preciseDurationField3;
        PreciseDurationField preciseDurationField4 = new PreciseDurationField(DurationFieldType.halfdays(), Constants.MILLS_OF_LAUNCH_INTERVAL);
        cHalfdaysField = preciseDurationField4;
        PreciseDurationField preciseDurationField5 = new PreciseDurationField(DurationFieldType.days(), 86400000L);
        cDaysField = preciseDurationField5;
        cWeeksField = new PreciseDurationField(DurationFieldType.weeks(), 604800000L);
        cMillisOfSecondField = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), durationField, preciseDurationField);
        cMillisOfDayField = new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), durationField, preciseDurationField5);
        cSecondOfMinuteField = new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), preciseDurationField, preciseDurationField2);
        cSecondOfDayField = new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), preciseDurationField, preciseDurationField5);
        cMinuteOfHourField = new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), preciseDurationField2, preciseDurationField3);
        cMinuteOfDayField = new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), preciseDurationField2, preciseDurationField5);
        PreciseDateTimeField preciseDateTimeField = new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), preciseDurationField3, preciseDurationField5);
        cHourOfDayField = preciseDateTimeField;
        PreciseDateTimeField preciseDateTimeField2 = new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), preciseDurationField3, preciseDurationField4);
        cHourOfHalfdayField = preciseDateTimeField2;
        cClockhourOfDayField = new ZeroIsMaxDateTimeField(preciseDateTimeField, DateTimeFieldType.clockhourOfDay());
        cClockhourOfHalfdayField = new ZeroIsMaxDateTimeField(preciseDateTimeField2, DateTimeFieldType.clockhourOfHalfday());
        cHalfdayOfDayField = new HalfdayField();
    }

    BasicChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj);
        this.iYearInfoCache = new YearInfo[1024];
        if (i2 >= 1 && i2 <= 7) {
            this.iMinDaysInFirstWeek = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid min days in first week: " + i2);
    }

    private YearInfo getYearInfo(int i2) {
        int i3 = i2 & 1023;
        YearInfo yearInfo = this.iYearInfoCache[i3];
        if (yearInfo != null && yearInfo.iYear == i2) {
            return yearInfo;
        }
        YearInfo yearInfo2 = new YearInfo(i2, calculateFirstDayOfYearMillis(i2));
        this.iYearInfoCache[i3] = yearInfo2;
        return yearInfo2;
    }

    @Override // org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        fields.millis = cMillisField;
        fields.seconds = cSecondsField;
        fields.minutes = cMinutesField;
        fields.hours = cHoursField;
        fields.halfdays = cHalfdaysField;
        fields.days = cDaysField;
        fields.weeks = cWeeksField;
        fields.millisOfSecond = cMillisOfSecondField;
        fields.millisOfDay = cMillisOfDayField;
        fields.secondOfMinute = cSecondOfMinuteField;
        fields.secondOfDay = cSecondOfDayField;
        fields.minuteOfHour = cMinuteOfHourField;
        fields.minuteOfDay = cMinuteOfDayField;
        fields.hourOfDay = cHourOfDayField;
        fields.hourOfHalfday = cHourOfHalfdayField;
        fields.clockhourOfDay = cClockhourOfDayField;
        fields.clockhourOfHalfday = cClockhourOfHalfdayField;
        fields.halfdayOfDay = cHalfdayOfDayField;
        BasicYearDateTimeField basicYearDateTimeField = new BasicYearDateTimeField(this);
        fields.year = basicYearDateTimeField;
        GJYearOfEraDateTimeField gJYearOfEraDateTimeField = new GJYearOfEraDateTimeField(basicYearDateTimeField, this);
        fields.yearOfEra = gJYearOfEraDateTimeField;
        DividedDateTimeField dividedDateTimeField = new DividedDateTimeField(new OffsetDateTimeField(gJYearOfEraDateTimeField, 99), DateTimeFieldType.centuryOfEra(), 100);
        fields.centuryOfEra = dividedDateTimeField;
        fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(dividedDateTimeField), DateTimeFieldType.yearOfCentury(), 1);
        fields.era = new GJEraDateTimeField(this);
        fields.dayOfWeek = new GJDayOfWeekDateTimeField(this, fields.days);
        fields.dayOfMonth = new BasicDayOfMonthDateTimeField(this, fields.days);
        fields.dayOfYear = new BasicDayOfYearDateTimeField(this, fields.days);
        fields.monthOfYear = new GJMonthOfYearDateTimeField(this);
        fields.weekyear = new BasicWeekyearDateTimeField(this);
        fields.weekOfWeekyear = new BasicWeekOfWeekyearDateTimeField(this, fields.weeks);
        fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
        fields.years = fields.year.getDurationField();
        fields.centuries = fields.centuryOfEra.getDurationField();
        fields.months = fields.monthOfYear.getDurationField();
        fields.weekyears = fields.weekyear.getDurationField();
    }

    abstract long calculateFirstDayOfYearMillis(int i2);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicChronology basicChronology = (BasicChronology) obj;
        return getMinimumDaysInFirstWeek() == basicChronology.getMinimumDaysInFirstWeek() && getZone().equals(basicChronology.getZone());
    }

    abstract long getApproxMillisAtEpochDividedByTwo();

    abstract long getAverageMillisPerMonth();

    abstract long getAverageMillisPerYear();

    abstract long getAverageMillisPerYearDividedByTwo();

    long getDateMidnightMillis(int i2, int i3, int i4) {
        FieldUtils.verifyValueBounds(DateTimeFieldType.year(), i2, getMinYear(), getMaxYear());
        FieldUtils.verifyValueBounds(DateTimeFieldType.monthOfYear(), i3, 1, getMaxMonth(i2));
        FieldUtils.verifyValueBounds(DateTimeFieldType.dayOfMonth(), i4, 1, getDaysInYearMonth(i2, i3));
        return getYearMonthDayMillis(i2, i3, i4);
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfDay(), i5, 0, 86399999);
        return getDateMidnightMillis(i2, i3, i4) + i5;
    }

    int getDayOfMonth(long j2) {
        int year = getYear(j2);
        return getDayOfMonth(j2, year, getMonthOfYear(j2, year));
    }

    int getDayOfWeek(long j2) {
        long j3;
        if (j2 >= 0) {
            j3 = j2 / 86400000;
        } else {
            j3 = (j2 - 86399999) / 86400000;
            if (j3 < -3) {
                return ((int) ((j3 + 4) % 7)) + 7;
            }
        }
        return ((int) ((j3 + 3) % 7)) + 1;
    }

    int getDayOfYear(long j2) {
        return getDayOfYear(j2, getYear(j2));
    }

    int getDaysInMonthMax() {
        return 31;
    }

    abstract int getDaysInMonthMax(int i2);

    int getDaysInMonthMaxForSet(long j2, int i2) {
        return getDaysInMonthMax(j2);
    }

    int getDaysInYear(int i2) {
        return isLeapYear(i2) ? 366 : 365;
    }

    int getDaysInYearMax() {
        return 366;
    }

    abstract int getDaysInYearMonth(int i2, int i3);

    long getFirstWeekOfYearMillis(int i2) {
        long yearMillis = getYearMillis(i2);
        return getDayOfWeek(yearMillis) > 8 - this.iMinDaysInFirstWeek ? yearMillis + ((8 - r8) * 86400000) : yearMillis - ((r8 - 1) * 86400000);
    }

    int getMaxMonth() {
        return 12;
    }

    abstract int getMaxYear();

    int getMillisOfDay(long j2) {
        return j2 >= 0 ? (int) (j2 % 86400000) : ((int) ((j2 + 1) % 86400000)) + 86399999;
    }

    abstract int getMinYear();

    public int getMinimumDaysInFirstWeek() {
        return this.iMinDaysInFirstWeek;
    }

    int getMonthOfYear(long j2) {
        return getMonthOfYear(j2, getYear(j2));
    }

    abstract int getMonthOfYear(long j2, int i2);

    abstract long getTotalMillisByYearMonth(int i2, int i3);

    int getWeekOfWeekyear(long j2) {
        return getWeekOfWeekyear(j2, getYear(j2));
    }

    int getWeeksInYear(int i2) {
        return (int) ((getFirstWeekOfYearMillis(i2 + 1) - getFirstWeekOfYearMillis(i2)) / 604800000);
    }

    int getWeekyear(long j2) {
        int year = getYear(j2);
        int weekOfWeekyear = getWeekOfWeekyear(j2, year);
        return weekOfWeekyear == 1 ? getYear(j2 + 604800000) : weekOfWeekyear > 51 ? getYear(j2 - 1209600000) : year;
    }

    int getYear(long j2) {
        long averageMillisPerYearDividedByTwo = getAverageMillisPerYearDividedByTwo();
        long approxMillisAtEpochDividedByTwo = (j2 >> 1) + getApproxMillisAtEpochDividedByTwo();
        if (approxMillisAtEpochDividedByTwo < 0) {
            approxMillisAtEpochDividedByTwo = (approxMillisAtEpochDividedByTwo - averageMillisPerYearDividedByTwo) + 1;
        }
        int i2 = (int) (approxMillisAtEpochDividedByTwo / averageMillisPerYearDividedByTwo);
        long yearMillis = getYearMillis(i2);
        long j3 = j2 - yearMillis;
        if (j3 < 0) {
            return i2 - 1;
        }
        if (j3 >= 31536000000L) {
            return yearMillis + (isLeapYear(i2) ? 31622400000L : 31536000000L) <= j2 ? i2 + 1 : i2;
        }
        return i2;
    }

    abstract long getYearDifference(long j2, long j3);

    long getYearMillis(int i2) {
        return getYearInfo(i2).iFirstDayMillis;
    }

    long getYearMonthDayMillis(int i2, int i3, int i4) {
        return getYearMillis(i2) + getTotalMillisByYearMonth(i2, i3) + ((i4 - 1) * 86400000);
    }

    long getYearMonthMillis(int i2, int i3) {
        return getYearMillis(i2) + getTotalMillisByYearMonth(i2, i3);
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public DateTimeZone getZone() {
        Chronology base = getBase();
        return base != null ? base.getZone() : DateTimeZone.UTC;
    }

    public int hashCode() {
        return (getClass().getName().hashCode() * 11) + getZone().hashCode() + getMinimumDaysInFirstWeek();
    }

    abstract boolean isLeapYear(int i2);

    abstract long setYear(long j2, int i2);

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        String name = getClass().getName();
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            name = name.substring(iLastIndexOf + 1);
        }
        sb.append(name);
        sb.append('[');
        DateTimeZone zone = getZone();
        if (zone != null) {
            sb.append(zone.getID());
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            sb.append(",mdfw=");
            sb.append(getMinimumDaysInFirstWeek());
        }
        sb.append(']');
        return sb.toString();
    }

    int getDayOfYear(long j2, int i2) {
        return ((int) ((j2 - getYearMillis(i2)) / 86400000)) + 1;
    }

    int getDaysInMonthMax(long j2) {
        int year = getYear(j2);
        return getDaysInYearMonth(year, getMonthOfYear(j2, year));
    }

    int getMaxMonth(int i2) {
        return getMaxMonth();
    }

    int getWeekOfWeekyear(long j2, int i2) {
        long firstWeekOfYearMillis = getFirstWeekOfYearMillis(i2);
        if (j2 < firstWeekOfYearMillis) {
            return getWeeksInYear(i2 - 1);
        }
        if (j2 >= getFirstWeekOfYearMillis(i2 + 1)) {
            return 1;
        }
        return ((int) ((j2 - firstWeekOfYearMillis) / 604800000)) + 1;
    }

    int getDayOfMonth(long j2, int i2) {
        return getDayOfMonth(j2, i2, getMonthOfYear(j2, i2));
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.hourOfDay(), i5, 0, 23);
        FieldUtils.verifyValueBounds(DateTimeFieldType.minuteOfHour(), i6, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.secondOfMinute(), i7, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfSecond(), i8, 0, 999);
        return getDateMidnightMillis(i2, i3, i4) + (i5 * 3600000) + (i6 * 60000) + (i7 * 1000) + i8;
    }

    int getDayOfMonth(long j2, int i2, int i3) {
        return ((int) ((j2 - (getYearMillis(i2) + getTotalMillisByYearMonth(i2, i3))) / 86400000)) + 1;
    }
}
