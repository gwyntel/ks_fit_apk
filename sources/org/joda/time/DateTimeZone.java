package org.joda.time;

import com.google.common.net.HttpHeaders;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.chrono.BaseChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.FormatUtils;
import org.joda.time.tz.DefaultNameProvider;
import org.joda.time.tz.FixedDateTimeZone;
import org.joda.time.tz.NameProvider;
import org.joda.time.tz.Provider;
import org.joda.time.tz.UTCProvider;
import org.joda.time.tz.ZoneInfoProvider;

/* loaded from: classes5.dex */
public abstract class DateTimeZone implements Serializable {
    private static final int MAX_MILLIS = 86399999;
    public static final DateTimeZone UTC = new FixedDateTimeZone("UTC", "UTC", 0, 0);
    private static Set<String> cAvailableIDs = null;
    private static volatile DateTimeZone cDefault = null;
    private static NameProvider cNameProvider = null;
    private static DateTimeFormatter cOffsetFormatter = null;
    private static Provider cProvider = null;
    private static Map<String, String> cZoneIdConversion = null;
    private static Map<String, SoftReference<DateTimeZone>> iFixedOffsetCache = null;
    private static final long serialVersionUID = 5546345482340108586L;
    private final String iID;

    private static final class Stub implements Serializable {
        private static final long serialVersionUID = -6471952376487863581L;
        private transient String iID;

        Stub(String str) {
            this.iID = str;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            this.iID = objectInputStream.readUTF();
        }

        private Object readResolve() throws ObjectStreamException {
            return DateTimeZone.forID(this.iID);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeUTF(this.iID);
        }
    }

    static {
        setProvider0(null);
        setNameProvider0(null);
    }

    protected DateTimeZone(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.iID = str;
    }

    private static synchronized DateTimeZone fixedOffsetZone(String str, int i2) {
        DateTimeZone dateTimeZone;
        if (i2 == 0) {
            return UTC;
        }
        if (iFixedOffsetCache == null) {
            iFixedOffsetCache = new HashMap();
        }
        SoftReference<DateTimeZone> softReference = iFixedOffsetCache.get(str);
        if (softReference != null && (dateTimeZone = softReference.get()) != null) {
            return dateTimeZone;
        }
        FixedDateTimeZone fixedDateTimeZone = new FixedDateTimeZone(str, null, i2, i2);
        iFixedOffsetCache.put(str, new SoftReference<>(fixedDateTimeZone));
        return fixedDateTimeZone;
    }

    @FromString
    public static DateTimeZone forID(String str) {
        if (str == null) {
            return getDefault();
        }
        if (str.equals("UTC")) {
            return UTC;
        }
        DateTimeZone zone = cProvider.getZone(str);
        if (zone != null) {
            return zone;
        }
        if (str.startsWith(MqttTopic.SINGLE_LEVEL_WILDCARD) || str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
            int offset = parseOffset(str);
            return ((long) offset) == 0 ? UTC : fixedOffsetZone(printOffset(offset), offset);
        }
        throw new IllegalArgumentException("The datetime zone id '" + str + "' is not recognised");
    }

    public static DateTimeZone forOffsetHours(int i2) throws IllegalArgumentException {
        return forOffsetHoursMinutes(i2, 0);
    }

    public static DateTimeZone forOffsetHoursMinutes(int i2, int i3) throws IllegalArgumentException {
        if (i2 == 0 && i3 == 0) {
            return UTC;
        }
        if (i2 < -23 || i2 > 23) {
            throw new IllegalArgumentException("Hours out of range: " + i2);
        }
        if (i3 < -59 || i3 > 59) {
            throw new IllegalArgumentException("Minutes out of range: " + i3);
        }
        if (i2 <= 0 || i3 >= 0) {
            int i4 = i2 * 60;
            try {
                return forOffsetMillis(FieldUtils.safeMultiply(i4 < 0 ? i4 - Math.abs(i3) : i4 + i3, 60000));
            } catch (ArithmeticException unused) {
                throw new IllegalArgumentException("Offset is too large");
            }
        }
        throw new IllegalArgumentException("Positive hours must not have negative minutes: " + i3);
    }

    public static DateTimeZone forOffsetMillis(int i2) {
        if (i2 >= -86399999 && i2 <= MAX_MILLIS) {
            return fixedOffsetZone(printOffset(i2), i2);
        }
        throw new IllegalArgumentException("Millis out of range: " + i2);
    }

    public static DateTimeZone forTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            return getDefault();
        }
        String id = timeZone.getID();
        if (id.equals("UTC")) {
            return UTC;
        }
        String convertedId = getConvertedId(id);
        DateTimeZone zone = convertedId != null ? cProvider.getZone(convertedId) : null;
        if (zone == null) {
            zone = cProvider.getZone(id);
        }
        if (zone != null) {
            return zone;
        }
        if (convertedId == null) {
            String id2 = timeZone.getID();
            if (id2.startsWith("GMT+") || id2.startsWith("GMT-")) {
                int offset = parseOffset(id2.substring(3));
                return ((long) offset) == 0 ? UTC : fixedOffsetZone(printOffset(offset), offset);
            }
        }
        throw new IllegalArgumentException("The datetime zone id '" + id + "' is not recognised");
    }

    public static Set<String> getAvailableIDs() {
        return cAvailableIDs;
    }

    private static synchronized String getConvertedId(String str) {
        Map map;
        try {
            map = cZoneIdConversion;
            if (map == null) {
                map = new HashMap();
                map.put("GMT", "UTC");
                map.put("WET", "WET");
                map.put("CET", "CET");
                map.put("MET", "CET");
                map.put(HttpHeaders.ECT, "CET");
                map.put("EET", "EET");
                map.put("MIT", "Pacific/Apia");
                map.put("HST", "Pacific/Honolulu");
                map.put("AST", "America/Anchorage");
                map.put("PST", "America/Los_Angeles");
                map.put("MST", "America/Denver");
                map.put("PNT", "America/Phoenix");
                map.put("CST", "America/Chicago");
                map.put("EST", "America/New_York");
                map.put("IET", "America/Indiana/Indianapolis");
                map.put("PRT", "America/Puerto_Rico");
                map.put("CNT", "America/St_Johns");
                map.put("AGT", "America/Argentina/Buenos_Aires");
                map.put("BET", "America/Sao_Paulo");
                map.put("ART", "Africa/Cairo");
                map.put("CAT", "Africa/Harare");
                map.put("EAT", "Africa/Addis_Ababa");
                map.put("NET", "Asia/Yerevan");
                map.put("PLT", "Asia/Karachi");
                map.put("IST", "Asia/Kolkata");
                map.put("BST", "Asia/Dhaka");
                map.put("VST", "Asia/Ho_Chi_Minh");
                map.put("CTT", "Asia/Shanghai");
                map.put("JST", "Asia/Tokyo");
                map.put("ACT", "Australia/Darwin");
                map.put("AET", "Australia/Sydney");
                map.put("SST", "Pacific/Guadalcanal");
                map.put("NST", "Pacific/Auckland");
                cZoneIdConversion = map;
            }
        } catch (Throwable th) {
            throw th;
        }
        return (String) map.get(str);
    }

    public static DateTimeZone getDefault() {
        DateTimeZone dateTimeZoneForTimeZone = cDefault;
        if (dateTimeZoneForTimeZone == null) {
            synchronized (DateTimeZone.class) {
                dateTimeZoneForTimeZone = cDefault;
                if (dateTimeZoneForTimeZone == null) {
                    dateTimeZoneForTimeZone = null;
                    try {
                        String property = System.getProperty("user.timezone");
                        if (property != null) {
                            dateTimeZoneForTimeZone = forID(property);
                        }
                    } catch (RuntimeException unused) {
                    }
                    if (dateTimeZoneForTimeZone == null) {
                        try {
                            dateTimeZoneForTimeZone = forTimeZone(TimeZone.getDefault());
                        } catch (IllegalArgumentException unused2) {
                        }
                    }
                    if (dateTimeZoneForTimeZone == null) {
                        dateTimeZoneForTimeZone = UTC;
                    }
                    cDefault = dateTimeZoneForTimeZone;
                }
            }
        }
        return dateTimeZoneForTimeZone;
    }

    private static NameProvider getDefaultNameProvider() {
        NameProvider nameProvider = null;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
            if (property != null) {
                try {
                    nameProvider = (NameProvider) Class.forName(property).newInstance();
                } catch (Exception e2) {
                    Thread threadCurrentThread = Thread.currentThread();
                    threadCurrentThread.getThreadGroup().uncaughtException(threadCurrentThread, e2);
                }
            }
        } catch (SecurityException unused) {
        }
        return nameProvider == null ? new DefaultNameProvider() : nameProvider;
    }

    private static Provider getDefaultProvider() {
        Provider zoneInfoProvider = null;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.Provider");
            if (property != null) {
                try {
                    zoneInfoProvider = (Provider) Class.forName(property).newInstance();
                } catch (Exception e2) {
                    Thread threadCurrentThread = Thread.currentThread();
                    threadCurrentThread.getThreadGroup().uncaughtException(threadCurrentThread, e2);
                }
            }
        } catch (SecurityException unused) {
        }
        if (zoneInfoProvider == null) {
            try {
                zoneInfoProvider = new ZoneInfoProvider("org/joda/time/tz/data");
            } catch (Exception e3) {
                Thread threadCurrentThread2 = Thread.currentThread();
                threadCurrentThread2.getThreadGroup().uncaughtException(threadCurrentThread2, e3);
            }
        }
        return zoneInfoProvider == null ? new UTCProvider() : zoneInfoProvider;
    }

    public static NameProvider getNameProvider() {
        return cNameProvider;
    }

    public static Provider getProvider() {
        return cProvider;
    }

    private static synchronized DateTimeFormatter offsetFormatter() {
        try {
            if (cOffsetFormatter == null) {
                cOffsetFormatter = new DateTimeFormatterBuilder().appendTimeZoneOffset(null, true, 2, 4).toFormatter();
            }
        } catch (Throwable th) {
            throw th;
        }
        return cOffsetFormatter;
    }

    private static int parseOffset(String str) {
        return -((int) offsetFormatter().withChronology(new BaseChronology() { // from class: org.joda.time.DateTimeZone.1
            private static final long serialVersionUID = -3128740902654445468L;

            @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
            public DateTimeZone getZone() {
                return null;
            }

            @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
            public String toString() {
                return getClass().getName();
            }

            @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
            public Chronology withUTC() {
                return this;
            }

            @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
            public Chronology withZone(DateTimeZone dateTimeZone) {
                return this;
            }
        }).parseMillis(str));
    }

    private static String printOffset(int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (i2 >= 0) {
            stringBuffer.append('+');
        } else {
            stringBuffer.append('-');
            i2 = -i2;
        }
        int i3 = i2 / 3600000;
        FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
        int i4 = i2 - (i3 * 3600000);
        int i5 = i4 / 60000;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i5, 2);
        int i6 = i4 - (i5 * 60000);
        if (i6 == 0) {
            return stringBuffer.toString();
        }
        int i7 = i6 / 1000;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i7, 2);
        int i8 = i6 - (i7 * 1000);
        if (i8 == 0) {
            return stringBuffer.toString();
        }
        stringBuffer.append('.');
        FormatUtils.appendPaddedInteger(stringBuffer, i8, 3);
        return stringBuffer.toString();
    }

    public static void setDefault(DateTimeZone dateTimeZone) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setDefault"));
        }
        if (dateTimeZone == null) {
            throw new IllegalArgumentException("The datetime zone must not be null");
        }
        synchronized (DateTimeZone.class) {
            cDefault = dateTimeZone;
        }
    }

    public static void setNameProvider(NameProvider nameProvider) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setNameProvider"));
        }
        setNameProvider0(nameProvider);
    }

    private static void setNameProvider0(NameProvider nameProvider) {
        if (nameProvider == null) {
            nameProvider = getDefaultNameProvider();
        }
        cNameProvider = nameProvider;
    }

    public static void setProvider(Provider provider) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setProvider"));
        }
        setProvider0(provider);
    }

    private static void setProvider0(Provider provider) {
        if (provider == null) {
            provider = getDefaultProvider();
        }
        Set<String> availableIDs = provider.getAvailableIDs();
        if (availableIDs == null || availableIDs.size() == 0) {
            throw new IllegalArgumentException("The provider doesn't have any available ids");
        }
        if (!availableIDs.contains("UTC")) {
            throw new IllegalArgumentException("The provider doesn't support UTC");
        }
        if (!UTC.equals(provider.getZone("UTC"))) {
            throw new IllegalArgumentException("Invalid UTC zone provided");
        }
        cProvider = provider;
        cAvailableIDs = availableIDs;
    }

    public long adjustOffset(long j2, boolean z2) {
        long j3 = j2 - 10800000;
        long offset = getOffset(j3);
        long offset2 = getOffset(10800000 + j2);
        if (offset <= offset2) {
            return j2;
        }
        long j4 = offset - offset2;
        long jNextTransition = nextTransition(j3);
        long j5 = jNextTransition - j4;
        return (j2 < j5 || j2 >= jNextTransition + j4) ? j2 : j2 - j5 >= j4 ? z2 ? j2 : j2 - j4 : z2 ? j2 + j4 : j2;
    }

    public long convertLocalToUTC(long j2, boolean z2, long j3) {
        int offset = getOffset(j3);
        long j4 = j2 - offset;
        return getOffset(j4) == offset ? j4 : convertLocalToUTC(j2, z2);
    }

    public long convertUTCToLocal(long j2) {
        long offset = getOffset(j2);
        long j3 = j2 + offset;
        if ((j2 ^ j3) >= 0 || (j2 ^ offset) < 0) {
            return j3;
        }
        throw new ArithmeticException("Adding time zone offset caused overflow");
    }

    public abstract boolean equals(Object obj);

    @ToString
    public final String getID() {
        return this.iID;
    }

    public long getMillisKeepLocal(DateTimeZone dateTimeZone, long j2) {
        if (dateTimeZone == null) {
            dateTimeZone = getDefault();
        }
        DateTimeZone dateTimeZone2 = dateTimeZone;
        return dateTimeZone2 == this ? j2 : dateTimeZone2.convertLocalToUTC(convertUTCToLocal(j2), false, j2);
    }

    public final String getName(long j2) {
        return getName(j2, null);
    }

    public abstract String getNameKey(long j2);

    public abstract int getOffset(long j2);

    public final int getOffset(ReadableInstant readableInstant) {
        return readableInstant == null ? getOffset(DateTimeUtils.currentTimeMillis()) : getOffset(readableInstant.getMillis());
    }

    public int getOffsetFromLocal(long j2) {
        int offset = getOffset(j2);
        long j3 = j2 - offset;
        int offset2 = getOffset(j3);
        if (offset != offset2) {
            if (offset - offset2 < 0 && nextTransition(j3) != nextTransition(j2 - offset2)) {
                return offset;
            }
        } else if (offset >= 0) {
            long jPreviousTransition = previousTransition(j3);
            if (jPreviousTransition < j3) {
                int offset3 = getOffset(jPreviousTransition);
                if (j3 - jPreviousTransition <= offset3 - offset) {
                    return offset3;
                }
            }
        }
        return offset2;
    }

    public final String getShortName(long j2) {
        return getShortName(j2, null);
    }

    public abstract int getStandardOffset(long j2);

    public int hashCode() {
        return getID().hashCode() + 57;
    }

    public abstract boolean isFixed();

    public boolean isLocalDateTimeGap(LocalDateTime localDateTime) {
        if (isFixed()) {
            return false;
        }
        try {
            localDateTime.toDateTime(this);
            return false;
        } catch (IllegalInstantException unused) {
            return true;
        }
    }

    public boolean isStandardOffset(long j2) {
        return getOffset(j2) == getStandardOffset(j2);
    }

    public abstract long nextTransition(long j2);

    public abstract long previousTransition(long j2);

    public String toString() {
        return getID();
    }

    public TimeZone toTimeZone() {
        return TimeZone.getTimeZone(this.iID);
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new Stub(this.iID);
    }

    public String getName(long j2, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j2);
        if (nameKey == null) {
            return this.iID;
        }
        String name = cNameProvider.getName(locale, this.iID, nameKey);
        return name != null ? name : printOffset(getOffset(j2));
    }

    public String getShortName(long j2, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j2);
        if (nameKey == null) {
            return this.iID;
        }
        String shortName = cNameProvider.getShortName(locale, this.iID, nameKey);
        return shortName != null ? shortName : printOffset(getOffset(j2));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long convertLocalToUTC(long r11, boolean r13) {
        /*
            r10 = this;
            int r0 = r10.getOffset(r11)
            long r1 = (long) r0
            long r1 = r11 - r1
            int r3 = r10.getOffset(r1)
            if (r0 == r3) goto L3d
            if (r13 != 0) goto L11
            if (r0 >= 0) goto L3d
        L11:
            long r4 = r10.nextTransition(r1)
            int r1 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r1 != 0) goto L1f
            r4 = r6
        L1f:
            long r1 = (long) r3
            long r1 = r11 - r1
            long r8 = r10.nextTransition(r1)
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 != 0) goto L2b
            goto L2c
        L2b:
            r6 = r8
        L2c:
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 == 0) goto L3d
            if (r13 != 0) goto L33
            goto L3e
        L33:
            org.joda.time.IllegalInstantException r13 = new org.joda.time.IllegalInstantException
            java.lang.String r0 = r10.getID()
            r13.<init>(r11, r0)
            throw r13
        L3d:
            r0 = r3
        L3e:
            long r0 = (long) r0
            long r2 = r11 - r0
            long r4 = r11 ^ r2
            r6 = 0
            int r13 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r13 >= 0) goto L57
            long r11 = r11 ^ r0
            int r11 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r11 < 0) goto L4f
            goto L57
        L4f:
            java.lang.ArithmeticException r11 = new java.lang.ArithmeticException
            java.lang.String r12 = "Subtracting time zone offset caused overflow"
            r11.<init>(r12)
            throw r11
        L57:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.DateTimeZone.convertLocalToUTC(long, boolean):long");
    }
}
