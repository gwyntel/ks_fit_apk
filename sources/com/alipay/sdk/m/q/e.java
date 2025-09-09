package com.alipay.sdk.m.q;

import android.content.Context;
import com.alipay.sdk.m.o.a;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class e extends com.alipay.sdk.m.p.e {
    @Override // com.alipay.sdk.m.p.e
    public String a(com.alipay.sdk.m.s.a aVar, String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.m.p.e
    public boolean c() {
        return false;
    }

    @Override // com.alipay.sdk.m.p.e
    public JSONObject a() {
        return null;
    }

    @Override // com.alipay.sdk.m.p.e
    public Map<String, String> a(boolean z2, String str) {
        return new HashMap();
    }

    @Override // com.alipay.sdk.m.p.e
    public com.alipay.sdk.m.p.b a(com.alipay.sdk.m.s.a aVar, Context context, String str) throws Throwable {
        com.alipay.sdk.m.u.e.d(com.alipay.sdk.m.l.a.f9433z, "mdap post");
        byte[] bArrA = com.alipay.sdk.m.n.b.a(str.getBytes(Charset.forName("UTF-8")));
        HashMap map = new HashMap();
        map.put("utdId", com.alipay.sdk.m.s.b.d().c());
        map.put("logHeader", "RAW");
        map.put("bizCode", com.alipay.sdk.m.u.e.f9763b);
        map.put(AlinkConstants.KEY_PRODUCT_ID, "alipaysdk_android");
        map.put("Content-Encoding", "Gzip");
        map.put("productVersion", "15.8.10");
        a.b bVarA = com.alipay.sdk.m.o.a.a(context, new a.C0051a(com.alipay.sdk.m.l.a.f9411d, map, bArrA));
        com.alipay.sdk.m.u.e.d(com.alipay.sdk.m.l.a.f9433z, "mdap got " + bVarA);
        if (bVarA != null) {
            boolean zA = com.alipay.sdk.m.p.e.a(bVarA);
            try {
                byte[] bArrB = bVarA.f9597c;
                if (zA) {
                    bArrB = com.alipay.sdk.m.n.b.b(bArrB);
                }
                return new com.alipay.sdk.m.p.b("", new String(bArrB, Charset.forName("UTF-8")));
            } catch (Exception e2) {
                com.alipay.sdk.m.u.e.a(e2);
                return null;
            }
        }
        throw new RuntimeException("Response is null");
    }
}
