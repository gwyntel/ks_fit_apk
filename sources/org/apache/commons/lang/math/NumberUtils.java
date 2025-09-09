package org.apache.commons.lang.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;

/* loaded from: classes5.dex */
public class NumberUtils {
    public static final Long LONG_ZERO = new Long(0);
    public static final Long LONG_ONE = new Long(1);
    public static final Long LONG_MINUS_ONE = new Long(-1);
    public static final Integer INTEGER_ZERO = new Integer(0);
    public static final Integer INTEGER_ONE = new Integer(1);
    public static final Integer INTEGER_MINUS_ONE = new Integer(-1);
    public static final Short SHORT_ZERO = new Short((short) 0);
    public static final Short SHORT_ONE = new Short((short) 1);
    public static final Short SHORT_MINUS_ONE = new Short((short) -1);
    public static final Byte BYTE_ZERO = new Byte((byte) 0);
    public static final Byte BYTE_ONE = new Byte((byte) 1);
    public static final Byte BYTE_MINUS_ONE = new Byte((byte) -1);
    public static final Double DOUBLE_ZERO = new Double(0.0d);
    public static final Double DOUBLE_ONE = new Double(1.0d);
    public static final Double DOUBLE_MINUS_ONE = new Double(-1.0d);
    public static final Float FLOAT_ZERO = new Float(0.0f);
    public static final Float FLOAT_ONE = new Float(1.0f);
    public static final Float FLOAT_MINUS_ONE = new Float(-1.0f);

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
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(str);
    }

    public static BigInteger createBigInteger(String str) {
        if (str == null) {
            return null;
        }
        return new BigInteger(str);
    }

    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static Float createFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.valueOf(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ec, code lost:
    
        if (r1 == 'l') goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:136:0x013e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0133 A[Catch: NumberFormatException -> 0x013e, TRY_LEAVE, TryCatch #2 {NumberFormatException -> 0x013e, blocks: (B:73:0x0129, B:75:0x0133), top: B:138:0x0129 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0148 A[Catch: NumberFormatException -> 0x0154, TRY_LEAVE, TryCatch #1 {NumberFormatException -> 0x0154, blocks: (B:80:0x013e, B:82:0x0148), top: B:136:0x013e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Number createNumber(java.lang.String r15) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 478
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.NumberUtils.createNumber(java.lang.String):java.lang.Number");
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
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0065, code lost:
    
        if (r3 >= r0.length) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0067, code lost:
    
        r0 = r0[r3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0069, code lost:
    
        if (r0 < '0') goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006b, code lost:
    
        if (r0 > '9') goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006d, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x006e, code lost:
    
        if (r0 == 'e') goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0070, code lost:
    
        if (r0 != 'E') goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0073, code lost:
    
        if (r0 != '.') goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0075, code lost:
    
        if (r14 != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0077, code lost:
    
        if (r13 == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x007a, code lost:
    
        return r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x007b, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x007c, code lost:
    
        if (r11 != false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0080, code lost:
    
        if (r0 == 'd') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0084, code lost:
    
        if (r0 == 'D') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0086, code lost:
    
        if (r0 == 'f') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x008a, code lost:
    
        if (r0 != 'F') goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x008c, code lost:
    
        return r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x008f, code lost:
    
        if (r0 == 'l') goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0093, code lost:
    
        if (r0 != 'L') goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0096, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0097, code lost:
    
        if (r12 == false) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0099, code lost:
    
        if (r13 != false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x009c, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x009d, code lost:
    
        if (r11 != false) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x009f, code lost:
    
        if (r12 == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00a2, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00b9, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNumber(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.NumberUtils.isNumber(java.lang.String):boolean");
    }

    public static byte max(byte b2, byte b3, byte b4) {
        if (b3 > b2) {
            b2 = b3;
        }
        return b4 > b2 ? b4 : b2;
    }

    public static byte min(byte b2, byte b3, byte b4) {
        if (b3 < b2) {
            b2 = b3;
        }
        return b4 < b2 ? b4 : b2;
    }

    public static int stringToInt(String str) {
        return toInt(str);
    }

    public static byte toByte(String str) {
        return toByte(str, (byte) 0);
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0d);
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static short toShort(String str) {
        return toShort(str, (short) 0);
    }

    public static int max(int i2, int i3, int i4) {
        if (i3 > i2) {
            i2 = i3;
        }
        return i4 > i2 ? i4 : i2;
    }

    public static int min(int i2, int i3, int i4) {
        if (i3 < i2) {
            i2 = i3;
        }
        return i4 < i2 ? i4 : i2;
    }

    public static int stringToInt(String str, int i2) {
        return toInt(str, i2);
    }

    public static byte toByte(String str, byte b2) {
        if (str == null) {
            return b2;
        }
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException unused) {
            return b2;
        }
    }

    public static double toDouble(String str, double d2) {
        if (str == null) {
            return d2;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    public static float toFloat(String str, float f2) {
        if (str == null) {
            return f2;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f2;
        }
    }

    public static int toInt(String str, int i2) {
        if (str == null) {
            return i2;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    public static long toLong(String str, long j2) {
        if (str == null) {
            return j2;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j2;
        }
    }

    public static short toShort(String str, short s2) {
        if (str == null) {
            return s2;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException unused) {
            return s2;
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

    public static long max(long j2, long j3, long j4) {
        if (j3 > j2) {
            j2 = j3;
        }
        return j4 > j2 ? j4 : j2;
    }

    public static long min(long j2, long j3, long j4) {
        if (j3 < j2) {
            j2 = j3;
        }
        return j4 < j2 ? j4 : j2;
    }

    public static short max(short s2, short s3, short s4) {
        if (s3 > s2) {
            s2 = s3;
        }
        return s4 > s2 ? s4 : s2;
    }

    public static short min(short s2, short s3, short s4) {
        if (s3 < s2) {
            s2 = s3;
        }
        return s4 < s2 ? s4 : s2;
    }

    public static long max(long[] jArr) {
        if (jArr != null) {
            if (jArr.length != 0) {
                long j2 = jArr[0];
                for (int i2 = 1; i2 < jArr.length; i2++) {
                    long j3 = jArr[i2];
                    if (j3 > j2) {
                        j2 = j3;
                    }
                }
                return j2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static long min(long[] jArr) {
        if (jArr != null) {
            if (jArr.length != 0) {
                long j2 = jArr[0];
                for (int i2 = 1; i2 < jArr.length; i2++) {
                    long j3 = jArr[i2];
                    if (j3 < j2) {
                        j2 = j3;
                    }
                }
                return j2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static int max(int[] iArr) {
        if (iArr != null) {
            if (iArr.length != 0) {
                int i2 = iArr[0];
                for (int i3 = 1; i3 < iArr.length; i3++) {
                    int i4 = iArr[i3];
                    if (i4 > i2) {
                        i2 = i4;
                    }
                }
                return i2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static int min(int[] iArr) {
        if (iArr != null) {
            if (iArr.length != 0) {
                int i2 = iArr[0];
                for (int i3 = 1; i3 < iArr.length; i3++) {
                    int i4 = iArr[i3];
                    if (i4 < i2) {
                        i2 = i4;
                    }
                }
                return i2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static short max(short[] sArr) {
        if (sArr != null) {
            if (sArr.length != 0) {
                short s2 = sArr[0];
                for (int i2 = 1; i2 < sArr.length; i2++) {
                    short s3 = sArr[i2];
                    if (s3 > s2) {
                        s2 = s3;
                    }
                }
                return s2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static short min(short[] sArr) {
        if (sArr != null) {
            if (sArr.length != 0) {
                short s2 = sArr[0];
                for (int i2 = 1; i2 < sArr.length; i2++) {
                    short s3 = sArr[i2];
                    if (s3 < s2) {
                        s2 = s3;
                    }
                }
                return s2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static byte max(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length != 0) {
                byte b2 = bArr[0];
                for (int i2 = 1; i2 < bArr.length; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 > b2) {
                        b2 = b3;
                    }
                }
                return b2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static byte min(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length != 0) {
                byte b2 = bArr[0];
                for (int i2 = 1; i2 < bArr.length; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 < b2) {
                        b2 = b3;
                    }
                }
                return b2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double max(double[] dArr) {
        if (dArr != null) {
            if (dArr.length != 0) {
                double d2 = dArr[0];
                for (int i2 = 1; i2 < dArr.length; i2++) {
                    if (Double.isNaN(dArr[i2])) {
                        return Double.NaN;
                    }
                    double d3 = dArr[i2];
                    if (d3 > d2) {
                        d2 = d3;
                    }
                }
                return d2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double min(double[] dArr) {
        if (dArr != null) {
            if (dArr.length != 0) {
                double d2 = dArr[0];
                for (int i2 = 1; i2 < dArr.length; i2++) {
                    if (Double.isNaN(dArr[i2])) {
                        return Double.NaN;
                    }
                    double d3 = dArr[i2];
                    if (d3 < d2) {
                        d2 = d3;
                    }
                }
                return d2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static float max(float[] fArr) {
        if (fArr != null) {
            if (fArr.length != 0) {
                float f2 = fArr[0];
                for (int i2 = 1; i2 < fArr.length; i2++) {
                    if (Float.isNaN(fArr[i2])) {
                        return Float.NaN;
                    }
                    float f3 = fArr[i2];
                    if (f3 > f2) {
                        f2 = f3;
                    }
                }
                return f2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static float min(float[] fArr) {
        if (fArr != null) {
            if (fArr.length != 0) {
                float f2 = fArr[0];
                for (int i2 = 1; i2 < fArr.length; i2++) {
                    if (Float.isNaN(fArr[i2])) {
                        return Float.NaN;
                    }
                    float f3 = fArr[i2];
                    if (f3 < f2) {
                        f2 = f3;
                    }
                }
                return f2;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double max(double d2, double d3, double d4) {
        return Math.max(Math.max(d2, d3), d4);
    }

    public static double min(double d2, double d3, double d4) {
        return Math.min(Math.min(d2, d3), d4);
    }

    public static float max(float f2, float f3, float f4) {
        return Math.max(Math.max(f2, f3), f4);
    }

    public static float min(float f2, float f3, float f4) {
        return Math.min(Math.min(f2, f3), f4);
    }
}
