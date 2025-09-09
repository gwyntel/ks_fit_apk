package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;

/* loaded from: classes5.dex */
public final class GregorianChronology extends BasicGJChronology {
    private static final int DAYS_0000_TO_1970 = 719527;
    private static final int MAX_YEAR = 292278993;
    private static final long MILLIS_PER_MONTH = 2629746000L;
    private static final long MILLIS_PER_YEAR = 31556952000L;
    private static final int MIN_YEAR = -292275054;
    private static final long serialVersionUID = -861407383323710522L;
    private static final Map<DateTimeZone, GregorianChronology[]> cCache = new HashMap();
    private static final GregorianChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);

    private GregorianChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
    }

    public static GregorianChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static GregorianChronology getInstanceUTC() {
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
        }
    }

    @Override // org.joda.time.chrono.BasicChronology
    long calculateFirstDayOfYearMillis(int i2) {
        int i3;
        int i4 = i2 / 100;
        if (i2 < 0) {
            i3 = ((((i2 + 3) >> 2) - i4) + ((i4 + 3) >> 2)) - 1;
        } else {
            i3 = ((i2 >> 2) - i4) + (i4 >> 2);
            if (isLeapYear(i2)) {
                i3--;
            }
        }
        return ((i2 * 365) + (i3 - DAYS_0000_TO_1970)) * 86400000;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getApproxMillisAtEpochDividedByTwo() {
        return 31083597720000L;
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
        return 15778476000L;
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
        return (i2 & 3) == 0 && (i2 % 100 != 0 || i2 % 400 == 0);
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

    public static GregorianChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static GregorianChronology getInstance(DateTimeZone dateTimeZone, int i2) {
        GregorianChronology gregorianChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        Map<DateTimeZone, GregorianChronology[]> map = cCache;
        synchronized (map) {
            GregorianChronology[] gregorianChronologyArr = map.get(dateTimeZone);
            if (gregorianChronologyArr == null) {
                gregorianChronologyArr = new GregorianChronology[7];
                map.put(dateTimeZone, gregorianChronologyArr);
            }
            int i3 = i2 - 1;
            try {
                gregorianChronology = gregorianChronologyArr[i3];
                if (gregorianChronology == null) {
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (dateTimeZone == dateTimeZone2) {
                        gregorianChronology = new GregorianChronology(null, null, i2);
                    } else {
                        gregorianChronology = new GregorianChronology(ZonedChronology.getInstance(getInstance(dateTimeZone2, i2), dateTimeZone), null, i2);
                    }
                    gregorianChronologyArr[i3] = gregorianChronology;
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i2);
            }
        }
        return gregorianChronology;
    }
}
