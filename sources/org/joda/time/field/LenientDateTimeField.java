package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;

/* loaded from: classes5.dex */
public class LenientDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = 8714085824173290599L;
    private final Chronology iBase;

    protected LenientDateTimeField(DateTimeField dateTimeField, Chronology chronology) {
        super(dateTimeField);
        this.iBase = chronology;
    }

    public static DateTimeField getInstance(DateTimeField dateTimeField, Chronology chronology) {
        if (dateTimeField == null) {
            return null;
        }
        if (dateTimeField instanceof StrictDateTimeField) {
            dateTimeField = ((StrictDateTimeField) dateTimeField).getWrappedField();
        }
        return dateTimeField.isLenient() ? dateTimeField : new LenientDateTimeField(dateTimeField, chronology);
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public final boolean isLenient() {
        return true;
    }

    @Override // org.joda.time.field.DelegatedDateTimeField, org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        return this.iBase.getZone().convertLocalToUTC(getType().getField(this.iBase.withUTC()).add(this.iBase.getZone().convertUTCToLocal(j2), FieldUtils.safeSubtract(i2, get(j2))), false, j2);
    }
}
