package org.joda.time.chrono;

import org.joda.time.Chronology;

/* loaded from: classes5.dex */
abstract class BasicFixedMonthChronology extends BasicChronology {
    static final long MILLIS_PER_MONTH = 2592000000L;
    static final long MILLIS_PER_YEAR = 31557600000L;
    static final int MONTH_LENGTH = 30;
    private static final long serialVersionUID = 261387371998L;

    BasicFixedMonthChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
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
        return 15778800000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDayOfMonth(long j2) {
        return ((getDayOfYear(j2) - 1) % 30) + 1;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMax() {
        return 30;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInYearMonth(int i2, int i3) {
        if (i3 != 13) {
            return 30;
        }
        return isLeapYear(i2) ? 6 : 5;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMaxMonth() {
        return 13;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMonthOfYear(long j2) {
        return ((getDayOfYear(j2) - 1) / 30) + 1;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getTotalMillisByYearMonth(int i2, int i3) {
        return (i3 - 1) * MILLIS_PER_MONTH;
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
    boolean isLeapYear(int i2) {
        return (i2 & 3) == 3;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long setYear(long j2, int i2) {
        int dayOfYear = getDayOfYear(j2, getYear(j2));
        int millisOfDay = getMillisOfDay(j2);
        if (dayOfYear > 365 && !isLeapYear(i2)) {
            dayOfYear--;
        }
        return getYearMonthDayMillis(i2, 1, dayOfYear) + millisOfDay;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMax(int i2) {
        return i2 != 13 ? 30 : 6;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMonthOfYear(long j2, int i2) {
        return ((int) ((j2 - getYearMillis(i2)) / MILLIS_PER_MONTH)) + 1;
    }
}
