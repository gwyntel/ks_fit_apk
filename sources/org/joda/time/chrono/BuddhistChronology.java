package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.DelegatedDateTimeField;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.SkipUndoDateTimeField;

/* loaded from: classes5.dex */
public final class BuddhistChronology extends AssembledChronology {
    public static final int BE = 1;
    private static final int BUDDHIST_OFFSET = 543;
    private static final long serialVersionUID = -3474595157769370126L;
    private static final DateTimeField ERA_FIELD = new BasicSingleEraDateTimeField("BE");
    private static final Map<DateTimeZone, BuddhistChronology> cCache = new HashMap();
    private static final BuddhistChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    private BuddhistChronology(Chronology chronology, Object obj) {
        super(chronology, obj);
    }

    public static BuddhistChronology getInstance() {
        return getInstance(DateTimeZone.getDefault());
    }

    public static BuddhistChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    private Object readResolve() {
        Chronology base = getBase();
        return base == null ? getInstanceUTC() : getInstance(base.getZone());
    }

    @Override // org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        if (getParam() == null) {
            OffsetDateTimeField offsetDateTimeField = new OffsetDateTimeField(new SkipUndoDateTimeField(this, fields.year), BUDDHIST_OFFSET);
            fields.year = offsetDateTimeField;
            fields.yearOfEra = new DelegatedDateTimeField(offsetDateTimeField, DateTimeFieldType.yearOfEra());
            fields.weekyear = new OffsetDateTimeField(new SkipUndoDateTimeField(this, fields.weekyear), BUDDHIST_OFFSET);
            DividedDateTimeField dividedDateTimeField = new DividedDateTimeField(new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
            fields.centuryOfEra = dividedDateTimeField;
            fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(dividedDateTimeField), DateTimeFieldType.yearOfCentury(), 1);
            fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
            fields.era = ERA_FIELD;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BuddhistChronology) {
            return getZone().equals(((BuddhistChronology) obj).getZone());
        }
        return false;
    }

    public int hashCode() {
        return 499287079 + getZone().hashCode();
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        DateTimeZone zone = getZone();
        if (zone == null) {
            return "BuddhistChronology";
        }
        return "BuddhistChronology[" + zone.getID() + ']';
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getZone() ? this : getInstance(dateTimeZone);
    }

    public static synchronized BuddhistChronology getInstance(DateTimeZone dateTimeZone) {
        BuddhistChronology buddhistChronology;
        if (dateTimeZone == null) {
            try {
                dateTimeZone = DateTimeZone.getDefault();
            } catch (Throwable th) {
                throw th;
            }
        }
        Map<DateTimeZone, BuddhistChronology> map = cCache;
        synchronized (map) {
            try {
                buddhistChronology = map.get(dateTimeZone);
                if (buddhistChronology == null) {
                    BuddhistChronology buddhistChronology2 = new BuddhistChronology(GJChronology.getInstance(dateTimeZone, null), null);
                    BuddhistChronology buddhistChronology3 = new BuddhistChronology(LimitChronology.getInstance(buddhistChronology2, new DateTime(1, 1, 1, 0, 0, 0, 0, buddhistChronology2), null), "");
                    map.put(dateTimeZone, buddhistChronology3);
                    buddhistChronology = buddhistChronology3;
                }
            } finally {
            }
        }
        return buddhistChronology;
    }
}
