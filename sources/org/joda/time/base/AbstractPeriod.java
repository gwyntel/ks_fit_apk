package org.joda.time.base;

import org.joda.convert.ToString;
import org.joda.time.DurationFieldType;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/* loaded from: classes5.dex */
public abstract class AbstractPeriod implements ReadablePeriod {
    protected AbstractPeriod() {
    }

    @Override // org.joda.time.ReadablePeriod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReadablePeriod)) {
            return false;
        }
        ReadablePeriod readablePeriod = (ReadablePeriod) obj;
        if (size() != readablePeriod.size()) {
            return false;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (getValue(i2) != readablePeriod.getValue(i2) || getFieldType(i2) != readablePeriod.getFieldType(i2)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.joda.time.ReadablePeriod
    public int get(DurationFieldType durationFieldType) {
        int iIndexOf = indexOf(durationFieldType);
        if (iIndexOf == -1) {
            return 0;
        }
        return getValue(iIndexOf);
    }

    @Override // org.joda.time.ReadablePeriod
    public DurationFieldType getFieldType(int i2) {
        return getPeriodType().getFieldType(i2);
    }

    public DurationFieldType[] getFieldTypes() {
        int size = size();
        DurationFieldType[] durationFieldTypeArr = new DurationFieldType[size];
        for (int i2 = 0; i2 < size; i2++) {
            durationFieldTypeArr[i2] = getFieldType(i2);
        }
        return durationFieldTypeArr;
    }

    public int[] getValues() {
        int size = size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = getValue(i2);
        }
        return iArr;
    }

    @Override // org.joda.time.ReadablePeriod
    public int hashCode() {
        int size = size();
        int value = 17;
        for (int i2 = 0; i2 < size; i2++) {
            value = (((value * 27) + getValue(i2)) * 27) + getFieldType(i2).hashCode();
        }
        return value;
    }

    public int indexOf(DurationFieldType durationFieldType) {
        return getPeriodType().indexOf(durationFieldType);
    }

    @Override // org.joda.time.ReadablePeriod
    public boolean isSupported(DurationFieldType durationFieldType) {
        return getPeriodType().isSupported(durationFieldType);
    }

    @Override // org.joda.time.ReadablePeriod
    public int size() {
        return getPeriodType().size();
    }

    @Override // org.joda.time.ReadablePeriod
    public MutablePeriod toMutablePeriod() {
        return new MutablePeriod(this);
    }

    @Override // org.joda.time.ReadablePeriod
    public Period toPeriod() {
        return new Period(this);
    }

    @Override // org.joda.time.ReadablePeriod
    @ToString
    public String toString() {
        return ISOPeriodFormat.standard().print(this);
    }

    public String toString(PeriodFormatter periodFormatter) {
        return periodFormatter == null ? toString() : periodFormatter.print(this);
    }
}
