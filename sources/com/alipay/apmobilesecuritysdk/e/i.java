package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    public static String f9076a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f9077b = "";

    /* renamed from: c, reason: collision with root package name */
    public static String f9078c = "";

    /* renamed from: d, reason: collision with root package name */
    public static String f9079d = "";

    /* renamed from: e, reason: collision with root package name */
    public static String f9080e = "";

    /* renamed from: f, reason: collision with root package name */
    public static Map<String, String> f9081f = new HashMap();

    public static synchronized String a(String str) {
        String str2 = "apdidTokenCache" + str;
        if (f9081f.containsKey(str2)) {
            String str3 = f9081f.get(str2);
            if (com.alipay.sdk.m.z.a.b(str3)) {
                return str3;
            }
        }
        return "";
    }

    public static synchronized String b() {
        return f9076a;
    }

    public static synchronized String c() {
        return f9077b;
    }

    public static synchronized String d() {
        return f9079d;
    }

    public static synchronized String e() {
        return f9080e;
    }

    public static synchronized String f() {
        return f9078c;
    }

    public static synchronized c g() {
        return new c(f9076a, f9077b, f9078c, f9079d, f9080e);
    }

    public static void h() {
        f9081f.clear();
        f9076a = "";
        f9077b = "";
        f9079d = "";
        f9080e = "";
        f9078c = "";
    }

    public static synchronized void a() {
    }

    public static void b(String str) {
        f9076a = str;
    }

    public static void c(String str) {
        f9077b = str;
    }

    public static void d(String str) {
        f9078c = str;
    }

    public static void e(String str) {
        f9079d = str;
    }

    public static void f(String str) {
        f9080e = str;
    }

    public static synchronized void a(b bVar) {
        if (bVar != null) {
            f9076a = bVar.f9062a;
            f9077b = bVar.f9063b;
            f9078c = bVar.f9064c;
        }
    }

    public static synchronized void a(c cVar) {
        if (cVar != null) {
            f9076a = cVar.f9065a;
            f9077b = cVar.f9066b;
            f9079d = cVar.f9068d;
            f9080e = cVar.f9069e;
            f9078c = cVar.f9067c;
        }
    }

    public static synchronized void a(String str, String str2) {
        try {
            String str3 = "apdidTokenCache" + str;
            if (f9081f.containsKey(str3)) {
                f9081f.remove(str3);
            }
            f9081f.put(str3, str2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized boolean a(Context context, String str) {
        long jA;
        try {
            jA = h.a(context);
        } catch (Throwable unused) {
        }
        if (jA < 0) {
            jA = 86400000;
        }
        try {
            if (Math.abs(System.currentTimeMillis() - h.h(context, str)) < jA) {
                return true;
            }
        } finally {
            return false;
        }
        return false;
    }
}
