package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.IllegalFieldValueException;

/* loaded from: classes5.dex */
public class FieldUtils {
    private FieldUtils() {
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static int getWrappedValue(int i2, int i3, int i4, int i5) {
        return getWrappedValue(i2 + i3, i4, i5);
    }

    public static int safeAdd(int i2, int i3) {
        int i4 = i2 + i3;
        if ((i2 ^ i4) >= 0 || (i2 ^ i3) < 0) {
            return i4;
        }
        throw new ArithmeticException("The calculation caused an overflow: " + i2 + " + " + i3);
    }

    public static int safeMultiply(int i2, int i3) {
        long j2 = i2 * i3;
        if (j2 >= -2147483648L && j2 <= 2147483647L) {
            return (int) j2;
        }
        throw new ArithmeticException("Multiplication overflows an int: " + i2 + " * " + i3);
    }

    public static int safeMultiplyToInt(long j2, long j3) {
        return safeToInt(safeMultiply(j2, j3));
    }

    public static int safeNegate(int i2) {
        if (i2 != Integer.MIN_VALUE) {
            return -i2;
        }
        throw new ArithmeticException("Integer.MIN_VALUE cannot be negated");
    }

    public static long safeSubtract(long j2, long j3) {
        long j4 = j2 - j3;
        if ((j2 ^ j4) >= 0 || (j2 ^ j3) >= 0) {
            return j4;
        }
        throw new ArithmeticException("The calculation caused an overflow: " + j2 + " - " + j3);
    }

    public static int safeToInt(long j2) {
        if (-2147483648L <= j2 && j2 <= 2147483647L) {
            return (int) j2;
        }
        throw new ArithmeticException("Value cannot fit in an int: " + j2);
    }

    public static void verifyValueBounds(DateTimeField dateTimeField, int i2, int i3, int i4) {
        if (i2 < i3 || i2 > i4) {
            throw new IllegalFieldValueException(dateTimeField.getType(), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
        }
    }

    public static int getWrappedValue(int i2, int i3, int i4) {
        if (i3 >= i4) {
            throw new IllegalArgumentException("MIN > MAX");
        }
        int i5 = (i4 - i3) + 1;
        int i6 = i2 - i3;
        if (i6 >= 0) {
            return (i6 % i5) + i3;
        }
        int i7 = (-i6) % i5;
        return i7 == 0 ? i3 : (i5 - i7) + i3;
    }

    public static long safeAdd(long j2, long j3) {
        long j4 = j2 + j3;
        if ((j2 ^ j4) >= 0 || (j2 ^ j3) < 0) {
            return j4;
        }
        throw new ArithmeticException("The calculation caused an overflow: " + j2 + " + " + j3);
    }

    public static long safeMultiply(long j2, int i2) {
        if (i2 == -1) {
            if (j2 != Long.MIN_VALUE) {
                return -j2;
            }
            throw new ArithmeticException("Multiplication overflows a long: " + j2 + " * " + i2);
        }
        if (i2 == 0) {
            return 0L;
        }
        if (i2 == 1) {
            return j2;
        }
        long j3 = i2;
        long j4 = j2 * j3;
        if (j4 / j3 == j2) {
            return j4;
        }
        throw new ArithmeticException("Multiplication overflows a long: " + j2 + " * " + i2);
    }

    public static void verifyValueBounds(DateTimeFieldType dateTimeFieldType, int i2, int i3, int i4) {
        if (i2 < i3 || i2 > i4) {
            throw new IllegalFieldValueException(dateTimeFieldType, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
        }
    }

    public static void verifyValueBounds(String str, int i2, int i3, int i4) {
        if (i2 < i3 || i2 > i4) {
            throw new IllegalFieldValueException(str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
        }
    }

    public static long safeMultiply(long j2, long j3) {
        if (j3 == 1) {
            return j2;
        }
        if (j2 == 1) {
            return j3;
        }
        if (j2 == 0 || j3 == 0) {
            return 0L;
        }
        long j4 = j2 * j3;
        if (j4 / j3 == j2 && ((j2 != Long.MIN_VALUE || j3 != -1) && (j3 != Long.MIN_VALUE || j2 != -1))) {
            return j4;
        }
        throw new ArithmeticException("Multiplication overflows a long: " + j2 + " * " + j3);
    }
}
