package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import java.util.List;

/* loaded from: classes2.dex */
class a implements f {

    /* renamed from: a, reason: collision with root package name */
    private final d f8796a;

    a(Context context) {
        this.f8796a = new d(context);
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public List<e> a() {
        return this.f8796a.b();
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public void b(e eVar) {
        this.f8796a.b(eVar.f8803m, eVar.host);
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public void a(e eVar) {
        this.f8796a.m31a(eVar);
    }
}
