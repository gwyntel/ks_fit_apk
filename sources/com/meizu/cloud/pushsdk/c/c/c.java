package com.meizu.cloud.pushsdk.c.c;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final int f19190a;

    /* renamed from: b, reason: collision with root package name */
    private final String f19191b;

    public c(int i2, String str) {
        this.f19190a = i2;
        this.f19191b = str;
    }

    public String toString() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", this.f19190a);
            jSONObject.put("body", this.f19191b);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return "[NetResponse] " + jSONObject.toString();
    }
}
