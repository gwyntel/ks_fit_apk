package com.alipay.sdk.m.u;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alipay.sdk.m.k.a;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9754a = "ap_req";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9755b = "ap_args";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9756c = "ap_resp";

    public static com.alipay.sdk.m.g.a a() {
        try {
            try {
                return com.alipay.sdk.m.h.a.a("NP", System.currentTimeMillis(), new com.alipay.sdk.m.h.c(com.alipay.sdk.m.s.b.d().c()), (short) a.c.a(com.alipay.sdk.m.s.b.d().b()), new com.alipay.sdk.m.h.f());
            } catch (Exception unused) {
                return com.alipay.sdk.m.h.a.c();
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public static HashMap<String, String> a(com.alipay.sdk.m.s.a aVar) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        try {
            com.alipay.sdk.m.g.a aVarA = a();
            JSONObject jSONObject = new JSONObject();
            Context contextA = aVar != null ? aVar.a() : null;
            if (contextA == null) {
                contextA = com.alipay.sdk.m.s.b.d().b().getApplicationContext();
            }
            String strA = n.a(aVar, contextA);
            String strB = com.alipay.sdk.m.w.b.b(aVar, contextA);
            jSONObject.put("ap_q", aVarA != null ? aVarA.a() : "");
            jSONObject.put(com.alipay.sdk.m.s.a.f9721x, aVar != null ? aVar.f9727d : "");
            jSONObject.put("u_pd", String.valueOf(n.g()));
            jSONObject.put("u_lk", String.valueOf(n.e(n.b())));
            jSONObject.put("u_pi", String.valueOf(aVar != null ? aVar.f9730g : OpenAccountUIConstants.UNDER_LINE));
            jSONObject.put("u_fu", strA);
            jSONObject.put("u_oi", strB);
            map.put(f9754a, jSONObject.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(aVarA != null ? aVarA.a() : "");
            sb.append("|");
            sb.append(strA);
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "ap_q", sb.toString());
        } catch (Exception e2) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "APMEx1", e2);
        }
        return map;
    }

    public static JSONObject a(com.alipay.sdk.m.s.a aVar, JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String strOptString = jSONObject.optString(f9756c);
        try {
            if (TextUtils.isEmpty(strOptString)) {
                return null;
            }
            return new JSONObject(strOptString);
        } catch (JSONException e2) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "APMEx2", e2);
            return null;
        }
    }

    public static void a(com.alipay.sdk.m.s.a aVar, JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (jSONObject == null || jSONObject2 == null) {
            return;
        }
        try {
            jSONObject.putOpt(f9755b, jSONObject2);
        } catch (JSONException e2) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "APMEx2", e2);
        }
    }

    public static void a(com.alipay.sdk.m.s.a aVar, HashMap<String, String> map) {
        JSONObject jSONObjectA = com.alipay.sdk.m.m.a.z().a();
        if (map == null || jSONObjectA == null) {
            return;
        }
        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "ap_r", jSONObjectA.optString("ap_r"));
        map.putAll(n.a(jSONObjectA));
    }
}
