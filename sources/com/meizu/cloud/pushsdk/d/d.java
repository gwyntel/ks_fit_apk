package com.meizu.cloud.pushsdk.d;

import com.huawei.hms.framework.common.ContainerUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes4.dex */
class d {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f19210a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', IOUtils.DIR_SEPARATOR_UNIX};

    /* renamed from: b, reason: collision with root package name */
    private static final char f19211b = (char) Integer.parseInt("00000011", 2);

    /* renamed from: c, reason: collision with root package name */
    private static final char f19212c = (char) Integer.parseInt("00001111", 2);

    /* renamed from: d, reason: collision with root package name */
    private static final char f19213d = (char) Integer.parseInt("00111111", 2);

    /* renamed from: e, reason: collision with root package name */
    private final String f19214e;

    /* renamed from: f, reason: collision with root package name */
    private char[] f19215f;

    /* renamed from: g, reason: collision with root package name */
    private int f19216g = 0;

    public d(String str) {
        this.f19214e = str;
        a();
    }

    public String a(byte[] bArr) {
        String str;
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(((bArr.length + 2) / 3) * 4);
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int i4 = bArr[i2] & 255;
            if (i3 == length) {
                sb.append(this.f19215f[i4 >>> 2]);
                sb.append(this.f19215f[(f19211b & i4) << 4]);
                str = "==";
            } else {
                int i5 = i2 + 2;
                int i6 = bArr[i3] & 255;
                if (i5 == length) {
                    sb.append(this.f19215f[i4 >>> 2]);
                    sb.append(this.f19215f[((f19211b & i4) << 4) | (i6 >>> 4)]);
                    sb.append(this.f19215f[(f19212c & i6) << 2]);
                    str = ContainerUtils.KEY_VALUE_DELIMITER;
                } else {
                    i2 += 3;
                    int i7 = bArr[i5] & 255;
                    sb.append(this.f19215f[i4 >>> 2]);
                    sb.append(this.f19215f[((i4 & f19211b) << 4) | (i6 >>> 4)]);
                    sb.append(this.f19215f[((i6 & f19212c) << 2) | (i7 >>> 6)]);
                    sb.append(this.f19215f[f19213d & i7]);
                }
            }
            sb.append(str);
            break;
        }
        return sb.toString();
    }

    private void a() {
        char[] cArr = new char[f19210a.length];
        int i2 = 0;
        this.f19216g = this.f19214e.charAt(0) % CharUtils.CR;
        while (true) {
            char[] cArr2 = f19210a;
            if (i2 >= cArr2.length) {
                this.f19215f = cArr;
                return;
            } else {
                cArr[i2] = cArr2[(this.f19216g + i2) % cArr2.length];
                i2++;
            }
        }
    }
}
