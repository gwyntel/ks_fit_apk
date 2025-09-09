package org.apache.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public final class NumberUtils {
    public static int compare(double d2, double d3) {
        if (d2 < d3) {
            return -1;
        }
        if (d2 > d3) {
            return 1;
        }
        long jDoubleToLongBits = Double.doubleToLongBits(d2);
        long jDoubleToLongBits2 = Double.doubleToLongBits(d3);
        if (jDoubleToLongBits == jDoubleToLongBits2) {
            return 0;
        }
        return jDoubleToLongBits < jDoubleToLongBits2 ? -1 : 1;
    }

    public static BigDecimal createBigDecimal(String str) {
        return new BigDecimal(str);
    }

    public static BigInteger createBigInteger(String str) {
        return new BigInteger(str);
    }

    public static Double createDouble(String str) {
        return Double.valueOf(str);
    }

    public static Float createFloat(String str) {
        return Float.valueOf(str);
    }

    public static Integer createInteger(String str) {
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        return Long.valueOf(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ef, code lost:
    
        if (r1 == 'l') goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0141 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0136 A[Catch: NumberFormatException -> 0x0141, TRY_LEAVE, TryCatch #1 {NumberFormatException -> 0x0141, blocks: (B:73:0x012c, B:75:0x0136), top: B:135:0x012c }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x014b A[Catch: NumberFormatException -> 0x0157, TRY_LEAVE, TryCatch #0 {NumberFormatException -> 0x0157, blocks: (B:80:0x0141, B:82:0x014b), top: B:133:0x0141 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Number createNumber(java.lang.String r14) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.NumberUtils.createNumber(java.lang.String):java.lang.Number");
    }

    private static boolean isAllZeros(String str) {
        if (str == null) {
            return true;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) != '0') {
                return false;
            }
        }
        return str.length() > 0;
    }

    public static boolean isDigits(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x00ac, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNumber(java.lang.String r16) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.NumberUtils.isNumber(java.lang.String):boolean");
    }

    public static int maximum(int i2, int i3, int i4) {
        if (i3 > i2) {
            i2 = i3;
        }
        return i4 > i2 ? i4 : i2;
    }

    public static int minimum(int i2, int i3, int i4) {
        if (i3 < i2) {
            i2 = i3;
        }
        return i4 < i2 ? i4 : i2;
    }

    public static int stringToInt(String str) {
        return stringToInt(str, 0);
    }

    public static long maximum(long j2, long j3, long j4) {
        if (j3 > j2) {
            j2 = j3;
        }
        return j4 > j2 ? j4 : j2;
    }

    public static long minimum(long j2, long j3, long j4) {
        if (j3 < j2) {
            j2 = j3;
        }
        return j4 < j2 ? j4 : j2;
    }

    public static int stringToInt(String str, int i2) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    public static int compare(float f2, float f3) {
        if (f2 < f3) {
            return -1;
        }
        if (f2 > f3) {
            return 1;
        }
        int iFloatToIntBits = Float.floatToIntBits(f2);
        int iFloatToIntBits2 = Float.floatToIntBits(f3);
        if (iFloatToIntBits == iFloatToIntBits2) {
            return 0;
        }
        return iFloatToIntBits < iFloatToIntBits2 ? -1 : 1;
    }
}
