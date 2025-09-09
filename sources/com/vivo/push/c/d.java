package com.vivo.push.c;

import android.content.Context;
import com.vivo.push.util.ContextDelegate;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: d, reason: collision with root package name */
    private static volatile d f23078d;

    /* renamed from: a, reason: collision with root package name */
    private b f23079a;

    /* renamed from: b, reason: collision with root package name */
    private c f23080b;

    /* renamed from: c, reason: collision with root package name */
    private Context f23081c;

    private d(Context context) {
        if (this.f23079a == null) {
            this.f23081c = ContextDelegate.getContext(context.getApplicationContext());
            this.f23079a = new e(this.f23081c);
        }
        if (this.f23080b == null) {
            this.f23080b = new a();
        }
    }

    public static d a(Context context) {
        if (f23078d == null) {
            synchronized (d.class) {
                try {
                    if (f23078d == null && context != null) {
                        f23078d = new d(context);
                    }
                } finally {
                }
            }
        }
        return f23078d;
    }

    public final b a() {
        return this.f23079a;
    }
}
