package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {
    public static b a(String str) {
        try {
            if (!com.alipay.sdk.m.z.a.a(str)) {
                JSONObject jSONObject = new JSONObject(str);
                return new b(jSONObject.optString("apdid"), jSONObject.optString("deviceInfoHash"), jSONObject.optString(com.alipay.sdk.m.t.a.f9743k));
            }
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
        }
        return null;
    }

    public static synchronized b b() {
        String strA = com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v3", "wxcasxx");
        if (com.alipay.sdk.m.z.a.a(strA)) {
            return null;
        }
        return a(strA);
    }

    public static synchronized b c(Context context) {
        String strA = com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v3", "deviceid");
        if (com.alipay.sdk.m.z.a.a(strA)) {
            return null;
        }
        return a(strA);
    }

    public static synchronized void a() {
    }

    public static synchronized b b(Context context) {
        String strA;
        try {
            strA = com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v3", "deviceid");
            if (com.alipay.sdk.m.z.a.a(strA)) {
                strA = com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v3", "wxcasxx");
            }
        } catch (Throwable th) {
            throw th;
        }
        return a(strA);
    }

    public static synchronized void a(Context context) {
        com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v3", "deviceid", "");
        com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v3", "wxcasxx", "");
    }

    public static synchronized void a(Context context, b bVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("apdid", bVar.f9062a);
            jSONObject.put("deviceInfoHash", bVar.f9063b);
            jSONObject.put(com.alipay.sdk.m.t.a.f9743k, bVar.f9064c);
            String string = jSONObject.toString();
            com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v3", "deviceid", string);
            com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v3", "wxcasxx", string);
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
        }
    }
}
