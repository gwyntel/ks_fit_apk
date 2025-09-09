package com.xiaomi.push;

import com.kingsmith.miot.KsProperty;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class gc implements ga {

    /* renamed from: a, reason: collision with root package name */
    private ga f23787a;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static gc f23788a = new gc();
    }

    public static gc a() {
        return a.f23788a;
    }

    private gc() {
    }

    @Override // com.xiaomi.push.ga
    public void a(fz fzVar) {
        ga gaVar = this.f23787a;
        if (gaVar != null) {
            gaVar.a(fzVar);
        }
    }

    @Override // com.xiaomi.push.ga
    public void a(String str, Map<String, Object> map) {
        ga gaVar = this.f23787a;
        if (gaVar != null) {
            gaVar.a(str, map);
        }
    }

    public void a(String str, Object obj) {
        if (this.f23787a != null) {
            HashMap map = new HashMap();
            map.put(KsProperty.Key, str);
            map.put("package", obj != null ? String.valueOf(obj) : "");
            this.f23787a.a("rd_event", map);
        }
    }
}
