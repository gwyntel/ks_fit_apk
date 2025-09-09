package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class CharUtils {
    private static final String CHAR_STRING = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f";
    public static final char CR = '\r';
    public static final char LF = '\n';
    private static final String[] CHAR_STRING_ARRAY = new String[128];
    private static final Character[] CHAR_ARRAY = new Character[128];

    static {
        for (int i2 = 127; i2 >= 0; i2--) {
            CHAR_STRING_ARRAY[i2] = CHAR_STRING.substring(i2, i2 + 1);
            CHAR_ARRAY[i2] = new Character((char) i2);
        }
    }

    static boolean a(char c2) {
        return 55296 <= c2 && 56319 >= c2;
    }

    public static boolean isAscii(char c2) {
        return c2 < 128;
    }

    public static boolean isAsciiAlpha(char c2) {
        return (c2 >= 'A' && c2 <= 'Z') || (c2 >= 'a' && c2 <= 'z');
    }

    public static boolean isAsciiAlphaLower(char c2) {
        return c2 >= 'a' && c2 <= 'z';
    }

    public static boolean isAsciiAlphaUpper(char c2) {
        return c2 >= 'A' && c2 <= 'Z';
    }

    public static boolean isAsciiAlphanumeric(char c2) {
        return (c2 >= 'A' && c2 <= 'Z') || (c2 >= 'a' && c2 <= 'z') || (c2 >= '0' && c2 <= '9');
    }

    public static boolean isAsciiControl(char c2) {
        return c2 < ' ' || c2 == 127;
    }

    public static boolean isAsciiNumeric(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    public static boolean isAsciiPrintable(char c2) {
        return c2 >= ' ' && c2 < 127;
    }

    public static char toChar(Character ch) {
        if (ch != null) {
            return ch.charValue();
        }
        throw new IllegalArgumentException("The Character must not be null");
    }

    public static Character toCharacterObject(char c2) {
        Character[] chArr = CHAR_ARRAY;
        return c2 < chArr.length ? chArr[c2] : new Character(c2);
    }

    public static int toIntValue(char c2) {
        if (isAsciiNumeric(c2)) {
            return c2 - '0';
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The character ");
        stringBuffer.append(c2);
        stringBuffer.append(" is not in the range '0' - '9'");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static String toString(char c2) {
        return c2 < 128 ? CHAR_STRING_ARRAY[c2] : new String(new char[]{c2});
    }

    public static String unicodeEscaped(char c2) {
        if (c2 < 16) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\\u000");
            stringBuffer.append(Integer.toHexString(c2));
            return stringBuffer.toString();
        }
        if (c2 < 256) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("\\u00");
            stringBuffer2.append(Integer.toHexString(c2));
            return stringBuffer2.toString();
        }
        if (c2 < 4096) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\\u0");
            stringBuffer3.append(Integer.toHexString(c2));
            return stringBuffer3.toString();
        }
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("\\u");
        stringBuffer4.append(Integer.toHexString(c2));
        return stringBuffer4.toString();
    }

    public static char toChar(Character ch, char c2) {
        return ch == null ? c2 : ch.charValue();
    }

    public static int toIntValue(char c2, int i2) {
        return !isAsciiNumeric(c2) ? i2 : c2 - '0';
    }

    public static String toString(Character ch) {
        if (ch == null) {
            return null;
        }
        return toString(ch.charValue());
    }

    public static char toChar(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str.charAt(0);
        }
        throw new IllegalArgumentException("The String must not be empty");
    }

    public static Character toCharacterObject(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return toCharacterObject(str.charAt(0));
    }

    public static int toIntValue(Character ch) {
        if (ch != null) {
            return toIntValue(ch.charValue());
        }
        throw new IllegalArgumentException("The character must not be null");
    }

    public static String unicodeEscaped(Character ch) {
        if (ch == null) {
            return null;
        }
        return unicodeEscaped(ch.charValue());
    }

    public static int toIntValue(Character ch, int i2) {
        return ch == null ? i2 : toIntValue(ch.charValue(), i2);
    }

    public static char toChar(String str, char c2) {
        return StringUtils.isEmpty(str) ? c2 : str.charAt(0);
    }
}
