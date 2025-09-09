package com.ta.utdid2.device;

import com.ta.a.c.f;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class b {

    /* renamed from: d, reason: collision with root package name */
    int f20039d = -1;

    b() {
    }

    static boolean a(int i2) {
        return i2 >= 0 && i2 != 10012;
    }

    static b a(String str) throws JSONException {
        JSONObject jSONObject;
        b bVar = new b();
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has("code")) {
                bVar.f20039d = jSONObject2.getInt("code");
            }
            if (jSONObject2.has("data") && (jSONObject = jSONObject2.getJSONObject("data")) != null && jSONObject.has("utdid")) {
                String string = jSONObject.getString("utdid");
                if (d.m80c(string)) {
                    com.ta.a.b.e.a(string);
                }
            }
            f.m77a("BizResponse", "content", str);
        } catch (JSONException e2) {
            f.m77a("", e2.toString());
        }
        return bVar;
    }
}
