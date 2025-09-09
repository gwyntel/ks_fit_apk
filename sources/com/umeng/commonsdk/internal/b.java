package com.umeng.commonsdk.internal;

import android.content.Context;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f22263b;

    /* renamed from: a, reason: collision with root package name */
    private Context f22264a;

    /* renamed from: c, reason: collision with root package name */
    private c f22265c;

    private b(Context context) {
        this.f22264a = context;
        this.f22265c = new c(context);
    }

    public static synchronized b a(Context context) {
        try {
            if (f22263b == null) {
                f22263b = new b(context.getApplicationContext());
            }
        } catch (Throwable th) {
            throw th;
        }
        return f22263b;
    }

    public c a() {
        return this.f22265c;
    }
}
