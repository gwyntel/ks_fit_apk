package org.joda.time.field;

import org.joda.time.DurationFieldType;

/* loaded from: classes5.dex */
public class PreciseDurationField extends BaseDurationField {
    private static final long serialVersionUID = -8346152187724495365L;
    private final long iUnitMillis;

    public PreciseDurationField(DurationFieldType durationFieldType, long j2) {
        super(durationFieldType);
        this.iUnitMillis = j2;
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, int i2) {
        return FieldUtils.safeAdd(j2, i2 * this.iUnitMillis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreciseDurationField)) {
            return false;
        }
        PreciseDurationField preciseDurationField = (PreciseDurationField) obj;
        return getType() == preciseDurationField.getType() && this.iUnitMillis == preciseDurationField.iUnitMillis;
    }

    @Override // org.joda.time.DurationField
    public long getDifferenceAsLong(long j2, long j3) {
        return FieldUtils.safeSubtract(j2, j3) / this.iUnitMillis;
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2, long j2) {
        return i2 * this.iUnitMillis;
    }

    @Override // org.joda.time.DurationField
    public final long getUnitMillis() {
        return this.iUnitMillis;
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2, long j3) {
        return j2 / this.iUnitMillis;
    }

    public int hashCode() {
        long j2 = this.iUnitMillis;
        return ((int) (j2 ^ (j2 >>> 32))) + getType().hashCode();
    }

    @Override // org.joda.time.DurationField
    public final boolean isPrecise() {
        return true;
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2, long j3) {
        return FieldUtils.safeMultiply(j2, this.iUnitMillis);
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, long j3) {
        return FieldUtils.safeAdd(j2, FieldUtils.safeMultiply(j3, this.iUnitMillis));
    }
}
