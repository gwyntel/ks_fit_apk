package com.umeng.commonsdk.config;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class b implements f {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, Boolean> f22111a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static Object f22112b = new Object();

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f22113a = new b();

        private a() {
        }
    }

    public static b b() {
        return a.f22113a;
    }

    public void a() {
        synchronized (f22112b) {
            f22111a.clear();
        }
    }

    private b() {
    }

    public static boolean a(String str) {
        if (!d.a(str)) {
            return false;
        }
        synchronized (f22112b) {
            try {
                if (!f22111a.containsKey(str)) {
                    return true;
                }
                return f22111a.get(str).booleanValue();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.umeng.commonsdk.config.f
    public void a(String str, Boolean bool) {
        if (d.a(str)) {
            synchronized (f22112b) {
                try {
                    Map<String, Boolean> map = f22111a;
                    if (map != null) {
                        map.put(str, bool);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
