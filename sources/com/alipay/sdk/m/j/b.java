package com.alipay.sdk.m.j;

import com.alipay.sdk.m.u.i;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f9317a = false;

    /* renamed from: b, reason: collision with root package name */
    public static String f9318b;

    public static void a(String str) {
        f9318b = str;
    }

    public static String b() {
        c cVarB = c.b(c.DOUBLE_REQUEST.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static boolean c() {
        return f9317a;
    }

    public static String d() {
        return f9318b;
    }

    public static String e() {
        c cVarB = c.b(c.PARAMS_ERROR.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static void a(boolean z2) {
        f9317a = z2;
    }

    public static String a() {
        c cVarB = c.b(c.CANCELED.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static String a(int i2, String str, String str2) {
        return "resultStatus={" + i2 + "};memo={" + str + "};result={" + str2 + i.f9804d;
    }
}
