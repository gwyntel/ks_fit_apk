package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.SkipDateTimeField;

/* loaded from: classes5.dex */
public final class CopticChronology extends BasicFixedMonthChronology {
    public static final int AM = 1;
    private static final int MAX_YEAR = 292272708;
    private static final int MIN_YEAR = -292269337;
    private static final long serialVersionUID = -5972804258688333942L;
    private static final DateTimeField ERA_FIELD = new BasicSingleEraDateTimeField("AM");
    private static final Map<DateTimeZone, CopticChronology[]> cCache = new HashMap();
    private static final CopticChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    CopticChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
    }

    public static CopticChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static CopticChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    private Object readResolve() {
        Chronology base = getBase();
        int minimumDaysInFirstWeek = getMinimumDaysInFirstWeek();
        if (minimumDaysInFirstWeek == 0) {
            minimumDaysInFirstWeek = 4;
        }
        return getInstance(base == null ? DateTimeZone.UTC : base.getZone(), minimumDaysInFirstWeek);
    }

    @Override // org.joda.time.chrono.BasicChronology, org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.year = new SkipDateTimeField(this, fields.year);
            fields.weekyear = new SkipDateTimeField(this, fields.weekyear);
            fields.era = ERA_FIELD;
            BasicMonthOfYearDateTimeField basicMonthOfYearDateTimeField = new BasicMonthOfYearDateTimeField(this, 13);
            fields.monthOfYear = basicMonthOfYearDateTimeField;
            fields.months = basicMonthOfYearDateTimeField.getDurationField();
        }
    }

    @Override // org.joda.time.chrono.BasicChronology
    long calculateFirstDayOfYearMillis(int i2) {
        int i3;
        int i4 = i2 - 1687;
        if (i4 <= 0) {
            i3 = (i2 - 1684) >> 2;
        } else {
            int i5 = i4 >> 2;
            i3 = !isLeapYear(i2) ? i5 + 1 : i5;
        }
        return (((i4 * 365) + i3) * 86400000) + 21859200000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getApproxMillisAtEpochDividedByTwo() {
        return 26607895200000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMaxYear() {
        return MAX_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMinYear() {
        return MIN_YEAR;
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

    public static CopticChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static CopticChronology getInstance(DateTimeZone dateTimeZone, int i2) {
        CopticChronology copticChronology;
        CopticChronology copticChronology2;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        Map<DateTimeZone, CopticChronology[]> map = cCache;
        synchronized (map) {
            CopticChronology[] copticChronologyArr = map.get(dateTimeZone);
            if (copticChronologyArr == null) {
                copticChronologyArr = new CopticChronology[7];
                map.put(dateTimeZone, copticChronologyArr);
            }
            int i3 = i2 - 1;
            try {
                copticChronology = copticChronologyArr[i3];
                if (copticChronology == null) {
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (dateTimeZone == dateTimeZone2) {
                        CopticChronology copticChronology3 = new CopticChronology(null, null, i2);
                        copticChronology2 = new CopticChronology(LimitChronology.getInstance(copticChronology3, new DateTime(1, 1, 1, 0, 0, 0, 0, copticChronology3), null), null, i2);
                    } else {
                        copticChronology2 = new CopticChronology(ZonedChronology.getInstance(getInstance(dateTimeZone2, i2), dateTimeZone), null, i2);
                    }
                    copticChronology = copticChronology2;
                    copticChronologyArr[i3] = copticChronology;
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i2);
            }
        }
        return copticChronology;
    }
}
