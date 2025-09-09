package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

/* loaded from: classes5.dex */
public final class MillisDurationField extends DurationField implements Serializable {
    public static final DurationField INSTANCE = new MillisDurationField();
    private static final long serialVersionUID = 2656707858124633367L;

    private MillisDurationField() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, int i2) {
        return FieldUtils.safeAdd(j2, i2);
    }

    public boolean equals(Object obj) {
        return (obj instanceof MillisDurationField) && getUnitMillis() == ((MillisDurationField) obj).getUnitMillis();
    }

    @Override // org.joda.time.DurationField
    public int getDifference(long j2, long j3) {
        return FieldUtils.safeToInt(FieldUtils.safeSubtract(j2, j3));
    }

    @Override // org.joda.time.DurationField
    public long getDifferenceAsLong(long j2, long j3) {
        return FieldUtils.safeSubtract(j2, j3);
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2) {
        return i2;
    }

    @Override // org.joda.time.DurationField
    public String getName() {
        return "millis";
    }

    @Override // org.joda.time.DurationField
    public DurationFieldType getType() {
        return DurationFieldType.millis();
    }

    @Override // org.joda.time.DurationField
    public final long getUnitMillis() {
        return 1L;
    }

    @Override // org.joda.time.DurationField
    public int getValue(long j2) {
        return FieldUtils.safeToInt(j2);
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2) {
        return j2;
    }

    public int hashCode() {
        return (int) getUnitMillis();
    }

    @Override // org.joda.time.DurationField
    public final boolean isPrecise() {
        return true;
    }

    @Override // org.joda.time.DurationField
    public boolean isSupported() {
        return true;
    }

    @Override // org.joda.time.DurationField
    public String toString() {
        return "DurationField[millis]";
    }

    @Override // org.joda.time.DurationField
    public long add(long j2, long j3) {
        return FieldUtils.safeAdd(j2, j3);
    }

    @Override // java.lang.Comparable
    public int compareTo(DurationField durationField) {
        long unitMillis = durationField.getUnitMillis();
        long unitMillis2 = getUnitMillis();
        if (unitMillis2 == unitMillis) {
            return 0;
        }
        return unitMillis2 < unitMillis ? -1 : 1;
    }

    @Override // org.joda.time.DurationField
    public long getMillis(int i2, long j2) {
        return i2;
    }

    @Override // org.joda.time.DurationField
    public int getValue(long j2, long j3) {
        return FieldUtils.safeToInt(j2);
    }

    @Override // org.joda.time.DurationField
    public long getValueAsLong(long j2, long j3) {
        return j2;
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2) {
        return j2;
    }

    @Override // org.joda.time.DurationField
    public long getMillis(long j2, long j3) {
        return j2;
    }
}
