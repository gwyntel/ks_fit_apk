package org.apache.commons.lang.text;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

/* loaded from: classes5.dex */
public class ExtendedMessageFormat extends MessageFormat {
    private static final String DUMMY_PATTERN = "";
    private static final char END_FE = '}';
    private static final String ESCAPED_QUOTE = "''";
    private static final int HASH_SEED = 31;
    private static final char QUOTE = '\'';
    private static final char START_FE = '{';
    private static final char START_FMT = ',';
    private static final long serialVersionUID = -2362048321261811743L;
    private final Map registry;
    private String toPattern;

    public ExtendedMessageFormat(String str) {
        this(str, Locale.getDefault());
    }

    private StrBuilder appendQuotedString(String str, ParsePosition parsePosition, StrBuilder strBuilder, boolean z2) {
        int index = parsePosition.getIndex();
        char[] charArray = str.toCharArray();
        if (z2 && charArray[index] == '\'') {
            next(parsePosition);
            if (strBuilder == null) {
                return null;
            }
            return strBuilder.append(QUOTE);
        }
        int index2 = index;
        for (int index3 = parsePosition.getIndex(); index3 < str.length(); index3++) {
            if (z2 && str.substring(index3).startsWith(ESCAPED_QUOTE)) {
                strBuilder.append(charArray, index2, parsePosition.getIndex() - index2).append(QUOTE);
                parsePosition.setIndex(index3 + 2);
                index2 = parsePosition.getIndex();
            } else {
                if (charArray[parsePosition.getIndex()] == '\'') {
                    next(parsePosition);
                    if (strBuilder == null) {
                        return null;
                    }
                    return strBuilder.append(charArray, index2, parsePosition.getIndex() - index2);
                }
                next(parsePosition);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unterminated quoted string at position ");
        stringBuffer.append(index);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    private boolean containsElements(Collection collection) {
        if (collection != null && collection.size() != 0) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (it.next() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private Format getFormat(String str) {
        String strTrim;
        if (this.registry != null) {
            int iIndexOf = str.indexOf(44);
            if (iIndexOf > 0) {
                String strTrim2 = str.substring(0, iIndexOf).trim();
                strTrim = str.substring(iIndexOf + 1).trim();
                str = strTrim2;
            } else {
                strTrim = null;
            }
            FormatFactory formatFactory = (FormatFactory) this.registry.get(str);
            if (formatFactory != null) {
                return formatFactory.getFormat(str, strTrim, getLocale());
            }
        }
        return null;
    }

    private void getQuotedString(String str, ParsePosition parsePosition, boolean z2) {
        appendQuotedString(str, parsePosition, null, z2);
    }

    private String insertFormats(String str, ArrayList arrayList) {
        if (!containsElements(arrayList)) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(str.length() * 2);
        ParsePosition parsePosition = new ParsePosition(0);
        int i2 = -1;
        int i3 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char cCharAt = str.charAt(parsePosition.getIndex());
            if (cCharAt == '\'') {
                appendQuotedString(str, parsePosition, strBuilder, false);
            } else if (cCharAt != '{') {
                if (cCharAt == '}') {
                    i3--;
                }
                strBuilder.append(cCharAt);
                next(parsePosition);
            } else {
                i3++;
                if (i3 == 1) {
                    i2++;
                    strBuilder.append(START_FE).append(readArgumentIndex(str, next(parsePosition)));
                    String str2 = (String) arrayList.get(i2);
                    if (str2 != null) {
                        strBuilder.append(START_FMT).append(str2);
                    }
                }
            }
        }
        return strBuilder.toString();
    }

    private ParsePosition next(ParsePosition parsePosition) {
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        return parsePosition;
    }

    private String parseFormatDescription(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        seekNonWs(str, parsePosition);
        int index2 = parsePosition.getIndex();
        int i2 = 1;
        while (parsePosition.getIndex() < str.length()) {
            char cCharAt = str.charAt(parsePosition.getIndex());
            if (cCharAt == '\'') {
                getQuotedString(str, parsePosition, false);
            } else if (cCharAt == '{') {
                i2++;
            } else if (cCharAt == '}' && i2 - 1 == 0) {
                return str.substring(index2, parsePosition.getIndex());
            }
            next(parsePosition);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unterminated format element at position ");
        stringBuffer.append(index);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003d A[PHI: r2
      0x003d: PHI (r2v5 char) = (r2v4 char), (r2v9 char), (r2v9 char) binds: [B:7:0x002a, B:9:0x0037, B:10:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readArgumentIndex(java.lang.String r8, java.text.ParsePosition r9) {
        /*
            r7 = this;
            int r0 = r9.getIndex()
            r7.seekNonWs(r8, r9)
            org.apache.commons.lang.text.StrBuilder r1 = new org.apache.commons.lang.text.StrBuilder
            r1.<init>()
            r2 = 0
        Ld:
            if (r2 != 0) goto L5d
            int r3 = r9.getIndex()
            int r4 = r8.length()
            if (r3 >= r4) goto L5d
            int r2 = r9.getIndex()
            char r2 = r8.charAt(r2)
            boolean r3 = java.lang.Character.isWhitespace(r2)
            r4 = 1
            r5 = 125(0x7d, float:1.75E-43)
            r6 = 44
            if (r3 == 0) goto L3d
            r7.seekNonWs(r8, r9)
            int r2 = r9.getIndex()
            char r2 = r8.charAt(r2)
            if (r2 == r6) goto L3d
            if (r2 == r5) goto L3d
            r2 = r4
            goto L59
        L3d:
            if (r2 == r6) goto L41
            if (r2 != r5) goto L50
        L41:
            int r3 = r1.length()
            if (r3 <= 0) goto L50
            java.lang.String r3 = r1.toString()     // Catch: java.lang.NumberFormatException -> L50
            int r8 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> L50
            return r8
        L50:
            boolean r3 = java.lang.Character.isDigit(r2)
            r3 = r3 ^ r4
            r1.append(r2)
            r2 = r3
        L59:
            r7.next(r9)
            goto Ld
        L5d:
            if (r2 == 0) goto L86
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r3 = "Invalid format argument index at position "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = ": "
            r2.append(r3)
            int r9 = r9.getIndex()
            java.lang.String r8 = r8.substring(r0, r9)
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            r1.<init>(r8)
            throw r1
        L86:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuffer r9 = new java.lang.StringBuffer
            r9.<init>()
            java.lang.String r1 = "Unterminated format element at position "
            r9.append(r1)
            r9.append(r0)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.text.ExtendedMessageFormat.readArgumentIndex(java.lang.String, java.text.ParsePosition):int");
    }

    private void seekNonWs(String str, ParsePosition parsePosition) {
        char[] charArray = str.toCharArray();
        do {
            int iIsMatch = StrMatcher.splitMatcher().isMatch(charArray, parsePosition.getIndex());
            parsePosition.setIndex(parsePosition.getIndex() + iIsMatch);
            if (iIsMatch <= 0) {
                return;
            }
        } while (parsePosition.getIndex() < str.length());
    }

    @Override // java.text.MessageFormat
    public final void applyPattern(String str) {
        String formatDescription;
        Format format;
        if (this.registry == null) {
            super.applyPattern(str);
            this.toPattern = super.toPattern();
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        StrBuilder strBuilder = new StrBuilder(str.length());
        int i2 = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        char[] charArray = str.toCharArray();
        int i3 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char c2 = charArray[parsePosition.getIndex()];
            if (c2 != '\'') {
                if (c2 == '{') {
                    i3++;
                    seekNonWs(str, parsePosition);
                    int index = parsePosition.getIndex();
                    strBuilder.append(START_FE).append(readArgumentIndex(str, next(parsePosition)));
                    seekNonWs(str, parsePosition);
                    if (charArray[parsePosition.getIndex()] == ',') {
                        formatDescription = parseFormatDescription(str, next(parsePosition));
                        format = getFormat(formatDescription);
                        if (format == null) {
                            strBuilder.append(START_FMT).append(formatDescription);
                        }
                    } else {
                        formatDescription = null;
                        format = null;
                    }
                    arrayList.add(format);
                    arrayList2.add(format != null ? formatDescription : null);
                    Validate.isTrue(arrayList.size() == i3);
                    Validate.isTrue(arrayList2.size() == i3);
                    if (charArray[parsePosition.getIndex()] != '}') {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Unreadable format element at position ");
                        stringBuffer.append(index);
                        throw new IllegalArgumentException(stringBuffer.toString());
                    }
                }
                strBuilder.append(charArray[parsePosition.getIndex()]);
                next(parsePosition);
            } else {
                appendQuotedString(str, parsePosition, strBuilder, true);
            }
        }
        super.applyPattern(strBuilder.toString());
        this.toPattern = insertFormats(super.toPattern(), arrayList2);
        if (containsElements(arrayList)) {
            Format[] formats = getFormats();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Format format2 = (Format) it.next();
                if (format2 != null) {
                    formats[i2] = format2;
                }
                i2++;
            }
            super.setFormats(formats);
        }
    }

    @Override // java.text.MessageFormat
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !super.equals(obj) || ObjectUtils.notEqual(getClass(), obj.getClass())) {
            return false;
        }
        ExtendedMessageFormat extendedMessageFormat = (ExtendedMessageFormat) obj;
        return (ObjectUtils.notEqual(this.toPattern, extendedMessageFormat.toPattern) || ObjectUtils.notEqual(this.registry, extendedMessageFormat.registry)) ? false : true;
    }

    @Override // java.text.MessageFormat
    public int hashCode() {
        return (((super.hashCode() * 31) + ObjectUtils.hashCode(this.registry)) * 31) + ObjectUtils.hashCode(this.toPattern);
    }

    @Override // java.text.MessageFormat
    public void setFormat(int i2, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatByArgumentIndex(int i2, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormats(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatsByArgumentIndex(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public String toPattern() {
        return this.toPattern;
    }

    public ExtendedMessageFormat(String str, Locale locale) {
        this(str, locale, null);
    }

    public ExtendedMessageFormat(String str, Map map) {
        this(str, Locale.getDefault(), map);
    }

    public ExtendedMessageFormat(String str, Locale locale, Map map) {
        super("");
        setLocale(locale);
        this.registry = map;
        applyPattern(str);
    }
}
