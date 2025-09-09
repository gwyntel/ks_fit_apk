package com.huawei.hms.feature.dynamic.f;

import android.util.Base64;
import com.huawei.hms.common.util.Logger;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16156a = "Base64";

    public static String a(byte[] bArr) {
        String strEncodeToString;
        if (bArr == null) {
            return "";
        }
        try {
            strEncodeToString = Base64.encodeToString(bArr, 2);
        } catch (AssertionError e2) {
            Logger.e(f16156a, "An exception occurred while encoding with Base64,AssertionError:", e2);
        }
        return strEncodeToString != null ? strEncodeToString : "";
    }

    public static byte[] a(String str) {
        if (str == null) {
            return new byte[0];
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 2);
            if (bArrDecode != null) {
                return bArrDecode;
            }
        } catch (IllegalArgumentException e2) {
            Logger.e(f16156a, "Decoding with Base64 IllegalArgumentException:", e2);
        }
        return new byte[0];
    }
}
