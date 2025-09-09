package org.joda.time;

import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/* loaded from: classes5.dex */
public final class Minutes extends BaseSingleFieldPeriod {
    private static final long serialVersionUID = 87525275727380863L;
    public static final Minutes ZERO = new Minutes(0);
    public static final Minutes ONE = new Minutes(1);
    public static final Minutes TWO = new Minutes(2);
    public static final Minutes THREE = new Minutes(3);
    public static final Minutes MAX_VALUE = new Minutes(Integer.MAX_VALUE);
    public static final Minutes MIN_VALUE = new Minutes(Integer.MIN_VALUE);
    private static final PeriodFormatter PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.minutes());

    private Minutes(int i2) {
        super(i2);
    }

    public static Minutes minutes(int i2) {
        return i2 != Integer.MIN_VALUE ? i2 != Integer.MAX_VALUE ? i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? new Minutes(i2) : THREE : TWO : ONE : ZERO : MAX_VALUE : MIN_VALUE;
    }

    public static Minutes minutesBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return minutes(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.minutes()));
    }

    public static Minutes minutesIn(ReadableInterval readableInterval) {
        return readableInterval == null ? ZERO : minutes(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.minutes()));
    }

    @FromString
    public static Minutes parseMinutes(String str) {
        return str == null ? ZERO : minutes(PARSER.parsePeriod(str).getMinutes());
    }

    private Object readResolve() {
        return minutes(getValue());
    }

    public static Minutes standardMinutesIn(ReadablePeriod readablePeriod) {
        return minutes(BaseSingleFieldPeriod.standardPeriodIn(readablePeriod, 60000L));
    }

    public Minutes dividedBy(int i2) {
        return i2 == 1 ? this : minutes(getValue() / i2);
    }

    @Override // org.joda.time.base.BaseSingleFieldPeriod
    public DurationFieldType getFieldType() {
        return DurationFieldType.minutes();
    }

    public int getMinutes() {
        return getValue();
    }

    @Override // org.joda.time.base.BaseSingleFieldPeriod, org.joda.time.ReadablePeriod
    public PeriodType getPeriodType() {
        return PeriodType.minutes();
    }

    public boolean isGreaterThan(Minutes minutes) {
        return minutes == null ? getValue() > 0 : getValue() > minutes.getValue();
    }

    public boolean isLessThan(Minutes minutes) {
        return minutes == null ? getValue() < 0 : getValue() < minutes.getValue();
    }

    public Minutes minus(int i2) {
        return plus(FieldUtils.safeNegate(i2));
    }

    public Minutes multipliedBy(int i2) {
        return minutes(FieldUtils.safeMultiply(getValue(), i2));
    }

    public Minutes negated() {
        return minutes(FieldUtils.safeNegate(getValue()));
    }

    public Minutes plus(int i2) {
        return i2 == 0 ? this : minutes(FieldUtils.safeAdd(getValue(), i2));
    }

    public Days toStandardDays() {
        return Days.days(getValue() / DateTimeConstants.MINUTES_PER_DAY);
    }

    public Duration toStandardDuration() {
        return new Duration(getValue() * 60000);
    }

    public Hours toStandardHours() {
        return Hours.hours(getValue() / 60);
    }

    public Seconds toStandardSeconds() {
        return Seconds.seconds(FieldUtils.safeMultiply(getValue(), 60));
    }

    public Weeks toStandardWeeks() {
        return Weeks.weeks(getValue() / DateTimeConstants.MINUTES_PER_WEEK);
    }

    @Override // org.joda.time.ReadablePeriod
    @ToString
    public String toString() {
        return "PT" + String.valueOf(getValue()) + "M";
    }

    public Minutes minus(Minutes minutes) {
        return minutes == null ? this : minus(minutes.getValue());
    }

    public Minutes plus(Minutes minutes) {
        return minutes == null ? this : plus(minutes.getValue());
    }

    public static Minutes minutesBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalTime) && (readablePartial2 instanceof LocalTime)) {
            return minutes(DateTimeUtils.getChronology(readablePartial.getChronology()).minutes().getDifference(((LocalTime) readablePartial2).getLocalMillis(), ((LocalTime) readablePartial).getLocalMillis()));
        }
        return minutes(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }
}
