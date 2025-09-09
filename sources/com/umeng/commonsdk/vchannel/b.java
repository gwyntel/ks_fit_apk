package com.umeng.commonsdk.vchannel;

import android.content.Context;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private String f22594b;

    /* renamed from: a, reason: collision with root package name */
    private String f22593a = "_$unknown";

    /* renamed from: c, reason: collision with root package name */
    private long f22595c = 0;

    /* renamed from: d, reason: collision with root package name */
    private long f22596d = 0;

    /* renamed from: e, reason: collision with root package name */
    private String f22597e = a.f22592j;

    /* renamed from: f, reason: collision with root package name */
    private Map<String, Object> f22598f = null;

    public b(Context context) {
        this.f22594b = UMGlobalContext.getInstance(context).getProcessName(context);
    }

    public String a() {
        return this.f22593a;
    }

    public long b() {
        return this.f22595c;
    }

    public Map<String, Object> c() {
        return this.f22598f;
    }

    public JSONObject d() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", this.f22593a);
            jSONObject.put("pn", this.f22594b);
            jSONObject.put("ds", this.f22596d);
            jSONObject.put("ts", this.f22595c);
            Map<String, Object> map = this.f22598f;
            if (map != null && map.size() > 0) {
                for (String str : this.f22598f.keySet()) {
                    jSONObject.put(str, this.f22598f.get(str));
                }
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(this.f22597e, jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("ekv", jSONArray2);
            return jSONObject3;
        } catch (Throwable unused) {
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id:" + this.f22593a + ",");
        sb.append("pn:" + this.f22594b + ",");
        sb.append("ts:" + this.f22595c + ",");
        Map<String, Object> map = this.f22598f;
        if (map != null && map.size() > 0) {
            for (String str : this.f22598f.keySet()) {
                Object obj = this.f22598f.get(str);
                sb.append(obj == null ? str + ": null," : str + ": " + obj.toString() + ",");
            }
        }
        sb.append("ds:" + this.f22596d + "]");
        return sb.toString();
    }

    public void a(String str) {
        this.f22593a = str;
    }

    public void a(long j2) {
        this.f22595c = j2;
    }

    public void a(Map<String, Object> map) {
        this.f22598f = map;
    }
}
