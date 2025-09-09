package com.xiaomi.push;

import android.content.Context;
import android.os.Build;

/* loaded from: classes4.dex */
public class ep {
    public static byte[] a(String str, byte[] bArr) {
        byte[] bArrM218a = bm.m218a(str);
        try {
            a(bArrM218a);
            return h.a(bArrM218a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        byte[] bArrM218a = bm.m218a(str);
        try {
            a(bArrM218a);
            return h.b(bArrM218a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    private static void a(byte[] bArr) {
        if (bArr.length >= 2) {
            bArr[0] = 99;
            bArr[1] = 100;
        }
    }

    public static boolean a(Context context, String str, long j2) {
        if (com.xiaomi.push.service.az.a(context).a(is.DCJobMutualSwitch.a(), false)) {
            return (Build.VERSION.SDK_INT < 29 || context.getApplicationInfo().targetSdkVersion < 29) && !af.a(context, str, j2);
        }
        return false;
    }
}
