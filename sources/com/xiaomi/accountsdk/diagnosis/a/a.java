package com.xiaomi.accountsdk.diagnosis.a;

import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public String f23296a;

    /* renamed from: b, reason: collision with root package name */
    public String f23297b;

    public static a a(JSONObject jSONObject) {
        a aVar = new a();
        aVar.f23297b = jSONObject.optString("diagnosisDomain", null);
        aVar.f23296a = jSONObject.optString("dataCenterZone", null);
        return aVar;
    }
}
