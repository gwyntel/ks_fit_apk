package org.joda.time;

/* loaded from: classes5.dex */
public interface ReadWritableDateTime extends ReadableDateTime, ReadWritableInstant {
    void addDays(int i2);

    void addHours(int i2);

    void addMillis(int i2);

    void addMinutes(int i2);

    void addMonths(int i2);

    void addSeconds(int i2);

    void addWeeks(int i2);

    void addWeekyears(int i2);

    void addYears(int i2);

    void setDate(int i2, int i3, int i4);

    void setDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    void setDayOfMonth(int i2);

    void setDayOfWeek(int i2);

    void setDayOfYear(int i2);

    void setHourOfDay(int i2);

    void setMillisOfDay(int i2);

    void setMillisOfSecond(int i2);

    void setMinuteOfDay(int i2);

    void setMinuteOfHour(int i2);

    void setMonthOfYear(int i2);

    void setSecondOfDay(int i2);

    void setSecondOfMinute(int i2);

    void setTime(int i2, int i3, int i4, int i5);

    void setWeekOfWeekyear(int i2);

    void setWeekyear(int i2);

    void setYear(int i2);
}
