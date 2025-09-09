package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class ae {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f23432a = "0123456789ABCDEF".toCharArray();

    public static boolean a(Context context) {
        return ad.f23431a;
    }

    public static String a(byte[] bArr, int i2, int i3) {
        StringBuilder sb = new StringBuilder(i3 * 2);
        for (int i4 = 0; i4 < i3; i4++) {
            byte b2 = bArr[i2 + i4];
            char[] cArr = f23432a;
            sb.append(cArr[(b2 & 255) >> 4]);
            sb.append(cArr[b2 & 15]);
        }
        return sb.toString();
    }
}
