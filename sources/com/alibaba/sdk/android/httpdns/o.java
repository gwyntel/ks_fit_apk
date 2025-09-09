package com.alibaba.sdk.android.httpdns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class o {

    /* renamed from: d, reason: collision with root package name */
    private String[] f8881d;
    private boolean enabled;

    o(String str) throws JSONException {
        this.enabled = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            i.d("StartIp Schedule center response:" + jSONObject.toString());
            if (jSONObject.has("service_status")) {
                this.enabled = !jSONObject.getString("service_status").equals("disable");
            }
            if (jSONObject.has("service_ip")) {
                JSONArray jSONArray = jSONObject.getJSONArray("service_ip");
                this.f8881d = new String[jSONArray.length()];
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    this.f8881d[i2] = (String) jSONArray.get(i2);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String[] b() {
        return this.f8881d;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
