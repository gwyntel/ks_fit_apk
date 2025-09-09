package com.huawei.hms.hatool;

import android.os.Build;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class y0 extends t0 {

    /* renamed from: f, reason: collision with root package name */
    String f16502f;

    /* renamed from: g, reason: collision with root package name */
    String f16503g;

    /* renamed from: h, reason: collision with root package name */
    private String f16504h;

    @Override // com.huawei.hms.hatool.o1
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("_rom_ver", this.f16504h);
        jSONObject.put("_emui_ver", this.f16485a);
        jSONObject.put("_model", Build.MODEL);
        jSONObject.put("_mcc", this.f16502f);
        jSONObject.put("_mnc", this.f16503g);
        jSONObject.put("_package_name", this.f16486b);
        jSONObject.put("_app_ver", this.f16487c);
        jSONObject.put("_lib_ver", "2.2.0.314");
        jSONObject.put("_channel", this.f16488d);
        jSONObject.put("_lib_name", "hianalytics");
        jSONObject.put("_oaid_tracking_flag", this.f16489e);
        return jSONObject;
    }

    public void f(String str) {
        this.f16502f = str;
    }

    public void g(String str) {
        this.f16503g = str;
    }

    public void h(String str) {
        this.f16504h = str;
    }
}
