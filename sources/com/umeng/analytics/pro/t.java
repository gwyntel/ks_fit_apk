package com.umeng.analytics.pro;

import android.content.Context;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.service.UMGlobalContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    private static final int f21918a = 0;

    /* renamed from: b, reason: collision with root package name */
    private static final int f21919b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static final int f21920c = 2;

    /* renamed from: d, reason: collision with root package name */
    private static final int f21921d = 3;

    /* renamed from: e, reason: collision with root package name */
    private final long f21922e;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final t f21923a = new t();

        private a() {
        }
    }

    public static t a() {
        return a.f21923a;
    }

    private JSONArray c() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", aa.a().d(UMGlobalContext.getAppContext(null)));
            jSONObject.put(f.f21694p, jCurrentTimeMillis);
            jSONArray.put(jSONObject);
        } catch (JSONException unused) {
        }
        return jSONArray;
    }

    public void b(JSONObject jSONObject, Context context) {
        int iA = a(context);
        if (iA == 1) {
            if (jSONObject.has(f.L)) {
                jSONObject.remove(f.L);
            }
            if (jSONObject.has(f.f21692n)) {
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray(f.f21692n);
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        if (jSONObject2.has(f.aA)) {
                            jSONObject2.remove(f.aA);
                        }
                        if (jSONObject2.has(f.aB)) {
                            jSONObject2.remove(f.aB);
                        }
                    }
                } catch (JSONException unused) {
                }
            }
            k.a(context).a(false, true);
            return;
        }
        if (iA == 2) {
            if (jSONObject.has(f.L)) {
                jSONObject.remove(f.L);
            }
            if (jSONObject.has(f.f21692n)) {
                jSONObject.remove(f.f21692n);
            }
            try {
                jSONObject.put(f.f21692n, c());
            } catch (Exception unused2) {
            }
            k.a(context).a(false, true);
            return;
        }
        if (iA == 3) {
            if (jSONObject.has(f.L)) {
                jSONObject.remove(f.L);
            }
            jSONObject.remove(f.f21692n);
            k.a(context).a(false, true);
        }
    }

    private t() {
        this.f21922e = 60000L;
    }

    public int a(Context context) {
        return Integer.valueOf(UMEnvelopeBuild.imprintProperty(context, "defcon", String.valueOf(0))).intValue();
    }

    private void a(JSONObject jSONObject, boolean z2) {
        if (!z2 && jSONObject.has(f.f21692n)) {
            jSONObject.remove(f.f21692n);
        }
        if (jSONObject.has(f.L)) {
            jSONObject.remove(f.L);
        }
        if (jSONObject.has("error")) {
            jSONObject.remove("error");
        }
        if (jSONObject.has("ekv")) {
            jSONObject.remove("ekv");
        }
        if (jSONObject.has(f.Z)) {
            jSONObject.remove(f.Z);
        }
        if (jSONObject.has(f.L)) {
            jSONObject.remove(f.L);
        }
        if (jSONObject.has("userlevel")) {
            jSONObject.remove("userlevel");
        }
    }

    public void a(JSONObject jSONObject, Context context) throws JSONException {
        int iA = a(context);
        if (iA == 1) {
            a(jSONObject, true);
            k.a(context).b(false, true);
        } else {
            if (iA == 2) {
                jSONObject.remove(f.f21692n);
                try {
                    jSONObject.put(f.f21692n, b());
                } catch (Exception unused) {
                }
                a(jSONObject, true);
                k.a(context).b(false, true);
                return;
            }
            if (iA == 3) {
                a(jSONObject, false);
                k.a(context).b(false, true);
            }
        }
    }

    private JSONArray b() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", aa.a().a(UMGlobalContext.getAppContext(null)));
            jSONObject.put(f.f21694p, jCurrentTimeMillis);
            jSONObject.put(f.f21695q, jCurrentTimeMillis + 60000);
            jSONObject.put("duration", 60000L);
            jSONArray.put(jSONObject);
        } catch (JSONException unused) {
        }
        return jSONArray;
    }
}
