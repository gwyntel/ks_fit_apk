package com.meizu.cloud.pushsdk.f.b;

import com.meizu.cloud.pushsdk.f.g.e;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class c implements a {

    /* renamed from: a, reason: collision with root package name */
    private final String f19525a = c.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<String, Object> f19526b = new HashMap<>();

    @Override // com.meizu.cloud.pushsdk.f.b.a
    public Map a() {
        return this.f19526b;
    }

    @Override // com.meizu.cloud.pushsdk.f.b.a
    public long b() {
        return e.a(toString());
    }

    public String toString() {
        return e.a((Map) this.f19526b).toString();
    }

    public void a(String str, Object obj) {
        if (obj != null) {
            this.f19526b.put(str, obj);
            return;
        }
        com.meizu.cloud.pushsdk.f.g.c.c(this.f19525a, "The keys value is empty, returning without adding key: " + str, new Object[0]);
    }

    @Override // com.meizu.cloud.pushsdk.f.b.a
    public void a(String str, String str2) {
        if (str2 != null && !str2.isEmpty()) {
            this.f19526b.put(str, str2);
            return;
        }
        com.meizu.cloud.pushsdk.f.g.c.c(this.f19525a, "The keys value is empty, returning without adding key: " + str, new Object[0]);
    }

    public void a(Map<String, Object> map) {
        if (map == null) {
            com.meizu.cloud.pushsdk.f.g.c.c(this.f19525a, "Map passed in is null, returning without adding map.", new Object[0]);
        } else {
            this.f19526b.putAll(map);
        }
    }
}
