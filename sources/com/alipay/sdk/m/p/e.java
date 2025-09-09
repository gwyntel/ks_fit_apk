package com.alipay.sdk.m.p;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alipay.sdk.m.o.a;
import com.alipay.sdk.m.u.m;
import com.alipay.sdk.m.u.n;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class e {

    /* renamed from: c, reason: collision with root package name */
    public static final String f9608c = "msp-gzip";

    /* renamed from: d, reason: collision with root package name */
    public static final String f9609d = "Msp-Param";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9610e = "Operation-Type";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9611f = "content-type";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9612g = "Version";

    /* renamed from: h, reason: collision with root package name */
    public static final String f9613h = "AppId";

    /* renamed from: i, reason: collision with root package name */
    public static final String f9614i = "des-mode";

    /* renamed from: j, reason: collision with root package name */
    public static final String f9615j = "namespace";

    /* renamed from: k, reason: collision with root package name */
    public static final String f9616k = "api_name";

    /* renamed from: l, reason: collision with root package name */
    public static final String f9617l = "api_version";

    /* renamed from: m, reason: collision with root package name */
    public static final String f9618m = "data";

    /* renamed from: n, reason: collision with root package name */
    public static final String f9619n = "params";

    /* renamed from: o, reason: collision with root package name */
    public static final String f9620o = "public_key";

    /* renamed from: p, reason: collision with root package name */
    public static final String f9621p = "device";

    /* renamed from: q, reason: collision with root package name */
    public static final String f9622q = "action";

    /* renamed from: r, reason: collision with root package name */
    public static final String f9623r = "type";

    /* renamed from: s, reason: collision with root package name */
    public static final String f9624s = "method";

    /* renamed from: a, reason: collision with root package name */
    public boolean f9625a = true;

    /* renamed from: b, reason: collision with root package name */
    public boolean f9626b = true;

    public Map<String, String> a(boolean z2, String str) {
        HashMap map = new HashMap();
        map.put(f9608c, String.valueOf(z2));
        map.put(f9610e, "alipay.msp.cashier.dispatch.bytes");
        map.put("content-type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
        map.put(f9612g, "2.0");
        map.put(f9613h, "TAOBAO");
        map.put(f9609d, a.a(str));
        map.put(f9614i, "CBC");
        return map;
    }

    public abstract JSONObject a() throws JSONException;

    public String b() {
        return "4.9.0";
    }

    public abstract boolean c();

    public String a(com.alipay.sdk.m.s.a aVar) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        map.put("device", Build.MODEL);
        map.put("namespace", "com.alipay.mobilecashier");
        map.put("api_name", "com.alipay.mcpay");
        map.put(f9617l, b());
        return a(aVar, map, new HashMap<>());
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put("method", str2);
        jSONObject.put("action", jSONObject2);
        return jSONObject;
    }

    public String a(com.alipay.sdk.m.s.a aVar, String str, JSONObject jSONObject) throws JSONException {
        com.alipay.sdk.m.s.b bVarD = com.alipay.sdk.m.s.b.d();
        com.alipay.sdk.m.t.a aVarA = com.alipay.sdk.m.t.a.a(bVarD.b());
        JSONObject jSONObjectA = com.alipay.sdk.m.u.d.a(new JSONObject(), jSONObject);
        try {
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9437d, str);
            jSONObjectA.put("tid", aVarA.d());
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9435b, bVarD.a().a(aVar, aVarA, c()));
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9438e, n.a(aVar, bVarD.b(), com.alipay.sdk.m.j.a.f9316d, false));
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9439f, n.h(bVarD.b()));
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9441h, com.alipay.sdk.m.l.a.f9413f);
            jSONObjectA.put("utdid", bVarD.c());
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9443j, aVarA.c());
            jSONObjectA.put(com.alipay.sdk.m.l.b.f9444k, com.alipay.sdk.m.m.b.b(bVarD.b()));
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "BodyErr", th);
            com.alipay.sdk.m.u.e.a(th);
        }
        return jSONObjectA.toString();
    }

    public static boolean a(a.b bVar) {
        return Boolean.valueOf(a(bVar, f9608c)).booleanValue();
    }

    public static String a(a.b bVar, String str) {
        Map<String, List<String>> map;
        List<String> list;
        if (bVar == null || str == null || (map = bVar.f9595a) == null || (list = map.get(str)) == null) {
            return null;
        }
        return TextUtils.join(",", list);
    }

    public String a(com.alipay.sdk.m.s.a aVar, HashMap<String, String> map, HashMap<String, String> map2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jSONObject2.put(entry.getKey(), entry.getValue());
            }
        }
        if (map2 != null) {
            JSONObject jSONObject3 = new JSONObject();
            for (Map.Entry<String, String> entry2 : map2.entrySet()) {
                jSONObject3.put(entry2.getKey(), entry2.getValue());
            }
            jSONObject2.put("params", jSONObject3);
        }
        jSONObject.put("data", jSONObject2);
        return jSONObject.toString();
    }

    public static boolean a(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("data");
            if (!jSONObject.has("params")) {
                return false;
            }
            String strOptString = jSONObject.getJSONObject("params").optString(f9620o, null);
            if (TextUtils.isEmpty(strOptString)) {
                return false;
            }
            com.alipay.sdk.m.m.b.a(strOptString);
            return true;
        } catch (JSONException e2) {
            com.alipay.sdk.m.u.e.a(e2);
            return false;
        }
    }

    public b a(com.alipay.sdk.m.s.a aVar, Context context) throws Throwable {
        return a(aVar, context, "");
    }

    public b a(com.alipay.sdk.m.s.a aVar, Context context, String str) throws Throwable {
        return a(aVar, context, str, m.b(context));
    }

    public b a(com.alipay.sdk.m.s.a aVar, Context context, String str, String str2) throws Throwable {
        return a(aVar, context, str, str2, true);
    }

    public b a(com.alipay.sdk.m.s.a aVar, Context context, String str, String str2, boolean z2) throws Throwable {
        com.alipay.sdk.m.u.e.b(com.alipay.sdk.m.l.a.f9433z, "Packet: " + str2);
        c cVar = new c(this.f9626b);
        b bVar = new b(a(aVar), a(aVar, str, a()));
        Map<String, String> mapA = a(false, str);
        d dVarA = cVar.a(bVar, this.f9625a, mapA.get("iSr"));
        a.b bVarA = com.alipay.sdk.m.o.a.a(context, new a.C0051a(str2, a(dVarA.b(), str), dVarA.a()));
        if (bVarA != null) {
            b bVarA2 = cVar.a(new d(a(bVarA), bVarA.f9597c), mapA.get("iSr"));
            return (bVarA2 != null && a(bVarA2.b()) && z2) ? a(aVar, context, str, str2, false) : bVarA2;
        }
        throw new RuntimeException("Response is null.");
    }
}
