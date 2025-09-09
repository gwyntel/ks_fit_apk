package com.xiaomi.push;

import org.json.JSONArray;

/* loaded from: classes4.dex */
public class bt extends JSONArray implements bs {

    /* renamed from: a, reason: collision with root package name */
    private int f23515a = 2;

    @Override // com.xiaomi.push.bs
    public int a() {
        return this.f23515a + (length() - 1);
    }

    @Override // org.json.JSONArray
    public JSONArray put(Object obj) {
        if (obj instanceof bs) {
            this.f23515a += ((bs) obj).a();
        }
        return super.put(obj);
    }
}
