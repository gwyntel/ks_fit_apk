package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a0 implements g {

    /* renamed from: a, reason: collision with root package name */
    private Context f16313a = q0.i();

    /* renamed from: b, reason: collision with root package name */
    private String f16314b;

    /* renamed from: c, reason: collision with root package name */
    private JSONObject f16315c;

    /* renamed from: d, reason: collision with root package name */
    private String f16316d;

    /* renamed from: e, reason: collision with root package name */
    private String f16317e;

    /* renamed from: f, reason: collision with root package name */
    private String f16318f;

    /* renamed from: g, reason: collision with root package name */
    private String f16319g;

    /* renamed from: h, reason: collision with root package name */
    private Boolean f16320h;

    public a0(String str, JSONObject jSONObject, String str2, String str3, long j2) {
        this.f16314b = str;
        this.f16315c = jSONObject;
        this.f16316d = str2;
        this.f16317e = str3;
        this.f16318f = String.valueOf(j2);
        if (z.i(str2, "oper")) {
            p0 p0VarA = y.a().a(str2, j2);
            this.f16319g = p0VarA.a();
            this.f16320h = Boolean.valueOf(p0VarA.b());
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONArray jSONArray;
        v.c("hmsSdk", "Begin to run EventRecordTask...");
        int iH = q0.h();
        int iK = a1.k(this.f16316d, this.f16317e);
        if (c0.a(this.f16313a, "stat_v2_1", iH * 1048576)) {
            v.c("hmsSdk", "stat sp file reach max limited size, discard new event");
            e.a().a("", "alltype");
            return;
        }
        b1 b1Var = new b1();
        b1Var.b(this.f16314b);
        b1Var.a(this.f16315c.toString());
        b1Var.d(this.f16317e);
        b1Var.c(this.f16318f);
        b1Var.f(this.f16319g);
        Boolean bool = this.f16320h;
        b1Var.e(bool == null ? null : String.valueOf(bool));
        try {
            JSONObject jSONObjectD = b1Var.d();
            String strA = n1.a(this.f16316d, this.f16317e);
            String strA2 = d.a(this.f16313a, "stat_v2_1", strA, "");
            try {
                jSONArray = !TextUtils.isEmpty(strA2) ? new JSONArray(strA2) : new JSONArray();
            } catch (JSONException unused) {
                v.d("hmsSdk", "Cached data corrupted: stat_v2_1");
                jSONArray = new JSONArray();
            }
            jSONArray.put(jSONObjectD);
            d.b(this.f16313a, "stat_v2_1", strA, jSONArray.toString());
            if (jSONArray.toString().length() > iK * 1024) {
                e.a().a(this.f16316d, this.f16317e);
            }
        } catch (JSONException unused2) {
            v.e("hmsSdk", "eventRecord toJson error! The record failed.");
        }
    }
}
