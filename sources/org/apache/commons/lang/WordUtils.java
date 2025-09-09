package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class WordUtils {
    public static String abbreviate(String str, int i2, int i3, String str2) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        if (i2 > str.length()) {
            i2 = str.length();
        }
        if (i3 == -1 || i3 > str.length()) {
            i3 = str.length();
        }
        if (i3 < i2) {
            i3 = i2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int iIndexOf = StringUtils.indexOf(str, " ", i2);
        if (iIndexOf == -1) {
            stringBuffer.append(str.substring(0, i3));
            if (i3 != str.length()) {
                stringBuffer.append(StringUtils.defaultString(str2));
            }
        } else if (iIndexOf > i3) {
            stringBuffer.append(str.substring(0, i3));
            stringBuffer.append(StringUtils.defaultString(str2));
        } else {
            stringBuffer.append(str.substring(0, iIndexOf));
            stringBuffer.append(StringUtils.defaultString(str2));
        }
        return stringBuffer.toString();
    }

    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalizeFully(String str) {
        return capitalizeFully(str, null);
    }

    public static String initials(String str) {
        return initials(str, null);
    }

    private static boolean isDelimiter(char c2, char[] cArr) {
        if (cArr == null) {
            return Character.isWhitespace(c2);
        }
        for (char c3 : cArr) {
            if (c2 == c3) {
                return true;
            }
        }
        return false;
    }

    public static String swapCase(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(length);
        boolean zIsWhitespace = true;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            stringBuffer.append(Character.isUpperCase(cCharAt) ? Character.toLowerCase(cCharAt) : Character.isTitleCase(cCharAt) ? Character.toLowerCase(cCharAt) : Character.isLowerCase(cCharAt) ? zIsWhitespace ? Character.toTitleCase(cCharAt) : Character.toUpperCase(cCharAt) : cCharAt);
            zIsWhitespace = Character.isWhitespace(cCharAt);
        }
        return stringBuffer.toString();
    }

    public static String uncapitalize(String str) {
        return uncapitalize(str, null);
    }

    public static String wrap(String str, int i2) {
        return wrap(str, i2, null, false);
    }

    public static String capitalize(String str, char[] cArr) {
        int length = cArr == null ? -1 : cArr.length;
        if (str == null || str.length() == 0 || length == 0) {
            return str;
        }
        int length2 = str.length();
        StringBuffer stringBuffer = new StringBuffer(length2);
        boolean z2 = true;
        for (int i2 = 0; i2 < length2; i2++) {
            char cCharAt = str.charAt(i2);
            if (isDelimiter(cCharAt, cArr)) {
                stringBuffer.append(cCharAt);
                z2 = true;
            } else if (z2) {
                stringBuffer.append(Character.toTitleCase(cCharAt));
                z2 = false;
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    public static String capitalizeFully(String str, char[] cArr) {
        return (str == null || str.length() == 0 || (cArr == null ? -1 : cArr.length) == 0) ? str : capitalize(str.toLowerCase(), cArr);
    }

    public static String initials(String str, char[] cArr) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (cArr != null && cArr.length == 0) {
            return "";
        }
        int length = str.length();
        char[] cArr2 = new char[(length / 2) + 1];
        boolean z2 = true;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (isDelimiter(cCharAt, cArr)) {
                z2 = true;
            } else if (z2) {
                cArr2[i2] = cCharAt;
                i2++;
                z2 = false;
            }
        }
        return new String(cArr2, 0, i2);
    }

    public static String uncapitalize(String str, char[] cArr) {
        int length = cArr == null ? -1 : cArr.length;
        if (str == null || str.length() == 0 || length == 0) {
            return str;
        }
        int length2 = str.length();
        StringBuffer stringBuffer = new StringBuffer(length2);
        boolean z2 = true;
        for (int i2 = 0; i2 < length2; i2++) {
            char cCharAt = str.charAt(i2);
            if (isDelimiter(cCharAt, cArr)) {
                stringBuffer.append(cCharAt);
                z2 = true;
            } else if (z2) {
                stringBuffer.append(Character.toLowerCase(cCharAt));
                z2 = false;
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    public static String wrap(String str, int i2, String str2, boolean z2) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = SystemUtils.LINE_SEPARATOR;
        }
        if (i2 < 1) {
            i2 = 1;
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 32);
        int i3 = 0;
        while (length - i3 > i2) {
            if (str.charAt(i3) == ' ') {
                i3++;
            } else {
                int i4 = i2 + i3;
                int iLastIndexOf = str.lastIndexOf(32, i4);
                if (iLastIndexOf >= i3) {
                    stringBuffer.append(str.substring(i3, iLastIndexOf));
                    stringBuffer.append(str2);
                    i3 = iLastIndexOf + 1;
                } else {
                    if (z2) {
                        stringBuffer.append(str.substring(i3, i4));
                        stringBuffer.append(str2);
                    } else {
                        int iIndexOf = str.indexOf(32, i4);
                        if (iIndexOf >= 0) {
                            stringBuffer.append(str.substring(i3, iIndexOf));
                            stringBuffer.append(str2);
                            i4 = iIndexOf + 1;
                        } else {
                            stringBuffer.append(str.substring(i3));
                            i3 = length;
                        }
                    }
                    i3 = i4;
                }
            }
        }
        stringBuffer.append(str.substring(i3));
        return stringBuffer.toString();
    }
}
