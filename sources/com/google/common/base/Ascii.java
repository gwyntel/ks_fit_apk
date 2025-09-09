package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Ascii {
    public static final byte ACK = 6;
    public static final byte BEL = 7;
    public static final byte BS = 8;
    public static final byte CAN = 24;
    private static final char CASE_MASK = ' ';
    public static final byte CR = 13;
    public static final byte DC1 = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte DC4 = 20;
    public static final byte DEL = Byte.MAX_VALUE;
    public static final byte DLE = 16;
    public static final byte EM = 25;
    public static final byte ENQ = 5;
    public static final byte EOT = 4;
    public static final byte ESC = 27;
    public static final byte ETB = 23;
    public static final byte ETX = 3;
    public static final byte FF = 12;
    public static final byte FS = 28;
    public static final byte GS = 29;
    public static final byte HT = 9;
    public static final byte LF = 10;
    public static final char MAX = 127;
    public static final char MIN = 0;
    public static final byte NAK = 21;
    public static final byte NL = 10;
    public static final byte NUL = 0;
    public static final byte RS = 30;
    public static final byte SI = 15;
    public static final byte SO = 14;
    public static final byte SOH = 1;
    public static final byte SP = 32;
    public static final byte SPACE = 32;
    public static final byte STX = 2;
    public static final byte SUB = 26;
    public static final byte SYN = 22;
    public static final byte US = 31;
    public static final byte VT = 11;
    public static final byte XOFF = 19;
    public static final byte XON = 17;

    private Ascii() {
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int alphaIndex;
        int length = charSequence.length();
        if (charSequence == charSequence2) {
            return true;
        }
        if (length != charSequence2.length()) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = charSequence.charAt(i2);
            char cCharAt2 = charSequence2.charAt(i2);
            if (cCharAt != cCharAt2 && ((alphaIndex = getAlphaIndex(cCharAt)) >= 26 || alphaIndex != getAlphaIndex(cCharAt2))) {
                return false;
            }
        }
        return true;
    }

    private static int getAlphaIndex(char c2) {
        return (char) ((c2 | CASE_MASK) - 97);
    }

    public static boolean isLowerCase(char c2) {
        return c2 >= 'a' && c2 <= 'z';
    }

    public static boolean isUpperCase(char c2) {
        return c2 >= 'A' && c2 <= 'Z';
    }

    public static String toLowerCase(String str) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            if (isUpperCase(str.charAt(i2))) {
                char[] charArray = str.toCharArray();
                while (i2 < length) {
                    char c2 = charArray[i2];
                    if (isUpperCase(c2)) {
                        charArray[i2] = (char) (c2 ^ CASE_MASK);
                    }
                    i2++;
                }
                return String.valueOf(charArray);
            }
            i2++;
        }
        return str;
    }

    public static String toUpperCase(String str) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            if (isLowerCase(str.charAt(i2))) {
                char[] charArray = str.toCharArray();
                while (i2 < length) {
                    char c2 = charArray[i2];
                    if (isLowerCase(c2)) {
                        charArray[i2] = (char) (c2 ^ CASE_MASK);
                    }
                    i2++;
                }
                return String.valueOf(charArray);
            }
            i2++;
        }
        return str;
    }

    public static String truncate(CharSequence charSequence, int i2, String str) {
        Preconditions.checkNotNull(charSequence);
        int length = i2 - str.length();
        Preconditions.checkArgument(length >= 0, "maxLength (%s) must be >= length of the truncation indicator (%s)", i2, str.length());
        int length2 = charSequence.length();
        String str2 = charSequence;
        if (length2 <= i2) {
            String string = charSequence.toString();
            int length3 = string.length();
            str2 = string;
            if (length3 <= i2) {
                return string;
            }
        }
        StringBuilder sb = new StringBuilder(i2);
        sb.append((CharSequence) str2, 0, length);
        sb.append(str);
        return sb.toString();
    }

    public static String toLowerCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return toLowerCase((String) charSequence);
        }
        int length = charSequence.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = toLowerCase(charSequence.charAt(i2));
        }
        return String.valueOf(cArr);
    }

    public static String toUpperCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return toUpperCase((String) charSequence);
        }
        int length = charSequence.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = toUpperCase(charSequence.charAt(i2));
        }
        return String.valueOf(cArr);
    }

    public static char toLowerCase(char c2) {
        return isUpperCase(c2) ? (char) (c2 ^ CASE_MASK) : c2;
    }

    public static char toUpperCase(char c2) {
        return isLowerCase(c2) ? (char) (c2 ^ CASE_MASK) : c2;
    }
}
