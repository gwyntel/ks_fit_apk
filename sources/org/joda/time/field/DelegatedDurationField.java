package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

/* loaded from: classes5.dex */
public class DelegatedDurationField extends DurationField implements Serializable {
    private static final long serialVersionUID = -5576443481242007829L;
    private final DurationField iField;
    private final DurationFieldType iType;

    protected DelegatedDurationField(DurationField durationField) {
        this(durationField, null);
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, int i2) {
        return this.iField.add(j2, i2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DelegatedDurationField) {
            return this.iField.equals(((DelegatedDurationField) obj).iField);
        }
        return false;
    }

    @Override // org.joda.time.DurationField
    public int getDifference(long j2, long j3) {
        return this.iField.getDifference(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getDifferenceAsLong(long j2, long j3) {
        return this.iField.getDifferenceAsLong(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2) {
        return this.iField.getMillis(i2);
    }

    @Override // org.joda.time.DurationField
    public String getName() {
        return this.iType.getName();
    }

    @Override // org.joda.time.DurationField
    public DurationFieldType getType() {
        return this.iType;
    }

    @Override // org.joda.time.DurationField
    public long getUnitMillis() {
        return this.iField.getUnitMillis();
    }

    @Override // org.joda.time.DurationField
    public int getValue(long j2) {
        return this.iField.getValue(j2);
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2) {
        return this.iField.getValueAsLong(j2);
    }

    public final DurationField getWrappedField() {
        return this.iField;
    }

    public int hashCode() {
        return this.iField.hashCode() ^ this.iType.hashCode();
    }

    @Override // org.joda.time.DurationField
    public boolean isPrecise() {
        return this.iField.isPrecise();
    }

    @Override // org.joda.time.DurationField
    public boolean isSupported() {
        return this.iField.isSupported();
    }

    @Override // org.joda.time.DurationField
    public String toString() {
        if (this.iType == null) {
            return this.iField.toString();
        }
        return "DurationField[" + this.iType + ']';
    }

    protected DelegatedDurationField(DurationField durationField, DurationFieldType durationFieldType) {
        if (durationField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        this.iField = durationField;
        this.iType = durationFieldType == null ? durationField.getType() : durationFieldType;
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, long j3) {
        return this.iField.add(j2, j3);
    }

    @Override // java.lang.Comparable
    public int compareTo(DurationField durationField) {
        return this.iField.compareTo(durationField);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2) {
        return this.iField.getMillis(j2);
    }

    @Override // org.joda.time.DurationField
    public int getValue(long j2, long j3) {
        return this.iField.getValue(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2, long j3) {
        return this.iField.getValueAsLong(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2, long j2) {
        return this.iField.getMillis(i2, j2);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2, long j3) {
        return this.iField.getMillis(j2, j3);
    }
}
