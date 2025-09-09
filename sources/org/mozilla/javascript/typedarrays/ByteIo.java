package org.mozilla.javascript.typedarrays;

import kotlin.UShort;

/* loaded from: classes5.dex */
public class ByteIo {
    private static short doReadInt16(byte[] bArr, int i2, boolean z2) {
        int i3;
        int i4;
        if (z2) {
            i3 = bArr[i2] & 255;
            i4 = (bArr[i2 + 1] & 255) << 8;
        } else {
            i3 = (bArr[i2] & 255) << 8;
            i4 = bArr[i2 + 1] & 255;
        }
        return (short) (i4 | i3);
    }

    private static void doWriteInt16(byte[] bArr, int i2, int i3, boolean z2) {
        if (z2) {
            bArr[i2] = (byte) (i3 & 255);
            bArr[i2 + 1] = (byte) ((i3 >>> 8) & 255);
        } else {
            bArr[i2] = (byte) ((i3 >>> 8) & 255);
            bArr[i2 + 1] = (byte) (i3 & 255);
        }
    }

    public static Object readFloat32(byte[] bArr, int i2, boolean z2) {
        return Float.valueOf(Float.intBitsToFloat((int) readUint32Primitive(bArr, i2, z2)));
    }

    public static Object readFloat64(byte[] bArr, int i2, boolean z2) {
        return Double.valueOf(Double.longBitsToDouble(readUint64Primitive(bArr, i2, z2)));
    }

    public static Object readInt16(byte[] bArr, int i2, boolean z2) {
        return Short.valueOf(doReadInt16(bArr, i2, z2));
    }

    public static Object readInt32(byte[] bArr, int i2, boolean z2) {
        if (z2) {
            return Integer.valueOf(((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16));
        }
        return Integer.valueOf((bArr[i2 + 3] & 255) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8));
    }

    public static Object readInt8(byte[] bArr, int i2) {
        return Byte.valueOf(bArr[i2]);
    }

    public static Object readUint16(byte[] bArr, int i2, boolean z2) {
        return Integer.valueOf(doReadInt16(bArr, i2, z2) & UShort.MAX_VALUE);
    }

    public static Object readUint32(byte[] bArr, int i2, boolean z2) {
        return Long.valueOf(readUint32Primitive(bArr, i2, z2));
    }

    public static long readUint32Primitive(byte[] bArr, int i2, boolean z2) {
        long j2;
        if (z2) {
            j2 = ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 2] & 255) << 16) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8);
        } else {
            j2 = (bArr[i2 + 3] & 255) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 2] & 255) << 8);
        }
        return j2 & 4294967295L;
    }

    public static long readUint64Primitive(byte[] bArr, int i2, boolean z2) {
        if (z2) {
            return ((bArr[i2 + 7] & 255) << 56) | ((bArr[i2 + 2] & 255) << 16) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }
        return (bArr[i2 + 7] & 255) | ((bArr[i2 + 1] & 255) << 48) | ((bArr[i2] & 255) << 56) | ((bArr[i2 + 2] & 255) << 40) | ((bArr[i2 + 3] & 255) << 32) | ((bArr[i2 + 4] & 255) << 24) | ((bArr[i2 + 5] & 255) << 16) | ((bArr[i2 + 6] & 255) << 8);
    }

    public static Object readUint8(byte[] bArr, int i2) {
        return Integer.valueOf(bArr[i2] & 255);
    }

    public static void writeFloat32(byte[] bArr, int i2, double d2, boolean z2) {
        writeUint32(bArr, i2, Float.floatToIntBits((float) d2), z2);
    }

    public static void writeFloat64(byte[] bArr, int i2, double d2, boolean z2) {
        writeUint64(bArr, i2, Double.doubleToLongBits(d2), z2);
    }

    public static void writeInt16(byte[] bArr, int i2, int i3, boolean z2) {
        doWriteInt16(bArr, i2, i3, z2);
    }

    public static void writeInt32(byte[] bArr, int i2, int i3, boolean z2) {
        if (z2) {
            bArr[i2] = (byte) (i3 & 255);
            bArr[i2 + 1] = (byte) ((i3 >>> 8) & 255);
            bArr[i2 + 2] = (byte) ((i3 >>> 16) & 255);
            bArr[i2 + 3] = (byte) ((i3 >>> 24) & 255);
            return;
        }
        bArr[i2] = (byte) ((i3 >>> 24) & 255);
        bArr[i2 + 1] = (byte) ((i3 >>> 16) & 255);
        bArr[i2 + 2] = (byte) ((i3 >>> 8) & 255);
        bArr[i2 + 3] = (byte) (i3 & 255);
    }

    public static void writeInt8(byte[] bArr, int i2, int i3) {
        bArr[i2] = (byte) i3;
    }

    public static void writeUint16(byte[] bArr, int i2, int i3, boolean z2) {
        doWriteInt16(bArr, i2, i3 & 65535, z2);
    }

    public static void writeUint32(byte[] bArr, int i2, long j2, boolean z2) {
        if (z2) {
            bArr[i2] = (byte) (j2 & 255);
            bArr[i2 + 1] = (byte) ((j2 >>> 8) & 255);
            bArr[i2 + 2] = (byte) ((j2 >>> 16) & 255);
            bArr[i2 + 3] = (byte) ((j2 >>> 24) & 255);
            return;
        }
        bArr[i2] = (byte) ((j2 >>> 24) & 255);
        bArr[i2 + 1] = (byte) ((j2 >>> 16) & 255);
        bArr[i2 + 2] = (byte) ((j2 >>> 8) & 255);
        bArr[i2 + 3] = (byte) (j2 & 255);
    }

    public static void writeUint64(byte[] bArr, int i2, long j2, boolean z2) {
        if (z2) {
            bArr[i2] = (byte) (j2 & 255);
            bArr[i2 + 1] = (byte) ((j2 >>> 8) & 255);
            bArr[i2 + 2] = (byte) ((j2 >>> 16) & 255);
            bArr[i2 + 3] = (byte) ((j2 >>> 24) & 255);
            bArr[i2 + 4] = (byte) ((j2 >>> 32) & 255);
            bArr[i2 + 5] = (byte) ((j2 >>> 40) & 255);
            bArr[i2 + 6] = (byte) ((j2 >>> 48) & 255);
            bArr[i2 + 7] = (byte) ((j2 >>> 56) & 255);
            return;
        }
        bArr[i2] = (byte) ((j2 >>> 56) & 255);
        bArr[i2 + 1] = (byte) ((j2 >>> 48) & 255);
        bArr[i2 + 2] = (byte) ((j2 >>> 40) & 255);
        bArr[i2 + 3] = (byte) ((j2 >>> 32) & 255);
        bArr[i2 + 4] = (byte) ((j2 >>> 24) & 255);
        bArr[i2 + 5] = (byte) ((j2 >>> 16) & 255);
        bArr[i2 + 6] = (byte) ((j2 >>> 8) & 255);
        bArr[i2 + 7] = (byte) (j2 & 255);
    }

    public static void writeUint8(byte[] bArr, int i2, int i3) {
        bArr[i2] = (byte) (i3 & 255);
    }
}
