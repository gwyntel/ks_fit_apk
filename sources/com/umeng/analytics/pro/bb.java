package com.umeng.analytics.pro;

import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class bb {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21400a = "OpenId";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f21401b = false;

    public static void a(boolean z2) {
        Log.d("OpenId", "setDebug:" + z2);
        f21401b = z2;
    }

    public static void b(String str, Object... objArr) {
        if (f21401b) {
            Log.i("OpenId", e(str, objArr));
        }
    }

    public static void c(String str, Object... objArr) {
        if (f21401b) {
            Log.w("OpenId", e(str, objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (f21401b) {
            Log.e("OpenId", e(str, objArr));
        }
    }

    private static String e(String str, Object... objArr) {
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

    public static void a(String str, Object... objArr) {
        if (f21401b) {
            Log.d("OpenId", e(str, objArr));
        }
    }

    private static String a(Object obj, Object obj2) {
        if (obj == null) {
            obj = "";
        }
        if (obj2 == null) {
            obj2 = "";
        }
        return String.format("%s:%s", obj, obj2);
    }
}
