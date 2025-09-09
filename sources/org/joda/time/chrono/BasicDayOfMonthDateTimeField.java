package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.PreciseDurationDateTimeField;

/* loaded from: classes5.dex */
final class BasicDayOfMonthDateTimeField extends PreciseDurationDateTimeField {
    private static final long serialVersionUID = -4677223814028011723L;
    private final BasicChronology iChronology;

    BasicDayOfMonthDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfMonth(), durationField);
        this.iChronology = basicChronology;
    }

    private Object readResolve() {
        return this.iChronology.dayOfMonth();
    }

    @Override // org.joda.time.field.PreciseDurationDateTimeField
    protected int b(long j2, int i2) {
        return this.iChronology.getDaysInMonthMaxForSet(j2, i2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        return this.iChronology.getDayOfMonth(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue() {
        return this.iChronology.getDaysInMonthMax();
    }

    @Override // org.joda.time.field.PreciseDurationDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return 1;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getRangeDurationField() {
        return this.iChronology.months();
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue(long j2) {
        return this.iChronology.getDaysInMonthMax(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial) {
        if (readablePartial.isSupported(DateTimeFieldType.monthOfYear())) {
            int i2 = readablePartial.get(DateTimeFieldType.monthOfYear());
            if (readablePartial.isSupported(DateTimeFieldType.year())) {
                return this.iChronology.getDaysInYearMonth(readablePartial.get(DateTimeFieldType.year()), i2);
            }
            return this.iChronology.getDaysInMonthMax(i2);
        }
        return getMaximumValue();
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        int size = readablePartial.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (readablePartial.getFieldType(i2) == DateTimeFieldType.monthOfYear()) {
                int i3 = iArr[i2];
                for (int i4 = 0; i4 < size; i4++) {
                    if (readablePartial.getFieldType(i4) == DateTimeFieldType.year()) {
                        return this.iChronology.getDaysInYearMonth(iArr[i4], i3);
                    }
                }
                return this.iChronology.getDaysInMonthMax(i3);
            }
        }
        return getMaximumValue();
    }
}
