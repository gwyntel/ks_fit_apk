package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadWritableInstant;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
public class DateTimeFormatter {
    private final Chronology iChrono;
    private final int iDefaultYear;
    private final Locale iLocale;
    private final boolean iOffsetParsed;
    private final DateTimeParser iParser;
    private final Integer iPivotYear;
    private final DateTimePrinter iPrinter;
    private final DateTimeZone iZone;

    public DateTimeFormatter(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        this.iPrinter = dateTimePrinter;
        this.iParser = dateTimeParser;
        this.iLocale = null;
        this.iOffsetParsed = false;
        this.iChrono = null;
        this.iZone = null;
        this.iPivotYear = null;
        this.iDefaultYear = 2000;
    }

    private DateTimeParser requireParser() {
        DateTimeParser dateTimeParser = this.iParser;
        if (dateTimeParser != null) {
            return dateTimeParser;
        }
        throw new UnsupportedOperationException("Parsing not supported");
    }

    private DateTimePrinter requirePrinter() {
        DateTimePrinter dateTimePrinter = this.iPrinter;
        if (dateTimePrinter != null) {
            return dateTimePrinter;
        }
        throw new UnsupportedOperationException("Printing not supported");
    }

    private Chronology selectChronology(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        Chronology chronology3 = this.iChrono;
        if (chronology3 != null) {
            chronology2 = chronology3;
        }
        DateTimeZone dateTimeZone = this.iZone;
        return dateTimeZone != null ? chronology2.withZone(dateTimeZone) : chronology2;
    }

    @Deprecated
    public Chronology getChronolgy() {
        return this.iChrono;
    }

    public Chronology getChronology() {
        return this.iChrono;
    }

    public int getDefaultYear() {
        return this.iDefaultYear;
    }

    public Locale getLocale() {
        return this.iLocale;
    }

    public DateTimeParser getParser() {
        return this.iParser;
    }

    public Integer getPivotYear() {
        return this.iPivotYear;
    }

    public DateTimePrinter getPrinter() {
        return this.iPrinter;
    }

    public DateTimeZone getZone() {
        return this.iZone;
    }

    public boolean isOffsetParsed() {
        return this.iOffsetParsed;
    }

    public boolean isParser() {
        return this.iParser != null;
    }

    public boolean isPrinter() {
        return this.iPrinter != null;
    }

    public DateTime parseDateTime(String str) {
        DateTimeParser dateTimeParserRequireParser = requireParser();
        Chronology chronologySelectChronology = selectChronology(null);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronologySelectChronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
        int into = dateTimeParserRequireParser.parseInto(dateTimeParserBucket, str, 0);
        if (into < 0) {
            into = ~into;
        } else if (into >= str.length()) {
            long jComputeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
                chronologySelectChronology = chronologySelectChronology.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                chronologySelectChronology = chronologySelectChronology.withZone(dateTimeParserBucket.getZone());
            }
            DateTime dateTime = new DateTime(jComputeMillis, chronologySelectChronology);
            DateTimeZone dateTimeZone = this.iZone;
            return dateTimeZone != null ? dateTime.withZone(dateTimeZone) : dateTime;
        }
        throw new IllegalArgumentException(FormatUtils.a(str, into));
    }

    public int parseInto(ReadWritableInstant readWritableInstant, String str, int i2) {
        DateTimeParser dateTimeParserRequireParser = requireParser();
        if (readWritableInstant == null) {
            throw new IllegalArgumentException("Instant must not be null");
        }
        long millis = readWritableInstant.getMillis();
        Chronology chronology = readWritableInstant.getChronology();
        int i3 = DateTimeUtils.getChronology(chronology).year().get(millis);
        long offset = millis + chronology.getZone().getOffset(millis);
        Chronology chronologySelectChronology = selectChronology(chronology);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(offset, chronologySelectChronology, this.iLocale, this.iPivotYear, i3);
        int into = dateTimeParserRequireParser.parseInto(dateTimeParserBucket, str, i2);
        readWritableInstant.setMillis(dateTimeParserBucket.computeMillis(false, str));
        if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
            chronologySelectChronology = chronologySelectChronology.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
        } else if (dateTimeParserBucket.getZone() != null) {
            chronologySelectChronology = chronologySelectChronology.withZone(dateTimeParserBucket.getZone());
        }
        readWritableInstant.setChronology(chronologySelectChronology);
        DateTimeZone dateTimeZone = this.iZone;
        if (dateTimeZone != null) {
            readWritableInstant.setZone(dateTimeZone);
        }
        return into;
    }

    public LocalDate parseLocalDate(String str) {
        return parseLocalDateTime(str).toLocalDate();
    }

    public LocalDateTime parseLocalDateTime(String str) {
        DateTimeParser dateTimeParserRequireParser = requireParser();
        Chronology chronologyWithUTC = selectChronology(null).withUTC();
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronologyWithUTC, this.iLocale, this.iPivotYear, this.iDefaultYear);
        int into = dateTimeParserRequireParser.parseInto(dateTimeParserBucket, str, 0);
        if (into < 0) {
            into = ~into;
        } else if (into >= str.length()) {
            long jComputeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (dateTimeParserBucket.getOffsetInteger() != null) {
                chronologyWithUTC = chronologyWithUTC.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                chronologyWithUTC = chronologyWithUTC.withZone(dateTimeParserBucket.getZone());
            }
            return new LocalDateTime(jComputeMillis, chronologyWithUTC);
        }
        throw new IllegalArgumentException(FormatUtils.a(str, into));
    }

    public LocalTime parseLocalTime(String str) {
        return parseLocalDateTime(str).toLocalTime();
    }

    public long parseMillis(String str) {
        DateTimeParser dateTimeParserRequireParser = requireParser();
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, selectChronology(this.iChrono), this.iLocale, this.iPivotYear, this.iDefaultYear);
        int into = dateTimeParserRequireParser.parseInto(dateTimeParserBucket, str, 0);
        if (into < 0) {
            into = ~into;
        } else if (into >= str.length()) {
            return dateTimeParserBucket.computeMillis(true, str);
        }
        throw new IllegalArgumentException(FormatUtils.a(str, into));
    }

    public MutableDateTime parseMutableDateTime(String str) {
        DateTimeParser dateTimeParserRequireParser = requireParser();
        Chronology chronologySelectChronology = selectChronology(null);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronologySelectChronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
        int into = dateTimeParserRequireParser.parseInto(dateTimeParserBucket, str, 0);
        if (into < 0) {
            into = ~into;
        } else if (into >= str.length()) {
            long jComputeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
                chronologySelectChronology = chronologySelectChronology.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                chronologySelectChronology = chronologySelectChronology.withZone(dateTimeParserBucket.getZone());
            }
            MutableDateTime mutableDateTime = new MutableDateTime(jComputeMillis, chronologySelectChronology);
            DateTimeZone dateTimeZone = this.iZone;
            if (dateTimeZone != null) {
                mutableDateTime.setZone(dateTimeZone);
            }
            return mutableDateTime;
        }
        throw new IllegalArgumentException(FormatUtils.a(str, into));
    }

    public String print(ReadableInstant readableInstant) {
        StringBuffer stringBuffer = new StringBuffer(requirePrinter().estimatePrintedLength());
        printTo(stringBuffer, readableInstant);
        return stringBuffer.toString();
    }

    public void printTo(StringBuffer stringBuffer, ReadableInstant readableInstant) {
        printTo(stringBuffer, DateTimeUtils.getInstantMillis(readableInstant), DateTimeUtils.getInstantChronology(readableInstant));
    }

    public DateTimeFormatter withChronology(Chronology chronology) {
        return this.iChrono == chronology ? this : new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, chronology, this.iZone, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withDefaultYear(int i2) {
        return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, this.iChrono, this.iZone, this.iPivotYear, i2);
    }

    public DateTimeFormatter withLocale(Locale locale) {
        return (locale == getLocale() || (locale != null && locale.equals(getLocale()))) ? this : new DateTimeFormatter(this.iPrinter, this.iParser, locale, this.iOffsetParsed, this.iChrono, this.iZone, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withOffsetParsed() {
        return this.iOffsetParsed ? this : new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, true, this.iChrono, null, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withPivotYear(Integer num) {
        Integer num2 = this.iPivotYear;
        return (num2 == num || (num2 != null && num2.equals(num))) ? this : new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, this.iChrono, this.iZone, num, this.iDefaultYear);
    }

    public DateTimeFormatter withZone(DateTimeZone dateTimeZone) {
        return this.iZone == dateTimeZone ? this : new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, false, this.iChrono, dateTimeZone, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withZoneUTC() {
        return withZone(DateTimeZone.UTC);
    }

    public DateTimeFormatter withPivotYear(int i2) {
        return withPivotYear(Integer.valueOf(i2));
    }

    public String print(long j2) {
        StringBuffer stringBuffer = new StringBuffer(requirePrinter().estimatePrintedLength());
        printTo(stringBuffer, j2);
        return stringBuffer.toString();
    }

    public void printTo(Writer writer, ReadableInstant readableInstant) throws IOException {
        printTo(writer, DateTimeUtils.getInstantMillis(readableInstant), DateTimeUtils.getInstantChronology(readableInstant));
    }

    public String print(ReadablePartial readablePartial) {
        StringBuffer stringBuffer = new StringBuffer(requirePrinter().estimatePrintedLength());
        printTo(stringBuffer, readablePartial);
        return stringBuffer.toString();
    }

    public void printTo(Appendable appendable, ReadableInstant readableInstant) throws IOException {
        appendable.append(print(readableInstant));
    }

    public void printTo(StringBuffer stringBuffer, long j2) {
        printTo(stringBuffer, j2, (Chronology) null);
    }

    public void printTo(Writer writer, long j2) throws IOException {
        printTo(writer, j2, (Chronology) null);
    }

    private DateTimeFormatter(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser, Locale locale, boolean z2, Chronology chronology, DateTimeZone dateTimeZone, Integer num, int i2) {
        this.iPrinter = dateTimePrinter;
        this.iParser = dateTimeParser;
        this.iLocale = locale;
        this.iOffsetParsed = z2;
        this.iChrono = chronology;
        this.iZone = dateTimeZone;
        this.iPivotYear = num;
        this.iDefaultYear = i2;
    }

    public void printTo(Appendable appendable, long j2) throws IOException {
        appendable.append(print(j2));
    }

    public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial) {
        DateTimePrinter dateTimePrinterRequirePrinter = requirePrinter();
        if (readablePartial != null) {
            dateTimePrinterRequirePrinter.printTo(stringBuffer, readablePartial, this.iLocale);
            return;
        }
        throw new IllegalArgumentException("The partial must not be null");
    }

    public void printTo(Writer writer, ReadablePartial readablePartial) throws IOException {
        DateTimePrinter dateTimePrinterRequirePrinter = requirePrinter();
        if (readablePartial != null) {
            dateTimePrinterRequirePrinter.printTo(writer, readablePartial, this.iLocale);
            return;
        }
        throw new IllegalArgumentException("The partial must not be null");
    }

    public void printTo(Appendable appendable, ReadablePartial readablePartial) throws IOException {
        appendable.append(print(readablePartial));
    }

    private void printTo(StringBuffer stringBuffer, long j2, Chronology chronology) {
        DateTimePrinter dateTimePrinterRequirePrinter = requirePrinter();
        Chronology chronologySelectChronology = selectChronology(chronology);
        DateTimeZone zone = chronologySelectChronology.getZone();
        int offset = zone.getOffset(j2);
        long j3 = offset;
        long j4 = j2 + j3;
        if ((j2 ^ j4) < 0 && (j3 ^ j2) >= 0) {
            zone = DateTimeZone.UTC;
            offset = 0;
            j4 = j2;
        }
        dateTimePrinterRequirePrinter.printTo(stringBuffer, j4, chronologySelectChronology.withUTC(), offset, zone, this.iLocale);
    }

    private void printTo(Writer writer, long j2, Chronology chronology) throws IOException {
        DateTimePrinter dateTimePrinterRequirePrinter = requirePrinter();
        Chronology chronologySelectChronology = selectChronology(chronology);
        DateTimeZone zone = chronologySelectChronology.getZone();
        int offset = zone.getOffset(j2);
        long j3 = offset;
        long j4 = j2 + j3;
        if ((j2 ^ j4) < 0 && (j3 ^ j2) >= 0) {
            zone = DateTimeZone.UTC;
            offset = 0;
            j4 = j2;
        }
        dateTimePrinterRequirePrinter.printTo(writer, j4, chronologySelectChronology.withUTC(), offset, zone, this.iLocale);
    }
}
