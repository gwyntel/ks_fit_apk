package org.joda.time;

import com.alipay.sdk.m.w.a;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.joda.time.chrono.ISOChronology;

/* loaded from: classes5.dex */
public class DateTimeUtils {
    private static final SystemMillisProvider SYSTEM_MILLIS_PROVIDER;
    private static volatile MillisProvider cMillisProvider;
    private static volatile Map<String, DateTimeZone> cZoneNames;

    static class FixedMillisProvider implements MillisProvider {
        private final long iMillis;

        FixedMillisProvider(long j2) {
            this.iMillis = j2;
        }

        @Override // org.joda.time.DateTimeUtils.MillisProvider
        public long getMillis() {
            return this.iMillis;
        }
    }

    public interface MillisProvider {
        long getMillis();
    }

    static class OffsetMillisProvider implements MillisProvider {
        private final long iMillis;

        OffsetMillisProvider(long j2) {
            this.iMillis = j2;
        }

        @Override // org.joda.time.DateTimeUtils.MillisProvider
        public long getMillis() {
            return System.currentTimeMillis() + this.iMillis;
        }
    }

    static class SystemMillisProvider implements MillisProvider {
        SystemMillisProvider() {
        }

        @Override // org.joda.time.DateTimeUtils.MillisProvider
        public long getMillis() {
            return System.currentTimeMillis();
        }
    }

    static {
        SystemMillisProvider systemMillisProvider = new SystemMillisProvider();
        SYSTEM_MILLIS_PROVIDER = systemMillisProvider;
        cMillisProvider = systemMillisProvider;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        DateTimeZone dateTimeZone = DateTimeZone.UTC;
        linkedHashMap.put("UT", dateTimeZone);
        linkedHashMap.put("UTC", dateTimeZone);
        linkedHashMap.put("GMT", dateTimeZone);
        put(linkedHashMap, "EST", "America/New_York");
        put(linkedHashMap, "EDT", "America/New_York");
        put(linkedHashMap, "CST", "America/Chicago");
        put(linkedHashMap, a.f9829a, "America/Chicago");
        put(linkedHashMap, "MST", "America/Denver");
        put(linkedHashMap, "MDT", "America/Denver");
        put(linkedHashMap, "PST", "America/Los_Angeles");
        put(linkedHashMap, "PDT", "America/Los_Angeles");
        cZoneNames = Collections.unmodifiableMap(linkedHashMap);
    }

    private static void checkPermission() throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("CurrentTime.setProvider"));
        }
    }

    public static final long currentTimeMillis() {
        return cMillisProvider.getMillis();
    }

    public static final long fromJulianDay(double d2) {
        return (long) ((d2 - 2440587.5d) * 8.64E7d);
    }

    public static final Chronology getChronology(Chronology chronology) {
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final DateFormatSymbols getDateFormatSymbols(Locale locale) {
        try {
            return (DateFormatSymbols) DateFormatSymbols.class.getMethod("getInstance", Locale.class).invoke(null, locale);
        } catch (Exception unused) {
            return new DateFormatSymbols(locale);
        }
    }

    public static final Map<String, DateTimeZone> getDefaultTimeZoneNames() {
        return cZoneNames;
    }

    public static final long getDurationMillis(ReadableDuration readableDuration) {
        if (readableDuration == null) {
            return 0L;
        }
        return readableDuration.getMillis();
    }

    public static final Chronology getInstantChronology(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return ISOChronology.getInstance();
        }
        Chronology chronology = readableInstant.getChronology();
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final long getInstantMillis(ReadableInstant readableInstant) {
        return readableInstant == null ? currentTimeMillis() : readableInstant.getMillis();
    }

    public static final Chronology getIntervalChronology(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        Chronology chronology = readableInstant != null ? readableInstant.getChronology() : readableInstant2 != null ? readableInstant2.getChronology() : null;
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final PeriodType getPeriodType(PeriodType periodType) {
        return periodType == null ? PeriodType.standard() : periodType;
    }

    public static final ReadableInterval getReadableInterval(ReadableInterval readableInterval) {
        if (readableInterval != null) {
            return readableInterval;
        }
        long jCurrentTimeMillis = currentTimeMillis();
        return new Interval(jCurrentTimeMillis, jCurrentTimeMillis);
    }

    public static final DateTimeZone getZone(DateTimeZone dateTimeZone) {
        return dateTimeZone == null ? DateTimeZone.getDefault() : dateTimeZone;
    }

    public static final boolean isContiguous(ReadablePartial readablePartial) {
        if (readablePartial == null) {
            throw new IllegalArgumentException("Partial must not be null");
        }
        DurationFieldType type = null;
        for (int i2 = 0; i2 < readablePartial.size(); i2++) {
            DateTimeField field = readablePartial.getField(i2);
            if (i2 > 0 && field.getRangeDurationField().getType() != type) {
                return false;
            }
            type = field.getDurationField().getType();
        }
        return true;
    }

    private static void put(Map<String, DateTimeZone> map, String str, String str2) {
        try {
            map.put(str, DateTimeZone.forID(str2));
        } catch (RuntimeException unused) {
        }
    }

    public static final void setCurrentMillisFixed(long j2) throws SecurityException {
        checkPermission();
        cMillisProvider = new FixedMillisProvider(j2);
    }

    public static final void setCurrentMillisOffset(long j2) throws SecurityException {
        checkPermission();
        if (j2 == 0) {
            cMillisProvider = SYSTEM_MILLIS_PROVIDER;
        } else {
            cMillisProvider = new OffsetMillisProvider(j2);
        }
    }

    public static final void setCurrentMillisProvider(MillisProvider millisProvider) throws SecurityException {
        if (millisProvider == null) {
            throw new IllegalArgumentException("The MillisProvider must not be null");
        }
        checkPermission();
        cMillisProvider = millisProvider;
    }

    public static final void setCurrentMillisSystem() throws SecurityException {
        checkPermission();
        cMillisProvider = SYSTEM_MILLIS_PROVIDER;
    }

    public static final void setDefaultTimeZoneNames(Map<String, DateTimeZone> map) {
        cZoneNames = Collections.unmodifiableMap(new HashMap(map));
    }

    public static final double toJulianDay(long j2) {
        return (j2 / 8.64E7d) + 2440587.5d;
    }

    public static final long toJulianDayNumber(long j2) {
        return (long) Math.floor(toJulianDay(j2) + 0.5d);
    }

    public static final Chronology getIntervalChronology(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ISOChronology.getInstance();
        }
        Chronology chronology = readableInterval.getChronology();
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }
}
