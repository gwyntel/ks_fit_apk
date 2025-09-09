package com.vivo.push.cache;

import android.content.Context;
import com.vivo.push.util.p;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile b f23087a;

    /* renamed from: b, reason: collision with root package name */
    private d f23088b;

    private b() {
    }

    public static synchronized b a() {
        try {
            if (f23087a == null) {
                f23087a = new b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23087a;
    }

    public final d a(Context context) {
        d dVar = this.f23088b;
        if (dVar != null) {
            return dVar;
        }
        try {
            Method method = ClientConfigManagerImpl.class.getMethod("getInstance", Context.class);
            p.d("ConfigManagerFactory", "createConfig success is ".concat("com.vivo.push.cache.ClientConfigManagerImpl"));
            d dVar2 = (d) method.invoke(null, context);
            this.f23088b = dVar2;
            return dVar2;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.b("ConfigManagerFactory", "createConfig error", e2);
            return null;
        }
    }
}
