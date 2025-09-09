package com.vivo.push;

/* loaded from: classes4.dex */
final class i implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f23172a;

    i(e eVar) {
        this.f23172a = eVar;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        if (i2 != 0) {
            this.f23172a.f23148k = null;
            this.f23172a.f23147j.b("APP_TOKEN");
        } else {
            this.f23172a.f23148k = "";
            this.f23172a.f23147j.a("APP_TOKEN", "");
            this.f23172a.m();
            this.f23172a.f23147j.b("APP_TAGS");
        }
    }
}
