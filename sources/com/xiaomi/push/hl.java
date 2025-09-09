package com.xiaomi.push;

/* loaded from: classes4.dex */
class hl implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ hi f23888a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f540a;

    hl(hi hiVar, String str) {
        this.f23888a = hiVar;
        this.f540a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        dd.a().a(this.f540a, true);
    }
}
