package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.SkipDateTimeField;

/* loaded from: classes5.dex */
public final class JulianChronology extends BasicGJChronology {
    private static final int MAX_YEAR = 292272992;
    private static final long MILLIS_PER_MONTH = 2629800000L;
    private static final long MILLIS_PER_YEAR = 31557600000L;
    private static final int MIN_YEAR = -292269054;
    private static final long serialVersionUID = -8731039522547897247L;
    private static final Map<DateTimeZone, JulianChronology[]> cCache = new HashMap();
    private static final JulianChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    JulianChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
    }

    static int adjustYearForSet(int i2) {
        if (i2 > 0) {
            return i2;
        }
        if (i2 != 0) {
            return i2 + 1;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.year(), Integer.valueOf(i2), (Number) null, (Number) null);
    }

    public static JulianChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static JulianChronology getInstanceUTC() {
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
        }
    }

    @Override // org.joda.time.chrono.BasicChronology
    long calculateFirstDayOfYearMillis(int i2) {
        int i3;
        int i4 = i2 - 1968;
        if (i4 <= 0) {
            i3 = (i2 - 1965) >> 2;
        } else {
            int i5 = i4 >> 2;
            i3 = !isLeapYear(i2) ? i5 + 1 : i5;
        }
        return (((i4 * 365) + i3) * 86400000) - 62035200000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getApproxMillisAtEpochDividedByTwo() {
        return 31083663600000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerMonth() {
        return MILLIS_PER_MONTH;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerYear() {
        return MILLIS_PER_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getAverageMillisPerYearDividedByTwo() {
        return 15778800000L;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getDateMidnightMillis(int i2, int i3, int i4) throws IllegalArgumentException {
        return super.getDateMidnightMillis(adjustYearForSet(i2), i3, i4);
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMaxYear() {
        return MAX_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getMinYear() {
        return MIN_YEAR;
    }

    @Override // org.joda.time.chrono.BasicChronology
    boolean isLeapYear(int i2) {
        return (i2 & 3) == 0;
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

    public static JulianChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static JulianChronology getInstance(DateTimeZone dateTimeZone, int i2) {
        JulianChronology julianChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        Map<DateTimeZone, JulianChronology[]> map = cCache;
        synchronized (map) {
            JulianChronology[] julianChronologyArr = map.get(dateTimeZone);
            if (julianChronologyArr == null) {
                julianChronologyArr = new JulianChronology[7];
                map.put(dateTimeZone, julianChronologyArr);
            }
            int i3 = i2 - 1;
            try {
                julianChronology = julianChronologyArr[i3];
                if (julianChronology == null) {
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (dateTimeZone == dateTimeZone2) {
                        julianChronology = new JulianChronology(null, null, i2);
                    } else {
                        julianChronology = new JulianChronology(ZonedChronology.getInstance(getInstance(dateTimeZone2, i2), dateTimeZone), null, i2);
                    }
                    julianChronologyArr[i3] = julianChronology;
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i2);
            }
        }
        return julianChronology;
    }
}
