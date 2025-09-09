package com.meizu.cloud.pushsdk.f.b;

import com.meizu.cloud.pushsdk.f.g.d;
import com.meizu.cloud.pushsdk.f.g.e;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class b implements a {

    /* renamed from: a, reason: collision with root package name */
    private final String f19523a = b.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<String, Object> f19524b = new HashMap<>();

    public b(String str, Object obj) {
        a(str);
        a(obj);
    }

    public b a(Object obj) {
        if (obj == null) {
            return this;
        }
        this.f19524b.put("dt", obj);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.f.b.a
    public long b() {
        return e.a(toString());
    }

    public String toString() {
        return e.a((Map) this.f19524b).toString();
    }

    public b a(String str) {
        d.a(str, "schema cannot be null");
        d.a(!str.isEmpty(), "schema cannot be empty.");
        this.f19524b.put("sa", str);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.f.b.a
    public Map<String, Object> a() {
        return this.f19524b;
    }

    @Override // com.meizu.cloud.pushsdk.f.b.a
    @Deprecated
    public void a(String str, String str2) {
        com.meizu.cloud.pushsdk.f.g.c.c(this.f19523a, "Payload: add(String, String) method called - Doing nothing.", new Object[0]);
    }
}
