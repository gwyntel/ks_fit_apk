package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private static z0 f16312a;

    private static synchronized z0 a() {
        try {
            if (f16312a == null) {
                f16312a = q.c().b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f16312a;
    }

    public static void b(int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a() == null || !q1.b().a()) {
            return;
        }
        if (i2 == 1 || i2 == 0) {
            f16312a.b(i2, str, linkedHashMap);
            return;
        }
        v.d("hmsSdk", "Data type no longer collects range.type: " + i2);
    }

    public static void c() {
        if (a() == null || !q1.b().a()) {
            return;
        }
        f16312a.a(-1);
    }

    public static void a(int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a() == null || !q1.b().a()) {
            return;
        }
        if (i2 == 1 || i2 == 0) {
            f16312a.a(i2, str, linkedHashMap);
            return;
        }
        v.d("hmsSdk", "Data type no longer collects range.type: " + i2);
    }

    public static boolean b() {
        return q.c().a();
    }

    @Deprecated
    public static void a(Context context, String str, String str2) {
        if (a() != null) {
            f16312a.a(context, str, str2);
        }
    }
}
