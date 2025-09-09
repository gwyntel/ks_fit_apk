package com.taobao.accs.internal;

/* loaded from: classes4.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.taobao.accs.c f20182a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ACCSManagerImpl f20183b;

    b(ACCSManagerImpl aCCSManagerImpl, com.taobao.accs.c cVar) {
        this.f20183b = aCCSManagerImpl;
        this.f20182a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.taobao.accs.c cVar = this.f20182a;
        if (cVar != null) {
            cVar.a(true, false);
        }
    }
}
