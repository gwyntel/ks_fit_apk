package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;

    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(long[] jArr, long[] jArr2) {
            int iMin = Math.min(jArr.length, jArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                long j2 = jArr[i2];
                long j3 = jArr2[i2];
                if (j2 != j3) {
                    return UnsignedLongs.compare(j2, j3);
                }
            }
            return jArr.length - jArr2.length;
        }
    }

    private static final class ParseOverflowDetection {

        /* renamed from: a, reason: collision with root package name */
        static final long[] f14719a = new long[37];

        /* renamed from: b, reason: collision with root package name */
        static final int[] f14720b = new int[37];

        /* renamed from: c, reason: collision with root package name */
        static final int[] f14721c = new int[37];

        static {
            BigInteger bigInteger = new BigInteger("10000000000000000", 16);
            for (int i2 = 2; i2 <= 36; i2++) {
                long j2 = i2;
                f14719a[i2] = UnsignedLongs.divide(-1L, j2);
                f14720b[i2] = (int) UnsignedLongs.remainder(-1L, j2);
                f14721c[i2] = bigInteger.toString(i2).length() - 1;
            }
        }

        private ParseOverflowDetection() {
        }

        static boolean a(long j2, int i2, int i3) {
            if (j2 < 0) {
                return true;
            }
            long j3 = f14719a[i3];
            if (j2 < j3) {
                return false;
            }
            return j2 > j3 || i2 > f14720b[i3];
        }
    }

    private UnsignedLongs() {
    }

    public static int compare(long j2, long j3) {
        return Longs.compare(flip(j2), flip(j3));
    }

    @CanIgnoreReturnValue
    public static long decode(String str) {
        ParseRequest parseRequestA = ParseRequest.a(str);
        try {
            return parseUnsignedLong(parseRequestA.f14715a, parseRequestA.f14716b);
        } catch (NumberFormatException e2) {
            NumberFormatException numberFormatException = new NumberFormatException("Error parsing value: " + str);
            numberFormatException.initCause(e2);
            throw numberFormatException;
        }
    }

    public static long divide(long j2, long j3) {
        if (j3 < 0) {
            return compare(j2, j3) < 0 ? 0L : 1L;
        }
        if (j2 >= 0) {
            return j2 / j3;
        }
        long j4 = ((j2 >>> 1) / j3) << 1;
        return j4 + (compare(j2 - (j4 * j3), j3) < 0 ? 0 : 1);
    }

    private static long flip(long j2) {
        return j2 ^ Long.MIN_VALUE;
    }

    public static String join(String str, long... jArr) {
        Preconditions.checkNotNull(str);
        if (jArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(jArr.length * 5);
        sb.append(toString(jArr[0]));
        for (int i2 = 1; i2 < jArr.length; i2++) {
            sb.append(str);
            sb.append(toString(jArr[i2]));
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long max(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long jFlip = flip(jArr[0]);
        for (int i2 = 1; i2 < jArr.length; i2++) {
            long jFlip2 = flip(jArr[i2]);
            if (jFlip2 > jFlip) {
                jFlip = jFlip2;
            }
        }
        return flip(jFlip);
    }

    public static long min(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long jFlip = flip(jArr[0]);
        for (int i2 = 1; i2 < jArr.length; i2++) {
            long jFlip2 = flip(jArr[i2]);
            if (jFlip2 < jFlip) {
                jFlip = jFlip2;
            }
        }
        return flip(jFlip);
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str) {
        return parseUnsignedLong(str, 10);
    }

    public static long remainder(long j2, long j3) {
        if (j3 < 0) {
            return compare(j2, j3) < 0 ? j2 : j2 - j3;
        }
        if (j2 >= 0) {
            return j2 % j3;
        }
        long j4 = j2 - ((((j2 >>> 1) / j3) << 1) * j3);
        if (compare(j4, j3) < 0) {
            j3 = 0;
        }
        return j4 - j3;
    }

    public static void sort(long[] jArr) {
        Preconditions.checkNotNull(jArr);
        sort(jArr, 0, jArr.length);
    }

    public static void sortDescending(long[] jArr) {
        Preconditions.checkNotNull(jArr);
        sortDescending(jArr, 0, jArr.length);
    }

    public static String toString(long j2) {
        return toString(j2, 10);
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str, int i2) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        if (i2 < 2 || i2 > 36) {
            throw new NumberFormatException("illegal radix: " + i2);
        }
        int i3 = ParseOverflowDetection.f14721c[i2] - 1;
        long j2 = 0;
        for (int i4 = 0; i4 < str.length(); i4++) {
            int iDigit = Character.digit(str.charAt(i4), i2);
            if (iDigit == -1) {
                throw new NumberFormatException(str);
            }
            if (i4 > i3 && ParseOverflowDetection.a(j2, iDigit, i2)) {
                throw new NumberFormatException("Too large for unsigned long: " + str);
            }
            j2 = (j2 * i2) + iDigit;
        }
        return j2;
    }

    public static String toString(long j2, int i2) {
        Preconditions.checkArgument(i2 >= 2 && i2 <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i2);
        if (j2 == 0) {
            return "0";
        }
        if (j2 > 0) {
            return Long.toString(j2, i2);
        }
        int i3 = 64;
        char[] cArr = new char[64];
        int i4 = i2 - 1;
        if ((i2 & i4) == 0) {
            int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
            do {
                i3--;
                cArr[i3] = Character.forDigit(((int) j2) & i4, i2);
                j2 >>>= iNumberOfTrailingZeros;
            } while (j2 != 0);
        } else {
            long jDivide = (i2 & 1) == 0 ? (j2 >>> 1) / (i2 >>> 1) : divide(j2, i2);
            long j3 = i2;
            int i5 = 63;
            cArr[63] = Character.forDigit((int) (j2 - (jDivide * j3)), i2);
            while (jDivide > 0) {
                i5--;
                cArr[i5] = Character.forDigit((int) (jDivide % j3), i2);
                jDivide /= j3;
            }
            i3 = i5;
        }
        return new String(cArr, i3, 64 - i3);
    }

    public static void sort(long[] jArr, int i2, int i3) {
        Preconditions.checkNotNull(jArr);
        Preconditions.checkPositionIndexes(i2, i3, jArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            jArr[i4] = flip(jArr[i4]);
        }
        Arrays.sort(jArr, i2, i3);
        while (i2 < i3) {
            jArr[i2] = flip(jArr[i2]);
            i2++;
        }
    }

    public static void sortDescending(long[] jArr, int i2, int i3) {
        Preconditions.checkNotNull(jArr);
        Preconditions.checkPositionIndexes(i2, i3, jArr.length);
        for (int i4 = i2; i4 < i3; i4++) {
            jArr[i4] = Long.MAX_VALUE ^ jArr[i4];
        }
        Arrays.sort(jArr, i2, i3);
        while (i2 < i3) {
            jArr[i2] = jArr[i2] ^ Long.MAX_VALUE;
            i2++;
        }
    }
}
