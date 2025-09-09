package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
public class DateTimeFormat {
    private static final int PATTERN_CACHE_SIZE = 500;
    private static final Map<String, DateTimeFormatter> PATTERN_CACHE = new LinkedHashMap<String, DateTimeFormatter>(7) { // from class: org.joda.time.format.DateTimeFormat.1
        private static final long serialVersionUID = 23;

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, DateTimeFormatter> entry) {
            return size() > 500;
        }
    };
    private static final DateTimeFormatter[] STYLE_CACHE = new DateTimeFormatter[25];

    static void a(DateTimeFormatterBuilder dateTimeFormatterBuilder, String str) {
        parsePatternTo(dateTimeFormatterBuilder, str);
    }

    private static DateTimeFormatter createDateTimeFormatter(int i2, int i3) {
        StyleFormatter styleFormatter = new StyleFormatter(i2, i3, i2 == 4 ? 1 : i3 == 4 ? 0 : 2);
        return new DateTimeFormatter(styleFormatter, styleFormatter);
    }

    private static DateTimeFormatter createFormatterForPattern(String str) {
        DateTimeFormatter formatter;
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        Map<String, DateTimeFormatter> map = PATTERN_CACHE;
        synchronized (map) {
            try {
                formatter = map.get(str);
                if (formatter == null) {
                    DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
                    parsePatternTo(dateTimeFormatterBuilder, str);
                    formatter = dateTimeFormatterBuilder.toFormatter();
                    map.put(str, formatter);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return formatter;
    }

    private static DateTimeFormatter createFormatterForStyle(String str) {
        if (str == null || str.length() != 2) {
            throw new IllegalArgumentException("Invalid style specification: " + str);
        }
        int iSelectStyle = selectStyle(str.charAt(0));
        int iSelectStyle2 = selectStyle(str.charAt(1));
        if (iSelectStyle == 4 && iSelectStyle2 == 4) {
            throw new IllegalArgumentException("Style '--' is invalid");
        }
        return createFormatterForStyleIndex(iSelectStyle, iSelectStyle2);
    }

    private static DateTimeFormatter createFormatterForStyleIndex(int i2, int i3) {
        DateTimeFormatter dateTimeFormatterCreateDateTimeFormatter;
        int i4 = (i2 << 2) + i2 + i3;
        DateTimeFormatter[] dateTimeFormatterArr = STYLE_CACHE;
        if (i4 >= dateTimeFormatterArr.length) {
            return createDateTimeFormatter(i2, i3);
        }
        synchronized (dateTimeFormatterArr) {
            try {
                dateTimeFormatterCreateDateTimeFormatter = dateTimeFormatterArr[i4];
                if (dateTimeFormatterCreateDateTimeFormatter == null) {
                    dateTimeFormatterCreateDateTimeFormatter = createDateTimeFormatter(i2, i3);
                    dateTimeFormatterArr[i4] = dateTimeFormatterCreateDateTimeFormatter;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dateTimeFormatterCreateDateTimeFormatter;
    }

    public static DateTimeFormatter forPattern(String str) {
        return createFormatterForPattern(str);
    }

    public static DateTimeFormatter forStyle(String str) {
        return createFormatterForStyle(str);
    }

    public static DateTimeFormatter fullDate() {
        return createFormatterForStyleIndex(0, 4);
    }

    public static DateTimeFormatter fullDateTime() {
        return createFormatterForStyleIndex(0, 0);
    }

    public static DateTimeFormatter fullTime() {
        return createFormatterForStyleIndex(4, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0013 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean isNumericToken(java.lang.String r3) {
        /*
            int r0 = r3.length()
            r1 = 0
            if (r0 <= 0) goto L14
            char r3 = r3.charAt(r1)
            r2 = 1
            switch(r3) {
                case 67: goto L13;
                case 68: goto L13;
                case 70: goto L13;
                case 72: goto L13;
                case 75: goto L13;
                case 77: goto L10;
                case 83: goto L13;
                case 87: goto L13;
                case 89: goto L13;
                case 99: goto L13;
                case 100: goto L13;
                case 101: goto L13;
                case 104: goto L13;
                case 107: goto L13;
                case 109: goto L13;
                case 115: goto L13;
                case 119: goto L13;
                case 120: goto L13;
                case 121: goto L13;
                default: goto Lf;
            }
        Lf:
            goto L14
        L10:
            r3 = 2
            if (r0 > r3) goto L14
        L13:
            return r2
        L14:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormat.isNumericToken(java.lang.String):boolean");
    }

    public static DateTimeFormatter longDate() {
        return createFormatterForStyleIndex(1, 4);
    }

    public static DateTimeFormatter longDateTime() {
        return createFormatterForStyleIndex(1, 1);
    }

    public static DateTimeFormatter longTime() {
        return createFormatterForStyleIndex(4, 1);
    }

    public static DateTimeFormatter mediumDate() {
        return createFormatterForStyleIndex(2, 4);
    }

    public static DateTimeFormatter mediumDateTime() {
        return createFormatterForStyleIndex(2, 2);
    }

    public static DateTimeFormatter mediumTime() {
        return createFormatterForStyleIndex(4, 2);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static void parsePatternTo(org.joda.time.format.DateTimeFormatterBuilder r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormat.parsePatternTo(org.joda.time.format.DateTimeFormatterBuilder, java.lang.String):void");
    }

    private static String parseToken(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char cCharAt = str.charAt(i2);
        if ((cCharAt < 'A' || cCharAt > 'Z') && (cCharAt < 'a' || cCharAt > 'z')) {
            sb.append('\'');
            boolean z2 = false;
            while (i2 < length) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 != '\'') {
                    if (!z2 && ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || (cCharAt2 >= 'a' && cCharAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    sb.append(cCharAt2);
                } else {
                    int i3 = i2 + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z2 = !z2;
                    } else {
                        sb.append(cCharAt2);
                        i2 = i3;
                    }
                }
                i2++;
            }
        } else {
            sb.append(cCharAt);
            while (true) {
                int i4 = i2 + 1;
                if (i4 >= length || str.charAt(i4) != cCharAt) {
                    break;
                }
                sb.append(cCharAt);
                i2 = i4;
            }
        }
        iArr[0] = i2;
        return sb.toString();
    }

    public static String patternForStyle(String str, Locale locale) {
        DateTimeFormatter dateTimeFormatterCreateFormatterForStyle = createFormatterForStyle(str);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return ((StyleFormatter) dateTimeFormatterCreateFormatterForStyle.getPrinter()).a(locale);
    }

    private static int selectStyle(char c2) {
        if (c2 == '-') {
            return 4;
        }
        if (c2 == 'F') {
            return 0;
        }
        if (c2 == 'S') {
            return 3;
        }
        if (c2 == 'L') {
            return 1;
        }
        if (c2 == 'M') {
            return 2;
        }
        throw new IllegalArgumentException("Invalid style character: " + c2);
    }

    public static DateTimeFormatter shortDate() {
        return createFormatterForStyleIndex(3, 4);
    }

    public static DateTimeFormatter shortDateTime() {
        return createFormatterForStyleIndex(3, 3);
    }

    public static DateTimeFormatter shortTime() {
        return createFormatterForStyleIndex(4, 3);
    }

    static class StyleFormatter implements DateTimePrinter, DateTimeParser {
        private static final Map<String, DateTimeFormatter> cCache = new HashMap();
        private final int iDateStyle;
        private final int iTimeStyle;
        private final int iType;

        StyleFormatter(int i2, int i3, int i4) {
            this.iDateStyle = i2;
            this.iTimeStyle = i3;
            this.iType = i4;
        }

        private DateTimeFormatter getFormatter(Locale locale) {
            DateTimeFormatter dateTimeFormatterForPattern;
            if (locale == null) {
                locale = Locale.getDefault();
            }
            String str = Integer.toString(this.iType + (this.iDateStyle << 4) + (this.iTimeStyle << 8)) + locale.toString();
            Map<String, DateTimeFormatter> map = cCache;
            synchronized (map) {
                try {
                    dateTimeFormatterForPattern = map.get(str);
                    if (dateTimeFormatterForPattern == null) {
                        dateTimeFormatterForPattern = DateTimeFormat.forPattern(a(locale));
                        map.put(str, dateTimeFormatterForPattern);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return dateTimeFormatterForPattern;
        }

        String a(Locale locale) {
            int i2 = this.iType;
            DateFormat dateTimeInstance = i2 != 0 ? i2 != 1 ? i2 != 2 ? null : DateFormat.getDateTimeInstance(this.iDateStyle, this.iTimeStyle, locale) : DateFormat.getTimeInstance(this.iTimeStyle, locale) : DateFormat.getDateInstance(this.iDateStyle, locale);
            if (dateTimeInstance instanceof SimpleDateFormat) {
                return ((SimpleDateFormat) dateTimeInstance).toPattern();
            }
            throw new IllegalArgumentException("No datetime pattern for locale: " + locale);
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return 40;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return 40;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            return getFormatter(dateTimeParserBucket.getLocale()).getParser().parseInto(dateTimeParserBucket, str, i2);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            getFormatter(locale).getPrinter().printTo(stringBuffer, j2, chronology, i2, dateTimeZone, locale);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            getFormatter(locale).getPrinter().printTo(writer, j2, chronology, i2, dateTimeZone, locale);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            getFormatter(locale).getPrinter().printTo(stringBuffer, readablePartial, locale);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            getFormatter(locale).getPrinter().printTo(writer, readablePartial, locale);
        }
    }
}
