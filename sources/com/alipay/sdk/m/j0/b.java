package com.alipay.sdk.m.j0;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9338a = "IdentifierManager";

    /* renamed from: b, reason: collision with root package name */
    public static Object f9339b;

    /* renamed from: c, reason: collision with root package name */
    public static Class<?> f9340c;

    /* renamed from: d, reason: collision with root package name */
    public static Method f9341d;

    /* renamed from: e, reason: collision with root package name */
    public static Method f9342e;

    /* renamed from: f, reason: collision with root package name */
    public static Method f9343f;

    /* renamed from: g, reason: collision with root package name */
    public static Method f9344g;

    static {
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            f9340c = cls;
            f9339b = cls.newInstance();
            f9341d = f9340c.getMethod("getUDID", Context.class);
            f9342e = f9340c.getMethod("getOAID", Context.class);
            f9343f = f9340c.getMethod("getVAID", Context.class);
            f9344g = f9340c.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            Log.e(f9338a, "reflect exception!", e2);
        }
    }

    public static boolean a() {
        return (f9340c == null || f9339b == null) ? false : true;
    }

    public static String b(Context context) {
        return a(context, f9342e);
    }

    public static String c(Context context) {
        return a(context, f9341d);
    }

    public static String d(Context context) {
        return a(context, f9343f);
    }

    public static String a(Context context) {
        return a(context, f9344g);
    }

    public static String a(Context context, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = f9339b;
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object objInvoke = method.invoke(obj, context);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
            return null;
        } catch (Exception e2) {
            Log.e(f9338a, "invoke exception!", e2);
            return null;
        }
    }
}
