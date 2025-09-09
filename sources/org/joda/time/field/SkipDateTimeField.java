package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.IllegalFieldValueException;

/* loaded from: classes5.dex */
public final class SkipDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = -8869148464118507846L;
    private final Chronology iChronology;
    private transient int iMinValue;
    private final int iSkip;

    public SkipDateTimeField(Chronology chronology, DateTimeField dateTimeField) {
        this(chronology, dateTimeField, 0);
    }

    private Object readResolve() {
        return getType().getField(this.iChronology);
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        int i2 = super.get(j2);
        return i2 <= this.iSkip ? i2 - 1 : i2;
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return this.iMinValue;
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, this.iMinValue, getMaximumValue());
        int i3 = this.iSkip;
        if (i2 <= i3) {
            if (i2 == i3) {
                throw new IllegalFieldValueException(DateTimeFieldType.year(), Integer.valueOf(i2), (Number) null, (Number) null);
            }
            i2++;
        }
        return super.set(j2, i2);
    }

    public SkipDateTimeField(Chronology chronology, DateTimeField dateTimeField, int i2) {
        super(dateTimeField);
        this.iChronology = chronology;
        int minimumValue = super.getMinimumValue();
        if (minimumValue < i2) {
            this.iMinValue = minimumValue - 1;
        } else if (minimumValue == i2) {
            this.iMinValue = i2 + 1;
        } else {
            this.iMinValue = minimumValue;
        }
        this.iSkip = i2;
    }
}
