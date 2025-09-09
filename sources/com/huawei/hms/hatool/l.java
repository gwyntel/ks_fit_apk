package com.huawei.hms.hatool;

import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.taobao.accs.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class l extends t {

    /* renamed from: b, reason: collision with root package name */
    private String f16415b = "";

    /* renamed from: c, reason: collision with root package name */
    private String f16416c = "";

    /* renamed from: d, reason: collision with root package name */
    private String f16417d = "";

    /* renamed from: e, reason: collision with root package name */
    private String f16418e = "";

    /* renamed from: f, reason: collision with root package name */
    protected String f16419f = "";

    /* renamed from: g, reason: collision with root package name */
    private String f16420g;

    @Override // com.huawei.hms.hatool.o1
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("androidid", this.f16484a);
        jSONObject.put("oaid", this.f16420g);
        jSONObject.put(DeviceCommonConstants.KEY_DEVICE_ID, this.f16419f);
        jSONObject.put("upid", this.f16418e);
        jSONObject.put(Constants.KEY_IMEI, this.f16415b);
        jSONObject.put("sn", this.f16416c);
        jSONObject.put("udid", this.f16417d);
        return jSONObject;
    }

    public void b(String str) {
        this.f16415b = str;
    }

    public void c(String str) {
        this.f16420g = str;
    }

    public void d(String str) {
        this.f16416c = str;
    }

    public void e(String str) {
        this.f16417d = str;
    }

    public void f(String str) {
        this.f16418e = str;
    }

    public void g(String str) {
        this.f16419f = str;
    }
}
