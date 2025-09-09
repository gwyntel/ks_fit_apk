package com.xiaomi.push;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class da {

    /* renamed from: a, reason: collision with root package name */
    private String f23568a;

    /* renamed from: a, reason: collision with other field name */
    private final ArrayList<cz> f261a = new ArrayList<>();

    public da(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        this.f23568a = str;
    }

    public synchronized void a(cz czVar) {
        int i2 = 0;
        while (true) {
            try {
                if (i2 >= this.f261a.size()) {
                    break;
                }
                if (this.f261a.get(i2).a(czVar)) {
                    this.f261a.set(i2, czVar);
                    break;
                }
                i2++;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (i2 >= this.f261a.size()) {
            this.f261a.add(czVar);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f23568a);
        sb.append("\n");
        Iterator<cz> it = this.f261a.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }

    public da() {
    }

    public synchronized cz a() {
        for (int size = this.f261a.size() - 1; size >= 0; size--) {
            cz czVar = this.f261a.get(size);
            if (czVar.m262a()) {
                dd.a().m273a(czVar.a());
                return czVar;
            }
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ArrayList<cz> m264a() {
        return this.f261a;
    }

    public synchronized void a(boolean z2) {
        try {
            for (int size = this.f261a.size() - 1; size >= 0; size--) {
                cz czVar = this.f261a.get(size);
                if (z2) {
                    if (czVar.c()) {
                        this.f261a.remove(size);
                    }
                } else if (!czVar.b()) {
                    this.f261a.remove(size);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m263a() {
        return this.f23568a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized JSONObject m265a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("host", this.f23568a);
            JSONArray jSONArray = new JSONArray();
            Iterator<cz> it = this.f261a.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().m260a());
            }
            jSONObject.put("fbs", jSONArray);
        } catch (Throwable th) {
            throw th;
        }
        return jSONObject;
    }

    public synchronized da a(JSONObject jSONObject) {
        this.f23568a = jSONObject.getString("host");
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.f261a.add(new cz(this.f23568a).a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }
}
