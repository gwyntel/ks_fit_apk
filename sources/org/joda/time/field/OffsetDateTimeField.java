package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

/* loaded from: classes5.dex */
public class OffsetDateTimeField extends DecoratedDateTimeField {
    private static final long serialVersionUID = 3145790132623583142L;
    private final int iMax;
    private final int iMin;
    private final int iOffset;

    public OffsetDateTimeField(DateTimeField dateTimeField, int i2) {
        this(dateTimeField, dateTimeField == null ? null : dateTimeField.getType(), i2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        long jAdd = super.add(j2, i2);
        FieldUtils.verifyValueBounds(this, get(jAdd), this.iMin, this.iMax);
        return jAdd;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return set(j2, FieldUtils.getWrappedValue(get(j2), i2, this.iMin, this.iMax));
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        return super.get(j2) + this.iOffset;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getLeapAmount(long j2) {
        return getWrappedField().getLeapAmount(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getLeapDurationField() {
        return getWrappedField().getLeapDurationField();
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue() {
        return this.iMax;
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return this.iMin;
    }

    public int getOffset() {
        return this.iOffset;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public boolean isLeap(long j2) {
        return getWrappedField().isLeap(j2);
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

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundHalfCeiling(long j2) {
        return getWrappedField().roundHalfCeiling(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundHalfEven(long j2) {
        return getWrappedField().roundHalfEven(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundHalfFloor(long j2) {
        return getWrappedField().roundHalfFloor(j2);
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, this.iMin, this.iMax);
        return super.set(j2, i2 - this.iOffset);
    }

    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i2) {
        this(dateTimeField, dateTimeFieldType, i2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i2, int i3, int i4) {
        super(dateTimeField, dateTimeFieldType);
        if (i2 != 0) {
            this.iOffset = i2;
            if (i3 < dateTimeField.getMinimumValue() + i2) {
                this.iMin = dateTimeField.getMinimumValue() + i2;
            } else {
                this.iMin = i3;
            }
            if (i4 > dateTimeField.getMaximumValue() + i2) {
                this.iMax = dateTimeField.getMaximumValue() + i2;
                return;
            } else {
                this.iMax = i4;
                return;
            }
        }
        throw new IllegalArgumentException("The offset cannot be zero");
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        long jAdd = super.add(j2, j3);
        FieldUtils.verifyValueBounds(this, get(jAdd), this.iMin, this.iMax);
        return jAdd;
    }
}
