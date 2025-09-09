package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

/* loaded from: classes5.dex */
public abstract class PreciseDurationDateTimeField extends BaseDateTimeField {
    private static final long serialVersionUID = 5004523158306266035L;

    /* renamed from: a, reason: collision with root package name */
    final long f26718a;
    private final DurationField iUnitField;

    public PreciseDurationDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        super(dateTimeFieldType);
        if (!durationField.isPrecise()) {
            throw new IllegalArgumentException("Unit duration field must be precise");
        }
        long unitMillis = durationField.getUnitMillis();
        this.f26718a = unitMillis;
        if (unitMillis < 1) {
            throw new IllegalArgumentException("The unit milliseconds must be at least 1");
        }
        this.iUnitField = durationField;
    }

    protected int b(long j2, int i2) {
        return getMaximumValue(j2);
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public DurationField getDurationField() {
        return this.iUnitField;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return 0;
    }

    public final long getUnitMillis() {
        return this.f26718a;
    }

    @Override // org.joda.time.DateTimeField
    public boolean isLenient() {
        return false;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long remainder(long j2) {
        if (j2 >= 0) {
            return j2 % this.f26718a;
        }
        long j3 = this.f26718a;
        return (((j2 + 1) % j3) + j3) - 1;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundCeiling(long j2) {
        if (j2 <= 0) {
            return j2 - (j2 % this.f26718a);
        }
        long j3 = j2 - 1;
        long j4 = this.f26718a;
        return (j3 - (j3 % j4)) + j4;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long roundFloor(long j2) {
        long j3;
        if (j2 >= 0) {
            j3 = j2 % this.f26718a;
        } else {
            long j4 = j2 + 1;
            j3 = this.f26718a;
            j2 = j4 - (j4 % j3);
        }
        return j2 - j3;
    }

    @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, getMinimumValue(), b(j2, i2));
        return j2 + ((i2 - get(j2)) * this.f26718a);
    }
}
