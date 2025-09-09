package com.xiaomi.push;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class cy {

    /* renamed from: a, reason: collision with root package name */
    private int f23555a;

    /* renamed from: a, reason: collision with other field name */
    private long f255a;

    /* renamed from: a, reason: collision with other field name */
    private String f256a;

    /* renamed from: b, reason: collision with root package name */
    private long f23556b;

    /* renamed from: c, reason: collision with root package name */
    private long f23557c;

    public cy() {
        this(0, 0L, 0L, null);
    }

    public int a() {
        return this.f23555a;
    }

    public cy(int i2, long j2, long j3, Exception exc) {
        this.f23555a = i2;
        this.f255a = j2;
        this.f23557c = j3;
        this.f23556b = System.currentTimeMillis();
        if (exc != null) {
            this.f256a = exc.getClass().getSimpleName();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public JSONObject m258a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cost", this.f255a);
        jSONObject.put("size", this.f23557c);
        jSONObject.put("ts", this.f23556b);
        jSONObject.put("wt", this.f23555a);
        jSONObject.put("expt", this.f256a);
        return jSONObject;
    }

    public cy a(JSONObject jSONObject) {
        this.f255a = jSONObject.getLong("cost");
        this.f23557c = jSONObject.getLong("size");
        this.f23556b = jSONObject.getLong("ts");
        this.f23555a = jSONObject.getInt("wt");
        this.f256a = jSONObject.optString("expt");
        return this;
    }
}
