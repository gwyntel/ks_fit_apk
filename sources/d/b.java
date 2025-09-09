package d;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public Map<String, Object> f24963a;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static b f24964a = new b();
    }

    public static b a() {
        return a.f24964a;
    }

    public b() {
        this.f24963a = new ConcurrentHashMap();
    }

    public void a(Class<?> cls, Object obj) {
        if (cls == null || obj == null) {
            return;
        }
        this.f24963a.put(cls.getCanonicalName(), obj);
    }

    public <T> T a(Class cls) {
        if (cls == null) {
            return null;
        }
        return (T) this.f24963a.get(cls.getCanonicalName());
    }
}
