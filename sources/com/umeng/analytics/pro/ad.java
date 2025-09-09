package com.umeng.analytics.pro;

import java.util.ArrayList;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ad extends ab {

    /* renamed from: a, reason: collision with root package name */
    private String f21287a;

    /* renamed from: b, reason: collision with root package name */
    private String f21288b;

    public ad(String str, ArrayList<ac> arrayList) {
        super(str, arrayList);
        this.f21287a = "";
        this.f21288b = "";
    }

    @Override // com.umeng.analytics.pro.ab, com.umeng.analytics.pro.aj
    public JSONObject a(String str, JSONObject jSONObject) {
        JSONObject jSONObjectA = super.a(str, jSONObject);
        if (jSONObjectA != null) {
            try {
                jSONObjectA.put("batch", this.f21287a);
                jSONObjectA.put("action", this.f21288b);
            } catch (Throwable unused) {
            }
        }
        return jSONObjectA;
    }

    @Override // com.umeng.analytics.pro.ab, com.umeng.analytics.pro.aj
    public void b(String str, JSONObject jSONObject) {
        super.b(str, jSONObject);
        if (jSONObject.has("action")) {
            d(jSONObject.optString("action"));
        }
        if (jSONObject.has("batch")) {
            c(jSONObject.optString("batch"));
        }
    }

    public void c(String str) {
        this.f21287a = str;
    }

    public String d() {
        return this.f21287a;
    }

    public String e() {
        return this.f21288b;
    }

    public void d(String str) {
        this.f21288b = str;
    }
}
