package com.taobao.accs.net;

/* loaded from: classes4.dex */
class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f20283a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ v f20284b;

    y(v vVar, String str) {
        this.f20284b = vVar;
        this.f20283a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = this.f20283a;
        if (str != null && str.equals(this.f20284b.O) && this.f20284b.f20268t == 2) {
            this.f20284b.K = false;
            this.f20284b.M = true;
            this.f20284b.o();
            this.f20284b.I.setCloseReason("conn timeout");
        }
    }
}
