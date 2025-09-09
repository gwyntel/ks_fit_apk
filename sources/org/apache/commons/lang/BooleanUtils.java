package org.apache.commons.lang;

import anetwork.channel.util.RequestConstant;

/* loaded from: classes5.dex */
public class BooleanUtils {
    public static boolean isFalse(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return !bool.booleanValue();
    }

    public static boolean isNotFalse(Boolean bool) {
        return !isFalse(bool);
    }

    public static boolean isNotTrue(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isTrue(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static Boolean negate(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean toBoolean(int i2) {
        return i2 != 0;
    }

    public static boolean toBooleanDefaultIfNull(Boolean bool, boolean z2) {
        return bool == null ? z2 : bool.booleanValue();
    }

    public static Boolean toBooleanObject(boolean z2) {
        return z2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static int toInteger(boolean z2) {
        return z2 ? 1 : 0;
    }

    public static Integer toIntegerObject(boolean z2, Integer num, Integer num2) {
        return z2 ? num : num2;
    }

    public static String toString(boolean z2, String str, String str2) {
        return z2 ? str : str2;
    }

    public static String toStringOnOff(Boolean bool) {
        return toString(bool, "on", "off", null);
    }

    public static String toStringTrueFalse(Boolean bool) {
        return toString(bool, "true", RequestConstant.FALSE, null);
    }

    public static String toStringYesNo(Boolean bool) {
        return toString(bool, "yes", "no", null);
    }

    public static boolean xor(boolean[] zArr) {
        if (zArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (zArr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        int i2 = 0;
        for (boolean z2 : zArr) {
            if (z2) {
                if (i2 >= 1) {
                    return false;
                }
                i2++;
            }
        }
        return i2 == 1;
    }

    public static boolean toBoolean(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static Boolean toBooleanObject(int i2) {
        return i2 == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static int toInteger(boolean z2, int i2, int i3) {
        return z2 ? i2 : i3;
    }

    public static Integer toIntegerObject(boolean z2) {
        return z2 ? org.apache.commons.lang.math.NumberUtils.INTEGER_ONE : org.apache.commons.lang.math.NumberUtils.INTEGER_ZERO;
    }

    public static String toString(Boolean bool, String str, String str2, String str3) {
        return bool == null ? str3 : bool.booleanValue() ? str : str2;
    }

    public static String toStringOnOff(boolean z2) {
        return toString(z2, "on", "off");
    }

    public static String toStringTrueFalse(boolean z2) {
        return toString(z2, "true", RequestConstant.FALSE);
    }

    public static String toStringYesNo(boolean z2) {
        return toString(z2, "yes", "no");
    }

    public static boolean toBoolean(int i2, int i3, int i4) {
        if (i2 == i3) {
            return true;
        }
        if (i2 == i4) {
            return false;
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }

    public static Boolean toBooleanObject(Integer num) {
        if (num == null) {
            return null;
        }
        return num.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static int toInteger(Boolean bool, int i2, int i3, int i4) {
        return bool == null ? i4 : bool.booleanValue() ? i2 : i3;
    }

    public static Integer toIntegerObject(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool.booleanValue() ? org.apache.commons.lang.math.NumberUtils.INTEGER_ONE : org.apache.commons.lang.math.NumberUtils.INTEGER_ZERO;
    }

    public static boolean toBoolean(Integer num, Integer num2, Integer num3) {
        if (num == null) {
            if (num2 == null) {
                return true;
            }
            if (num3 == null) {
                return false;
            }
        } else {
            if (num.equals(num2)) {
                return true;
            }
            if (num.equals(num3)) {
                return false;
            }
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }

    public static Boolean toBooleanObject(int i2, int i3, int i4, int i5) {
        if (i2 == i3) {
            return Boolean.TRUE;
        }
        if (i2 == i4) {
            return Boolean.FALSE;
        }
        if (i2 == i5) {
            return null;
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }

    public static Integer toIntegerObject(Boolean bool, Integer num, Integer num2, Integer num3) {
        return bool == null ? num3 : bool.booleanValue() ? num : num2;
    }

    public static Boolean xor(Boolean[] boolArr) {
        if (boolArr != null) {
            if (boolArr.length != 0) {
                try {
                    return xor(ArrayUtils.toPrimitive(boolArr)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (NullPointerException unused) {
                    throw new IllegalArgumentException("The array must not contain any null elements");
                }
            }
            throw new IllegalArgumentException("Array is empty");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static boolean toBoolean(String str) {
        return toBoolean(toBooleanObject(str));
    }

    public static Boolean toBooleanObject(Integer num, Integer num2, Integer num3, Integer num4) {
        if (num == null) {
            if (num2 == null) {
                return Boolean.TRUE;
            }
            if (num3 == null) {
                return Boolean.FALSE;
            }
            if (num4 == null) {
                return null;
            }
        } else {
            if (num.equals(num2)) {
                return Boolean.TRUE;
            }
            if (num.equals(num3)) {
                return Boolean.FALSE;
            }
            if (num.equals(num4)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }

    public static boolean toBoolean(String str, String str2, String str3) {
        if (str == null) {
            if (str2 == null) {
                return true;
            }
            if (str3 == null) {
                return false;
            }
        } else {
            if (str.equals(str2)) {
                return true;
            }
            if (str.equals(str3)) {
                return false;
            }
        }
        throw new IllegalArgumentException("The String did not match either specified value");
    }

    public static Boolean toBooleanObject(String str) {
        if (str == "true") {
            return Boolean.TRUE;
        }
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 1) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'y' || cCharAt == 'Y' || cCharAt == 't' || cCharAt == 'T') {
                return Boolean.TRUE;
            }
            if (cCharAt == 'n' || cCharAt == 'N' || cCharAt == 'f' || cCharAt == 'F') {
                return Boolean.FALSE;
            }
            return null;
        }
        if (length == 2) {
            char cCharAt2 = str.charAt(0);
            char cCharAt3 = str.charAt(1);
            if ((cCharAt2 == 'o' || cCharAt2 == 'O') && (cCharAt3 == 'n' || cCharAt3 == 'N')) {
                return Boolean.TRUE;
            }
            if (cCharAt2 != 'n' && cCharAt2 != 'N') {
                return null;
            }
            if (cCharAt3 == 'o' || cCharAt3 == 'O') {
                return Boolean.FALSE;
            }
            return null;
        }
        if (length == 3) {
            char cCharAt4 = str.charAt(0);
            char cCharAt5 = str.charAt(1);
            char cCharAt6 = str.charAt(2);
            if ((cCharAt4 == 'y' || cCharAt4 == 'Y') && ((cCharAt5 == 'e' || cCharAt5 == 'E') && (cCharAt6 == 's' || cCharAt6 == 'S'))) {
                return Boolean.TRUE;
            }
            if (cCharAt4 != 'o' && cCharAt4 != 'O') {
                return null;
            }
            if (cCharAt5 != 'f' && cCharAt5 != 'F') {
                return null;
            }
            if (cCharAt6 == 'f' || cCharAt6 == 'F') {
                return Boolean.FALSE;
            }
            return null;
        }
        if (length == 4) {
            char cCharAt7 = str.charAt(0);
            char cCharAt8 = str.charAt(1);
            char cCharAt9 = str.charAt(2);
            char cCharAt10 = str.charAt(3);
            if (cCharAt7 != 't' && cCharAt7 != 'T') {
                return null;
            }
            if (cCharAt8 != 'r' && cCharAt8 != 'R') {
                return null;
            }
            if (cCharAt9 != 'u' && cCharAt9 != 'U') {
                return null;
            }
            if (cCharAt10 == 'e' || cCharAt10 == 'E') {
                return Boolean.TRUE;
            }
            return null;
        }
        if (length != 5) {
            return null;
        }
        char cCharAt11 = str.charAt(0);
        char cCharAt12 = str.charAt(1);
        char cCharAt13 = str.charAt(2);
        char cCharAt14 = str.charAt(3);
        char cCharAt15 = str.charAt(4);
        if (cCharAt11 != 'f' && cCharAt11 != 'F') {
            return null;
        }
        if (cCharAt12 != 'a' && cCharAt12 != 'A') {
            return null;
        }
        if (cCharAt13 != 'l' && cCharAt13 != 'L') {
            return null;
        }
        if (cCharAt14 != 's' && cCharAt14 != 'S') {
            return null;
        }
        if (cCharAt15 == 'e' || cCharAt15 == 'E') {
            return Boolean.FALSE;
        }
        return null;
    }

    public static Boolean toBooleanObject(String str, String str2, String str3, String str4) {
        if (str == null) {
            if (str2 == null) {
                return Boolean.TRUE;
            }
            if (str3 == null) {
                return Boolean.FALSE;
            }
            if (str4 == null) {
                return null;
            }
        } else {
            if (str.equals(str2)) {
                return Boolean.TRUE;
            }
            if (str.equals(str3)) {
                return Boolean.FALSE;
            }
            if (str.equals(str4)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The String did not match any specified value");
    }
}
