package com.umeng.ut.a.c;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    private static boolean f22997b = false;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f22998c = false;

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m86a() {
        return f22997b;
    }

    public static void b(String str, Object... objArr) {
        if (f22998c) {
            Log.d(d(), a(str, objArr));
        }
    }

    public static void c() {
        if (f22997b) {
            Log.d(d(), a((String) null, new Object[0]));
        }
    }

    private static String d() {
        String methodName;
        String strSubstring;
        StackTraceElement stackTraceElementA = a();
        if (stackTraceElementA != null) {
            String className = stackTraceElementA.getClassName();
            strSubstring = !TextUtils.isEmpty(className) ? className.substring(className.lastIndexOf(46) + 1) : "";
            methodName = stackTraceElementA.getMethodName();
        } else {
            methodName = "";
            strSubstring = methodName;
        }
        return "Utdid." + strSubstring + "." + methodName + "." + String.valueOf(Process.myPid()) + "." + (Thread.currentThread().getId() + "");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m85a(String str, Object... objArr) {
        if (f22997b) {
            Log.d(d(), a(str, objArr));
        }
    }

    public static void b(String str, Throwable th, Object... objArr) {
        if (f22998c) {
            Log.e(d(), a(str, objArr), th);
        }
    }

    public static void a(String str, Throwable th, Object... objArr) {
        if (f22997b) {
            Log.e(d(), a(str, objArr), th);
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

    private static String a(String str, Object... objArr) {
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

    private static StackTraceElement a() {
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(e.class.getName())) {
                    return stackTraceElement;
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
