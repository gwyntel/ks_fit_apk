package com.huawei.secure.android.common.anonymization;

/* loaded from: classes4.dex */
public class a {
    public static String[] a(String[] strArr, String[] strArr2) {
        if (strArr.length <= 0) {
            return strArr2.length <= 0 ? new String[0] : strArr2;
        }
        if (strArr2.length <= 0) {
            return strArr;
        }
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    public static String[] b(String str, String[] strArr) {
        return a(new String[]{str}, strArr);
    }

    public static String[] b(String str, char c2) {
        if (str == null) {
            return new String[0];
        }
        if (str.length() <= 0) {
            return new String[]{str};
        }
        int i2 = 1;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) == c2) {
                i2++;
            }
        }
        if (i2 <= 1) {
            return new String[]{str};
        }
        String[] strArr = new String[i2];
        StringBuilder sb = new StringBuilder(str.length());
        int i4 = 0;
        for (int i5 = 0; i5 < str.length() && i4 < i2; i5++) {
            char cCharAt = str.charAt(i5);
            if (cCharAt == c2) {
                strArr[i4] = sb.toString();
                sb.setLength(0);
                i4++;
            } else {
                sb.append(cCharAt);
            }
        }
        strArr[i4] = sb.toString();
        return strArr;
    }

    public static String[] a(String[] strArr, String str) {
        return a(strArr, new String[]{str});
    }

    public static String[] a(String str, int i2, int i3) {
        String[] strArrA = a(str, i2);
        return b(a(strArrA, 0), a(a(strArrA, 1), i3 - i2));
    }

    public static String[] a(String str, int i2, int i3, int i4) {
        String[] strArrA = a(str, i2);
        return b(a(strArrA, 0), a(a(strArrA, 1), i3 - i2, i4 - i2));
    }

    public static String[] a(String str, int... iArr) {
        if (str == null) {
            return new String[]{""};
        }
        if (str.length() > 1 && iArr.length > 0) {
            if (iArr.length <= 1) {
                return a(str, iArr[0]);
            }
            int i2 = iArr[0];
            int length = iArr.length - 1;
            int[] iArr2 = new int[length];
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3 + 1;
                iArr2[i3] = iArr[i4] - i2;
                i3 = i4;
            }
            String[] strArrA = a(str, i2);
            return b(a(strArrA, 0), a(a(strArrA, 1), iArr2));
        }
        return new String[]{str};
    }

    public static String[] a(String str, int i2) {
        if (str == null) {
            return new String[]{"", ""};
        }
        if (i2 >= 0 && i2 <= str.length()) {
            return new String[]{str.substring(0, i2), str.substring(i2)};
        }
        return new String[]{str, ""};
    }

    public static String a(String str, String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i2 = 1; i2 < strArr.length; i2++) {
            if (strArr[i2] != null) {
                sb.append(str);
                sb.append(strArr[i2]);
            }
        }
        return sb.toString();
    }

    public static String a(String str, String str2, String str3) {
        if (str == null || str.length() <= 0 || str2.length() <= 0 || str3.length() <= 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        char[] cArr = new char[str.length()];
        char cCharAt = str3.charAt(str3.length() - 1);
        for (int i2 = 0; i2 < charArray.length; i2++) {
            char c2 = charArray[i2];
            int iLastIndexOf = str2.lastIndexOf(c2);
            if (iLastIndexOf < 0) {
                cArr[i2] = c2;
            } else {
                cArr[i2] = iLastIndexOf >= str3.length() ? cCharAt : str3.charAt(iLastIndexOf);
            }
        }
        return new String(cArr);
    }

    public static String a(String[] strArr, int i2) {
        return (strArr == null || strArr.length <= 0 || i2 < 0 || i2 >= strArr.length) ? "" : strArr[i2];
    }

    public static String a(String str, char c2) {
        if (str == null || str.length() <= 0) {
            return "";
        }
        int length = str.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = c2;
        }
        return new String(cArr);
    }

    public static int a(String str, char c2, int i2) {
        int length = str.length() - 1;
        while (length >= 0 && (str.charAt(length) != c2 || i2 - 1 > 0)) {
            length--;
        }
        return length;
    }
}
