package org.joda.time;

import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/* loaded from: classes5.dex */
public final class Months extends BaseSingleFieldPeriod {
    private static final long serialVersionUID = 87525275727380867L;
    public static final Months ZERO = new Months(0);
    public static final Months ONE = new Months(1);
    public static final Months TWO = new Months(2);
    public static final Months THREE = new Months(3);
    public static final Months FOUR = new Months(4);
    public static final Months FIVE = new Months(5);
    public static final Months SIX = new Months(6);
    public static final Months SEVEN = new Months(7);
    public static final Months EIGHT = new Months(8);
    public static final Months NINE = new Months(9);
    public static final Months TEN = new Months(10);
    public static final Months ELEVEN = new Months(11);
    public static final Months TWELVE = new Months(12);
    public static final Months MAX_VALUE = new Months(Integer.MAX_VALUE);
    public static final Months MIN_VALUE = new Months(Integer.MIN_VALUE);
    private static final PeriodFormatter PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.months());

    private Months(int i2) {
        super(i2);
    }

    public static Months months(int i2) {
        if (i2 == Integer.MIN_VALUE) {
            return MIN_VALUE;
        }
        if (i2 == Integer.MAX_VALUE) {
            return MAX_VALUE;
        }
        switch (i2) {
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            case 9:
                return NINE;
            case 10:
                return TEN;
            case 11:
                return ELEVEN;
            case 12:
                return TWELVE;
            default:
                return new Months(i2);
        }
    }

    public static Months monthsBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return months(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.months()));
    }

    public static Months monthsIn(ReadableInterval readableInterval) {
        return readableInterval == null ? ZERO : months(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.months()));
    }

    @FromString
    public static Months parseMonths(String str) {
        return str == null ? ZERO : months(PARSER.parsePeriod(str).getMonths());
    }

    private Object readResolve() {
        return months(getValue());
    }

    public Months dividedBy(int i2) {
        return i2 == 1 ? this : months(getValue() / i2);
    }

    @Override // org.joda.time.base.BaseSingleFieldPeriod
    public DurationFieldType getFieldType() {
        return DurationFieldType.months();
    }

    public int getMonths() {
        return getValue();
    }

    @Override // org.joda.time.base.BaseSingleFieldPeriod, org.joda.time.ReadablePeriod
    public PeriodType getPeriodType() {
        return PeriodType.months();
    }

    public boolean isGreaterThan(Months months) {
        return months == null ? getValue() > 0 : getValue() > months.getValue();
    }

    public boolean isLessThan(Months months) {
        return months == null ? getValue() < 0 : getValue() < months.getValue();
    }

    public Months minus(int i2) {
        return plus(FieldUtils.safeNegate(i2));
    }

    public Months multipliedBy(int i2) {
        return months(FieldUtils.safeMultiply(getValue(), i2));
    }

    public Months negated() {
        return months(FieldUtils.safeNegate(getValue()));
    }

    public Months plus(int i2) {
        return i2 == 0 ? this : months(FieldUtils.safeAdd(getValue(), i2));
    }

    @Override // org.joda.time.ReadablePeriod
    @ToString
    public String toString() {
        return "P" + String.valueOf(getValue()) + "M";
    }

    public Months minus(Months months) {
        return months == null ? this : minus(months.getValue());
    }

    public Months plus(Months months) {
        return months == null ? this : plus(months.getValue());
    }

    public static Months monthsBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalDate) && (readablePartial2 instanceof LocalDate)) {
            return months(DateTimeUtils.getChronology(readablePartial.getChronology()).months().getDifference(((LocalDate) readablePartial2).getLocalMillis(), ((LocalDate) readablePartial).getLocalMillis()));
        }
        return months(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }
}
