package com.umeng.ut.b.b;

import org.json.JSONObject;

/* loaded from: classes4.dex */
class b {

    /* renamed from: d, reason: collision with root package name */
    int f23011d = -1;

    b() {
    }

    static boolean a(int i2) {
        return i2 >= 0 && i2 != 10012;
    }

    static b a(String str) {
        JSONObject jSONObject;
        b bVar = new b();
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has("code")) {
                bVar.f23011d = jSONObject2.getInt("code");
            }
            if (jSONObject2.has("data") && (jSONObject = jSONObject2.getJSONObject("data")) != null && jSONObject.has("id") && jSONObject.has("d_ts")) {
                d.a(com.umeng.ut.a.a.a().m82a()).a(jSONObject.getString("id"), jSONObject.getLong("d_ts"));
            }
            com.umeng.ut.a.c.e.m85a("BizResponse", "content", str);
        } catch (Throwable th) {
            com.umeng.ut.a.c.e.m85a("", th.toString());
        }
        return bVar;
    }
}
