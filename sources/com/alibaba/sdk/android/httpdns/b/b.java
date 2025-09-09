package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static f f8797a = null;

    /* renamed from: a, reason: collision with other field name */
    private static com.alibaba.sdk.android.httpdns.c.a f5a = null;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f6a = false;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f8798n = true;

    public static List<e> a() {
        ArrayList arrayList = new ArrayList();
        if (!f6a) {
            return arrayList;
        }
        arrayList.addAll(f8797a.a());
        return arrayList;
    }

    public static void b(Context context) {
        if (context != null) {
            f5a.c(context);
        }
    }

    public static boolean g() {
        return f8798n;
    }

    public static String i() {
        return f5a.i();
    }

    public static void a(Context context) {
        a(context, (com.alibaba.sdk.android.httpdns.c.a) null);
    }

    public static void b(e eVar) {
        if (eVar == null) {
            return;
        }
        f8797a.b(eVar);
    }

    public static void a(Context context, com.alibaba.sdk.android.httpdns.c.a aVar) {
        f8797a = new a(context);
        f5a = aVar;
        if (aVar == null) {
            f5a = com.alibaba.sdk.android.httpdns.c.a.a();
        }
    }

    public static void a(e eVar) {
        if (eVar == null) {
            return;
        }
        f8797a.a(eVar);
    }

    public static void a(boolean z2, boolean z3) {
        f6a = z2;
        f8798n = z3;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m28a() {
        return f6a;
    }
}
