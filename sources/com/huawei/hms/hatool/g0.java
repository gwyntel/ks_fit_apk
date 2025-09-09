package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class g0 {

    /* renamed from: c, reason: collision with root package name */
    private static g0 f16359c;

    /* renamed from: a, reason: collision with root package name */
    private Context f16360a;

    /* renamed from: b, reason: collision with root package name */
    private final Object f16361b = new Object();

    private g0() {
    }

    public static g0 a() {
        if (f16359c == null) {
            b();
        }
        return f16359c;
    }

    private static synchronized void b() {
        if (f16359c == null) {
            f16359c = new g0();
        }
    }

    private JSONObject a(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map != null) {
            try {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            } catch (JSONException unused) {
                v.b("hmsSdk", "Exception occured when transferring bundle to json");
            }
        }
        return jSONObject;
    }

    public void b(String str, int i2, String str2, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        e.a().a(str, i2, str2, a(linkedHashMap), System.currentTimeMillis());
    }

    public void a(Context context) {
        synchronized (this.f16361b) {
            try {
                if (this.f16360a != null) {
                    return;
                }
                this.f16360a = context;
                e.a().a(context);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(String str, int i2) {
        e.a().a(str, i2);
    }

    public void a(String str, int i2, String str2, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        e.a().a(str, i2, str2, a(linkedHashMap));
    }

    public void a(String str, Context context, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("_constants", str3);
            e.a().a(str, 0, str2, jSONObject);
        } catch (JSONException unused) {
            v.f("hmsSdk", "onEvent():JSON structure Exception!");
        }
    }
}
