package org.joda.time.chrono;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;

/* loaded from: classes5.dex */
public final class IslamicChronology extends BasicChronology {
    public static final int AH = 1;
    private static final int CYCLE = 30;
    private static final int LONG_MONTH_LENGTH = 30;
    private static final int MAX_YEAR = 292271022;
    private static final long MILLIS_PER_CYCLE = 918518400000L;
    private static final long MILLIS_PER_LONG_MONTH = 2592000000L;
    private static final long MILLIS_PER_LONG_YEAR = 30672000000L;
    private static final long MILLIS_PER_MONTH = 2551440384L;
    private static final long MILLIS_PER_MONTH_PAIR = 5097600000L;
    private static final long MILLIS_PER_SHORT_YEAR = 30585600000L;
    private static final long MILLIS_PER_YEAR = 30617280288L;
    private static final long MILLIS_YEAR_1 = -42521587200000L;
    private static final int MIN_YEAR = -292269337;
    private static final int MONTH_PAIR_LENGTH = 59;
    private static final int SHORT_MONTH_LENGTH = 29;
    private static final long serialVersionUID = -3663823829888L;
    private final LeapYearPatternType iLeapYears;
    private static final DateTimeField ERA_FIELD = new BasicSingleEraDateTimeField("AH");
    public static final LeapYearPatternType LEAP_YEAR_15_BASED = new LeapYearPatternType(0, 623158436);
    public static final LeapYearPatternType LEAP_YEAR_16_BASED = new LeapYearPatternType(1, 623191204);
    public static final LeapYearPatternType LEAP_YEAR_INDIAN = new LeapYearPatternType(2, 690562340);
    public static final LeapYearPatternType LEAP_YEAR_HABASH_AL_HASIB = new LeapYearPatternType(3, 153692453);
    private static final Map<DateTimeZone, IslamicChronology[]> cCache = new HashMap();
    private static final IslamicChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    public static class LeapYearPatternType implements Serializable {
        private static final long serialVersionUID = 26581275372698L;
        final byte index;
        final int pattern;

        LeapYearPatternType(int i2, int i3) {
            this.index = (byte) i2;
            this.pattern = i3;
        }

        private Object readResolve() {
            byte b2 = this.index;
            return b2 != 0 ? b2 != 1 ? b2 != 2 ? b2 != 3 ? this : IslamicChronology.LEAP_YEAR_HABASH_AL_HASIB : IslamicChronology.LEAP_YEAR_INDIAN : IslamicChronology.LEAP_YEAR_16_BASED : IslamicChronology.LEAP_YEAR_15_BASED;
        }

        public boolean equals(Object obj) {
            return (obj instanceof LeapYearPatternType) && this.index == ((LeapYearPatternType) obj).index;
        }

        public int hashCode() {
            return this.index;
        }

        boolean isLeapYear(int i2) {
            return ((1 << (i2 % 30)) & this.pattern) > 0;
        }
    }

    IslamicChronology(Chronology chronology, Object obj, LeapYearPatternType leapYearPatternType) {
        super(chronology, obj, 4);
        this.iLeapYears = leapYearPatternType;
    }

    public static IslamicChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), LEAP_YEAR_16_BASED);
    }

    public static IslamicChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    private Object readResolve() {
        Chronology base = getBase();
        return base == null ? getInstanceUTC() : getInstance(base.getZone());
    }

    @Override // org.joda.time.chrono.BasicChronology, org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.era = ERA_FIELD;
            BasicMonthOfYearDateTimeField basicMonthOfYearDateTimeField = new BasicMonthOfYearDateTimeField(this, 12);
            fields.monthOfYear = basicMonthOfYearDateTimeField;
            fields.months = basicMonthOfYearDateTimeField.getDurationField();
        }
    }

    @Override // org.joda.time.chrono.BasicChronology
    long calculateFirstDayOfYearMillis(int i2) {
        if (i2 > MAX_YEAR) {
            throw new ArithmeticException("Year is too large: " + i2 + " > " + MAX_YEAR);
        }
        if (i2 < MIN_YEAR) {
            throw new ArithmeticException("Year is too small: " + i2 + " < " + MIN_YEAR);
        }
        long j2 = ((r6 / 30) * MILLIS_PER_CYCLE) + MILLIS_YEAR_1;
        int i3 = ((i2 - 1) % 30) + 1;
        for (int i4 = 1; i4 < i3; i4++) {
            j2 += isLeapYear(i4) ? MILLIS_PER_LONG_YEAR : MILLIS_PER_SHORT_YEAR;
        }
        return j2;
    }

    @Override // org.joda.time.chrono.BasicChronology
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IslamicChronology) {
            return getLeapYearPatternType().index == ((IslamicChronology) obj).getLeapYearPatternType().index && super.equals(obj);
        }
        return false;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getApproxMillisAtEpochDividedByTwo() {
        return 21260793600000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerMonth() {
        return MILLIS_PER_MONTH;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerYear() {
        return MILLIS_PER_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerYearDividedByTwo() {
        return 15308640144L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDayOfMonth(long j2) {
        int dayOfYear = getDayOfYear(j2) - 1;
        if (dayOfYear == 354) {
            return 30;
        }
        return ((dayOfYear % 59) % 30) + 1;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMax() {
        return 30;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInYear(int i2) {
        return isLeapYear(i2) ? 355 : 354;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInYearMax() {
        return 355;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInYearMonth(int i2, int i3) {
        return ((i3 == 12 && isLeapYear(i2)) || (i3 + (-1)) % 2 == 0) ? 30 : 29;
    }

    public LeapYearPatternType getLeapYearPatternType() {
        return this.iLeapYears;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMaxYear() {
        return MAX_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMinYear() {
        return 1;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMonthOfYear(long j2, int i2) {
        int yearMillis = (int) ((j2 - getYearMillis(i2)) / 86400000);
        if (yearMillis == 354) {
            return 12;
        }
        return ((yearMillis * 2) / 59) + 1;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getTotalMillisByYearMonth(int i2, int i3) {
        return (i3 - 1) % 2 == 1 ? ((r5 / 2) * MILLIS_PER_MONTH_PAIR) + MILLIS_PER_LONG_MONTH : (r5 / 2) * MILLIS_PER_MONTH_PAIR;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0036, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        r6 = 30585600000L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3:0x0023, code lost:
    
        if (isLeapYear(r0) != false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0025, code lost:
    
        r6 = 30672000000L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0027, code lost:
    
        r6 = 30585600000L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
    
        if (r9 < r6) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002c, code lost:
    
        r9 = r9 - r6;
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0033, code lost:
    
        if (isLeapYear(r0) == false) goto L5;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0033 -> B:4:0x0025). Please report as a decompilation issue!!! */
    @Override // org.joda.time.chrono.BasicChronology
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getYear(long r9) {
        /*
            r8 = this;
            r0 = -42521587200000(0xffffd953abe65000, double:NaN)
            long r9 = r9 - r0
            r0 = 918518400000(0xd5dbf68400, double:4.53808386513E-312)
            long r2 = r9 / r0
            long r9 = r9 % r0
            r0 = 30
            long r2 = r2 * r0
            r0 = 1
            long r2 = r2 + r0
            int r0 = (int) r2
            boolean r1 = r8.isLeapYear(r0)
            r2 = 30585600000(0x71f0b3800, double:1.51112942174E-313)
            r4 = 30672000000(0x724319400, double:1.5153981489E-313)
            if (r1 == 0) goto L27
        L25:
            r6 = r4
            goto L28
        L27:
            r6 = r2
        L28:
            int r1 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r1 < 0) goto L36
            long r9 = r9 - r6
            int r0 = r0 + 1
            boolean r1 = r8.isLeapYear(r0)
            if (r1 == 0) goto L27
            goto L25
        L36:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.chrono.IslamicChronology.getYear(long):int");
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getYearDifference(long j2, long j3) {
        int year = getYear(j2);
        int year2 = getYear(j3);
        long yearMillis = j2 - getYearMillis(year);
        int i2 = year - year2;
        if (yearMillis < j3 - getYearMillis(year2)) {
            i2--;
        }
        return i2;
    }

    @Override // org.joda.time.chrono.BasicChronology
    public int hashCode() {
        return (super.hashCode() * 13) + getLeapYearPatternType().hashCode();
    }

    @Override // org.joda.time.chrono.BasicChronology
    boolean isLeapYear(int i2) {
        return this.iLeapYears.isLeapYear(i2);
    }

    @Override // org.joda.time.chrono.BasicChronology
    long setYear(long j2, int i2) {
        int dayOfYear = getDayOfYear(j2, getYear(j2));
        int millisOfDay = getMillisOfDay(j2);
        if (dayOfYear > 354 && !isLeapYear(i2)) {
            dayOfYear--;
        }
        return getYearMonthDayMillis(i2, 1, dayOfYear) + millisOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getZone() ? this : getInstance(dateTimeZone);
    }

    public static IslamicChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, LEAP_YEAR_16_BASED);
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMax(int i2) {
        return (i2 == 12 || (i2 + (-1)) % 2 == 0) ? 30 : 29;
    }

    public static IslamicChronology getInstance(DateTimeZone dateTimeZone, LeapYearPatternType leapYearPatternType) {
        IslamicChronology islamicChronology;
        IslamicChronology islamicChronology2;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        Map<DateTimeZone, IslamicChronology[]> map = cCache;
        synchronized (map) {
            try {
                IslamicChronology[] islamicChronologyArr = map.get(dateTimeZone);
                if (islamicChronologyArr == null) {
                    islamicChronologyArr = new IslamicChronology[4];
                    map.put(dateTimeZone, islamicChronologyArr);
                }
                islamicChronology = islamicChronologyArr[leapYearPatternType.index];
                if (islamicChronology == null) {
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (dateTimeZone == dateTimeZone2) {
                        IslamicChronology islamicChronology3 = new IslamicChronology(null, null, leapYearPatternType);
                        islamicChronology2 = new IslamicChronology(LimitChronology.getInstance(islamicChronology3, new DateTime(1, 1, 1, 0, 0, 0, 0, islamicChronology3), null), null, leapYearPatternType);
                    } else {
                        islamicChronology2 = new IslamicChronology(ZonedChronology.getInstance(getInstance(dateTimeZone2, leapYearPatternType), dateTimeZone), null, leapYearPatternType);
                    }
                    islamicChronology = islamicChronology2;
                    islamicChronologyArr[leapYearPatternType.index] = islamicChronology;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return islamicChronology;
    }
}
