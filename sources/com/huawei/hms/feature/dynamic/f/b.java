package com.huawei.hms.feature.dynamic.f;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static final char[] f16157a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    public static final char[] f16158b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] a(byte[] bArr) {
        return a(bArr, false);
    }

    public static String b(byte[] bArr, boolean z2) {
        return new String(a(bArr, z2));
    }

    public static char[] a(byte[] bArr, boolean z2) {
        return a(bArr, z2 ? f16158b : f16157a);
    }

    public static char[] a(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b2 & 240) >>> 4];
            i2 += 2;
            cArr2[i3] = cArr[b2 & 15];
        }
        return cArr2;
    }
}
