package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;

/* loaded from: classes5.dex */
public final class SkipUndoDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = -5875876968979L;
    private final Chronology iChronology;
    private transient int iMinValue;
    private final int iSkip;

    public SkipUndoDateTimeField(Chronology chronology, DateTimeField dateTimeField) {
        this(chronology, dateTimeField, 0);
    }

    private Object readResolve() {
        return getType().getField(this.iChronology);
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public int get(long j2) {
        int i2 = super.get(j2);
        return i2 < this.iSkip ? i2 + 1 : i2;
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public int getMinimumValue() {
        return this.iMinValue;
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        FieldUtils.verifyValueBounds(this, i2, this.iMinValue, getMaximumValue());
        if (i2 <= this.iSkip) {
            i2--;
        }
        return super.set(j2, i2);
    }

    public SkipUndoDateTimeField(Chronology chronology, DateTimeField dateTimeField, int i2) {
        super(dateTimeField);
        this.iChronology = chronology;
        int minimumValue = super.getMinimumValue();
        if (minimumValue < i2) {
            this.iMinValue = minimumValue + 1;
        } else if (minimumValue == i2 + 1) {
            this.iMinValue = i2;
        } else {
            this.iMinValue = minimumValue;
        }
        this.iSkip = i2;
    }
}
