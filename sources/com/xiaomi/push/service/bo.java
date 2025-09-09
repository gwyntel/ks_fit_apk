package com.xiaomi.push.service;

import com.huawei.hms.framework.common.ContainerUtils;

/* loaded from: classes4.dex */
public class bo {

    /* renamed from: a, reason: collision with root package name */
    private static int f24547a = 8;

    /* renamed from: d, reason: collision with root package name */
    private int f24550d = -666;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f1052a = new byte[256];

    /* renamed from: c, reason: collision with root package name */
    private int f24549c = 0;

    /* renamed from: b, reason: collision with root package name */
    private int f24548b = 0;

    public static int a(byte b2) {
        return b2 >= 0 ? b2 : b2 + 256;
    }

    private void a(int i2, byte[] bArr, boolean z2) {
        int length = bArr.length;
        for (int i3 = 0; i3 < 256; i3++) {
            this.f1052a[i3] = (byte) i3;
        }
        this.f24549c = 0;
        this.f24548b = 0;
        while (true) {
            int i4 = this.f24548b;
            if (i4 >= i2) {
                break;
            }
            int iA = ((this.f24549c + a(this.f1052a[i4])) + a(bArr[this.f24548b % length])) % 256;
            this.f24549c = iA;
            a(this.f1052a, this.f24548b, iA);
            this.f24548b++;
        }
        if (i2 != 256) {
            this.f24550d = ((this.f24549c + a(this.f1052a[i2])) + a(bArr[i2 % length])) % 256;
        }
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("S_");
            int i5 = i2 - 1;
            sb.append(i5);
            sb.append(":");
            for (int i6 = 0; i6 <= i2; i6++) {
                sb.append(" ");
                sb.append(a(this.f1052a[i6]));
            }
            sb.append("   j_");
            sb.append(i5);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(this.f24549c);
            sb.append("   j_");
            sb.append(i2);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(this.f24550d);
            sb.append("   S_");
            sb.append(i5);
            sb.append("[j_");
            sb.append(i5);
            sb.append("]=");
            sb.append(a(this.f1052a[this.f24549c]));
            sb.append("   S_");
            sb.append(i5);
            sb.append("[j_");
            sb.append(i2);
            sb.append("]=");
            sb.append(a(this.f1052a[this.f24550d]));
            if (this.f1052a[1] != 0) {
                sb.append("   S[1]!=0");
            }
            com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
        }
    }

    private void a(byte[] bArr) {
        a(256, bArr, false);
    }

    private void a() {
        this.f24549c = 0;
        this.f24548b = 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    byte m764a() {
        int i2 = (this.f24548b + 1) % 256;
        this.f24548b = i2;
        int iA = (this.f24549c + a(this.f1052a[i2])) % 256;
        this.f24549c = iA;
        a(this.f1052a, this.f24548b, iA);
        byte[] bArr = this.f1052a;
        return bArr[(a(bArr[this.f24548b]) + a(this.f1052a[this.f24549c])) % 256];
    }

    private static void a(byte[] bArr, int i2, int i3) {
        byte b2 = bArr[i2];
        bArr[i2] = bArr[i3];
        bArr[i3] = b2;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr2.length];
        bo boVar = new bo();
        boVar.a(bArr);
        boVar.a();
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            bArr3[i2] = (byte) (bArr2[i2] ^ boVar.m764a());
        }
        return bArr3;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z2, int i2, int i3) {
        byte[] bArr3;
        int i4;
        if (i2 >= 0 && i2 <= bArr2.length && i2 + i3 <= bArr2.length) {
            if (z2) {
                bArr3 = bArr2;
                i4 = i2;
            } else {
                bArr3 = new byte[i3];
                i4 = 0;
            }
            bo boVar = new bo();
            boVar.a(bArr);
            boVar.a();
            for (int i5 = 0; i5 < i3; i5++) {
                bArr3[i4 + i5] = (byte) (bArr2[i2 + i5] ^ boVar.m764a());
            }
            return bArr3;
        }
        throw new IllegalArgumentException("start = " + i2 + " len = " + i3);
    }

    public static byte[] a(byte[] bArr, String str) {
        return a(bArr, com.xiaomi.push.bm.m218a(str));
    }

    public static byte[] a(String str, String str2) {
        byte[] bArrM218a = com.xiaomi.push.bm.m218a(str);
        byte[] bytes = str2.getBytes();
        byte[] bArr = new byte[bArrM218a.length + 1 + bytes.length];
        for (int i2 = 0; i2 < bArrM218a.length; i2++) {
            bArr[i2] = bArrM218a[i2];
        }
        bArr[bArrM218a.length] = 95;
        for (int i3 = 0; i3 < bytes.length; i3++) {
            bArr[bArrM218a.length + 1 + i3] = bytes[i3];
        }
        return bArr;
    }
}
