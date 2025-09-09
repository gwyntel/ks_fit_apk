package com.xiaomi.push.service;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class am extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24463a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ aw f1008a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1009a;

    am(String str, aw awVar, int i2) {
        this.f1009a = str;
        this.f1008a = awVar;
        this.f24463a = i2;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return this.f1009a;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1008a.a(this.f24463a);
    }
}
