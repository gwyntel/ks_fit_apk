package com.vivo.push.util;

import android.content.Context;

/* loaded from: classes4.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    public static final o f23256a = new n();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f23257b;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f23258c;

    static {
        b();
    }

    public static boolean a() {
        return f23257b && f23258c;
    }

    private static void b() {
        f23257b = z.b("persist.sys.log.ctrl", "no").equals("yes");
    }

    public static int c(String str, String str2) {
        return f23256a.c(str, str2);
    }

    public static int d(String str, String str2) {
        return f23256a.d(str, str2);
    }

    public static int e(String str, String str2) {
        return f23256a.e(str, str2);
    }

    public static int b(String str, String str2) {
        return f23256a.b(str, str2);
    }

    public static void c(Context context, String str) {
        f23256a.c(context, str);
    }

    public static void a(boolean z2) {
        b();
        f23258c = z2;
    }

    public static int b(String str, String str2, Throwable th) {
        return f23256a.b(str, str2, th);
    }

    public static void b(Context context, String str) {
        f23256a.b(context, str);
    }

    public static int a(String str, String str2) {
        return f23256a.a(str, str2);
    }

    public static int a(String str, Throwable th) {
        return f23256a.a(str, th);
    }

    public static int a(String str, String str2, Throwable th) {
        return f23256a.a(str, str2, th);
    }

    public static String a(Throwable th) {
        return f23256a.a(th);
    }

    public static void a(Context context, String str) {
        f23256a.a(context, str);
    }
}
