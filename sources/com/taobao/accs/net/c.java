package com.taobao.accs.net;

import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f20212a;

    c(a aVar) {
        this.f20212a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f20212a.f20195e.c()) {
            ALog.e(this.f20212a.d(), "receive ping time out! ", new Object[0]);
            g.a(this.f20212a.f20194d).c();
            this.f20212a.a("", false, "receive ping timeout");
            this.f20212a.f20195e.a(-12);
        }
    }
}
