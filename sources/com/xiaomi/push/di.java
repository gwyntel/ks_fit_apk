package com.xiaomi.push;

import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class di implements Comparable<di> {

    /* renamed from: a, reason: collision with root package name */
    protected int f23578a;

    /* renamed from: a, reason: collision with other field name */
    private long f275a;

    /* renamed from: a, reason: collision with other field name */
    String f276a;

    /* renamed from: a, reason: collision with other field name */
    private final LinkedList<cy> f277a;

    public di() {
        this(null, 0);
    }

    protected synchronized void a(cy cyVar) {
        if (cyVar != null) {
            try {
                this.f277a.add(cyVar);
                int iA = cyVar.a();
                if (iA > 0) {
                    this.f23578a += cyVar.a();
                } else {
                    int i2 = 0;
                    for (int size = this.f277a.size() - 1; size >= 0 && this.f277a.get(size).a() < 0; size--) {
                        i2++;
                    }
                    this.f23578a += iA * i2;
                }
                if (this.f277a.size() > 30) {
                    this.f23578a -= this.f277a.remove().a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public String toString() {
        return this.f276a + ":" + this.f23578a;
    }

    public di(String str) {
        this(str, 0);
    }

    public di(String str, int i2) {
        this.f277a = new LinkedList<>();
        this.f275a = 0L;
        this.f276a = str;
        this.f23578a = i2;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(di diVar) {
        if (diVar == null) {
            return 1;
        }
        return diVar.f23578a - this.f23578a;
    }

    public synchronized JSONObject a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("tt", this.f275a);
            jSONObject.put("wt", this.f23578a);
            jSONObject.put("host", this.f276a);
            JSONArray jSONArray = new JSONArray();
            Iterator<cy> it = this.f277a.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().m258a());
            }
            jSONObject.put("ah", jSONArray);
        } catch (Throwable th) {
            throw th;
        }
        return jSONObject;
    }

    public synchronized di a(JSONObject jSONObject) {
        this.f275a = jSONObject.getLong("tt");
        this.f23578a = jSONObject.getInt("wt");
        this.f276a = jSONObject.getString("host");
        JSONArray jSONArray = jSONObject.getJSONArray("ah");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.f277a.add(new cy().a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }
}
