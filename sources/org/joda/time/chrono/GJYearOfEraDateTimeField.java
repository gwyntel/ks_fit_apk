package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.ReadablePartial;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

/* loaded from: classes5.dex */
final class GJYearOfEraDateTimeField extends DecoratedDateTimeField {
    private static final long serialVersionUID = -5961050944769862059L;
    private final BasicChronology iChronology;

    GJYearOfEraDateTimeField(DateTimeField dateTimeField, BasicChronology basicChronology) {
        super(dateTimeField, DateTimeFieldType.yearOfEra());
        this.iChronology = basicChronology;
    }

    private Object readResolve() {
        return this.iChronology.yearOfEra();
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        return getWrappedField().add(j2, i2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return getWrappedField().addWrapField(j2, i2);
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        int i2 = getWrappedField().get(j2);
        return i2 <= 0 ? 1 - i2 : i2;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getDifference(long j2, long j3) {
        return getWrappedField().getDifference(j2, j3);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        return getWrappedField().getDifferenceAsLong(j2, j3);
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue() {
        return getWrappedField().getMaximumValue();
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return 1;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long remainder(long j2) {
        return getWrappedField().remainder(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundCeiling(long j2) {
        return getWrappedField().roundCeiling(j2);
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundFloor(long j2) {
        return getWrappedField().roundFloor(j2);
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, 1, getMaximumValue());
        if (this.iChronology.getYear(j2) <= 0) {
            i2 = 1 - i2;
        }
        return super.set(j2, i2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        return getWrappedField().add(j2, j3);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int[] addWrapField(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return getWrappedField().addWrapField(readablePartial, i2, iArr, i3);
    }
}
