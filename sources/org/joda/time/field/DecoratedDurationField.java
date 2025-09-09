package org.joda.time.field;

import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

/* loaded from: classes5.dex */
public class DecoratedDurationField extends BaseDurationField {
    private static final long serialVersionUID = 8019982251647420015L;
    private final DurationField iField;

    public DecoratedDurationField(DurationField durationField, DurationFieldType durationFieldType) {
        super(durationFieldType);
        if (durationField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (!durationField.isSupported()) {
            throw new IllegalArgumentException("The field must be supported");
        }
        this.iField = durationField;
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, int i2) {
        return this.iField.add(j2, i2);
    }

    @Override // org.joda.time.DurationField
    public long getDifferenceAsLong(long j2, long j3) {
        return this.iField.getDifferenceAsLong(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2, long j2) {
        return this.iField.getMillis(i2, j2);
    }

    @Override // org.joda.time.DurationField
    public long getUnitMillis() {
        return this.iField.getUnitMillis();
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2, long j3) {
        return this.iField.getValueAsLong(j2, j3);
    }

    public final DurationField getWrappedField() {
        return this.iField;
    }

    @Override // org.joda.time.DurationField
    public boolean isPrecise() {
        return this.iField.isPrecise();
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, long j3) {
        return this.iField.add(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2, long j3) {
        return this.iField.getMillis(j2, j3);
    }
}
