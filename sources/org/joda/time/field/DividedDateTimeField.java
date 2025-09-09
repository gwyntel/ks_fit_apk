package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

/* loaded from: classes5.dex */
public class DividedDateTimeField extends DecoratedDateTimeField {
    private static final long serialVersionUID = 8318475124230605365L;

    /* renamed from: a, reason: collision with root package name */
    final int f26715a;

    /* renamed from: b, reason: collision with root package name */
    final DurationField f26716b;
    private final int iMax;
    private final int iMin;

    public DividedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i2) {
        super(dateTimeField, dateTimeFieldType);
        if (i2 < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        DurationField durationField = dateTimeField.getDurationField();
        if (durationField == null) {
            this.f26716b = null;
        } else {
            this.f26716b = new ScaledDurationField(durationField, dateTimeFieldType.getDurationType(), i2);
        }
        this.f26715a = i2;
        int minimumValue = dateTimeField.getMinimumValue();
        int i3 = minimumValue >= 0 ? minimumValue / i2 : ((minimumValue + 1) / i2) - 1;
        int maximumValue = dateTimeField.getMaximumValue();
        int i4 = maximumValue >= 0 ? maximumValue / i2 : ((maximumValue + 1) / i2) - 1;
        this.iMin = i3;
        this.iMax = i4;
    }

    private int getRemainder(int i2) {
        if (i2 >= 0) {
            return i2 % this.f26715a;
        }
        int i3 = this.f26715a;
        return (i3 - 1) + ((i2 + 1) % i3);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        return getWrappedField().add(j2, i2 * this.f26715a);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return set(j2, FieldUtils.getWrappedValue(get(j2), i2, this.iMin, this.iMax));
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        int i2 = getWrappedField().get(j2);
        return i2 >= 0 ? i2 / this.f26715a : ((i2 + 1) / this.f26715a) - 1;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getDifference(long j2, long j3) {
        return getWrappedField().getDifference(j2, j3) / this.f26715a;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        return getWrappedField().getDifferenceAsLong(j2, j3) / this.f26715a;
    }

    public int getDivisor() {
        return this.f26715a;
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getDurationField() {
        return this.f26716b;
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMaximumValue() {
        return this.iMax;
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return this.iMin;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long remainder(long j2) {
        return set(j2, get(getWrappedField().remainder(j2)));
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundFloor(long j2) {
        DateTimeField wrappedField = getWrappedField();
        return wrappedField.roundFloor(wrappedField.set(j2, get(j2) * this.f26715a));
    }

    @Override // org.joda.time.field.DecoratedDateTimeField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, this.iMin, this.iMax);
        return getWrappedField().set(j2, (i2 * this.f26715a) + getRemainder(getWrappedField().get(j2)));
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        return getWrappedField().add(j2, j3 * this.f26715a);
    }

    public DividedDateTimeField(RemainderDateTimeField remainderDateTimeField, DateTimeFieldType dateTimeFieldType) {
        super(remainderDateTimeField.getWrappedField(), dateTimeFieldType);
        int i2 = remainderDateTimeField.f26719a;
        this.f26715a = i2;
        this.f26716b = remainderDateTimeField.f26720b;
        DateTimeField wrappedField = getWrappedField();
        int minimumValue = wrappedField.getMinimumValue();
        int i3 = minimumValue >= 0 ? minimumValue / i2 : ((minimumValue + 1) / i2) - 1;
        int maximumValue = wrappedField.getMaximumValue();
        int i4 = maximumValue >= 0 ? maximumValue / i2 : ((maximumValue + 1) / i2) - 1;
        this.iMin = i3;
        this.iMax = i4;
    }
}
