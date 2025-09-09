package c.a.d;

import com.google.common.base.Ascii;

/* loaded from: classes2.dex */
public abstract class h {
    public static int a(byte[] bArr, int i2) {
        return (bArr[i2 + 3] & 255) | (bArr[i2] << Ascii.CAN) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
    }

    public static long b(byte[] bArr, int i2) {
        return (a(bArr, i2 + 4) & 4294967295L) | ((a(bArr, i2) & 4294967295L) << 32);
    }

    public static int c(byte[] bArr, int i2) {
        return (bArr[i2 + 3] << Ascii.CAN) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    public static byte[] a(int i2) {
        byte[] bArr = new byte[4];
        a(i2, bArr, 0);
        return bArr;
    }

    public static void a(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >>> 24);
        bArr[i3 + 1] = (byte) (i2 >>> 16);
        bArr[i3 + 2] = (byte) (i2 >>> 8);
        bArr[i3 + 3] = (byte) i2;
    }

    public static void a(long j2, byte[] bArr, int i2) {
        a((int) (j2 >>> 32), bArr, i2);
        a((int) (j2 & 4294967295L), bArr, i2 + 4);
    }
}
