package com.alipay.sdk.m.h0;

import android.content.Context;
import com.alipay.sdk.m.a.a;

/* loaded from: classes2.dex */
public class a {
    public static String a(Context context) {
        if (a.b.f9167a) {
            return a.c.b.f9175a.a(context.getApplicationContext(), "AUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String b(Context context) {
        if (a.b.f9167a) {
            return a.c.b.f9175a.a(context.getApplicationContext(), "OUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String c(Context context) {
        if (a.b.f9167a) {
            return a.c.b.f9175a.a(context.getApplicationContext(), "GUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String d(Context context) {
        if (a.b.f9167a) {
            return a.c.b.f9175a.a(context.getApplicationContext(), "DUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static void e(Context context) {
        a.b.f9168b = a.c.b.f9175a.a(context.getApplicationContext());
        a.b.f9167a = true;
    }

    public static boolean a() {
        if (a.b.f9167a) {
            return a.b.f9168b;
        }
        throw new RuntimeException("SDK Need Init First!");
    }
}
