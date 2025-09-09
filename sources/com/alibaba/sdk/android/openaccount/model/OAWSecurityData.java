package com.alibaba.sdk.android.openaccount.model;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OAWSecurityData {
    public String apdId;

    /* renamed from: t, reason: collision with root package name */
    public String f8929t;
    public String umidToken;
    public String wua;

    public String toString() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("wua", this.wua);
            jSONObject.put("apdId", this.apdId);
            jSONObject.put("umidToken", this.umidToken);
            jSONObject.put("t", this.f8929t);
        } catch (Exception unused) {
        }
        return jSONObject.toString();
    }
}
