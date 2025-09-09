package com.alipay.sdk.m.d;

import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9224a = "OpenId";

    /* renamed from: b, reason: collision with root package name */
    public static boolean f9225b = false;

    public static void a(boolean z2) {
        Log.d(f9224a, "setDebug:" + z2);
        f9225b = z2;
    }

    public static void b(String str, Object... objArr) {
        if (f9225b) {
            Log.d(f9224a, a(str, objArr));
        }
    }

    public static void c(String str, Object... objArr) {
        if (f9225b) {
            Log.e(f9224a, a(str, objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (f9225b) {
            Log.i(f9224a, a(str, objArr));
        }
    }

    public static void e(String str, Object... objArr) {
        if (f9225b) {
            Log.w(f9224a, a(str, objArr));
        }
    }

    public static String a(String str, Object... objArr) {
        int i2 = 0;
        if (str == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        }
        sb.append(String.format("[%s] ", str));
        if (objArr != null) {
            int length = objArr.length;
            while (true) {
                int i3 = i2 + 1;
                if (i3 >= objArr.length) {
                    break;
                }
                sb.append(a(objArr[i2], objArr[i3]));
                if (i3 < length - 1) {
                    sb.append(",");
                }
                i2 += 2;
            }
            if (i2 == objArr.length - 1) {
                sb.append(objArr[i2]);
            }
        }
        return sb.toString();
    }

    public static String a(Object obj, Object obj2) {
        if (obj == null) {
            obj = "";
        }
        if (obj2 == null) {
            obj2 = "";
        }
        return String.format("%s:%s", obj, obj2);
    }
}
