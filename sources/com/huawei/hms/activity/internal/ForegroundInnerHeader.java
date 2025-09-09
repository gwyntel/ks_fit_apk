package com.huawei.hms.activity.internal;

import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ForegroundInnerHeader {

    /* renamed from: a, reason: collision with root package name */
    private int f15741a;

    /* renamed from: b, reason: collision with root package name */
    private String f15742b;

    /* renamed from: c, reason: collision with root package name */
    private String f15743c;

    public void fromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f15741a = JsonUtil.getIntValue(jSONObject, "apkVersion");
            this.f15742b = JsonUtil.getStringValue(jSONObject, "action");
            this.f15743c = JsonUtil.getStringValue(jSONObject, "responseCallbackKey");
        } catch (JSONException e2) {
            HMSLog.e("ForegroundInnerHeader", "fromJson failed: " + e2.getMessage());
        }
    }

    public String getAction() {
        return this.f15742b;
    }

    public int getApkVersion() {
        return this.f15741a;
    }

    public String getResponseCallbackKey() {
        return this.f15743c;
    }

    public void setAction(String str) {
        this.f15742b = str;
    }

    public void setApkVersion(int i2) {
        this.f15741a = i2;
    }

    public void setResponseCallbackKey(String str) {
        this.f15743c = str;
    }

    public String toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("apkVersion", this.f15741a);
            jSONObject.put("action", this.f15742b);
            jSONObject.put("responseCallbackKey", this.f15743c);
        } catch (JSONException e2) {
            HMSLog.e("ForegroundInnerHeader", "ForegroundInnerHeader toJson failed: " + e2.getMessage());
        }
        return jSONObject.toString();
    }

    public String toString() {
        return "apkVersion:" + this.f15741a + ", action:" + this.f15742b + ", responseCallbackKey:" + this.f15743c;
    }
}
