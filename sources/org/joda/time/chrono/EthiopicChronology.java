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
public final class EthiopicChronology extends BasicFixedMonthChronology {
    public static final int EE = 1;
    private static final int MAX_YEAR = 292272984;
    private static final int MIN_YEAR = -292269337;
    private static final long serialVersionUID = -5972804258688333942L;
    private static final DateTimeField ERA_FIELD = new BasicSingleEraDateTimeField("EE");
    private static final Map<DateTimeZone, EthiopicChronology[]> cCache = new HashMap();
    private static final EthiopicChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    EthiopicChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
    }

    public static EthiopicChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static EthiopicChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    private Object readResolve() {
        Chronology base = getBase();
        return getInstance(base == null ? DateTimeZone.UTC : base.getZone(), getMinimumDaysInFirstWeek());
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
        int i4 = i2 - 1963;
        if (i4 <= 0) {
            i3 = (i2 - 1960) >> 2;
        } else {
            int i5 = i4 >> 2;
            i3 = !isLeapYear(i2) ? i5 + 1 : i5;
        }
        return (((i4 * 365) + i3) * 86400000) + 21859200000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getApproxMillisAtEpochDividedByTwo() {
        return 30962844000000L;
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

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone, int i2) {
        EthiopicChronology ethiopicChronology;
        EthiopicChronology ethiopicChronology2;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        Map<DateTimeZone, EthiopicChronology[]> map = cCache;
        synchronized (map) {
            EthiopicChronology[] ethiopicChronologyArr = map.get(dateTimeZone);
            if (ethiopicChronologyArr == null) {
                ethiopicChronologyArr = new EthiopicChronology[7];
                map.put(dateTimeZone, ethiopicChronologyArr);
            }
            int i3 = i2 - 1;
            try {
                ethiopicChronology = ethiopicChronologyArr[i3];
                if (ethiopicChronology == null) {
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (dateTimeZone == dateTimeZone2) {
                        EthiopicChronology ethiopicChronology3 = new EthiopicChronology(null, null, i2);
                        ethiopicChronology2 = new EthiopicChronology(LimitChronology.getInstance(ethiopicChronology3, new DateTime(1, 1, 1, 0, 0, 0, 0, ethiopicChronology3), null), null, i2);
                    } else {
                        ethiopicChronology2 = new EthiopicChronology(ZonedChronology.getInstance(getInstance(dateTimeZone2, i2), dateTimeZone), null, i2);
                    }
                    ethiopicChronology = ethiopicChronology2;
                    ethiopicChronologyArr[i3] = ethiopicChronology;
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i2);
            }
        }
        return ethiopicChronology;
    }
}
