package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = 64;

    private enum LexicographicalComparator implements Comparator<byte[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "SignedBytes.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            int iMin = Math.min(bArr.length, bArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = Byte.compare(bArr[i2], bArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return bArr.length - bArr2.length;
        }
    }

    private SignedBytes() {
    }

    public static byte checkedCast(long j2) {
        byte b2 = (byte) j2;
        Preconditions.checkArgument(((long) b2) == j2, "Out of range: %s", j2);
        return b2;
    }

    public static int compare(byte b2, byte b3) {
        return Byte.compare(b2, b3);
    }

    public static String join(String str, byte... bArr) {
        Preconditions.checkNotNull(str);
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 5);
        sb.append((int) bArr[0]);
        for (int i2 = 1; i2 < bArr.length; i2++) {
            sb.append(str);
            sb.append((int) bArr[i2]);
        }
        return sb.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static byte max(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        byte b2 = bArr[0];
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    public static byte min(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        byte b2 = bArr[0];
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    public static byte saturatedCast(long j2) {
        if (j2 > 127) {
            return Byte.MAX_VALUE;
        }
        if (j2 < -128) {
            return Byte.MIN_VALUE;
        }
        return (byte) j2;
    }

    public static void sortDescending(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        sortDescending(bArr, 0, bArr.length);
    }

    public static void sortDescending(byte[] bArr, int i2, int i3) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkPositionIndexes(i2, i3, bArr.length);
        Arrays.sort(bArr, i2, i3);
        Bytes.reverse(bArr, i2, i3);
    }
}
