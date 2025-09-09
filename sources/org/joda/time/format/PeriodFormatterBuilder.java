package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import org.joda.time.DurationFieldType;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

/* loaded from: classes5.dex */
public class PeriodFormatterBuilder {
    private static final int DAYS = 3;
    private static final int HOURS = 4;
    private static final int MAX_FIELD = 9;
    private static final int MILLIS = 7;
    private static final int MINUTES = 5;
    private static final int MONTHS = 1;
    private static final int PRINT_ZERO_ALWAYS = 4;
    private static final int PRINT_ZERO_IF_SUPPORTED = 3;
    private static final int PRINT_ZERO_NEVER = 5;
    private static final int PRINT_ZERO_RARELY_FIRST = 1;
    private static final int PRINT_ZERO_RARELY_LAST = 2;
    private static final int SECONDS = 6;
    private static final int SECONDS_MILLIS = 8;
    private static final int SECONDS_OPTIONAL_MILLIS = 9;
    private static final int WEEKS = 2;
    private static final int YEARS = 0;
    private List<Object> iElementPairs;
    private FieldFormatter[] iFieldFormatters;
    private int iMaxParsedDigits;
    private int iMinPrintedDigits;
    private boolean iNotParser;
    private boolean iNotPrinter;
    private PeriodFieldAffix iPrefix;
    private int iPrintZeroSetting;
    private boolean iRejectSignedValues;

    static class Literal implements PeriodPrinter, PeriodParser {

        /* renamed from: a, reason: collision with root package name */
        static final Literal f26737a = new Literal("");
        private final String iText;

        Literal(String str) {
            this.iText = str;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            return this.iText.length();
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i2, Locale locale) {
            return 0;
        }

        @Override // org.joda.time.format.PeriodParser
        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i2, Locale locale) {
            String str2 = this.iText;
            return str.regionMatches(true, i2, str2, 0, str2.length()) ? i2 + this.iText.length() : ~i2;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            stringBuffer.append(this.iText);
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            writer.write(this.iText);
        }
    }

    interface PeriodFieldAffix {
        int calculatePrintedLength(int i2);

        int parse(String str, int i2);

        void printTo(Writer writer, int i2) throws IOException;

        void printTo(StringBuffer stringBuffer, int i2);

        int scan(String str, int i2);
    }

    static class PluralAffix implements PeriodFieldAffix {
        private final String iPluralText;
        private final String iSingularText;

        PluralAffix(String str, String str2) {
            this.iSingularText = str;
            this.iPluralText = str2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int calculatePrintedLength(int i2) {
            return (i2 == 1 ? this.iSingularText : this.iPluralText).length();
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int parse(String str, int i2) {
            String str2;
            int length;
            String str3 = this.iPluralText;
            String str4 = this.iSingularText;
            if (str3.length() < str4.length()) {
                str2 = str3;
                str3 = str4;
            } else {
                str2 = str4;
            }
            if (str.regionMatches(true, i2, str3, 0, str3.length())) {
                length = str3.length();
            } else {
                if (!str.regionMatches(true, i2, str2, 0, str2.length())) {
                    return ~i2;
                }
                length = str2.length();
            }
            return i2 + length;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(StringBuffer stringBuffer, int i2) {
            stringBuffer.append(i2 == 1 ? this.iSingularText : this.iPluralText);
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int scan(String str, int i2) {
            String str2;
            String str3 = this.iPluralText;
            String str4 = this.iSingularText;
            if (str3.length() < str4.length()) {
                str2 = str3;
                str3 = str4;
            } else {
                str2 = str4;
            }
            int length = str3.length();
            int length2 = str2.length();
            int length3 = str.length();
            for (int i3 = i2; i3 < length3; i3++) {
                if (str.regionMatches(true, i3, str3, 0, length)) {
                    return i3;
                }
                if (str.regionMatches(true, i3, str2, 0, length2)) {
                    return i3;
                }
            }
            return ~i2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(Writer writer, int i2) throws IOException {
            writer.write(i2 == 1 ? this.iSingularText : this.iPluralText);
        }
    }

    static class SimpleAffix implements PeriodFieldAffix {
        private final String iText;

        SimpleAffix(String str) {
            this.iText = str;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int calculatePrintedLength(int i2) {
            return this.iText.length();
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int parse(String str, int i2) {
            String str2 = this.iText;
            int length = str2.length();
            return str.regionMatches(true, i2, str2, 0, length) ? i2 + length : ~i2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(StringBuffer stringBuffer, int i2) {
            stringBuffer.append(this.iText);
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int scan(String str, int i2) {
            String str2 = this.iText;
            int length = str2.length();
            int length2 = str.length();
            for (int i3 = i2; i3 < length2; i3++) {
                if (str.regionMatches(true, i3, str2, 0, length)) {
                    return i3;
                }
                switch (str.charAt(i3)) {
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                }
                return ~i2;
            }
            return ~i2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(Writer writer, int i2) throws IOException {
            writer.write(this.iText);
        }
    }

    public PeriodFormatterBuilder() {
        clear();
    }

    private PeriodFormatterBuilder append0(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        this.iElementPairs.add(periodPrinter);
        this.iElementPairs.add(periodParser);
        this.iNotPrinter = (periodPrinter == null) | this.iNotPrinter;
        this.iNotParser |= periodParser == null;
        return this;
    }

    private void appendField(int i2) {
        appendField(i2, this.iMinPrintedDigits);
    }

    private void clearPrefix() throws IllegalStateException {
        if (this.iPrefix != null) {
            throw new IllegalStateException("Prefix not followed by field");
        }
        this.iPrefix = null;
    }

    private static Object[] createComposite(List<Object> list) {
        int size = list.size();
        if (size == 0) {
            Literal literal = Literal.f26737a;
            return new Object[]{literal, literal};
        }
        if (size == 1) {
            return new Object[]{list.get(0), list.get(1)};
        }
        Composite composite = new Composite(list);
        return new Object[]{composite, composite};
    }

    public PeriodFormatterBuilder append(PeriodFormatter periodFormatter) throws IllegalStateException {
        if (periodFormatter == null) {
            throw new IllegalArgumentException("No formatter supplied");
        }
        clearPrefix();
        append0(periodFormatter.getPrinter(), periodFormatter.getParser());
        return this;
    }

    public PeriodFormatterBuilder appendDays() {
        appendField(3);
        return this;
    }

    public PeriodFormatterBuilder appendHours() {
        appendField(4);
        return this;
    }

    public PeriodFormatterBuilder appendLiteral(String str) throws IllegalStateException {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        clearPrefix();
        Literal literal = new Literal(str);
        append0(literal, literal);
        return this;
    }

    public PeriodFormatterBuilder appendMillis() {
        appendField(7);
        return this;
    }

    public PeriodFormatterBuilder appendMillis3Digit() {
        appendField(7, 3);
        return this;
    }

    public PeriodFormatterBuilder appendMinutes() {
        appendField(5);
        return this;
    }

    public PeriodFormatterBuilder appendMonths() {
        appendField(1);
        return this;
    }

    public PeriodFormatterBuilder appendPrefix(String str) {
        if (str != null) {
            return appendPrefix(new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSeconds() {
        appendField(6);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithMillis() {
        appendField(8);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithOptionalMillis() {
        appendField(9);
        return this;
    }

    public PeriodFormatterBuilder appendSeparator(String str) {
        return appendSeparator(str, str, null, true, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsAfter(String str) {
        return appendSeparator(str, str, null, false, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsBefore(String str) {
        return appendSeparator(str, str, null, true, false);
    }

    public PeriodFormatterBuilder appendSuffix(String str) {
        if (str != null) {
            return appendSuffix(new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendWeeks() {
        appendField(2);
        return this;
    }

    public PeriodFormatterBuilder appendYears() {
        appendField(0);
        return this;
    }

    public void clear() {
        this.iMinPrintedDigits = 1;
        this.iPrintZeroSetting = 2;
        this.iMaxParsedDigits = 10;
        this.iRejectSignedValues = false;
        this.iPrefix = null;
        List<Object> list = this.iElementPairs;
        if (list == null) {
            this.iElementPairs = new ArrayList();
        } else {
            list.clear();
        }
        this.iNotPrinter = false;
        this.iNotParser = false;
        this.iFieldFormatters = new FieldFormatter[10];
    }

    public PeriodFormatterBuilder maximumParsedDigits(int i2) {
        this.iMaxParsedDigits = i2;
        return this;
    }

    public PeriodFormatterBuilder minimumPrintedDigits(int i2) {
        this.iMinPrintedDigits = i2;
        return this;
    }

    public PeriodFormatterBuilder printZeroAlways() {
        this.iPrintZeroSetting = 4;
        return this;
    }

    public PeriodFormatterBuilder printZeroIfSupported() {
        this.iPrintZeroSetting = 3;
        return this;
    }

    public PeriodFormatterBuilder printZeroNever() {
        this.iPrintZeroSetting = 5;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyFirst() {
        this.iPrintZeroSetting = 1;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyLast() {
        this.iPrintZeroSetting = 2;
        return this;
    }

    public PeriodFormatterBuilder rejectSignedValues(boolean z2) {
        this.iRejectSignedValues = z2;
        return this;
    }

    public PeriodFormatter toFormatter() {
        PeriodFormatter formatter = toFormatter(this.iElementPairs, this.iNotPrinter, this.iNotParser);
        this.iFieldFormatters = (FieldFormatter[]) this.iFieldFormatters.clone();
        return formatter;
    }

    public PeriodParser toParser() {
        if (this.iNotParser) {
            return null;
        }
        return toFormatter().getParser();
    }

    public PeriodPrinter toPrinter() {
        if (this.iNotPrinter) {
            return null;
        }
        return toFormatter().getPrinter();
    }

    static class CompositeAffix implements PeriodFieldAffix {
        private final PeriodFieldAffix iLeft;
        private final PeriodFieldAffix iRight;

        CompositeAffix(PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iLeft = periodFieldAffix;
            this.iRight = periodFieldAffix2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int calculatePrintedLength(int i2) {
            return this.iLeft.calculatePrintedLength(i2) + this.iRight.calculatePrintedLength(i2);
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int parse(String str, int i2) {
            int i3 = this.iLeft.parse(str, i2);
            return i3 >= 0 ? this.iRight.parse(str, i3) : i3;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(StringBuffer stringBuffer, int i2) {
            this.iLeft.printTo(stringBuffer, i2);
            this.iRight.printTo(stringBuffer, i2);
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public int scan(String str, int i2) {
            int iScan = this.iLeft.scan(str, i2);
            return iScan >= 0 ? this.iRight.scan(str, iScan) : ~i2;
        }

        @Override // org.joda.time.format.PeriodFormatterBuilder.PeriodFieldAffix
        public void printTo(Writer writer, int i2) throws IOException {
            this.iLeft.printTo(writer, i2);
            this.iRight.printTo(writer, i2);
        }
    }

    private void appendField(int i2, int i3) {
        FieldFormatter fieldFormatter = new FieldFormatter(i3, this.iPrintZeroSetting, this.iMaxParsedDigits, this.iRejectSignedValues, i2, this.iFieldFormatters, this.iPrefix, null);
        append0(fieldFormatter, fieldFormatter);
        this.iFieldFormatters[i2] = fieldFormatter;
        this.iPrefix = null;
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2) {
        return appendSeparator(str, str2, null, true, true);
    }

    static class Composite implements PeriodPrinter, PeriodParser {
        private final PeriodParser[] iParsers;
        private final PeriodPrinter[] iPrinters;

        Composite(List list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.size() <= 0) {
                this.iPrinters = null;
            } else {
                this.iPrinters = (PeriodPrinter[]) arrayList.toArray(new PeriodPrinter[arrayList.size()]);
            }
            if (arrayList2.size() <= 0) {
                this.iParsers = null;
            } else {
                this.iParsers = (PeriodParser[]) arrayList2.toArray(new PeriodParser[arrayList2.size()]);
            }
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
                if (obj instanceof PeriodPrinter) {
                    if (obj instanceof Composite) {
                        addArrayToList(list2, ((Composite) obj).iPrinters);
                    } else {
                        list2.add(obj);
                    }
                }
                Object obj2 = list.get(i2 + 1);
                if (obj2 instanceof PeriodParser) {
                    if (obj2 instanceof Composite) {
                        addArrayToList(list3, ((Composite) obj2).iParsers);
                    } else {
                        list3.add(obj2);
                    }
                }
            }
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            int iCalculatePrintedLength = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return iCalculatePrintedLength;
                }
                iCalculatePrintedLength += periodPrinterArr[length].calculatePrintedLength(readablePeriod, locale);
            }
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i2, Locale locale) {
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            int iCountFieldsToPrint = 0;
            while (iCountFieldsToPrint < i2) {
                length--;
                if (length < 0) {
                    break;
                }
                iCountFieldsToPrint += periodPrinterArr[length].countFieldsToPrint(readablePeriod, Integer.MAX_VALUE, locale);
            }
            return iCountFieldsToPrint;
        }

        @Override // org.joda.time.format.PeriodParser
        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i2, Locale locale) {
            PeriodParser[] periodParserArr = this.iParsers;
            if (periodParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = periodParserArr.length;
            for (int i3 = 0; i3 < length && i2 >= 0; i3++) {
                i2 = periodParserArr[i3].parseInto(readWritablePeriod, str, i2, locale);
            }
            return i2;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            for (PeriodPrinter periodPrinter : this.iPrinters) {
                periodPrinter.printTo(stringBuffer, readablePeriod, locale);
            }
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            for (PeriodPrinter periodPrinter : this.iPrinters) {
                periodPrinter.printTo(writer, readablePeriod, locale);
            }
        }
    }

    private static PeriodFormatter toFormatter(List<Object> list, boolean z2, boolean z3) {
        if (z2 && z3) {
            throw new IllegalStateException("Builder has created neither a printer nor a parser");
        }
        int size = list.size();
        if (size >= 2 && (list.get(0) instanceof Separator)) {
            Separator separator = (Separator) list.get(0);
            if (separator.iAfterParser == null && separator.iAfterPrinter == null) {
                PeriodFormatter formatter = toFormatter(list.subList(2, size), z2, z3);
                Separator separatorC = separator.c(formatter.getPrinter(), formatter.getParser());
                return new PeriodFormatter(separatorC, separatorC);
            }
        }
        Object[] objArrCreateComposite = createComposite(list);
        if (z2) {
            return new PeriodFormatter(null, (PeriodParser) objArrCreateComposite[1]);
        }
        if (z3) {
            return new PeriodFormatter((PeriodPrinter) objArrCreateComposite[0], null);
        }
        return new PeriodFormatter((PeriodPrinter) objArrCreateComposite[0], (PeriodParser) objArrCreateComposite[1]);
    }

    public PeriodFormatterBuilder appendPrefix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendPrefix(new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr) {
        return appendSeparator(str, str2, strArr, true, true);
    }

    public PeriodFormatterBuilder appendSuffix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendSuffix(new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr, boolean z2, boolean z3) throws IllegalStateException {
        Separator separator;
        if (str != null && str2 != null) {
            clearPrefix();
            List<Object> listSubList = this.iElementPairs;
            if (listSubList.size() == 0) {
                if (z3 && !z2) {
                    Literal literal = Literal.f26737a;
                    Separator separator2 = new Separator(str, str2, strArr, literal, literal, z2, z3);
                    append0(separator2, separator2);
                }
                return this;
            }
            int size = listSubList.size();
            while (true) {
                int i2 = size - 1;
                if (i2 < 0) {
                    separator = null;
                    break;
                }
                if (listSubList.get(i2) instanceof Separator) {
                    separator = (Separator) listSubList.get(i2);
                    listSubList = listSubList.subList(size, listSubList.size());
                    break;
                }
                size -= 2;
            }
            List<Object> list = listSubList;
            if (separator != null && list.size() == 0) {
                throw new IllegalStateException("Cannot have two adjacent separators");
            }
            Object[] objArrCreateComposite = createComposite(list);
            list.clear();
            Separator separator3 = new Separator(str, str2, strArr, (PeriodPrinter) objArrCreateComposite[0], (PeriodParser) objArrCreateComposite[1], z2, z3);
            list.add(separator3);
            list.add(separator3);
            return this;
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder append(PeriodPrinter periodPrinter, PeriodParser periodParser) throws IllegalStateException {
        if (periodPrinter == null && periodParser == null) {
            throw new IllegalArgumentException("No printer or parser supplied");
        }
        clearPrefix();
        append0(periodPrinter, periodParser);
        return this;
    }

    private PeriodFormatterBuilder appendPrefix(PeriodFieldAffix periodFieldAffix) {
        if (periodFieldAffix != null) {
            PeriodFieldAffix periodFieldAffix2 = this.iPrefix;
            if (periodFieldAffix2 != null) {
                periodFieldAffix = new CompositeAffix(periodFieldAffix2, periodFieldAffix);
            }
            this.iPrefix = periodFieldAffix;
            return this;
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendSuffix(PeriodFieldAffix periodFieldAffix) throws IllegalStateException {
        Object obj;
        Object obj2;
        if (this.iElementPairs.size() > 0) {
            obj = this.iElementPairs.get(r0.size() - 2);
            obj2 = this.iElementPairs.get(r1.size() - 1);
        } else {
            obj = null;
            obj2 = null;
        }
        if (obj != null && obj2 != null && obj == obj2 && (obj instanceof FieldFormatter)) {
            clearPrefix();
            FieldFormatter fieldFormatter = new FieldFormatter((FieldFormatter) obj, periodFieldAffix);
            this.iElementPairs.set(r4.size() - 2, fieldFormatter);
            this.iElementPairs.set(r4.size() - 1, fieldFormatter);
            this.iFieldFormatters[fieldFormatter.a()] = fieldFormatter;
            return this;
        }
        throw new IllegalStateException("No field to apply suffix to");
    }

    static class FieldFormatter implements PeriodPrinter, PeriodParser {
        private final FieldFormatter[] iFieldFormatters;
        private final int iFieldType;
        private final int iMaxParsedDigits;
        private final int iMinPrintedDigits;
        private final PeriodFieldAffix iPrefix;
        private final int iPrintZeroSetting;
        private final boolean iRejectSignedValues;
        private final PeriodFieldAffix iSuffix;

        FieldFormatter(int i2, int i3, int i4, boolean z2, int i5, FieldFormatter[] fieldFormatterArr, PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iMinPrintedDigits = i2;
            this.iPrintZeroSetting = i3;
            this.iMaxParsedDigits = i4;
            this.iRejectSignedValues = z2;
            this.iFieldType = i5;
            this.iFieldFormatters = fieldFormatterArr;
            this.iPrefix = periodFieldAffix;
            this.iSuffix = periodFieldAffix2;
        }

        private int parseInt(String str, int i2, int i3) {
            if (i3 >= 10) {
                return Integer.parseInt(str.substring(i2, i3 + i2));
            }
            boolean z2 = false;
            if (i3 <= 0) {
                return 0;
            }
            int i4 = i2 + 1;
            char cCharAt = str.charAt(i2);
            int i5 = i3 - 1;
            if (cCharAt == '-') {
                i5 = i3 - 2;
                if (i5 < 0) {
                    return 0;
                }
                cCharAt = str.charAt(i4);
                z2 = true;
                i4 = i2 + 2;
            }
            int i6 = cCharAt - '0';
            while (true) {
                int i7 = i5 - 1;
                if (i5 <= 0) {
                    break;
                }
                int iCharAt = (((i6 << 3) + (i6 << 1)) + str.charAt(i4)) - 48;
                i5 = i7;
                i4++;
                i6 = iCharAt;
            }
            return z2 ? -i6 : i6;
        }

        int a() {
            return this.iFieldType;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0087  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        long b(org.joda.time.ReadablePeriod r10) {
            /*
                r9 = this;
                int r0 = r9.iPrintZeroSetting
                r1 = 4
                if (r0 != r1) goto L7
                r0 = 0
                goto Lb
            L7:
                org.joda.time.PeriodType r0 = r10.getPeriodType()
            Lb:
                r1 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                if (r0 == 0) goto L1b
                int r3 = r9.iFieldType
                boolean r3 = r9.c(r0, r3)
                if (r3 != 0) goto L1b
                return r1
            L1b:
                int r3 = r9.iFieldType
                switch(r3) {
                    case 0: goto L78;
                    case 1: goto L6f;
                    case 2: goto L66;
                    case 3: goto L5d;
                    case 4: goto L54;
                    case 5: goto L4b;
                    case 6: goto L42;
                    case 7: goto L38;
                    case 8: goto L21;
                    case 9: goto L21;
                    default: goto L20;
                }
            L20:
                return r1
            L21:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.seconds()
                int r3 = r10.get(r3)
                org.joda.time.DurationFieldType r4 = org.joda.time.DurationFieldType.millis()
                int r4 = r10.get(r4)
                long r5 = (long) r3
                r7 = 1000(0x3e8, double:4.94E-321)
                long r5 = r5 * r7
                long r3 = (long) r4
                long r5 = r5 + r3
                goto L81
            L38:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.millis()
                int r3 = r10.get(r3)
            L40:
                long r5 = (long) r3
                goto L81
            L42:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.seconds()
                int r3 = r10.get(r3)
                goto L40
            L4b:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.minutes()
                int r3 = r10.get(r3)
                goto L40
            L54:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.hours()
                int r3 = r10.get(r3)
                goto L40
            L5d:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.days()
                int r3 = r10.get(r3)
                goto L40
            L66:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.weeks()
                int r3 = r10.get(r3)
                goto L40
            L6f:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.months()
                int r3 = r10.get(r3)
                goto L40
            L78:
                org.joda.time.DurationFieldType r3 = org.joda.time.DurationFieldType.years()
                int r3 = r10.get(r3)
                goto L40
            L81:
                r3 = 0
                int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
                if (r3 != 0) goto Ldf
                int r3 = r9.iPrintZeroSetting
                r4 = 9
                r7 = 1
                if (r3 == r7) goto Lb8
                r8 = 2
                if (r3 == r8) goto L96
                r10 = 5
                if (r3 == r10) goto L95
                goto Ldf
            L95:
                return r1
            L96:
                boolean r10 = r9.d(r10)
                if (r10 == 0) goto Lb7
                org.joda.time.format.PeriodFormatterBuilder$FieldFormatter[] r10 = r9.iFieldFormatters
                int r3 = r9.iFieldType
                r10 = r10[r3]
                if (r10 != r9) goto Lb7
                int r3 = r3 + r7
            La5:
                if (r3 > r4) goto Ldf
                boolean r10 = r9.c(r0, r3)
                if (r10 == 0) goto Lb4
                org.joda.time.format.PeriodFormatterBuilder$FieldFormatter[] r10 = r9.iFieldFormatters
                r10 = r10[r3]
                if (r10 == 0) goto Lb4
                return r1
            Lb4:
                int r3 = r3 + 1
                goto La5
            Lb7:
                return r1
            Lb8:
                boolean r10 = r9.d(r10)
                if (r10 == 0) goto Lde
                org.joda.time.format.PeriodFormatterBuilder$FieldFormatter[] r10 = r9.iFieldFormatters
                int r3 = r9.iFieldType
                r10 = r10[r3]
                if (r10 != r9) goto Lde
                r10 = 8
                int r10 = java.lang.Math.min(r3, r10)
            Lcc:
                int r10 = r10 + (-1)
                if (r10 < 0) goto Ldf
                if (r10 > r4) goto Ldf
                boolean r3 = r9.c(r0, r10)
                if (r3 == 0) goto Lcc
                org.joda.time.format.PeriodFormatterBuilder$FieldFormatter[] r3 = r9.iFieldFormatters
                r3 = r3[r10]
                if (r3 == 0) goto Lcc
            Lde:
                return r1
            Ldf:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.FieldFormatter.b(org.joda.time.ReadablePeriod):long");
        }

        boolean c(PeriodType periodType, int i2) {
            switch (i2) {
                case 0:
                    return periodType.isSupported(DurationFieldType.years());
                case 1:
                    return periodType.isSupported(DurationFieldType.months());
                case 2:
                    return periodType.isSupported(DurationFieldType.weeks());
                case 3:
                    return periodType.isSupported(DurationFieldType.days());
                case 4:
                    return periodType.isSupported(DurationFieldType.hours());
                case 5:
                    return periodType.isSupported(DurationFieldType.minutes());
                case 6:
                    return periodType.isSupported(DurationFieldType.seconds());
                case 7:
                    return periodType.isSupported(DurationFieldType.millis());
                case 8:
                case 9:
                    return periodType.isSupported(DurationFieldType.seconds()) || periodType.isSupported(DurationFieldType.millis());
                default:
                    return false;
            }
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            long jB = b(readablePeriod);
            if (jB == Long.MAX_VALUE) {
                return 0;
            }
            int iMax = Math.max(FormatUtils.calculateDigitCount(jB), this.iMinPrintedDigits);
            if (this.iFieldType >= 8) {
                int iMax2 = Math.max(iMax, jB < 0 ? 5 : 4);
                iMax = (this.iFieldType == 9 && Math.abs(jB) % 1000 == 0) ? iMax2 - 3 : iMax2 + 1;
                jB /= 1000;
            }
            int i2 = (int) jB;
            PeriodFieldAffix periodFieldAffix = this.iPrefix;
            if (periodFieldAffix != null) {
                iMax += periodFieldAffix.calculatePrintedLength(i2);
            }
            PeriodFieldAffix periodFieldAffix2 = this.iSuffix;
            return periodFieldAffix2 != null ? iMax + periodFieldAffix2.calculatePrintedLength(i2) : iMax;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i2, Locale locale) {
            if (i2 <= 0) {
                return 0;
            }
            return (this.iPrintZeroSetting == 4 || b(readablePeriod) != Long.MAX_VALUE) ? 1 : 0;
        }

        boolean d(ReadablePeriod readablePeriod) {
            int size = readablePeriod.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (readablePeriod.getValue(i2) != 0) {
                    return false;
                }
            }
            return true;
        }

        void e(ReadWritablePeriod readWritablePeriod, int i2, int i3) {
            switch (i2) {
                case 0:
                    readWritablePeriod.setYears(i3);
                    break;
                case 1:
                    readWritablePeriod.setMonths(i3);
                    break;
                case 2:
                    readWritablePeriod.setWeeks(i3);
                    break;
                case 3:
                    readWritablePeriod.setDays(i3);
                    break;
                case 4:
                    readWritablePeriod.setHours(i3);
                    break;
                case 5:
                    readWritablePeriod.setMinutes(i3);
                    break;
                case 6:
                    readWritablePeriod.setSeconds(i3);
                    break;
                case 7:
                    readWritablePeriod.setMillis(i3);
                    break;
            }
        }

        @Override // org.joda.time.format.PeriodParser
        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i2, Locale locale) {
            int iScan;
            PeriodFieldAffix periodFieldAffix;
            int i3;
            char cCharAt;
            int i4 = i2;
            boolean z2 = this.iPrintZeroSetting == 4;
            if (i4 >= str.length()) {
                return z2 ? ~i4 : i4;
            }
            PeriodFieldAffix periodFieldAffix2 = this.iPrefix;
            if (periodFieldAffix2 != null) {
                i4 = periodFieldAffix2.parse(str, i4);
                if (i4 < 0) {
                    return !z2 ? ~i4 : i4;
                }
                z2 = true;
            }
            PeriodFieldAffix periodFieldAffix3 = this.iSuffix;
            int i5 = -1;
            if (periodFieldAffix3 == null || z2) {
                iScan = -1;
            } else {
                iScan = periodFieldAffix3.scan(str, i4);
                if (iScan < 0) {
                    return !z2 ? ~iScan : iScan;
                }
                z2 = true;
            }
            if (!z2 && !c(readWritablePeriod.getPeriodType(), this.iFieldType)) {
                return i4;
            }
            int iMin = iScan > 0 ? Math.min(this.iMaxParsedDigits, iScan - i4) : Math.min(this.iMaxParsedDigits, str.length() - i4);
            int i6 = 0;
            boolean z3 = false;
            while (i6 < iMin) {
                int i7 = i4 + i6;
                char cCharAt2 = str.charAt(i7);
                if (i6 != 0 || (!(cCharAt2 == '-' || cCharAt2 == '+') || this.iRejectSignedValues)) {
                    if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                        z3 = true;
                    } else {
                        if ((cCharAt2 != '.' && cCharAt2 != ',') || (((i3 = this.iFieldType) != 8 && i3 != 9) || i5 >= 0)) {
                            break;
                        }
                        iMin = Math.min(iMin + 1, str.length() - i4);
                        i5 = i7 + 1;
                    }
                    i6++;
                } else {
                    boolean z4 = cCharAt2 == '-';
                    int i8 = i6 + 1;
                    if (i8 >= iMin || (cCharAt = str.charAt(i7 + 1)) < '0' || cCharAt > '9') {
                        break;
                    }
                    if (z4) {
                        i6 = i8;
                    } else {
                        i4++;
                    }
                    iMin = Math.min(iMin + 1, str.length() - i4);
                }
            }
            if (!z3) {
                return ~i4;
            }
            if (iScan >= 0 && i4 + i6 != iScan) {
                return i4;
            }
            int i9 = this.iFieldType;
            if (i9 != 8 && i9 != 9) {
                e(readWritablePeriod, i9, parseInt(str, i4, i6));
            } else if (i5 < 0) {
                e(readWritablePeriod, 6, parseInt(str, i4, i6));
                e(readWritablePeriod, 7, 0);
            } else {
                int i10 = 0;
                int i11 = parseInt(str, i4, (i5 - i4) - 1);
                e(readWritablePeriod, 6, i11);
                int i12 = (i4 + i6) - i5;
                if (i12 > 0) {
                    if (i12 >= 3) {
                        i10 = parseInt(str, i5, 3);
                    } else {
                        int i13 = parseInt(str, i5, i12);
                        i10 = i12 == 1 ? i13 * 100 : i13 * 10;
                    }
                    if (i11 < 0) {
                        i10 = -i10;
                    }
                }
                e(readWritablePeriod, 7, i10);
            }
            int i14 = i4 + i6;
            return (i14 < 0 || (periodFieldAffix = this.iSuffix) == null) ? i14 : periodFieldAffix.parse(str, i14);
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            long jB = b(readablePeriod);
            if (jB == Long.MAX_VALUE) {
                return;
            }
            int i2 = (int) jB;
            if (this.iFieldType >= 8) {
                i2 = (int) (jB / 1000);
            }
            PeriodFieldAffix periodFieldAffix = this.iPrefix;
            if (periodFieldAffix != null) {
                periodFieldAffix.printTo(stringBuffer, i2);
            }
            int length = stringBuffer.length();
            int i3 = this.iMinPrintedDigits;
            if (i3 <= 1) {
                FormatUtils.appendUnpaddedInteger(stringBuffer, i2);
            } else {
                FormatUtils.appendPaddedInteger(stringBuffer, i2, i3);
            }
            if (this.iFieldType >= 8) {
                int iAbs = (int) (Math.abs(jB) % 1000);
                if (this.iFieldType == 8 || iAbs > 0) {
                    if (jB < 0 && jB > -1000) {
                        stringBuffer.insert(length, '-');
                    }
                    stringBuffer.append('.');
                    FormatUtils.appendPaddedInteger(stringBuffer, iAbs, 3);
                }
            }
            PeriodFieldAffix periodFieldAffix2 = this.iSuffix;
            if (periodFieldAffix2 != null) {
                periodFieldAffix2.printTo(stringBuffer, i2);
            }
        }

        FieldFormatter(FieldFormatter fieldFormatter, PeriodFieldAffix periodFieldAffix) {
            this.iMinPrintedDigits = fieldFormatter.iMinPrintedDigits;
            this.iPrintZeroSetting = fieldFormatter.iPrintZeroSetting;
            this.iMaxParsedDigits = fieldFormatter.iMaxParsedDigits;
            this.iRejectSignedValues = fieldFormatter.iRejectSignedValues;
            this.iFieldType = fieldFormatter.iFieldType;
            this.iFieldFormatters = fieldFormatter.iFieldFormatters;
            this.iPrefix = fieldFormatter.iPrefix;
            PeriodFieldAffix periodFieldAffix2 = fieldFormatter.iSuffix;
            this.iSuffix = periodFieldAffix2 != null ? new CompositeAffix(periodFieldAffix2, periodFieldAffix) : periodFieldAffix;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            long jB = b(readablePeriod);
            if (jB == Long.MAX_VALUE) {
                return;
            }
            int i2 = (int) jB;
            if (this.iFieldType >= 8) {
                i2 = (int) (jB / 1000);
            }
            PeriodFieldAffix periodFieldAffix = this.iPrefix;
            if (periodFieldAffix != null) {
                periodFieldAffix.printTo(writer, i2);
            }
            int i3 = this.iMinPrintedDigits;
            if (i3 <= 1) {
                FormatUtils.writeUnpaddedInteger(writer, i2);
            } else {
                FormatUtils.writePaddedInteger(writer, i2, i3);
            }
            if (this.iFieldType >= 8) {
                int iAbs = (int) (Math.abs(jB) % 1000);
                if (this.iFieldType == 8 || iAbs > 0) {
                    writer.write(46);
                    FormatUtils.writePaddedInteger(writer, iAbs, 3);
                }
            }
            PeriodFieldAffix periodFieldAffix2 = this.iSuffix;
            if (periodFieldAffix2 != null) {
                periodFieldAffix2.printTo(writer, i2);
            }
        }
    }

    static class Separator implements PeriodPrinter, PeriodParser {
        private volatile PeriodParser iAfterParser;
        private volatile PeriodPrinter iAfterPrinter;
        private final PeriodParser iBeforeParser;
        private final PeriodPrinter iBeforePrinter;
        private final String iFinalText;
        private final String[] iParsedForms;
        private final String iText;
        private final boolean iUseAfter;
        private final boolean iUseBefore;

        Separator(String str, String str2, String[] strArr, PeriodPrinter periodPrinter, PeriodParser periodParser, boolean z2, boolean z3) {
            this.iText = str;
            this.iFinalText = str2;
            if ((str2 == null || str.equals(str2)) && (strArr == null || strArr.length == 0)) {
                this.iParsedForms = new String[]{str};
            } else {
                TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                treeSet.add(str);
                treeSet.add(str2);
                if (strArr != null) {
                    int length = strArr.length;
                    while (true) {
                        length--;
                        if (length < 0) {
                            break;
                        } else {
                            treeSet.add(strArr[length]);
                        }
                    }
                }
                ArrayList arrayList = new ArrayList(treeSet);
                Collections.reverse(arrayList);
                this.iParsedForms = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            this.iBeforePrinter = periodPrinter;
            this.iBeforeParser = periodParser;
            this.iUseBefore = z2;
            this.iUseAfter = z3;
        }

        Separator c(PeriodPrinter periodPrinter, PeriodParser periodParser) {
            this.iAfterPrinter = periodPrinter;
            this.iAfterParser = periodParser;
            return this;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            int length;
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            int iCalculatePrintedLength = periodPrinter.calculatePrintedLength(readablePeriod, locale) + periodPrinter2.calculatePrintedLength(readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) <= 0) {
                    return iCalculatePrintedLength;
                }
                if (this.iUseAfter) {
                    int iCountFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                    if (iCountFieldsToPrint <= 0) {
                        return iCalculatePrintedLength;
                    }
                    length = (iCountFieldsToPrint > 1 ? this.iText : this.iFinalText).length();
                } else {
                    length = this.iText.length();
                }
            } else {
                if (!this.iUseAfter || periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) <= 0) {
                    return iCalculatePrintedLength;
                }
                length = this.iText.length();
            }
            return iCalculatePrintedLength + length;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i2, Locale locale) {
            int iCountFieldsToPrint = this.iBeforePrinter.countFieldsToPrint(readablePeriod, i2, locale);
            return iCountFieldsToPrint < i2 ? iCountFieldsToPrint + this.iAfterPrinter.countFieldsToPrint(readablePeriod, i2, locale) : iCountFieldsToPrint;
        }

        @Override // org.joda.time.format.PeriodParser
        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i2, Locale locale) {
            int i3;
            int into = this.iBeforeParser.parseInto(readWritablePeriod, str, i2, locale);
            if (into < 0) {
                return into;
            }
            if (into > i2) {
                String[] strArr = this.iParsedForms;
                int length = strArr.length;
                for (int i4 = 0; i4 < length; i4++) {
                    String str2 = strArr[i4];
                    if (str2 == null || str2.length() == 0 || str.regionMatches(true, into, str2, 0, str2.length())) {
                        length = str2 != null ? str2.length() : 0;
                        into += length;
                        int i5 = length;
                        length = 1;
                        i3 = i5;
                    }
                }
                i3 = -1;
            } else {
                i3 = -1;
            }
            int into2 = this.iAfterParser.parseInto(readWritablePeriod, str, into, locale);
            return into2 < 0 ? into2 : (length == 0 || into2 != into || i3 <= 0) ? (into2 <= into || length != 0 || this.iUseBefore) ? into2 : ~into : ~into;
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(stringBuffer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.iUseAfter) {
                        int iCountFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (iCountFieldsToPrint > 0) {
                            stringBuffer.append(iCountFieldsToPrint > 1 ? this.iText : this.iFinalText);
                        }
                    } else {
                        stringBuffer.append(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                stringBuffer.append(this.iText);
            }
            periodPrinter2.printTo(stringBuffer, readablePeriod, locale);
        }

        @Override // org.joda.time.format.PeriodPrinter
        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(writer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.iUseAfter) {
                        int iCountFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (iCountFieldsToPrint > 0) {
                            writer.write(iCountFieldsToPrint > 1 ? this.iText : this.iFinalText);
                        }
                    } else {
                        writer.write(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                writer.write(this.iText);
            }
            periodPrinter2.printTo(writer, readablePeriod, locale);
        }
    }
}
