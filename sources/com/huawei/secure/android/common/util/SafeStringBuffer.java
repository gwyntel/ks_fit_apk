package com.huawei.secure.android.common.util;

import android.util.Log;

/* loaded from: classes4.dex */
public class SafeStringBuffer {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18522a = "SafeStringBuffer";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18523b = "";

    public static String substring(StringBuffer stringBuffer, int i2) {
        if (stringBuffer != null && stringBuffer.length() >= i2 && i2 >= 0) {
            try {
                return stringBuffer.substring(i2);
            } catch (Exception e2) {
                Log.e(f18522a, "substring exception: " + e2.getMessage());
            }
        }
        return "";
    }

    public static String substring(StringBuffer stringBuffer, int i2, int i3) {
        if (stringBuffer != null && i2 >= 0 && i3 <= stringBuffer.length() && i3 >= i2) {
            try {
                return stringBuffer.substring(i2, i3);
            } catch (Exception e2) {
                Log.e(f18522a, "substring: " + e2.getMessage());
            }
        }
        return "";
    }
}
