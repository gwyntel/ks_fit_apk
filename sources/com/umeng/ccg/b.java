package com.umeng.ccg;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f22025a = true;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f22026b = true;

    /* renamed from: c, reason: collision with root package name */
    private static volatile boolean f22027c = true;

    /* renamed from: d, reason: collision with root package name */
    private static volatile boolean f22028d = true;

    /* renamed from: e, reason: collision with root package name */
    private static volatile boolean f22029e = true;

    /* renamed from: g, reason: collision with root package name */
    private static Map<String, Boolean> f22031g = new HashMap();

    /* renamed from: f, reason: collision with root package name */
    private static Object f22030f = new Object();

    public static boolean a() {
        boolean z2;
        synchronized (f22030f) {
            z2 = f22025a;
        }
        return z2;
    }

    public static boolean b() {
        boolean z2;
        synchronized (f22030f) {
            z2 = f22026b;
        }
        return z2;
    }

    public static boolean c() {
        boolean z2;
        synchronized (f22030f) {
            z2 = f22027c;
        }
        return z2;
    }

    public static boolean d() {
        boolean z2;
        synchronized (f22030f) {
            z2 = f22028d;
        }
        return z2;
    }

    public static void a(boolean z2) {
        synchronized (f22030f) {
            f22028d = z2;
            f22031g.put(a.f22003e, Boolean.valueOf(z2));
        }
    }

    public static void b(boolean z2) {
        synchronized (f22030f) {
            f22029e = z2;
            f22031g.put(a.f22007i, Boolean.valueOf(z2));
        }
    }

    public static boolean a(String str) {
        boolean zBooleanValue;
        synchronized (f22030f) {
            try {
                zBooleanValue = f22031g.containsKey(str) ? f22031g.get(str).booleanValue() : true;
            } catch (Throwable th) {
                throw th;
            }
        }
        return zBooleanValue;
    }
}
