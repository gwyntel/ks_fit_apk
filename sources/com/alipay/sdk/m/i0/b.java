package com.alipay.sdk.m.i0;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9298a = "OpenIdHelper";

    /* renamed from: b, reason: collision with root package name */
    public static Method f9299b;

    public static String a(Context context) {
        f fVarA = f.a();
        return fVarA.a(context.getApplicationContext(), fVarA.f9309c);
    }

    public static String b(Context context) {
        f fVarA = f.a();
        return fVarA.a(context.getApplicationContext(), fVarA.f9308b);
    }

    public static String c(Context context) {
        f fVarA = f.a();
        return fVarA.a(context.getApplicationContext(), fVarA.f9307a);
    }

    public static String d(Context context) {
        f fVarA = f.a();
        return fVarA.a(context.getApplicationContext(), fVarA.f9310d);
    }

    public static void a(boolean z2) {
        f.a();
        f.a(z2);
    }

    public static final boolean a() throws NoSuchMethodException, SecurityException {
        Context context = null;
        try {
            if (f9299b == null) {
                Method method = Class.forName("android.app.ActivityThread").getMethod("currentApplication", null);
                f9299b = method;
                method.setAccessible(true);
            }
            context = (Context) f9299b.invoke(null, null);
        } catch (Exception e2) {
            Log.e(f9298a, "ActivityThread:currentApplication --> " + e2.toString());
        }
        if (context == null) {
            return false;
        }
        return f.a().a(context, false);
    }
}
