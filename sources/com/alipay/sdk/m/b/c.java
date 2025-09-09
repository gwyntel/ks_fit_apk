package com.alipay.sdk.m.b;

import android.content.Context;
import android.os.Looper;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static b f9193a = null;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f9194b = false;

    public static synchronized String a(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        b(context);
        b bVar = f9193a;
        if (bVar != null) {
            try {
                return bVar.a(context);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static void b(Context context) {
        if (f9193a != null || f9194b) {
            return;
        }
        synchronized (c.class) {
            try {
                if (f9193a == null && !f9194b) {
                    f9193a = com.alipay.sdk.m.c.a.a(context);
                    f9194b = true;
                }
            } finally {
            }
        }
    }
}
