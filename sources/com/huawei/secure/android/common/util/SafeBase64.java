package com.huawei.secure.android.common.util;

import android.util.Base64;
import android.util.Log;

/* loaded from: classes4.dex */
public class SafeBase64 {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18519a = "SafeBase64";

    private SafeBase64() {
    }

    public static byte[] decode(byte[] bArr, int i2) {
        try {
            return Base64.decode(bArr, i2);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message0 : " + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] encode(byte[] bArr, int i2) {
        try {
            return Base64.encode(bArr, i2);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message3 : " + e2.getMessage());
            return new byte[0];
        }
    }

    public static String encodeToString(byte[] bArr, int i2) {
        try {
            return Base64.encodeToString(bArr, i2);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message5 : " + e2.getMessage());
            return "";
        }
    }

    public static String encodeToString(byte[] bArr, int i2, int i3, int i4) {
        try {
            return Base64.encodeToString(bArr, i2, i3, i4);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message6 : " + e2.getMessage());
            return "";
        }
    }

    public static byte[] decode(byte[] bArr, int i2, int i3, int i4) {
        try {
            return Base64.decode(bArr, i2, i3, i4);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message1 : " + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] encode(byte[] bArr, int i2, int i3, int i4) {
        try {
            return Base64.encode(bArr, i2, i3, i4);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message4 : " + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] decode(String str, int i2) {
        try {
            return Base64.decode(str, i2);
        } catch (Exception e2) {
            Log.e(f18519a, e2.getClass().getSimpleName() + " , message2 : " + e2.getMessage());
            return new byte[0];
        }
    }
}
