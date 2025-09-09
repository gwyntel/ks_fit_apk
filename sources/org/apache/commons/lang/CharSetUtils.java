package org.apache.commons.lang;

import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class CharSetUtils {
    public static int count(String str, String str2) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
            return 0;
        }
        return count(str, new String[]{str2});
    }

    public static String delete(String str, String str2) {
        return (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) ? str : delete(str, new String[]{str2});
    }

    public static CharSet evaluateSet(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return new CharSet(strArr);
    }

    public static String keep(String str, String str2) {
        if (str == null) {
            return null;
        }
        return (str.length() == 0 || StringUtils.isEmpty(str2)) ? "" : keep(str, new String[]{str2});
    }

    private static String modify(String str, String[] strArr, boolean z2) {
        CharSet charSet = CharSet.getInstance(strArr);
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (charSet.contains(charArray[i2]) == z2) {
                strBuilder.append(charArray[i2]);
            }
        }
        return strBuilder.toString();
    }

    public static String squeeze(String str, String str2) {
        return (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) ? str : squeeze(str, new String[]{str2});
    }

    public static String translate(String str, String str2, String str3) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        char[] charArray2 = str3.toCharArray();
        int length = charArray.length;
        int length2 = str3.length() - 1;
        for (int i2 = 0; i2 < length; i2++) {
            int iIndexOf = str2.indexOf(charArray[i2]);
            if (iIndexOf != -1) {
                if (iIndexOf > length2) {
                    iIndexOf = length2;
                }
                strBuilder.append(charArray2[iIndexOf]);
            } else {
                strBuilder.append(charArray[i2]);
            }
        }
        return strBuilder.toString();
    }

    public static int count(String str, String[] strArr) {
        if (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
            return 0;
        }
        CharSet charSet = CharSet.getInstance(strArr);
        int i2 = 0;
        for (char c2 : str.toCharArray()) {
            if (charSet.contains(c2)) {
                i2++;
            }
        }
        return i2;
    }

    public static String delete(String str, String[] strArr) {
        return (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) ? str : modify(str, strArr, false);
    }

    public static String squeeze(String str, String[] strArr) {
        if (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
            return str;
        }
        CharSet charSet = CharSet.getInstance(strArr);
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        char c2 = ' ';
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (!charSet.contains(c3) || c3 != c2 || i2 == 0) {
                strBuilder.append(c3);
                c2 = c3;
            }
        }
        return strBuilder.toString();
    }

    public static String keep(String str, String[] strArr) {
        if (str == null) {
            return null;
        }
        if (str.length() != 0 && !ArrayUtils.isEmpty(strArr)) {
            return modify(str, strArr, true);
        }
        return "";
    }
}
