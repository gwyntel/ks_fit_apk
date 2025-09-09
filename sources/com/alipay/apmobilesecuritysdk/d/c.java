package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.e.f;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class c {
    public static Map<String, String> a(Context context) throws JSONException {
        com.alipay.sdk.m.a0.b bVarB = com.alipay.sdk.m.a0.b.b();
        HashMap map = new HashMap();
        f fVarA = com.alipay.apmobilesecuritysdk.e.e.a(context);
        String strA = bVarB.a(context);
        String strB = bVarB.b(context);
        String strL = com.alipay.sdk.m.a0.b.l(context);
        if (fVarA != null) {
            if (com.alipay.sdk.m.z.a.a(strA)) {
                strA = fVarA.a();
            }
            if (com.alipay.sdk.m.z.a.a(strB)) {
                strB = fVarA.b();
            }
            if (com.alipay.sdk.m.z.a.a(strL)) {
                strL = fVarA.e();
            }
        }
        f fVar = new f(strA, strB, "", "", strL);
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constants.KEY_IMEI, fVar.a());
                jSONObject.put(Constants.KEY_IMSI, fVar.b());
                jSONObject.put(AlinkConstants.KEY_MAC, fVar.c());
                jSONObject.put("bluetoothmac", fVar.d());
                jSONObject.put("gsi", fVar.e());
                String string = jSONObject.toString();
                com.alipay.apmobilesecuritysdk.f.a.a("device_feature_file_name", "device_feature_file_key", string);
                com.alipay.apmobilesecuritysdk.f.a.a(context, "device_feature_prefs_name", "device_feature_prefs_key", string);
            } catch (Exception e2) {
                com.alipay.apmobilesecuritysdk.c.a.a(e2);
            }
        }
        map.put("AD1", strA);
        map.put("AD2", strB);
        map.put("AD3", com.alipay.sdk.m.a0.b.g(context));
        map.put("AD5", com.alipay.sdk.m.a0.b.i(context));
        map.put("AD6", com.alipay.sdk.m.a0.b.j(context));
        map.put("AD7", com.alipay.sdk.m.a0.b.k(context));
        map.put("AD9", bVarB.c(context));
        map.put("AD10", strL);
        map.put("AD11", com.alipay.sdk.m.a0.b.e());
        map.put("AD12", bVarB.a());
        map.put("AD13", com.alipay.sdk.m.a0.b.f());
        map.put("AD14", com.alipay.sdk.m.a0.b.h());
        map.put("AD15", com.alipay.sdk.m.a0.b.i());
        map.put("AD16", com.alipay.sdk.m.a0.b.j());
        map.put("AD17", "");
        map.put("AD19", com.alipay.sdk.m.a0.b.m(context));
        map.put("AD20", com.alipay.sdk.m.a0.b.k());
        map.put("AD22", "");
        map.put("AD23", com.alipay.sdk.m.a0.b.n(context));
        map.put("AD24", com.alipay.sdk.m.z.a.g(com.alipay.sdk.m.a0.b.h(context)));
        map.put("AD26", com.alipay.sdk.m.a0.b.f(context));
        map.put("AD27", com.alipay.sdk.m.a0.b.p());
        map.put("AD28", com.alipay.sdk.m.a0.b.r());
        map.put("AD29", com.alipay.sdk.m.a0.b.t());
        map.put("AD30", com.alipay.sdk.m.a0.b.q());
        map.put("AD31", com.alipay.sdk.m.a0.b.s());
        map.put("AD32", com.alipay.sdk.m.a0.b.n());
        map.put("AD33", com.alipay.sdk.m.a0.b.o());
        map.put("AD34", com.alipay.sdk.m.a0.b.p(context));
        map.put("AD35", com.alipay.sdk.m.a0.b.q(context));
        map.put("AD36", com.alipay.sdk.m.a0.b.o(context));
        map.put("AD37", com.alipay.sdk.m.a0.b.m());
        map.put("AD38", com.alipay.sdk.m.a0.b.l());
        map.put("AD39", com.alipay.sdk.m.a0.b.d(context));
        map.put("AD40", com.alipay.sdk.m.a0.b.e(context));
        map.put("AD41", com.alipay.sdk.m.a0.b.c());
        map.put("AD42", com.alipay.sdk.m.a0.b.d());
        return map;
    }
}
