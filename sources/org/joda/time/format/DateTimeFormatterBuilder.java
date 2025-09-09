package org.joda.time.format;

import androidx.media3.common.C;
import androidx.media3.exoplayer.MediaPeriodQueue;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadablePartial;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.PreciseDateTimeField;

/* loaded from: classes5.dex */
public class DateTimeFormatterBuilder {
    private ArrayList<Object> iElementPairs = new ArrayList<>();
    private Object iFormatter;

    static class CharacterLiteral implements DateTimePrinter, DateTimeParser {
        private final char iValue;

        CharacterLiteral(char c2) {
            this.iValue = c2;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return 1;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return 1;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            char upperCase;
            char upperCase2;
            if (i2 >= str.length()) {
                return ~i2;
            }
            char cCharAt = str.charAt(i2);
            char c2 = this.iValue;
            return (cCharAt == c2 || (upperCase = Character.toUpperCase(cCharAt)) == (upperCase2 = Character.toUpperCase(c2)) || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2)) ? i2 + 1 : ~i2;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            writer.write(this.iValue);
        }
    }

    static class FixedNumber extends PaddedNumber {
        protected FixedNumber(DateTimeFieldType dateTimeFieldType, int i2, boolean z2) {
            super(dateTimeFieldType, i2, z2, i2);
        }

        @Override // org.joda.time.format.DateTimeFormatterBuilder.NumberFormatter, org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) throws NumberFormatException {
            int i3;
            char cCharAt;
            int into = super.parseInto(dateTimeParserBucket, str, i2);
            if (into < 0 || into == (i3 = this.f26724b + i2)) {
                return into;
            }
            if (this.f26725c && ((cCharAt = str.charAt(i2)) == '-' || cCharAt == '+')) {
                i3++;
            }
            return into > i3 ? ~(i3 + 1) : into < i3 ? ~into : into;
        }
    }

    static class Fraction implements DateTimePrinter, DateTimeParser {

        /* renamed from: a, reason: collision with root package name */
        protected int f26721a;

        /* renamed from: b, reason: collision with root package name */
        protected int f26722b;
        private final DateTimeFieldType iFieldType;

        protected Fraction(DateTimeFieldType dateTimeFieldType, int i2, int i3) {
            this.iFieldType = dateTimeFieldType;
            i3 = i3 > 18 ? 18 : i3;
            this.f26721a = i2;
            this.f26722b = i3;
        }

        private long[] getFractionData(long j2, DateTimeField dateTimeField) {
            long j3;
            long unitMillis = dateTimeField.getDurationField().getUnitMillis();
            int i2 = this.f26722b;
            while (true) {
                switch (i2) {
                    case 1:
                        j3 = 10;
                        break;
                    case 2:
                        j3 = 100;
                        break;
                    case 3:
                        j3 = 1000;
                        break;
                    case 4:
                        j3 = 10000;
                        break;
                    case 5:
                        j3 = SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US;
                        break;
                    case 6:
                        j3 = 1000000;
                        break;
                    case 7:
                        j3 = 10000000;
                        break;
                    case 8:
                        j3 = 100000000;
                        break;
                    case 9:
                        j3 = C.NANOS_PER_SECOND;
                        break;
                    case 10:
                        j3 = 10000000000L;
                        break;
                    case 11:
                        j3 = 100000000000L;
                        break;
                    case 12:
                        j3 = MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US;
                        break;
                    case 13:
                        j3 = 10000000000000L;
                        break;
                    case 14:
                        j3 = 100000000000000L;
                        break;
                    case 15:
                        j3 = 1000000000000000L;
                        break;
                    case 16:
                        j3 = 10000000000000000L;
                        break;
                    case 17:
                        j3 = 100000000000000000L;
                        break;
                    case 18:
                        j3 = 1000000000000000000L;
                        break;
                    default:
                        j3 = 1;
                        break;
                }
                if ((unitMillis * j3) / j3 == unitMillis) {
                    return new long[]{(j2 * j3) / unitMillis, i2};
                }
                i2--;
            }
        }

        protected void a(StringBuffer stringBuffer, Writer writer, long j2, Chronology chronology) throws IOException {
            DateTimeField field = this.iFieldType.getField(chronology);
            int i2 = this.f26721a;
            try {
                long jRemainder = field.remainder(j2);
                if (jRemainder != 0) {
                    long[] fractionData = getFractionData(jRemainder, field);
                    int i3 = 0;
                    long j3 = fractionData[0];
                    int i4 = (int) fractionData[1];
                    String string = (2147483647L & j3) == j3 ? Integer.toString((int) j3) : Long.toString(j3);
                    int length = string.length();
                    while (length < i4) {
                        if (stringBuffer != null) {
                            stringBuffer.append('0');
                        } else {
                            writer.write(48);
                        }
                        i2--;
                        i4--;
                    }
                    if (i2 < i4) {
                        while (i2 < i4 && length > 1 && string.charAt(length - 1) == '0') {
                            i4--;
                            length--;
                        }
                        if (length < string.length()) {
                            if (stringBuffer != null) {
                                while (i3 < length) {
                                    stringBuffer.append(string.charAt(i3));
                                    i3++;
                                }
                                return;
                            } else {
                                while (i3 < length) {
                                    writer.write(string.charAt(i3));
                                    i3++;
                                }
                                return;
                            }
                        }
                    }
                    if (stringBuffer != null) {
                        stringBuffer.append(string);
                        return;
                    } else {
                        writer.write(string);
                        return;
                    }
                }
                if (stringBuffer != null) {
                    while (true) {
                        i2--;
                        if (i2 < 0) {
                            return;
                        } else {
                            stringBuffer.append('0');
                        }
                    }
                } else {
                    while (true) {
                        i2--;
                        if (i2 < 0) {
                            return;
                        } else {
                            writer.write(48);
                        }
                    }
                }
            } catch (RuntimeException unused) {
                if (stringBuffer != null) {
                    DateTimeFormatterBuilder.a(stringBuffer, i2);
                } else {
                    DateTimeFormatterBuilder.b(writer, i2);
                }
            }
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.f26722b;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.f26722b;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            DateTimeField field = this.iFieldType.getField(dateTimeParserBucket.getChronology());
            int iMin = Math.min(this.f26722b, str.length() - i2);
            long unitMillis = field.getDurationField().getUnitMillis() * 10;
            long j2 = 0;
            int i3 = 0;
            while (i3 < iMin) {
                char cCharAt = str.charAt(i2 + i3);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i3++;
                unitMillis /= 10;
                j2 += (cCharAt - '0') * unitMillis;
            }
            long j3 = j2 / 10;
            if (i3 == 0) {
                return ~i2;
            }
            if (j3 > 2147483647L) {
                return ~i2;
            }
            dateTimeParserBucket.saveField(new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), MillisDurationField.INSTANCE, field.getDurationField()), (int) j3);
            return i2 + i3;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            try {
                a(stringBuffer, null, j2, chronology);
            } catch (IOException unused) {
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            a(null, writer, j2, chronology);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            try {
                a(stringBuffer, null, readablePartial.getChronology().set(readablePartial, 0L), readablePartial.getChronology());
            } catch (IOException unused) {
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            a(null, writer, readablePartial.getChronology().set(readablePartial, 0L), readablePartial.getChronology());
        }
    }

    static class MatchingParser implements DateTimeParser {
        private final int iParsedLengthEstimate;
        private final DateTimeParser[] iParsers;

        MatchingParser(DateTimeParser[] dateTimeParserArr) {
            int iEstimateParsedLength;
            this.iParsers = dateTimeParserArr;
            int length = dateTimeParserArr.length;
            int i2 = 0;
            while (true) {
                length--;
                if (length < 0) {
                    this.iParsedLengthEstimate = i2;
                    return;
                }
                DateTimeParser dateTimeParser = dateTimeParserArr[length];
                if (dateTimeParser != null && (iEstimateParsedLength = dateTimeParser.estimateParsedLength()) > i2) {
                    i2 = iEstimateParsedLength;
                }
            }
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0041, code lost:
        
            if (r6 > r12) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0043, code lost:
        
            if (r6 != r12) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0045, code lost:
        
            if (r3 == false) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0049, code lost:
        
            return ~r7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x004a, code lost:
        
            if (r4 == null) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x004c, code lost:
        
            r10.restoreState(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x004f, code lost:
        
            return r6;
         */
        @Override // org.joda.time.format.DateTimeParser
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r10, java.lang.String r11, int r12) {
            /*
                r9 = this;
                org.joda.time.format.DateTimeParser[] r0 = r9.iParsers
                int r1 = r0.length
                java.lang.Object r2 = r10.saveState()
                r3 = 0
                r4 = 0
                r6 = r12
                r7 = r6
                r5 = r3
            Lc:
                if (r5 >= r1) goto L41
                r8 = r0[r5]
                if (r8 != 0) goto L17
                if (r6 > r12) goto L15
                return r12
            L15:
                r3 = 1
                goto L41
            L17:
                int r8 = r8.parseInto(r10, r11, r12)
                if (r8 < r12) goto L35
                if (r8 <= r6) goto L3b
                int r4 = r11.length()
                if (r8 >= r4) goto L34
                int r4 = r5 + 1
                if (r4 >= r1) goto L34
                r4 = r0[r4]
                if (r4 != 0) goto L2e
                goto L34
            L2e:
                java.lang.Object r4 = r10.saveState()
                r6 = r8
                goto L3b
            L34:
                return r8
            L35:
                if (r8 >= 0) goto L3b
                int r8 = ~r8
                if (r8 <= r7) goto L3b
                r7 = r8
            L3b:
                r10.restoreState(r2)
                int r5 = r5 + 1
                goto Lc
            L41:
                if (r6 > r12) goto L4a
                if (r6 != r12) goto L48
                if (r3 == 0) goto L48
                goto L4a
            L48:
                int r10 = ~r7
                return r10
            L4a:
                if (r4 == 0) goto L4f
                r10.restoreState(r4)
            L4f:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.MatchingParser.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.String, int):int");
        }
    }

    static abstract class NumberFormatter implements DateTimePrinter, DateTimeParser {

        /* renamed from: a, reason: collision with root package name */
        protected final DateTimeFieldType f26723a;

        /* renamed from: b, reason: collision with root package name */
        protected final int f26724b;

        /* renamed from: c, reason: collision with root package name */
        protected final boolean f26725c;

        NumberFormatter(DateTimeFieldType dateTimeFieldType, int i2, boolean z2) {
            this.f26723a = dateTimeFieldType;
            this.f26724b = i2;
            this.f26725c = z2;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.f26724b;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) throws NumberFormatException {
            int i3;
            int i4;
            char cCharAt;
            int iMin = Math.min(this.f26724b, str.length() - i2);
            int i5 = 0;
            boolean z2 = false;
            while (i5 < iMin) {
                int i6 = i2 + i5;
                char cCharAt2 = str.charAt(i6);
                if (i5 == 0 && ((cCharAt2 == '-' || cCharAt2 == '+') && this.f26725c)) {
                    z2 = cCharAt2 == '-';
                    int i7 = i5 + 1;
                    if (i7 >= iMin || (cCharAt = str.charAt(i6 + 1)) < '0' || cCharAt > '9') {
                        break;
                    }
                    if (z2) {
                        i5 = i7;
                    } else {
                        i2++;
                    }
                    iMin = Math.min(iMin + 1, str.length() - i2);
                } else {
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        break;
                    }
                    i5++;
                }
            }
            if (i5 == 0) {
                return ~i2;
            }
            if (i5 >= 9) {
                i3 = i5 + i2;
                i4 = Integer.parseInt(str.substring(i2, i3));
            } else {
                int i8 = z2 ? i2 + 1 : i2;
                int i9 = i8 + 1;
                try {
                    int iCharAt = str.charAt(i8) - '0';
                    i3 = i5 + i2;
                    while (i9 < i3) {
                        int iCharAt2 = (((iCharAt << 3) + (iCharAt << 1)) + str.charAt(i9)) - 48;
                        i9++;
                        iCharAt = iCharAt2;
                    }
                    i4 = z2 ? -iCharAt : iCharAt;
                } catch (StringIndexOutOfBoundsException unused) {
                    return ~i2;
                }
            }
            dateTimeParserBucket.saveField(this.f26723a, i4);
            return i3;
        }
    }

    static class StringLiteral implements DateTimePrinter, DateTimeParser {
        private final String iValue;

        StringLiteral(String str) {
            this.iValue = str;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.iValue.length();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.iValue.length();
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            String str2 = this.iValue;
            return str.regionMatches(true, i2, str2, 0, str2.length()) ? i2 + this.iValue.length() : ~i2;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            writer.write(this.iValue);
        }
    }

    enum TimeZoneId implements DateTimePrinter, DateTimeParser {
        INSTANCE;

        static final Set<String> ALL_IDS;
        static final int MAX_LENGTH;

        static {
            int iMax = 0;
            Set<String> availableIDs = DateTimeZone.getAvailableIDs();
            ALL_IDS = availableIDs;
            Iterator<String> it = availableIDs.iterator();
            while (it.hasNext()) {
                iMax = Math.max(iMax, it.next().length());
            }
            MAX_LENGTH = iMax;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return MAX_LENGTH;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return MAX_LENGTH;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            String strSubstring = str.substring(i2);
            String str2 = null;
            for (String str3 : ALL_IDS) {
                if (strSubstring.startsWith(str3) && (str2 == null || str3.length() > str2.length())) {
                    str2 = str3;
                }
            }
            if (str2 == null) {
                return ~i2;
            }
            dateTimeParserBucket.setZone(DateTimeZone.forID(str2));
            return i2 + str2.length();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(dateTimeZone != null ? dateTimeZone.getID() : "");
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(dateTimeZone != null ? dateTimeZone.getID() : "");
        }
    }

    static class TimeZoneName implements DateTimePrinter, DateTimeParser {
        private final Map<String, DateTimeZone> iParseLookup;
        private final int iType;

        TimeZoneName(int i2, Map map) {
            this.iType = i2;
            this.iParseLookup = map;
        }

        private String print(long j2, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone == null) {
                return "";
            }
            int i2 = this.iType;
            return i2 != 0 ? i2 != 1 ? "" : dateTimeZone.getShortName(j2, locale) : dateTimeZone.getName(j2, locale);
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.iType == 1 ? 4 : 20;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.iType == 1 ? 4 : 20;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            Map<String, DateTimeZone> defaultTimeZoneNames = this.iParseLookup;
            if (defaultTimeZoneNames == null) {
                defaultTimeZoneNames = DateTimeUtils.getDefaultTimeZoneNames();
            }
            String strSubstring = str.substring(i2);
            String str2 = null;
            for (String str3 : defaultTimeZoneNames.keySet()) {
                if (strSubstring.startsWith(str3) && (str2 == null || str3.length() > str2.length())) {
                    str2 = str3;
                }
            }
            if (str2 == null) {
                return ~i2;
            }
            dateTimeParserBucket.setZone(defaultTimeZoneNames.get(str2));
            return i2 + str2.length();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(print(j2 - i2, dateTimeZone, locale));
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(print(j2 - i2, dateTimeZone, locale));
        }
    }

    static class TimeZoneOffset implements DateTimePrinter, DateTimeParser {
        private final int iMaxFields;
        private final int iMinFields;
        private final boolean iShowSeparators;
        private final String iZeroOffsetParseText;
        private final String iZeroOffsetPrintText;

        TimeZoneOffset(String str, String str2, boolean z2, int i2, int i3) {
            this.iZeroOffsetPrintText = str;
            this.iZeroOffsetParseText = str2;
            this.iShowSeparators = z2;
            if (i2 <= 0 || i3 < i2) {
                throw new IllegalArgumentException();
            }
            if (i2 > 4) {
                i2 = 4;
                i3 = 4;
            }
            this.iMinFields = i2;
            this.iMaxFields = i3;
        }

        private int digitCount(String str, int i2, int i3) {
            int i4 = 0;
            for (int iMin = Math.min(str.length() - i2, i3); iMin > 0; iMin--) {
                char cCharAt = str.charAt(i2 + i4);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i4++;
            }
            return i4;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            int i2 = this.iMinFields;
            int i3 = (i2 + 1) << 1;
            if (this.iShowSeparators) {
                i3 += i2 - 1;
            }
            String str = this.iZeroOffsetPrintText;
            return (str == null || str.length() <= i3) ? i3 : this.iZeroOffsetPrintText.length();
        }

        /* JADX WARN: Removed duplicated region for block: B:82:0x00ef A[PHI: r0 r6
          0x00ef: PHI (r0v4 int) = (r0v3 int), (r0v5 int), (r0v3 int) binds: [B:75:0x00da, B:93:0x010d, B:81:0x00ed] A[DONT_GENERATE, DONT_INLINE]
          0x00ef: PHI (r6v8 int) = (r6v7 int), (r6v9 int), (r6v7 int) binds: [B:75:0x00da, B:93:0x010d, B:81:0x00ed] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // org.joda.time.format.DateTimeParser
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r13, java.lang.String r14, int r15) {
            /*
                Method dump skipped, instructions count: 306
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.TimeZoneOffset.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.String, int):int");
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            String str;
            if (dateTimeZone == null) {
                return;
            }
            if (i2 == 0 && (str = this.iZeroOffsetPrintText) != null) {
                stringBuffer.append(str);
                return;
            }
            if (i2 >= 0) {
                stringBuffer.append('+');
            } else {
                stringBuffer.append('-');
                i2 = -i2;
            }
            int i3 = i2 / 3600000;
            FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
            if (this.iMaxFields == 1) {
                return;
            }
            int i4 = i2 - (i3 * 3600000);
            if (i4 != 0 || this.iMinFields > 1) {
                int i5 = i4 / 60000;
                if (this.iShowSeparators) {
                    stringBuffer.append(':');
                }
                FormatUtils.appendPaddedInteger(stringBuffer, i5, 2);
                if (this.iMaxFields == 2) {
                    return;
                }
                int i6 = i4 - (i5 * 60000);
                if (i6 != 0 || this.iMinFields > 2) {
                    int i7 = i6 / 1000;
                    if (this.iShowSeparators) {
                        stringBuffer.append(':');
                    }
                    FormatUtils.appendPaddedInteger(stringBuffer, i7, 2);
                    if (this.iMaxFields == 3) {
                        return;
                    }
                    int i8 = i6 - (i7 * 1000);
                    if (i8 != 0 || this.iMinFields > 3) {
                        if (this.iShowSeparators) {
                            stringBuffer.append('.');
                        }
                        FormatUtils.appendPaddedInteger(stringBuffer, i8, 3);
                    }
                }
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            String str;
            if (dateTimeZone == null) {
                return;
            }
            if (i2 == 0 && (str = this.iZeroOffsetPrintText) != null) {
                writer.write(str);
                return;
            }
            if (i2 >= 0) {
                writer.write(43);
            } else {
                writer.write(45);
                i2 = -i2;
            }
            int i3 = i2 / 3600000;
            FormatUtils.writePaddedInteger(writer, i3, 2);
            if (this.iMaxFields == 1) {
                return;
            }
            int i4 = i2 - (i3 * 3600000);
            if (i4 == 0 && this.iMinFields == 1) {
                return;
            }
            int i5 = i4 / 60000;
            if (this.iShowSeparators) {
                writer.write(58);
            }
            FormatUtils.writePaddedInteger(writer, i5, 2);
            if (this.iMaxFields == 2) {
                return;
            }
            int i6 = i4 - (i5 * 60000);
            if (i6 == 0 && this.iMinFields == 2) {
                return;
            }
            int i7 = i6 / 1000;
            if (this.iShowSeparators) {
                writer.write(58);
            }
            FormatUtils.writePaddedInteger(writer, i7, 2);
            if (this.iMaxFields == 3) {
                return;
            }
            int i8 = i6 - (i7 * 1000);
            if (i8 == 0 && this.iMinFields == 3) {
                return;
            }
            if (this.iShowSeparators) {
                writer.write(46);
            }
            FormatUtils.writePaddedInteger(writer, i8, 3);
        }
    }

    static void a(StringBuffer stringBuffer, int i2) {
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            } else {
                stringBuffer.append((char) 65533);
            }
        }
    }

    private DateTimeFormatterBuilder append0(Object obj) {
        this.iFormatter = null;
        this.iElementPairs.add(obj);
        this.iElementPairs.add(obj);
        return this;
    }

    static void b(Writer writer, int i2) throws IOException {
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            } else {
                writer.write(65533);
            }
        }
    }

    private void checkParser(DateTimeParser dateTimeParser) {
        if (dateTimeParser == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private void checkPrinter(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    private Object getFormatter() {
        Object composite = this.iFormatter;
        if (composite == null) {
            if (this.iElementPairs.size() == 2) {
                Object obj = this.iElementPairs.get(0);
                Object obj2 = this.iElementPairs.get(1);
                if (obj == null) {
                    composite = obj2;
                } else if (obj == obj2 || obj2 == null) {
                    composite = obj;
                }
            }
            if (composite == null) {
                composite = new Composite(this.iElementPairs);
            }
            this.iFormatter = composite;
        }
        return composite;
    }

    private boolean isFormatter(Object obj) {
        return isPrinter(obj) || isParser(obj);
    }

    private boolean isParser(Object obj) {
        if (!(obj instanceof DateTimeParser)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).a();
        }
        return true;
    }

    private boolean isPrinter(Object obj) {
        if (!(obj instanceof DateTimePrinter)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).b();
        }
        return true;
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter != null) {
            return append0(dateTimeFormatter.getPrinter(), dateTimeFormatter.getParser());
        }
        throw new IllegalArgumentException("No formatter supplied");
    }

    public DateTimeFormatterBuilder appendCenturyOfEra(int i2, int i3) {
        return appendSignedDecimal(DateTimeFieldType.centuryOfEra(), i2, i3);
    }

    public DateTimeFormatterBuilder appendClockhourOfDay(int i2) {
        return appendDecimal(DateTimeFieldType.clockhourOfDay(), i2, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfHalfday(int i2) {
        return appendDecimal(DateTimeFieldType.clockhourOfHalfday(), i2, 2);
    }

    public DateTimeFormatterBuilder appendDayOfMonth(int i2) {
        return appendDecimal(DateTimeFieldType.dayOfMonth(), i2, 2);
    }

    public DateTimeFormatterBuilder appendDayOfWeek(int i2) {
        return appendDecimal(DateTimeFieldType.dayOfWeek(), i2, 1);
    }

    public DateTimeFormatterBuilder appendDayOfWeekShortText() {
        return appendShortText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfWeekText() {
        return appendText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfYear(int i2) {
        return appendDecimal(DateTimeFieldType.dayOfYear(), i2, 3);
    }

    public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType dateTimeFieldType, int i2, int i3) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i3 < i2) {
            i3 = i2;
        }
        if (i2 < 0 || i3 <= 0) {
            throw new IllegalArgumentException();
        }
        return i2 <= 1 ? append0(new UnpaddedNumber(dateTimeFieldType, i3, false)) : append0(new PaddedNumber(dateTimeFieldType, i3, false, i2));
    }

    public DateTimeFormatterBuilder appendEraText() {
        return appendText(DateTimeFieldType.era());
    }

    public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i2, false));
        }
        throw new IllegalArgumentException("Illegal number of digits: " + i2);
    }

    public DateTimeFormatterBuilder appendFixedSignedDecimal(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i2, true));
        }
        throw new IllegalArgumentException("Illegal number of digits: " + i2);
    }

    public DateTimeFormatterBuilder appendFraction(DateTimeFieldType dateTimeFieldType, int i2, int i3) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i3 < i2) {
            i3 = i2;
        }
        if (i2 < 0 || i3 <= 0) {
            throw new IllegalArgumentException();
        }
        return append0(new Fraction(dateTimeFieldType, i2, i3));
    }

    public DateTimeFormatterBuilder appendFractionOfDay(int i2, int i3) {
        return appendFraction(DateTimeFieldType.dayOfYear(), i2, i3);
    }

    public DateTimeFormatterBuilder appendFractionOfHour(int i2, int i3) {
        return appendFraction(DateTimeFieldType.hourOfDay(), i2, i3);
    }

    public DateTimeFormatterBuilder appendFractionOfMinute(int i2, int i3) {
        return appendFraction(DateTimeFieldType.minuteOfDay(), i2, i3);
    }

    public DateTimeFormatterBuilder appendFractionOfSecond(int i2, int i3) {
        return appendFraction(DateTimeFieldType.secondOfDay(), i2, i3);
    }

    public DateTimeFormatterBuilder appendHalfdayOfDayText() {
        return appendText(DateTimeFieldType.halfdayOfDay());
    }

    public DateTimeFormatterBuilder appendHourOfDay(int i2) {
        return appendDecimal(DateTimeFieldType.hourOfDay(), i2, 2);
    }

    public DateTimeFormatterBuilder appendHourOfHalfday(int i2) {
        return appendDecimal(DateTimeFieldType.hourOfHalfday(), i2, 2);
    }

    public DateTimeFormatterBuilder appendLiteral(char c2) {
        return append0(new CharacterLiteral(c2));
    }

    public DateTimeFormatterBuilder appendMillisOfDay(int i2) {
        return appendDecimal(DateTimeFieldType.millisOfDay(), i2, 8);
    }

    public DateTimeFormatterBuilder appendMillisOfSecond(int i2) {
        return appendDecimal(DateTimeFieldType.millisOfSecond(), i2, 3);
    }

    public DateTimeFormatterBuilder appendMinuteOfDay(int i2) {
        return appendDecimal(DateTimeFieldType.minuteOfDay(), i2, 4);
    }

    public DateTimeFormatterBuilder appendMinuteOfHour(int i2) {
        return appendDecimal(DateTimeFieldType.minuteOfHour(), i2, 2);
    }

    public DateTimeFormatterBuilder appendMonthOfYear(int i2) {
        return appendDecimal(DateTimeFieldType.monthOfYear(), i2, 2);
    }

    public DateTimeFormatterBuilder appendMonthOfYearShortText() {
        return appendShortText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendMonthOfYearText() {
        return appendText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0(null, new MatchingParser(new DateTimeParser[]{dateTimeParser, null}));
    }

    public DateTimeFormatterBuilder appendPattern(String str) {
        DateTimeFormat.a(this, str);
        return this;
    }

    public DateTimeFormatterBuilder appendSecondOfDay(int i2) {
        return appendDecimal(DateTimeFieldType.secondOfDay(), i2, 5);
    }

    public DateTimeFormatterBuilder appendSecondOfMinute(int i2) {
        return appendDecimal(DateTimeFieldType.secondOfMinute(), i2, 2);
    }

    public DateTimeFormatterBuilder appendShortText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, true));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType dateTimeFieldType, int i2, int i3) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i3 < i2) {
            i3 = i2;
        }
        if (i2 < 0 || i3 <= 0) {
            throw new IllegalArgumentException();
        }
        return i2 <= 1 ? append0(new UnpaddedNumber(dateTimeFieldType, i3, true)) : append0(new PaddedNumber(dateTimeFieldType, i3, true, i2));
    }

    public DateTimeFormatterBuilder appendText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, false));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendTimeZoneId() {
        TimeZoneId timeZoneId = TimeZoneId.INSTANCE;
        return append0(timeZoneId, timeZoneId);
    }

    public DateTimeFormatterBuilder appendTimeZoneName() {
        return append0(new TimeZoneName(0, null), null);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, boolean z2, int i2, int i3) {
        return append0(new TimeZoneOffset(str, str, z2, i2, i3));
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName() {
        return append0(new TimeZoneName(1, null), null);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i2) {
        return appendTwoDigitWeekyear(i2, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i2) {
        return appendTwoDigitYear(i2, false);
    }

    public DateTimeFormatterBuilder appendWeekOfWeekyear(int i2) {
        return appendDecimal(DateTimeFieldType.weekOfWeekyear(), i2, 2);
    }

    public DateTimeFormatterBuilder appendWeekyear(int i2, int i3) {
        return appendSignedDecimal(DateTimeFieldType.weekyear(), i2, i3);
    }

    public DateTimeFormatterBuilder appendYear(int i2, int i3) {
        return appendSignedDecimal(DateTimeFieldType.year(), i2, i3);
    }

    public DateTimeFormatterBuilder appendYearOfCentury(int i2, int i3) {
        return appendDecimal(DateTimeFieldType.yearOfCentury(), i2, i3);
    }

    public DateTimeFormatterBuilder appendYearOfEra(int i2, int i3) {
        return appendDecimal(DateTimeFieldType.yearOfEra(), i2, i3);
    }

    public boolean canBuildFormatter() {
        return isFormatter(getFormatter());
    }

    public boolean canBuildParser() {
        return isParser(getFormatter());
    }

    public boolean canBuildPrinter() {
        return isPrinter(getFormatter());
    }

    public void clear() {
        this.iFormatter = null;
        this.iElementPairs.clear();
    }

    public DateTimeFormatter toFormatter() {
        Object formatter = getFormatter();
        DateTimePrinter dateTimePrinter = isPrinter(formatter) ? (DateTimePrinter) formatter : null;
        DateTimeParser dateTimeParser = isParser(formatter) ? (DateTimeParser) formatter : null;
        if (dateTimePrinter == null && dateTimeParser == null) {
            throw new UnsupportedOperationException("Both printing and parsing not supported");
        }
        return new DateTimeFormatter(dateTimePrinter, dateTimeParser);
    }

    public DateTimeParser toParser() {
        Object formatter = getFormatter();
        if (isParser(formatter)) {
            return (DateTimeParser) formatter;
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    public DateTimePrinter toPrinter() {
        Object formatter = getFormatter();
        if (isPrinter(formatter)) {
            return (DateTimePrinter) formatter;
        }
        throw new UnsupportedOperationException("Printing is not supported");
    }

    static class TextField implements DateTimePrinter, DateTimeParser {
        private static Map<Locale, Map<DateTimeFieldType, Object[]>> cParseCache = new HashMap();
        private final DateTimeFieldType iFieldType;
        private final boolean iShort;

        TextField(DateTimeFieldType dateTimeFieldType, boolean z2) {
            this.iFieldType = dateTimeFieldType;
            this.iShort = z2;
        }

        private String print(long j2, Chronology chronology, Locale locale) {
            DateTimeField field = this.iFieldType.getField(chronology);
            return this.iShort ? field.getAsShortText(j2, locale) : field.getAsText(j2, locale);
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.iShort ? 6 : 20;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            int iIntValue;
            Set hashSet;
            Locale locale = dateTimeParserBucket.getLocale();
            synchronized (cParseCache) {
                try {
                    Map<DateTimeFieldType, Object[]> map = cParseCache.get(locale);
                    if (map == null) {
                        map = new HashMap<>();
                        cParseCache.put(locale, map);
                    }
                    Object[] objArr = map.get(this.iFieldType);
                    if (objArr == null) {
                        hashSet = new HashSet(32);
                        MutableDateTime.Property property = new MutableDateTime(0L, DateTimeZone.UTC).property(this.iFieldType);
                        int minimumValueOverall = property.getMinimumValueOverall();
                        int maximumValueOverall = property.getMaximumValueOverall();
                        if (maximumValueOverall - minimumValueOverall > 32) {
                            return ~i2;
                        }
                        iIntValue = property.getMaximumTextLength(locale);
                        while (minimumValueOverall <= maximumValueOverall) {
                            property.set(minimumValueOverall);
                            hashSet.add(property.getAsShortText(locale));
                            hashSet.add(property.getAsShortText(locale).toLowerCase(locale));
                            hashSet.add(property.getAsShortText(locale).toUpperCase(locale));
                            hashSet.add(property.getAsText(locale));
                            hashSet.add(property.getAsText(locale).toLowerCase(locale));
                            hashSet.add(property.getAsText(locale).toUpperCase(locale));
                            minimumValueOverall++;
                        }
                        if ("en".equals(locale.getLanguage()) && this.iFieldType == DateTimeFieldType.era()) {
                            hashSet.add("BCE");
                            hashSet.add("bce");
                            hashSet.add("CE");
                            hashSet.add("ce");
                            iIntValue = 3;
                        }
                        map.put(this.iFieldType, new Object[]{hashSet, Integer.valueOf(iIntValue)});
                    } else {
                        Set set = (Set) objArr[0];
                        iIntValue = ((Integer) objArr[1]).intValue();
                        hashSet = set;
                    }
                    for (int iMin = Math.min(str.length(), iIntValue + i2); iMin > i2; iMin--) {
                        String strSubstring = str.substring(i2, iMin);
                        if (hashSet.contains(strSubstring)) {
                            dateTimeParserBucket.saveField(this.iFieldType, strSubstring, locale);
                            return iMin;
                        }
                    }
                    return ~i2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            try {
                stringBuffer.append(print(j2, chronology, locale));
            } catch (RuntimeException unused) {
                stringBuffer.append((char) 65533);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                writer.write(print(j2, chronology, locale));
            } catch (RuntimeException unused) {
                writer.write(65533);
            }
        }

        private String print(ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.iFieldType)) {
                DateTimeField field = this.iFieldType.getField(readablePartial.getChronology());
                if (this.iShort) {
                    return field.getAsShortText(readablePartial, locale);
                }
                return field.getAsText(readablePartial, locale);
            }
            return "�";
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            try {
                stringBuffer.append(print(readablePartial, locale));
            } catch (RuntimeException unused) {
                stringBuffer.append((char) 65533);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            try {
                writer.write(print(readablePartial, locale));
            } catch (RuntimeException unused) {
                writer.write(65533);
            }
        }
    }

    static class TwoDigitYear implements DateTimePrinter, DateTimeParser {
        private final boolean iLenientParse;
        private final int iPivot;
        private final DateTimeFieldType iType;

        TwoDigitYear(DateTimeFieldType dateTimeFieldType, int i2, boolean z2) {
            this.iType = dateTimeFieldType;
            this.iPivot = i2;
            this.iLenientParse = z2;
        }

        private int getTwoDigitYear(long j2, Chronology chronology) {
            try {
                int i2 = this.iType.getField(chronology).get(j2);
                if (i2 < 0) {
                    i2 = -i2;
                }
                return i2 % 100;
            } catch (RuntimeException unused) {
                return -1;
            }
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.iLenientParse ? 4 : 2;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return 2;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) throws NumberFormatException {
            int i3;
            int i4;
            int length = str.length() - i2;
            if (this.iLenientParse) {
                int i5 = 0;
                boolean z2 = false;
                boolean z3 = false;
                while (i5 < length) {
                    char cCharAt = str.charAt(i2 + i5);
                    if (i5 == 0 && (cCharAt == '-' || cCharAt == '+')) {
                        z3 = cCharAt == '-';
                        if (z3) {
                            i5++;
                        } else {
                            i2++;
                            length--;
                        }
                        z2 = true;
                    } else {
                        if (cCharAt < '0' || cCharAt > '9') {
                            break;
                        }
                        i5++;
                    }
                }
                if (i5 == 0) {
                    return ~i2;
                }
                if (z2 || i5 != 2) {
                    if (i5 >= 9) {
                        i3 = i5 + i2;
                        i4 = Integer.parseInt(str.substring(i2, i3));
                    } else {
                        int i6 = z3 ? i2 + 1 : i2;
                        int i7 = i6 + 1;
                        try {
                            int iCharAt = str.charAt(i6) - '0';
                            i3 = i5 + i2;
                            while (i7 < i3) {
                                int iCharAt2 = (((iCharAt << 3) + (iCharAt << 1)) + str.charAt(i7)) - 48;
                                i7++;
                                iCharAt = iCharAt2;
                            }
                            i4 = z3 ? -iCharAt : iCharAt;
                        } catch (StringIndexOutOfBoundsException unused) {
                            return ~i2;
                        }
                    }
                    dateTimeParserBucket.saveField(this.iType, i4);
                    return i3;
                }
            } else if (Math.min(2, length) < 2) {
                return ~i2;
            }
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                return ~i2;
            }
            int i8 = cCharAt2 - '0';
            char cCharAt3 = str.charAt(i2 + 1);
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                return ~i2;
            }
            int i9 = (((i8 << 3) + (i8 << 1)) + cCharAt3) - 48;
            int iIntValue = this.iPivot;
            if (dateTimeParserBucket.getPivotYear() != null) {
                iIntValue = dateTimeParserBucket.getPivotYear().intValue();
            }
            int i10 = iIntValue - 50;
            int i11 = i10 >= 0 ? i10 % 100 : ((iIntValue - 49) % 100) + 99;
            dateTimeParserBucket.saveField(this.iType, i9 + ((i10 + (i9 < i11 ? 100 : 0)) - i11));
            return i2 + 2;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            int twoDigitYear = getTwoDigitYear(j2, chronology);
            if (twoDigitYear >= 0) {
                FormatUtils.appendPaddedInteger(stringBuffer, twoDigitYear, 2);
            } else {
                stringBuffer.append((char) 65533);
                stringBuffer.append((char) 65533);
            }
        }

        private int getTwoDigitYear(ReadablePartial readablePartial) {
            if (!readablePartial.isSupported(this.iType)) {
                return -1;
            }
            try {
                int i2 = readablePartial.get(this.iType);
                if (i2 < 0) {
                    i2 = -i2;
                }
                return i2 % 100;
            } catch (RuntimeException unused) {
                return -1;
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(j2, chronology);
            if (twoDigitYear < 0) {
                writer.write(65533);
                writer.write(65533);
            } else {
                FormatUtils.writePaddedInteger(writer, twoDigitYear, 2);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            int twoDigitYear = getTwoDigitYear(readablePartial);
            if (twoDigitYear < 0) {
                stringBuffer.append((char) 65533);
                stringBuffer.append((char) 65533);
            } else {
                FormatUtils.appendPaddedInteger(stringBuffer, twoDigitYear, 2);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(readablePartial);
            if (twoDigitYear < 0) {
                writer.write(65533);
                writer.write(65533);
            } else {
                FormatUtils.writePaddedInteger(writer, twoDigitYear, 2);
            }
        }
    }

    public DateTimeFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        int length = str.length();
        return length != 0 ? length != 1 ? append0(new StringLiteral(str)) : append0(new CharacterLiteral(str.charAt(0))) : this;
    }

    public DateTimeFormatterBuilder appendTimeZoneName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(0, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, String str2, boolean z2, int i2, int i3) {
        return append0(new TimeZoneOffset(str, str2, z2, i2, i3));
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(1, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i2, boolean z2) {
        return append0(new TwoDigitYear(DateTimeFieldType.weekyear(), i2, z2));
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i2, boolean z2) {
        return append0(new TwoDigitYear(DateTimeFieldType.year(), i2, z2));
    }

    static class PaddedNumber extends NumberFormatter {

        /* renamed from: d, reason: collision with root package name */
        protected final int f26726d;

        protected PaddedNumber(DateTimeFieldType dateTimeFieldType, int i2, boolean z2, int i3) {
            super(dateTimeFieldType, i2, z2);
            this.f26726d = i3;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.f26724b;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendPaddedInteger(stringBuffer, this.f26723a.getField(chronology).get(j2), this.f26726d);
            } catch (RuntimeException unused) {
                DateTimeFormatterBuilder.a(stringBuffer, this.f26726d);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.writePaddedInteger(writer, this.f26723a.getField(chronology).get(j2), this.f26726d);
            } catch (RuntimeException unused) {
                DateTimeFormatterBuilder.b(writer, this.f26726d);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.f26723a)) {
                try {
                    FormatUtils.appendPaddedInteger(stringBuffer, readablePartial.get(this.f26723a), this.f26726d);
                    return;
                } catch (RuntimeException unused) {
                    DateTimeFormatterBuilder.a(stringBuffer, this.f26726d);
                    return;
                }
            }
            DateTimeFormatterBuilder.a(stringBuffer, this.f26726d);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.f26723a)) {
                try {
                    FormatUtils.writePaddedInteger(writer, readablePartial.get(this.f26723a), this.f26726d);
                    return;
                } catch (RuntimeException unused) {
                    DateTimeFormatterBuilder.b(writer, this.f26726d);
                    return;
                }
            }
            DateTimeFormatterBuilder.b(writer, this.f26726d);
        }
    }

    static class UnpaddedNumber extends NumberFormatter {
        protected UnpaddedNumber(DateTimeFieldType dateTimeFieldType, int i2, boolean z2) {
            super(dateTimeFieldType, i2, z2);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.f26724b;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendUnpaddedInteger(stringBuffer, this.f26723a.getField(chronology).get(j2));
            } catch (RuntimeException unused) {
                stringBuffer.append((char) 65533);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.writeUnpaddedInteger(writer, this.f26723a.getField(chronology).get(j2));
            } catch (RuntimeException unused) {
                writer.write(65533);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.f26723a)) {
                try {
                    FormatUtils.appendUnpaddedInteger(stringBuffer, readablePartial.get(this.f26723a));
                    return;
                } catch (RuntimeException unused) {
                    stringBuffer.append((char) 65533);
                    return;
                }
            }
            stringBuffer.append((char) 65533);
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.f26723a)) {
                try {
                    FormatUtils.writeUnpaddedInteger(writer, readablePartial.get(this.f26723a));
                    return;
                } catch (RuntimeException unused) {
                    writer.write(65533);
                    return;
                }
            }
            writer.write(65533);
        }
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter) {
        checkPrinter(dateTimePrinter);
        return append0(dateTimePrinter, null);
    }

    private DateTimeFormatterBuilder append0(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        this.iFormatter = null;
        this.iElementPairs.add(dateTimePrinter);
        this.iElementPairs.add(dateTimeParser);
        return this;
    }

    static class Composite implements DateTimePrinter, DateTimeParser {
        private final int iParsedLengthEstimate;
        private final DateTimeParser[] iParsers;
        private final int iPrintedLengthEstimate;
        private final DateTimePrinter[] iPrinters;

        Composite(List list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.contains(null) || arrayList.isEmpty()) {
                this.iPrinters = null;
                this.iPrintedLengthEstimate = 0;
            } else {
                int size = arrayList.size();
                this.iPrinters = new DateTimePrinter[size];
                int iEstimatePrintedLength = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    DateTimePrinter dateTimePrinter = (DateTimePrinter) arrayList.get(i2);
                    iEstimatePrintedLength += dateTimePrinter.estimatePrintedLength();
                    this.iPrinters[i2] = dateTimePrinter;
                }
                this.iPrintedLengthEstimate = iEstimatePrintedLength;
            }
            if (arrayList2.contains(null) || arrayList2.isEmpty()) {
                this.iParsers = null;
                this.iParsedLengthEstimate = 0;
                return;
            }
            int size2 = arrayList2.size();
            this.iParsers = new DateTimeParser[size2];
            int iEstimateParsedLength = 0;
            for (int i3 = 0; i3 < size2; i3++) {
                DateTimeParser dateTimeParser = (DateTimeParser) arrayList2.get(i3);
                iEstimateParsedLength += dateTimeParser.estimateParsedLength();
                this.iParsers[i3] = dateTimeParser;
            }
            this.iParsedLengthEstimate = iEstimateParsedLength;
        }

        private void addArrayToList(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (Object obj : objArr) {
                    list.add(obj);
                }
            }
        }

        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2 += 2) {
                Object obj = list.get(i2);
                if (obj instanceof Composite) {
                    addArrayToList(list2, ((Composite) obj).iPrinters);
                } else {
                    list2.add(obj);
                }
                Object obj2 = list.get(i2 + 1);
                if (obj2 instanceof Composite) {
                    addArrayToList(list3, ((Composite) obj2).iParsers);
                } else {
                    list3.add(obj2);
                }
            }
        }

        boolean a() {
            return this.iParsers != null;
        }

        boolean b() {
            return this.iPrinters != null;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public int estimatePrintedLength() {
            return this.iPrintedLengthEstimate;
        }

        @Override // org.joda.time.format.DateTimeParser
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2) {
            DateTimeParser[] dateTimeParserArr = this.iParsers;
            if (dateTimeParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = dateTimeParserArr.length;
            for (int i3 = 0; i3 < length && i2 >= 0; i3++) {
                i2 = dateTimeParserArr[i3].parseInto(dateTimeParserBucket, str, i2);
            }
            return i2;
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            Locale locale2 = locale == null ? Locale.getDefault() : locale;
            for (DateTimePrinter dateTimePrinter : dateTimePrinterArr) {
                dateTimePrinter.printTo(stringBuffer, j2, chronology, i2, dateTimeZone, locale2);
            }
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr != null) {
                Locale locale2 = locale == null ? Locale.getDefault() : locale;
                for (DateTimePrinter dateTimePrinter : dateTimePrinterArr) {
                    dateTimePrinter.printTo(writer, j2, chronology, i2, dateTimeZone, locale2);
                }
                return;
            }
            throw new UnsupportedOperationException();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr != null) {
                if (locale == null) {
                    locale = Locale.getDefault();
                }
                for (DateTimePrinter dateTimePrinter : dateTimePrinterArr) {
                    dateTimePrinter.printTo(stringBuffer, readablePartial, locale);
                }
                return;
            }
            throw new UnsupportedOperationException();
        }

        @Override // org.joda.time.format.DateTimePrinter
        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr != null) {
                if (locale == null) {
                    locale = Locale.getDefault();
                }
                for (DateTimePrinter dateTimePrinter : dateTimePrinterArr) {
                    dateTimePrinter.printTo(writer, readablePartial, locale);
                }
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    public DateTimeFormatterBuilder append(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0(null, dateTimeParser);
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        checkPrinter(dateTimePrinter);
        checkParser(dateTimeParser);
        return append0(dateTimePrinter, dateTimeParser);
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser[] dateTimeParserArr) {
        if (dateTimePrinter != null) {
            checkPrinter(dateTimePrinter);
        }
        if (dateTimeParserArr != null) {
            int length = dateTimeParserArr.length;
            int i2 = 0;
            if (length == 1) {
                DateTimeParser dateTimeParser = dateTimeParserArr[0];
                if (dateTimeParser != null) {
                    return append0(dateTimePrinter, dateTimeParser);
                }
                throw new IllegalArgumentException("No parser supplied");
            }
            DateTimeParser[] dateTimeParserArr2 = new DateTimeParser[length];
            while (i2 < length - 1) {
                DateTimeParser dateTimeParser2 = dateTimeParserArr[i2];
                dateTimeParserArr2[i2] = dateTimeParser2;
                if (dateTimeParser2 == null) {
                    throw new IllegalArgumentException("Incomplete parser array");
                }
                i2++;
            }
            dateTimeParserArr2[i2] = dateTimeParserArr[i2];
            return append0(dateTimePrinter, new MatchingParser(dateTimeParserArr2));
        }
        throw new IllegalArgumentException("No parsers supplied");
    }
}
