package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class au {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f23457a = false;

    private static void a(Class<?> cls, Context context) {
        if (f23457a) {
            return;
        }
        try {
            f23457a = true;
            cls.getDeclaredMethod("InitEntry", Context.class).invoke(cls, context);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.m91a("mdid:load lib error " + th);
        }
    }

    public static boolean a(Context context) {
        try {
            Class<?> clsA = C0472r.a(context, "com.bun.miitmdid.core.JLibrary");
            if (clsA == null) {
                return false;
            }
            a(clsA, context);
            return true;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.m91a("mdid:check error " + th);
            return false;
        }
    }
}
