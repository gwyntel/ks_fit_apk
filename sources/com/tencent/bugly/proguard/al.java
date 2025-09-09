package com.tencent.bugly.proguard;

import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class al {

    /* renamed from: a, reason: collision with root package name */
    public static String f20773a = "CrashReportInfo";

    /* renamed from: b, reason: collision with root package name */
    public static String f20774b = "CrashReport";

    /* renamed from: c, reason: collision with root package name */
    public static boolean f20775c = false;

    private static boolean a(int i2, String str, Object... objArr) {
        if (!f20775c) {
            return false;
        }
        if (str == null) {
            str = TmpConstant.GROUP_ROLE_UNKNOWN;
        } else if (objArr != null && objArr.length != 0) {
            str = String.format(Locale.US, str, objArr);
        }
        if (i2 == 0) {
            Log.i(f20774b, str);
            return true;
        }
        if (i2 == 1) {
            Log.d(f20774b, str);
            return true;
        }
        if (i2 == 2) {
            Log.w(f20774b, str);
            return true;
        }
        if (i2 == 3) {
            Log.e(f20774b, str);
            return true;
        }
        if (i2 != 5) {
            return false;
        }
        Log.i(f20773a, str);
        return true;
    }

    public static boolean b(String str, Object... objArr) {
        return a(5, str, objArr);
    }

    public static boolean c(String str, Object... objArr) {
        return a(1, str, objArr);
    }

    public static boolean d(String str, Object... objArr) {
        return a(2, str, objArr);
    }

    public static boolean e(String str, Object... objArr) {
        return a(3, str, objArr);
    }

    public static boolean b(Throwable th) {
        return a(3, th);
    }

    private static boolean a(int i2, Throwable th) {
        if (f20775c) {
            return a(i2, ap.a(th), new Object[0]);
        }
        return false;
    }

    public static boolean a(String str, Object... objArr) {
        return a(0, str, objArr);
    }

    public static boolean a(Class cls, String str, Object... objArr) {
        return a(0, String.format(Locale.US, "[%s] %s", cls.getSimpleName(), str), objArr);
    }

    public static boolean a(Throwable th) {
        return a(2, th);
    }
}
