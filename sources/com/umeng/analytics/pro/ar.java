package com.umeng.analytics.pro;

import com.umeng.commonsdk.service.UMGlobalContext;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ar implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public static final String f21338a = "https://ucc.umeng.com/v2/inn/fetch";

    /* renamed from: b, reason: collision with root package name */
    private String f21339b;

    /* renamed from: c, reason: collision with root package name */
    private String f21340c;

    /* renamed from: d, reason: collision with root package name */
    private String f21341d;

    public ar(String str, JSONObject jSONObject, String str2) {
        this.f21339b = str;
        this.f21340c = jSONObject.toString();
        this.f21341d = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject jSONObject = null;
        try {
            byte[] bArrA = ap.a(this.f21339b, this.f21340c);
            if (bArrA != null) {
                JSONObject jSONObject2 = new JSONObject(new String(bArrA));
                JSONObject jSONObject3 = new JSONObject();
                try {
                    jSONObject3.put("sourceIucc", this.f21341d);
                    jSONObject3.put("config", jSONObject2);
                } catch (Throwable unused) {
                }
                jSONObject = jSONObject3;
            }
        } catch (Throwable unused2) {
        }
        com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 102, com.umeng.ccg.d.a(), jSONObject);
    }
}
