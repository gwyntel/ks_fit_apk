package com.huawei.hms.hatool;

import com.aliyun.alink.business.devicecenter.utils.EncryptUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f0 extends k0 {

    /* renamed from: g, reason: collision with root package name */
    private String f16358g = "";

    @Override // com.huawei.hms.hatool.o1
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("protocol_version", "3");
        jSONObject.put("compress_mode", "1");
        jSONObject.put("serviceid", this.f16412d);
        jSONObject.put("appid", this.f16409a);
        jSONObject.put(EncryptUtils.SIGN_METHOD_HMAC, this.f16358g);
        jSONObject.put("chifer", this.f16414f);
        jSONObject.put(com.alipay.sdk.m.t.a.f9743k, this.f16410b);
        jSONObject.put("servicetag", this.f16411c);
        jSONObject.put("requestid", this.f16413e);
        return jSONObject;
    }

    public void g(String str) {
        this.f16358g = str;
    }
}
