package com.taobao.accs.net;

/* loaded from: classes4.dex */
class x implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ v f20282a;

    x(v vVar) {
        this.f20282a = vVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20282a.o();
        if (this.f20282a.I != null) {
            this.f20282a.I.setCloseReason("shut down");
        }
        synchronized (this.f20282a.f20269u) {
            try {
                this.f20282a.f20269u.notifyAll();
            } catch (Exception unused) {
            }
        }
    }
}
