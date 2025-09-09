package com.alipay.sdk.m.q;

import android.content.Context;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class d extends com.alipay.sdk.m.p.e {

    /* renamed from: t, reason: collision with root package name */
    public static final String f9675t = "log_v";

    @Override // com.alipay.sdk.m.p.e
    public String a(com.alipay.sdk.m.s.a aVar, String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.m.p.e
    public boolean c() {
        return false;
    }

    @Override // com.alipay.sdk.m.p.e
    public JSONObject a() throws JSONException {
        return null;
    }

    @Override // com.alipay.sdk.m.p.e
    public Map<String, String> a(boolean z2, String str) {
        HashMap map = new HashMap();
        map.put(com.alipay.sdk.m.p.e.f9608c, String.valueOf(z2));
        map.put("content-type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
        map.put(com.alipay.sdk.m.p.e.f9614i, "CBC");
        return map;
    }

    @Override // com.alipay.sdk.m.p.e
    public String a(com.alipay.sdk.m.s.a aVar) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        map.put("api_name", "/sdk/log");
        map.put(com.alipay.sdk.m.p.e.f9617l, "1.0.0");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put(f9675t, "1.0");
        return a(aVar, map, map2);
    }

    @Override // com.alipay.sdk.m.p.e
    public com.alipay.sdk.m.p.b a(com.alipay.sdk.m.s.a aVar, Context context, String str) throws Throwable {
        return a(aVar, context, str, com.alipay.sdk.m.l.a.f9410c, true);
    }
}
