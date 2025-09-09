package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

/* loaded from: classes5.dex */
class BasicMonthOfYearDateTimeField extends ImpreciseDateTimeField {
    private static final int MIN = 1;
    private static final long serialVersionUID = -8258715387168736L;
    private final BasicChronology iChronology;
    private final int iLeapMonth;
    private final int iMax;

    BasicMonthOfYearDateTimeField(BasicChronology basicChronology, int i2) {
        super(DateTimeFieldType.monthOfYear(), basicChronology.getAverageMillisPerMonth());
        this.iChronology = basicChronology;
        this.iMax = basicChronology.getMaxMonth();
        this.iLeapMonth = i2;
    }

    private Object readResolve() {
        return this.iChronology.monthOfYear();
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        int i3;
        int i4;
        if (i2 == 0) {
            return j2;
        }
        long millisOfDay = this.iChronology.getMillisOfDay(j2);
        int year = this.iChronology.getYear(j2);
        int monthOfYear = this.iChronology.getMonthOfYear(j2, year);
        int i5 = (monthOfYear - 1) + i2;
        if (i5 >= 0) {
            int i6 = this.iMax;
            i3 = (i5 / i6) + year;
            i4 = (i5 % i6) + 1;
        } else {
            i3 = year + (i5 / this.iMax);
            int i7 = i3 - 1;
            int iAbs = Math.abs(i5);
            int i8 = this.iMax;
            int i9 = iAbs % i8;
            if (i9 == 0) {
                i9 = i8;
            }
            i4 = (i8 - i9) + 1;
            if (i4 != 1) {
                i3 = i7;
            }
        }
        int dayOfMonth = this.iChronology.getDayOfMonth(j2, year, monthOfYear);
        int daysInYearMonth = this.iChronology.getDaysInYearMonth(i3, i4);
        if (dayOfMonth > daysInYearMonth) {
            dayOfMonth = daysInYearMonth;
        }
        return this.iChronology.getYearMonthDayMillis(i3, i4, dayOfMonth) + millisOfDay;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return set(j2, FieldUtils.getWrappedValue(get(j2), i2, 1, this.iMax));
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        return this.iChronology.getMonthOfYear(j2);
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        if (j2 < j3) {
            return -getDifference(j3, j2);
        }
        int year = this.iChronology.getYear(j2);
        int monthOfYear = this.iChronology.getMonthOfYear(j2, year);
        int year2 = this.iChronology.getYear(j3);
        int monthOfYear2 = this.iChronology.getMonthOfYear(j3, year2);
        long j4 = (((year - year2) * this.iMax) + monthOfYear) - monthOfYear2;
        int dayOfMonth = this.iChronology.getDayOfMonth(j2, year, monthOfYear);
        if (dayOfMonth == this.iChronology.getDaysInYearMonth(year, monthOfYear) && this.iChronology.getDayOfMonth(j3, year2, monthOfYear2) > dayOfMonth) {
            j3 = this.iChronology.dayOfMonth().set(j3, dayOfMonth);
        }
        return j2 - this.iChronology.getYearMonthMillis(year, monthOfYear) < j3 - this.iChronology.getYearMonthMillis(year2, monthOfYear2) ? j4 - 1 : j4;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getLeapAmount(long j2) {
        return isLeap(j2) ? 1 : 0;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getLeapDurationField() {
        return this.iChronology.days();
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue() {
        return this.iMax;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return 1;
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getRangeDurationField() {
        return this.iChronology.years();
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public boolean isLeap(long j2) {
        int year = this.iChronology.getYear(j2);
        return this.iChronology.isLeapYear(year) && this.iChronology.getMonthOfYear(j2, year) == this.iLeapMonth;
    }

    @Override // org.joda.time.DateTimeField
    public boolean isLenient() {
        return false;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long remainder(long j2) {
        return j2 - roundFloor(j2);
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundFloor(long j2) {
        int year = this.iChronology.getYear(j2);
        return this.iChronology.getYearMonthMillis(year, this.iChronology.getMonthOfYear(j2, year));
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, 1, this.iMax);
        int year = this.iChronology.getYear(j2);
        int dayOfMonth = this.iChronology.getDayOfMonth(j2, year);
        int daysInYearMonth = this.iChronology.getDaysInYearMonth(year, i2);
        if (dayOfMonth > daysInYearMonth) {
            dayOfMonth = daysInYearMonth;
        }
        return this.iChronology.getYearMonthDayMillis(year, i2, dayOfMonth) + this.iChronology.getMillisOfDay(j2);
    }

    @Override // org.joda.time.field.ImpreciseDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        long j4;
        long j5;
        int i2 = (int) j3;
        if (i2 == j3) {
            return add(j2, i2);
        }
        long millisOfDay = this.iChronology.getMillisOfDay(j2);
        int year = this.iChronology.getYear(j2);
        int monthOfYear = this.iChronology.getMonthOfYear(j2, year);
        long j6 = (monthOfYear - 1) + j3;
        if (j6 >= 0) {
            int i3 = this.iMax;
            j4 = year + (j6 / i3);
            j5 = (j6 % i3) + 1;
        } else {
            j4 = year + (j6 / this.iMax);
            long j7 = j4 - 1;
            long jAbs = Math.abs(j6);
            int i4 = this.iMax;
            int i5 = (int) (jAbs % i4);
            if (i5 == 0) {
                i5 = i4;
            }
            j5 = (i4 - i5) + 1;
            if (j5 != 1) {
                j4 = j7;
            }
        }
        if (j4 >= this.iChronology.getMinYear() && j4 <= this.iChronology.getMaxYear()) {
            int i6 = (int) j4;
            int i7 = (int) j5;
            int dayOfMonth = this.iChronology.getDayOfMonth(j2, year, monthOfYear);
            int daysInYearMonth = this.iChronology.getDaysInYearMonth(i6, i7);
            if (dayOfMonth > daysInYearMonth) {
                dayOfMonth = daysInYearMonth;
            }
            return this.iChronology.getYearMonthDayMillis(i6, i7, dayOfMonth) + millisOfDay;
        }
        throw new IllegalArgumentException("Magnitude of add amount is too large: " + j3);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int[] add(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        if (i3 == 0) {
            return iArr;
        }
        if (readablePartial.size() > 0 && readablePartial.getFieldType(0).equals(DateTimeFieldType.monthOfYear()) && i2 == 0) {
            return set(readablePartial, 0, iArr, ((((readablePartial.getValue(0) - 1) + (i3 % 12)) + 12) % 12) + 1);
        }
        if (DateTimeUtils.isContiguous(readablePartial)) {
            int size = readablePartial.size();
            long j2 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                j2 = readablePartial.getFieldType(i4).getField(this.iChronology).set(j2, iArr[i4]);
            }
            return this.iChronology.get(readablePartial, add(j2, i3));
        }
        return super.add(readablePartial, i2, iArr, i3);
    }
}
