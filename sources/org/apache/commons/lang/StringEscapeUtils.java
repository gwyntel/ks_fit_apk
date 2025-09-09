package org.apache.commons.lang;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class StringEscapeUtils {
    private static final char CSV_QUOTE = '\"';
    private static final String CSV_QUOTE_STR = String.valueOf('\"');
    private static final char CSV_DELIMITER = ',';
    private static final char[] CSV_SEARCH_CHARS = {CSV_DELIMITER, '\"', CharUtils.CR, '\n'};

    public static String escapeCsv(String str) {
        if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
            return str;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            escapeCsv(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String escapeHtml(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter((int) (str.length() * 1.5d));
            escapeHtml(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String escapeJava(String str) {
        return escapeJavaStyleString(str, false, false);
    }

    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, true);
    }

    private static String escapeJavaStyleString(String str, boolean z2, boolean z3) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter(str.length() * 2);
            escapeJavaStyleString(stringWriter, str, z2, z3);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    public static void escapeXml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (str == null) {
            return;
        }
        Entities.XML.escape(writer, str);
    }

    private static String hex(char c2) {
        return Integer.toHexString(c2).toUpperCase(Locale.ENGLISH);
    }

    public static String unescapeCsv(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            unescapeCsv(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String unescapeHtml(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter((int) (str.length() * 1.5d));
            unescapeHtml(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String unescapeJava(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter(str.length());
            unescapeJava(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public static String unescapeJavaScript(String str) {
        return unescapeJava(str);
    }

    public static void unescapeXml(Writer writer, String str) throws IOException, NumberFormatException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (str == null) {
            return;
        }
        Entities.XML.unescape(writer, str);
    }

    public static void escapeJava(Writer writer, String str) throws IOException {
        escapeJavaStyleString(writer, str, false, false);
    }

    public static void escapeJavaScript(Writer writer, String str) throws IOException {
        escapeJavaStyleString(writer, str, true, true);
    }

    public static void unescapeJavaScript(Writer writer, String str) throws IOException {
        unescapeJava(writer, str);
    }

    public static String escapeXml(String str) {
        if (str == null) {
            return null;
        }
        return Entities.XML.escape(str);
    }

    public static String unescapeXml(String str) {
        if (str == null) {
            return null;
        }
        return Entities.XML.unescape(str);
    }

    public static void escapeHtml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (str == null) {
            return;
        }
        Entities.HTML40.escape(writer, str);
    }

    private static void escapeJavaStyleString(Writer writer, String str, boolean z2, boolean z3) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (str == null) {
            return;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt > 4095) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\\u");
                stringBuffer.append(hex(cCharAt));
                writer.write(stringBuffer.toString());
            } else if (cCharAt > 255) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("\\u0");
                stringBuffer2.append(hex(cCharAt));
                writer.write(stringBuffer2.toString());
            } else if (cCharAt > 127) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("\\u00");
                stringBuffer3.append(hex(cCharAt));
                writer.write(stringBuffer3.toString());
            } else if (cCharAt < ' ') {
                switch (cCharAt) {
                    case '\b':
                        writer.write(92);
                        writer.write(98);
                        break;
                    case '\t':
                        writer.write(92);
                        writer.write(116);
                        break;
                    case '\n':
                        writer.write(92);
                        writer.write(110);
                        break;
                    case 11:
                    default:
                        if (cCharAt > 15) {
                            StringBuffer stringBuffer4 = new StringBuffer();
                            stringBuffer4.append("\\u00");
                            stringBuffer4.append(hex(cCharAt));
                            writer.write(stringBuffer4.toString());
                            break;
                        } else {
                            StringBuffer stringBuffer5 = new StringBuffer();
                            stringBuffer5.append("\\u000");
                            stringBuffer5.append(hex(cCharAt));
                            writer.write(stringBuffer5.toString());
                            break;
                        }
                    case '\f':
                        writer.write(92);
                        writer.write(102);
                        break;
                    case '\r':
                        writer.write(92);
                        writer.write(114);
                        break;
                }
            } else if (cCharAt == '\"') {
                writer.write(92);
                writer.write(34);
            } else if (cCharAt == '\'') {
                if (z2) {
                    writer.write(92);
                }
                writer.write(39);
            } else if (cCharAt == '/') {
                if (z3) {
                    writer.write(92);
                }
                writer.write(47);
            } else if (cCharAt != '\\') {
                writer.write(cCharAt);
            } else {
                writer.write(92);
                writer.write(92);
            }
        }
    }

    public static void unescapeCsv(Writer writer, String str) throws IOException {
        if (str == null) {
            return;
        }
        if (str.length() < 2) {
            writer.write(str);
            return;
        }
        if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
            String strSubstring = str.substring(1, str.length() - 1);
            if (StringUtils.containsAny(strSubstring, CSV_SEARCH_CHARS)) {
                StringBuffer stringBuffer = new StringBuffer();
                String str2 = CSV_QUOTE_STR;
                stringBuffer.append(str2);
                stringBuffer.append(str2);
                str = StringUtils.replace(strSubstring, stringBuffer.toString(), str2);
            }
            writer.write(str);
            return;
        }
        writer.write(str);
    }

    public static void unescapeHtml(Writer writer, String str) throws IOException, NumberFormatException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (str == null) {
            return;
        }
        Entities.HTML40.unescape(writer, str);
    }

    public static void unescapeJava(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (str == null) {
            return;
        }
        int length = str.length();
        StrBuilder strBuilder = new StrBuilder(4);
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (z3) {
                strBuilder.append(cCharAt);
                if (strBuilder.length() == 4) {
                    try {
                        writer.write((char) Integer.parseInt(strBuilder.toString(), 16));
                        strBuilder.setLength(0);
                        z2 = false;
                        z3 = false;
                    } catch (NumberFormatException e2) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Unable to parse unicode value: ");
                        stringBuffer.append(strBuilder);
                        throw new NestableRuntimeException(stringBuffer.toString(), e2);
                    }
                } else {
                    continue;
                }
            } else if (z2) {
                if (cCharAt == '\"') {
                    writer.write(34);
                } else if (cCharAt == '\'') {
                    writer.write(39);
                } else if (cCharAt == '\\') {
                    writer.write(92);
                } else if (cCharAt == 'b') {
                    writer.write(8);
                } else if (cCharAt == 'f') {
                    writer.write(12);
                } else if (cCharAt == 'n') {
                    writer.write(10);
                } else if (cCharAt == 'r') {
                    writer.write(13);
                } else if (cCharAt == 't') {
                    writer.write(9);
                } else if (cCharAt != 'u') {
                    writer.write(cCharAt);
                } else {
                    z2 = false;
                    z3 = true;
                }
                z2 = false;
            } else if (cCharAt == '\\') {
                z2 = true;
            } else {
                writer.write(cCharAt);
            }
        }
        if (z2) {
            writer.write(92);
        }
    }

    public static void escapeCsv(Writer writer, String str) throws IOException {
        if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
            if (str != null) {
                writer.write(str);
                return;
            }
            return;
        }
        writer.write(34);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\"') {
                writer.write(34);
            }
            writer.write(cCharAt);
        }
        writer.write(34);
    }
}
