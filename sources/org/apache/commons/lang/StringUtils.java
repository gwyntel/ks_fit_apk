package org.apache.commons.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public static String abbreviate(String str, int i2) {
        return abbreviate(str, 0, i2);
    }

    public static String abbreviateMiddle(String str, String str2, int i2) {
        if (isEmpty(str) || isEmpty(str2) || i2 >= str.length() || i2 < str2.length() + 2) {
            return str;
        }
        int length = i2 - str2.length();
        int i3 = length / 2;
        int i4 = (length % 2) + i3;
        int length2 = str.length() - i3;
        StrBuilder strBuilder = new StrBuilder(i2);
        strBuilder.append(str.substring(0, i4));
        strBuilder.append(str2);
        strBuilder.append(str.substring(length2));
        return strBuilder.toString();
    }

    public static String capitalise(String str) {
        return capitalize(str);
    }

    public static String capitaliseAllWords(String str) {
        return WordUtils.capitalize(str);
    }

    public static String capitalize(String str) {
        int length;
        return (str == null || (length = str.length()) == 0) ? str : new StrBuilder(length).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String center(String str, int i2) {
        return center(str, i2, ' ');
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String chomp(java.lang.String r6) {
        /*
            boolean r0 = isEmpty(r6)
            if (r0 == 0) goto L7
            return r6
        L7:
            int r0 = r6.length()
            r1 = 10
            r2 = 0
            r3 = 1
            r4 = 13
            if (r0 != r3) goto L20
            char r0 = r6.charAt(r2)
            if (r0 == r4) goto L1d
            if (r0 != r1) goto L1c
            goto L1d
        L1c:
            return r6
        L1d:
            java.lang.String r6 = ""
            return r6
        L20:
            int r0 = r6.length()
            int r3 = r0 + (-1)
            char r5 = r6.charAt(r3)
            if (r5 != r1) goto L37
            int r1 = r0 + (-2)
            char r1 = r6.charAt(r1)
            if (r1 != r4) goto L3a
            int r0 = r0 + (-2)
            goto L3b
        L37:
            if (r5 == r4) goto L3a
            goto L3b
        L3a:
            r0 = r3
        L3b:
            java.lang.String r6 = r6.substring(r2, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.StringUtils.chomp(java.lang.String):java.lang.String");
    }

    public static String chompLast(String str) {
        return chompLast(str, "\n");
    }

    public static String chop(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length < 2) {
            return "";
        }
        int i2 = length - 1;
        String strSubstring = str.substring(0, i2);
        if (str.charAt(i2) == '\n') {
            int i3 = length - 2;
            if (strSubstring.charAt(i3) == '\r') {
                return strSubstring.substring(0, i3);
            }
        }
        return strSubstring;
    }

    public static String chopNewline(String str) {
        int length = str.length();
        int i2 = length - 1;
        if (i2 <= 0) {
            return "";
        }
        if (str.charAt(i2) == '\n') {
            length = str.charAt(length + (-2)) == '\r' ? length - 2 : i2;
        }
        return str.substring(0, length);
    }

    public static String clean(String str) {
        return str == null ? "" : str.trim();
    }

    public static String concatenate(Object[] objArr) {
        return join(objArr, (String) null);
    }

    public static boolean contains(String str, char c2) {
        return !isEmpty(str) && str.indexOf(c2) >= 0;
    }

    public static boolean containsAny(String str, char[] cArr) {
        if (!isEmpty(str) && !ArrayUtils.isEmpty(cArr)) {
            int length = str.length();
            int length2 = cArr.length;
            int i2 = length - 1;
            int i3 = length2 - 1;
            for (int i4 = 0; i4 < length; i4++) {
                char cCharAt = str.charAt(i4);
                for (int i5 = 0; i5 < length2; i5++) {
                    if (cArr[i5] == cCharAt) {
                        if (!CharUtils.a(cCharAt) || i5 == i3) {
                            return true;
                        }
                        if (i4 < i2 && cArr[i5 + 1] == str.charAt(i4 + 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(String str, String str2) {
        if (str != null && str2 != null) {
            int length = str2.length();
            int length2 = str.length() - length;
            for (int i2 = 0; i2 <= length2; i2++) {
                if (str.regionMatches(true, i2, str2, 0, length)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsNone(String str, char[] cArr) {
        if (str != null && cArr != null) {
            int length = str.length();
            int i2 = length - 1;
            int length2 = cArr.length;
            int i3 = length2 - 1;
            for (int i4 = 0; i4 < length; i4++) {
                char cCharAt = str.charAt(i4);
                for (int i5 = 0; i5 < length2; i5++) {
                    if (cArr[i5] == cCharAt) {
                        if (!CharUtils.a(cCharAt) || i5 == i3) {
                            return false;
                        }
                        if (i4 < i2 && cArr[i5 + 1] == str.charAt(i4 + 1)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsOnly(String str, char[] cArr) {
        if (cArr == null || str == null) {
            return false;
        }
        if (str.length() == 0) {
            return true;
        }
        return cArr.length != 0 && indexOfAnyBut(str, cArr) == -1;
    }

    public static int countMatches(String str, String str2) {
        int length = 0;
        if (isEmpty(str) || isEmpty(str2)) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            int iIndexOf = str.indexOf(str2, length);
            if (iIndexOf == -1) {
                return i2;
            }
            i2++;
            length = iIndexOf + str2.length();
        }
    }

    public static String defaultIfBlank(String str, String str2) {
        return isBlank(str) ? str2 : str;
    }

    public static String defaultIfEmpty(String str, String str2) {
        return isEmpty(str) ? str2 : str;
    }

    public static String defaultString(String str, String str2) {
        return str == null ? str2 : str;
    }

    public static String deleteSpaces(String str) {
        if (str == null) {
            return null;
        }
        return CharSetUtils.delete(str, " \t\r\n\b");
    }

    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (!Character.isWhitespace(str.charAt(i3))) {
                cArr[i2] = str.charAt(i3);
                i2++;
            }
        }
        return i2 == length ? str : new String(cArr, 0, i2);
    }

    public static String difference(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        int iIndexOfDifference = indexOfDifference(str, str2);
        return iIndexOfDifference == -1 ? "" : str2.substring(iIndexOfDifference);
    }

    public static boolean endsWith(String str, String str2) {
        return endsWith(str, str2, false);
    }

    public static boolean endsWithAny(String str, String[] strArr) {
        if (!isEmpty(str) && !ArrayUtils.isEmpty(strArr)) {
            for (String str2 : strArr) {
                if (endsWith(str, str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean endsWithIgnoreCase(String str, String str2) {
        return endsWith(str, str2, true);
    }

    public static boolean equals(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        return str == null ? str2 == null : str.equalsIgnoreCase(str2);
    }

    public static String escape(String str) {
        return StringEscapeUtils.escapeJava(str);
    }

    public static String getChomp(String str, String str2) {
        int iLastIndexOf = str.lastIndexOf(str2);
        return iLastIndexOf == str.length() - str2.length() ? str2 : iLastIndexOf != -1 ? str.substring(iLastIndexOf) : "";
    }

    public static String getCommonPrefix(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        int iIndexOfDifference = indexOfDifference(strArr);
        if (iIndexOfDifference != -1) {
            return iIndexOfDifference == 0 ? "" : strArr[0].substring(0, iIndexOfDifference);
        }
        String str = strArr[0];
        return str == null ? "" : str;
    }

    public static int getLevenshteinDistance(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = str.length();
        int length2 = str2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            length2 = str.length();
            length = length2;
        } else {
            str2 = str;
            str = str2;
        }
        int i2 = length + 1;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        for (int i3 = 0; i3 <= length; i3++) {
            iArr[i3] = i3;
        }
        int i4 = 1;
        while (i4 <= length2) {
            char cCharAt = str.charAt(i4 - 1);
            iArr2[0] = i4;
            for (int i5 = 1; i5 <= length; i5++) {
                int i6 = i5 - 1;
                iArr2[i5] = Math.min(Math.min(iArr2[i6] + 1, iArr[i5] + 1), iArr[i6] + (str2.charAt(i6) == cCharAt ? 0 : 1));
            }
            i4++;
            int[] iArr3 = iArr;
            iArr = iArr2;
            iArr2 = iArr3;
        }
        return iArr[length];
    }

    public static String getNestedString(String str, String str2) {
        return substringBetween(str, str2, str2);
    }

    public static String getPrechomp(String str, String str2) {
        int iIndexOf = str.indexOf(str2);
        return iIndexOf == -1 ? "" : str.substring(0, iIndexOf + str2.length());
    }

    public static int indexOf(String str, char c2) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(c2);
    }

    public static int indexOfAny(String str, char[] cArr) {
        if (!isEmpty(str) && !ArrayUtils.isEmpty(cArr)) {
            int length = str.length();
            int i2 = length - 1;
            int length2 = cArr.length;
            int i3 = length2 - 1;
            for (int i4 = 0; i4 < length; i4++) {
                char cCharAt = str.charAt(i4);
                for (int i5 = 0; i5 < length2; i5++) {
                    if (cArr[i5] == cCharAt && (i4 >= i2 || i5 >= i3 || !CharUtils.a(cCharAt) || cArr[i5 + 1] == str.charAt(i4 + 1))) {
                        return i4;
                    }
                }
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003c, code lost:
    
        r6 = r6 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOfAnyBut(java.lang.String r11, char[] r12) {
        /*
            boolean r0 = isEmpty(r11)
            r1 = -1
            if (r0 != 0) goto L43
            boolean r0 = org.apache.commons.lang.ArrayUtils.isEmpty(r12)
            if (r0 == 0) goto Le
            goto L43
        Le:
            int r0 = r11.length()
            int r2 = r0 + (-1)
            int r3 = r12.length
            int r4 = r3 + (-1)
            r5 = 0
            r6 = r5
        L19:
            if (r6 >= r0) goto L43
            char r7 = r11.charAt(r6)
            r8 = r5
        L20:
            if (r8 >= r3) goto L42
            char r9 = r12[r8]
            if (r9 != r7) goto L3f
            if (r6 >= r2) goto L3c
            if (r8 >= r4) goto L3c
            boolean r9 = org.apache.commons.lang.CharUtils.a(r7)
            if (r9 == 0) goto L3c
            int r9 = r8 + 1
            char r9 = r12[r9]
            int r10 = r6 + 1
            char r10 = r11.charAt(r10)
            if (r9 != r10) goto L3f
        L3c:
            int r6 = r6 + 1
            goto L19
        L3f:
            int r8 = r8 + 1
            goto L20
        L42:
            return r6
        L43:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.StringUtils.indexOfAnyBut(java.lang.String, char[]):int");
    }

    public static int indexOfDifference(String str, String str2) {
        if (str == str2) {
            return -1;
        }
        int i2 = 0;
        if (str != null && str2 != null) {
            while (i2 < str.length() && i2 < str2.length() && str.charAt(i2) == str2.charAt(i2)) {
                i2++;
            }
            if (i2 >= str2.length() && i2 >= str.length()) {
                return -1;
            }
        }
        return i2;
    }

    public static int indexOfIgnoreCase(String str, String str2) {
        return indexOfIgnoreCase(str, str2, 0);
    }

    public static boolean isAllLowerCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isLowerCase(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllUpperCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isUpperCase(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isLetter(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isLetter(str.charAt(i2)) && str.charAt(i2) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isLetterOrDigit(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isLetterOrDigit(str.charAt(i2)) && str.charAt(i2) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAsciiPrintable(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!CharUtils.isAsciiPrintable(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                if (!Character.isWhitespace(str.charAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isDigit(str.charAt(i2)) && str.charAt(i2) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static String join(Object[] objArr) {
        return join(objArr, (String) null);
    }

    public static int lastIndexOf(String str, char c2) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(c2);
    }

    public static int lastIndexOfAny(String str, String[] strArr) {
        int iLastIndexOf;
        int i2 = -1;
        if (str != null && strArr != null) {
            for (String str2 : strArr) {
                if (str2 != null && (iLastIndexOf = str.lastIndexOf(str2)) > i2) {
                    i2 = iLastIndexOf;
                }
            }
        }
        return i2;
    }

    public static int lastIndexOfIgnoreCase(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(str, str2, str.length());
    }

    public static int lastOrdinalIndexOf(String str, String str2, int i2) {
        return ordinalIndexOf(str, str2, i2, true);
    }

    public static String left(String str, int i2) {
        if (str == null) {
            return null;
        }
        return i2 < 0 ? "" : str.length() <= i2 ? str : str.substring(0, i2);
    }

    public static String leftPad(String str, int i2) {
        return leftPad(str, i2, ' ');
    }

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String mid(String str, int i2, int i3) {
        if (str == null) {
            return null;
        }
        if (i3 < 0 || i2 > str.length()) {
            return "";
        }
        if (i2 < 0) {
            i2 = 0;
        }
        int i4 = i3 + i2;
        return str.length() <= i4 ? str.substring(i2) : str.substring(i2, i4);
    }

    public static String normalizeSpace(String str) {
        String strStrip = strip(str);
        if (strStrip == null || strStrip.length() <= 2) {
            return strStrip;
        }
        StrBuilder strBuilder = new StrBuilder(strStrip.length());
        for (int i2 = 0; i2 < strStrip.length(); i2++) {
            char cCharAt = strStrip.charAt(i2);
            if (!Character.isWhitespace(cCharAt)) {
                strBuilder.append(cCharAt);
            } else if (i2 > 0 && !Character.isWhitespace(strStrip.charAt(i2 - 1))) {
                strBuilder.append(' ');
            }
        }
        return strBuilder.toString();
    }

    public static int ordinalIndexOf(String str, String str2, int i2) {
        return ordinalIndexOf(str, str2, i2, false);
    }

    public static String overlay(String str, String str2, int i2, int i3) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = "";
        }
        int length = str.length();
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > length) {
            i2 = length;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        if (i3 > length) {
            i3 = length;
        }
        if (i2 > i3) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
        }
        return new StrBuilder(((length + i2) - i3) + str2.length() + 1).append(str.substring(0, i2)).append(str2).append(str.substring(i3)).toString();
    }

    public static String overlayString(String str, String str2, int i2, int i3) {
        return new StrBuilder((((str2.length() + i2) + str.length()) - i3) + 1).append(str.substring(0, i2)).append(str2).append(str.substring(i3)).toString();
    }

    private static String padding(int i2, char c2) throws IndexOutOfBoundsException {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot pad a negative amount: ");
            stringBuffer.append(i2);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = c2;
        }
        return new String(cArr);
    }

    public static String prechomp(String str, String str2) {
        int iIndexOf = str.indexOf(str2);
        return iIndexOf == -1 ? str : str.substring(iIndexOf + str2.length());
    }

    public static String remove(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2)) ? str : replace(str, str2, "", -1);
    }

    public static String removeEnd(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !str.endsWith(str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static String removeEndIgnoreCase(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !endsWithIgnoreCase(str, str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static String removeStart(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !str.startsWith(str2)) ? str : str.substring(str2.length());
    }

    public static String removeStartIgnoreCase(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !startsWithIgnoreCase(str, str2)) ? str : str.substring(str2.length());
    }

    public static String repeat(String str, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 <= 0) {
            return "";
        }
        int length = str.length();
        if (i2 == 1 || length == 0) {
            return str;
        }
        if (length == 1 && i2 <= 8192) {
            return padding(i2, str.charAt(0));
        }
        int i3 = length * i2;
        if (length == 1) {
            char cCharAt = str.charAt(0);
            char[] cArr = new char[i3];
            for (int i4 = i2 - 1; i4 >= 0; i4--) {
                cArr[i4] = cCharAt;
            }
            return new String(cArr);
        }
        if (length != 2) {
            StrBuilder strBuilder = new StrBuilder(i3);
            for (int i5 = 0; i5 < i2; i5++) {
                strBuilder.append(str);
            }
            return strBuilder.toString();
        }
        char cCharAt2 = str.charAt(0);
        char cCharAt3 = str.charAt(1);
        char[] cArr2 = new char[i3];
        for (int i6 = (i2 * 2) - 2; i6 >= 0; i6 -= 2) {
            cArr2[i6] = cCharAt2;
            cArr2[i6 + 1] = cCharAt3;
        }
        return new String(cArr2);
    }

    public static String replace(String str, String str2, String str3) {
        return replace(str, str2, str3, -1);
    }

    public static String replaceChars(String str, char c2, char c3) {
        if (str == null) {
            return null;
        }
        return str.replace(c2, c3);
    }

    public static String replaceEach(String str, String[] strArr, String[] strArr2) {
        return replaceEach(str, strArr, strArr2, false, 0);
    }

    public static String replaceEachRepeatedly(String str, String[] strArr, String[] strArr2) {
        return replaceEach(str, strArr, strArr2, true, strArr == null ? 0 : strArr.length);
    }

    public static String replaceOnce(String str, String str2, String str3) {
        return replace(str, str2, str3, 1);
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StrBuilder(str).reverse().toString();
    }

    public static String reverseDelimited(String str, char c2) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = split(str, c2);
        ArrayUtils.reverse(strArrSplit);
        return join(strArrSplit, c2);
    }

    public static String reverseDelimitedString(String str, String str2) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = split(str, str2);
        ArrayUtils.reverse(strArrSplit);
        return str2 == null ? join((Object[]) strArrSplit, ' ') : join(strArrSplit, str2);
    }

    public static String right(String str, int i2) {
        if (str == null) {
            return null;
        }
        return i2 < 0 ? "" : str.length() <= i2 ? str : str.substring(str.length() - i2);
    }

    public static String rightPad(String str, int i2) {
        return rightPad(str, i2, ' ');
    }

    public static String[] split(String str) {
        return split(str, null, -1);
    }

    public static String[] splitByCharacterType(String str) {
        return splitByCharacterType(str, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return splitByCharacterType(str, true);
    }

    public static String[] splitByWholeSeparator(String str, String str2) {
        return splitByWholeSeparatorWorker(str, str2, -1, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2) {
        return splitByWholeSeparatorWorker(str, str2, -1, true);
    }

    private static String[] splitByWholeSeparatorWorker(String str, String str2, int i2, boolean z2) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (str2 == null || "".equals(str2)) {
            return splitWorker(str, null, i2, z2);
        }
        int length2 = str2.length();
        ArrayList arrayList = new ArrayList();
        int iIndexOf = 0;
        int i3 = 0;
        int i4 = 0;
        while (iIndexOf < length) {
            iIndexOf = str.indexOf(str2, i3);
            if (iIndexOf > -1) {
                if (iIndexOf > i3) {
                    i4++;
                    if (i4 == i2) {
                        arrayList.add(str.substring(i3));
                    } else {
                        arrayList.add(str.substring(i3, iIndexOf));
                    }
                } else if (z2) {
                    i4++;
                    if (i4 == i2) {
                        arrayList.add(str.substring(i3));
                        iIndexOf = length;
                    } else {
                        arrayList.add("");
                    }
                }
                i3 = iIndexOf + length2;
            } else {
                arrayList.add(str.substring(i3));
            }
            iIndexOf = length;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return splitWorker(str, null, -1, true);
    }

    private static String[] splitWorker(String str, char c2, boolean z2) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        boolean z3 = false;
        boolean z4 = false;
        int i3 = 0;
        while (i2 < length) {
            if (str.charAt(i2) == c2) {
                if (z3 || z2) {
                    arrayList.add(str.substring(i3, i2));
                    z3 = false;
                    z4 = true;
                }
                i3 = i2 + 1;
                i2 = i3;
            } else {
                i2++;
                z4 = false;
                z3 = true;
            }
        }
        if (z3 || (z2 && z4)) {
            arrayList.add(str.substring(i3, i2));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean startsWith(String str, String str2) {
        return startsWith(str, str2, false);
    }

    public static boolean startsWithAny(String str, String[] strArr) {
        if (!isEmpty(str) && !ArrayUtils.isEmpty(strArr)) {
            for (String str2 : strArr) {
                if (startsWith(str, str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        return startsWith(str, str2, true);
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String[] stripAll(String[] strArr) {
        return stripAll(strArr, null);
    }

    public static String stripEnd(String str, String str2) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        if (str2 == null) {
            while (length != 0 && Character.isWhitespace(str.charAt(length - 1))) {
                length--;
            }
        } else {
            if (str2.length() == 0) {
                return str;
            }
            while (length != 0 && str2.indexOf(str.charAt(length - 1)) != -1) {
                length--;
            }
        }
        return str.substring(0, length);
    }

    public static String stripStart(String str, String str2) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        int i2 = 0;
        if (str2 == null) {
            while (i2 != length && Character.isWhitespace(str.charAt(i2))) {
                i2++;
            }
        } else {
            if (str2.length() == 0) {
                return str;
            }
            while (i2 != length && str2.indexOf(str.charAt(i2)) != -1) {
                i2++;
            }
        }
        return str.substring(i2);
    }

    public static String stripToEmpty(String str) {
        return str == null ? "" : strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        String strStrip = strip(str, null);
        if (strStrip.length() == 0) {
            return null;
        }
        return strStrip;
    }

    public static String substring(String str, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 0) {
            i2 += str.length();
        }
        if (i2 < 0) {
            i2 = 0;
        }
        return i2 > str.length() ? "" : str.substring(i2);
    }

    public static String substringAfter(String str, String str2) {
        int iIndexOf;
        return isEmpty(str) ? str : (str2 == null || (iIndexOf = str.indexOf(str2)) == -1) ? "" : str.substring(iIndexOf + str2.length());
    }

    public static String substringAfterLast(String str, String str2) {
        int iLastIndexOf;
        return isEmpty(str) ? str : (isEmpty(str2) || (iLastIndexOf = str.lastIndexOf(str2)) == -1 || iLastIndexOf == str.length() - str2.length()) ? "" : str.substring(iLastIndexOf + str2.length());
    }

    public static String substringBefore(String str, String str2) {
        if (isEmpty(str) || str2 == null) {
            return str;
        }
        if (str2.length() == 0) {
            return "";
        }
        int iIndexOf = str.indexOf(str2);
        return iIndexOf == -1 ? str : str.substring(0, iIndexOf);
    }

    public static String substringBeforeLast(String str, String str2) {
        int iLastIndexOf;
        return (isEmpty(str) || isEmpty(str2) || (iLastIndexOf = str.lastIndexOf(str2)) == -1) ? str : str.substring(0, iLastIndexOf);
    }

    public static String substringBetween(String str, String str2) {
        return substringBetween(str, str2, str2);
    }

    public static String[] substringsBetween(String str, String str2, String str3) {
        int iIndexOf;
        int i2;
        int iIndexOf2;
        if (str == null || isEmpty(str2) || isEmpty(str3)) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int length2 = str3.length();
        int length3 = str2.length();
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < length - length2 && (iIndexOf = str.indexOf(str2, i3)) >= 0 && (iIndexOf2 = str.indexOf(str3, (i2 = iIndexOf + length3))) >= 0) {
            arrayList.add(str.substring(i2, iIndexOf2));
            i3 = iIndexOf2 + length2;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String swapCase(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (Character.isUpperCase(cCharAt)) {
                cCharAt = Character.toLowerCase(cCharAt);
            } else if (Character.isTitleCase(cCharAt)) {
                cCharAt = Character.toLowerCase(cCharAt);
            } else if (Character.isLowerCase(cCharAt)) {
                cCharAt = Character.toUpperCase(cCharAt);
            }
            strBuilder.append(cCharAt);
        }
        return strBuilder.toString();
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trimToNull(String str) {
        String strTrim = trim(str);
        if (isEmpty(strTrim)) {
            return null;
        }
        return strTrim;
    }

    public static String uncapitalise(String str) {
        return uncapitalize(str);
    }

    public static String uncapitalize(String str) {
        int length;
        return (str == null || (length = str.length()) == 0) ? str : new StrBuilder(length).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String abbreviate(String str, int i2, int i3) {
        if (str == null) {
            return null;
        }
        if (i3 < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (str.length() <= i3) {
            return str;
        }
        if (i2 > str.length()) {
            i2 = str.length();
        }
        int i4 = i3 - 3;
        if (str.length() - i2 < i4) {
            i2 = str.length() - i4;
        }
        if (i2 <= 4) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str.substring(0, i4));
            stringBuffer.append("...");
            return stringBuffer.toString();
        }
        if (i3 < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (i2 + i4 < str.length()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("...");
            stringBuffer2.append(abbreviate(str.substring(i2), i4));
            return stringBuffer2.toString();
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("...");
        stringBuffer3.append(str.substring(str.length() - i4));
        return stringBuffer3.toString();
    }

    public static String center(String str, int i2, char c2) {
        int length;
        int length2;
        return (str == null || i2 <= 0 || (length2 = i2 - (length = str.length())) <= 0) ? str : rightPad(leftPad(str, length + (length2 / 2), c2), i2, c2);
    }

    public static String chompLast(String str, String str2) {
        return (str.length() != 0 && str2.equals(str.substring(str.length() - str2.length()))) ? str.substring(0, str.length() - str2.length()) : str;
    }

    public static String defaultString(String str) {
        return str == null ? "" : str;
    }

    private static boolean endsWith(String str, String str2, boolean z2) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (str2.length() > str.length()) {
            return false;
        }
        return str.regionMatches(z2, str.length() - str2.length(), str2, 0, str2.length());
    }

    public static String getNestedString(String str, String str2, String str3) {
        return substringBetween(str, str2, str3);
    }

    public static int indexOfIgnoreCase(String str, String str2, int i2) {
        if (str != null && str2 != null) {
            if (i2 < 0) {
                i2 = 0;
            }
            int length = (str.length() - str2.length()) + 1;
            if (i2 > length) {
                return -1;
            }
            if (str2.length() == 0) {
                return i2;
            }
            while (i2 < length) {
                if (str.regionMatches(true, i2, str2, 0, str2.length())) {
                    return i2;
                }
                i2++;
            }
        }
        return -1;
    }

    public static String join(Object[] objArr, char c2) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, c2, 0, objArr.length);
    }

    public static int lastIndexOfIgnoreCase(String str, String str2, int i2) {
        if (str != null && str2 != null) {
            if (i2 > str.length() - str2.length()) {
                i2 = str.length() - str2.length();
            }
            if (i2 < 0) {
                return -1;
            }
            if (str2.length() == 0) {
                return i2;
            }
            while (i2 >= 0) {
                if (str.regionMatches(true, i2, str2, 0, str2.length())) {
                    return i2;
                }
                i2--;
            }
        }
        return -1;
    }

    public static String leftPad(String str, int i2, char c2) {
        if (str == null) {
            return null;
        }
        int length = i2 - str.length();
        return length <= 0 ? str : length > 8192 ? leftPad(str, i2, String.valueOf(c2)) : padding(length, c2).concat(str);
    }

    public static String lowerCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(locale);
    }

    private static int ordinalIndexOf(String str, String str2, int i2, boolean z2) {
        if (str != null && str2 != null && i2 > 0) {
            int i3 = 0;
            if (str2.length() == 0) {
                if (z2) {
                    return str.length();
                }
                return 0;
            }
            length = z2 ? str.length() : -1;
            do {
                length = z2 ? str.lastIndexOf(str2, length - 1) : str.indexOf(str2, length + 1);
                if (length < 0) {
                    return length;
                }
                i3++;
            } while (i3 < i2);
        }
        return length;
    }

    public static String replace(String str, String str2, String str3, int i2) {
        int i3;
        if (isEmpty(str) || isEmpty(str2) || str3 == null || i2 == 0) {
            return str;
        }
        int i4 = 0;
        int iIndexOf = str.indexOf(str2, 0);
        if (iIndexOf == -1) {
            return str;
        }
        int length = str2.length();
        int length2 = str3.length() - length;
        if (length2 < 0) {
            length2 = 0;
        }
        if (i2 < 0) {
            i3 = 16;
        } else {
            i3 = 64;
            if (i2 <= 64) {
                i3 = i2;
            }
        }
        StrBuilder strBuilder = new StrBuilder(str.length() + (length2 * i3));
        while (iIndexOf != -1) {
            strBuilder.append(str.substring(i4, iIndexOf)).append(str3);
            i4 = iIndexOf + length;
            i2--;
            if (i2 == 0) {
                break;
            }
            iIndexOf = str.indexOf(str2, i4);
        }
        strBuilder.append(str.substring(i4));
        return strBuilder.toString();
    }

    public static String replaceChars(String str, String str2, String str3) {
        if (isEmpty(str) || isEmpty(str2)) {
            return str;
        }
        if (str3 == null) {
            str3 = "";
        }
        int length = str3.length();
        int length2 = str.length();
        StrBuilder strBuilder = new StrBuilder(length2);
        boolean z2 = false;
        for (int i2 = 0; i2 < length2; i2++) {
            char cCharAt = str.charAt(i2);
            int iIndexOf = str2.indexOf(cCharAt);
            if (iIndexOf >= 0) {
                if (iIndexOf < length) {
                    strBuilder.append(str3.charAt(iIndexOf));
                }
                z2 = true;
            } else {
                strBuilder.append(cCharAt);
            }
        }
        return z2 ? strBuilder.toString() : str;
    }

    private static String replaceEach(String str, String[] strArr, String[] strArr2, boolean z2, int i2) {
        String str2;
        String str3;
        int length;
        String str4;
        if (str == null || str.length() == 0 || strArr == null || strArr.length == 0 || strArr2 == null || strArr2.length == 0) {
            return str;
        }
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("TimeToLive of ");
            stringBuffer.append(i2);
            stringBuffer.append(" is less than 0: ");
            stringBuffer.append(str);
            throw new IllegalStateException(stringBuffer.toString());
        }
        int length2 = strArr.length;
        int length3 = strArr2.length;
        if (length2 != length3) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Search and Replace array lengths don't match: ");
            stringBuffer2.append(length2);
            stringBuffer2.append(" vs ");
            stringBuffer2.append(length3);
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
        boolean[] zArr = new boolean[length2];
        int i3 = -1;
        int i4 = -1;
        for (int i5 = 0; i5 < length2; i5++) {
            if (!zArr[i5] && (str4 = strArr[i5]) != null && str4.length() != 0 && strArr2[i5] != null) {
                int iIndexOf = str.indexOf(strArr[i5]);
                if (iIndexOf == -1) {
                    zArr[i5] = true;
                } else if (i3 == -1 || iIndexOf < i3) {
                    i4 = i5;
                    i3 = iIndexOf;
                }
            }
        }
        if (i3 == -1) {
            return str;
        }
        int i6 = 0;
        for (int i7 = 0; i7 < strArr.length; i7++) {
            if (strArr[i7] != null && (str3 = strArr2[i7]) != null && (length = str3.length() - strArr[i7].length()) > 0) {
                i6 += length * 3;
            }
        }
        StrBuilder strBuilder = new StrBuilder(str.length() + Math.min(i6, str.length() / 5));
        int length4 = 0;
        while (i3 != -1) {
            while (length4 < i3) {
                strBuilder.append(str.charAt(length4));
                length4++;
            }
            strBuilder.append(strArr2[i4]);
            length4 = strArr[i4].length() + i3;
            i3 = -1;
            i4 = -1;
            for (int i8 = 0; i8 < length2; i8++) {
                if (!zArr[i8] && (str2 = strArr[i8]) != null && str2.length() != 0 && strArr2[i8] != null) {
                    int iIndexOf2 = str.indexOf(strArr[i8], length4);
                    if (iIndexOf2 == -1) {
                        zArr[i8] = true;
                    } else if (i3 == -1 || iIndexOf2 < i3) {
                        i4 = i8;
                        i3 = iIndexOf2;
                    }
                }
            }
        }
        int length5 = str.length();
        while (length4 < length5) {
            strBuilder.append(str.charAt(length4));
            length4++;
        }
        String string = strBuilder.toString();
        return !z2 ? string : replaceEach(string, strArr, strArr2, z2, i2 - 1);
    }

    public static String rightPad(String str, int i2, char c2) {
        if (str == null) {
            return null;
        }
        int length = i2 - str.length();
        return length <= 0 ? str : length > 8192 ? rightPad(str, i2, String.valueOf(c2)) : str.concat(padding(length, c2));
    }

    public static String[] split(String str, char c2) {
        return splitWorker(str, c2, false);
    }

    private static String[] splitByCharacterType(String str, boolean z2) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int type = Character.getType(charArray[0]);
        for (int i3 = 1; i3 < charArray.length; i3++) {
            int type2 = Character.getType(charArray[i3]);
            if (type2 != type) {
                if (z2 && type2 == 2 && type == 1) {
                    int i4 = i3 - 1;
                    if (i4 != i2) {
                        arrayList.add(new String(charArray, i2, i4 - i2));
                        i2 = i4;
                    }
                } else {
                    arrayList.add(new String(charArray, i2, i3 - i2));
                    i2 = i3;
                }
                type = type2;
            }
        }
        arrayList.add(new String(charArray, i2, charArray.length - i2));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitByWholeSeparator(String str, String str2, int i2) {
        return splitByWholeSeparatorWorker(str, str2, i2, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2, int i2) {
        return splitByWholeSeparatorWorker(str, str2, i2, true);
    }

    public static String[] splitPreserveAllTokens(String str, char c2) {
        return splitWorker(str, c2, true);
    }

    private static boolean startsWith(String str, String str2, boolean z2) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (str2.length() > str.length()) {
            return false;
        }
        return str.regionMatches(z2, 0, str2, 0, str2.length());
    }

    public static String strip(String str, String str2) {
        return isEmpty(str) ? str : stripEnd(stripStart(str, str2), str2);
    }

    public static String[] stripAll(String[] strArr, String str) {
        int length;
        if (strArr == null || (length = strArr.length) == 0) {
            return strArr;
        }
        String[] strArr2 = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            strArr2[i2] = strip(strArr[i2], str);
        }
        return strArr2;
    }

    public static String substringBetween(String str, String str2, String str3) {
        int iIndexOf;
        int iIndexOf2;
        if (str == null || str2 == null || str3 == null || (iIndexOf = str.indexOf(str2)) == -1 || (iIndexOf2 = str.indexOf(str3, str2.length() + iIndexOf)) == -1) {
            return null;
        }
        return str.substring(iIndexOf + str2.length(), iIndexOf2);
    }

    public static String upperCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(locale);
    }

    public static boolean contains(String str, String str2) {
        return (str == null || str2 == null || str.indexOf(str2) < 0) ? false : true;
    }

    public static int indexOf(String str, char c2, int i2) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(c2, i2);
    }

    public static String join(Object[] objArr, char c2, int i2, int i3) {
        if (objArr == null) {
            return null;
        }
        int i4 = i3 - i2;
        if (i4 <= 0) {
            return "";
        }
        Object obj = objArr[i2];
        StrBuilder strBuilder = new StrBuilder(i4 * ((obj == null ? 16 : obj.toString().length()) + 1));
        for (int i5 = i2; i5 < i3; i5++) {
            if (i5 > i2) {
                strBuilder.append(c2);
            }
            Object obj2 = objArr[i5];
            if (obj2 != null) {
                strBuilder.append(obj2);
            }
        }
        return strBuilder.toString();
    }

    public static int lastIndexOf(String str, char c2, int i2) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(c2, i2);
    }

    public static String remove(String str, char c2) {
        if (isEmpty(str) || str.indexOf(c2) == -1) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int i2 = 0;
        for (char c3 : charArray) {
            if (c3 != c2) {
                charArray[i2] = c3;
                i2++;
            }
        }
        return new String(charArray, 0, i2);
    }

    public static String[] split(String str, String str2) {
        return splitWorker(str, str2, -1, false);
    }

    public static String[] splitPreserveAllTokens(String str, String str2) {
        return splitWorker(str, str2, -1, true);
    }

    public static boolean containsOnly(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        return containsOnly(str, str2.toCharArray());
    }

    public static int indexOfDifference(String[] strArr) {
        if (strArr != null && strArr.length > 1) {
            int length = strArr.length;
            int iMin = Integer.MAX_VALUE;
            boolean z2 = true;
            int iMax = 0;
            boolean z3 = false;
            for (int i2 = 0; i2 < length; i2++) {
                String str = strArr[i2];
                if (str == null) {
                    z3 = true;
                    iMin = 0;
                } else {
                    iMin = Math.min(str.length(), iMin);
                    iMax = Math.max(strArr[i2].length(), iMax);
                    z2 = false;
                }
            }
            if (!z2 && (iMax != 0 || z3)) {
                if (iMin == 0) {
                    return 0;
                }
                int i3 = -1;
                for (int i4 = 0; i4 < iMin; i4++) {
                    char cCharAt = strArr[0].charAt(i4);
                    int i5 = 1;
                    while (true) {
                        if (i5 >= length) {
                            break;
                        }
                        if (strArr[i5].charAt(i4) != cCharAt) {
                            i3 = i4;
                            break;
                        }
                        i5++;
                    }
                    if (i3 != -1) {
                        break;
                    }
                }
                return (i3 != -1 || iMin == iMax) ? i3 : iMin;
            }
        }
        return -1;
    }

    public static String[] split(String str, String str2, int i2) {
        return splitWorker(str, str2, i2, false);
    }

    public static String[] splitPreserveAllTokens(String str, String str2, int i2) {
        return splitWorker(str, str2, i2, true);
    }

    public static String center(String str, int i2, String str2) {
        if (str == null || i2 <= 0) {
            return str;
        }
        if (isEmpty(str2)) {
            str2 = " ";
        }
        int length = str.length();
        int i3 = i2 - length;
        return i3 <= 0 ? str : rightPad(leftPad(str, length + (i3 / 2), str2), i2, str2);
    }

    public static int indexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.indexOf(str2);
    }

    public static int lastIndexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2);
    }

    public static String leftPad(String str, int i2, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = " ";
        }
        int length = str2.length();
        int length2 = i2 - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= 8192) {
            return leftPad(str, i2, str2.charAt(0));
        }
        if (length2 == length) {
            return str2.concat(str);
        }
        if (length2 < length) {
            return str2.substring(0, length2).concat(str);
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i3 = 0; i3 < length2; i3++) {
            cArr[i3] = charArray[i3 % length];
        }
        return new String(cArr).concat(str);
    }

    public static String rightPad(String str, int i2, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = " ";
        }
        int length = str2.length();
        int length2 = i2 - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= 8192) {
            return rightPad(str, i2, str2.charAt(0));
        }
        if (length2 == length) {
            return str.concat(str2);
        }
        if (length2 < length) {
            return str.concat(str2.substring(0, length2));
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i3 = 0; i3 < length2; i3++) {
            cArr[i3] = charArray[i3 % length];
        }
        return str.concat(new String(cArr));
    }

    public static String substring(String str, int i2, int i3) {
        if (str == null) {
            return null;
        }
        if (i3 < 0) {
            i3 += str.length();
        }
        if (i2 < 0) {
            i2 += str.length();
        }
        if (i3 > str.length()) {
            i3 = str.length();
        }
        if (i2 > i3) {
            return "";
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        return str.substring(i2, i3);
    }

    public static int indexOf(String str, String str2, int i2) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (str2.length() == 0 && i2 >= str.length()) {
            return str.length();
        }
        return str.indexOf(str2, i2);
    }

    public static int lastIndexOf(String str, String str2, int i2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2, i2);
    }

    public static boolean containsNone(String str, String str2) {
        if (str == null || str2 == null) {
            return true;
        }
        return containsNone(str, str2.toCharArray());
    }

    public static boolean containsAny(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        return containsAny(str, str2.toCharArray());
    }

    public static int indexOfAny(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return -1;
        }
        return indexOfAny(str, str2.toCharArray());
    }

    public static int indexOfAnyBut(String str, String str2) {
        if (!isEmpty(str) && !isEmpty(str2)) {
            int length = str.length();
            int i2 = 0;
            while (i2 < length) {
                char cCharAt = str.charAt(i2);
                boolean z2 = str2.indexOf(cCharAt) >= 0;
                int i3 = i2 + 1;
                if (i3 < length && CharUtils.a(cCharAt)) {
                    char cCharAt2 = str.charAt(i3);
                    if (z2 && str2.indexOf(cCharAt2) < 0) {
                        return i2;
                    }
                } else if (!z2) {
                    return i2;
                }
                i2 = i3;
            }
        }
        return -1;
    }

    private static String[] splitWorker(String str, String str2, int i2, boolean z2) {
        int i3;
        boolean z3;
        boolean z4;
        int i4;
        int i5;
        boolean z5;
        boolean z6;
        int i6;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        if (str2 == null) {
            i5 = 0;
            z5 = false;
            z6 = false;
            i6 = 0;
            int i7 = 1;
            while (i5 < length) {
                if (Character.isWhitespace(str.charAt(i5))) {
                    if (z5 || z2) {
                        int i8 = i7 + 1;
                        if (i7 == i2) {
                            i5 = length;
                            z6 = false;
                        } else {
                            z6 = true;
                        }
                        arrayList.add(str.substring(i6, i5));
                        i7 = i8;
                        z5 = false;
                    }
                    i6 = i5 + 1;
                    i5 = i6;
                } else {
                    i5++;
                    z6 = false;
                    z5 = true;
                }
            }
        } else {
            if (str2.length() == 1) {
                char cCharAt = str2.charAt(0);
                i3 = 0;
                z3 = false;
                z4 = false;
                i4 = 0;
                int i9 = 1;
                while (i3 < length) {
                    if (str.charAt(i3) == cCharAt) {
                        if (z3 || z2) {
                            int i10 = i9 + 1;
                            if (i9 == i2) {
                                i3 = length;
                                z4 = false;
                            } else {
                                z4 = true;
                            }
                            arrayList.add(str.substring(i4, i3));
                            i9 = i10;
                            z3 = false;
                        }
                        i4 = i3 + 1;
                        i3 = i4;
                    } else {
                        i3++;
                        z4 = false;
                        z3 = true;
                    }
                }
            } else {
                i3 = 0;
                z3 = false;
                z4 = false;
                i4 = 0;
                int i11 = 1;
                while (i3 < length) {
                    if (str2.indexOf(str.charAt(i3)) >= 0) {
                        if (z3 || z2) {
                            int i12 = i11 + 1;
                            if (i11 == i2) {
                                i3 = length;
                                z4 = false;
                            } else {
                                z4 = true;
                            }
                            arrayList.add(str.substring(i4, i3));
                            i11 = i12;
                            z3 = false;
                        }
                        i4 = i3 + 1;
                        i3 = i4;
                    } else {
                        i3++;
                        z4 = false;
                        z3 = true;
                    }
                }
            }
            i5 = i3;
            z5 = z3;
            z6 = z4;
            i6 = i4;
        }
        if (z5 || (z2 && z6)) {
            arrayList.add(str.substring(i6, i5));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String chomp(String str, String str2) {
        return (isEmpty(str) || str2 == null || !str.endsWith(str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static int indexOfAny(String str, String[] strArr) {
        int iIndexOf;
        if (str == null || strArr == null) {
            return -1;
        }
        int i2 = Integer.MAX_VALUE;
        for (String str2 : strArr) {
            if (str2 != null && (iIndexOf = str.indexOf(str2)) != -1 && iIndexOf < i2) {
                i2 = iIndexOf;
            }
        }
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        return i2;
    }

    public static String join(Object[] objArr, String str) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, str, 0, objArr.length);
    }

    public static String join(Object[] objArr, String str, int i2, int i3) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        int i4 = i3 - i2;
        if (i4 <= 0) {
            return "";
        }
        Object obj = objArr[i2];
        StrBuilder strBuilder = new StrBuilder(i4 * ((obj == null ? 16 : obj.toString().length()) + str.length()));
        for (int i5 = i2; i5 < i3; i5++) {
            if (i5 > i2) {
                strBuilder.append(str);
            }
            Object obj2 = objArr[i5];
            if (obj2 != null) {
                strBuilder.append(obj2);
            }
        }
        return strBuilder.toString();
    }

    public static String repeat(String str, String str2, int i2) {
        if (str != null && str2 != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str);
            stringBuffer.append(str2);
            return removeEnd(repeat(stringBuffer.toString(), i2), str2);
        }
        return repeat(str, i2);
    }

    public static String join(Iterator it, char c2) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return ObjectUtils.toString(next);
        }
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            strBuilder.append(c2);
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String join(Iterator it, String str) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return ObjectUtils.toString(next);
        }
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                strBuilder.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String join(Collection collection, char c2) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), c2);
    }

    public static String join(Collection collection, String str) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), str);
    }
}
